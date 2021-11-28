package comp3111.covidEntity;

public class VaccinationRecord {
	public Long fullyVaccinated;
	public Float backupRate;
	
	public VaccinationRecord(Long fullyVaccinated, Float backup) {
		this.fullyVaccinated = fullyVaccinated;
		this.backupRate = backup;
	}

	public void updateVaccinationRecord(Long fullyVaccinated) { this.fullyVaccinated = fullyVaccinated; }
}
