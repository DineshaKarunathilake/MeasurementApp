package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.BatchJpaController;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Created by shenal on 10/16/16.
 */
public class BatchJpaControllerExt extends BatchJpaController {
    public BatchJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }

    public Object getBatchList() {

//        SELECT id,style_id as styleId,
//        batch_number as batchNumber,
//                current_stage_id as currentStage,
//        total_count as totalCount
//        FROM `batch`

            EntityManager em = getEntityManager();
            try {

                String sqlString =
                        "        SELECT id,style_id as styleId,\n" +
                        "        batch_number as batchNumber,\n" +
                        "                current_stage_id as currentStage,\n" +
                        "        total_count as totalCount\n" +
                        "        FROM `batch`";
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
            return query.getFirstResult();
        } finally {
            em.close();
        }
    }
}
