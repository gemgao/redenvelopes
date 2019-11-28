package com.example.redenvelopes.base

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.redenvelopes.`interface`.PermissionListener
import java.util.ArrayList


open class PermissionBaseActivity : AppCompatActivity() {

    private var mRequestCode: Int = 0
    private lateinit var mPermissionListener: PermissionListener

    /**
     * Request permission.
     */
    fun requestPermission(
        requestCode: Int,
        permissionListener: PermissionListener,
        vararg permissions: String
    ) {
        mRequestCode = requestCode
        mPermissionListener = permissionListener
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mPermissionListener.permissionSuccess()
            return
        }
        val deniedPermissions = getDeniedPermissions(*permissions)

        if (deniedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, deniedPermissions.toTypedArray(), requestCode)
        } else {
            mPermissionListener.permissionSuccess()
        }
    }

    /**
     * Check if has this permission.
     */
    fun isHasPermission(permission: String) =
        ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_DENIED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (mRequestCode == requestCode) {
            if (verifyPermiisssions(grantResults))
                mPermissionListener.permissionSuccess()
            else
                mPermissionListener.permissionFail()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * Check permissions that are denied.
     */
    private fun getDeniedPermissions(vararg requestPermissions: String): List<String> {
        val permissions = ArrayList<String>()
        for (requestPermission in requestPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    requestPermission
                ) == PackageManager.PERMISSION_DENIED
            )
                permissions.add(requestPermission)
        }
        return permissions
    }

    private fun verifyPermiisssions(grantResults: IntArray): Boolean {
        if (grantResults.isEmpty())
            return false
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

}
