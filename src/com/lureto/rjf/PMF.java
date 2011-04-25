package com.lureto.rjf;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PMF {

	private final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public PersistenceManager getManager() {
        return pmfInstance.getPersistenceManager();
    }

    public PersistenceManagerFactory get() {
        return pmfInstance;
    }

}
