package edu.clevertec.check.util;

import edu.clevertec.check.service.impl.SupermarketServiceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileOutput {
    public static void printReceiptToConsole(SupermarketServiceImpl supermarketServiceImpl, StringBuilder infoFromOrderSb) {
        System.out.println(getReadyReceipt(supermarketServiceImpl, infoFromOrderSb));
    }

    public static void printReceiptToFile(File file, SupermarketServiceImpl supermarketServiceImpl, StringBuilder infoSb) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write(getReadyReceipt(supermarketServiceImpl, infoSb));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getReadyReceipt(SupermarketServiceImpl supermarketServiceImpl, StringBuilder infoFromOrderSb) {
        StringBuilder readyReceipt = new StringBuilder();
        readyReceipt.append("****************************************\n");
        readyReceipt.append(String.format("%1s %28s %9s", "*", supermarketServiceImpl.getName(),"*")).append("\n");
        readyReceipt.append(String.format("%1s %24s %13s", "*", "EKE \"Centrail\"", " *")).append("\n");
        readyReceipt.append(String.format("%1s %26s %11s", "*", supermarketServiceImpl.getPhoneNumber(),"*")).append("\n");
        readyReceipt.append(String.format("%1s %27s", "ZWDN:304219","CKHO:300030394")).append("\n");
        readyReceipt.append(String.format("%1s %23s", "REGN:3100016076","UNP:390286042")).append("\n");
        readyReceipt.append(String.format("%1s %14s", "KASSA:0001 Change:000750","DKH:000271821")).append("\n");
        readyReceipt.append(String.format("%1s %20s", "31 Osipova Tatiana","CHK:01/000000285")).append("\n");
        readyReceipt.append(getCurrentTimeAsString()).append("\n");
        readyReceipt.append("----------------------------------------\n");
        readyReceipt.append(String.format("%-5s %-17s %5s %10s\n", "QTY", "DESCRIPTION", "PRICE", "TOTAL"));
        readyReceipt.append(infoFromOrderSb);

        return readyReceipt.toString();
    }

    private static String getCurrentTimeAsString() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "TIME  " + ZonedDateTime.now().format(formatterTime)
                + "\t\t   DATE  " + ZonedDateTime.now().format(formatterDate);
    }
}
