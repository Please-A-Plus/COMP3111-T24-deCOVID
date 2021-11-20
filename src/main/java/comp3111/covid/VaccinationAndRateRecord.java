package comp3111.covid;

public class VaccinationAndRateRecord {
	public String country;
	public String fullyVaccinated;
	public String rateOfVaccination;

	public VaccinationAndRateRecord(String country, String fullyVaccinated, String rateOfVaccination) {
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
