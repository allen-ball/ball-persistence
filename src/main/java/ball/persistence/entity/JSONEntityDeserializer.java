/*
 * $Id$
 *
 * Copyright 2017 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBean;
import ball.databind.JSONBeanDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

/**
 * {@link JSONEntity} {@link com.fasterxml.jackson.databind.JsonDeserializer}
 * implementation.  Supports polymorphism by differentiating subclass
 * implementations' bean properties.
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
public class JSONEntityDeserializer<T extends JSONEntity>
             extends JSONBeanDeserializer<T> {

    /**
     * Sole constructor.
     *
     * @param   supertype       The {@link Class} served by this
     *                          {@link com.fasterxml.jackson.databind.JsonDeserializer}.
     * @param   subtypes        The polymorphic subtype {@link Class}es
     *                          (optional).  Must be subclasses of
     *                          {@code supertype}.
     */
    public JSONEntityDeserializer(Class<? extends T> supertype,
                                  Class<?>... subtypes) {
        super(supertype, subtypes);
    }

    @Override
    protected void initialize(JSONBean bean,
                              JsonNode node,
                              String string) throws IOException {
        super.initialize(bean, node, string);
        ((JSONEntity) bean).string = string;
    }
}
