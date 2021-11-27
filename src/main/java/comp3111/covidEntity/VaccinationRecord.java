package comp3111.covidEntity;

public class VaccinationRecord {
	public Long fullyVaccinated;
	
	public VaccinationRecord(Long fullyVaccinated) {
		this.fullyVaccinated = fullyVaccinated;
	}

	public void updateVaccinationRecord(Long fullyVaccinated) { this.fullyVaccinated = fullyVaccinated; }
}
