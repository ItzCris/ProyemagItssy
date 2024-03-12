package com.example.administradordeproyectos.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.administradordeproyectos.R

class Intro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)


            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

            // This is used to get the file from the assets folder and set it to the title textView.
            val typeface: Typeface =
                Typeface.createFromAsset(assets, "carbon bl.ttf")
            val tv_app_name_intro : TextView = findViewById(R.id.tv_app_name_intro)
            tv_app_name_intro.typeface = typeface

            val btn_sign_in_intro : Button = findViewById(R.id.btn_sign_in_intro)
            btn_sign_in_intro.setOnClickListener {

                // Launch the sign in screen.
                startActivity(Intent(this@Intro, Inicio::class.java))
            }
            val btn_sign_up_intro : Button = findViewById(R.id.btn_sign_up_intro)
            btn_sign_up_intro.setOnClickListener {

                // Launch the sign up screen.
                startActivity(Intent(this@Intro, Registrate::class.java))
            }
        }
    }
