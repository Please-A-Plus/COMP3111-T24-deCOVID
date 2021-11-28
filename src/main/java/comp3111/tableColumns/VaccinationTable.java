package comp3111.tableColumns;

/**
 * <h1>Vaccination Table Column Structure</h1>
 * The structure required to display vaccination data for task C1
 * @author CHAN Kei Chi
 * @since 2021-10-09
 */

public class VaccinationTable {
	public String country;
	public String fullyVaccinated;
	public String rateOfVaccination;
	
	/**
	 * Constructor for VaccinationTable
	 * @param country Name of the country in the vaccination data.
	 * @param fullyVaccinated Total number of fully vaccinated individual in the country until a day.
	 * @param rateOfVaccination Rate of vaccination in the country.
	 */
	public VaccinationTable(String country, String fullyVaccinated, String rateOfVaccination) {
		this.country = country;
		this.fullyVaccinated = fullyVaccinated;
		this.rateOfVaccination = rateOfVaccination;
	}

	/**
	 * Get the name of the country recorded.
	 * @return String representing the country's name
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Get the amount of fully vaccinated people in the country until a day
	 * @return String on number of fully vaccinated people
	 */
	public String getFullyVaccinated() {
		return fullyVaccinated;
	}

	/**
	 * Get the amount of vacccinated people per population in the country until a day.
	 * @return String on number of fully vaccinated people per population
	 */
	public String getRateOfVaccination() {
		return rateOfVaccination;
	}
}
