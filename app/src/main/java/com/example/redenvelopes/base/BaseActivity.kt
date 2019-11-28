package com.example.redenvelopes.base

import android.content.pm.ActivityInfo
import android.os.Bundle

open class BaseActivity : CBaseAccessibilityActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}
