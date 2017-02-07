package com.babcsany.templetripplanner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.babcsany.templetripplanner.R;
import com.babcsany.templetripplanner.parcels.Patron;

import java.util.List;

/**
 * Patron list adapter which can be used in RecyclerView
 */
public class PatronAdapter extends RecyclerView.Adapter<PatronAdapter.PatronViewHolder> {
    private final List<Patron> patrons;
    private IPatronClicks listeners;

    public interface IPatronClicks {
        void onPatronClick(View patronView, int layoutPosition);
    }

    static class PatronViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.patronImage) ImageView picture;
        @BindView(R.id.patronName) TextView name;
        @BindView(R.id.patronKind) TextView kind;
        IPatronClicks listeners;

        PatronViewHolder(View view, IPatronClicks patronClickListeners) {
            super(view);
            ButterKnife.bind(this, view);
            listeners = patronClickListeners;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != listeners) {
                listeners.onPatronClick(view, this.getAdapterPosition());
            }
        }

    }

    public PatronAdapter(List<Patron> patrons) {
        this.patrons = patrons;
    }

    public PatronAdapter(List<Patron> patrons, IPatronClicks patronClicksListeners) {
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
//        holder.picture.setImageDrawable(patron.getPicture());
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

    public List<Patron> getPatrons() {
        return patrons;
    }
}
