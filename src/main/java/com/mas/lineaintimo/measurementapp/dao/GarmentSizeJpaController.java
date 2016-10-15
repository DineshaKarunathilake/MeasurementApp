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
import com.mas.lineaintimo.measurementapp.entity.SleeveEntry;
import java.util.ArrayList;
import java.util.Collection;
import com.mas.lineaintimo.measurementapp.entity.GarmentEntry;
import com.mas.lineaintimo.measurementapp.entity.GarmentSize;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DineshaK
 */
public class GarmentSizeJpaController implements Serializable {

    public GarmentSizeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GarmentSize garmentSize) {
        if (garmentSize.getSleeveEntryCollection() == null) {
            garmentSize.setSleeveEntryCollection(new ArrayList<SleeveEntry>());
        }
        if (garmentSize.getGarmentEntryCollection() == null) {
            garmentSize.setGarmentEntryCollection(new ArrayList<GarmentEntry>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SleeveEntry> attachedSleeveEntryCollection = new ArrayList<SleeveEntry>();
            for (SleeveEntry sleeveEntryCollectionSleeveEntryToAttach : garmentSize.getSleeveEntryCollection()) {
                sleeveEntryCollectionSleeveEntryToAttach = em.getReference(sleeveEntryCollectionSleeveEntryToAttach.getClass(), sleeveEntryCollectionSleeveEntryToAttach.getId());
                attachedSleeveEntryCollection.add(sleeveEntryCollectionSleeveEntryToAttach);
            }
            garmentSize.setSleeveEntryCollection(attachedSleeveEntryCollection);
            Collection<GarmentEntry> attachedGarmentEntryCollection = new ArrayList<GarmentEntry>();
            for (GarmentEntry garmentEntryCollectionGarmentEntryToAttach : garmentSize.getGarmentEntryCollection()) {
                garmentEntryCollectionGarmentEntryToAttach = em.getReference(garmentEntryCollectionGarmentEntryToAttach.getClass(), garmentEntryCollectionGarmentEntryToAttach.getId());
                attachedGarmentEntryCollection.add(garmentEntryCollectionGarmentEntryToAttach);
            }
            garmentSize.setGarmentEntryCollection(attachedGarmentEntryCollection);
            em.persist(garmentSize);
            for (SleeveEntry sleeveEntryCollectionSleeveEntry : garmentSize.getSleeveEntryCollection()) {
                GarmentSize oldSizeIdOfSleeveEntryCollectionSleeveEntry = sleeveEntryCollectionSleeveEntry.getSizeId();
                sleeveEntryCollectionSleeveEntry.setSizeId(garmentSize);
                sleeveEntryCollectionSleeveEntry = em.merge(sleeveEntryCollectionSleeveEntry);
                if (oldSizeIdOfSleeveEntryCollectionSleeveEntry != null) {
                    oldSizeIdOfSleeveEntryCollectionSleeveEntry.getSleeveEntryCollection().remove(sleeveEntryCollectionSleeveEntry);
                    oldSizeIdOfSleeveEntryCollectionSleeveEntry = em.merge(oldSizeIdOfSleeveEntryCollectionSleeveEntry);
                }
            }
            for (GarmentEntry garmentEntryCollectionGarmentEntry : garmentSize.getGarmentEntryCollection()) {
                GarmentSize oldSizeIdOfGarmentEntryCollectionGarmentEntry = garmentEntryCollectionGarmentEntry.getSizeId();
                garmentEntryCollectionGarmentEntry.setSizeId(garmentSize);
                garmentEntryCollectionGarmentEntry = em.merge(garmentEntryCollectionGarmentEntry);
                if (oldSizeIdOfGarmentEntryCollectionGarmentEntry != null) {
                    oldSizeIdOfGarmentEntryCollectionGarmentEntry.getGarmentEntryCollection().remove(garmentEntryCollectionGarmentEntry);
                    oldSizeIdOfGarmentEntryCollectionGarmentEntry = em.merge(oldSizeIdOfGarmentEntryCollectionGarmentEntry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GarmentSize garmentSize) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GarmentSize persistentGarmentSize = em.find(GarmentSize.class, garmentSize.getId());
            Collection<SleeveEntry> sleeveEntryCollectionOld = persistentGarmentSize.getSleeveEntryCollection();
            Collection<SleeveEntry> sleeveEntryCollectionNew = garmentSize.getSleeveEntryCollection();
            Collection<GarmentEntry> garmentEntryCollectionOld = persistentGarmentSize.getGarmentEntryCollection();
            Collection<GarmentEntry> garmentEntryCollectionNew = garmentSize.getGarmentEntryCollection();
            List<String> illegalOrphanMessages = null;
            for (SleeveEntry sleeveEntryCollectionOldSleeveEntry : sleeveEntryCollectionOld) {
                if (!sleeveEntryCollectionNew.contains(sleeveEntryCollectionOldSleeveEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SleeveEntry " + sleeveEntryCollectionOldSleeveEntry + " since its sizeId field is not nullable.");
                }
            }
            for (GarmentEntry garmentEntryCollectionOldGarmentEntry : garmentEntryCollectionOld) {
                if (!garmentEntryCollectionNew.contains(garmentEntryCollectionOldGarmentEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GarmentEntry " + garmentEntryCollectionOldGarmentEntry + " since its sizeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SleeveEntry> attachedSleeveEntryCollectionNew = new ArrayList<SleeveEntry>();
            for (SleeveEntry sleeveEntryCollectionNewSleeveEntryToAttach : sleeveEntryCollectionNew) {
                sleeveEntryCollectionNewSleeveEntryToAttach = em.getReference(sleeveEntryCollectionNewSleeveEntryToAttach.getClass(), sleeveEntryCollectionNewSleeveEntryToAttach.getId());
                attachedSleeveEntryCollectionNew.add(sleeveEntryCollectionNewSleeveEntryToAttach);
            }
            sleeveEntryCollectionNew = attachedSleeveEntryCollectionNew;
            garmentSize.setSleeveEntryCollection(sleeveEntryCollectionNew);
            Collection<GarmentEntry> attachedGarmentEntryCollectionNew = new ArrayList<GarmentEntry>();
            for (GarmentEntry garmentEntryCollectionNewGarmentEntryToAttach : garmentEntryCollectionNew) {
                garmentEntryCollectionNewGarmentEntryToAttach = em.getReference(garmentEntryCollectionNewGarmentEntryToAttach.getClass(), garmentEntryCollectionNewGarmentEntryToAttach.getId());
                attachedGarmentEntryCollectionNew.add(garmentEntryCollectionNewGarmentEntryToAttach);
            }
            garmentEntryCollectionNew = attachedGarmentEntryCollectionNew;
            garmentSize.setGarmentEntryCollection(garmentEntryCollectionNew);
            garmentSize = em.merge(garmentSize);
            for (SleeveEntry sleeveEntryCollectionNewSleeveEntry : sleeveEntryCollectionNew) {
                if (!sleeveEntryCollectionOld.contains(sleeveEntryCollectionNewSleeveEntry)) {
                    GarmentSize oldSizeIdOfSleeveEntryCollectionNewSleeveEntry = sleeveEntryCollectionNewSleeveEntry.getSizeId();
                    sleeveEntryCollectionNewSleeveEntry.setSizeId(garmentSize);
                    sleeveEntryCollectionNewSleeveEntry = em.merge(sleeveEntryCollectionNewSleeveEntry);
                    if (oldSizeIdOfSleeveEntryCollectionNewSleeveEntry != null && !oldSizeIdOfSleeveEntryCollectionNewSleeveEntry.equals(garmentSize)) {
                        oldSizeIdOfSleeveEntryCollectionNewSleeveEntry.getSleeveEntryCollection().remove(sleeveEntryCollectionNewSleeveEntry);
                        oldSizeIdOfSleeveEntryCollectionNewSleeveEntry = em.merge(oldSizeIdOfSleeveEntryCollectionNewSleeveEntry);
                    }
                }
            }
            for (GarmentEntry garmentEntryCollectionNewGarmentEntry : garmentEntryCollectionNew) {
                if (!garmentEntryCollectionOld.contains(garmentEntryCollectionNewGarmentEntry)) {
                    GarmentSize oldSizeIdOfGarmentEntryCollectionNewGarmentEntry = garmentEntryCollectionNewGarmentEntry.getSizeId();
                    garmentEntryCollectionNewGarmentEntry.setSizeId(garmentSize);
                    garmentEntryCollectionNewGarmentEntry = em.merge(garmentEntryCollectionNewGarmentEntry);
                    if (oldSizeIdOfGarmentEntryCollectionNewGarmentEntry != null && !oldSizeIdOfGarmentEntryCollectionNewGarmentEntry.equals(garmentSize)) {
                        oldSizeIdOfGarmentEntryCollectionNewGarmentEntry.getGarmentEntryCollection().remove(garmentEntryCollectionNewGarmentEntry);
                        oldSizeIdOfGarmentEntryCollectionNewGarmentEntry = em.merge(oldSizeIdOfGarmentEntryCollectionNewGarmentEntry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = garmentSize.getId();
                if (findGarmentSize(id) == null) {
                    throw new NonexistentEntityException("The garmentSize with id " + id + " no longer exists.");
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
            GarmentSize garmentSize;
            try {
                garmentSize = em.getReference(GarmentSize.class, id);
                garmentSize.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The garmentSize with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SleeveEntry> sleeveEntryCollectionOrphanCheck = garmentSize.getSleeveEntryCollection();
            for (SleeveEntry sleeveEntryCollectionOrphanCheckSleeveEntry : sleeveEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GarmentSize (" + garmentSize + ") cannot be destroyed since the SleeveEntry " + sleeveEntryCollectionOrphanCheckSleeveEntry + " in its sleeveEntryCollection field has a non-nullable sizeId field.");
            }
            Collection<GarmentEntry> garmentEntryCollectionOrphanCheck = garmentSize.getGarmentEntryCollection();
            for (GarmentEntry garmentEntryCollectionOrphanCheckGarmentEntry : garmentEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GarmentSize (" + garmentSize + ") cannot be destroyed since the GarmentEntry " + garmentEntryCollectionOrphanCheckGarmentEntry + " in its garmentEntryCollection field has a non-nullable sizeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(garmentSize);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GarmentSize> findGarmentSizeEntities() {
        return findGarmentSizeEntities(true, -1, -1);
    }

    public List<GarmentSize> findGarmentSizeEntities(int maxResults, int firstResult) {
        return findGarmentSizeEntities(false, maxResults, firstResult);
    }

    private List<GarmentSize> findGarmentSizeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GarmentSize.class));
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

    public GarmentSize findGarmentSize(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GarmentSize.class, id);
        } finally {
            em.close();
        }
    }

    public int getGarmentSizeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GarmentSize> rt = cq.from(GarmentSize.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
