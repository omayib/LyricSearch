package com.lirik.lagu.app.model;

/**
 * Created by omayib on 3/22/15.
 */
public interface UserAction {
    void search(String keyword);
    void getTopLyric();
    void getHistory();
    void favourite(Lyric lyric);
    void getLyricFrom(String url);

}
