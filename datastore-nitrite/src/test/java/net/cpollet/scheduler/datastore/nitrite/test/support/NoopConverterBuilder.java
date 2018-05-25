package net.cpollet.scheduler.datastore.nitrite.test.support;

import net.cpollet.scheduler.datastore.nitrite.converters.Converter;

public class NoopConverterBuilder {
    public static <F, T> Converter<F, T> build(Class<F> from, Class<T> to) {
        return new Converter<F, T>() {
            @Override
            public T convert(F from) {
                return null;
            }

            @Override
            public F convertBack(T from) {
                return null;
            }
        };
    }
}
