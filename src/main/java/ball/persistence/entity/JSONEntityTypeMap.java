/*
 * $Id$
 *
 * Copyright 2017 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBeanTypeMap;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

/**
 * {@link JSONEntity} {@link JSONBeanTypeMap}
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
public abstract class JSONEntityTypeMap extends JSONBeanTypeMap {
    private static final long serialVersionUID = 4058644186117804634L;

    /**
     * Sole constructor.
     */
    protected JSONEntityTypeMap() { super(); }

    @Override
    protected void initialize(Object object,
                              ObjectCodec codec,
                              JsonNode node) throws IOException {
        JSONEntity entity = (JSONEntity) object;

        super.initialize(entity, codec, node);

        entity.setJson(((ObjectMapper) codec).writeValueAsString(node));
    }
}
