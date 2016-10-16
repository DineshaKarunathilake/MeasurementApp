package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.SleeveEntryJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by shenal on 10/16/16.
 */
public class SleeveEntryJpaControllerExt extends SleeveEntryJpaController {
    public SleeveEntryJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
}
