package com.dzytsiuk.jdbcwrapper.parser;

import com.dzytsiuk.jdbcwrapper.entity.Query;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class QueryParser {

    public Query parseQuery(String query, Map param) {
        String[] words = query.split("\\s");
        Queue<Object> parameterQueue = new LinkedList<>();
        Query parsedQuery = new Query();
        for (String word : words) {
            if (word.startsWith(":")) {
                query = query.replace(word, "?");
                Object parameter = param.get(word.substring(1));
                parameterQueue.add(parameter);
            }
        }
        parsedQuery.setQuery(query);
        parsedQuery.setParams(parameterQueue.toArray());
        return parsedQuery;
    }

}
