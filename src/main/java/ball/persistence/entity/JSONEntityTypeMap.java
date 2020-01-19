/*
 * $Id$
 *
 * Copyright 2017 - 2020 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBeanTypeMap;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * {@link JSONEntity} {@link JSONBeanTypeMap}.
 *
 * @author {@link.uri mailto:ball@hcf.dev Allen D. Ball}
 * @version $Revision$
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class JSONEntityTypeMap extends JSONBeanTypeMap {
    private static final long serialVersionUID = 4058644186117804634L;

    @Override
    protected void initialize(Object object,
                              ObjectCodec codec,
                              JsonNode node) throws IOException {
        super.initialize(object, codec, node);

        if (object instanceof JSONEntity) {
            JSONEntity entity = (JSONEntity) object;

            entity.setJson(((ObjectMapper) codec).writeValueAsString(node));
        }
    }
}
