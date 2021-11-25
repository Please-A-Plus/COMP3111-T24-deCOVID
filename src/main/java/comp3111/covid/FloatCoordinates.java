package comp3111.covid;

import java.time.LocalDate;

public class FloatCoordinates {
    public LocalDate date;
    public Float value;

    public FloatCoordinates(LocalDate date, Float value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Float getValue() {
        return value;
    }
}
