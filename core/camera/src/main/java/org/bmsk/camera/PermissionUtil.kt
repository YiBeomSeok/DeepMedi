package org.bmsk.camera

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionUtil {
    fun checkPermission(context: Context, permissionList: List<String>): Boolean {
        permissionList.forEach {
            if (ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_DENIED
            ) {
                return false
            }
        }
        return true
    }

    fun requestPermission(activity: Activity, permissionList: List<String>) {
        ActivityCompat.requestPermissions(activity, permissionList.toTypedArray(), 1)
    }
    fun requestPermission(fragment: Fragment, permissionList: List<String>) {
        fragment.activity?.requestPermissions(permissionList.toTypedArray(), 1)
    }
}