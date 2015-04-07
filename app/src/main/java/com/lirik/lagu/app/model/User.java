package com.lirik.lagu.app.model;

import com.lirik.lagu.app.model.event.OnLyricLoadFailed;
import com.lirik.lagu.app.model.event.OnLyricLoaded;
import com.lirik.lagu.app.model.event.OnSearchingFailed;
import com.lirik.lagu.app.model.event.OnSearchingSucceeded;
import com.lirik.lagu.app.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by omayib on 3/22/15.
 */
public class User implements UserAction{
    public List<LyricCandidate> searchResult;
    public List<Lyric> topLyric;
    public List<Lyric> history;
    public List<Lyric> favour;
    private Lyric lyric;
    private Agent userAgent;

    public User(Agent userAgent) {
        System.out.println("constructor user");
        searchResult=new ArrayList<>();
        topLyric=new ArrayList<>();
        history=new ArrayList<>();
        favour=new ArrayList<>();
        this.userAgent=userAgent;
    }


    @Override
    public void search(String keyword) {
        System.out.println("user search.... ");
        userAgent.search(keyword,new Result<List<LyricCandidate>>() {
            @Override
            public void onSuccess(List<LyricCandidate> result) {
                //publish result
                searchResult.clear();
                searchResult.addAll(result);
                EventBus.getDefault().post(new OnSearchingSucceeded());
            }

            @Override
            public void onFailure() {
                EventBus.getDefault().post(new OnSearchingFailed());

            }
        });
    }

    public void clearLyricsCandidate(){
        if(!searchResult.isEmpty())
            searchResult.clear();

    }
    public Lyric getLyric() {
        return lyric;
    }

    @Override
    public void getTopLyric() {

    }

    @Override
    public void getHistory() {

    }

    @Override
    public void favourite(Lyric lyric) {

    }

    @Override
    public void getLyricFrom(String url) {
        System.out.println("getting lyric.. from "+url);
        userAgent.lyric(url,new Result<Lyric>() {
            @Override
            public void onSuccess(Lyric result) {
                System.out.println("onSuccess");
                lyric=result;
                EventBus.getDefault().post(new OnLyricLoaded());
            }

            @Override
            public void onFailure() {
                System.out.println("onFailure");
                EventBus.getDefault().post(new OnLyricLoadFailed());
            }
        });
    }

    public List<LyricCandidate> getSearchResults() {
        return searchResult;
    }
}
