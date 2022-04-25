//package edu.clevertec.check.servlet;
//
//import com.google.gson.Gson;
//import edu.clevertec.check.dto.Product;
//import edu.clevertec.check.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//
////import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@WebServlet(name = "AddProductController", value = "/product/add")
//public class AddProductController extends HttpServlet {
//
//    @Autowired
//    private final ProductService<Integer, Product> productService;
//
//    public AddProductController(ProductService<Integer, Product> productService) {
//        this.productService = productService;
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  throws IOException {
//        int idProduct = Integer.parseInt(req.getParameter("idProduct"));
//        String name = req.getParameter("name");
//        double cost = Double.parseDouble(req.getParameter("cost"));
//        boolean promotional = Boolean.parseBoolean(req.getParameter("promotional"));
//        Product product = new Product(idProduct, name, cost, promotional);
//        productService.save(product);
//
//        PrintWriter writer = resp.getWriter();
//        String id = new Gson().toJson(product);
//        writer.write(id);
//        resp.setStatus(200);
//    }
//
//}
//
