package com.lirik.lagu.app.model;

/**
 * Created by omayib on 4/3/15.
 */
public class LyricCandidate {
    private String url,title;

    public LyricCandidate(String url, String title) {
        this.url=url;
        String replaced = title.replace("|", "").replace("-","");
        if(replaced.contains("MetroLyrics")){
            replaced= replaced.replace("MetroLyrics","");
        }else if(replaced.contains("KapanLagi.com:")){
            replaced=replaced.replace("KapanLagi.com:","").replace("Lirik Lagu","");
        }
        this.title=replaced;

    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
