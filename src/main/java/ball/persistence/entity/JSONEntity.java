/*
 * $Id$
 *
 * Copyright 2017 - 2019 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.databind.JSONBean;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Abstract base class for {@link JSONBean} entities.
 *
 * {@bean.info}
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JSONEntity extends JSONBean {
    private static final long serialVersionUID = -8802509358938963189L;

    /** @serial */
    @Getter
    @Column @Lob
    protected String json = null;

    protected void setJson(String string) {
        synchronized (this) {
            boolean modified = (node == null || (! string.equals(json)));

            json = string;

            if (modified) {
                node = null;

                if (json != null) {
                    try {
                        node = mapper.readTree(json);
                    } catch (IOException exception) {
                        throw new IllegalArgumentException(exception);
                    }
                }
            }
        }
    }

    /**
     * Method to get this {@link JSONEntity} as a {@link JsonNode}.
     *
     * @return  The {@link JSONEntity} as a {@link JsonNode}.
     */
    public JsonNode asJsonNode() {
        JsonNode node = this.node;

        return (node != null) ? node.deepCopy() : null;
    }

    @Override
    public String toString() {
        String string = getJson();

        return (string != null) ? string : super.toString();
    }
}
