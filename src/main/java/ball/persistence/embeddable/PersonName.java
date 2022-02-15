package ball.persistence.embeddable;
/*-
 * ##########################################################################
 * Persistence Implementation (Hibernate)
 * %%
 * Copyright (C) 2016 - 2022 Allen D. Ball
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * {@link PersonName} {@link Embeddable}.
 *
 * {@bean.info}
 *
 * @author {@link.uri mailto:ball@hcf.dev Allen D. Ball}
 */
@Embeddable
@NoArgsConstructor @Getter @Setter @EqualsAndHashCode(callSuper = false)
public class PersonName {
    @Column(length = 12)
    private String title = null;

    @Column(length = 32)
    private String first = null;

    @Column(length = 32)
    private String middle = null;

    @Column(length = 32)
    private String last = null;

    @Column(length = 12)
    private String suffix = null;

    @Column @Lob
    private String alias = null;

    /**
     * Method to clear all fields.
     */
    public void clear() {
        setTitle(null);
        setFirst(null);
        setMiddle(null);
        setLast(null);
        setSuffix(null);
        setAlias(null);
    }

    @Override
    public String toString() {
        String string = getAlias();

        if (isEmpty(string)) {
            string =
                Stream.of(title, first, middle, last, suffix)
                .filter(s -> (! isEmpty(s)))
                .collect(Collectors.joining(SPACE));
        }

        return string;
    }
}
