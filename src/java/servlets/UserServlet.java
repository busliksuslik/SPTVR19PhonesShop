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
import entites.User;
import facades.HistoryFacade;
import facades.ProductFacade;
import facades.RoleFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.io.IOException;
import java.util.GregorianCalendar;
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
 * @author user
 */
@WebServlet(name = "UserServlet", urlPatterns = {
    "/adminMode",
    "/histories",
    "/addHistory",
    "/addMoney",
    "/createHistory",
    "/createMoney",
    
    
    
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
        Boolean isRole =  userRolesFacade.isRole("READER" , user);
        if (!isRole){
            request.setAttribute("info", "нет прав");
            request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
            return ;
        }
        String path = request.getServletPath();
        
        switch (path) {
            
            case "/addHistory":{
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                request.getRequestDispatcher("/WEB-INF/addForms/addHistoryForm.jsp").forward(request, response);
                break;
            }
            case "/createHistory":{
                String productstr = request.getParameter("product");
                String count = request.getParameter("count");
                String namestr = request.getParameter("name");
                String passstr = request.getParameter("password");
                Product product = productFacade.find(Long.parseLong(productstr));
                user = userFacade.userFind(namestr, passstr);
                if(user == null){
                    request.setAttribute("info","Непровильный пароль или имя пользователя");
                    request.getRequestDispatcher("/WEB-INF/addForms/addHistoryForm.jsp").forward(request, response);
                    break;
                }
                if (product.getCount() < Integer.parseInt(count)){
                    request.setAttribute("info","Недостаточно товара");
                    request.getRequestDispatcher("/WEB-INF/addForms/addHistoryForm.jsp").forward(request, response);
                    break;
                }
                if (user.getMoney() < product.getPrice()*Integer.parseInt(count)){
                    request.setAttribute("info","Недостаточно денег");
                    request.getRequestDispatcher("/WEB-INF/addForms/addHistoryForm.jsp").forward(request, response);
                    break;
                }
                user.setMoney(user.getMoney() - product.getPrice()*Integer.parseInt(count));
                product.setCount(product.getCount()-Integer.parseInt(count));
                History history = new History(user, product, new GregorianCalendar().getTime(),Integer.parseInt(count));
                historyFacade.create(history);
                productFacade.edit(product);
                userFacade.edit(user);
                request.setAttribute("info","Куплено");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            }
            case "/addMoney":{
                request.getRequestDispatcher("/WEB-INF/changeForms/changeUserMoney.jsp").forward(request, response);
                break;
            }
            case "/createMoney":{
                String namestr = request.getParameter("name");
                String moneystr = request.getParameter("money");
                user = userFacade.userFind(namestr);
                if (user == null){
                    request.setAttribute("info","нет такого пользователя");
                    request.getRequestDispatcher("/WEB-INF/changeForms/changeUserMoney.jsp").forward(request, response);
                    break;
                }
                user.setMoney(Integer.parseInt(moneystr)+user.getMoney());
                userFacade.edit(user);
                request.setAttribute("info", "деньги добавлены");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            }
            
            case "/histories":{
                List<History> listHistories = historyFacade.findAll();
                request.setAttribute("listHistories", listHistories);
                request.getRequestDispatcher("/WEB-INF/showers/histories.jsp").forward(request, response);
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
