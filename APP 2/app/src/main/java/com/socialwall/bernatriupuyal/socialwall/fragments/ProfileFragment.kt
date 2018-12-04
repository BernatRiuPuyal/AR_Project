package com.socialwall.bernatriupuyal.socialwall.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.socialwall.bernatriupuyal.socialwall.COLLECTION_USERS
import com.socialwall.bernatriupuyal.socialwall.R
import com.socialwall.bernatriupuyal.socialwall.model.UserProfile
import kotlinx.android.synthetic.main.fragment_profile.*


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


}
