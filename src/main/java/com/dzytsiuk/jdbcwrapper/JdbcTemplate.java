package com.dzytsiuk.jdbcwrapper;

import com.dzytsiuk.jdbcwrapper.mapper.RowMapper;

import java.util.List;
import java.util.Map;

public interface JdbcTemplate {

    <T> List<T> query(String query, RowMapper<T> rowMapper, Map param);

    <T> T queryForObject(String query, RowMapper<T> rowMapper, Map param);

    int update(String query, Map param);

    <T> List<T> query(String query, RowMapper<T> rowMapper, Object... args);

    <T> T queryForObject(String query, RowMapper<T> rowMapper, Object... args);

    int update(String query, Object... args);

}

