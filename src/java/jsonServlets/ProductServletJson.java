/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets;

import entites.Product;
import entites.User;
import facades.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jsonServlets.builders.JsonProductBuilder;

/**
 *
 * @author user
 */
@WebServlet(name = "ProductServletJson", urlPatterns = {
    "/listProductsJson",
    "/addProductJson"})
public class ProductServletJson extends HttpServlet {
    @EJB ProductFacade productFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String json = null;
        JsonReader jsonReader = Json.createReader(request.getReader());
        JsonObjectBuilder job = Json.createObjectBuilder();
        String path = request.getServletPath();
        switch (path) {
            case "/listProductsJson":{
                List<Product> listProducts = productFacade.findAll();
                JsonArrayBuilder jab = Json.createArrayBuilder();
                listProducts.forEach((product)->{
                    jab.add(new JsonProductBuilder().createProductJson(product));
                });
                json = jab.build().toString();
                break;
            }
            case "/addProductJson":{
                JsonObject jsonObject = jsonReader.readObject();
                String name = jsonObject.getString("name","");
                int price = Integer.parseInt(jsonObject.getString("price","-1"));
                int amount = Integer.parseInt(jsonObject.getString("amount","-1"));
                if(name == null || "".equals(name) || price == -1 || amount == -1){
                    job.add("info", "data fault");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                if (productFacade.productExist(name, price)){
                    job.add("info", "duplicates are not allowed");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                Product product = new Product(name, amount, price);
                productFacade.create(product);
                job.add("info", "Чётко");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                
            }
        }
        if(json == null || "".equals(json)){
            job = Json.createObjectBuilder();
            job.add("info", "Ошибка обработки запроса");
            job.add("requestStatus","false");     
            JsonObject jsonResponse = job.build();
            json = jsonResponse.toString();
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(json);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
