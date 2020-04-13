package com.ossez.covid19.service.rets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author YuCheng Hu
 */
public class RetsCriteria {
    private String query = "";
    private String path = "";
    private List<String> fields = new ArrayList<String>();

    public RetsCriteria(String query, String path, String[] fields) {
        this.query = query;
        this.path = path;
        this.fields = Arrays.asList(fields);
    }

    public RetsCriteria(String query, String path, List<String> fields) {
        this.query = query;
        this.path = path;
        this.fields = fields;
    }

    /**
     * @return
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * @param fields
     */
    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
