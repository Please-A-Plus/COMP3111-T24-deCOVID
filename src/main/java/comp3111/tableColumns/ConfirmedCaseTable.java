package comp3111.tableColumns;

/**
 * <h1>Confirmed Case Table Column Structure</h1>
 * The structure required to display number of Covid cases for Task A1.
 * @author Stefan Tanuwijaya
 * @since 2021-10-09
 */

public class ConfirmedCaseTable {
	public String country;
	public String totalCases;
	public String totalCasesPerMillion;

	/**
	 * Constructor for ConfirmedCaseTable
	 * @param iCountry Country name where the case exist in.
	 * @param iTotalCases Total number of cases on a day in the given country in.
	 * @param iTotalCasesPerMillion Total number of cases per million on a day in the given country.
	 */
	public ConfirmedCaseTable(String iCountry, String iTotalCases, String iTotalCasesPerMillion) {
        country = iCountry;
        totalCases = iTotalCases;
        totalCasesPerMillion = iTotalCasesPerMillion;
    }
	
	/**
	 * Get the name of the country recorded until a day.
	 * @return String representing the country's name.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Get the amount of total cases until a day in the country.
	 * @return String on total number of cases.
	 */
	public String getTotalCases() {
		return totalCases;
	}
	
	/**
	 * Get the amount of total cases per million population until a day in the country.
	 * @return String on total number of cases per million.
	 */
	public String getTotalCasesPerMillion() {
		return totalCasesPerMillion;
	}
}
