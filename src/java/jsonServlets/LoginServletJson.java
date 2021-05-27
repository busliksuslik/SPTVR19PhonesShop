/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets;

import entites.Product;
import entites.Role;
import entites.User;
import facades.ProductFacade;
import facades.RoleFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
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
import jsonServlets.builders.JsonUserBuilder;
import tools.EncryptPassword;

/**
 *
 * @author user
 */
@WebServlet(name = "LoginServletJson", urlPatterns = {
    "/loginJson",
    "/logoutJson",
    "/createUserJson",
    "/listProductsJson",
    
})
public class LoginServletJson extends HttpServlet {
    @EJB UserFacade userFacade;
    @EJB UserRolesFacade userRolesFacade;
    @EJB RoleFacade roleFacade;
    @EJB ProductFacade productFacade;
    
    @Inject EncryptPassword encryptPassword;
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
            case "/loginJson":
                String login ;
                String password ;
                JsonObject jsonObject = jsonReader.readObject();
                login = jsonObject.getString("login","");
                password = jsonObject.getString("password","");
                if(login == null || "".equals(login)
                        || password == null || "".equals(password)){
                    json=job.add("requestStatus", "false")
                        .add("info", "Нет такого пользователя")
                        .build()
                        .toString();
                    break;
                }
                User regUser = userFacade.userFind(login);
                if(regUser == null){
                   json=job.add("requestStatus", "false")
                        .add("info", "Нет такого пользователя")
                        .build()
                        .toString();
                    break;
                }
                password = encryptPassword.createHash(password, regUser.getSalt());
                if(!password.equals(regUser.getPassword())){
                    json=job.add("requestStatus", "false")
                        .add("info", "Нет такого пользователя")
                        .build()
                        .toString();
                    break;
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("user", regUser);
                json=job.add("requestStatus", "true")
                        .add("info", "Вы вошли как "+regUser.getLogin())
                        .add("token", session.getId())
                        .add("role", userRolesFacade.getTopUserRole(regUser).getName())
                        .build()
                        .toString();
                
                break;
            case "/logoutJson":
                session = request.getSession(false);
                if(session != null){
                    session.invalidate();
                    json=job.add("requestStatus", "true")
                        .add("info", "Вы вышли")
                        .build()
                        .toString();
                }
                break;
            case "/createUserJson":{
                jsonReader = Json.createReader(request.getReader());
                job = Json.createObjectBuilder();
                jsonObject = jsonReader.readObject();
                login = jsonObject.getString("login", "");
                password = jsonObject.getString("password", "");
                if(login == null || "".equals(login)
                        || password == null || "".equals(password)
                        ){
                    job.add("info", "Пользователь не создан");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                String salt = encryptPassword.createSalt();
                password = encryptPassword.createHash(password, salt);
                User user= new User(login, password, salt);
                try {
                    userFacade.create(user);
                } catch (Exception e) {
                    userFacade.remove(user);
                    job.add("info", "Может такой пользователь уже есть");
                    job.add("requestStatus","false");     
                    JsonObject jsonResponse = job.build();
                    json = jsonResponse.toString();
                    break;
                }
                Role role = roleFacade.findByName("CUSTOMER");
                userRolesFacade.setRoleToUser(role, user);
                job.add("info", "Пользователь "+user.getLogin()+" создан");
                job.add("requestStatus","true");     
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
                break;
            }
            case "/listProductsJson":{
                List<Product> listProducts = productFacade.findAll();
                JsonArrayBuilder jab = Json.createArrayBuilder();
                listProducts.forEach((product)->{
                    jab.add(new JsonProductBuilder().createProductJson(product));
                });
                json = jab.build().toString();
                break;
            }
            
        }
        
        if(json == null && "".equals(json)){
            json=job.add("requestStatus", "false")
                        .add("info", "Ошибка обработки запроса")
                        .build()
                        .toString();
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
