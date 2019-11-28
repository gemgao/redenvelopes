package com.example.redenvelopes.base

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.view.accessibility.AccessibilityManager

open class CBaseAccessibilityActivity : CBaseActivity() {

    private lateinit var accessibilityManager: AccessibilityManager
    private lateinit var accessibilityServiceListeners: AccessibilityServiceListeners
    private lateinit var accessibilityServiceName: String

    interface AccessibilityServiceListeners {

        fun updateStatus(boolean: Boolean)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::accessibilityManager.isInitialized)
            accessibilityManager.removeAccessibilityStateChangeListener(
                mAccessibilityStateChangeListener
            )
    }

    fun addAccessibilityServiceListener(
        accessibilityServiceListeners: AccessibilityServiceListeners,
        accessibilityServiceName: String
    ) {
        this.accessibilityServiceListeners = accessibilityServiceListeners
        this.accessibilityServiceName = accessibilityServiceName
        accessibilityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        accessibilityManager.addAccessibilityStateChangeListener(mAccessibilityStateChangeListener)
    }

    var mAccessibilityStateChangeListener: AccessibilityManager.AccessibilityStateChangeListener =
        AccessibilityManager.AccessibilityStateChangeListener {
            accessibilityServiceListeners.updateStatus(checkStatus())
        }

    fun checkStatus(): Boolean {
        val accessibilityServiceInfoList =
            accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
        for (info in accessibilityServiceInfoList) {
            if (info.id == accessibilityServiceName) {
                return true
            }
        }
        return false
    }

}