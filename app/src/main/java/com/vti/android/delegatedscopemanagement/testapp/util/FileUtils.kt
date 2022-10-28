package com.vti.android.delegatedscopemanagement.testapp.util

import android.content.res.AssetManager
import java.io.ByteArrayOutputStream
import java.io.IOException

class FileUtils {
    companion object {
        fun read(manager: AssetManager, filename: String): ByteArray? {
            try {
                val inputStream = manager.open(filename)
                val buffer = ByteArray(8192)
                val output = ByteArrayOutputStream()
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }
                return output.toByteArray()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }
}
