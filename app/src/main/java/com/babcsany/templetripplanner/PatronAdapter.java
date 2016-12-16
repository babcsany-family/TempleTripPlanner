package com.babcsany.templetripplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Created by peter on 2016. 12. 12..
 */

public class PatronAdapter extends RecyclerView.Adapter<PatronAdapter.PatronViewHolder> {
    private final List<Patron> patrons;
    private PatronViewHolder.IPatronClicks listeners;

    public static class PatronViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView picture;
        public TextView name;
        public TextView kind;
        public IPatronClicks listeners;

        public PatronViewHolder(View view, IPatronClicks patronClickListeners) {
            super(view);
            picture = (ImageView) view.findViewById(R.id.patronImage);
            name = (TextView) view.findViewById(R.id.patronName);
            kind = (TextView) view.findViewById(R.id.patronKind);
            listeners = patronClickListeners;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != listeners) {
                listeners.onPatronClick(view, this.getAdapterPosition());
            }
        }

        public static interface IPatronClicks {
            public void onPatronClick(View patronView, int layoutPosition);
        }

    }

    public PatronAdapter(List<Patron> patrons) {
        this.patrons = patrons;
    }

    public PatronAdapter(List<Patron> patrons, PatronViewHolder.IPatronClicks patronClicksListeners) {
        this.patrons = patrons;
        this.listeners = patronClicksListeners;
    }

    @Override
    public PatronViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patron_row_layout, parent, false);
        return new PatronViewHolder(rowView, listeners);
    }

    @Override
    public void onBindViewHolder(PatronViewHolder holder, int position) {
        Patron patron = patrons.get(position);
        holder.picture.setImageDrawable(patron.getPicture());
        holder.name.setText(patron.getName());
        if (null != patron.getKind()) {
            holder.kind.setText(patron.getKind().getResourceId());
        }
    }

    @Override
    public int getItemCount() {
        return patrons.size();
    }

    public void remove(int position) {
        patrons.remove(position);
        notifyItemRemoved(position);
    }

    public Patron get(int position) {
        return patrons.get(position);
    }

    public void set(int position, Patron patron) {
        patrons.set(position, patron);
        notifyItemChanged(position);
    }

    public void add(Patron patron) {
        patrons.add(patron);
        notifyDataSetChanged();
    }
}
