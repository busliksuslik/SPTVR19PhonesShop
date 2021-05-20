/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets;

import entites.Role;
import entites.User;
import facades.ProductFacade;
import facades.RoleFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jms.Session;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tools.EncryptPassword;
import tools.HashPassword;

/**
 *
 * @author nikita
 */
@WebServlet(name = "UserServletJson", urlPatterns = {
    "/createUserJson",
    "/mutateOne",
    "/mutateUserJson"})
public class UserServletJson extends HttpServlet {
    @EJB private ProductFacade productFacade;
    @EJB private UserFacade userFacade;
    @EJB private RoleFacade roleFacade;
    @EJB private UserRolesFacade userRolesFacade;
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
        String path = request.getServletPath();
        String json = null;
        JsonReader jsonReader ;
        JsonObjectBuilder job ;
        JsonObject jsonObject ;
        switch (path) {
            case "/createUserJson":{
                jsonReader = Json.createReader(request.getReader());
                job = Json.createObjectBuilder();
                jsonObject = jsonReader.readObject();
                String login = jsonObject.getString("login", "");
                String password = jsonObject.getString("password", "");
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
            case "/mutateOne":{
                job = Json.createObjectBuilder();
                System.out.println(job);
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute("user");
                job.add("login", user.getLogin());
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
                //user.setLogin(login);
                //userFacade.edit(user);
                break;
            }
            case "/mutateUserJson":{
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
                HttpSession session = request.getSession(false);
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
                job.add("requestStatus","false");     
                JsonObject jsonResponse = job.build();
                json = jsonResponse.toString();
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
