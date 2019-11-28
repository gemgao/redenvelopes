package com.example.redenvelopes.utils

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.text.TextUtils
import android.view.accessibility.AccessibilityNodeInfo
import java.lang.reflect.Field

object AccessibilityHelper {
    /** 通过id查找 */
    fun findNodeInfosById(
        nodeInfo: AccessibilityNodeInfo,
        resId: String?
    ): AccessibilityNodeInfo? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val list =
                nodeInfo.findAccessibilityNodeInfosByViewId(resId)
            if (list != null && list.isNotEmpty()) {
                return list[0]
            }
        }
        return null
    }

    /** 通过id查找 */
    fun isExistElementById(
        nodeInfo: AccessibilityNodeInfo,
        resId: String?
    ): Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val list =
                nodeInfo.findAccessibilityNodeInfosByViewId(resId)
            if (list != null && list.isNotEmpty()) {
                return true
            }
        }
        return false
    }
    /** 通过文本查找 */
    fun findNodeInfosByText(
        nodeInfo: AccessibilityNodeInfo,
        text: String?
    ): AccessibilityNodeInfo? {
        val list =
            nodeInfo.findAccessibilityNodeInfosByText(text)
        return if (list == null || list.isEmpty()) {
            null
        } else list[0]
    }

    /** 通过关键字查找 */
    fun findNodeInfosByTexts(
        nodeInfo: AccessibilityNodeInfo,
        vararg texts: String?
    ): AccessibilityNodeInfo? {
        for (key in texts) {
            val info =
                findNodeInfosByText(
                    nodeInfo,
                    key
                )
            if (info != null) {
                return info
            }
        }
        return null
    }

    /** 通过组件名字查找 */
    fun findNodeInfosByClassName(
        nodeInfo: AccessibilityNodeInfo,
        className: String
    ): AccessibilityNodeInfo? {
        if (TextUtils.isEmpty(className)) {
            return null
        }
        for (i in 0 until nodeInfo.childCount) {
            val node = nodeInfo.getChild(i)
            if (className == node.className) {
                return node
            }
        }
        return null
    }

    /** 找父组件 */
    fun findParentNodeInfosByClassName(
        nodeInfo: AccessibilityNodeInfo?,
        className: String
    ): AccessibilityNodeInfo? {
        if (nodeInfo == null) {
            return null
        }
        if (TextUtils.isEmpty(className)) {
            return null
        }
        return if (className == nodeInfo.className) {
            nodeInfo
        } else findParentNodeInfosByClassName(
            nodeInfo.parent,
            className
        )
    }

    private var sSourceNodeField: Field? = null
    fun getSourceNodeId(nodeInfo: AccessibilityNodeInfo?): Long {
        if (sSourceNodeField == null) {
            return -1
        }
        try {
            return sSourceNodeField!!.getLong(nodeInfo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }

    fun getViewIdResourceName(nodeInfo: AccessibilityNodeInfo): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            nodeInfo.viewIdResourceName
        } else null
    }

    /** 返回主界面事件 */
    fun performHome(service: AccessibilityService?) {
        if (service == null) {
            return
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
    }

    /** 返回事件 */
    fun performBack(service: AccessibilityService?) {
        if (service == null) {
            return
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

    /** 点击事件 */
    fun performClick(nodeInfo: AccessibilityNodeInfo?) {
        if (nodeInfo == null) {
            return
        }
        if (nodeInfo.isClickable) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else {
            performClick(
                nodeInfo.parent
            )
        }
    }

    init {
        var field: Field? = null
        try {
            field = AccessibilityNodeInfo::class.java.getDeclaredField("mSourceNodeId")
            field.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        sSourceNodeField = field
    }
}