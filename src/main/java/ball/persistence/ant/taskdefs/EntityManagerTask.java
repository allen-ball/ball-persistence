/*
 * $Id$
 *
 * Copyright 2016 - 2019 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.ant.taskdefs;

import ball.util.BeanMap;
import ball.util.MapUtil;
import ball.util.PropertiesImpl;
import ball.util.ant.taskdefs.AbstractClasspathTask;
import ball.util.ant.taskdefs.AntTask;
import ball.util.ant.taskdefs.ConfigurableAntTask;
import ball.util.ant.taskdefs.NotNull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.tools.ant.BuildException;

import static lombok.AccessLevel.PROTECTED;

/**
 * Abstract {@link.uri http://ant.apache.org/ Ant} base
 * {@link org.apache.tools.ant.Task} for subclass implementations that
 * manipulate {@link EntityManagerFactory} instances.
 *
 * {@bean.info}
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class EntityManagerTask extends AbstractClasspathTask
                                        implements ConfigurableAntTask {
    protected PropertiesImpl properties = null;
    private EntityManagerFactory factory = null;
    private EntityManager manager = null;
    @NotNull @Getter @Setter
    private String persistenceUnit = null;

    /**
     * Method to lazily create an {@link EntityManagerFactory}.
     *
     * @return  The {@link EntityManagerFactory}.
     *
     * @see #properties
     * @see Persistence#createEntityManagerFactory(String,Map)
     */
    protected EntityManagerFactory factory() {
        synchronized (this) {
            if (factory == null) {
                ClassLoader loader =
                    Thread.currentThread().getContextClassLoader();

                try {
                    Thread.currentThread()
                        .setContextClassLoader(getClassLoader());
                    factory =
                        Persistence
                        .createEntityManagerFactory(getPersistenceUnit(),
                                                    properties);
                } finally {
                    Thread.currentThread().setContextClassLoader(loader);
                }
            }
        }

        return factory;
    }

    /**
     * Method to lazily create an {@link EntityManager}.
     *
     * @return  The an {@link EntityManager}.
     *
     * @see #factory()
     */
    protected EntityManager manager() {
        synchronized (this) {
            if (manager == null) {
                manager = factory().createEntityManager();
            }
        }

        return manager;
    }

    @Override
    public void init() throws BuildException {
        super.init();

        properties = new PropertiesImpl();
        MapUtil.copy(getProject().getProperties(), properties);
    }

    /**
     * {@link.uri http://ant.apache.org/ Ant}
     * {@link org.apache.tools.ant.Task} to create an {@link EntityManager}
     * (often with the side-effect to update a persistence unit schema).
     *
     * {@bean.info}
     */
    @AntTask("em-create")
    @NoArgsConstructor @ToString
    public static class Create extends EntityManagerTask {
        @Override
        public void execute() throws BuildException {
            super.execute();

            try {
                Metamodel metamodel = manager().getMetamodel();

                for (EntityType<?> type : metamodel.getEntities()) {
                    log(String.valueOf(new BeanMap(type)));
                }
            } catch (BuildException exception) {
                throw exception;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new BuildException(throwable);
            }
        }
    }
}
