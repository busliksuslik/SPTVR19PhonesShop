/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entites.Product;
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
@WebServlet(name = "ManagerServlet", urlPatterns = {
    "/addProduct",
    "/createProduct",
    "/changeProductForm",
    "/changeUser",
    "/changeProduct",
    "/users",
    "/adminMode",
    "/changeUserProperties",
    "/setRole",
})
public class ManagerServlet extends HttpServlet {
    @EJB
    private ProductFacade productFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private HistoryFacade historyFacade;
    @EJB
    private UserRolesFacade userRolesFacade;
    @EJB
    private RoleFacade roleFacade;

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
        if (session == null){
            request.setAttribute("info", "нет прав");
            request.getRequestDispatcher(LoginServlet.pathToJsp.getString("loginForm")).forward(request, response);
            return ;
        }
        User user = (User) session.getAttribute("user");
        if (user  == null){
            request.setAttribute("info", "нет прав");
            request.getRequestDispatcher(LoginServlet.pathToJsp.getString("loginForm")).forward(request, response);
            return ;
        }
        Boolean isRole =  userRolesFacade.isRole("MANAGER" , user);
        if (!isRole){
            request.setAttribute("info", "нет прав");
            request.getRequestDispatcher(LoginServlet.pathToJsp.getString("loginForm")).forward(request, response);
            return ;
        }
        String path = request.getServletPath();
        
        switch (path) {
            case "/addProduct":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addProductForm")).forward(request, response);
                break;
            }
            case "/createProduct":{
                String name = request.getParameter("name");
                String pricestr = request.getParameter("price");
                String amountstr = request.getParameter("amount");
                if ("".equals(name)  || name == null ||
                        "".equals(pricestr)  || pricestr == null ||
                        "".equals(amountstr)  || amountstr == null){
                    request.setAttribute("info", "INCORRECT");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addProductForm")).forward(request, response);
                    break;
                }
                
                Product product = new Product(name,Integer.parseInt(amountstr),Integer.parseInt(pricestr));
                if (productFacade.productExist(name,pricestr)){
                    request.setAttribute("info", "Такой продукт уже существует");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addProductForm")).forward(request, response);
                    break;
                }
                productFacade.create(product);
                request.setAttribute("info", "продукт Создан");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("adminMode")).forward(request, response);
                break;
            }
            case "/users":{
                List<User> listUsers = userFacade.findAll();
                request.setAttribute("listUsers", listUsers);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("users")).forward(request, response);
                break;
            }
            case "/changeProduct":{
                String productstr = request.getParameter("product");
                String amountstr = request.getParameter("amount");
                String pricestr = request.getParameter("price");
                String name = request.getParameter("name");
                Product begin = productFacade.find(Long.parseLong(productstr));
                if (begin == null){
                    request.setAttribute("info", "Не выбран начальный продукт");
                    List<Product> listProducts = productFacade.findAll();
                    request.setAttribute("listProducts", listProducts);
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeProduct")).forward(request, response);
                }
                if (!("".equals(name)  || name == null)){
                    begin.setName(name);
                }
                if (!("".equals(pricestr)  || pricestr == null)){
                    begin.setPrice(Integer.parseInt(pricestr));
                }
                if (!("".equals(amountstr)  || amountstr == null)){
                    begin.setCount(Integer.parseInt(amountstr));
                }
                if (productFacade.productExist(begin.getName(),String.valueOf(begin.getPrice()))){
                    request.setAttribute("info", "Такой продукт уже существует");
                    List<Product> listProducts = productFacade.findAll();
                    request.setAttribute("listProducts", listProducts);
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeProduct")).forward(request, response);
                }
                productFacade.edit(begin);
                request.setAttribute("info", "продукт изменён");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("adminMode")).forward(request, response);
                break;
            }
            case "/adminMode":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("adminMode")).forward(request, response);
                break;
            }
            case "/changeProductForm":{
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeProduct")).forward(request, response);
                break;
            }
            case "/changeUserProperties":{
                List<Role> roles = roleFacade.findAll();
                request.setAttribute("listRoles", roles);
                List<User> users = userFacade.findAll();
                Map<User, List<Role>> usersMap = new HashMap<>();
                for(User u : users){
                    usersMap.put(u, userRolesFacade.getRoles(u));
                }
                request.setAttribute("usersMap", usersMap);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeUserProperties")).forward(request, response);
                break;
            }
            case "/setRole":{
                String roleId = request.getParameter("roleId");
                String userId = request.getParameter("userId");
                String changeRole = request.getParameter("changeRole");
                if("".equals(roleId) || roleId == null
                        || "".equals(userId) || userId == null){
                    request.setAttribute("roleId", roleId);
                    request.setAttribute("userId", userId);
                    request.setAttribute("info", "Выберите все поля");
                    request.getRequestDispatcher("/adminPanel").forward(request, response);
                }
                Role r = roleFacade.find(Long.parseLong(roleId));
                User u = userFacade.find(Long.parseLong(userId));
                if("0".equals(changeRole)){
                    userRolesFacade.setRoleToUser(r,u);
                }else if("1".equals(changeRole)){
                    userRolesFacade.removeRoleFromUser(r,u);
                }
                request.setAttribute("info", "Роль назначена");
                request.getRequestDispatcher("/adminPanel").forward(request, response);
                break;
            }
            
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
