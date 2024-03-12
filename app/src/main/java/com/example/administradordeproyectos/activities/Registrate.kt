package com.example.administradordeproyectos.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.administradordeproyectos.Firebase.FirestoreClass
import com.example.administradordeproyectos.model.User
import com.example.administradordeproyectos.R


class Registrate : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrate)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()
        val btn_sign_up : Button = findViewById(R.id.btn_sign_up)
        // Click event for sign-up button.
        btn_sign_up.setOnClickListener {
            registerUser()
        }


    }
    private fun setupActionBar() {
        val toolbar_sign_up_activity : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_sign_up_activity)
        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }
    }



    private fun registerUser() {
        // Here we get the text from editText and trim the space
        val et_email : AppCompatEditText = findViewById(R.id.et_email)
        val et_name : AppCompatEditText = findViewById(R.id.et_name)
        val et_password : AppCompatEditText = findViewById(R.id.et_password)
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            // Registered Email
                            val registeredEmail = firebaseUser.email!!

                            val user = User(
                                firebaseUser.uid, name, registeredEmail
                            )

                            // call the registerUser function of FirestoreClass to make an entry in the database.
                            FirestoreClass().registerUser(this@Registrate, user)
                        } else {
                            Toast.makeText(
                                this@Registrate,
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
        }
    }



    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }
    }

    fun userRegisteredSuccess() {

        Toast.makeText(
            this@Registrate,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        // Hide the progress dialog
        hideProgressDialog()

        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        FirebaseAuth.getInstance().signOut()
        // Finish the Sign-Up Screen
        finish()
    }

}