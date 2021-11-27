package comp3111.covidEntity;

public class ConfirmedDeathRecord {
	Long totalDeaths;
	Long newDeaths;
	Float totalDeathsPerMillion;
	Float newDeathsPerMillion;

	public ConfirmedDeathRecord(Long totalDeaths, Long newDeaths, Float totalDeathsPerMillion, Float newDeathsPerMillion) {
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalDeathsPerMillion = totalDeathsPerMillion;
        this.newDeathsPerMillion = newDeathsPerMillion;
    }

    public Long getTotalDeaths(){
        return totalDeaths;
    }

    public Long getNewDeaths(){
        return newDeaths;
    }

    public Float getTotalDeathsPerMillion(){
        return totalDeathsPerMillion;
    }

    public Float getNewDeathsPerMillion(){
        return newDeathsPerMillion;
    }
}
