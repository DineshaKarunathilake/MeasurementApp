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
import com.mas.lineaintimo.measurementapp.entity.Batch;
import com.mas.lineaintimo.measurementapp.entity.GarmentEntry;
import com.mas.lineaintimo.measurementapp.entity.Stage;
import com.mas.lineaintimo.measurementapp.entity.GarmentSize;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DineshaK
 */
public class GarmentEntryJpaController implements Serializable {

    public GarmentEntryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GarmentEntry garmentEntry) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Batch batchId = garmentEntry.getBatchId();
            if (batchId != null) {
                batchId = em.getReference(batchId.getClass(), batchId.getId());
                garmentEntry.setBatchId(batchId);
            }
            Stage stageId = garmentEntry.getStageId();
            if (stageId != null) {
                stageId = em.getReference(stageId.getClass(), stageId.getId());
                garmentEntry.setStageId(stageId);
            }
            GarmentSize sizeId = garmentEntry.getSizeId();
            if (sizeId != null) {
                sizeId = em.getReference(sizeId.getClass(), sizeId.getId());
                garmentEntry.setSizeId(sizeId);
            }
            em.persist(garmentEntry);
            if (batchId != null) {
                batchId.getGarmentEntryCollection().add(garmentEntry);
                batchId = em.merge(batchId);
            }
            if (stageId != null) {
                stageId.getGarmentEntryCollection().add(garmentEntry);
                stageId = em.merge(stageId);
            }
            if (sizeId != null) {
                sizeId.getGarmentEntryCollection().add(garmentEntry);
                sizeId = em.merge(sizeId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GarmentEntry garmentEntry) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GarmentEntry persistentGarmentEntry = em.find(GarmentEntry.class, garmentEntry.getId());
            Batch batchIdOld = persistentGarmentEntry.getBatchId();
            Batch batchIdNew = garmentEntry.getBatchId();
            Stage stageIdOld = persistentGarmentEntry.getStageId();
            Stage stageIdNew = garmentEntry.getStageId();
            GarmentSize sizeIdOld = persistentGarmentEntry.getSizeId();
            GarmentSize sizeIdNew = garmentEntry.getSizeId();
            if (batchIdNew != null) {
                batchIdNew = em.getReference(batchIdNew.getClass(), batchIdNew.getId());
                garmentEntry.setBatchId(batchIdNew);
            }
            if (stageIdNew != null) {
                stageIdNew = em.getReference(stageIdNew.getClass(), stageIdNew.getId());
                garmentEntry.setStageId(stageIdNew);
            }
            if (sizeIdNew != null) {
                sizeIdNew = em.getReference(sizeIdNew.getClass(), sizeIdNew.getId());
                garmentEntry.setSizeId(sizeIdNew);
            }
            garmentEntry = em.merge(garmentEntry);
            if (batchIdOld != null && !batchIdOld.equals(batchIdNew)) {
                batchIdOld.getGarmentEntryCollection().remove(garmentEntry);
                batchIdOld = em.merge(batchIdOld);
            }
            if (batchIdNew != null && !batchIdNew.equals(batchIdOld)) {
                batchIdNew.getGarmentEntryCollection().add(garmentEntry);
                batchIdNew = em.merge(batchIdNew);
            }
            if (stageIdOld != null && !stageIdOld.equals(stageIdNew)) {
                stageIdOld.getGarmentEntryCollection().remove(garmentEntry);
                stageIdOld = em.merge(stageIdOld);
            }
            if (stageIdNew != null && !stageIdNew.equals(stageIdOld)) {
                stageIdNew.getGarmentEntryCollection().add(garmentEntry);
                stageIdNew = em.merge(stageIdNew);
            }
            if (sizeIdOld != null && !sizeIdOld.equals(sizeIdNew)) {
                sizeIdOld.getGarmentEntryCollection().remove(garmentEntry);
                sizeIdOld = em.merge(sizeIdOld);
            }
            if (sizeIdNew != null && !sizeIdNew.equals(sizeIdOld)) {
                sizeIdNew.getGarmentEntryCollection().add(garmentEntry);
                sizeIdNew = em.merge(sizeIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = garmentEntry.getId();
                if (findGarmentEntry(id) == null) {
                    throw new NonexistentEntityException("The garmentEntry with id " + id + " no longer exists.");
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
            GarmentEntry garmentEntry;
            try {
                garmentEntry = em.getReference(GarmentEntry.class, id);
                garmentEntry.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The garmentEntry with id " + id + " no longer exists.", enfe);
            }
            Batch batchId = garmentEntry.getBatchId();
            if (batchId != null) {
                batchId.getGarmentEntryCollection().remove(garmentEntry);
                batchId = em.merge(batchId);
            }
            Stage stageId = garmentEntry.getStageId();
            if (stageId != null) {
                stageId.getGarmentEntryCollection().remove(garmentEntry);
                stageId = em.merge(stageId);
            }
            GarmentSize sizeId = garmentEntry.getSizeId();
            if (sizeId != null) {
                sizeId.getGarmentEntryCollection().remove(garmentEntry);
                sizeId = em.merge(sizeId);
            }
            em.remove(garmentEntry);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GarmentEntry> findGarmentEntryEntities() {
        return findGarmentEntryEntities(true, -1, -1);
    }

    public List<GarmentEntry> findGarmentEntryEntities(int maxResults, int firstResult) {
        return findGarmentEntryEntities(false, maxResults, firstResult);
    }

    private List<GarmentEntry> findGarmentEntryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GarmentEntry.class));
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

    public GarmentEntry findGarmentEntry(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GarmentEntry.class, id);
        } finally {
            em.close();
        }
    }

    public int getGarmentEntryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GarmentEntry> rt = cq.from(GarmentEntry.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
