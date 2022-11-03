package com.vti.android.delegatedscopemanagement.testapp.receiver

import android.app.admin.DelegatedAdminReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.Q)
class AdminReceiver : DelegatedAdminReceiver() {
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

    override fun onNetworkLogsAvailable(
        context: Context,
        intent: Intent,
        batchToken: Long,
        networkLogsCount: Int
    ) {
        Log.d(TAG, "onNetworkLogsAvailable: $batchToken")
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
    }

    override fun onSecurityLogsAvailable(context: Context, intent: Intent) {
        Log.d(TAG, "onSecurityLogsAvailable: ")
        super.onSecurityLogsAvailable(context, intent)
    }

    companion object {
        private val TAG = AdminReceiver::class.simpleName
    }
}