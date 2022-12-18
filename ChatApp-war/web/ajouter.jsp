<%-- 
    Document   : ajouter
    Created on : Dec 13, 2022, 7:20:29 PM
    Author     : Rached
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="utilisateurservlet" >
            <table>
                <tr>
                    <td>id : </td>
                    <td><input type="text" name="id" value="" /></td>
                </tr>
                <tr>
                    <td>Name : </td>
                    <td><input type="text" name="name" value="" /></td>
                </tr>
                <tr>
                    <td>email : </td>
                    <td><input type="text" name="email" value="" /></td>
                </tr>
                <tr>
                <tr>
                    <td>password : </td>
                    <td><input type="text" name="password" value="" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Ajouter" name="btn" /></td>
                </tr>
            </table>
        </form>
    </body>
</html>
