package com.lirik.lagu.app.infrastructure;

/**
 * Created by omayib on 3/22/15.
 */
public class LyricSource {
    private static String GOOGLE="https://www.google.com/search?q=";
    public static String METROLYRIC="metrolyrics.com";
    public static String KAPANLAGI="lirik.kapanlagi.com";
    public static String AZLYRIC="azlyrics.com";
    public static String FROM_ALLRESOURCE(String keyword){
        String replaced=keyword.replace(" ","+");
        return GOOGLE+replaced+"+site:"+METROLYRIC +"+OR+site:"+KAPANLAGI;
    }
    public static String FROM_METROLYRIC(String keyword){
        String replaced=keyword.replace(" ","+");
        return GOOGLE+replaced+"+site:"+METROLYRIC;
    }
    public static String FROM_KAPANLAGI(String keyword){
        String replaced=keyword.replace(" ","+");
        return GOOGLE+replaced+"+site:"+KAPANLAGI;
    }
    public static String FROM_AZLYRIC(String keyword){
        String replaced=keyword.replace(" ","+");
        return GOOGLE+replaced+"+site:"+AZLYRIC;
    }
}
