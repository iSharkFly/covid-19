package com.ossez.covid19.common.models;

import com.ossez.covid19.common.DataObject;
import com.ossez.covid19.common.Utility;
import com.ossez.covid19.common.mls.County;
import com.ossez.covid19.common.search.analyzers.LowercaseAnalyzer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(catalog= "covid19", name = "Covid19Current")
@Entity
public class Covid19Current extends DataObject implements java.io.Serializable {
    private static final long serialVersionUID = -612061041423673622L;

    private String state;

    private String positive;
    private String positiveScore;
    private String negativeScore;
    private String negativeRegularScore;

    public Covid19Current() {
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


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getPositiveScore() {
        return positiveScore;
    }

    public void setPositiveScore(String positiveScore) {
        this.positiveScore = positiveScore;
    }

    public String getNegativeScore() {
        return negativeScore;
    }

    public void setNegativeScore(String negativeScore) {
        this.negativeScore = negativeScore;
    }

    public String getNegativeRegularScore() {
        return negativeRegularScore;
    }

    public void setNegativeRegularScore(String negativeRegularScore) {
        this.negativeRegularScore = negativeRegularScore;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(state)
                .append(positive)
                .append(positiveScore)
                .append(negativeScore)
                .append(negativeRegularScore)
                .toHashCode();
    }


}
