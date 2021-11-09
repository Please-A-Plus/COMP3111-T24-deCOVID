package comp3111.covid;

import java.time.LocalDate;

public class CovidRecord {
	public String iso_code;
	public String location;
	public LocalDate date;
	public Long population;
	
	public ConfirmedCaseRecord confirmedCaseRecord;
	public ConfirmedDeathRecord confirmedDeathRecord;
	public VaccinationRecord vaccinationRecord;
	
	CovidRecord(String iso_code, String location, LocalDate date, Long population, 
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
