package com.dzytsiuk.jdbcwrapper.util;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;

public class JdbcUtil {
    public static void setQueryParam(PreparedStatement preparedStatement, int index, Object param) throws SQLException {
        if (param == null) {
            preparedStatement.setNull(index, Types.NULL);
        } else {
            Class<?> paramClass = param.getClass();
            if (Integer.class.equals(paramClass)) {
                preparedStatement.setInt(index, (int) param);
            } else if (Timestamp.class.equals(paramClass)) {
                preparedStatement.setTimestamp(index, (Timestamp) param);
            } else if (Time.class.equals(paramClass)) {
                preparedStatement.setTime(index, (Time) param);
            } else if (String.class.equals(paramClass)) {
                preparedStatement.setString(index, (String) param);
            } else if (Double.class.equals(paramClass)) {
                preparedStatement.setDouble(index, (double) param);
            } else if (Date.class.equals(paramClass)) {
                preparedStatement.setDate(index, (Date) param);
            } else if (BigDecimal.class.equals(paramClass)) {
                preparedStatement.setBigDecimal(index, (BigDecimal) param);
            } else if (Blob.class.equals(paramClass)) {
                preparedStatement.setBlob(index, (Blob) param);
            } else if (Boolean.class.equals(paramClass)) {
                preparedStatement.setBoolean(index, (boolean) param);
            } else if (Byte.class.equals(paramClass)) {
                preparedStatement.setByte(index, (byte) param);
            } else if (Byte[].class.equals(paramClass)) {
                preparedStatement.setBytes(index, (byte[]) param);
            } else if (Clob.class.equals(paramClass)) {
                preparedStatement.setClob(index, (Clob) param);
            } else if (Float.class.equals(paramClass)) {
                preparedStatement.setFloat(index, (float) param);
            } else if (Long.class.equals(paramClass)) {
                preparedStatement.setLong(index, (long) param);
            } else if (Ref.class.equals(paramClass)) {
                preparedStatement.setRef(index, (Ref) param);
            } else if (RowId.class.equals(paramClass)) {
                preparedStatement.setRowId(index, (RowId) param);
            } else if (Short.class.equals(paramClass)) {
                preparedStatement.setShort(index, (short) param);
            } else if (SQLXML.class.equals(paramClass)) {
                preparedStatement.setSQLXML(index, (SQLXML) param);
            } else if (URL.class.equals(paramClass)) {
                preparedStatement.setURL(index, (URL) param);
            } else if (LocalDateTime.class.equals(paramClass)) {
                preparedStatement.setTimestamp(index, Timestamp.valueOf((LocalDateTime) param));
            } else if (Character.class.equals(paramClass)) {
                preparedStatement.setString(index, String.valueOf(param));
            } else {
                preparedStatement.setObject(index, param);
            }
        }
    }
}
