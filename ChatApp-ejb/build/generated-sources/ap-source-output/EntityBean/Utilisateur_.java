package EntityBean;

import EntityBean.Conversation;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-12-18T13:54:10")
@StaticMetamodel(Utilisateur.class)
public class Utilisateur_ { 

    public static volatile SingularAttribute<Utilisateur, String> password;
    public static volatile SingularAttribute<Utilisateur, String> img;
    public static volatile CollectionAttribute<Utilisateur, Conversation> conversationCollection1;
    public static volatile CollectionAttribute<Utilisateur, Conversation> conversationCollection;
    public static volatile SingularAttribute<Utilisateur, String> fullname;
    public static volatile SingularAttribute<Utilisateur, String> userid;
    public static volatile SingularAttribute<Utilisateur, String> email;

}