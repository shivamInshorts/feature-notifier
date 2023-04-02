package com.nis.app.featurenotifierusage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.nis.app.featurenotifier.NotifierCore
import com.nis.app.featurenotifier.NotifierLib
import com.nis.app.featurenotifier.views.tooltip.Tooltip

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var notifierCore: NotifierCore
    private var button: Button? = null;
    var tooltip: Tooltip? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tagName = "settings";

        NotifierLib.getInstance().build(NotifierProps(application))
        NotifierLib.getInstance().getNotifierCore()?.let { notifierCore = it }

        val layout = findViewById<FrameLayout>(R.id.lol)
        button = findViewById(R.id.button)
        var tooltipView = notifierCore.getTooltipNotifierForTag(button, "settings", this);

        notifierCore.canShowNotifierHere(tagName).observe(this) {
            button!!.post {
                tooltipView
                    ?.doOnHidden {
                        tooltipView = null
                    }
                    ?.doOnFailure { }
                    ?.doOnShown {}
                    ?.show(button!!, Tooltip.Gravity.CENTER, true)
            }
        }

        button?.setOnClickListener {
            startActivity(Intent(this, FeatureActivity::class.java));
        }
    }
}