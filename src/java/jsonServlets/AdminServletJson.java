/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets;

import entites.Role;
import entites.User;
import facades.RoleFacade;
import facades.TagFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.ejb.EJB;
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
import jsonServlets.builders.JsonRolesBuilder;
import jsonServlets.builders.JsonUserBuilder;
import servlets.LoginServlet;
import tools.AnswerGenerator;

/**
 *
 * @author user
 */
@WebServlet(name = "AdminServletJson", urlPatterns = {
    "/changeUserRoleFormJson",
    "/changeUserRoleJson"})
public class AdminServletJson extends HttpServlet {
    @EJB UserFacade userFacade;
    @EJB RoleFacade roleFacade;
    @EJB TagFacade tagFacade;
    @EJB UserRolesFacade userRolesFacade;
    private AnswerGenerator ansGen = new AnswerGenerator();

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
        HttpSession session = request.getSession(false);
        if(session == null){
            json = ansGen.generateAnswer("нет прав", "false");
            try (PrintWriter out = response.getWriter()) {
                out.println(json);}
            return;
        }
        User user = (User) session.getAttribute("user");
        if(user == null){
            json = ansGen.generateAnswer("нет прав", "false");
            try (PrintWriter out = response.getWriter()) {
                out.println(json);}
            return;
        }
        boolean isRole = userRolesFacade.isRole("ADMIN", user);
        if(!isRole){
            json = ansGen.generateAnswer("нет прав", "false");
            try (PrintWriter out = response.getWriter()) {
                out.println(json);}
            return;
        }
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        String path = request.getServletPath();

        switch (path) {
            case "/changeUserRoleFormJson":{
                job.add("roles", new JsonRolesBuilder().createRoles())
                        .add("users", new JsonUserBuilder().createAllUsersJson());
                json = job.build().toString();
                break;
            }
            case "/changeUserRoleJson":{              
                JsonReader jsonReader = Json.createReader(request.getReader());
                JsonObject obj = jsonReader.readObject();
                String userId = obj.getString("user", null);
                String roleId = obj.getString("role", null);
                user = userFacade.find(Long.parseLong(userId));
                Role role = roleFacade.find(Long.parseLong(roleId));
                userRolesFacade.deleteAllRoles(user);
                for(int i = 3; i >= role.getId(); i--){
                    userRolesFacade.setRoleToUser(roleFacade.find(new Long(i)), user);
                }
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
