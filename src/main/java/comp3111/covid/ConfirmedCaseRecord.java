package comp3111.covid;

public class ConfirmedCaseRecord {
	public Long totalCases;
	public Long newCases;
	public Float totalCasesPerMillion;
	public Float newCasesPerMillion;

	public ConfirmedCaseRecord(Long totalCases, Long newCases, Float totalCasesPerMillion, Float newCasesPerMillion) {
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalCasesPerMillion = totalCasesPerMillion;
        this.newCasesPerMillion = newCasesPerMillion;  
    }
}