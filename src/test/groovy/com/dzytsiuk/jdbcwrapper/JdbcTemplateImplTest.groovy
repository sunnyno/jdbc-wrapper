package com.dzytsiuk.jdbcwrapper

import com.dzytsiuk.jdbcwrapper.datasource.DBInitializer
import com.dzytsiuk.jdbcwrapper.enity.Product
import com.dzytsiuk.jdbcwrapper.exception.MoreThanOneObjectFoundException
import com.dzytsiuk.jdbcwrapper.mapper.ProductRowMapper
import org.dbunit.dataset.IDataSet
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import java.time.LocalDateTime

import static groovy.test.GroovyAssert.shouldFail
import static org.junit.Assert.*

class JdbcTemplateImplTest {
    static final String PROPERTY_FILE_PATH = "/properties/test.application.properties"
    static DBInitializer dbInitializer = new DBInitializer()
    static final String SCHEMA_FILE_PATH = "/db/schema.sql"
    static final String DATASET_FILE_PATH = "/db/dataset/product-dataset.xml"

    @BeforeClass
    static void setUp() {
        dbInitializer.createSchema(PROPERTY_FILE_PATH, SCHEMA_FILE_PATH)
    }

    @Before
    void importDataSet() throws Exception {
        IDataSet dataSet = dbInitializer.readDataSet(DATASET_FILE_PATH)
        dbInitializer.cleanlyInsert(dataSet)
    }

    @Test
    void "test query with parameters map"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "SELECT * from Product where name like :name AND price > :price"
        def param = ["name": "cake%", "price": 100.00] as HashMap<String, Object>
        def actualResult = jdbcTemplate.query(query, new ProductRowMapper(), param)
        def expectedResult = [new Product(1, LocalDateTime.of(2018, 6, 12, 17, 52, 23),
                "cake", 100.5),
                              new Product(2, LocalDateTime.of(2018, 6, 12, 17, 52, 23),
                                      "cake2", 300.0)]
        expectedResult.each {
            assertNotNull(actualResult.find { it })
        }

    }

    @Test
    void "test query with vararg parameters"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "SELECT * from Product where name like ? AND price > ?"
        def actualResult = jdbcTemplate.query(query, new ProductRowMapper(), "cake%", "100.0")
        def expectedResult = [new Product(1, LocalDateTime.of(2018, 6, 12, 17, 52, 23),
                "cake", 100.5),
                              new Product(2, LocalDateTime.of(2018, 6, 12, 17, 52, 23),
                                      "cake2", 300.0)]
        expectedResult.each {
            assertNotNull(actualResult.find { it })
        }
    }

    @Test
    void "test query for one object with parameter map"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "SELECT * from Product where id = :id"
        def param = ["id": "1"]
        def actualResult = jdbcTemplate.queryForObject(query, new ProductRowMapper(), param)
        def expectedResult = new Product(1, LocalDateTime.of(2018, 6, 12, 17, 52, 23),
                "cake", 100.5)
        assertEquals(expectedResult, actualResult)

    }

    @Test
    void "test query for one object with parameter map with MoreThanOneObjectException"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "SELECT * from Product where name like :name"
        def param = ["name": "cake%"]
        shouldFail(MoreThanOneObjectFoundException.class, {
            jdbcTemplate.queryForObject(query, new ProductRowMapper(), param)
        })
    }

    @Test
    void "test query for one object with varargs"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "SELECT * from Product where id = ?"
        def actualResult = jdbcTemplate.queryForObject(query, new ProductRowMapper(), 1)
        def expectedResult = new Product(1, LocalDateTime.of(2018, 6, 12, 17, 52, 23),
                "cake", 100.5)
        assertEquals(expectedResult, actualResult)

    }

    @Test
    void "test query for one object with varargs with MoreThanOneObjectException"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "SELECT * from Product where name like ?"
        shouldFail(MoreThanOneObjectFoundException.class,
                { jdbcTemplate.queryForObject(query, new ProductRowMapper(), "cake%") })
    }

    @Test
    void "test update with parameter map"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "INSERT INTO Product(name, price, creation_date) VALUES ( :name , :price , :creation_date )"
        def param = ["name": "cake3", "price": 1.0, "creation_date": LocalDateTime.now()]
        def actualResult = jdbcTemplate.update(query, param)
        def expectedResult = 1
        assertEquals(expectedResult, actualResult)

    }

    @Test
    void "test update with vararg"() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbInitializer.getDataSource())
        def query = "INSERT INTO Product(name, price, creation_date) VALUES ( ?, ?, ?)"
        def actualResult = jdbcTemplate.update(query, "cake3", 1.0, LocalDateTime.now())
        def expectedResult = 1
        assertEquals(expectedResult, actualResult)
    }

}
