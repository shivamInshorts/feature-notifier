package com.nis.app.featurenotifierusage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nis.app.featurenotifier.NotifierCore
import com.nis.app.featurenotifier.NotifierLib

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var notifierCore: NotifierCore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tagName = "settings";

        // goes to app class
        NotifierLib.Builder().setNotifierProp(NotifierProps(application)).create(application);

        val layout = findViewById<FrameLayout>(R.id.lol)
        val dotView = notifierCore.getDotNotifierForTag(tagName)

        notifierCore.canShowNotifierHere(tagName).observe(this) {
            Log.d(TAG, "onCreate: ")
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            dotView?.let { view ->
                view.layoutParams = ViewGroup.LayoutParams(28, 28)
                if (it)
                    layout.addView(view)
                else layout.removeView(view)
                layout.invalidate()
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, FeatureActivity::class.java));
        }
    }
}