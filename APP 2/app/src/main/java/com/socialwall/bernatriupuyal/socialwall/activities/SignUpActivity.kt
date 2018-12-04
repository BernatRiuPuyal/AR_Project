package com.socialwall.bernatriupuyal.socialwall.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.socialwall.bernatriupuyal.socialwall.COLLECTION_USERS
import com.socialwall.bernatriupuyal.socialwall.R
import com.socialwall.bernatriupuyal.socialwall.model.UserProfile
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        submitButton.setOnClickListener{
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()){


                submitButton.isEnabled = false
                progressBar.visibility = View.VISIBLE


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUpActivity", "createUserWithEmail:success")
                            val userAuth = FirebaseAuth.getInstance().currentUser

                            userAuth?.let {userAuth->

                                //create user document

                                val userProfile = UserProfile(
                                    username = username,
                                    userId = userAuth.uid,
                                    email = email
                                );

                                //users

                                val db = FirebaseFirestore.getInstance()
                                db.collection(COLLECTION_USERS).document(userAuth.uid).set(userProfile).addOnCompleteListener { task->
                                    if(task.isSuccessful){
                                     //all OK
                                        finish()
                                    }
                                    else{
                                        Toast.makeText(this,"Could not create user, ty again later",Toast.LENGTH_SHORT).show()
                                    }
                                }
                                userAuth.uid


                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this@SignUpActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                            submitButton.isEnabled = true
                            progressBar.visibility = View.GONE

                        }

                        // ...
                    }

            }
        }
    }






}
