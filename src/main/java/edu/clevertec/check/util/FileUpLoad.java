package edu.clevertec.check.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUpLoad {

    public static String[] loadStringsFromFile(File file) {

        String[] arrString = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            List<String> listOfStrings = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listOfStrings.add(line);
            }

            arrString = listOfStrings.toArray(new String[listOfStrings.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrString;
    }
}
