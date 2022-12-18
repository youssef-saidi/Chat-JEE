/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import EntityBean.Utilisateur;
import EntityBeans.UtilisateurFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rached
 */
@WebServlet(name = "utilisateurservlet", urlPatterns = {"/utilisateurservlet"})
public class utilisateurservlet extends HttpServlet {

    @EJB
    private UtilisateurFacadeLocal utilisateurFacade;

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
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        String id = request.getParameter("id");
        String nom = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(id);
        System.out.println(nom);
        System.out.println(email);
        System.out.println(password);

        try ( PrintWriter out = response.getWriter()) {
            Utilisateur c1 = new Utilisateur();
            c1.setUserid(id);
            c1.setFullname(nom);
            c1.setEmail(email);
            c1.setPassword(password);
            utilisateurFacade.create(c1);

            // List<Utilisateur> l1 = utilisateurFacade.findAll();
            JsonObjectBuilder obj = Json.createObjectBuilder().add("status", true);
            JsonObject jsonObject = obj.build();

            String jsonString;
            try ( Writer writer = new StringWriter()) {
                Json.createWriter(writer).write(jsonObject);
                jsonString = writer.toString();
            }
            System.out.print(jsonString);

            out.print(jsonString);

            //  for (Utilisateur object : l1) {
            //    out.println("<h1>" + object.getFullname() + "</h1>");
            //  out.println("<h1>" + object.getEmail() + "</h1>");
            //}
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
