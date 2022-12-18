/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityBean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByMessageid", query = "SELECT m FROM Message m WHERE m.messageid = :messageid"),
    @NamedQuery(name = "Message.findByText", query = "SELECT m FROM Message m WHERE m.text = :text"),
    @NamedQuery(name = "Message.findBySentat", query = "SELECT m FROM Message m WHERE m.sentat = :sentat")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "MESSAGEID")
    private String messageid;
    @Size(max = 3000)
    @Column(name = "TEXT")
    private String text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SENTAT")
    @Temporal(TemporalType.TIME)
    private Date sentat;
    @JoinColumn(name = "CONVID", referencedColumnName = "CONVERSATIONID")
    @ManyToOne(optional = false)
    private Conversation convid;

    public Message() {
    }

    public Message(String messageid) {
        this.messageid = messageid;
    }

    public Message(String messageid, Date sentat) {
        this.messageid = messageid;
        this.sentat = sentat;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSentat() {
        return sentat;
    }

    public void setSentat(Date sentat) {
        this.sentat = sentat;
    }

    public Conversation getConvid() {
        return convid;
    }

    public void setConvid(Conversation convid) {
        this.convid = convid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageid != null ? messageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageid == null && other.messageid != null) || (this.messageid != null && !this.messageid.equals(other.messageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityBean.Message[ messageid=" + messageid + " ]";
    }
    
}
