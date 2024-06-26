package com.smatech.backendapiservice.common;

import java.net.InetAddress;

public interface SystemConstants {
    String NEW_USER_EMAIL_SUBJECT = "New SmatechRentalPro Credentials";

    int FAILURE_INT_VALUE = 400;
    int SUCCESS_INT_VALUE = 200;

    String FAILURE_MESSAGE = "FAILURE";

    String SUCCESS_MESSAGE = "SUCCESS";

    String BASE_URL= "http://"+InetAddress.getLoopbackAddress().getHostAddress()+":8090";
}
