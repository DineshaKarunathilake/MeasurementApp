/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mas.lineaintimo.measurementapp.dao;

import com.mas.lineaintimo.measurementapp.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mas.lineaintimo.measurementapp.entity.Stage;
import com.mas.lineaintimo.measurementapp.entity.GarmentSize;
import com.mas.lineaintimo.measurementapp.entity.Batch;
import com.mas.lineaintimo.measurementapp.entity.SleeveEntry;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DineshaK
 */
public class SleeveEntryJpaController implements Serializable {

    public SleeveEntryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SleeveEntry sleeveEntry) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stage stageId = sleeveEntry.getStageId();
            if (stageId != null) {
                stageId = em.getReference(stageId.getClass(), stageId.getId());
                sleeveEntry.setStageId(stageId);
            }
            GarmentSize sizeId = sleeveEntry.getSizeId();
            if (sizeId != null) {
                sizeId = em.getReference(sizeId.getClass(), sizeId.getId());
                sleeveEntry.setSizeId(sizeId);
            }
            Batch batchId = sleeveEntry.getBatchId();
            if (batchId != null) {
                batchId = em.getReference(batchId.getClass(), batchId.getId());
                sleeveEntry.setBatchId(batchId);
            }
            em.persist(sleeveEntry);
            if (stageId != null) {
                stageId.getSleeveEntryCollection().add(sleeveEntry);
                stageId = em.merge(stageId);
            }
            if (sizeId != null) {
                sizeId.getSleeveEntryCollection().add(sleeveEntry);
                sizeId = em.merge(sizeId);
            }
            if (batchId != null) {
                batchId.getSleeveEntryCollection().add(sleeveEntry);
                batchId = em.merge(batchId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SleeveEntry sleeveEntry) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SleeveEntry persistentSleeveEntry = em.find(SleeveEntry.class, sleeveEntry.getId());
            Stage stageIdOld = persistentSleeveEntry.getStageId();
            Stage stageIdNew = sleeveEntry.getStageId();
            GarmentSize sizeIdOld = persistentSleeveEntry.getSizeId();
            GarmentSize sizeIdNew = sleeveEntry.getSizeId();
            Batch batchIdOld = persistentSleeveEntry.getBatchId();
            Batch batchIdNew = sleeveEntry.getBatchId();
            if (stageIdNew != null) {
                stageIdNew = em.getReference(stageIdNew.getClass(), stageIdNew.getId());
                sleeveEntry.setStageId(stageIdNew);
            }
            if (sizeIdNew != null) {
                sizeIdNew = em.getReference(sizeIdNew.getClass(), sizeIdNew.getId());
                sleeveEntry.setSizeId(sizeIdNew);
            }
            if (batchIdNew != null) {
                batchIdNew = em.getReference(batchIdNew.getClass(), batchIdNew.getId());
                sleeveEntry.setBatchId(batchIdNew);
            }
            sleeveEntry = em.merge(sleeveEntry);
            if (stageIdOld != null && !stageIdOld.equals(stageIdNew)) {
                stageIdOld.getSleeveEntryCollection().remove(sleeveEntry);
                stageIdOld = em.merge(stageIdOld);
            }
            if (stageIdNew != null && !stageIdNew.equals(stageIdOld)) {
                stageIdNew.getSleeveEntryCollection().add(sleeveEntry);
                stageIdNew = em.merge(stageIdNew);
            }
            if (sizeIdOld != null && !sizeIdOld.equals(sizeIdNew)) {
                sizeIdOld.getSleeveEntryCollection().remove(sleeveEntry);
                sizeIdOld = em.merge(sizeIdOld);
            }
            if (sizeIdNew != null && !sizeIdNew.equals(sizeIdOld)) {
                sizeIdNew.getSleeveEntryCollection().add(sleeveEntry);
                sizeIdNew = em.merge(sizeIdNew);
            }
            if (batchIdOld != null && !batchIdOld.equals(batchIdNew)) {
                batchIdOld.getSleeveEntryCollection().remove(sleeveEntry);
                batchIdOld = em.merge(batchIdOld);
            }
            if (batchIdNew != null && !batchIdNew.equals(batchIdOld)) {
                batchIdNew.getSleeveEntryCollection().add(sleeveEntry);
                batchIdNew = em.merge(batchIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sleeveEntry.getId();
                if (findSleeveEntry(id) == null) {
                    throw new NonexistentEntityException("The sleeveEntry with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SleeveEntry sleeveEntry;
            try {
                sleeveEntry = em.getReference(SleeveEntry.class, id);
                sleeveEntry.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sleeveEntry with id " + id + " no longer exists.", enfe);
            }
            Stage stageId = sleeveEntry.getStageId();
            if (stageId != null) {
                stageId.getSleeveEntryCollection().remove(sleeveEntry);
                stageId = em.merge(stageId);
            }
            GarmentSize sizeId = sleeveEntry.getSizeId();
            if (sizeId != null) {
                sizeId.getSleeveEntryCollection().remove(sleeveEntry);
                sizeId = em.merge(sizeId);
            }
            Batch batchId = sleeveEntry.getBatchId();
            if (batchId != null) {
                batchId.getSleeveEntryCollection().remove(sleeveEntry);
                batchId = em.merge(batchId);
            }
            em.remove(sleeveEntry);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SleeveEntry> findSleeveEntryEntities() {
        return findSleeveEntryEntities(true, -1, -1);
    }

    public List<SleeveEntry> findSleeveEntryEntities(int maxResults, int firstResult) {
        return findSleeveEntryEntities(false, maxResults, firstResult);
    }

    private List<SleeveEntry> findSleeveEntryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SleeveEntry.class));
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

    public SleeveEntry findSleeveEntry(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SleeveEntry.class, id);
        } finally {
            em.close();
        }
    }

    public int getSleeveEntryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SleeveEntry> rt = cq.from(SleeveEntry.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
