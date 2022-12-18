/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EntityBean.Conversation;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import EntityBean.Utilisateur;
import EntityBean.Message;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Rached
 */
public class ConversationJpaController implements Serializable {

    public ConversationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conversation conversation) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (conversation.getMessageCollection() == null) {
            conversation.setMessageCollection(new ArrayList<Message>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Utilisateur user2 = conversation.getUser2();
            if (user2 != null) {
                user2 = em.getReference(user2.getClass(), user2.getUserid());
                conversation.setUser2(user2);
            }
            Utilisateur user1 = conversation.getUser1();
            if (user1 != null) {
                user1 = em.getReference(user1.getClass(), user1.getUserid());
                conversation.setUser1(user1);
            }
            Collection<Message> attachedMessageCollection = new ArrayList<Message>();
            for (Message messageCollectionMessageToAttach : conversation.getMessageCollection()) {
                messageCollectionMessageToAttach = em.getReference(messageCollectionMessageToAttach.getClass(), messageCollectionMessageToAttach.getMessageid());
                attachedMessageCollection.add(messageCollectionMessageToAttach);
            }
            conversation.setMessageCollection(attachedMessageCollection);
            em.persist(conversation);
            if (user2 != null) {
                user2.getConversationCollection().add(conversation);
                user2 = em.merge(user2);
            }
            if (user1 != null) {
                user1.getConversationCollection().add(conversation);
                user1 = em.merge(user1);
            }
            for (Message messageCollectionMessage : conversation.getMessageCollection()) {
                Conversation oldConvidOfMessageCollectionMessage = messageCollectionMessage.getConvid();
                messageCollectionMessage.setConvid(conversation);
                messageCollectionMessage = em.merge(messageCollectionMessage);
                if (oldConvidOfMessageCollectionMessage != null) {
                    oldConvidOfMessageCollectionMessage.getMessageCollection().remove(messageCollectionMessage);
                    oldConvidOfMessageCollectionMessage = em.merge(oldConvidOfMessageCollectionMessage);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConversation(conversation.getConversationid()) != null) {
                throw new PreexistingEntityException("Conversation " + conversation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conversation conversation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Conversation persistentConversation = em.find(Conversation.class, conversation.getConversationid());
            Utilisateur user2Old = persistentConversation.getUser2();
            Utilisateur user2New = conversation.getUser2();
            Utilisateur user1Old = persistentConversation.getUser1();
            Utilisateur user1New = conversation.getUser1();
            Collection<Message> messageCollectionOld = persistentConversation.getMessageCollection();
            Collection<Message> messageCollectionNew = conversation.getMessageCollection();
            List<String> illegalOrphanMessages = null;
            for (Message messageCollectionOldMessage : messageCollectionOld) {
                if (!messageCollectionNew.contains(messageCollectionOldMessage)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Message " + messageCollectionOldMessage + " since its convid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (user2New != null) {
                user2New = em.getReference(user2New.getClass(), user2New.getUserid());
                conversation.setUser2(user2New);
            }
            if (user1New != null) {
                user1New = em.getReference(user1New.getClass(), user1New.getUserid());
                conversation.setUser1(user1New);
            }
            Collection<Message> attachedMessageCollectionNew = new ArrayList<Message>();
            for (Message messageCollectionNewMessageToAttach : messageCollectionNew) {
                messageCollectionNewMessageToAttach = em.getReference(messageCollectionNewMessageToAttach.getClass(), messageCollectionNewMessageToAttach.getMessageid());
                attachedMessageCollectionNew.add(messageCollectionNewMessageToAttach);
            }
            messageCollectionNew = attachedMessageCollectionNew;
            conversation.setMessageCollection(messageCollectionNew);
            conversation = em.merge(conversation);
            if (user2Old != null && !user2Old.equals(user2New)) {
                user2Old.getConversationCollection().remove(conversation);
                user2Old = em.merge(user2Old);
            }
            if (user2New != null && !user2New.equals(user2Old)) {
                user2New.getConversationCollection().add(conversation);
                user2New = em.merge(user2New);
            }
            if (user1Old != null && !user1Old.equals(user1New)) {
                user1Old.getConversationCollection().remove(conversation);
                user1Old = em.merge(user1Old);
            }
            if (user1New != null && !user1New.equals(user1Old)) {
                user1New.getConversationCollection().add(conversation);
                user1New = em.merge(user1New);
            }
            for (Message messageCollectionNewMessage : messageCollectionNew) {
                if (!messageCollectionOld.contains(messageCollectionNewMessage)) {
                    Conversation oldConvidOfMessageCollectionNewMessage = messageCollectionNewMessage.getConvid();
                    messageCollectionNewMessage.setConvid(conversation);
                    messageCollectionNewMessage = em.merge(messageCollectionNewMessage);
                    if (oldConvidOfMessageCollectionNewMessage != null && !oldConvidOfMessageCollectionNewMessage.equals(conversation)) {
                        oldConvidOfMessageCollectionNewMessage.getMessageCollection().remove(messageCollectionNewMessage);
                        oldConvidOfMessageCollectionNewMessage = em.merge(oldConvidOfMessageCollectionNewMessage);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = conversation.getConversationid();
                if (findConversation(id) == null) {
                    throw new NonexistentEntityException("The conversation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Conversation conversation;
            try {
                conversation = em.getReference(Conversation.class, id);
                conversation.getConversationid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conversation with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Message> messageCollectionOrphanCheck = conversation.getMessageCollection();
            for (Message messageCollectionOrphanCheckMessage : messageCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Conversation (" + conversation + ") cannot be destroyed since the Message " + messageCollectionOrphanCheckMessage + " in its messageCollection field has a non-nullable convid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Utilisateur user2 = conversation.getUser2();
            if (user2 != null) {
                user2.getConversationCollection().remove(conversation);
                user2 = em.merge(user2);
            }
            Utilisateur user1 = conversation.getUser1();
            if (user1 != null) {
                user1.getConversationCollection().remove(conversation);
                user1 = em.merge(user1);
            }
            em.remove(conversation);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Conversation> findConversationEntities() {
        return findConversationEntities(true, -1, -1);
    }

    public List<Conversation> findConversationEntities(int maxResults, int firstResult) {
        return findConversationEntities(false, maxResults, firstResult);
    }

    private List<Conversation> findConversationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conversation.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Conversation findConversation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conversation.class, id);
        } finally {
            em.close();
        }
    }

    public int getConversationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conversation> rt = cq.from(Conversation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
