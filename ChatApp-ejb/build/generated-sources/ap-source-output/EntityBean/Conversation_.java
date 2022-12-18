package EntityBean;

import EntityBean.Message;
import EntityBean.Utilisateur;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-12-18T13:54:10")
@StaticMetamodel(Conversation.class)
public class Conversation_ { 

    public static volatile SingularAttribute<Conversation, Utilisateur> user1;
    public static volatile SingularAttribute<Conversation, Utilisateur> user2;
    public static volatile SingularAttribute<Conversation, String> conversationid;
    public static volatile CollectionAttribute<Conversation, Message> messageCollection;

}