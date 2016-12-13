package com.babcsany.templetripplanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peter on 2016. 12. 12..
 */

public class PatronAdapter extends ArrayAdapter<Patron> {
    private final Context context;
    private final List<Patron> patrons;

    public PatronAdapter(Context context, List<Patron> patrons) {
        super(context, -1, patrons);
        this.context = context;
        this.patrons = patrons;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null == convertView ? inflater.inflate(R.layout.patron_row_layout, parent, false) : convertView;
        final Patron patron = patrons.get(position);
        TextView patronNameView = (TextView) rowView.findViewById(R.id.patronName);
        patronNameView.setText(patron.getName());
        ImageView patronImageView = (ImageView) rowView.findViewById(R.id.patronImage);
        patronImageView.setImageDrawable(patron.getPicture());
        if (null != patron.getKind()) {
            TextView patronKindView = (TextView) rowView.findViewById(R.id.patronKind);
            patronKindView.setText(patron.getKind().getResourceId());
        }
        return rowView;
    }

}
