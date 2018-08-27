package com.dzytsiuk.jdbcwrapper.entity;

import java.util.List;
import java.util.Objects;

public class Query {
    private String query;
    private List<Object> params;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query that = (Query) o;
        return Objects.equals(query, that.query) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {

        return Objects.hash(query, params);
    }

    @Override
    public String toString() {
        return "Query{" +
                "query='" + query + '\'' +
                ", params=" + params +
                '}';
    }
}
