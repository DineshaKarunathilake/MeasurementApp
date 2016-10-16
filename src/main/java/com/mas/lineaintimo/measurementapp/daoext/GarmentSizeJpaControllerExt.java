package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.GarmentSizeJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by shenal on 10/16/16.
 */
public class GarmentSizeJpaControllerExt extends GarmentSizeJpaController {
    public GarmentSizeJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
}
