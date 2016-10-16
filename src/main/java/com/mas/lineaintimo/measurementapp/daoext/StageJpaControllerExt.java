package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.StageJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by shenal on 10/16/16.
 */
public class StageJpaControllerExt extends StageJpaController {
    public StageJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
}
