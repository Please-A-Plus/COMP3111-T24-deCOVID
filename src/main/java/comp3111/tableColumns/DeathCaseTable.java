package comp3111.tableColumns;

public class DeathCaseTable {
    public String country;
	public String totalDeaths;
	public String totalDeathsPerMillion;

	public DeathCaseTable(String iCountry, String iTotalDeaths, String iTotalDeathsPerMillion) {
        country = iCountry;
        totalDeaths = iTotalDeaths;
        totalDeathsPerMillion = iTotalDeathsPerMillion;
    }
	
	public String getCountry() {
		return country;
	}
	
	public String getTotalDeaths() {
		return totalDeaths;
	}
	
	public String getTotalDeathsPerMillion() {
		return totalDeathsPerMillion;
	}
}
