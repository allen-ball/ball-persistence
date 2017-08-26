/*
 * $Id$
 *
 * Copyright 2017 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBeanDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;

/**
 * {@link JSONEntity} {@link com.fasterxml.jackson.databind.JsonDeserializer}
 * implementation.  Supports polymorphism by differentiating subclass
 * implementations' bean properties.
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
public class JSONEntityDeserializer extends JSONBeanDeserializer {

    /**
     * Sole constructor.
     *
     * @param   supertype       The {@link Class} served by this
     *                          {@link com.fasterxml.jackson.databind.JsonDeserializer}.
     * @param   subtypes        The polymorphic subtype {@link Class}es
     *                          (optional).  Must be subclasses of
     *                          {@code supertype}.
     */
    @SafeVarargs
    @SuppressWarnings({"varargs"})
    public JSONEntityDeserializer(Class<? extends JSONEntity> supertype,
                                  Class<? extends JSONEntity>... subtypes) {
        super(supertype, subtypes);
    }

    @Override
    public JSONEntity deserialize(JsonParser parser,
                                  DeserializationContext context) throws IOException {
        JSONEntity entity = (JSONEntity) super.deserialize(parser, context);

        entity.string = OM.writeValueAsString(entity.asJsonNode());

        return entity;
    }
}
