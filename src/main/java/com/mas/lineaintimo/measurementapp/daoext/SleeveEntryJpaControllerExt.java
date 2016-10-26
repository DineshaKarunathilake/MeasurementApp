package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.SleeveEntryJpaController;
import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

/**
 * Created by shenal on 10/16/16.
 */
public class SleeveEntryJpaControllerExt extends SleeveEntryJpaController {
    public SleeveEntryJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
    
    
    public void createSleeveEntry(double bid,double sid,double stageid,double sleeveopening,double sleevewidth,double sleevelength) {

        EntityManager em = getEntityManager();
        try {
            
            EntityTransaction et=em.getTransaction();
            et.begin();

            String sqlString ="INSERT INTO sleeve_entry (batch_id,size_id,stage_id,opening,length,width) values (?bid,?sid,?stageid,?sleeveopening,?sleevelength,?sleevewidth)";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("bid", bid);
            query.setParameter("sid", sid);
            query.setParameter("stageid", stageid);
            query.setParameter("sleeveopening", sleeveopening);
            query.setParameter("sleevewidth", sleevewidth);
            query.setParameter("sleevelength", sleevelength);
            query.executeUpdate();
            et.commit();
            
        } finally {
            em.close();
        }
    }
    
    
     public Object getSleeveEntry(int bid,int sizeid,int stageid) {

        EntityManager em = getEntityManager();
        try {

            String sqlString ="SELECT id,opening as sleeveOpening,length as sleeveLength,width as sleeveWidth FROM sleeve_entry where "
                    + "batch_id = ?bid "
                    + "and size_id = ?sizeid "
                    + "and stage_id = ?stageid";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("bid", bid);
            query.setParameter("sizeid", sizeid);
            query.setParameter("stageid", stageid);
            query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
     
     
     public void updateSleeveEntry(double id,double bid,double sid,double stageid,double sleeveopening,double sleevewidth,double sleevelength) {

        EntityManager em = getEntityManager();
        try {
            
            EntityTransaction et=em.getTransaction();
            et.begin();

            String sqlString ="update sleeve_entry set opening=?sleeveopening,length=?sleevelength,width=?sleevewidth where id=?id";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("id", id);
            query.setParameter("bid", bid);
            query.setParameter("sid", sid);
            query.setParameter("stageid", stageid);
            query.setParameter("sleeveopening", sleeveopening);
            query.setParameter("sleevewidth", sleevewidth);
            query.setParameter("sleevelength", sleevelength);
            query.executeUpdate();
            et.commit();
            
        } finally {
            em.close();
        }
    }
     
     public Object getSleeves(int batchId,int sizeid) {

        EntityManager em = getEntityManager();
        try {

            String sqlString ="SELECT * FROM sleeve_entry where "
                    + "batch_id = ?batchId "
                    + "and size_id = ?sizeid "
                    + "order by stage_id";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("batchId", batchId);
            query.setParameter("sizeid", sizeid);
            query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
}
