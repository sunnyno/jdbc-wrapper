package com.dzytsiuk.jdbcwrapper.parser;

import com.dzytsiuk.jdbcwrapper.entity.Query;

import java.util.*;

public class QueryParser {

    public Query parseQuery(String query, Map<String, Object> param) {
        String[] words = query.split("\\s");
        List<Object> paramList = new ArrayList<>();
        StringBuilder queryStringBuilder = new StringBuilder(query);
        Query parsedQuery = new Query();
        for (String word : words) {
            if (word.startsWith(":")) {
                int indexOfReplaceStart = queryStringBuilder.indexOf(word);
                queryStringBuilder.replace(indexOfReplaceStart, indexOfReplaceStart +word.length(), "?");
                Object parameter = param.get(word.substring(1));
                paramList.add(parameter);
            }
        }
        parsedQuery.setQuery(queryStringBuilder.toString());
        parsedQuery.setParams(paramList);
        return parsedQuery;
    }

}
