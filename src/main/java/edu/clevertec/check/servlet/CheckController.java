package edu.clevertec.check.servlet;

import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;
import edu.clevertec.check.pdf.CashReceiptPdfFilePrinter;
import edu.clevertec.check.pdf.CashReceiptPrinter;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/api/pdf"})
@Slf4j
public class CheckController extends HttpServlet {

    private final CashReceiptPrinter pdfFilePrinter = new CashReceiptPdfFilePrinter();

    private SupermarketServiceImpl dionis17 = new SupermarketServiceImpl("Storage \"Dionis17\" ",
            "+375(29)937-99-92");

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String storageArguments = req.getParameter("arguments");
            dionis17.addOrder(storageArguments)
                    .processOrder();
        } catch (NoSuchProductException | InvalidCardTypeException | InvalidCardNumberException |
                OrderAreNotCreatedException r) {
            log.error("error", r);
        }

        try {
            final byte[] bytes = pdfFilePrinter.print(dionis17);
            resp.setContentType("application/pdf");
            resp.setStatus(201);
            resp.addHeader("Content-Disposition", "attachment; filename=");
            resp.setContentLength(bytes.length);
            resp.getOutputStream().write(bytes);
        } catch (Exception e) {
            resp.sendError(400, "Incorrect request: " + e.getMessage());
        }
    }


}
