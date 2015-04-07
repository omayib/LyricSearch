package com.lirik.lagu.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lirik.lagu.app.R;
import com.lirik.lagu.app.model.Lyric;
import com.lirik.lagu.app.model.LyricCandidate;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by omayib on 4/2/15.
 */
public class ListSearchResultAdapter extends ArrayAdapter<LyricCandidate> {
    private Context context;
    private LayoutInflater layoutInflater;
    private int layout;
    public ListSearchResultAdapter(Context context, int resource, List<LyricCandidate> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layout=resource;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LyricCandidate currentLyric=getItem(position);
        Holder holder;
        if(convertView==null){
            convertView=layoutInflater.inflate(layout,parent,false);
            holder=new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }

        holder.title.setText(currentLyric.getTitle());
        return convertView;
    }

    static class Holder{
        @InjectView(R.id.item_title)
        TextView title;

        public Holder(View view){
            ButterKnife.inject(this,view);
        }
    }
}
