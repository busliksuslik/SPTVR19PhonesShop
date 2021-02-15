/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entites.History;
import entites.Role;
import entites.User;
import facades.HistoryFacade;
import facades.ProductFacade;
import facades.RoleFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nikita
 */
@WebServlet(name = "AdminServlet", urlPatterns = {
    "/adminMode",
    "/changeUserPropertiesForm",
    "/setRole",
})
public class AdminServlet extends HttpServlet {
    @EJB 
    private ProductFacade productFacade;
    
    @EJB
    private UserFacade userFacade;
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private RoleFacade roleFacade;
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
        if(session == null){
            request.setAttribute("info", "У вас нет права для этого ресурса. Войдите в систему");
            request.getRequestDispatcher("/showLoginForm").forward(request, response);
            return;
        }
        User user = (User) session.getAttribute("user");
        if(user == null){
            request.setAttribute("info", "У вас нет права для этого ресурса. Войдите в систему");
            request.getRequestDispatcher("/showLoginForm").forward(request, response);
            return;
        }
        boolean isRole = userRolesFacade.isRole("ADMIN", user);
        if(!isRole){
            request.setAttribute("info", "У вас нет права для этого ресурса. Войдите в систему с соответствующими правами");
            request.getRequestDispatcher("/showLoginForm").forward(request, response);
            return;
        }
        String path = request.getServletPath();
        
        switch (path) {
            case "/adminMode":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("adminMode")).forward(request, response);
            }
            case "/changeUserPropertiesForm":
                request.setAttribute("adminPanel", "true");
                List<Role> listRoles = roleFacade.findAll();
                request.setAttribute("listRoles", listRoles);
                List<User> listUsers = userFacade.findAll();
                Map<User,List<Role>> usersMap = new HashMap<>();
                for(User u : listUsers){
                    usersMap.put(u, userRolesFacade.getRoles(u));
                }
                request.setAttribute("usersMap", usersMap);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeUserPropertiesForm")).forward(request, response);
                break;
            case "/setRole":
                String roleId = request.getParameter("roleId");
                String userId = request.getParameter("userId");
                String changeRole = request.getParameter("changeRole");
                if("".equals(roleId) || roleId == null
                        || "".equals(userId) || userId == null){
                    request.setAttribute("roleId", roleId);
                    request.setAttribute("userId", userId);
                    request.setAttribute("info", "Выберите все поля");
                    request.getRequestDispatcher("/adminMode").forward(request, response);
                }
                Role r = roleFacade.find(Long.parseLong(roleId));
                User u = userFacade.find(Long.parseLong(userId));
                if("0".equals(changeRole)){
                    userRolesFacade.setRoleToUser(r,u);
                }else if("1".equals(changeRole)){
                    userRolesFacade.removeRoleFromUser(r,u);
                }
                request.setAttribute("info", "Роль назначена");
                request.getRequestDispatcher("/adminMode").forward(request, response);
                break;
                
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
