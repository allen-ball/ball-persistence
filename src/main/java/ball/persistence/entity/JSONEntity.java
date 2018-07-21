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
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * Abstract base class for {@link JSONBean} entities.
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
@MappedSuperclass
public abstract class JSONEntity extends JSONBean {
    private static final long serialVersionUID = -7690003611785281916L;

    /**
     * Sole constructor.
     */
    protected JSONEntity() { super(); }

    @Column @Lob
    public String getJSON() {
        String string = null;

        synchronized (this) {
            try {
                JsonNode node = this.node;

                string = (node != null) ? OM.writeValueAsString(node) : null;
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        return string;
    }

    protected void setJSON(String string) {
        if (string != null) {
            try {
                node = OM.readTree(string);
            } catch (Exception exception) {
                throw new IllegalArgumentException(exception);
            }
        } else {
            node = null;
        }
    }

    /**
     * Method to get this {@link JSONEntity} as a {@link JsonNode}.
     *
     * @return  The {@link JSONEntity} as a {@link JsonNode}.
     */
    public JsonNode asJsonNode() {
        JsonNode node = this.node;

        if (node != null) {
            node = node.deepCopy();
        } else {
            node = OM.valueToTree(this);
        }

        return node;
    }

    @Override
    public String toString() {
        String string = null;

        try {
            string = OM.writeValueAsString(asJsonNode());
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }

        return string;
    }
}
