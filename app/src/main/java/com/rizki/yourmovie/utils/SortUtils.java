package com.rizki.yourmovie.utils;

import androidx.sqlite.db.SimpleSQLiteQuery;

public class SortUtils {
    public static final String RATING_TERTINGGI = "Best";
    public static final String RATING_TERENDAH = "worst";
    public static SimpleSQLiteQuery getSortedQuery(String filter){
        StringBuilder simpleQuery = new StringBuilder().append("SELECT * FROM movie_entities ");
        if (filter.equals(RATING_TERTINGGI)){
            simpleQuery.append("ORDER BY voteAverage DESC");
        }else if (filter.equals(RATING_TERENDAH)){
            simpleQuery.append("ORDER BY voteAverage ASC");
        }
        return new SimpleSQLiteQuery(simpleQuery.toString());
    }
}
