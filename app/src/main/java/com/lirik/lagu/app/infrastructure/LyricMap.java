package com.lirik.lagu.app.infrastructure;

import com.lirik.lagu.app.model.Lyric;
import com.lirik.lagu.app.model.LyricCandidate;
import com.lirik.lagu.app.utilities.Logger;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omayib on 3/22/15.
 */
public class LyricMap {
    public static Lyric toLyric(String url,Document result){
        System.out.println("maping...");
        if(url.contains(LyricSource.KAPANLAGI)){
            Elements elements=result.select("div.deskrip-img");
            System.out.println("element "+elements.html());
            return new Lyric(elements.html(),url);
        }else if(url.contains(LyricSource.METROLYRIC)){
            Elements elements=result.select("p.verse");
            return new Lyric(elements.html(),url);
        }
        return  null;
    }

    public static List<LyricCandidate> ALLRESOURCE(Document result){
        List<LyricCandidate> lyrics=new ArrayList<>();
        Elements r=result.select("h3.r>a");
        for(Element e:r){
            String title=e.text();
            if(title.contains("Lagu Indonesia Abjad")){
                continue;
            }
            String src=e.attr("abs:href");
            lyrics.add(new LyricCandidate(src,title));
        }

        return lyrics;
    }

}
