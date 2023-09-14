package com.nis.app.featurenotifierusage

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.nis.app.featurenotifier.NotifierCore
import com.nis.app.featurenotifier.NotifierLib


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var notifierCore: NotifierCore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val tagName = "settings";

//        // goes to app class
        NotifierLib.getInstance().build(NotifierProps(application))
//
        NotifierLib.getInstance().getNotifierCore()?.let { notifierCore = it }
//
//        val layout = findViewById<FrameLayout>(R.id.lol)
//        val dotView = notifierCore.getDotNotifierForTag(tagName)
//
//        notifierCore.canShowNotifierHere(tagName).observe(this) {
//            Log.d(TAG, "onCreate: ")
//            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
//            dotView?.let { view ->
//                view.layoutParams = ViewGroup.LayoutParams(28, 28)
//                if (it)
//                    layout.addView(view)
//                else layout.removeView(view)
//                layout.invalidate()
//            }
//        }
//
//        val button = findViewById<Button>(R.id.button)
//        val badgeDrawable = BadgeDrawable.create(this)
//        badgeDrawable.isVisible = true
//        button.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//            BadgeUtils.attachBadgeDrawable(badgeDrawable, button)
//        }
//
//        button.setOnClickListener {
//            badgeDrawable.isVisible = false
//        }

        val img = findViewById<Button>(R.id.button)
        notifierCore.addBadgeNotifierForView(img)

        img.setOnClickListener {
            notifierCore.notifierShown(img.tag.toString())
            notifierCore.clearOrHideBatch(img.tag.toString())
        }
    }
}