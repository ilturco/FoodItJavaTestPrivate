package com.foodit.test.sample.controller;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.TranslatorFactory;
import com.googlecode.objectify.impl.translate.TranslatorRegistry;
import com.googlecode.objectify.impl.translate.opt.joda.*;
import com.threewks.thundr.gae.objectify.ObjectifyModule;
import com.threewks.thundr.injection.InjectionContextImpl;
import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.test.TestSupport;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by salvatore on 01/08/2014.
 */
public class ApplicationModuleTest {
    private UpdatableInjectionContext injectionContext = new InjectionContextImpl();
    @Test
    public void shouldRegisterTranslatorsAndInjectObjectifyFactory() {
        new ObjectifyModule().initialise(injectionContext);

        assertThat(injectionContext.get(ObjectifyFactory.class), is(notNullValue()));

        TranslatorRegistry translators = ObjectifyService.factory().getTranslators();
        List<TranslatorFactory<?>> factories = TestSupport.getField(translators, "translators");
        assertThat(factoriesContain(factories, ReadableInstantTranslatorFactory.class), is(true));
        assertThat(factoriesContain(factories, LocalDateTranslatorFactory.class), is(true));
        assertThat(factoriesContain(factories, LocalDateTimeTranslatorFactory.class), is(true));
        assertThat(factoriesContain(factories, LocalTimeTranslatorFactory.class), is(true));
        assertThat(factoriesContain(factories, DateTimeZoneTranslatorFactory.class), is(true));

    }

    private boolean factoriesContain(List<TranslatorFactory<?>> factories, Class<? extends TranslatorFactory<?>> class1) {
        for (TranslatorFactory<?> factory : factories) {
            if (factory.getClass() == class1) {
                return true;
            }
        }
        return false;
    }
}
