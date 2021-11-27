package comp3111.covidEntity;

public class ConfirmedDeathRecord {
	public Long totalDeaths;
	public Long newDeaths;
	public Float totalDeathsPerMillion;
	public Float newDeathsPerMillion;

	public ConfirmedDeathRecord(Long totalDeaths, Long newDeaths, Float totalDeathsPerMillion, Float newDeathsPerMillion) {
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalDeathsPerMillion = totalDeathsPerMillion;
        this.newDeathsPerMillion = newDeathsPerMillion;
    }
}
