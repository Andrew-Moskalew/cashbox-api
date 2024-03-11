public class MainPage {
    private static final String OPEN_SHIFT_BODY = "{\n" +
            "  \"cashier\": {\n" +
            "    \"id\": \"1\",\n" +
            "    \"name\": \"Иванов И.И.\"\n" +
            "  }\n" +
            "}";

    private static final String CLOSE_SHIFT_BODY = "{}";
    private static final String URL_LOGIN = "http://10.2.0.131:22222/login?pin=12345";
    private static final String URL_OPEN_SHIFT = "http://10.2.0.131:22222/openShift";
    private static final String URL_CLOSE_SHIFT = "http://10.2.0.131:22222/closeShift";

    public static String getOpenShiftBody() {
        return OPEN_SHIFT_BODY;
    }

    public static String getCloseShiftBody() {
        return CLOSE_SHIFT_BODY;
    }

    public static String getUrlLogin() {
        return URL_LOGIN;
    }

    public static String getUrlOpenShift() {
        return URL_OPEN_SHIFT;
    }

    public static String getUrlCloseShift() {
        return URL_CLOSE_SHIFT;
    }

}
