package com.dzytsiuk.jdbcwrapper;

import com.dzytsiuk.jdbcwrapper.entity.Query;
import com.dzytsiuk.jdbcwrapper.exception.MoreThanOneObjectFoundException;
import com.dzytsiuk.jdbcwrapper.mapper.RowMapper;
import com.dzytsiuk.jdbcwrapper.parser.QueryParser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcTemplateImpl implements JdbcTemplate {
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final QueryParser QUERY_PARSER = new QueryParser();

    private DataSource dataSource;

    public JdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, Object... args) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setQueryParams(preparedStatement, args);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<T> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    resultList.add(rowMapper.mapRow(resultSet));
                }
                return resultList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query " + query, e);
        }
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, Map param) {
        Query parsedQuery = QUERY_PARSER.parseQuery(query, param);
        return query(parsedQuery.getQuery(), rowMapper, parsedQuery.getParams());
    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Map param) {
        List<T> resultList = query(query, rowMapper, param);
        return getObjectFromOneElementList(resultList);
    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Object... args) {
        List<T> resultList = query(query, rowMapper, args);
        return getObjectFromOneElementList(resultList);
    }


    public int update(String query, Object... args) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setQueryParams(preparedStatement, args);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query " + query, e);
        }
    }

    public int update(String query, Map param) {
        Query parsedQuery = QUERY_PARSER.parseQuery(query,param);
        return update(parsedQuery.getQuery(), parsedQuery.getParams());
    }


    private void setQueryParams(PreparedStatement preparedStatement, Object[] args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
    }

    private <T> T getObjectFromOneElementList(List<T> resultList) {
        int resultListSize = resultList.size();
        if (resultListSize > 1) {
            throw new MoreThanOneObjectFoundException("Expected one object, found " + resultListSize);
        } else if (resultListSize == 0) {
            return null;
        }
        return resultList.get(FIRST_ELEMENT_INDEX);
    }
}
