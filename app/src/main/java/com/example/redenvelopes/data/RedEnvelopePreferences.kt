package com.example.redenvelopes.data

import com.example.redenvelopes.MyApplication
import com.example.redenvelopes.base.BasePreferences
import com.example.redenvelopes.dao.WechatControlVO
import kotlinx.serialization.json.JSON


object RedEnvelopePreferences :
    BasePreferences("redenvelope_preferences", MyApplication.instance.applicationContext) {

    private val WECHAT_CONTROL = "wechat_control"
    private val TIME = "TIME"

    var wechatControl: WechatControlVO
        get() {
            val data = getString(WECHAT_CONTROL, "")
            if (data.isNullOrEmpty()) return WechatControlVO()
            return try {
                JSON.parse(WechatControlVO.serializer(), data)
            } catch (e: Exception) {
                setString(
                    WECHAT_CONTROL,
                    JSON.stringify(WechatControlVO.serializer(), WechatControlVO())
                )
                WechatControlVO()
            }
        }
        set(value) {
            setString(WECHAT_CONTROL, JSON.stringify(WechatControlVO.serializer(), value))
        }
    var daleyTime: Long
        get() = getLong(TIME, 0L)
        set(value) {
            setLong(TIME, value)
        }
}