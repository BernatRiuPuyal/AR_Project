package com.socialwall.bernatriupuyal.socialwall.fragments


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.socialwall.bernatriupuyal.socialwall.COLLECTION_USERS
import com.socialwall.bernatriupuyal.socialwall.R
import com.socialwall.bernatriupuyal.socialwall.model.UserProfile
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseAuth.getInstance().currentUser?.let {
            //we have user
            userFields.visibility = View.VISIBLE
            signUpButton.visibility = View.GONE
            // fill user data

            // Fill user data
            val db = FirebaseFirestore.getInstance()
            // Get user
            db.collection(COLLECTION_USERS).document(it.uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val userProfile = documentSnapshot.toObject(UserProfile::class.java)
                    Log.i("ProfileFragment", "Got user profile: " + userProfile)
                    userProfile?.let {userProfile ->
                        // Populate fields
                        username.text = userProfile.username
                        userEmail.text = userProfile.email




                    }
                }
                .addOnFailureListener {
                    // TODO: failure getitng user
                }







        } ?: kotlin.run{
            // we do not have user
            userFields.visibility = View.GONE
            signUpButton.visibility = View.VISIBLE
        }
    }


        val REQUEST_IMAGE_CAPTURE = 1

        private fun takePicture() {
            activity?.let { activity ->
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    // Ensure that there's a camera activity to handle the intent
                    takePictureIntent.resolveActivity(activity.packageManager)?.also {
                        // Create the File where the photo should go
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                            ex.printStackTrace()
                            Log.e("ProfileFragment", "Error creating camera file")
                            null
                        }
                        // Continue only if the File was successfully created
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                activity,
                                "com.example.android.fileprovider",
                                it
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                        }
                    }
                }
            }

        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){

                mCurrentPhotoPath?.let{

                    userImage.setImageURI(Uri.fromFile(File(it)))

                    //upload to storage


                    val file = File(it)
                    //get reference to final location

                    val avatarStorageReference = FirebaseStorage.getInstance().getReference("images/users/${file.name}.jpg")

                    val uri = Uri.fromFile(file)

                    val uploadTask = avatarStorageReference.putFile(uri)

                    uploadTask .addOnSuccessListener {
                        // Get Download Url
                        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            return@Continuation avatarStorageReference.downloadUrl
                        }).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Got URL!!
                                val downloadUri = task.result
                                // TODO: Save to user profile
                                // Obtenemos el id de usuario
                                FirebaseAuth.getInstance().currentUser?.uid?.let {uid ->
                                    // Actualizamos el documento del usuario
                                    FirebaseFirestore.getInstance().collection(COLLECTION_USERS).document(uid).update("avatarUrl", downloadUri.toString())
                                }


                            } else {
                                // Handle failures
                                Log.w("ProfileFragment", "Error getting download url :( " + task.exception?.message)
                            }
                        }



                    }
                }

        }
    }

    // Create Image File
    var mCurrentPhotoPath: String? = null
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        activity?.let { activity ->
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = absolutePath
            }
        }
        return null
    }



}
