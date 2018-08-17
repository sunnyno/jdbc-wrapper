package com.dzytsiuk.jdbcwrapper.entity;

import java.util.Arrays;
import java.util.Objects;

public class Query {
    private String query;
    private Object[] params;

    public Query() {
    }

    public Query(String query, Object[] params) {
        this.query = query;
        this.params = params;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query1 = (Query) o;
        return Objects.equals(query, query1.query) &&
                Arrays.equals(params, query1.params);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(query);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }
}
