/*
 * $Id$
 *
 * Copyright 2016, 2017 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.util.BeanMap;

/**
 * Abstract base class for entities.
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
public class AbstractEntity {

    /**
     * Sole constructor.
     */
    protected AbstractEntity() { }

    @Override
    public String toString() {
        return getClass().getSimpleName() + new BeanMap(this).toString();
    }
}
