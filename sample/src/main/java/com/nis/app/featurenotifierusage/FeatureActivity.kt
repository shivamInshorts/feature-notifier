package com.nis.app.featurenotifierusage

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nis.app.featurenotifier.NotifierCore
import com.nis.app.featurenotifier.NotifierLib

class FeatureActivity : AppCompatActivity() {
    val tagName = "settings"
    private lateinit var notifierCore: NotifierCore
    private val TAG = "FeatureActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)
        NotifierLib.getInstance().getNotifierCore()?.let { notifierCore = it }

        val layout = findViewById<FrameLayout>(R.id.root)
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

        findViewById<Button>(R.id.button).setOnClickListener { notifierCore.notifierShown(tagName) }

    }
}