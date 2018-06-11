package ua.nure.queue_managment_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.nure.queue_managment_android.R;
import ua.nure.queue_managment_android.model.TimeSlotEntity;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private TimeSlotEntity[] timeSlots;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private TimeSlotEntity selectedSlot;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, TimeSlotEntity[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.timeSlots = data;
        this.selectedSlot = null;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.time_slot_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeSlotEntity currentSlot = timeSlots[position];
        holder.myTextView.setText(currentSlot.getStartTime());
        if (currentSlot.equals(selectedSlot)) {
            holder.myTextView.setBackgroundColor(holder.myTextView.getContext().getResources().getColor(R.color.selected_slot));
        }
        if (currentSlot.getClient() != null) {
            holder.myTextView.setBackgroundColor(holder.myTextView.getContext().getResources().getColor(R.color.occupied_slot));
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return timeSlots.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return timeSlots[id].getStartTime();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setSelectedSlot(TimeSlotEntity selectedSlot) {
        if (selectedSlot.getClient() == null) {
            this.selectedSlot = selectedSlot;
        }
    }

    public TimeSlotEntity getSelectedSlot() {
        return selectedSlot;
    }
}