/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBean;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rached
 */
@Entity
@Table(name = "CONVERSATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conversation.findAll", query = "SELECT c FROM Conversation c"),
    @NamedQuery(name = "Conversation.findByConversationid", query = "SELECT c FROM Conversation c WHERE c.conversationid = :conversationid")})
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CONVERSATIONID")
    private String conversationid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convid")
    private Collection<Message> messageCollection;
    @JoinColumn(name = "USER2", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Utilisateur user2;
    @JoinColumn(name = "USER1", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Utilisateur user1;

    public Conversation() {
    }

    public Conversation(String conversationid) {
        this.conversationid = conversationid;
    }

    public String getConversationid() {
        return conversationid;
    }

    public void setConversationid(String conversationid) {
        this.conversationid = conversationid;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    public Utilisateur getUser2() {
        return user2;
    }

    public void setUser2(Utilisateur user2) {
        this.user2 = user2;
    }

    public Utilisateur getUser1() {
        return user1;
    }

    public void setUser1(Utilisateur user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conversationid != null ? conversationid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversation)) {
            return false;
        }
        Conversation other = (Conversation) object;
        if ((this.conversationid == null && other.conversationid != null) || (this.conversationid != null && !this.conversationid.equals(other.conversationid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityBean.Conversation[ conversationid=" + conversationid + " ]";
    }
    
}
