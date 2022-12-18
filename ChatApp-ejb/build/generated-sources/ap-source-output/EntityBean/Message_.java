package EntityBean;

import EntityBean.Conversation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-12-18T13:54:10")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Conversation> convid;
    public static volatile SingularAttribute<Message, String> messageid;
    public static volatile SingularAttribute<Message, String> text;
    public static volatile SingularAttribute<Message, Date> sentat;

}