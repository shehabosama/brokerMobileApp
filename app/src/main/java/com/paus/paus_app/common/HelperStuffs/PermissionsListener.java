package com.paus.paus_app.common.HelperStuffs;

/**
 * Created by peter on 19/05/18.
 */

public interface PermissionsListener {

    void onPermissionGranted(String[] permissions);

    void onPermissionDenied(String[] permissions);
}
