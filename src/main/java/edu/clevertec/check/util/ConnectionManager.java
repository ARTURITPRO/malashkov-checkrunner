//package edu.clevertec.check.util;
//
//import lombok.Data;
//import lombok.SneakyThrows;
//import lombok.experimental.UtilityClass;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//@Data
//@UtilityClass
//public class ConnectionManager {
//
//    private static final String PROPERTIES_FILE = "application.yml";
//    private static final String URL = "spring.datasource.url";
//    private static final String USER = "spring.datasource.username";
//    private static final String PASSWORD = "spring.datasource.password";
//    private static final String DRIVER = "spring.datasource.driver";
//    static {
//        loadDriver();
//    }
//
//    @SneakyThrows
//    private static void loadDriver() {
//        Class.forName(SettingsUtil.get(DRIVER));
//    }
//
//    @SneakyThrows
//    public static Connection get() {
//        return DriverManager.getConnection(
//                SettingsUtil.get(URL),
//                SettingsUtil.get(USER),
//                SettingsUtil.get(PASSWORD));
//    }
//}
