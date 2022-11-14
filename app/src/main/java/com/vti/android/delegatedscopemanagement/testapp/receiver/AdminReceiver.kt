package com.vti.android.delegatedscopemanagement.testapp.receiver

import android.app.admin.DelegatedAdminReceiver
import android.app.admin.DevicePolicyManager
import android.app.admin.DnsEvent
import android.app.admin.NetworkEvent
import android.app.admin.SecurityLog.SecurityEvent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
class AdminReceiver : DelegatedAdminReceiver() {
    override fun onNetworkLogsAvailable(
        context: Context,
        intent: Intent,
        batchToken: Long,
        networkLogsCount: Int
    ) {
        Log.d(TAG, "onNetworkLogsAvailable: $batchToken")
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
        val dpm = getManager(context)
        var logs: List<NetworkEvent>? = null
        // Fetch the batch of logs with the batch token from the callback's arguments.
        try {
            logs = dpm.retrieveNetworkLogs(getWho(context), batchToken)
        } catch (e: SecurityException) {
            // Perhaps an unaffiliated user - handle the exception ...
            Log.e(TAG, "onNetworkLogsAvailable: ${e.message}")
        }
        // Here, logs might be null. We can't fix because either the token doesn't match
        // the current batch or network logging was deactivated.
        // Confirm with isNetworkLoggingEnabled().
        logs?.forEach {
            Log.d(
                TAG,
                "onNetworkLogsAvailable --> packageName: ${it.packageName},  ${if (it is DnsEvent) "hostname: ${it.hostname}, " else ""}, time: ${
                    getDateTime(
                        it.timestamp
                    )
                }"
            )
        }
    }

    override fun onSecurityLogsAvailable(context: Context, intent: Intent) {
        Log.d(TAG, "onSecurityLogsAvailable")
        super.onSecurityLogsAvailable(context, intent)
        val dpm = getManager(context)
        var logs: List<SecurityEvent>? = null
        try {
            logs = dpm.retrieveSecurityLogs(getWho(context))
        } catch (e: SecurityException) {
            // Perhaps an unaffiliated user - handle the exception ...
            Log.e(TAG, "onSecurityLogsAvailable: ${e.message}")
        }
        logs?.forEach {
            Log.d(
                TAG,
                "onSecurityLogsAvailable --> id: ${it.id}, log level: ${it.logLevel}, has data: ${it.data != null}, time: ${
                    getDateTime(
                        it.timeNanos
                    )
                }"
            )
        }
    }

    private fun getDateTime(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
            val netDate = Date(s * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun getManager(context: Context): DevicePolicyManager {
        return context.getSystemService(
            Context.DEVICE_POLICY_SERVICE
        ) as DevicePolicyManager
    }

    private fun getWho(context: Context): ComponentName {
        return ComponentName(context, javaClass)
    }

    companion object {
        private val TAG = AdminReceiver::class.simpleName
    }
}