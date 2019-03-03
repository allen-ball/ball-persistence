/*
 * $Id$
 *
 * Copyright 2017 Allen D. Ball.  All rights reserved.
 */
package ball.persistence.entity;

import ball.annotation.ServiceProviderFor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * {@link Module} service provider for {@link ball.persistence.entity}.
 *
 * @author {@link.uri mailto:ball@hcf.dev Allen D. Ball}
 * @version $Revision$
 */
@ServiceProviderFor({ Module.class })
public class ModuleImpl extends SimpleModule {
    private static final long serialVersionUID = 3636104653602191146L;

    /**
     * Sole constructor.
     */
    public ModuleImpl() {
        super(ModuleImpl.class.getPackage().getName());
    }

    @Override
    public void setupModule(Module.SetupContext context) {
        super.setupModule(context);
    }

    @Override
    public String toString() { return super.toString(); }
}
