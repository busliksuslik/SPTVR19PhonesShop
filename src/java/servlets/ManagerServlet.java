/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entites.History;
import entites.Picture;
import entites.Product;
import entites.ProductTag;
import entites.Role;
import entites.Tag;
import entites.User;
import facades.HistoryFacade;
import facades.PictureFacade;
import facades.ProductFacade;
import facades.ProductTagFacade;
import facades.RoleFacade;
import facades.TagFacade;
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
    "/managerMode",
    
    "/addProductForm",
    "/addProduct",
    "/createProduct",
    "/uploadForm",
    
    "/changeProductForm",
    "/changeProduct",
    
    
    "/addTagForm",
    "/addTag",
    
    "/changeProductTagsForm",
    "/changeProductTags",
    
    "/changeTagForm",
    "/changeTag",
    
    "/users",
    
    "/histories",
})
public class ManagerServlet extends HttpServlet {
    @EJB
    private ProductFacade productFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private UserRolesFacade userRolesFacade;
    @EJB
    private PictureFacade pictureFacade;
    @EJB
    private TagFacade tagFacade;
    @EJB
    private ProductTagFacade productTagFacade;
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
        
        request.setAttribute("isManager", session.getAttribute("isManager"));
        String path = request.getServletPath();
        
        switch (path) {
            case "/addProductForm":{
                List<Tag> listTags = tagFacade.findAll();
                request.setAttribute("listTags", listTags);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addProductForm")).forward(request, response);
                break;
            }
            case "/createProduct":{
                request.setAttribute("activeCreateProduct", "true");
                List<Picture> listPictures = pictureFacade.findAll();
                List<Tag> listTags = tagFacade.findAll();
                request.setAttribute("listTags", listTags);
                request.setAttribute("listPictures", listPictures);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addProductForm")).forward(request, response);
                break;
            }
            case "/addProduct":{
                String name = request.getParameter("name");
                String pricestr = request.getParameter("price");
                String amountstr = request.getParameter("amount");
                String pictureId = request.getParameter("pictureId");
                String tagId = request.getParameter("tagId");
                if("".equals(name) || name == null 
                        || "".equals(pricestr) || pricestr == null
                        || "".equals(amountstr) || amountstr == null
                        || "".equals(pictureId) || pictureId == null){
                    request.setAttribute("info", "INCORRECT");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addProductForm")).forward(request, response);
                    break;
                }
                Tag tag = tagFacade.findByName("other");
                if("".equals(tagId) || tagId == null){
                    tag = tagFacade.findByName("other");
                } else {
                    tag = tagFacade.find(Long.parseLong(tagId));
                }
                Picture pic = pictureFacade.find(Long.parseLong(pictureId));
                
                Long pricel = Math.round(Double.parseDouble(pricestr)*100);
                int priceint = pricel.intValue();
                
                Product product = new Product(name,Integer.parseInt(amountstr),priceint,pic);
                if (productFacade.productExist(name,pricestr)){
                    request.setAttribute("info", "Такой продукт уже существует");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addProductForm")).forward(request, response);
                    break;
                }
                productFacade.create(product);
                ProductTag pt = new ProductTag(product,tag);
                productTagFacade.create(pt);
                request.setAttribute("info", "продукт Создан");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("managerMode")).forward(request, response);
                
                break;
            }
            case "/users":{
                List<User> listUsers = userFacade.findAll();
                request.setAttribute("listUsers", listUsers);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("users")).forward(request, response);
                break;
            }
            case "/changeProductForm":{
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                
                
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeProductForm")).forward(request, response);
                break;
            }
            case "/changeProductTagsForm":{
                request.setAttribute("adminPanel", "true");
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                List<Tag> listTags = tagFacade.findAll();
                request.setAttribute("listTags", listTags);
                Map<Product,List<Tag>> productMap = new HashMap<>();
                for(Product p : listProducts){
                    productMap.put(p, productTagFacade.findTags(p));
                }
                request.setAttribute("productMap", productMap);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeProductTagsForm")).forward(request, response);
                break;
            }
            case "/changeProductTags":{
                String tagId = request.getParameter("tagId");
                String productId = request.getParameter("productId");
                String changeTag = request.getParameter("changeTag");
                if("".equals(tagId) || tagId == null
                        || "".equals(productId) || productId == null){
                    request.setAttribute("tagId", tagId);
                    request.setAttribute("productId", productId);
                    request.setAttribute("info", "Выберите все поля");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("managerMode")).forward(request, response);
                }
                Tag t = tagFacade.find(Long.parseLong(tagId));
                Product p = productFacade.find(Long.parseLong(productId));
                if("0".equals(changeTag)){
                    productTagFacade.setTagToProduct(t,p);
                }else if("1".equals(changeTag)){
                    productTagFacade.removeTagFromProduct(t,p);
                }
                request.setAttribute("info", "Тэг назначен");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("managerMode")).forward(request, response);
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
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeProductForm")).forward(request, response);
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
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("changeProductForm")).forward(request, response);
                }
                productFacade.edit(begin);
                request.setAttribute("info", "продукт изменён");
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("managerMode")).forward(request, response);
                break;
            }
            case "/managerMode":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("managerMode")).forward(request, response);
                break;
            }
            case "/uploadForm":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("uploadForm")).forward(request, response);
                break;
            }
            
            case "/addTagForm":{
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addTagForm")).forward(request, response);
                break;
            }
            case "/addTag":{
                String name = request.getParameter("name");
                if (name == null || "".equals(name)){
                    request.setAttribute("info", "введите данные");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addTagForm")).forward(request, response);
                    break;
                }
                if(tagFacade.findByName(name) != null){
                    request.setAttribute("info", "Такой тэг уже существует");
                    request.getRequestDispatcher(LoginServlet.pathToJsp.getString("addTagForm")).forward(request, response);
                    break;
                }
                Tag tag = new Tag(name);
                tagFacade.create(tag);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("managerMode")).forward(request, response);
                break;
            }
            case "/histories":{
                List<History> listHistories = historyFacade.findAll();
                request.setAttribute("listHistories", listHistories);
                request.getRequestDispatcher(LoginServlet.pathToJsp.getString("histories")).forward(request, response);
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
