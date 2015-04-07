package com.lirik.lagu.app.model;

import java.util.List;

/**
 * Created by omayib on 3/23/15.
 */
public interface Agent {
    void search(String keyword,Result<List<LyricCandidate>> callback);
    void topLyric(Result<List<Lyric>> callback);
    void lyric(String url,Result<Lyric> callback);
}
