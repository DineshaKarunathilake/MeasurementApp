package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.BatchJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by shenal on 10/16/16.
 */
public class BatchJpaControllerExt extends BatchJpaController {
    public BatchJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }

}
