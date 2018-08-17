package com.dzytsiuk.jdbcwrapper.mapper

import com.dzytsiuk.jdbcwrapper.enity.Product

import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp

class ProductRowMapper implements RowMapper<Product>{
    @Override
    Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product()
        product.setId(resultSet.getInt("id"))
        Timestamp date = resultSet.getTimestamp("creation_date")
        product.setCreationDate(date.toLocalDateTime())
        product.setName(resultSet.getString("name"))
        product.setPrice(resultSet.getDouble("price"))
        return product
    }
}
