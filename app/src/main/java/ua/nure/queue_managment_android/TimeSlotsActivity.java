package ua.nure.queue_managment_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ua.nure.queue_managment_android.adapters.MyRecyclerViewAdapter;
import ua.nure.queue_managment_android.constants.Constants;
import ua.nure.queue_managment_android.model.QueueEntity;
import ua.nure.queue_managment_android.model.TimeSlotEntity;

public class TimeSlotsActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    ObjectMapper objectMapper = new ObjectMapper();
    OkHttpClient client = new OkHttpClient();

    private QueueEntity queue;

    MyRecyclerViewAdapter adapter;

    private EditText slotPhone;

    private EditText slotDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);


        slotPhone = findViewById(R.id.slot_phone);
        slotDetails = findViewById(R.id.slot_details);


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

    public void onEnrollClicked(View view) {
        new EnrollToQueueTask().execute();
    }


    @SuppressLint("StaticFieldLeak")
    class EnrollToQueueTask extends AsyncTask<Void, Void, Integer> {


        @Override
        protected Integer doInBackground(Void... params) {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("phone", slotPhone.getText().toString());
            requestBody.put("details", slotDetails.getText().toString());


            @SuppressLint("DefaultLocale") Request request = null;
            try {
                request = new Request.Builder()
                        .url(Constants.BASE_URL + "/time-slots/" + adapter.getSelectedSlot().getId())
                        .put(RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(requestBody)))
                        .build();
                Response response = client.newCall(request).execute();
                return response.code();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 500;
        }

        @SuppressLint("ShowToast")
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 200) {
                Toast.makeText(TimeSlotsActivity.this, "You successfully enrolled to queue!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TimeSlotsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
