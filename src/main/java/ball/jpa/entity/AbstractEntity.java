/*
 * $Id$
 *
 * Copyright 2016 Allen D. Ball.  All rights reserved.
 */
package ball.jpa.entity;

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
    protected AbstractEntity() { super(); }

    @Override
    public String toString() { return new BeanMap(this).toString(); }
}
