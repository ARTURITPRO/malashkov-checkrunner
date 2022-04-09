package edu.clevertec.check.util;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;
import lombok.SneakyThrows;

import javax.lang.model.util.Elements;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

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
    @SneakyThrows
    public static void printReceiptToPdf (SupermarketServiceImpl supermarketServiceImpl, StringBuilder infoFromOrderSb) {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/addingParagraph.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdf);


        String para1 = "guilui;liliol";
        String para2 = "The journey commenced with a single tutorial on HTML in 2006 ";

        // Creating Paragraphs
        Paragraph paragraph1 = new Paragraph(para1);
        Paragraph paragraph2 = new Paragraph(para2);

        // Adding paragraphs to document
        document.add(paragraph1);
        document.add(paragraph2);

        // Closing the document
        document.close();
        System.out.println("Paragraph added");
    }
}
