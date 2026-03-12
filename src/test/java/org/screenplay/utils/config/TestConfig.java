package org.screenplay.utils.config;


public final class TestConfig {

    public static final String BASE_URL =
            System.getProperty("webdriver.base.url", "http://localhost:3000");

    public static final String LOGIN_URL    = BASE_URL + "/login";
    public static final String REGISTER_URL = BASE_URL + "/register";
    public static final String TICKETS_URL  = BASE_URL + "/tickets";

    public static final int DEMO_DELAY =
            Integer.parseInt(System.getProperty("demo.delay", "1"));

    public static final String ADMIN_EMAIL    = "admin@sofkau.com";
    public static final String ADMIN_PASSWORD = "Admin@SofkaU_2026!";

    public static final String AUTH_API_URL     = "http://localhost:8003/api/auth";
    public static final String TICKETS_API_URL  = "http://localhost:8000/api/tickets";

    private TestConfig() {
    }
}
