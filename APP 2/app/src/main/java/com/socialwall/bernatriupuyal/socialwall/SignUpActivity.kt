package com.socialwall.bernatriupuyal.socialwall

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUpActivity", "createUserWithEmail:success")
                            val user = FirebaseAuth.getInstance().currentUser
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this@SignUpActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        // ...
                    }

            }
        }
    }






}
