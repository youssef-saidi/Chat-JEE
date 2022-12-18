/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package EntityBeans;

import EntityBean.Conversation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ConversationFacadeLocal {

    void create(Conversation conversation);

    void edit(Conversation conversation);

    void remove(Conversation conversation);

    Conversation find(Object id);

    List<Conversation> findAll();

    List<Conversation> findRange(int[] range);

    int count();
    
}
