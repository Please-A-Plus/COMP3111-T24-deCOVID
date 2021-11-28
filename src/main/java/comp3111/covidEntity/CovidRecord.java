package comp3111.covidEntity;

import java.time.LocalDate;

/**
 * CovidRecord stores the relevant information from the dataset.
 */
public class CovidRecord {
	/**
	 * Country ISO Code.
	 */
	public final String iso_code;

	/**
	 * Country name in english
	 */
	public final String location;

	/**
	 * Date of the record.
	 */
	public final LocalDate date;

	/**
	 * Population of the country.
	 */
	public final Long population;
	
	/**
	 * Confirmed cases record. Includes the number of daily new cases and total cases.
	*/
	public final ConfirmedCaseRecord confirmedCaseRecord;

	/**
	 * Confirmed deaths record. Includes the number of daily new deaths and total deaths.
	 */
	public final ConfirmedDeathRecord confirmedDeathRecord;

	/**
	 * Vaccination rate. Includes the number of total number of fully vaccinated, and vaccination rate.
	 */
	public final VaccinationRecord vaccinationRecord;
	
	/**
	 * Class Constructor.
	 */
	public CovidRecord(String iso_code, String location, LocalDate date, Long population, 
			ConfirmedCaseRecord confirmedCaseRecord, 
			ConfirmedDeathRecord confirmedDeathRecord,
			VaccinationRecord vaccinationRecord
			) {
		this.iso_code = iso_code;
		this.location = location;
		this.date = date;
		this.population = population;
		this.confirmedCaseRecord = confirmedCaseRecord;
		this.confirmedDeathRecord = confirmedDeathRecord;
		this.vaccinationRecord = vaccinationRecord;
	}
}
