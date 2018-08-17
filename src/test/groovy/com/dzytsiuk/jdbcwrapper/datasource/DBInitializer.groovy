package com.dzytsiuk.jdbcwrapper.datasource

import org.apache.commons.dbcp.BasicDataSource
import org.dbunit.IDatabaseTester
import org.dbunit.JdbcDatabaseTester
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.h2.Driver
import org.h2.tools.RunScript

import javax.sql.DataSource
import java.nio.charset.Charset

class DBInitializer {
    static final String JDBC_DRIVER = Driver.class.getName()
    def url
    def username
    def password

    void createSchema(String propertyFilePath, String schemaFilePath) {
        Properties properties = new Properties()
        properties.load(getClass().getResourceAsStream(propertyFilePath))
        url = properties.getProperty("db.url")
        username = properties.getProperty("db.username")
        password = properties.getProperty("db.password")
        RunScript.execute(url, username, password, getClass().getResource(schemaFilePath).getFile(), Charset.defaultCharset(), false)
    }


    IDataSet readDataSet(String datasetFilePath) throws Exception {
        return new FlatXmlDataSetBuilder().build(new File(getClass().getResource(datasetFilePath).getFile()))
    }

    void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, url, username, password)
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT)
        databaseTester.setDataSet(dataSet)
        databaseTester.onSetup()
    }

    DataSource getDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url)
        basicDataSource.setUsername(username)
        basicDataSource.setPassword(password)
        return basicDataSource;
    }
}
