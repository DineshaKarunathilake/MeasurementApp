package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.StyleJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by shenal on 10/16/16.
 */
public class StyleJpaControllerExt extends StyleJpaController {
    public StyleJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
}
