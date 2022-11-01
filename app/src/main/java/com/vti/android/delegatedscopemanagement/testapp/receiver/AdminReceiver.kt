package com.vti.android.delegatedscopemanagement.testapp.receiver

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.Q)
class AdminReceiver : DeviceAdminReceiver() {
    override fun onChoosePrivateKeyAlias(
        context: Context,
        intent: Intent,
        uid: Int,
        uri: Uri?,
        alias: String?
    ): String? {
        Log.d(TAG, "onChoosePrivateKeyAlias: $uid")
        return super.onChoosePrivateKeyAlias(context, intent, uid, uri, alias)
    }

    companion object {
        private val TAG = AdminReceiver::class.simpleName
    }
}