package com.babcsany.templetripplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peter on 2016. 12. 12..
 */

public class PatronAdapter extends ArrayAdapter<PatronAdapter.Patron> {
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
        View rowView = inflater.inflate(R.layout.patron_row_layout, parent, false);
        TextView patronNameView = (TextView) rowView.findViewById(R.id.patronName);
        patronNameView.setText(patrons.get(position).getName());
        return rowView;
    }

    public enum PatronKind {
        CHILD, ADULT, TEMPLE_WORKER
    }

    public class Patron {
        private String name;
        private PatronAdapter.PatronKind kind;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PatronKind getKind() {
            return kind;
        }

        public void setKind(PatronKind kind) {
            this.kind = kind;
        }
    }
}
