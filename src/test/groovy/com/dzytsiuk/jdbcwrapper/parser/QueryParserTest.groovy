package com.dzytsiuk.jdbcwrapper.parser

import com.dzytsiuk.jdbcwrapper.entity.Query
import org.junit.Test

import static org.junit.Assert.assertEquals

class QueryParserTest {

    @Test
    void "parse query and extract parameters"() {
        def query = "SELECT id,name from Person where name like :name AND age > :age"
        def param = ["name": "zhenya", "age": "23"]
        def expectedParsedQuery = new Query(query: "SELECT id,name from Person where name like ? AND age > ?", params: ["zhenya", "23"])
        QueryParser queryParser = new QueryParser()
        def actualParsedQuery = queryParser.parseQuery(query, param)
        assertEquals(expectedParsedQuery, actualParsedQuery)
    }
}
