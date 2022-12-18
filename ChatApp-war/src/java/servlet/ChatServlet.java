/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.stream.Collectors;
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
 * @author USER
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/ChatServlet"})
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // response.setContentType("application/json;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");

        try ( PrintWriter out = response.getWriter()) {
            JsonObjectBuilder obj = Json.createObjectBuilder().add("status", false).add("message", "Hello");
            JsonObject jsonObject = obj.build();

            String jsonString;
            try ( Writer writer = new StringWriter()) {
                Json.createWriter(writer).write(jsonObject);
                jsonString = writer.toString();
            }
            System.out.print(jsonString);

            out.print(jsonString);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // response.setContentType("application/json;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");

        // System.out.print(request.getParameter("name"));
        StringBuilder body = new StringBuilder();
        BufferedReader bufferedReader;
        InputStream inputStream = request.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        char[] bufferChar = new char[1024];
        int index = 0;
        while ((index = in.read(bufferChar)) != -1) {
            body.append(bufferChar, 0, index);
        }
        System.out.print(body.toString());
        System.out.print(request.getParameter("name"));

        // String body = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        try ( PrintWriter out = response.getWriter()) {
            JsonObjectBuilder obj = Json.createObjectBuilder().add("status", false).add("message", "d");
            JsonObject jsonObject = obj.build();

            String jsonString;
            try ( Writer writer = new StringWriter()) {
                Json.createWriter(writer).write(jsonObject);
                jsonString = writer.toString();
            }
            System.out.print(jsonString);

            out.print(jsonString);
        }
    }
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
