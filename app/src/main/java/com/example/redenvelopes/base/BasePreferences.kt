package com.example.redenvelopes.base

import android.content.Context
import android.content.SharedPreferences

open class BasePreferences(private val fileName: String, private val cContext: Context) {

    open fun setString(key: String, value: String, context: Context = cContext) {
        getEditor(context).putString(key, value).apply()
    }

    open fun getString(key: String, defaultValue: String, context: Context = cContext): String? {
        return getSharedPreferences(context).getString(key, defaultValue)
    }

    open fun setInt(key: String, value: Int, context: Context = cContext) {
        getEditor(context).putInt(key, value).apply()
    }

    open fun getInt(key: String, defaultValue: Int, context: Context = cContext): Int {
        return getSharedPreferences(context).getInt(key, defaultValue)
    }

    open fun setLong(key: String, value: Long, context: Context = cContext) {
        getEditor(context).putLong(key, value).apply()
    }

    open fun getLong(key: String, defaultValue: Long, context: Context = cContext): Long {
        return getSharedPreferences(context).getLong(key, defaultValue)
    }

    open fun setBoolean(key: String, value: Boolean, context: Context = cContext) {
        getEditor(context).putBoolean(key, value).apply()
    }

    open fun getBoolean(key: String, defaultValue: Boolean, context: Context = cContext): Boolean {
        return getSharedPreferences(context).getBoolean(key, defaultValue)
    }

    private fun getEditor(context: Context): SharedPreferences.Editor {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

}