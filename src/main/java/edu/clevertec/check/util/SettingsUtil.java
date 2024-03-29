package edu.clevertec.check.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@UtilityClass
public class SettingsUtil {

    private static final Map<String, String> SETTINGS = new HashMap<>();
    private static final String SETTING_REGEX = "(.+)(:\\s+)(.+)";

    static {
        loadSettings();
    }

    public static String get(String key) {
        return SETTINGS.get(key);
    }

    @SneakyThrows
    private static void loadSettings() {

        System.out.println("SETTINGS");
        try (InputStream inputStream = SettingsUtil.class.getClassLoader()
                .getResourceAsStream("application.yml")) {
            if (inputStream != null) {
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .forEach(SettingsUtil::parseSetting);
            }
        }
        System.out.println("SETTINGS " + SETTINGS);
    }

    private static void parseSetting(String setting) {
        Matcher matcher = Pattern.compile(SETTING_REGEX).matcher(setting);
        if (matcher.find()) {
            SETTINGS.put(matcher.group(1), matcher.group(3));
        }
    }
}


















