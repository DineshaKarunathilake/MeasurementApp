package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.GarmentEntryJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by shenal on 10/16/16.
 */
public class GarmentEntryJpaControllerExt extends GarmentEntryJpaController {
    public GarmentEntryJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
}
