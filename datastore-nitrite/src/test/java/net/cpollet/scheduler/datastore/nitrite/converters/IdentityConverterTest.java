package net.cpollet.scheduler.datastore.nitrite.converters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IdentityConverterTest {
    private IdentityConverter<Object> converter;

    @BeforeEach
    public void setup() {
        converter = new IdentityConverter<>();
    }

    @Test
    public void convert() {
        // GIVEN
        String source = "source";

        // WHEN
        Object converted = converter.convert(source);

        // THEN
        Assertions.assertThat(converted)
                .isSameAs(source);
    }

    @Test
    public void convertBack() {
        // GIVEN
        String source = "source";

        // WHEN
        Object converted = converter.convertBack(source);

        // THEN
        Assertions.assertThat(converted)
                .isSameAs(source);
    }
}
