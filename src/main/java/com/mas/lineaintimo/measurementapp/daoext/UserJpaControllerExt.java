package com.mas.lineaintimo.measurementapp.daoext;

import com.mas.lineaintimo.measurementapp.dao.UserJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by shenal on 10/16/16.
 */
public class UserJpaControllerExt extends UserJpaController {
    public UserJpaControllerExt(EntityManagerFactory emf) {
        super(emf);
    }
}
