package edu.clevertec.check.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import edu.clevertec.check.dto.Product;
import edu.clevertec.check.service.ProductService;
import edu.clevertec.check.service.impl.ProductServiceImpl;
import edu.clevertec.check.util.ReaderRequestBody;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet({"/products", "/products/*"})
@Slf4j
public class ProductController extends HttpServlet {

    private final ProductService<Integer, Product> productService = new ProductServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Starting ProductController for GET request: {}", req.getRequestURI());

        String[] requestPath = req.getRequestURI().split("/");
        String productsJson = new ObjectMapper().writeValueAsString(
                req.getRequestURI().contains("/products/")
                        ? productService.findById(Integer.valueOf(requestPath[requestPath.length - 1])).get()
                        : productService.findAll()
        );

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(productsJson);
        out.flush();

        log.info("Stopping ProductController for GET request: {}", req.getRequestURI());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Starting ProductController for POST request: {}", req.getRequestURI());

        String requestBody = ReaderRequestBody.getRequestBody(req);
        log.info("Incoming JSON: {}", requestBody);

        Product product = objectMapper.readValue(requestBody, Product.class);
        productService.save(product);

        PrintWriter writer = resp.getWriter();
        String id = new Gson().toJson(product);
        writer.write(id);
        resp.setStatus(200);

        log.info("Stopping ProductController for POST request: {}", req.getRequestURI());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Starting ProductController for PUT request: {}", req.getRequestURI());

        int idProduct = Integer.parseInt(req.getParameter("idProduct"));
        String name = req.getParameter("name");
        double cost = Double.parseDouble(req.getParameter("cost"));
        boolean promotional = Boolean.parseBoolean(req.getParameter("promotional"));
        Product product = new Product(idProduct, name, cost, promotional);
        productService.update(product);

        PrintWriter writer = resp.getWriter();
        String result = new Gson().toJson(true);
        writer.write(result);

        log.info("Stopping ProductController for PUT request: {}", req.getRequestURI());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idProduct = Integer.parseInt(req.getParameter("idProduct"));
        boolean isRemove = false;

            isRemove = productService.delete(idProduct);

        try (PrintWriter writer = resp.getWriter()) {
            if (isRemove) {
                String result = new Gson().toJson(true);
                writer.write(result);
                resp.setStatus(200);
            } else {
                resp.sendError(400, "Product not found.");
            }
        }
    }
}
