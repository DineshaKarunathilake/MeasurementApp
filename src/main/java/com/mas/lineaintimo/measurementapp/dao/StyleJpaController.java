/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mas.lineaintimo.measurementapp.dao;

import com.mas.lineaintimo.measurementapp.dao.exceptions.IllegalOrphanException;
import com.mas.lineaintimo.measurementapp.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mas.lineaintimo.measurementapp.entity.Batch;
import com.mas.lineaintimo.measurementapp.entity.Style;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DineshaK
 */
public class StyleJpaController implements Serializable {

    public StyleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Style style) {
        if (style.getBatchCollection() == null) {
            style.setBatchCollection(new ArrayList<Batch>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Batch> attachedBatchCollection = new ArrayList<Batch>();
            for (Batch batchCollectionBatchToAttach : style.getBatchCollection()) {
                batchCollectionBatchToAttach = em.getReference(batchCollectionBatchToAttach.getClass(), batchCollectionBatchToAttach.getId());
                attachedBatchCollection.add(batchCollectionBatchToAttach);
            }
            style.setBatchCollection(attachedBatchCollection);
            em.persist(style);
            for (Batch batchCollectionBatch : style.getBatchCollection()) {
                Style oldStyleIdOfBatchCollectionBatch = batchCollectionBatch.getStyleId();
                batchCollectionBatch.setStyleId(style);
                batchCollectionBatch = em.merge(batchCollectionBatch);
                if (oldStyleIdOfBatchCollectionBatch != null) {
                    oldStyleIdOfBatchCollectionBatch.getBatchCollection().remove(batchCollectionBatch);
                    oldStyleIdOfBatchCollectionBatch = em.merge(oldStyleIdOfBatchCollectionBatch);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Style style) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Style persistentStyle = em.find(Style.class, style.getId());
            Collection<Batch> batchCollectionOld = persistentStyle.getBatchCollection();
            Collection<Batch> batchCollectionNew = style.getBatchCollection();
            List<String> illegalOrphanMessages = null;
            for (Batch batchCollectionOldBatch : batchCollectionOld) {
                if (!batchCollectionNew.contains(batchCollectionOldBatch)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Batch " + batchCollectionOldBatch + " since its styleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Batch> attachedBatchCollectionNew = new ArrayList<Batch>();
            for (Batch batchCollectionNewBatchToAttach : batchCollectionNew) {
                batchCollectionNewBatchToAttach = em.getReference(batchCollectionNewBatchToAttach.getClass(), batchCollectionNewBatchToAttach.getId());
                attachedBatchCollectionNew.add(batchCollectionNewBatchToAttach);
            }
            batchCollectionNew = attachedBatchCollectionNew;
            style.setBatchCollection(batchCollectionNew);
            style = em.merge(style);
            for (Batch batchCollectionNewBatch : batchCollectionNew) {
                if (!batchCollectionOld.contains(batchCollectionNewBatch)) {
                    Style oldStyleIdOfBatchCollectionNewBatch = batchCollectionNewBatch.getStyleId();
                    batchCollectionNewBatch.setStyleId(style);
                    batchCollectionNewBatch = em.merge(batchCollectionNewBatch);
                    if (oldStyleIdOfBatchCollectionNewBatch != null && !oldStyleIdOfBatchCollectionNewBatch.equals(style)) {
                        oldStyleIdOfBatchCollectionNewBatch.getBatchCollection().remove(batchCollectionNewBatch);
                        oldStyleIdOfBatchCollectionNewBatch = em.merge(oldStyleIdOfBatchCollectionNewBatch);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = style.getId();
                if (findStyle(id) == null) {
                    throw new NonexistentEntityException("The style with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Style style;
            try {
                style = em.getReference(Style.class, id);
                style.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The style with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Batch> batchCollectionOrphanCheck = style.getBatchCollection();
            for (Batch batchCollectionOrphanCheckBatch : batchCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Style (" + style + ") cannot be destroyed since the Batch " + batchCollectionOrphanCheckBatch + " in its batchCollection field has a non-nullable styleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(style);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Style> findStyleEntities() {
        return findStyleEntities(true, -1, -1);
    }

    public List<Style> findStyleEntities(int maxResults, int firstResult) {
        return findStyleEntities(false, maxResults, firstResult);
    }

    private List<Style> findStyleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Style.class));
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

    public Style findStyle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Style.class, id);
        } finally {
            em.close();
        }
    }

    public int getStyleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Style> rt = cq.from(Style.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
