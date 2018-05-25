package net.cpollet.scheduler.datastore.nitrite.converters;

public class IdentityConverter<T> implements Converter<T,T> {
    @Override
    public T convert(T from) {
        return from;
    }

    @Override
    public T convertBack(T from) {
        return from;
    }
}
