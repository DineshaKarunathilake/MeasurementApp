/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mas.lineaintimo.measurementapp.dao;

import com.mas.lineaintimo.measurementapp.dao.exceptions.IllegalOrphanException;
import com.mas.lineaintimo.measurementapp.dao.exceptions.NonexistentEntityException;
import com.mas.lineaintimo.measurementapp.entity.Batch;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mas.lineaintimo.measurementapp.entity.Style;
import com.mas.lineaintimo.measurementapp.entity.Stage;
import com.mas.lineaintimo.measurementapp.entity.SleeveEntry;
import java.util.ArrayList;
import java.util.Collection;
import com.mas.lineaintimo.measurementapp.entity.GarmentEntry;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DineshaK
 */
public class BatchJpaController implements Serializable {

    public BatchJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Batch batch) {
        if (batch.getSleeveEntryCollection() == null) {
            batch.setSleeveEntryCollection(new ArrayList<SleeveEntry>());
        }
        if (batch.getGarmentEntryCollection() == null) {
            batch.setGarmentEntryCollection(new ArrayList<GarmentEntry>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Style styleId = batch.getStyleId();
            if (styleId != null) {
                styleId = em.getReference(styleId.getClass(), styleId.getId());
                batch.setStyleId(styleId);
            }
            Stage currentStageId = batch.getCurrentStageId();
            if (currentStageId != null) {
                currentStageId = em.getReference(currentStageId.getClass(), currentStageId.getId());
                batch.setCurrentStageId(currentStageId);
            }
            Collection<SleeveEntry> attachedSleeveEntryCollection = new ArrayList<SleeveEntry>();
            for (SleeveEntry sleeveEntryCollectionSleeveEntryToAttach : batch.getSleeveEntryCollection()) {
                sleeveEntryCollectionSleeveEntryToAttach = em.getReference(sleeveEntryCollectionSleeveEntryToAttach.getClass(), sleeveEntryCollectionSleeveEntryToAttach.getId());
                attachedSleeveEntryCollection.add(sleeveEntryCollectionSleeveEntryToAttach);
            }
            batch.setSleeveEntryCollection(attachedSleeveEntryCollection);
            Collection<GarmentEntry> attachedGarmentEntryCollection = new ArrayList<GarmentEntry>();
            for (GarmentEntry garmentEntryCollectionGarmentEntryToAttach : batch.getGarmentEntryCollection()) {
                garmentEntryCollectionGarmentEntryToAttach = em.getReference(garmentEntryCollectionGarmentEntryToAttach.getClass(), garmentEntryCollectionGarmentEntryToAttach.getId());
                attachedGarmentEntryCollection.add(garmentEntryCollectionGarmentEntryToAttach);
            }
            batch.setGarmentEntryCollection(attachedGarmentEntryCollection);
            em.persist(batch);
            if (styleId != null) {
                styleId.getBatchCollection().add(batch);
                styleId = em.merge(styleId);
            }
            if (currentStageId != null) {
                currentStageId.getBatchCollection().add(batch);
                currentStageId = em.merge(currentStageId);
            }
            for (SleeveEntry sleeveEntryCollectionSleeveEntry : batch.getSleeveEntryCollection()) {
                Batch oldBatchIdOfSleeveEntryCollectionSleeveEntry = sleeveEntryCollectionSleeveEntry.getBatchId();
                sleeveEntryCollectionSleeveEntry.setBatchId(batch);
                sleeveEntryCollectionSleeveEntry = em.merge(sleeveEntryCollectionSleeveEntry);
                if (oldBatchIdOfSleeveEntryCollectionSleeveEntry != null) {
                    oldBatchIdOfSleeveEntryCollectionSleeveEntry.getSleeveEntryCollection().remove(sleeveEntryCollectionSleeveEntry);
                    oldBatchIdOfSleeveEntryCollectionSleeveEntry = em.merge(oldBatchIdOfSleeveEntryCollectionSleeveEntry);
                }
            }
            for (GarmentEntry garmentEntryCollectionGarmentEntry : batch.getGarmentEntryCollection()) {
                Batch oldBatchIdOfGarmentEntryCollectionGarmentEntry = garmentEntryCollectionGarmentEntry.getBatchId();
                garmentEntryCollectionGarmentEntry.setBatchId(batch);
                garmentEntryCollectionGarmentEntry = em.merge(garmentEntryCollectionGarmentEntry);
                if (oldBatchIdOfGarmentEntryCollectionGarmentEntry != null) {
                    oldBatchIdOfGarmentEntryCollectionGarmentEntry.getGarmentEntryCollection().remove(garmentEntryCollectionGarmentEntry);
                    oldBatchIdOfGarmentEntryCollectionGarmentEntry = em.merge(oldBatchIdOfGarmentEntryCollectionGarmentEntry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Batch batch) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Batch persistentBatch = em.find(Batch.class, batch.getId());
            Style styleIdOld = persistentBatch.getStyleId();
            Style styleIdNew = batch.getStyleId();
            Stage currentStageIdOld = persistentBatch.getCurrentStageId();
            Stage currentStageIdNew = batch.getCurrentStageId();
            Collection<SleeveEntry> sleeveEntryCollectionOld = persistentBatch.getSleeveEntryCollection();
            Collection<SleeveEntry> sleeveEntryCollectionNew = batch.getSleeveEntryCollection();
            Collection<GarmentEntry> garmentEntryCollectionOld = persistentBatch.getGarmentEntryCollection();
            Collection<GarmentEntry> garmentEntryCollectionNew = batch.getGarmentEntryCollection();
            List<String> illegalOrphanMessages = null;
            for (SleeveEntry sleeveEntryCollectionOldSleeveEntry : sleeveEntryCollectionOld) {
                if (!sleeveEntryCollectionNew.contains(sleeveEntryCollectionOldSleeveEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SleeveEntry " + sleeveEntryCollectionOldSleeveEntry + " since its batchId field is not nullable.");
                }
            }
            for (GarmentEntry garmentEntryCollectionOldGarmentEntry : garmentEntryCollectionOld) {
                if (!garmentEntryCollectionNew.contains(garmentEntryCollectionOldGarmentEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GarmentEntry " + garmentEntryCollectionOldGarmentEntry + " since its batchId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (styleIdNew != null) {
                styleIdNew = em.getReference(styleIdNew.getClass(), styleIdNew.getId());
                batch.setStyleId(styleIdNew);
            }
            if (currentStageIdNew != null) {
                currentStageIdNew = em.getReference(currentStageIdNew.getClass(), currentStageIdNew.getId());
                batch.setCurrentStageId(currentStageIdNew);
            }
            Collection<SleeveEntry> attachedSleeveEntryCollectionNew = new ArrayList<SleeveEntry>();
            for (SleeveEntry sleeveEntryCollectionNewSleeveEntryToAttach : sleeveEntryCollectionNew) {
                sleeveEntryCollectionNewSleeveEntryToAttach = em.getReference(sleeveEntryCollectionNewSleeveEntryToAttach.getClass(), sleeveEntryCollectionNewSleeveEntryToAttach.getId());
                attachedSleeveEntryCollectionNew.add(sleeveEntryCollectionNewSleeveEntryToAttach);
            }
            sleeveEntryCollectionNew = attachedSleeveEntryCollectionNew;
            batch.setSleeveEntryCollection(sleeveEntryCollectionNew);
            Collection<GarmentEntry> attachedGarmentEntryCollectionNew = new ArrayList<GarmentEntry>();
            for (GarmentEntry garmentEntryCollectionNewGarmentEntryToAttach : garmentEntryCollectionNew) {
                garmentEntryCollectionNewGarmentEntryToAttach = em.getReference(garmentEntryCollectionNewGarmentEntryToAttach.getClass(), garmentEntryCollectionNewGarmentEntryToAttach.getId());
                attachedGarmentEntryCollectionNew.add(garmentEntryCollectionNewGarmentEntryToAttach);
            }
            garmentEntryCollectionNew = attachedGarmentEntryCollectionNew;
            batch.setGarmentEntryCollection(garmentEntryCollectionNew);
            batch = em.merge(batch);
            if (styleIdOld != null && !styleIdOld.equals(styleIdNew)) {
                styleIdOld.getBatchCollection().remove(batch);
                styleIdOld = em.merge(styleIdOld);
            }
            if (styleIdNew != null && !styleIdNew.equals(styleIdOld)) {
                styleIdNew.getBatchCollection().add(batch);
                styleIdNew = em.merge(styleIdNew);
            }
            if (currentStageIdOld != null && !currentStageIdOld.equals(currentStageIdNew)) {
                currentStageIdOld.getBatchCollection().remove(batch);
                currentStageIdOld = em.merge(currentStageIdOld);
            }
            if (currentStageIdNew != null && !currentStageIdNew.equals(currentStageIdOld)) {
                currentStageIdNew.getBatchCollection().add(batch);
                currentStageIdNew = em.merge(currentStageIdNew);
            }
            for (SleeveEntry sleeveEntryCollectionNewSleeveEntry : sleeveEntryCollectionNew) {
                if (!sleeveEntryCollectionOld.contains(sleeveEntryCollectionNewSleeveEntry)) {
                    Batch oldBatchIdOfSleeveEntryCollectionNewSleeveEntry = sleeveEntryCollectionNewSleeveEntry.getBatchId();
                    sleeveEntryCollectionNewSleeveEntry.setBatchId(batch);
                    sleeveEntryCollectionNewSleeveEntry = em.merge(sleeveEntryCollectionNewSleeveEntry);
                    if (oldBatchIdOfSleeveEntryCollectionNewSleeveEntry != null && !oldBatchIdOfSleeveEntryCollectionNewSleeveEntry.equals(batch)) {
                        oldBatchIdOfSleeveEntryCollectionNewSleeveEntry.getSleeveEntryCollection().remove(sleeveEntryCollectionNewSleeveEntry);
                        oldBatchIdOfSleeveEntryCollectionNewSleeveEntry = em.merge(oldBatchIdOfSleeveEntryCollectionNewSleeveEntry);
                    }
                }
            }
            for (GarmentEntry garmentEntryCollectionNewGarmentEntry : garmentEntryCollectionNew) {
                if (!garmentEntryCollectionOld.contains(garmentEntryCollectionNewGarmentEntry)) {
                    Batch oldBatchIdOfGarmentEntryCollectionNewGarmentEntry = garmentEntryCollectionNewGarmentEntry.getBatchId();
                    garmentEntryCollectionNewGarmentEntry.setBatchId(batch);
                    garmentEntryCollectionNewGarmentEntry = em.merge(garmentEntryCollectionNewGarmentEntry);
                    if (oldBatchIdOfGarmentEntryCollectionNewGarmentEntry != null && !oldBatchIdOfGarmentEntryCollectionNewGarmentEntry.equals(batch)) {
                        oldBatchIdOfGarmentEntryCollectionNewGarmentEntry.getGarmentEntryCollection().remove(garmentEntryCollectionNewGarmentEntry);
                        oldBatchIdOfGarmentEntryCollectionNewGarmentEntry = em.merge(oldBatchIdOfGarmentEntryCollectionNewGarmentEntry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = batch.getId();
                if (findBatch(id) == null) {
                    throw new NonexistentEntityException("The batch with id " + id + " no longer exists.");
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
            Batch batch;
            try {
                batch = em.getReference(Batch.class, id);
                batch.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The batch with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SleeveEntry> sleeveEntryCollectionOrphanCheck = batch.getSleeveEntryCollection();
            for (SleeveEntry sleeveEntryCollectionOrphanCheckSleeveEntry : sleeveEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Batch (" + batch + ") cannot be destroyed since the SleeveEntry " + sleeveEntryCollectionOrphanCheckSleeveEntry + " in its sleeveEntryCollection field has a non-nullable batchId field.");
            }
            Collection<GarmentEntry> garmentEntryCollectionOrphanCheck = batch.getGarmentEntryCollection();
            for (GarmentEntry garmentEntryCollectionOrphanCheckGarmentEntry : garmentEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Batch (" + batch + ") cannot be destroyed since the GarmentEntry " + garmentEntryCollectionOrphanCheckGarmentEntry + " in its garmentEntryCollection field has a non-nullable batchId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Style styleId = batch.getStyleId();
            if (styleId != null) {
                styleId.getBatchCollection().remove(batch);
                styleId = em.merge(styleId);
            }
            Stage currentStageId = batch.getCurrentStageId();
            if (currentStageId != null) {
                currentStageId.getBatchCollection().remove(batch);
                currentStageId = em.merge(currentStageId);
            }
            em.remove(batch);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Batch> findBatchEntities() {
        return findBatchEntities(true, -1, -1);
    }

    public List<Batch> findBatchEntities(int maxResults, int firstResult) {
        return findBatchEntities(false, maxResults, firstResult);
    }

    private List<Batch> findBatchEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Batch.class));
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

    public Batch findBatch(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Batch.class, id);
        } finally {
            em.close();
        }
    }

    public int getBatchCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Batch> rt = cq.from(Batch.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
