package ball.persistence.ant.taskdefs;
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
import ball.util.ant.taskdefs.AnnotatedAntTask;
import ball.util.ant.taskdefs.AntTask;
import ball.util.ant.taskdefs.ClasspathDelegateAntTask;
import ball.util.ant.taskdefs.ConfigurableAntTask;
import ball.util.ant.taskdefs.NotNull;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
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
 * @author {@link.uri mailto:ball@hcf.dev Allen D. Ball}
 */
@NoArgsConstructor(access = PROTECTED)
public abstract class PersistenceTask extends Task implements AnnotatedAntTask,
                                                              ClasspathDelegateAntTask, ConfigurableAntTask {
    @Getter @Setter @Accessors(chain = true, fluent = true)
    private ClasspathUtils.Delegate delegate = null;

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
     * {@link.uri http://ant.apache.org/ Ant} {@link Task} to list available
     * {@link javax.persistence.spi.PersistenceProvider}s.
     *
     * {@bean.info}
     */
    @AntTask("list-providers")
    @NoArgsConstructor @ToString
    public static class ListProviders extends PersistenceTask {
        @Override
        public void execute() throws BuildException {
            super.execute();

            try {
                PersistenceProviderResolver resolver =
                    PersistenceProviderResolverHolder.getPersistenceProviderResolver();

                log(String.valueOf(resolver.getPersistenceProviders()));
            } catch (BuildException exception) {
                throw exception;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new BuildException(throwable);
            }
        }
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
        @NotNull @Getter @Setter
        private String name = null;

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
