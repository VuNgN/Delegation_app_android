package com.vti.android.delegatedscopemanagement.testapp.module;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.vti.android.delegatedscopemanagement.testapp.BuildConfig;
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.ApplicationPermission;
import com.vti.android.delegatedscopemanagement.testapp.util.FileUtils;

import java.util.List;


public class DelegateDevicePolicyManager {
    private final DevicePolicyManager devicePolicyManager;
    private final Context context;

    public DelegateDevicePolicyManager(Context context, DevicePolicyManager devicePolicyManager) {
        this.devicePolicyManager = devicePolicyManager;
        this.context = context;
    }

    public List<String> getScopes() {
        return devicePolicyManager.getDelegatedScopes(null, BuildConfig.APPLICATION_ID);
    }

    public int getNumberOfCerts() {
        return devicePolicyManager.getInstalledCaCerts(null).size();
    }

    public boolean installCert() {
        String filename = "ca-example.cer";
        byte[] certBuffer = FileUtils.Companion.read(context.getAssets(), filename);
        return devicePolicyManager.installCaCert(null, certBuffer);
    }

    public boolean uninstallCert() {
        List<byte[]> installedCaCerts = devicePolicyManager.getInstalledCaCerts(null);
        if (installedCaCerts.isEmpty()) {
            return false;
        }
        devicePolicyManager.uninstallCaCert(null, installedCaCerts.get(0));
        return true;
    }

    public void uninstallAllCert() {
        devicePolicyManager.uninstallAllUserCaCerts(null);
    }

    public Bundle getRestrictStatus() {
        return devicePolicyManager.getApplicationRestrictions(null, "com.android.chrome");
    }

    public void setAppRestrictions(Bundle bundle) {
        devicePolicyManager.setApplicationRestrictions(null, "com.android.chrome", bundle);
    }

    public void blockUninstall(String packageName, boolean isBlock) {
        devicePolicyManager.setUninstallBlocked(null, packageName, isBlock);
    }

    public boolean getBlockUninstall(String packageName) {
        return devicePolicyManager.isUninstallBlocked(null, packageName);
    }

    public boolean setPermissionGrantState(ApplicationPermission applicationPermission) {
        int grantState = DevicePolicyManager.PERMISSION_GRANT_STATE_DEFAULT;
        switch (applicationPermission.getGrantState()) {
            case DEFAULT:
                break;
            case GRANT:
                grantState = DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED;
                break;
            case DENY:
                grantState = DevicePolicyManager.PERMISSION_GRANT_STATE_DENIED;
                break;
        }
        return devicePolicyManager.setPermissionGrantState(null, applicationPermission.getPackageName(), applicationPermission.getPermission(), grantState);
    }

    public void setPermissionPolicy(int policy) {
        devicePolicyManager.setPermissionPolicy(null, policy);
    }

    public boolean setApplicationHidden(String packageName, boolean isHide) {
        return devicePolicyManager.setApplicationHidden(null, packageName, isHide);
    }

    public String[] setPackageSuspended(String packageName, boolean isSuspend) {
        String[] packageNames = {packageName};
        return devicePolicyManager.setPackagesSuspended(null, packageNames, isSuspend);
    }

    public void clearPackageAccessState(String packageName) {
        String[] packageNames = {packageName};
        devicePolicyManager.setApplicationHidden(null, packageName, false);
        devicePolicyManager.setPackagesSuspended(null, packageNames, false);
    }

    public int enableSystemApp(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        return devicePolicyManager.enableSystemApp(null, intent);
    }

    public void enableNetworkLogging(Boolean isEnable) {
        devicePolicyManager.setNetworkLoggingEnabled(null, isEnable);
    }

    public boolean isEnableNetworkLogging() {
        return devicePolicyManager.isNetworkLoggingEnabled(null);
    }
}
