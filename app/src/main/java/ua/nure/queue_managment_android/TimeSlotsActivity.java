package ua.nure.queue_managment_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ua.nure.queue_managment_android.adapters.MyRecyclerViewAdapter;
import ua.nure.queue_managment_android.model.QueueEntity;
import ua.nure.queue_managment_android.model.TimeSlotEntity;

public class TimeSlotsActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private QueueEntity queue;

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);


        Intent intent = getIntent();
        queue = (QueueEntity) intent.getSerializableExtra("queue");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 6;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, queue.getTimeSlots().toArray(new TimeSlotEntity[0]));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        adapter.setSelectedSlot(queue.getTimeSlots().get(position));
        adapter.notifyDataSetChanged();
    }
}
