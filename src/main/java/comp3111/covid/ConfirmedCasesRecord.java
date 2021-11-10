package comp3111.covid;

public class ConfirmedCasesRecord {
	public String country;
	public String totalCases;
	public String totalCasesPer1MPopulation;

	public ConfirmedCasesRecord(String country, String total_cases, String total_cases_per_1M_population) {
        this.country = country;
        this.totalCases = total_cases;
        this.totalCasesPer1MPopulation = total_cases_per_1M_population;
    }
	
	public String getCountry() {
		return country;
	}
	
	public String getTotalCases() {
		return totalCases;
	}
	
	public String getTotalCasesPer1MPopulation() {
		return totalCasesPer1MPopulation;
	}
}
