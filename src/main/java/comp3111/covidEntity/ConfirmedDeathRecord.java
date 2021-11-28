package comp3111.covidEntity;

/**
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
     */
	public ConfirmedDeathRecord(Long totalDeaths, Long newDeaths, Float totalDeathsPerMillion, Float newDeathsPerMillion) {
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalDeathsPerMillion = totalDeathsPerMillion;
        this.newDeathsPerMillion = newDeathsPerMillion;
    }
}
