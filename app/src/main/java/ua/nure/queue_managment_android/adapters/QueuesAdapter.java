package ua.nure.queue_managment_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.nure.queue_managment_android.R;
import ua.nure.queue_managment_android.model.QueueEntity;

public class QueuesAdapter extends ArrayAdapter<QueueEntity> {

    private final List<QueueEntity> allQueues;
    private final Context context;
    private final List<QueueEntity> values;


    public QueuesAdapter(@NonNull Context context, @NonNull List<QueueEntity> objects) {
        super(context, -1, objects);
        this.context = context;
        this.allQueues = objects;
        this.values = new ArrayList<>(allQueues);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.queue_row_layout, parent, false);
        if (values.size() == 0) {
            return rowView;
        }


        TextView queueName = rowView.findViewById(R.id.queue_name);
        TextView queue_start_time = rowView.findViewById(R.id.queue_name_start_time);
        TextView queue_end_time = rowView.findViewById(R.id.queue_name_end_time);


        QueueEntity queue = allQueues.get(position);
        queueName.setText(queue.getName());
        queue_start_time.setText(DateUtils.formatDateTime(context,
                queue.getStartTime(), DateUtils.FORMAT_SHOW_TIME));
        queue_end_time.setText(DateUtils.formatDateTime(context,
                queue.getEndTime(), DateUtils.FORMAT_SHOW_TIME));


        return rowView;
    }

}
