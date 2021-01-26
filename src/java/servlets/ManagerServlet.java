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
import facades.RoleFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
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
@WebServlet(name = "ManagerServlet", urlPatterns = {
    "/addProduct",
    "/createProduct",
    "/changeProductForm",
    "/changeUser",
    "/changeProduct",
    "/users",
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
            request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
            return ;
        }
        User user = (User) session.getAttribute("user");
        if (user  == null){
            request.setAttribute("info", "нет прав");
            request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
            return ;
        }
        Boolean isRole =  userRolesFacade.isRole("MANAGER" , user);
        if (!isRole){
            request.setAttribute("info", "нет прав");
            request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
            return ;
        }
        String path = request.getServletPath();
        
        switch (path) {
            case "/addProduct":{
                request.getRequestDispatcher("/WEB-INF/addForms/addProductForm.jsp").forward(request, response);
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
                    request.getRequestDispatcher("/WEB-INF/addForms/addProductForm.jsp").forward(request, response);
                    break;
                }
                
                Product product = new Product(name,Integer.parseInt(amountstr),Integer.parseInt(pricestr));
                if (productFacade.productExist(name,pricestr)){
                    request.setAttribute("info", "Такой продукт уже существует");
                    request.getRequestDispatcher("/WEB-INF/addForms/addProductForm.jsp").forward(request, response);
                    break;
                }
                productFacade.create(product);
                request.setAttribute("info", "продукт Создан");
                request.getRequestDispatcher("/WEB-INF/adminMode.jsp").forward(request, response);
                break;
            }
            case "/users":{
                List<User> listUsers = userFacade.findAll();
                request.setAttribute("listUsers", listUsers);
                request.getRequestDispatcher("/WEB-INF/showers/users.jsp").forward(request, response);
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
                    request.getRequestDispatcher("/WEB-INF/changeForms/changeProduct.jsp").forward(request, response);
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
                    request.getRequestDispatcher("/WEB-INF/changeForms/changeProduct.jsp").forward(request, response);
                }
                productFacade.edit(begin);
                request.setAttribute("info", "продукт изменён");
                request.getRequestDispatcher("/WEB-INF/adminMode.jsp").forward(request, response);
                break;
            }
            case "/adminMode":{
                String name = request.getParameter("name");
                String pass = request.getParameter("password");
                if(userFacade.userExist(name, pass, "admin")){
                    request.getRequestDispatcher("/WEB-INF/adminMode.jsp").forward(request, response);
                    break;
                } else {
                    request.setAttribute("info","Нет такого администратора или неправильный пароль");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                }
            }
            case "/changeProductForm":{
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                request.getRequestDispatcher("/WEB-INF/changeForms/changeProduct.jsp").forward(request, response);
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
