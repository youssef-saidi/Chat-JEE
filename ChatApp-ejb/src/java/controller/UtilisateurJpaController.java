/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import EntityBean.Conversation;
import EntityBean.Utilisateur;
import EntityBean.Utilisateur_;
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
public class UtilisateurJpaController implements Serializable {

    public UtilisateurJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Utilisateur utilisateur) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (utilisateur.getConversationCollection() == null) {
            utilisateur.setConversationCollection(new ArrayList<Conversation>());
        }
        if (utilisateur.getConversationCollection1() == null) {
            utilisateur.setConversationCollection1(new ArrayList<Conversation>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Conversation> attachedConversationCollection = new ArrayList<Conversation>();
            for (Conversation conversationCollectionConversationToAttach : utilisateur.getConversationCollection()) {
                conversationCollectionConversationToAttach = em.getReference(conversationCollectionConversationToAttach.getClass(), conversationCollectionConversationToAttach.getConversationid());
                attachedConversationCollection.add(conversationCollectionConversationToAttach);
            }
            utilisateur.setConversationCollection(attachedConversationCollection);
            Collection<Conversation> attachedConversationCollection1 = new ArrayList<Conversation>();
            for (Conversation conversationCollection1ConversationToAttach : utilisateur.getConversationCollection1()) {
                conversationCollection1ConversationToAttach = em.getReference(conversationCollection1ConversationToAttach.getClass(), conversationCollection1ConversationToAttach.getConversationid());
                attachedConversationCollection1.add(conversationCollection1ConversationToAttach);
            }
            utilisateur.setConversationCollection1(attachedConversationCollection1);
            em.persist(utilisateur);
            for (Conversation conversationCollectionConversation : utilisateur.getConversationCollection()) {
                Utilisateur oldUser2OfConversationCollectionConversation = conversationCollectionConversation.getUser2();
                conversationCollectionConversation.setUser2(utilisateur);
                conversationCollectionConversation = em.merge(conversationCollectionConversation);
                if (oldUser2OfConversationCollectionConversation != null) {
                    oldUser2OfConversationCollectionConversation.getConversationCollection().remove(conversationCollectionConversation);
                    oldUser2OfConversationCollectionConversation = em.merge(oldUser2OfConversationCollectionConversation);
                }
            }
            for (Conversation conversationCollection1Conversation : utilisateur.getConversationCollection1()) {
                Utilisateur oldUser1OfConversationCollection1Conversation = conversationCollection1Conversation.getUser1();
                conversationCollection1Conversation.setUser1(utilisateur);
                conversationCollection1Conversation = em.merge(conversationCollection1Conversation);
                if (oldUser1OfConversationCollection1Conversation != null) {
                    oldUser1OfConversationCollection1Conversation.getConversationCollection1().remove(conversationCollection1Conversation);
                    oldUser1OfConversationCollection1Conversation = em.merge(oldUser1OfConversationCollection1Conversation);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUtilisateur(utilisateur.getUserid()) != null) {
                throw new PreexistingEntityException("Utilisateur " + utilisateur + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Utilisateur utilisateur) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Utilisateur persistentUtilisateur = em.find(Utilisateur.class, utilisateur.getUserid());
            Collection<Conversation> conversationCollectionOld = persistentUtilisateur.getConversationCollection();
            Collection<Conversation> conversationCollectionNew = utilisateur.getConversationCollection();
            Collection<Conversation> conversationCollection1Old = persistentUtilisateur.getConversationCollection1();
            Collection<Conversation> conversationCollection1New = utilisateur.getConversationCollection1();
            List<String> illegalOrphanMessages = null;
            for (Conversation conversationCollectionOldConversation : conversationCollectionOld) {
                if (!conversationCollectionNew.contains(conversationCollectionOldConversation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversation " + conversationCollectionOldConversation + " since its user2 field is not nullable.");
                }
            }
            for (Conversation conversationCollection1OldConversation : conversationCollection1Old) {
                if (!conversationCollection1New.contains(conversationCollection1OldConversation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversation " + conversationCollection1OldConversation + " since its user1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Conversation> attachedConversationCollectionNew = new ArrayList<Conversation>();
            for (Conversation conversationCollectionNewConversationToAttach : conversationCollectionNew) {
                conversationCollectionNewConversationToAttach = em.getReference(conversationCollectionNewConversationToAttach.getClass(), conversationCollectionNewConversationToAttach.getConversationid());
                attachedConversationCollectionNew.add(conversationCollectionNewConversationToAttach);
            }
            conversationCollectionNew = attachedConversationCollectionNew;
            utilisateur.setConversationCollection(conversationCollectionNew);
            Collection<Conversation> attachedConversationCollection1New = new ArrayList<Conversation>();
            for (Conversation conversationCollection1NewConversationToAttach : conversationCollection1New) {
                conversationCollection1NewConversationToAttach = em.getReference(conversationCollection1NewConversationToAttach.getClass(), conversationCollection1NewConversationToAttach.getConversationid());
                attachedConversationCollection1New.add(conversationCollection1NewConversationToAttach);
            }
            conversationCollection1New = attachedConversationCollection1New;
            utilisateur.setConversationCollection1(conversationCollection1New);
            utilisateur = em.merge(utilisateur);
            for (Conversation conversationCollectionNewConversation : conversationCollectionNew) {
                if (!conversationCollectionOld.contains(conversationCollectionNewConversation)) {
                    Utilisateur oldUser2OfConversationCollectionNewConversation = conversationCollectionNewConversation.getUser2();
                    conversationCollectionNewConversation.setUser2(utilisateur);
                    conversationCollectionNewConversation = em.merge(conversationCollectionNewConversation);
                    if (oldUser2OfConversationCollectionNewConversation != null && !oldUser2OfConversationCollectionNewConversation.equals(utilisateur)) {
                        oldUser2OfConversationCollectionNewConversation.getConversationCollection().remove(conversationCollectionNewConversation);
                        oldUser2OfConversationCollectionNewConversation = em.merge(oldUser2OfConversationCollectionNewConversation);
                    }
                }
            }
            for (Conversation conversationCollection1NewConversation : conversationCollection1New) {
                if (!conversationCollection1Old.contains(conversationCollection1NewConversation)) {
                    Utilisateur oldUser1OfConversationCollection1NewConversation = conversationCollection1NewConversation.getUser1();
                    conversationCollection1NewConversation.setUser1(utilisateur);
                    conversationCollection1NewConversation = em.merge(conversationCollection1NewConversation);
                    if (oldUser1OfConversationCollection1NewConversation != null && !oldUser1OfConversationCollection1NewConversation.equals(utilisateur)) {
                        oldUser1OfConversationCollection1NewConversation.getConversationCollection1().remove(conversationCollection1NewConversation);
                        oldUser1OfConversationCollection1NewConversation = em.merge(oldUser1OfConversationCollection1NewConversation);
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
                String id = utilisateur.getUserid();
                if (findUtilisateur(id) == null) {
                    throw new NonexistentEntityException("The utilisateur with id " + id + " no longer exists.");
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
            Utilisateur utilisateur;
            try {
                utilisateur = em.getReference(Utilisateur.class, id);
                utilisateur.getUserid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utilisateur with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Conversation> conversationCollectionOrphanCheck = utilisateur.getConversationCollection();
            for (Conversation conversationCollectionOrphanCheckConversation : conversationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the Conversation " + conversationCollectionOrphanCheckConversation + " in its conversationCollection field has a non-nullable user2 field.");
            }
            Collection<Conversation> conversationCollection1OrphanCheck = utilisateur.getConversationCollection1();
            for (Conversation conversationCollection1OrphanCheckConversation : conversationCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Utilisateur (" + utilisateur + ") cannot be destroyed since the Conversation " + conversationCollection1OrphanCheckConversation + " in its conversationCollection1 field has a non-nullable user1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(utilisateur);
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

    public List<Utilisateur> findUtilisateurEntities() {
        return findUtilisateurEntities(true, -1, -1);
    }

    public List<Utilisateur> findUtilisateurEntities(int maxResults, int firstResult) {
        return findUtilisateurEntities(false, maxResults, firstResult);
    }

    private List<Utilisateur> findUtilisateurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilisateur.class));
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

    public Utilisateur findUtilisateur(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilisateur.class, id);
        } finally {
            em.close();
        }
    }

    public int getUtilisateurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilisateur> rt = cq.from(Utilisateur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
   
    
}
