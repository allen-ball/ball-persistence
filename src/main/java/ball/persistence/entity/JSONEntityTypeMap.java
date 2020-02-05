package ball.persistence.entity;
/*-
 * ##########################################################################
 * Persistence Implementation (Hibernate)
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2016 - 2020 Allen D. Ball
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ##########################################################################
 */
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
