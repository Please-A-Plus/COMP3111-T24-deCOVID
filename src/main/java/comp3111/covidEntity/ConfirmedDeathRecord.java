package comp3111.covidEntity;

/**
<<<<<<< HEAD
 * Death Record Data [Task B]
 * Data related to death cases due to Covid for CovidRecord
 * @see comp3111.covidEntity.CovidRecord
 * @author Bryan Suryaraso Gani
 * @since 2021-11-07
 */
public class ConfirmedDeathRecord {
    /**
     * Total number of death due to Covid until a day.
     */
	public Long totalDeaths;
    /**
     * New death cases due to covid on a day
     */
	public Long newDeaths;
	public Float totalDeathsPerMillion;
	public Float newDeathsPerMillion;

    /**
     * Constructor for ConfirmedDeathRecord
     * @param totalDeaths Total deaths until that day
     * @param newDeaths New deaths on that day
     * @param totalDeathsPerMillion Total deaths per million population
     * @param newDeathsPerMillion New Deaths per million population
=======
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
>>>>>>> a52897e07dc65db746f2f170ce28d44df927bb75
     */
	public ConfirmedDeathRecord(Long totalDeaths, Long newDeaths, Float totalDeathsPerMillion, Float newDeathsPerMillion) {
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalDeathsPerMillion = totalDeathsPerMillion;
        this.newDeathsPerMillion = newDeathsPerMillion;
    }
}
