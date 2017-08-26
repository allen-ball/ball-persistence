/*
 * $Id$
 *
 * Copyright 2017 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBean;
import ball.util.BeanMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.Column;

/**
 * Abstract base class for {@link JSONBean} entities.
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
@JsonSerialize(using = JSONEntity.Serializer.class)
public abstract class JSONEntity extends JSONBean {
    @Column(name = "json", length = Integer.MAX_VALUE, nullable = false)
    protected String string = null;

    /**
     * Sole constructor.
     */
    protected JSONEntity() { super(); }

    /**
     * Method to get this {@link JSONEntity} as a {@link JsonNode}.
     *
     * @return  The {@link JSONEntity} as a {@link JsonNode}.
     */
    @Override
    public JsonNode asJsonNode() {
        synchronized (this) {
            if (node == null) {
                try {
                    node = OM.readTree(string);
                } catch (Exception exception) {
                    throw new IllegalStateException(exception);
                }
            }
        }

        return node;
    }

    @Override
    public String toString() {
        synchronized (this) {
            if (string == null) {
                JsonNode node = asJsonNode();

                if (node != null) {
                    try {
                        string = JSONEntity.OM.writeValueAsString(node);
                    } catch (Exception exception) {
                        throw new IllegalStateException(exception);
                    }
                }
            }
        }

        return ((string != null)
                    ? string
                    : (getClass().getSimpleName()
                       + new BeanMap(this).toString()));
    }

    /**
     * {@link JSONEntity}
     * {@link com.fasterxml.jackson.databind.JsonSerializer}
     * implementation.
     */
    public static class Serializer extends JSONBean.Serializer {

        /**
         * Sole constructor.
         */
        public Serializer() { super(); }
    }
}
