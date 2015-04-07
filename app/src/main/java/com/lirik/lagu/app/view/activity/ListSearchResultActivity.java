package com.lirik.lagu.app.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lirik.lagu.app.R;
import com.lirik.lagu.app.application.LyricApplication;
import com.lirik.lagu.app.model.Lyric;
import com.lirik.lagu.app.model.LyricCandidate;
import com.lirik.lagu.app.model.User;
import com.lirik.lagu.app.model.event.OnLyricLoadFailed;
import com.lirik.lagu.app.model.event.OnLyricLoaded;
import com.lirik.lagu.app.model.event.OnSearchingFailed;
import com.lirik.lagu.app.model.event.OnSearchingSucceeded;
import com.lirik.lagu.app.utilities.Logger;
import com.lirik.lagu.app.view.adapter.ListSearchResultAdapter;
import com.lirik.lagu.app.view.fragment.AdFragment;
import com.lirik.lagu.app.view.fragment.DialogAskToRate;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

import static com.lirik.lagu.app.R.layout;

/**
 * Created by omayib on 3/22/15.
 */
public class ListSearchResultActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.listView_result)
    ListView listViewResult;
    @InjectView(R.id.loading_spinner)
    View loadingView;
    View contentView;
    private ProgressDialog pd;

    private LyricApplication app;
    private User user;
    private ListSearchResultAdapter adapter;
    private List<LyricCandidate> listResult;

    private boolean contentLoaded=false;
    private int shortAnimationDuration;

    private ActionBar actionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.fragment_list_search_result);

        ButterKnife.inject(this);
        EventBus.getDefault().register(this);

        app = (LyricApplication)getApplication();
        user=app.getUser();
        listResult=user.getSearchResults();


        actionBar =getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name));
        actionBar.setSubtitle("searching...");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        pd=new ProgressDialog(this);
        pd.setMessage("please wait...");

        adapter=new ListSearchResultAdapter(this, layout.item_search_result,listResult);
        listViewResult.setAdapter(adapter);
        listViewResult.setOnItemClickListener(this);

        contentView=listViewResult;


        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        showContentOrLoadingIndicator(contentLoaded);

        new AdFragment();

        Intent intent=getIntent();
        if(intent!=null){
            String kwd=intent.getStringExtra("keyword");
            user.clearLyricsCandidate();
            user.search(kwd);
        }
    }
    private void showContentOrLoadingIndicator(boolean contentLoaded) {
        final View showView = contentLoaded ? contentView : loadingView;
        final View hideView = contentLoaded ? loadingView : contentView;

        showView.setAlpha(0f);
        showView.setVisibility(View.VISIBLE);

        showView.animate().alpha(1f).setDuration(shortAnimationDuration)
                .setListener(null);

        hideView.animate().alpha(0f).setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideView.setVisibility(View.GONE);
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LyricCandidate candidate= (LyricCandidate) parent.getAdapter().getItem(position);
        user.getLyricFrom(candidate.getUrl());
        progressDialogShow();
    }

    private void progressDialogShow() {
        if(!pd.isShowing()){
            pd.show();
        }
    }
    private void progressDialogDismiss(){
        if(pd.isShowing())
            pd.dismiss();
    }

    public void onEvent(OnLyricLoaded e){
        progressDialogDismiss();
        System.out.println("OnLyricLoaded");
        startActivity(new Intent(this,LyricActivity.class));
    }
    public void onEvent(OnLyricLoadFailed e){
        progressDialogDismiss();
        Toast.makeText(this,"failed to load,please try other result",Toast.LENGTH_SHORT).show();
    }
    public void onEvent(OnSearchingSucceeded e){
        contentLoaded= !contentLoaded;
        actionBar.setSubtitle("About "+adapter.getCount()+ " results");
        showContentOrLoadingIndicator(contentLoaded);
        adapter.notifyDataSetChanged();
    }
    public void onEvent(OnSearchingFailed e){
        contentLoaded= !contentLoaded;
        actionBar.setSubtitle("search failed");
        showContentOrLoadingIndicator(contentLoaded);
        Toast.makeText(this,"sorry, failed. please try again",Toast.LENGTH_SHORT).show();
    }
}
