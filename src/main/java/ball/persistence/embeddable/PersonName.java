/*
 * $Id$
 *
 * Copyright 2018 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.embeddable;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static ball.util.StringUtil.isNil;

/**
 * {@bean.info}
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@Embeddable
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

        if (isNil(string)) {
            string =
                Stream.of(title, first, middle, last, suffix)
                .filter(s -> (! isNil(s)))
                .collect(Collectors.joining(" "));
        }

        return string;
    }
}
