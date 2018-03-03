/*
 * $Id$
 *
 * Copyright 2017, 2018 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBean;
import ball.util.BeanMap;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Abstract base class for {@link JSONBean} entities.
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
@MappedSuperclass
public abstract class JSONEntity extends JSONBean {
    protected String string = null;

    /**
     * Sole constructor.
     */
    protected JSONEntity() { super(); }

    @Column(length = Integer.MAX_VALUE, nullable = false)
    public String getJSON() { return toString(); }
    protected void setJSON(String string) { this.string = string; }

    /**
     * Method to get this {@link JSONEntity} as a {@link JsonNode}.
     *
     * @return  The {@link JSONEntity} as a {@link JsonNode}.
     */
    public JsonNode asJsonNode() {
        synchronized (this) {
            if (node == null) {
                String string = this.string;

                if (string != null) {
                    try {
                        node = OM.readTree(string);
                    } catch (RuntimeException exception) {
                        throw exception;
                    } catch (Exception exception) {
                        throw new IllegalStateException(exception);
                    }
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
                        string = OM.writeValueAsString(node);
                    } catch (Exception exception) {
                        throw new IllegalStateException(exception);
                    }
                }
            }
        }

        return (string != null) ? string : super.toString();
    }
}
