package com.ossez.covid19.common.dao.factories;

import com.ossez.covid19.common.Factory;
import com.ossez.covid19.common.mls.Provider;
import com.ossez.covid19.common.models.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Covid19Factory {
    private final static Logger logger = LoggerFactory.getLogger(Covid19Factory.class);

    public static Covid19Current get(Long id) {
        logger.debug(">>>");

        Object result = null;

        try {
            Factory.beginTransaction();
            result = Factory.getSession().get(Covid19Current.class, id);
        } finally {
            Factory.commitTransaction();
        }

        if (result != null)
            return (Covid19Current) result;

        return null;

    }

    /**
     * Gets Covid19Current by State
     *
     * @param covid19Current
     * @return
     */
    public static Covid19Current getByState(Covid19Current covid19Current) {
        Object result = null;

        try {
            Factory.beginTransaction();
            Criteria criteria = Factory.createCriteria(Covid19Current.class);
            criteria.add(Restrictions.eq("state", covid19Current.getState()));

            criteria.setMaxResults(1);
            result = criteria.uniqueResult();
        } finally {
            Factory.commitTransaction();
        }

        if (result != null)
            return (Covid19Current) result;

        return null;

    }

    /**
     * @param hash
     * @return
     */
    public static Covid19States getCovid19StatesByHash(String hash) {
        Object result = null;

        try {
            Factory.beginTransaction();
            Criteria criteria = Factory.createCriteria(Covid19States.class);
            criteria.add(Restrictions.eq("hash", hash));

            criteria.setMaxResults(1);
            result = criteria.uniqueResult();
        } finally {
            Factory.commitTransaction();
        }

        if (result != null)
            return (Covid19States) result;

        return null;

    }

    public static Covid19DailyUS getCovid19DailyUSByHash(String hash) {
        Object result = null;

        try {
            Factory.beginTransaction();
            Criteria criteria = Factory.createCriteria(Covid19DailyUS.class);
            criteria.add(Restrictions.eq("hash", hash));

            criteria.setMaxResults(1);
            result = criteria.uniqueResult();
        } finally {
            Factory.commitTransaction();
        }

        if (result != null)
            return (Covid19DailyUS) result;

        return null;

    }


    /**
     * Saves Covid19Current
     *
     * @param covid19Current
     */
    public static void save(Covid19Current covid19Current) {
        try {
            Factory.beginTransaction();
            covid19Current.setDateM(new Date());
            Factory.saveOrUpdate(covid19Current);
            Factory.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Save Covid19States
     *
     * @param covid19States
     */
    public static void save(Covid19States covid19States) {
        try {
            Factory.beginTransaction();
            covid19States.setDateM(new Date());
            Factory.saveOrUpdate(covid19States);
            Factory.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Save Covid19DailyUS
     *
     * @param covid19DailyUS
     */
    public static void save(Covid19DailyUS covid19DailyUS) {
        try {
            Factory.beginTransaction();
            covid19DailyUS.setDateM(new Date());
            Factory.saveOrUpdate(covid19DailyUS);
            Factory.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Deletes the specified feed
     *
     * @param listing
     */
    public static void delete(Provider provider) {
        Factory.delete(provider);
    }
}
