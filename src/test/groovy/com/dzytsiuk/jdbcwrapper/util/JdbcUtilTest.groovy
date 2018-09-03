package com.dzytsiuk.jdbcwrapper.util

import org.junit.Test

import java.sql.Date
import java.sql.PreparedStatement
import java.sql.Time
import java.sql.Timestamp

import static org.junit.Assert.assertNull;

class JdbcUtilTest {
    @Test
    void "set int as query param"() {
        def preparedStatement = [setInt: { index, param -> "int" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, 1))
    }

    @Test
    void "set Double as query param"() {
        def preparedStatement = [setDouble: { index, param -> "double" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, 1.0 as Double))
    }

    @Test
    void "set Time as query param"() {
        def preparedStatement = [setTime: { index, param -> "time" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        def param = new Time(1, 1, 1)
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, param))
    }

    @Test
    void "set Timestamp as query param"() {
        def preparedStatement = [setTimestamp: { index, param -> "timestamp" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        def param = new Timestamp(1)
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, param))
    }

    @Test
    void "set String as query param"() {
        def preparedStatement = [setString: { index, param -> "string" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, "str"))
    }

    @Test
    void "set Date as query param"() {
        def preparedStatement = [setDate: { index, param -> "date" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        def param = new Date(1, 1, 1)
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, param))
    }

    @Test
    void "set BigDecimal as query param"() {
        def preparedStatement = [setBigDecimal: { index, param -> "big decimal" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        def param = new BigDecimal(1)
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, param))
    }

    @Test
    void "set null as query param"() {
        def preparedStatement = [setNull: { index, param -> "null" }] as PreparedStatement
        //UnsupportedOperationException is not thrown
        assertNull(JdbcUtil.setQueryParam(preparedStatement, 0, null))
    }
}