package com.example.administradordeproyectos.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.TextView
import com.example.administradordeproyectos.Firebase.FirestoreClass
import com.example.administradordeproyectos.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_splash)

        // This is used to hide the status bar and make the splash screen as a full screen activity.


        // This is used to get the file from the assets folder and set it to the title textView.
        val typeface: Typeface =
            Typeface.createFromAsset(assets, "carbon bl.ttf")
        val tv_app_name : TextView = findViewById(R.id.tv_app_name)
        tv_app_name.typeface = typeface

        // Adding the handler to after the a task after some delay.
        Handler().postDelayed({

            // Here if the user is signed in once and not signed out again from the app. So next time while coming into the app
            // we will redirect him to MainScreen or else to the Intro Screen as it was before.

            // Get the current user id
            val currentUserID = FirestoreClass().getCurrentUserID()
            //Start the Intro Activity

            if (currentUserID.isNotEmpty()) {
                // Start the Main Activity
                startActivity(Intent(this@Splash, MainActivity::class.java))
            } else {
                //Start the Intro Activity
                startActivity(Intent(this@Splash, Intro::class.java))
           }
           finish() // Call this when your activity is done and should be closed.
        }, 2500) // Here we pass the delay time in milliSeconds after which the splash activity will disappear.
    }
}