package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.GarmentEntryJpaController;
import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

/**
 * Created by shenal on 10/16/16.
 */
public class GarmentEntryJpaControllerExt extends GarmentEntryJpaController {
    public GarmentEntryJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
    
    public void createGarmentEntry(double bid,double sid,double stageid,double cblength,double cflength,double chestwidth,double hemwidth) {

        EntityManager em = getEntityManager();
        try {
            
            EntityTransaction et=em.getTransaction();
            et.begin();

            String sqlString ="INSERT INTO garment_entry (batch_id,size_id,stage_id,chest_width,hem_width,cb_length,cf_length) values (?bid,?sid,?stageid,?cblength,?cflength,?chestwidth,?hemwidth)";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("bid", bid);
            query.setParameter("sid", sid);
            query.setParameter("stageid", stageid);
            query.setParameter("cblength", cblength);
            query.setParameter("cflength", cflength);
            query.setParameter("chestwidth", chestwidth);
            query.setParameter("hemwidth", hemwidth);
            query.executeUpdate();
            et.commit();
            
        } finally {
            em.close();
        }
    }
    
    public Object getGarmentEntry(int bid,int sizeid,int stageid) {

        EntityManager em = getEntityManager();
        try {

            String sqlString ="SELECT id,chest_width as chestWidth,hem_width as hemWidth,cb_length as cbLength, cf_length as cfLength FROM garment_entry where "
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
    
    public Object getGarments(int batchId,int sizeid) {

        EntityManager em = getEntityManager();
        try {

            String sqlString ="SELECT * FROM garment_entry where "
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
    
    
    public void updateGarmentEntry(double id,double bid,double sid,double stageid,double cblength,double cflength,double chestwidth,double hemwidth) {

        EntityManager em = getEntityManager();
        try {
            
            EntityTransaction et=em.getTransaction();
            et.begin();

            String sqlString ="update garment_entry set chest_width=?chestwidth,hem_width=?hemwidth,cb_length=?cblength,cf_length=?cflength where id=?id ";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("id", id);
            query.setParameter("bid", bid);
            query.setParameter("sid", sid);
            query.setParameter("stageid", stageid);
            query.setParameter("cblength", cblength);
            query.setParameter("cflength", cflength);
            query.setParameter("chestwidth", chestwidth);
            query.setParameter("hemwidth", hemwidth);
            query.executeUpdate();
            et.commit();
            
        } finally {
            em.close();
        }
    }
    
    
    
    
    
}
