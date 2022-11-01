package com.vti.android.delegatedscopemanagement.testapp.di

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.vti.android.delegatedscopemanagement.testapp.receiver.AdminReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DevicePolicyManagerModule {
    @Singleton
    @Provides
    fun provideDevicePolicy(@ApplicationContext context: Context): DevicePolicyManager =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    @Singleton
    @Provides
    fun provideComponentNames(@ApplicationContext context: Context): List<ComponentName> {
        val componentNames = mutableListOf<ComponentName>()
        val intent = Intent("android.app.action.DEVICE_ADMIN_ENABLED", null)
        val pkgAppsList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.queryBroadcastReceivers(
                intent, PackageManager.ResolveInfoFlags.of(
                    PackageManager.MATCH_ALL.toLong()
                )
            )
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.queryBroadcastReceivers(intent, 0)
        }
        for (aResolveInfo in pkgAppsList) {
            val componentName =
                ComponentName(aResolveInfo.activityInfo.packageName, aResolveInfo.activityInfo.name)
            componentNames.add(componentName)
        }
        return componentNames
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Singleton
    @Provides
    fun provideComponentName(@ApplicationContext context: Context): ComponentName {
        return ComponentName(context, AdminReceiver::class.java)
    }
}