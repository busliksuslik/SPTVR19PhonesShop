/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entites.Product;
import entites.User;
import facades.HistoryFacade;
import facades.ProductFacade;
import facades.UserFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "LoginServlet", urlPatterns = {
    "/loginForm",
    "/login",
    "/logout",
    "/addUser",
    "/products",
    "/createUser",
})
public class LoginServlet extends HttpServlet {
    @EJB
    private ProductFacade productFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private HistoryFacade historyFacade;

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
                request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
                break;
            }
            case "/login":{
                String name = request.getParameter("name");
                String pass = request.getParameter("password");
                if(!userFacade.userExist(name, pass)){
                    request.setAttribute("info","Нет такого gjkmpjdfntkz");
                    request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
                    break;
                } 
                HttpSession session = request.getSession(true);
                session.setAttribute("user", userFacade.userFind(name, pass));
                request.setAttribute("info","success");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            }
            case "/logout":{
                HttpSession session = request.getSession(false);
                if (session != null){
                    session.invalidate();
                }
                request.setAttribute("info","вы вышли");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            }
            case "/addUser":{
                request.getRequestDispatcher("/WEB-INF/addForms/addUserForm.jsp").forward(request, response);
                break;
            }
            case "/createUser":{
                String name = request.getParameter("name");
                String password = request.getParameter("password");
                if ("".equals(name)  || name == null ||
                        "".equals(password)  || password == null ){
                    request.setAttribute("info", "INCORRECT");
                    request.getRequestDispatcher("/WEB-INF/addForms/addUserForm.jsp").forward(request, response);
                    break;
                }
                if (userFacade.userExist(name)){
                    request.setAttribute("info", "пользователь уже существует");
                    request.getRequestDispatcher("/WEB-INF/addForms/addUserForm.jsp").forward(request, response);
                    break;
                }
                User user = new User(name,password);
                userFacade.create(user);
                request.setAttribute("info", "пользователь создан ");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            }
            case "/products":{
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                request.getRequestDispatcher("/WEB-INF/showers/products.jsp").forward(request, response);
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
