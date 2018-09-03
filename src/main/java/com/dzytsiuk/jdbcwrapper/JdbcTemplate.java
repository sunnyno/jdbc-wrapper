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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.dzytsiuk.jdbcwrapper.util.JdbcUtil.*;

public class JdbcTemplate {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final QueryParser QUERY_PARSER = new QueryParser();

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, Object... args) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length; i++) {
                setQueryParam(preparedStatement, i + 1, args[i]);
            }
            logger.info("Executing query {}", query);
            return getListFromResultSet(rowMapper, preparedStatement);
        } catch (SQLException e) {
            logger.error("Failed to execute query {}", query);
            throw new RuntimeException("Error executing query " + query, e);
        }
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, Map<String, Object> param) {
        Query parsedQuery = QUERY_PARSER.parseQuery(query, param);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(parsedQuery.getQuery())) {
            List<Object> params = parsedQuery.getParams();
            for (int i = 0; i < params.size(); i++) {
                setQueryParam(preparedStatement, i + 1, params.get(i));
            }
            logger.info("Executing query {}", query);
            return getListFromResultSet(rowMapper, preparedStatement);
        } catch (SQLException e) {
            logger.error("Failed to execute query {}", query);
            throw new RuntimeException("Error executing query " + query, e);
        }
    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Map<String, Object> param) {
        List<T> resultList = query(query, rowMapper, param);
        return getObjectFromOneElementList(resultList);
    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Object... args) {
        List<T> resultList = query(query, rowMapper, args);
        logger.info("Executing query {}", query);
        return getObjectFromOneElementList(resultList);
    }


    public int update(String query, Object... args) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length; i++) {
                setQueryParam(preparedStatement, i + 1, args[i]);
            }
            logger.info("Executing query {}", query);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to execute query {}", query);
            throw new RuntimeException("Error executing query " + query, e);
        }
    }

    public int update(String query, Map<String, Object> param) {
        Query parsedQuery = QUERY_PARSER.parseQuery(query, param);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(parsedQuery.getQuery())) {
            List<Object> params = parsedQuery.getParams();
            for (int i = 0; i < params.size(); i++) {
                setQueryParam(preparedStatement, i + 1, params.get(i));
            }
            logger.info("Executing query {}", query);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to execute query {}", query);
            throw new RuntimeException("Error executing query " + query, e);
        }
    }


    private <T> List<T> getListFromResultSet(RowMapper<T> rowMapper, PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<T> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add(rowMapper.mapRow(resultSet));
            }
            return resultList;
        }
    }

    private <T> T getObjectFromOneElementList(List<T> resultList) {
        int resultListSize = resultList.size();
        if (resultListSize > 1) {
            logger.error("Found {} objects, expected 1", resultListSize);
            throw new MoreThanOneObjectFoundException("Expected one object, found " + resultListSize);
        } else if (resultListSize == 0) {
            return null;
        }
        return resultList.get(FIRST_ELEMENT_INDEX);
    }
}
