package comp3111.tableColumns;

public class VaccinationTable {
	public String country;
	public String fullyVaccinated;
	public String rateOfVaccination;

	public VaccinationTable(String country, String fullyVaccinated, String rateOfVaccination) {
		this.country = country;
		this.fullyVaccinated = fullyVaccinated;
		this.rateOfVaccination = rateOfVaccination;
	}

	public String getCountry() {
		return country;
	}

	public String getFullyVaccinated() {
		return fullyVaccinated;
	}

	public String getRateOfVaccination() {
		return rateOfVaccination;
	}
}
