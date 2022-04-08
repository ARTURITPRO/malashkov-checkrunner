package edu.clevertec.check.pdf;

import edu.clevertec.check.service.impl.SupermarketServiceImpl;


import java.io.IOException;

public interface CashReceiptPrinter {

     byte [] print(SupermarketServiceImpl cashReceipt) throws IOException;

}
