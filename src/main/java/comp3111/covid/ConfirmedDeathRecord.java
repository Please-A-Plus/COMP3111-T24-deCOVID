package comp3111.covid;

public class ConfirmedDeathRecord {
	public Long totalDeaths;
	public Long newDeaths;
	public Float totalDeathsPerMillion;
	public Float newDeathsPerMillion;

	public ConfirmedDeathRecord(Long totalCases, Long newCases, Float totalCasesPerMillion, Float newCasesPerMillion) {
        this.totalDeaths = totalCases;
        this.newDeaths = newCases;
        this.totalDeathsPerMillion = totalCasesPerMillion;
        this.newDeathsPerMillion = newCasesPerMillion;
    }
}