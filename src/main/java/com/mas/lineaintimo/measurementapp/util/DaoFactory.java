package com.mas.lineaintimo.measurementapp.util;

import com.mas.lineaintimo.measurementapp.daoext.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by shenal on 10/16/16.
 */
public class DaoFactory {
    private final static DaoFactory instance = new DaoFactory();
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("measurement-app-pu");
    private final BatchJpaControllerExt batchDao = new BatchJpaControllerExt(emf);
    private final GarmentEntryJpaControllerExt garmentEntryDao = new GarmentEntryJpaControllerExt(emf);
    private final GarmentSizeJpaControllerExt garmentSizeDao = new GarmentSizeJpaControllerExt(emf);
    private final SleeveEntryJpaControllerExt sleeveEntryDao = new SleeveEntryJpaControllerExt(emf);
    private final StageJpaControllerExt stageDao = new StageJpaControllerExt(emf);
    private final StyleJpaControllerExt styleDao = new StyleJpaControllerExt(emf);
    private final UserJpaControllerExt userDao = new UserJpaControllerExt(emf);

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public BatchJpaControllerExt getBatchDao() {
        return batchDao;
    }

    public GarmentEntryJpaControllerExt getGarmentEntryDao() {
        return garmentEntryDao;
    }

    public GarmentSizeJpaControllerExt getGarmentSizeDao() {
        return garmentSizeDao;
    }

    public SleeveEntryJpaControllerExt getSleeveEntryDao() {
        return sleeveEntryDao;
    }

    public StageJpaControllerExt getStageDao() {
        return stageDao;
    }

    public StyleJpaControllerExt getStyleDao() {
        return styleDao;
    }

    public UserJpaControllerExt getUserDao() {
        return userDao;
    }
}
