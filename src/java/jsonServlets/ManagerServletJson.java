/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets;

import entites.History;
import entites.Picture;
import entites.Product;
import entites.Tag;
import entites.User;
import facades.HistoryFacade;
import facades.PictureFacade;
import facades.ProductFacade;
import facades.TagFacade;
import facades.UserFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import jsonServlets.builders.JsonHistoryBuilder;
import jsonServlets.builders.JsonProductBuilder;
import jsonServlets.builders.JsonTagBuilder;
import jsonServlets.builders.JsonUserBuilder;

/**
 *
 * @author user
 */
@MultipartConfig
@WebServlet(name = "ProductServletJson", urlPatterns = {
    "/changeProductJson",
    "/addProductJson",
    "/users",
    "/histories",
    "/tagsJson",
    "/addTagJson"})
public class ManagerServletJson extends HttpServlet {
    @EJB ProductFacade productFacade;
    @EJB PictureFacade pictureFacade;
    @EJB UserFacade userFacade;
    @EJB HistoryFacade historyFacade;
    @EJB TagFacade tagFacade;
    public static final ResourceBundle pathToUploadDir = ResourceBundle.getBundle("property.settingUpload");

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
        String uploadFolder = ManagerServletJson.pathToUploadDir.getString("dir");
        String json = null;
        JsonObjectBuilder job = Json.createObjectBuilder();
        String path = request.getServletPath();
        switch (path) {
             case "/addProductJson":{
                List<Part> listParts = request
                        .getParts()
                        .stream()
                        .filter(part -> "file".equals(part.getName()))
                        .collect(Collectors.toList());
                Set<String> imagesExtensions = new HashSet<>();       
                imagesExtensions.add("jpg");
                imagesExtensions.add("png");
                imagesExtensions.add("gif");
                String fileFolder = "";
                Product product = null;
                Picture picture = null;
                for(Part filePart : listParts){
                    String fileName = getFileName(filePart);
                    String fileExtension = fileName.substring(fileName.length()-3, fileName.length());
                    if(imagesExtensions.contains(fileExtension)){
                        fileFolder = "images";
                    }else{
                        fileFolder = "texts";
                    }
                    StringBuilder sbFulPathToFile = new StringBuilder();
                    sbFulPathToFile.append(uploadFolder)
                            .append(File.separator)
                            .append(fileFolder)
                            .append(File.separator)
                            .append(fileName);
                    File file = new File(sbFulPathToFile.toString());
                    file.mkdirs();
                    try(InputStream fileContent = filePart.getInputStream()){
                        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    if("images".equals(fileFolder)){
                        picture = new Picture("",sbFulPathToFile.toString());
                        pictureFacade.create(picture);
                    }
                }
                if(picture == null){
                    job = Json.createObjectBuilder();
                    json=job.add("requestStatus", "false")
                        .add("info", "Выберите файл обложки и текст книги")
                        .build()
                        .toString();
                    break;
                }
                String name = request.getParameter("name");
                String count = request.getParameter("amount");
                String price = request.getParameter("price");
                if(name == null || "".equals(name)
                  || count == null || "".equals(count)
                  || price == null || "".equals(price)
                    ){
                    json=job.add("requestStatus", "false")
                        .add("info", "Заполните все поля")
                        .build()
                        .toString();
                     break; 
                }
                product = new Product(name, Integer.parseInt(count), Integer.parseInt(price));
                product.setPicture(picture);
                productFacade.create(product);
                job = Json.createObjectBuilder();
                json=job.add("requestStatus", "true")
                    .add("info", "Создана продукт: "+product.getName())
                    .add("book", new JsonProductBuilder().createProductJson(product))
                    .build()
                    .toString();
                
                break;
             }
             case "/changeProductJson":{
                JsonReader jsonReader = Json.createReader(request.getReader());
                JsonObject jsonObject = jsonReader.readObject();
                String id = jsonObject.getString("id", null);
                String name = jsonObject.getString("name", null);
                String count = jsonObject.getString("count", null);
                String price = jsonObject.getString("price", null);
                if (id == null || "".equals(id)){
                    json=job.add("requestStatus", "false")
                        .add("info", "Не выбран продукт")
                        .build()
                        .toString();
                     break; 
                }
                Product product = productFacade.find(Long.parseLong(id));
                if(!"".equals(name) && name != null){
                    product.setName(name);
                }
                if(!"".equals(count) && count != null){
                    product.setCount(Integer.parseInt(count));
                }
                if(!"".equals(price) && price != null){
                    product.setPrice(Integer.parseInt(price));
                }
                productFacade.edit(product);
                json=job.add("requestStatus", "true")
                        .add("info", "OK")
                        .build()
                        .toString();
                     break; 
             }
             case "/users":{
                JsonArrayBuilder jab = Json.createArrayBuilder();
                for (User u: userFacade.findAllExceptAdmin()){
                    jab.add(new JsonUserBuilder().createUserJson(u));
                }
                JsonArray jsonResponse = jab.build();
                json = jsonResponse.toString();
                break;
             }
             case "/histories":{
                 JsonArrayBuilder jab = Json.createArrayBuilder();
                for (History h: historyFacade.findAll()){
                    jab.add(new JsonHistoryBuilder().createProductJson(h));
                }
                JsonArray jsonResponse = jab.build();
                json = jsonResponse.toString();
                break;
             }
             case "/tagsJson":{
                 json = new JsonTagBuilder().createAllTagsJson().toString();
                 break;
             }
             case"/rolesJson":{
                json = new JsonUserBuilder().createAllRolesJson().toString();
                break;
            }
             case "/addTagJson":{
                JsonReader jsonReader = Json.createReader(request.getReader());
                JsonObject jsonObject = jsonReader.readObject();
                String name = jsonObject.getString("name", null);
                if(name == null || "".equals(name)){
                    job = Json.createObjectBuilder();
                    json=job.add("requestStatus", "false")
                    .add("info", "We failed. Abort mission")
                    .build()
                    .toString();
                    break;
                }
                Tag tag = new Tag(name);
                tagFacade.create(tag);
                job = Json.createObjectBuilder();
                json=job.add("requestStatus", "true")
                    .add("info", "Создан tag: "+tag.getName())
                    .build()
                    .toString();
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
    private String getFileName(Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")){
            if(content.trim().startsWith("filename")){
                return content
                        .substring(content.indexOf('=')+1)
                        .trim()
                        .replace("\"",""); 
            }
        }
        return null;
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
