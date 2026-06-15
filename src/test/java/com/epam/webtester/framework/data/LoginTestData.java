package com.epam.webtester.framework.data;

public final class LoginTestData {

    private LoginTestData() {
        // Utility class
    }

    public static final String BASE_URL = "https://www.saucedemo.com/";

    public static final String STANDARD_USER = "standard_user";
    public static final String LOCKED_OUT_USER = "locked_out_user";
    public static final String INVALID_USER = "invalid_user";
    public static final String SECRET_SAUCE = "secret_sauce";
    public static final String INVALID_PASSWORD = "wrong_pass";
}
