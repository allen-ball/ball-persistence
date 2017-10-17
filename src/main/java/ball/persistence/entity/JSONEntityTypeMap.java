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
    private static final long serialVersionUID = -1055558641915834979L;

    private static final ObjectMapper OM =
        new ObjectMapper()
        .configure(SerializationFeature.INDENT_OUTPUT, true);

    /**
     * Sole constructor.
     */
    protected JSONEntityTypeMap() { super(); }

    @Override
    protected void initialize(Object object,
                              ObjectCodec codec,
                              JsonNode node) throws IOException {
        super.initialize(object, codec, node);
        ((JSONEntity) object).string = OM.writeValueAsString(node);
    }
}
