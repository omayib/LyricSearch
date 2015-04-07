package com.lirik.lagu.app.model;

/**
 * Created by omayib on 3/22/15.
 */
public class Lyric {
    public int idLyric;
    public String title, artist, content, url;

    public Lyric(String content, String url) {
        this.content = content;
        this.url = url;
        if(url.contains("kapanlagi")){
            String[] splited=url.split("/");
            this.title=splited[splited.length-1].replace("_"," ");
            this.artist =splited[splited.length-2].replace("_"," ");
        }else if(url.contains("metrolyrics")){
            String a=url.substring(url.lastIndexOf("/")+1).replace(".html","");
            System.out.println("a "+a);
            String[] splitedByLyrics=a.split("lyrics");
            this.artist =splitedByLyrics[1].replace("-"," ");
            this.title=splitedByLyrics[0].replace("-"," ");
        }
    }


    public void setIdLyric(int idLyric) {
        this.idLyric = idLyric;
    }

    public int getIdLyric() {
        return idLyric;
    }

    public String getTitle() {

        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "idLyric=" + idLyric +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
