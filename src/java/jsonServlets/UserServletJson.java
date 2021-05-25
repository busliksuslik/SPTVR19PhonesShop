/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets;

import entites.History;
import entites.Product;
import entites.Role;
import entites.Tag;
import entites.User;
import facades.HistoryFacade;
import facades.ProductFacade;
import facades.RoleFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.jms.Session;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jsonServlets.builders.JsonPairBuilder;
import servlets.LoginServlet;
import tools.EncryptPassword;
import tools.HashPassword;

/**
 *
 * @author nikita
 */
@WebServlet(name = "UserServletJson", urlPatterns = {
    "/mutateOne",
    "/mutateUserJson",
    "/addMoney",
    "/addToCartJson",
    "/cartJson",
    "/buyProductsJson",})
public class UserServletJson extends HttpServlet {
    @EJB private ProductFacade productFacade;
    @EJB private UserFacade userFacade;
    @EJB private RoleFacade roleFacade;
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private HistoryFacade historyFacade;
     private EncryptPassword encryptPassword = new HashPassword();

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
        HttpSession session = request.getSession(false);
        String path = request.getServletPath();
        String json = null;
        
        JsonObjectBuilder job ;
        JsonObject jsonObject ;
        switch (path) {
            case "/mutateOne":{
                job = Json.createObjectBuilder();
                System.out.println(job);
                session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                job.add("login", user.getLogin());
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
                //user.setLogin(login);
                //userFacade.edit(user);
                break;
            }
            case "/mutateUserJson":{
                JsonReader jsonReader ;
                jsonReader = Json.createReader(request.getReader());
                job = Json.createObjectBuilder();
                jsonObject = jsonReader.readObject();
                String login = jsonObject.getString("login", "");
                String password = jsonObject.getString("password", "");
                if(login == null || "".equals(login)
                        || password == null || "".equals(password)
                        ){
                    job.add("info", "Пользователь не изменён");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                if (userFacade.userExist(login)){
                    job.add("info", "Already exists");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                if(!user.getPassword().equals(encryptPassword.createHash(password, user.getSalt()))){
                    job.add("info", "Invalid password");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                user.setLogin(login);
                userFacade.edit(user);
                session.setAttribute("user", user);
                job.add("info", "success - new login: " + user.getLogin());
                job.add("requestStatus","true");     
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
                break;
                
            }
            case "/addMoney":{
                JsonReader jsonReader ;
                jsonReader = Json.createReader(request.getReader());
                job = Json.createObjectBuilder();
                jsonObject = jsonReader.readObject();
                
                String moneystr = jsonObject.getString("money", "");
                User user = (User) session.getAttribute("user");
                if (user == null){
                    job.add("info","нет такого пользователя");
                    job.add("requestStatus","false");
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                Long moneyl = Math.round(Double.parseDouble(moneystr)*100);
                int moneyint = moneyl.intValue();
                user.setMoney(moneyint+user.getMoney());
                userFacade.edit(user);
                job.add("info", "деньги добавлены");
                job.add("requestStatus","true");
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
                break;
                
            }
            case "/addToCartJson":{
                job = Json.createObjectBuilder();
                JsonReader jsonReader ;
                jsonReader = Json.createReader(request.getReader());
                jsonObject = jsonReader.readObject();
                String productId = jsonObject.getString("id", null);
                String productCount = jsonObject.getString("count", "0");
                Product product = productFacade.find(Long.parseLong(productId));
                Map<Product, Integer> cart = (Map) session.getAttribute("cart");
                if(cart == null){
                    cart = new HashMap<>();
                }
                cart.put(product, Integer.parseInt(productCount));
                session.setAttribute("cart", cart);
                job.add("info", "OK");
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
                break;
            }
            case "/cartJson":{
                JsonArrayBuilder jab = Json.createArrayBuilder();
                Map<Product, Integer> cart = (Map) session.getAttribute("cart");
                if(cart == null){
                    cart = new HashMap<>();
                }
                for(Product p: cart.keySet()){
                    jab.add(new JsonPairBuilder().createPairJson(p, cart.get(p)));
                }
                json = jab.build().toString();
                break;
            }
            case "/buyProductsJson":{
                JsonReader jsonReader = Json.createReader(request.getReader());
                JsonArray jsonArray = jsonReader.readArray();
                job = Json.createObjectBuilder();
                int price = 0;
                for (int i = 0; i < jsonArray.size(); i++){
                    JsonValue jsonValue = jsonArray.get(0);
                    jsonObject = (JsonObject) jsonValue;
                    String id = jsonObject.getString("id", null);
                    Product product = productFacade.find(Long.parseLong(id));
                    String count = jsonObject.getString("count", null);
                    price+= product.getPrice()*Integer.parseInt(count);
                    
                }
          
                User user = (User) session.getAttribute("user");
                if (user.getMoney() < price){
                    job.add("info","Недостаточно денег");
                    break;
                }
                for(int i = 0; i < jsonArray.size(); i++){
                    JsonValue jsonValue = jsonArray.get(0);
                    jsonObject = (JsonObject) jsonValue;
                    String id = jsonObject.getString("id", null);
                    Product product = productFacade.find(Long.parseLong(id));
                    String count = jsonObject.getString("count", null);
                    historyFacade.create(new History(user,product, new GregorianCalendar().getTime(),Integer.parseInt(count)));
                    product.setCount(product.getCount()-Integer.parseInt(count));
                    productFacade.edit(product);
                }
                
                user.setMoney(user.getMoney() - price);
                userFacade.edit(user);
                job.add("info","Куплено");
                break;
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
