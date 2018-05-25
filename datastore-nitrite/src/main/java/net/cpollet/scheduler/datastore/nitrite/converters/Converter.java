package net.cpollet.scheduler.datastore.nitrite.converters;

public interface Converter<F, T> {
    T convert(F from);

    F convertBack(T from);
}
