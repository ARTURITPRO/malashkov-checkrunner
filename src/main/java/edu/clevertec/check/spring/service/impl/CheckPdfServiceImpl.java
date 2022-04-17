package edu.clevertec.check.spring.service.impl;

import edu.clevertec.check.exception.InvalidCardNumberException;
import edu.clevertec.check.exception.InvalidCardTypeException;
import edu.clevertec.check.exception.NoSuchProductException;
import edu.clevertec.check.exception.OrderAreNotCreatedException;
import edu.clevertec.check.service.SupermarketService;
import edu.clevertec.check.spring.service.*;
import edu.clevertec.check.pdf.CashReceiptPdfFilePrinter;
import edu.clevertec.check.pdf.CashReceiptPrinter;
import edu.clevertec.check.service.impl.SupermarketServiceImpl;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
@RequiredArgsConstructor
public class CheckPdfServiceImpl implements CheckPdfService {

    private final CashReceiptPrinter pdfFilePrinter;
    private final SupermarketService dionis17;

//    @PostConstruct
//    public void main() {
//        dionis17.setName();
//        dionis17.setName();
//        dionis17.setName();
//    }

    public byte[] getReceiptInPDF(String a) throws InvalidCardNumberException, NoSuchProductException,
            InvalidCardTypeException, OrderAreNotCreatedException, IOException {
        dionis17.addOrder(a).processOrder();
        final byte[] bytes = pdfFilePrinter.print(dionis17);
        return bytes;
    }
}
