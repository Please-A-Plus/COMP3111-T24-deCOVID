package comp3111.covid;

import java.time.LocalDate;

/**
 * This class is used to store the coordinates of a date and value.
 * 
 */
public class FloatCoordinates {
    public LocalDate date;
    public Float value;

    /**
     * Constructor for the class.
     * 
     * @param date date entry
     * @param value value entry
     */
    public FloatCoordinates(LocalDate date, Float value) {
        this.date = date;
        this.value = value;
    }

    /**
     * Get the date of the entry.
     * 
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the value of the entry.
     * 
     * @return value
     */
    public Float getValue() {
        return value;
    }
}
