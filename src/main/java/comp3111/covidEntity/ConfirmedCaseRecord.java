package comp3111.covidEntity;

/**
 * Confirmed Cases Record Data [Task A]
 * Data related to confirmed cases for CovidRecord
 * @see comp3111.covidEntity.CovidRecord .
 * @author Randy Stefan Tanuwijaya
 * @since 2021-11-07
 */
public class ConfirmedCaseRecord {
    /**
     * Total number of Covid cases until a day.
     */
	public Long totalCases;
    /**
     * New Covid cases on a day
     */
	public Long newCases;
	public Float totalCasesPerMillion;
	public Float newCasesPerMillion;

    /**
     * Constructor for ConfirmedCaseRecord
     * @param totalCases Total cases until that day
     * @param newCases New cases on that day
     * @param totalCasesPerMillion Total cases per million population
     * @param newCasesPerMillion New cases per million population
     */
	public ConfirmedCaseRecord(Long totalCases, Long newCases, Float totalCasesPerMillion, Float newCasesPerMillion) {
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalCasesPerMillion = totalCasesPerMillion;
        this.newCasesPerMillion = newCasesPerMillion;  
    }
}
