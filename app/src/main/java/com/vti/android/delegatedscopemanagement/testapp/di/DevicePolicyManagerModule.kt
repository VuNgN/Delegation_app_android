package com.vti.android.delegatedscopemanagement.testapp.di

import android.app.admin.DevicePolicyManager
import android.content.Context
import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
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
    fun provideDelegateManager(
        @ApplicationContext context: Context, devicePolicyManager: DevicePolicyManager
    ): DelegateDevicePolicyManager = DelegateDevicePolicyManager(context, devicePolicyManager)
}