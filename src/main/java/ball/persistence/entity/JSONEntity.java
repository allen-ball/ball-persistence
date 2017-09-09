/*
 * $Id$
 *
 * Copyright 2017 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBean;
import ball.util.BeanMap;
import com.fasterxml.jackson.databind.JsonNode;
import javax.persistence.Column;

/**
 * Abstract base class for {@link JSONBean} entities.
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
public abstract class JSONEntity extends JSONBean {
    @Column(name = "json", length = Integer.MAX_VALUE, nullable = false)
    protected String string = null;

    /**
     * Sole constructor.
     */
    protected JSONEntity() { super(); }

    @Override
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
