package edu.clevertec.check.spring.controller;

import edu.clevertec.check.spring.service.CheckPdfService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pdf")
public class CheckController {

    private final CheckPdfService receiptInPDFService;

    @SneakyThrows
    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    protected byte[] getReceipt(@RequestParam(value = "arguments", required = false,
            defaultValue = "1-2 2-3 3-5 4-1 5-4 6-3 7-4 8-2 card-5") String arguments) {
        return receiptInPDFService.getReceiptInPDF(arguments);
    }

}
