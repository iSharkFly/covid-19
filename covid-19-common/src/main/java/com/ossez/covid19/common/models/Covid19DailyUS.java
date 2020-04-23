package com.ossez.covid19.common.models;

import com.ossez.covid19.common.DataObject;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(catalog = "covid19", name = "Covid19DailyUS")
public class Covid19DailyUS extends DataObject implements java.io.Serializable {
    private static final long serialVersionUID = -612061041423673622L;

    private String date;
    private String states;

    private String positive;
    private String negative;
    private String pending;
    private String hospitalizedCurrently;
    private String hospitalizedCumulative;
    private String inIcuCurrently;
    private String inIcuCumulative;
    private String onVentilatorCurrently;
    private String onVentilatorCumulative;
    private String recovered;
    private String hash;
    private String dateChecked;
    private String death;
    private String hospitalized;
    private String total;
    private String totalTestResults;
    private String posNeg;
    private String deathIncrease;
    private String hospitalizedIncrease;
    private String negativeIncrease;
    private String positiveIncrease;
    private String totalTestResultsIncrease;


    public Covid19DailyUS() {
    }

//    @Id
//    @GeneratedValue
//    private long id = 0;
//
//    @Override
//    public long getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(long id) {
//        this.id = id;
//    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getHospitalizedCurrently() {
        return hospitalizedCurrently;
    }

    public void setHospitalizedCurrently(String hospitalizedCurrently) {
        this.hospitalizedCurrently = hospitalizedCurrently;
    }

    public String getHospitalizedCumulative() {
        return hospitalizedCumulative;
    }

    public void setHospitalizedCumulative(String hospitalizedCumulative) {
        this.hospitalizedCumulative = hospitalizedCumulative;
    }

    public String getInIcuCurrently() {
        return inIcuCurrently;
    }

    public void setInIcuCurrently(String inIcuCurrently) {
        this.inIcuCurrently = inIcuCurrently;
    }

    public String getInIcuCumulative() {
        return inIcuCumulative;
    }

    public void setInIcuCumulative(String inIcuCumulative) {
        this.inIcuCumulative = inIcuCumulative;
    }

    public String getOnVentilatorCurrently() {
        return onVentilatorCurrently;
    }

    public void setOnVentilatorCurrently(String onVentilatorCurrently) {
        this.onVentilatorCurrently = onVentilatorCurrently;
    }

    public String getOnVentilatorCumulative() {
        return onVentilatorCumulative;
    }

    public void setOnVentilatorCumulative(String onVentilatorCumulative) {
        this.onVentilatorCumulative = onVentilatorCumulative;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDateChecked() {
        return dateChecked;
    }

    public void setDateChecked(String dateChecked) {
        this.dateChecked = dateChecked;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(String hospitalized) {
        this.hospitalized = hospitalized;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalTestResults() {
        return totalTestResults;
    }

    public void setTotalTestResults(String totalTestResults) {
        this.totalTestResults = totalTestResults;
    }

    public String getPosNeg() {
        return posNeg;
    }

    public void setPosNeg(String posNeg) {
        this.posNeg = posNeg;
    }

    public String getDeathIncrease() {
        return deathIncrease;
    }

    public void setDeathIncrease(String deathIncrease) {
        this.deathIncrease = deathIncrease;
    }

    public String getHospitalizedIncrease() {
        return hospitalizedIncrease;
    }

    public void setHospitalizedIncrease(String hospitalizedIncrease) {
        this.hospitalizedIncrease = hospitalizedIncrease;
    }

    public String getNegativeIncrease() {
        return negativeIncrease;
    }

    public void setNegativeIncrease(String negativeIncrease) {
        this.negativeIncrease = negativeIncrease;
    }

    public String getPositiveIncrease() {
        return positiveIncrease;
    }

    public void setPositiveIncrease(String positiveIncrease) {
        this.positiveIncrease = positiveIncrease;
    }

    public String getTotalTestResultsIncrease() {
        return totalTestResultsIncrease;
    }

    public void setTotalTestResultsIncrease(String totalTestResultsIncrease) {
        this.totalTestResultsIncrease = totalTestResultsIncrease;
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(date)
                .append(states)
                .append(positive)
                .append(negative)
                .append(pending)
                .append(hospitalizedCurrently)
                .append(hospitalizedCumulative)
                .append(inIcuCurrently)
                .append(inIcuCumulative)
                .append(onVentilatorCurrently)
                .append(onVentilatorCumulative)
                .append(recovered)
                .append(hash)
                .append(dateChecked)
                .append(death)
                .append(hospitalized)
                .append(total)
                .append(totalTestResults)
                .append(posNeg)
                .append(deathIncrease)
                .append(hospitalizedIncrease)
                .append(negativeIncrease)
                .append(positiveIncrease)
                .append(totalTestResultsIncrease)
                .toHashCode();
    }
}
