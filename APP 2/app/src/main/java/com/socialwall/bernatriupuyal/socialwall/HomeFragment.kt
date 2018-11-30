package com.socialwall.bernatriupuyal.socialwall


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO: Put list
        sendButton.setOnClickListener {

            // TODO: check if user logged in

            if (FirebaseAuth.getInstance().currentUser == null) {

                val signUpIntent = Intent(activity, SignUpActivity::class.java)
                startActivity(signUpIntent)
                return@setOnClickListener
            }

            //no mandar mensajes vacios chaval
            if(userInput.text.toString().isEmpty()) return@setOnClickListener


            val db = FirebaseFirestore.getInstance()

            // Get User


            var authUser = FirebaseAuth.getInstance().currentUser!!
            db.collection(COLLECTION_USERS).document(authUser.uid).get().addOnSuccessListener { documentSnapshot ->
                val userProfile = documentSnapshot.toObject(UserProfile::class.java)
                userProfile.let {
                    Log.i("homefragment", "got user profiles")


                    //get user text
                    var userText = userInput.text.toString()

                    val userMessage = MessageModel(text = userText, createdAt = Date())

                    db.collection(COLLECTION_MESSAGES).add(userMessage).addOnSuccessListener {
                        refreshdata()

                    }.addOnFailureListener {

                        Log.e("HomeFragment", it.message)
                    }
                }
            }
                .addOnFailureListener {
                    Log.e("matat", "ha fallat algo pero ja no se")
                }
        }

    }

    private fun refreshdata() {
        val db = FirebaseFirestore.getInstance()


        db.collection("messages").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //todo get messages
                task.result?.forEach { documentSnapshot ->
                    val messages = documentSnapshot.toObject(MessageModel::class.java)
                    Log.i("MainActivity", "Get Message with Text: " + messages.text)
                }
            } else {
                //todo: oh shiattyyyy
            }
        }
    }

}
