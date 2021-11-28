package comp3111.tableColumns;

/**
 * Death Cases Table Column Structure
 * The structure required to display number of death cases due to Covid for Task B1.
 * @author Bryan Suryaraso Gani
 * @since 2021-10-09
 */
public class DeathCaseTable {
    public String country;
	public String totalDeaths;
	public String totalDeathsPerMillion;

	/**
	 * Constructor for ConfirmedCaseTable
	 * @param iCountry Country name where the death cases due to Covid exist in.
	 * @param iTotalDeaths Total number of death cases due to Covid on a day in the given country.
	 * @param iTotalDeathsPerMillion Total number of death cases per million due to covid on a day in the given country.
	 */
	public DeathCaseTable(String iCountry, String iTotalDeaths, String iTotalDeathsPerMillion) {
        country = iCountry;
        totalDeaths = iTotalDeaths;
        totalDeathsPerMillion = iTotalDeathsPerMillion;
    }
	
	/**
	 * Get the name of the country recorded until a day.
	 * @return String representing the country's name.
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Get the amount of total death cases due to Covid until a day in the country.
	 * @return String on total number of deaths.
	 */
	public String getTotalDeaths() {
		return totalDeaths;
	}

	/**
	 * Get the amount of total death cases due to Covid until a day in the country.
	 * @return String on total number of deaths per million population.
	 */
	public String getTotalDeathsPerMillion() {
		return totalDeathsPerMillion;
	}
}
