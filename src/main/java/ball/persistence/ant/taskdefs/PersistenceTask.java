/*
 * $Id$
 *
 * Copyright 2019 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.ant.taskdefs;

import ball.util.ant.taskdefs.AnnotatedAntTask;
import ball.util.ant.taskdefs.AntTask;
import ball.util.ant.taskdefs.ClasspathDelegateAntTask;
import ball.util.ant.taskdefs.ConfigurableAntTask;
import ball.util.ant.taskdefs.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.util.ClasspathUtils;

import static javax.persistence.Persistence.generateSchema;
import static lombok.AccessLevel.PROTECTED;

/**
 * Abstract {@link javax.persistence.Persistence}
 * {@link.uri http://ant.apache.org/ Ant} {@link Task} base class.
 *
 * {@bean.info}
 *
 * @author {@link.uri mailto:ball@iprotium.com Allen D. Ball}
 * @version $Revision$
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class PersistenceTask extends Task
                                      implements AnnotatedAntTask,
                                                 ClasspathDelegateAntTask,
                                                 ConfigurableAntTask {
    @Getter @Setter @Accessors(chain = true, fluent = true)
    private ClasspathUtils.Delegate delegate = null;
    @NotNull @Getter @Setter
    private String name = null;

    @Override
    public void init() throws BuildException {
        super.init();
        ClasspathDelegateAntTask.super.init();
        ConfigurableAntTask.super.init();
    }

    @Override
    public void execute() throws BuildException {
        super.execute();
        AnnotatedAntTask.super.execute();
    }

    /**
     * {@link.uri http://ant.apache.org/ Ant} {@link Task} to generate
     * schema.
     *
     * {@bean.info}
     */
    @AntTask("generate-schema")
    @NoArgsConstructor @ToString
    public static class GenerateSchema extends PersistenceTask {
        @Override
        public void execute() throws BuildException {
            super.execute();

            try {
                generateSchema(getName(), getProject().getProperties());
            } catch (BuildException exception) {
                throw exception;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new BuildException(throwable);
            }
        }
    }
}
