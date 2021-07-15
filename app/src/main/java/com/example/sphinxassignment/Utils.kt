package com.example.sphinxassignment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog

import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

object Utils {

    const val MY_PERMISSIONS_REQUEST_LOCATION = 101

    fun showToast(context: Context?, msg: String?) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun isDataConnected(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            Log.d("1st : ", "connectivityManager")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                Log.d("2nd : ", "connectivityManager")
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            } else {
                Log.d("3rd : ", "else")
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
        }
        return false
    }

    fun showSNACK_BAR_NO_INTERNET(activity: Activity, str_class_name: String) {
        Snackbar.make(
            activity.findViewById<View>(R.id.content),
            "No internet connection !",
            Snackbar.LENGTH_INDEFINITE
        ).setAction(
            "Retry"
        ) { v: View? ->
            try {
                activity.startActivity(
                    Intent(
                        activity,
                        Class.forName(str_class_name)
                    )
                )
                activity.finish()
            } catch (e: ClassNotFoundException) {
                // Toast.makeText(activity, s + " does not exist yet", Toast.LENGTH_SHORT).show();
            }
        }.show()
    }

    fun generateProgressDialog(context: Context?): Dialog? {
        val pDialog = Dialog(context!!, com.example.sphinxassignment.R.style.ProgressTheme)
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view: View =
            LayoutInflater.from(context)
                .inflate(com.example.sphinxassignment.R.layout.progress_layout, null)
        pDialog.setContentView(view)
        pDialog.setCancelable(false)
        pDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return pDialog
    }

    fun showMessageOKDialog(
        c: Context?,
        message: String?,
        okListener: DialogInterface.OnClickListener?,
    ) {
        AlertDialog.Builder(c!!)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", okListener)
            .create()
            .show()
    }

    fun arrayAdpter(context: Context?, list: List<String>): ArrayAdapter<String?> {
        val adapter: ArrayAdapter<String?> =
            object : ArrayAdapter<String?>(
                context!!,
                android.R.layout.simple_spinner_dropdown_item,
                list
            ) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int, convertView: View?,
                    parent: ViewGroup,
                ): View {
                    // TODO Auto-generated method stub
                    val mView =
                        super.getDropDownView(position, convertView, parent)
                    val mTextView = mView as TextView
                    if (position == 0) {
                        mTextView.setTextColor(context?.resources?.getColor(R.color.grey)!!)
                    } else {
                        mTextView.setTextColor(Color.BLACK)
                    }
                    return mView
                }
            }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}