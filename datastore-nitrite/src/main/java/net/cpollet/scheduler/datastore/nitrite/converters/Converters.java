package net.cpollet.scheduler.datastore.nitrite.converters;

public final class Converters {
    private Converters(){
        // not instantiable class
    }

    public static JobConverter job() {
        return new JobConverter(
                new JobIdConverter(),
                new IdentityConverter<>(),
                new IdentityConverter<>(),
                new TriggerConverter(
                        new IdentityConverter<>(),
                        new TriggerUnitConverter(),
                        new IdentityConverter<>()
                ),
                new JobStatusConverter()
        );
    }
}
