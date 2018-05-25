package net.cpollet.scheduler.datastore.nitrite.converters;

import net.cpollet.scheduler.engine.api.PeriodicTrigger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TriggerUnitConverterTest {
    private TriggerUnitConverter converter;

    @BeforeEach
    public void setup() {
        converter = new TriggerUnitConverter();
    }

    @Test
    public void convert_MILLISECOND() {
        // GIVEN
        PeriodicTrigger.Unit unit = PeriodicTrigger.Unit.MILLISECOND;

        // WHEN
        String converted = converter.convert(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("MILLISECOND");
    }

    @Test
    public void convert_SECOND() {
        // GIVEN
        PeriodicTrigger.Unit unit = PeriodicTrigger.Unit.SECOND;

        // WHEN
        String converted = converter.convert(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("SECOND");
    }

    @Test
    public void convert_MINUTE() {
        // GIVEN
        PeriodicTrigger.Unit unit = PeriodicTrigger.Unit.MINUTE;

        // WHEN
        String converted = converter.convert(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("MINUTE");
    }

    @Test
    public void convert_HOUR() {
        // GIVEN
        PeriodicTrigger.Unit unit = PeriodicTrigger.Unit.HOUR;

        // WHEN
        String converted = converter.convert(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("HOUR");
    }

    @Test
    public void convert_DAY() {
        // GIVEN
        PeriodicTrigger.Unit unit = PeriodicTrigger.Unit.DAY;

        // WHEN
        String converted = converter.convert(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("DAY");
    }

    @Test
    public void convert_WEEK() {
        // GIVEN
        PeriodicTrigger.Unit unit = PeriodicTrigger.Unit.WEEK;

        // WHEN
        String converted = converter.convert(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("WEEK");
    }

    @Test
    public void convert_MONTH() {
        // GIVEN
        PeriodicTrigger.Unit unit = PeriodicTrigger.Unit.MONTH;

        // WHEN
        String converted = converter.convert(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo("MONTH");
    }

    @Test
    public void convertBack_MILLISECOND() {
        // GIVEN
        String unit = "MILLISECOND";

        // WHEN
        PeriodicTrigger.Unit converted = converter.convertBack(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(PeriodicTrigger.Unit.MILLISECOND);
    }

    @Test
    public void convertBack_SECOND() {
        // GIVEN
        String unit ="SECOND";

        // WHEN
        PeriodicTrigger.Unit converted = converter.convertBack(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(PeriodicTrigger.Unit.SECOND);
    }

    @Test
    public void convertBack_MINUTE() {
        // GIVEN
        String unit = "MINUTE";

        // WHEN
        PeriodicTrigger.Unit converted = converter.convertBack(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(PeriodicTrigger.Unit.MINUTE);
    }

    @Test
    public void convertBack_HOUR() {
        // GIVEN
        String unit = "HOUR";

        // WHEN
        PeriodicTrigger.Unit converted = converter.convertBack(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(PeriodicTrigger.Unit.HOUR);
    }

    @Test
    public void convertBack_DAY() {
        // GIVEN
        String unit = "DAY";

        // WHEN
        PeriodicTrigger.Unit converted = converter.convertBack(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(PeriodicTrigger.Unit.DAY);
    }

    @Test
    public void convertBack_WEEK() {
        // GIVEN
        String unit = "WEEK";

        // WHEN
        PeriodicTrigger.Unit converted = converter.convertBack(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(PeriodicTrigger.Unit.WEEK);
    }

    @Test
    public void convertBack_MONTH() {
        // GIVEN
        String unit = "MONTH";

        // WHEN
        PeriodicTrigger.Unit converted = converter.convertBack(unit);

        // THEN
        Assertions.assertThat(converted)
                .isEqualTo(PeriodicTrigger.Unit.MONTH);
    }
}
