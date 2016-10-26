package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.BatchJpaController;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Created by shenal on 10/16/16.
 */
public class BatchJpaControllerExt extends BatchJpaController {
    public BatchJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
    
    
    public void createBatch(Integer batchNo,String customer,String styleName) {

        EntityManager em = getEntityManager();
        try {
            
            EntityTransaction et=em.getTransaction();
            et.begin();

            String sqlString ="INSERT INTO style (name,customer) values (?styleName,?customer)";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("batchNo", batchNo);
            query.setParameter("customer", customer);
            query.setParameter("styleName", styleName);
            query.executeUpdate();
            
            String sqlString2 ="INSERT INTO batch (style_id,batch_number,current_stage_id,total_count) values ((select id from style where name=?styleName and customer=?customer),?batchNo,1,10)";
            Query query2 = em.createNativeQuery(sqlString2);
            query2.setParameter("batchNo", batchNo);
            query2.setParameter("customer", customer);
            query2.setParameter("styleName", styleName);
            query2.executeUpdate();
            et.commit();
            
        } finally {
            em.close();
        }
    }

    public Object getBatchList() {

//        SELECT id,style_id as styleId,
//        batch_number as batchNumber,
//                current_stage_id as currentStage,
//        total_count as totalCount
//        FROM `batch`

            EntityManager em = getEntityManager();
            try {

                String sqlString = "select b.id as id,b.batch_number as batchNumber,s.name as styleName,ss.id as stage,s.customer as customer"
                        + " from batch b "
                        + "inner join style s on b.style_id = s.id "
                        + "inner join stage ss on b.current_stage_id = ss.id";
//                        "        SELECT id,style_id as styleId,\n" +
//                        "        batch_number as batchNumber,\n" +
//                        "                current_stage_id as currentStage,\n" +
//                        "        total_count as totalCount\n" +
//                        "        FROM `batch`";
                Query query = em.createNativeQuery(sqlString);
                query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
                return query.getResultList();
            } finally {
                em.close();
            }

    }
    
    public Object getSizeList() {

            EntityManager em = getEntityManager();
            try {

                String sqlString = "select * "
                                 + "from size";
                Query query = em.createNativeQuery(sqlString);
                query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
                return query.getResultList();
            } finally {
                em.close();
            }

    }

    public Object getBatch(int id) {
//        SELECT id,style_id as styleId,
//        batch_number as batchNumber,
//                current_stage_id as currentStage,
//        total_count as totalCount
//        FROM `batch`
//        WHERE id =1

        EntityManager em = getEntityManager();
        try {

            String sqlString ="SELECT id,style_id as styleId,\n" +
                    "    batch_number as batchNumber,\n" +
                    "    current_stage_id as currentStage,\n" +
                    "    total_count as totalCount \n" +
                    "    FROM `batch` \n" +
                    "    WHERE id =?id";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("id", id);
            query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    
     public Object checkBatchNo(int id) {

        EntityManager em = getEntityManager();
        try {

            String sqlString ="SELECT count(b.id) as count,b.id,s.customer ,s.name as styleName "
                    + "FROM batch b "
                    + "inner join style s on b.style_id = s.id "
                    + "where b.batch_number=?id";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("id", id);
            query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
     
     
     public Object getBatchData(int id) {

        EntityManager em = getEntityManager();
        try {

            String sqlString ="select b.batch_number as batchNo, s.name as styleName, s.customer as customer from batch b inner join style s on b.style_id = s.id where b.id=?id";
            Query query = em.createNativeQuery(sqlString);
            query.setParameter("id", id);
            query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
     
}
