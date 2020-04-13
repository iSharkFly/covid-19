package com.ossez.covid19.service.rets;

/**
 * Class to store a town,state,county association. This class is used as part of a larger collection to map a town id to the three
 * aforementioned values.
 *
 * @author YuCheng Hu
 */
public class MlsPinLocation {
    private String townName = null;
    private String stateName = null;
    private String countyName = null;

    public MlsPinLocation(String townName, String stateName, String countyName) {
        this.townName = townName;
        this.stateName = stateName;
        this.countyName = countyName;
    }

    public String getTownName() {
        return townName;
    }

    public String getStateName() {
        return stateName;
    }

    public String getCountyName() {
        return countyName;
    }
}
