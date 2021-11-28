package comp3111.covidEntity;

/**
 * This class is used to store the vaccination rate record. 
 */
public class VaccinationRecord {
    /**
     * The number of total confirmed cases.
     */
	public final Long fullyVaccinated;

    /**
     * The number of vaccination rate.
     */
	public final Float backupRate;

    /**
     * Class constructor.
     */
	public VaccinationRecord(Long fullyVaccinated, Float backup) {
		this.fullyVaccinated = fullyVaccinated;
		this.backupRate = backup;
	}

}
