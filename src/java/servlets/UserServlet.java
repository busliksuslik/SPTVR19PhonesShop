/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
// Алгоритм создания веб приложения в джава ее
// 
package servlets;

import entites.History;
import entites.Product;
import entites.Tag;
import entites.User;
import facades.HistoryFacade;
import facades.ProductFacade;
import facades.ProductTagFacade;
import facades.RoleFacade;
import facades.TagFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javafx.scene.input.KeyCode.T;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "UserServlet", urlPatterns = {
    "/addHistoryForm",
    "/addHistory",
    "/addMoneyForm",
    "/addMoney",
    "/cart",
    "/buy",
    
    
    
})
public class UserServlet extends HttpServlet {
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
    @EJB
    private TagFacade tagFacade;
    @EJB
    private ProductTagFacade productTagFacade;
    

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
        Boolean isRole =  userRolesFacade.isRole("CUSTOMER" , user);
        if (!isRole){
            request.setAttribute("info", "нет прав");
            request.getRequestDispatcher(LoginServlet.pathToJsp.getString("loginForm")).forward(request, response);
            return ;
        }
        request.setAttribute("isCustomer", session.getAttribute("isCustomer"));
        String path = request.getServletPath();
        
        switch (path) {
            
            case "/addHistoryForm":{
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);

                List<Tag> listTags = tagFacade.findAll();
                request.setAttribute("listTags", listTags);
                
                Map<Product,Pair<List<Tag>,Integer>> productMap = new HashMap<>();
                for(Product p : listProducts){
                    productMap.put(p,new Pair<>(productTagFacade.findTags(p),0));
                }
                request.setAttribute("productMap", productMap);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addHistoryForm")).forward(request, response);
                break;
            }
            case "/addHistory":{
                
                //NOTE: product quantity doesn't connected to the product itself. Dangerous.
                List<String> quantityStr = Arrays.asList(request.getParameterValues("quantity"));
                List<Integer> quantity = new ArrayList<>();
                
                for (String q : quantityStr){
                    quantity.add(Integer.parseInt(q));
                }
                Map<Product,Integer> cart = new HashMap<>();
                List<Product> listProducts = productFacade.findAll();
                for (int i = 0; i < listProducts.size(); i++){
                    if (quantity.get(i) == 0){continue;}
                    cart.put(listProducts.get(i), quantity.get(i));
                } 
                session.setAttribute("cart", cart);
                
                request.setAttribute("info","Добавлено в корзину");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("index")).forward(request, response);
                break;
            }
            case "/addMoneyForm":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeUserMoneyForm")).forward(request, response);
                break;
            }
            case "/addMoney":{
                String moneystr = request.getParameter("money");
                user = (User) session.getAttribute("user");
                if (user == null){
                    request.setAttribute("info","нет такого пользователя");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeUserMoneyForm")).forward(request, response);
                    break;
                }
                user.setMoney(Integer.parseInt(moneystr)+user.getMoney());
                userFacade.edit(user);
                request.setAttribute("info", "деньги добавлены");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("index")).forward(request, response);
                break;
            }
            case"/cart":{
                request.setAttribute("cart", session.getAttribute("cart"));
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("cart")).forward(request, response);
            }
            case"buy":{
                
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
