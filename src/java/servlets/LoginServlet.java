/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entites.Product;
import entites.Role;
import entites.Tag;
import entites.User;
import entites.UserRoles;

import facades.ProductFacade;
import facades.ProductTagFacade;
import facades.RoleFacade;
import facades.TagFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
@WebServlet(name = "LoginServlet",loadOnStartup = 1, urlPatterns = {
    "/loginForm",
    "/login",
    
    "/logout",
    
    "/addUserForm",
    "/products",
    "/createUser",
})
public class LoginServlet extends HttpServlet {
    @EJB
    private ProductFacade productFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private UserRolesFacade userRolesFacade;
    @EJB
    private RoleFacade roleFacade;
    @EJB
    private TagFacade tagFacade;
    @EJB
    private ProductTagFacade productTagFacade;
    
    public static final ResourceBundle pathToJsp = ResourceBundle.getBundle("property.pathToJsp");

    @Override
    public void init() throws ServletException {
        super.init();
        if(tagFacade.findAll().isEmpty()) {
            Tag tag = new Tag("other");
            tagFacade.create(tag);
        }
        if(userFacade.findAll().size() > 0) return;
        
        User user = new User("admin","admin");
        userFacade.create(user);
        
        Role role = new Role("ADMIN");
        roleFacade.create(role);
        
        UserRoles userRoles = new UserRoles(user,role);
        userRolesFacade.create(userRoles);
        
        role = new Role("MANAGER");
        roleFacade.create(role);
        
        userRoles = new UserRoles(user,role);
        userRolesFacade.create(userRoles);
        
        role = new Role("CUSTOMER");
        roleFacade.create(role);
        
        userRoles = new UserRoles(user,role);
        userRolesFacade.create(userRoles);
        
        Tag tag = new Tag("other");
        tagFacade.create(tag);
    }

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
        
        switch (path) {
            case "/loginForm":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("loginForm")).forward(request, response);
                break;
            }
            case "/login":{
                String name = request.getParameter("name");
                String pass = request.getParameter("password");
                if(!userFacade.userExist(name, pass)){
                    request.setAttribute("info","Нет такого gjkmpjdfntkz");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("loginForm")).forward(request, response);
                    break;
                } 
                HttpSession session = request.getSession(true);
                session.setAttribute("user", userFacade.userFind(name, pass));
                request.setAttribute("info","success");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("index")).forward(request, response);
                break;
            }
            case "/logout":{
                HttpSession session = request.getSession(false);
                if (session != null){
                    session.invalidate();
                }
                request.setAttribute("info","вы вышли");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("index")).forward(request, response);
                break;
            }
            case "/addUserForm":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addUserForm")).forward(request, response);
                break;
            }
            case "/createUser":{
                String name = request.getParameter("name");
                String password = request.getParameter("password");
                if ("".equals(name)  || name == null ||
                        "".equals(password)  || password == null ){
                    request.setAttribute("info", "INCORRECT");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addUserForm")).forward(request, response);
                    break;
                }
                if (userFacade.userExist(name)){
                    request.setAttribute("info", "пользователь уже существует");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addUserForm")).forward(request, response);
                    break;
                }
                User user = new User(name,password);
                userFacade.create(user);
                Role role = roleFacade.findByName("CUSTOMER");
                UserRoles userRoles = new UserRoles(user,role);
                userRolesFacade.create(userRoles);
                request.setAttribute("info", "пользователь создан ");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("index")).forward(request, response);
                break;
            }
            case "/products":{
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                
                List<Tag> listTags = tagFacade.findAll();
                request.setAttribute("listTags", listTags);
                
                Map<Product,List<Tag>> productMap = new HashMap<>();
                for(Product p : listProducts){
                    productMap.put(p, productTagFacade.findTags(p));
                }
                request.setAttribute("productMap", productMap);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("products")).forward(request, response);
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
