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
import java.util.ArrayList;
import java.util.Collection;
import com.mas.lineaintimo.measurementapp.entity.SleeveEntry;
import com.mas.lineaintimo.measurementapp.entity.GarmentEntry;
import com.mas.lineaintimo.measurementapp.entity.Stage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DineshaK
 */
public class StageJpaController implements Serializable {

    public StageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Stage stage) {
        if (stage.getBatchCollection() == null) {
            stage.setBatchCollection(new ArrayList<Batch>());
        }
        if (stage.getSleeveEntryCollection() == null) {
            stage.setSleeveEntryCollection(new ArrayList<SleeveEntry>());
        }
        if (stage.getGarmentEntryCollection() == null) {
            stage.setGarmentEntryCollection(new ArrayList<GarmentEntry>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Batch> attachedBatchCollection = new ArrayList<Batch>();
            for (Batch batchCollectionBatchToAttach : stage.getBatchCollection()) {
                batchCollectionBatchToAttach = em.getReference(batchCollectionBatchToAttach.getClass(), batchCollectionBatchToAttach.getId());
                attachedBatchCollection.add(batchCollectionBatchToAttach);
            }
            stage.setBatchCollection(attachedBatchCollection);
            Collection<SleeveEntry> attachedSleeveEntryCollection = new ArrayList<SleeveEntry>();
            for (SleeveEntry sleeveEntryCollectionSleeveEntryToAttach : stage.getSleeveEntryCollection()) {
                sleeveEntryCollectionSleeveEntryToAttach = em.getReference(sleeveEntryCollectionSleeveEntryToAttach.getClass(), sleeveEntryCollectionSleeveEntryToAttach.getId());
                attachedSleeveEntryCollection.add(sleeveEntryCollectionSleeveEntryToAttach);
            }
            stage.setSleeveEntryCollection(attachedSleeveEntryCollection);
            Collection<GarmentEntry> attachedGarmentEntryCollection = new ArrayList<GarmentEntry>();
            for (GarmentEntry garmentEntryCollectionGarmentEntryToAttach : stage.getGarmentEntryCollection()) {
                garmentEntryCollectionGarmentEntryToAttach = em.getReference(garmentEntryCollectionGarmentEntryToAttach.getClass(), garmentEntryCollectionGarmentEntryToAttach.getId());
                attachedGarmentEntryCollection.add(garmentEntryCollectionGarmentEntryToAttach);
            }
            stage.setGarmentEntryCollection(attachedGarmentEntryCollection);
            em.persist(stage);
            for (Batch batchCollectionBatch : stage.getBatchCollection()) {
                Stage oldCurrentStageIdOfBatchCollectionBatch = batchCollectionBatch.getCurrentStageId();
                batchCollectionBatch.setCurrentStageId(stage);
                batchCollectionBatch = em.merge(batchCollectionBatch);
                if (oldCurrentStageIdOfBatchCollectionBatch != null) {
                    oldCurrentStageIdOfBatchCollectionBatch.getBatchCollection().remove(batchCollectionBatch);
                    oldCurrentStageIdOfBatchCollectionBatch = em.merge(oldCurrentStageIdOfBatchCollectionBatch);
                }
            }
            for (SleeveEntry sleeveEntryCollectionSleeveEntry : stage.getSleeveEntryCollection()) {
                Stage oldStageIdOfSleeveEntryCollectionSleeveEntry = sleeveEntryCollectionSleeveEntry.getStageId();
                sleeveEntryCollectionSleeveEntry.setStageId(stage);
                sleeveEntryCollectionSleeveEntry = em.merge(sleeveEntryCollectionSleeveEntry);
                if (oldStageIdOfSleeveEntryCollectionSleeveEntry != null) {
                    oldStageIdOfSleeveEntryCollectionSleeveEntry.getSleeveEntryCollection().remove(sleeveEntryCollectionSleeveEntry);
                    oldStageIdOfSleeveEntryCollectionSleeveEntry = em.merge(oldStageIdOfSleeveEntryCollectionSleeveEntry);
                }
            }
            for (GarmentEntry garmentEntryCollectionGarmentEntry : stage.getGarmentEntryCollection()) {
                Stage oldStageIdOfGarmentEntryCollectionGarmentEntry = garmentEntryCollectionGarmentEntry.getStageId();
                garmentEntryCollectionGarmentEntry.setStageId(stage);
                garmentEntryCollectionGarmentEntry = em.merge(garmentEntryCollectionGarmentEntry);
                if (oldStageIdOfGarmentEntryCollectionGarmentEntry != null) {
                    oldStageIdOfGarmentEntryCollectionGarmentEntry.getGarmentEntryCollection().remove(garmentEntryCollectionGarmentEntry);
                    oldStageIdOfGarmentEntryCollectionGarmentEntry = em.merge(oldStageIdOfGarmentEntryCollectionGarmentEntry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Stage stage) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Stage persistentStage = em.find(Stage.class, stage.getId());
            Collection<Batch> batchCollectionOld = persistentStage.getBatchCollection();
            Collection<Batch> batchCollectionNew = stage.getBatchCollection();
            Collection<SleeveEntry> sleeveEntryCollectionOld = persistentStage.getSleeveEntryCollection();
            Collection<SleeveEntry> sleeveEntryCollectionNew = stage.getSleeveEntryCollection();
            Collection<GarmentEntry> garmentEntryCollectionOld = persistentStage.getGarmentEntryCollection();
            Collection<GarmentEntry> garmentEntryCollectionNew = stage.getGarmentEntryCollection();
            List<String> illegalOrphanMessages = null;
            for (Batch batchCollectionOldBatch : batchCollectionOld) {
                if (!batchCollectionNew.contains(batchCollectionOldBatch)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Batch " + batchCollectionOldBatch + " since its currentStageId field is not nullable.");
                }
            }
            for (SleeveEntry sleeveEntryCollectionOldSleeveEntry : sleeveEntryCollectionOld) {
                if (!sleeveEntryCollectionNew.contains(sleeveEntryCollectionOldSleeveEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SleeveEntry " + sleeveEntryCollectionOldSleeveEntry + " since its stageId field is not nullable.");
                }
            }
            for (GarmentEntry garmentEntryCollectionOldGarmentEntry : garmentEntryCollectionOld) {
                if (!garmentEntryCollectionNew.contains(garmentEntryCollectionOldGarmentEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GarmentEntry " + garmentEntryCollectionOldGarmentEntry + " since its stageId field is not nullable.");
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
            stage.setBatchCollection(batchCollectionNew);
            Collection<SleeveEntry> attachedSleeveEntryCollectionNew = new ArrayList<SleeveEntry>();
            for (SleeveEntry sleeveEntryCollectionNewSleeveEntryToAttach : sleeveEntryCollectionNew) {
                sleeveEntryCollectionNewSleeveEntryToAttach = em.getReference(sleeveEntryCollectionNewSleeveEntryToAttach.getClass(), sleeveEntryCollectionNewSleeveEntryToAttach.getId());
                attachedSleeveEntryCollectionNew.add(sleeveEntryCollectionNewSleeveEntryToAttach);
            }
            sleeveEntryCollectionNew = attachedSleeveEntryCollectionNew;
            stage.setSleeveEntryCollection(sleeveEntryCollectionNew);
            Collection<GarmentEntry> attachedGarmentEntryCollectionNew = new ArrayList<GarmentEntry>();
            for (GarmentEntry garmentEntryCollectionNewGarmentEntryToAttach : garmentEntryCollectionNew) {
                garmentEntryCollectionNewGarmentEntryToAttach = em.getReference(garmentEntryCollectionNewGarmentEntryToAttach.getClass(), garmentEntryCollectionNewGarmentEntryToAttach.getId());
                attachedGarmentEntryCollectionNew.add(garmentEntryCollectionNewGarmentEntryToAttach);
            }
            garmentEntryCollectionNew = attachedGarmentEntryCollectionNew;
            stage.setGarmentEntryCollection(garmentEntryCollectionNew);
            stage = em.merge(stage);
            for (Batch batchCollectionNewBatch : batchCollectionNew) {
                if (!batchCollectionOld.contains(batchCollectionNewBatch)) {
                    Stage oldCurrentStageIdOfBatchCollectionNewBatch = batchCollectionNewBatch.getCurrentStageId();
                    batchCollectionNewBatch.setCurrentStageId(stage);
                    batchCollectionNewBatch = em.merge(batchCollectionNewBatch);
                    if (oldCurrentStageIdOfBatchCollectionNewBatch != null && !oldCurrentStageIdOfBatchCollectionNewBatch.equals(stage)) {
                        oldCurrentStageIdOfBatchCollectionNewBatch.getBatchCollection().remove(batchCollectionNewBatch);
                        oldCurrentStageIdOfBatchCollectionNewBatch = em.merge(oldCurrentStageIdOfBatchCollectionNewBatch);
                    }
                }
            }
            for (SleeveEntry sleeveEntryCollectionNewSleeveEntry : sleeveEntryCollectionNew) {
                if (!sleeveEntryCollectionOld.contains(sleeveEntryCollectionNewSleeveEntry)) {
                    Stage oldStageIdOfSleeveEntryCollectionNewSleeveEntry = sleeveEntryCollectionNewSleeveEntry.getStageId();
                    sleeveEntryCollectionNewSleeveEntry.setStageId(stage);
                    sleeveEntryCollectionNewSleeveEntry = em.merge(sleeveEntryCollectionNewSleeveEntry);
                    if (oldStageIdOfSleeveEntryCollectionNewSleeveEntry != null && !oldStageIdOfSleeveEntryCollectionNewSleeveEntry.equals(stage)) {
                        oldStageIdOfSleeveEntryCollectionNewSleeveEntry.getSleeveEntryCollection().remove(sleeveEntryCollectionNewSleeveEntry);
                        oldStageIdOfSleeveEntryCollectionNewSleeveEntry = em.merge(oldStageIdOfSleeveEntryCollectionNewSleeveEntry);
                    }
                }
            }
            for (GarmentEntry garmentEntryCollectionNewGarmentEntry : garmentEntryCollectionNew) {
                if (!garmentEntryCollectionOld.contains(garmentEntryCollectionNewGarmentEntry)) {
                    Stage oldStageIdOfGarmentEntryCollectionNewGarmentEntry = garmentEntryCollectionNewGarmentEntry.getStageId();
                    garmentEntryCollectionNewGarmentEntry.setStageId(stage);
                    garmentEntryCollectionNewGarmentEntry = em.merge(garmentEntryCollectionNewGarmentEntry);
                    if (oldStageIdOfGarmentEntryCollectionNewGarmentEntry != null && !oldStageIdOfGarmentEntryCollectionNewGarmentEntry.equals(stage)) {
                        oldStageIdOfGarmentEntryCollectionNewGarmentEntry.getGarmentEntryCollection().remove(garmentEntryCollectionNewGarmentEntry);
                        oldStageIdOfGarmentEntryCollectionNewGarmentEntry = em.merge(oldStageIdOfGarmentEntryCollectionNewGarmentEntry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stage.getId();
                if (findStage(id) == null) {
                    throw new NonexistentEntityException("The stage with id " + id + " no longer exists.");
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
            Stage stage;
            try {
                stage = em.getReference(Stage.class, id);
                stage.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stage with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Batch> batchCollectionOrphanCheck = stage.getBatchCollection();
            for (Batch batchCollectionOrphanCheckBatch : batchCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Stage (" + stage + ") cannot be destroyed since the Batch " + batchCollectionOrphanCheckBatch + " in its batchCollection field has a non-nullable currentStageId field.");
            }
            Collection<SleeveEntry> sleeveEntryCollectionOrphanCheck = stage.getSleeveEntryCollection();
            for (SleeveEntry sleeveEntryCollectionOrphanCheckSleeveEntry : sleeveEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Stage (" + stage + ") cannot be destroyed since the SleeveEntry " + sleeveEntryCollectionOrphanCheckSleeveEntry + " in its sleeveEntryCollection field has a non-nullable stageId field.");
            }
            Collection<GarmentEntry> garmentEntryCollectionOrphanCheck = stage.getGarmentEntryCollection();
            for (GarmentEntry garmentEntryCollectionOrphanCheckGarmentEntry : garmentEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Stage (" + stage + ") cannot be destroyed since the GarmentEntry " + garmentEntryCollectionOrphanCheckGarmentEntry + " in its garmentEntryCollection field has a non-nullable stageId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(stage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Stage> findStageEntities() {
        return findStageEntities(true, -1, -1);
    }

    public List<Stage> findStageEntities(int maxResults, int firstResult) {
        return findStageEntities(false, maxResults, firstResult);
    }

    private List<Stage> findStageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stage.class));
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

    public Stage findStage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stage.class, id);
        } finally {
            em.close();
        }
    }

    public int getStageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stage> rt = cq.from(Stage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
