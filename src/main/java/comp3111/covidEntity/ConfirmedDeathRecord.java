package comp3111.covidEntity;

/**
 * This class is used to store the confirmed death record.
 */
public class ConfirmedDeathRecord {
    /**
     * The number of total confirmed deaths.
     */
	public final Long totalDeaths;

    /**
     * The number of daily new deaths.
     */
	public final Long newDeaths;

    /**
     * The number of total confirmed deaths per million population.
     */
	public final Float totalDeathsPerMillion;

    /**
     * The number of daily confirmed deaths per million population.
     */
	public final Float newDeathsPerMillion;

    /**
     * Class constructor.
     */
	public ConfirmedDeathRecord(Long totalDeaths, Long newDeaths, Float totalDeathsPerMillion, Float newDeathsPerMillion) {
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalDeathsPerMillion = totalDeathsPerMillion;
        this.newDeathsPerMillion = newDeathsPerMillion;
    }
}
