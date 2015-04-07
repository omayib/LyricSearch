package com.lirik.lagu.app.view.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.lirik.lagu.app.R;
import com.lirik.lagu.app.application.LyricApplication;
import com.lirik.lagu.app.model.User;
import com.lirik.lagu.app.utilities.Logger;
import com.lirik.lagu.app.view.activity.ListSearchResultActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.lirik.lagu.app.R.layout;

/**
 * Created by omayib on 3/22/15.
 */
public class SearchFragment extends Fragment {
    @InjectView(R.id.search_input)
    EditText searchView;
    @InjectView(R.id.btn_search)
    Button searchBtn;
    private LyricApplication app;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new AdFragment();

        app = (LyricApplication)getActivity().getApplication();
        user=app.getUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_search,container,false);
        ButterKnife.inject(this,rootView);
//        searchView.setText("always bonjovi");
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kwd=searchView.getText().toString();
                if(!kwd.isEmpty()) {
                    startActivity(new Intent(getActivity(), ListSearchResultActivity.class).putExtra("keyword",kwd));
                }
            }
        });


        return rootView;
    }


}
