package com.lirik.lagu.app.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.support.v7.*;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.lirik.lagu.app.R;
import com.lirik.lagu.app.application.LyricApplication;
import com.lirik.lagu.app.model.Lyric;
import com.lirik.lagu.app.model.User;
import com.lirik.lagu.app.view.fragment.AdFragment;
import com.lirik.lagu.app.view.fragment.DialogAskToRate;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by omayib on 4/2/15.
 */
public class LyricActivity extends ActionBarActivity{

    private LyricApplication app;
    private User user;

    @InjectView(R.id.textView_artist)
    TextView textViewArtist;
    @InjectView(R.id.textView_title) TextView textViewTitle;
    @InjectView(R.id.webView_content)
    WebView webViewContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);
        ButterKnife.inject(this);

        app= (LyricApplication) getApplication();
        user=app.getUser();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        new AdFragment();

        Lyric lyric=user.getLyric();
        textViewArtist.setText(lyric.getArtist());
        textViewTitle.setText(lyric.getTitle());
        webViewContent.loadData(lyric.getContent(),"text/html","utf-8");

    }

    @Override
    public void onBackPressed() {
        DialogAskToRate dialogAskToRate=new DialogAskToRate();
//        dialogAskToRate.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                finish();
//            }
//        });
        dialogAskToRate.show(getSupportFragmentManager(), "ask_to_rate");
    }
}


