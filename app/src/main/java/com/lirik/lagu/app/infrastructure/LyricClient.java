package com.lirik.lagu.app.infrastructure;

import com.lirik.lagu.app.model.Agent;
import com.lirik.lagu.app.model.Lyric;
import com.lirik.lagu.app.model.LyricCandidate;
import com.lirik.lagu.app.model.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by omayib on 3/23/15.
 */
public class LyricClient implements Agent {
    private static String MOBILE_AGENT="Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.114 Mobile Safari/537.36";
    private static String FIREFOX_AGENT="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0";

    public LyricClient() {
        System.out.println("constructor LyricClient");
    }

    @Override
    public void search(final String keyword, final Result<List<LyricCandidate>> callback) {
        Observable.create(new Observable.OnSubscribe<Document>() {
            @Override
            public void call(Subscriber<? super Document> subscriber) {

                try {
                    System.out.println(LyricSource.FROM_ALLRESOURCE(keyword));
                    Document doc= Jsoup.connect(LyricSource.FROM_ALLRESOURCE(keyword)).get();
                    subscriber.onNext(doc);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        }).map(new Func1<Document, List<LyricCandidate>>() {
            @Override
            public List<LyricCandidate> call(Document document) {
                return LyricMap.ALLRESOURCE(document);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(new Action1<List<LyricCandidate>>() {
            @Override
            public void call(List<LyricCandidate> lyrics) {
                callback.onSuccess(lyrics);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void topLyric(Result<List<Lyric>> callback) {

    }

    @Override
    public void lyric(final String url, final Result<Lyric> callback) {
        Observable.create(new Observable.OnSubscribe<Document>() {
            @Override
            public void call(Subscriber<? super Document> subscriber) {
                Document doc;
                try {
//                    if(url.contains(LyricSource.KAPANLAGI)){
//                        doc= Jsoup.connect(url).userAgent(MOBILE_AGENT).get();
//                    }else{
//                        doc= Jsoup.connect(url).userAgent(FIREFOX_AGENT).get();
//                    }
                    doc= Jsoup.connect(url).userAgent(MOBILE_AGENT).get();
                    subscriber.onNext(doc);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<Document, Lyric>() {
            @Override
            public Lyric call(Document document) {


//                Elements elements=document.select("div#lyrics-body-text");

//                Lyric lyricd= new Lyric(elements.html(),"aa");
//                System.out.println("on success "+lyricd.toString());

                return LyricMap.toLyric(url,document);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Lyric>() {
                    @Override
                    public void call(Lyric lyric) {
                        System.out.println("on successvv :" + lyric.toString());
                        if(!lyric.getContent().isEmpty()) {
                            callback.onSuccess(lyric);
                        }else{
                            callback.onFailure();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        System.out.println("throwable "+throwable.toString());
                        callback.onFailure();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("on completed ");
                    }
                });
    }
}
