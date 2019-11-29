package com.paus.paus_app.common.HelperStuffs;


/**
 * Created by shehab on 28/11/2019.
 */

public class Constants {

    public static class AppPreferences {
        public static final int STATUS_LOGGED_OUT = 0;
        public static final int STATUS_WAITING_VERIFICATION_CODE = 1;
        public static final int STATUS_LOGGED_IN = 2;
        public static final String DRIVER_IS_HUB_KEY = "driver_is_hub_key";
        public static final String PUSH_KEY = "push_key";
        public static final String BASE_URL_KEY = "base_url_key";
        public static final String LOGGED_IN_USER_KEY = "logged_in_user_key";
        public static final String ORDERS_LAST_UPDATED_TIME = "orders_last_updated_time_key";
        public static final String OS_ANDROID_KEY = "A";
        public static final String ACCOUNT_NUMBER_KEY = "account_number_key";
        public static final String APPLICATION_NAME_KEY = "application_name_key";
        public static final String TYPE_KEY = "type_key";
        public static final String SESSION_ID_KEY = "session_id_key";
        public static final String LOCATION_KEY = "location_key";

    }


    public static class UserTypes {
        public static final String USER_TYPE_CLIENT = "C";
        public static final String DRIVER_TYPE_CLIENT = "D";
    }


    public static class ServiceStatusCodes {

        public static final int STATUS_SUCCESS = 0;
        public static final int STATUS_FAILED = 1;
        public static final int STATUS_INVALID_USERNAME_OR_PASSWORD = 2;
        public static final int STATUS_INVALID_ACCOUNT_NAME = 3;
        public static final int STATUS_INACTIVE_DRIVER = 4;
        public static final int STATUS_ANOTHER_IMEI_EXISTS = 5;
        public static final int STATUS_INVALID_IMEI = 1000;
        public static final int STATUS_NO_NETWORK = 102;
        public static final int STATUS_USER_INACTIVE = 6;
        public static final int STATUS_ERROR_SENDING_EMAIL = 7;
        public static final int ORDER_ALREADY_IN_HUB = 12;


    }



    public static class ActivityRequestCodes {
        public static final int PICKUP_DELIVER_ACTIVITY_REQUEST_CODE = 1000;
        public static final int ORDER_DETAILS_ACTIVITY_REQUEST_CODE = 1001;
        public static final int DELIVERY_SIGNATURE_REQUEST_CODE = 1002;
        public static final int ADD_ORDER_IMAGES_REQUEST_CODE = 1004;
        public static final int REASSIGN_ORDER_REQUEST_CODE = 1005;
        public static final int IMAGE_CAPTURE_REQUEST_CODE = 1006;
        public static final int ACTIVITY_HUB_ORDER_PACKAGES_REQUEST_CODE = 1007;
    }



}
