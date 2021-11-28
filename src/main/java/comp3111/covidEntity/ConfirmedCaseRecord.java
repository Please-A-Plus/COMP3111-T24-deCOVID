package comp3111.covidEntity;

/**
 * This class is used to store the confirmed case record. 
 */
public class ConfirmedCaseRecord {
    /**
     * The number of total confirmed cases.
     */
	public final Long totalCases;

    /**
     * The number of daily new cases.
     */
	public final Long newCases;

    /**
     * The number of total confirmed cases per million population.
     */
	public final Float totalCasesPerMillion;

    /**
     * The number of daily confirmed cases per million population.
     */
	public final Float newCasesPerMillion;

    /**
     * Class constructor.
     */
	public ConfirmedCaseRecord(Long totalCases, Long newCases, Float totalCasesPerMillion, Float newCasesPerMillion) {
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalCasesPerMillion = totalCasesPerMillion;
        this.newCasesPerMillion = newCasesPerMillion;  
    }
}
