package com.anuj.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

const val format = "yyyy-MM-dd_hh:mm:ss"
const val REQUEST_EXTERNAL_STORAGE = 1

private val permissionStorage = arrayOf(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
)

class Utils(val context: Activity) {

    fun takeScreenshot(view: View, filename: String): Bitmap? {
        val date = Date()
        var bitmap: Bitmap? = null
        val format: CharSequence = DateFormat.format(format, date)
        try {
            val dirPath = context.externalCacheDir?.absolutePath
            val file = File(dirPath)
            if (!file.exists()) {
                file.mkdir()
            }
            val path = "$dirPath/$filename-$format.jpeg"
            view?.isDrawingCacheEnabled = true
            bitmap = Bitmap.createBitmap(view.drawingCache)
            view?.isDrawingCacheEnabled = false
            val imageurl = File(path)
            val outputStream = FileOutputStream(imageurl)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            outputStream.flush()
            outputStream.close()
            return bitmap
        } catch (io: FileNotFoundException) {
            io.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun verifyStoragePermissions() {
        val permissions = PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, permissionStorage, REQUEST_EXTERNAL_STORAGE)
        }
    }

    fun shareImage(bitmap: Bitmap?) {
        val uri: Uri? = getImageToShare(bitmap)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.type = "image/png"
        context.startActivity(Intent.createChooser(intent, "Share Via"))
    }

    // Retrieving the url to share
    private fun getImageToShare(bitmap: Bitmap?): Uri? {
        val imageFolder = File(context.cacheDir, "images")
        var uri: Uri? = null
        try {
            imageFolder.mkdirs()
            val file = File(imageFolder, "shared_image.png")
            val outputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(context, "com.anuj.fileprovider", file)
        } catch (e: Exception) {
            Toast.makeText(context, "" + e.message, Toast.LENGTH_LONG).show()
        }
        return uri
    }

    fun showToast(context: Activity, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}