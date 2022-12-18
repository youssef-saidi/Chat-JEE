/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntityBeans;

import EntityBean.Conversation;
import EntityBean.Utilisateur;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import static java.lang.System.out;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> implements UtilisateurFacadeLocal {

    @PersistenceContext(unitName = "ChatApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    @Override
    public String verifUser(String name, String pass) {
        em = getEntityManager();
        Query req = em.createQuery("SELECT u FROM Utilisateur u WHERE u.fullname = :x and u.password = :y");
        req.setParameter("x", name);
        req.setParameter("y", pass);

        if (req.getResultList().size() > 0) {
            List<Utilisateur> list = req.getResultList();

            for (Utilisateur object : list) {
                System.out.println("<h1>" + object.getUserid() + "</h1>");
                System.out.println("<h1>" + object.getEmail() + "</h1>");
                JsonObjectBuilder obj = Json.createObjectBuilder().add("status", true).add("userId", object.getUserid());
                JsonObject jsonObject = obj.build();

                String jsonString;
                try ( Writer writer = new StringWriter()) {
                    Json.createWriter(writer).write(jsonObject);
                    jsonString = writer.toString();

                    System.out.print(jsonString);
                    return jsonString;

                } catch (IOException ex) {
                    Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return "";
    }

    @Override
    public String getFriends(String id) {
        em = getEntityManager();

        //SELECT distinct(req.userid),req.fullname,req.img,req.conversationid FROM (SELECT * FROM Utilisateur u ,Conversation v WHERE (v.user1.userid = :x or v.user2.userid = :x ) and (v.user1.userid = u.userid or v.user2.userid = u.userid) )  req,utilisateur u WHERE req.userid<>:x
        Query req = em.createQuery("SELECT u,v.conversationid FROM Utilisateur u ,Conversation v WHERE (v.user1.userid = :x or v.user2.userid = :x ) and (v.user1.userid = u.userid or v.user2.userid = u.userid) ");
        req.setParameter("x", id);

        String finalJSON = "{";

        List<Object[]> reportDetails = req.getResultList();

        for (Object[] reportDetail : reportDetails) {
            String conv = (String) reportDetail[1];
            Utilisateur user = (Utilisateur) reportDetail[0];
            System.out.print(user.getFullname());
            System.out.print(conv);
            String img = "";
            if (user.getImg() != null) {
                img = user.getImg();
            }
            JsonObjectBuilder obj = Json.createObjectBuilder().add("IdUser", user.getUserid()).add("userName", user.getFullname()).add("imgUser", img).add("room", conv);
            JsonObject jsonObject = obj.build();

            String jsonString;

            try ( Writer writer = new StringWriter()) {
                Json.createWriter(writer).write(jsonObject);
                jsonString = writer.toString();
                finalJSON += jsonString + ",";
            } catch (IOException ex) {
                Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

            // do something with entities
        }

    
        return (finalJSON + "}");
    }
    
    @Override
    public String recherche(String text) {
        em = getEntityManager();
        Query req = em.createQuery("SELECT u FROM Utilisateur u WHERE u.fullname LIKE %:x% ");
        req.setParameter("x", text);
        
        String finalJSON = "{";
        

        List<Utilisateur> searchResults = req.getResultList();
        
        for (Utilisateur user : searchResults) {
            System.out.print(user.getFullname());
            String img = "";
            if (user.getImg() != null) {
                img = user.getImg();
            }
            JsonObjectBuilder obj = Json.createObjectBuilder().add("IdUser", user.getUserid()).add("userName", user.getFullname()).add("imgUser", img);
            JsonObject jsonObject = obj.build();

            String jsonString;

            try ( Writer writer = new StringWriter()) {
                Json.createWriter(writer).write(jsonObject);
                jsonString = writer.toString();
                finalJSON += jsonString + ",";
            } catch (IOException ex) {
                Logger.getLogger(UtilisateurFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

            // do something with entities
        }
        
    return (finalJSON + "}");
    }
  

}
