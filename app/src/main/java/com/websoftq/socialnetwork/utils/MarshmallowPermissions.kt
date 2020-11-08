package com.websoftq.socialnetwork.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.websoftq.socialnetwork.R

const val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0
const val PERMISSIONS_REQUEST_CAMERA = 1
const val PERMISSION_REQUEST_READ_SMS = 2
const val PERMISSION_REQUEST_RECORD_AUDIO = 3
const val PERMISSIONS_REQUEST_LOCATION = 4


class MarshmallowPermissions {

    private var activity: Activity
    private lateinit var fragment: Fragment

    constructor(fragment: Fragment) {
        this.fragment = fragment
        activity = fragment.activity as Activity
    }

    constructor(activity: AppCompatActivity) {
        this.activity = activity
    }

    private fun showAlert(message: String, okListener: DialogInterface.OnClickListener?, cancelListener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(activity)
            .setMessage(message).setCancelable(false)
            .setPositiveButton("YES", okListener)
            .setNegativeButton("NO", cancelListener).show()
    }

    /* ------------    Storage Application  ---------- */

    fun isPermissionGrantedForStorage() = PackageManager.PERMISSION_GRANTED == checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)

    /* For fragment */
    fun requestPermissionForStorage() = if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        showAlert(activity.getString(R.string.need_storage_permission), DialogInterface.OnClickListener { dialog, _ ->
            fragment.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
            dialog.dismiss()
        }, DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
    } else {
        fragment.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
    }

    /* for Activity */
    fun requestActivityPermissionForStorage() = if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        showAlert(activity.getString(R.string.need_storage_permission), DialogInterface.OnClickListener { dialog, _ ->
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
            dialog.dismiss()
        }, DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
    } else {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
    }

    /* ---------------   Camera Permission   ------------- */

    fun isPermissionGrantedForCamera() = PackageManager.PERMISSION_GRANTED == checkSelfPermission(activity, Manifest.permission.CAMERA)

    /* for fragment */
    fun requestPermissionForCamera() = if (ActivityCompat.shouldShowRequestPermissionRationale(
            activity, Manifest.permission.CAMERA)) {
        showAlert(activity.getString(R.string.need_camera_permission), DialogInterface.OnClickListener { dialog, _ ->
            fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
            dialog.dismiss()
        }, DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
    } else {
        fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
    }

    /* for Activity */
    fun requestActivityPermissionForCamera() = if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
        showAlert(activity.getString(R.string.need_camera_permission), DialogInterface.OnClickListener { dialog, _ ->
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
            dialog.dismiss()
        }, DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
    } else {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
    }

    /* ---------------   Record Audio Permission   ------------- */

    fun isPermissionGrantedForAudio() = PackageManager.PERMISSION_GRANTED == checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)


    /* ------------    Location Application  ---------- */

    fun isPermissionGrantedForLocation() = PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)

    /* For fragment */
    fun requestPermissionForLocation() = if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
        showAlert(activity.getString(R.string.need_Location_permission), DialogInterface.OnClickListener { dialog, _ ->
            fragment.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
            dialog.dismiss()
        }, DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
    } else {
        fragment.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
    }

    /* ------------    Location Application  ---------- */

    fun isPermissionGrantedForLocationActivity() = PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)

    /* For Activity */
    fun requestPermissionForLocationActivity() = if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
        showAlert(activity.getString(R.string.need_Location_permission), DialogInterface.OnClickListener { dialog, _ ->
            ActivityCompat.requestPermissions(activity,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
            dialog.dismiss()
        }, DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })
    } else {
        ActivityCompat.requestPermissions(activity,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
    }

}
