package comp3111.covid;

public class ConfirmedCaseTable {
	public String country;
	public String totalCases;
	public String totalCasesPerMillion;

	public ConfirmedCaseTable(String iCountry, String iTotalCases, String iTotalCasesPerMillion) {
        country = iCountry;
        totalCases = iTotalCases;
        totalCasesPerMillion = iTotalCasesPerMillion;
    }
	
	public String getCountry() {
		return country;
	}
	
	public String getTotalCases() {
		return totalCases;
	}
	
	public String getTotalCasesPerMillion() {
		return totalCasesPerMillion;
	}
}
