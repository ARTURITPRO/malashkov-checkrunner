package edu.clevertec.check.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import edu.clevertec.check.dto.Product;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;

public class CashReceiptPdfFilePrinter implements CashReceiptPrinter {

    @Override
    public void print(SupermarketServiceImpl check) throws IOException {

        String readFilePath = "src\\main\\resources\\clevertec.pdf";
        String pathRecordFile = "checkOfSupermarket.pdf";

        PdfDocument backgroundPdfDocument = new PdfDocument(new PdfReader(readFilePath));

        PdfDocument checkPdfDocument = new PdfDocument(new PdfWriter(pathRecordFile));
        checkPdfDocument.addNewPage();

        Document document = new Document(checkPdfDocument);
        document.add(getHeadingTable());
        document.add(getProductTable(check));
        document.add(getResultTable(check));

        PdfCanvas PdfCanvas = new PdfCanvas(checkPdfDocument.getFirstPage().newContentStreamBefore(),
                checkPdfDocument.getFirstPage().getResources(), checkPdfDocument);

        PdfFormXObject pdfFormXObject = backgroundPdfDocument.getFirstPage().copyAsFormXObject(checkPdfDocument);
        PdfCanvas.addXObjectAt(pdfFormXObject, 0, 0);

        backgroundPdfDocument.close();
        checkPdfDocument.close();
        document.close();
    }

    private static Table getHeadingTable() {
        Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth().setMarginTop(100);

        table.setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setWidth(UnitValue.createPercentValue(100))
                .setFontSize(16f);

        Cell cashReceipt = new Cell(1, 5).add(new Paragraph("Storage \"Dionis17\" "));
        Cell quentity = new Cell(1, 1).add(new Paragraph("QTY"));
        Cell description = new Cell(1, 1).add(new Paragraph("DESCRIPTION"));
        Cell price = new Cell(1, 1).add(new Paragraph("PRICE"));
        Cell total = new Cell(1, 1).add(new Paragraph("TOTAL"));

        table.addCell(cashReceipt);
        table.addCell(quentity);
        table.addCell(description);
        table.addCell(price);

        table.addCell(total);

        return table.setTextAlignment(TextAlignment.CENTER);
    }

    private static Table getProductTable(SupermarketServiceImpl check) {
        Map<Product, Integer> mapProduct = check.getOrderMap();
        Table tableProducts = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();

        for (Map.Entry<Product, Integer> product : mapProduct.entrySet()) {
            Cell qty = new Cell(1, 1).add(new Paragraph(String.valueOf(product.getValue())));
            Cell description = new Cell(1, 1).add(new Paragraph(String.valueOf(product.getKey().getName())));
            Cell price = new Cell(1, 1).add(new Paragraph(String.valueOf(product.getKey().getCost())));
            Cell total = new Cell(1, 1).add(new Paragraph(String.valueOf(CashReceiptPdfFilePrinter.
                    cutDouble((product.getKey().getCost()) * (product.getValue())))));

            tableProducts.addCell(qty);
            tableProducts.addCell(description);
            tableProducts.addCell(price);

            tableProducts.addCell(total);
        }

        return tableProducts.setTextAlignment(TextAlignment.CENTER);
    }

    private static Table getResultTable(SupermarketServiceImpl check) {
        Table totalPurchase = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

        Cell niceDay = new Cell(1, 1).add(new Paragraph("Have a nice day !"));
        Cell phoneNumber = new Cell(1, 1).add(new Paragraph("Phone number: " + check.getPhoneNumber()));
        Cell totalPrice = new Cell(2, 2).add(new Paragraph("Total price: " + CashReceiptPdfFilePrinter.
                cutDouble(check.getTotalCost())));

        totalPurchase.addCell(totalPrice);
        totalPurchase.addCell(phoneNumber);
        totalPurchase.addCell(niceDay);

        return totalPurchase.setTextAlignment(TextAlignment.CENTER);
    }

    private static double cutDouble(double value) {
        MathContext mathContext = new MathContext(15, RoundingMode.HALF_UP);
        BigDecimal bigDecimal = new BigDecimal(value, mathContext);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        value = bigDecimal.doubleValue();
        return value;
    }
}
