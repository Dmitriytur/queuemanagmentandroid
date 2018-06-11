package ua.nure.queue_managment_android;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ua.nure.queue_managment_android.adapters.QueuesAdapter;
import ua.nure.queue_managment_android.constants.Constants;
import ua.nure.queue_managment_android.model.QueueEntity;

public class QueuesActivity extends AppCompatActivity {
    ObjectMapper objectMapper = new ObjectMapper();
    OkHttpClient client = new OkHttpClient();

    private String categoryId;

    TextView currentDateTime;
    Calendar dateAndTime = Calendar.getInstance();


    private QueuesAdapter queuesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queues);

        dateAndTime.set(Calendar.HOUR, 0);
        dateAndTime.set(Calendar.MINUTE, 0);
        dateAndTime.set(Calendar.SECOND, 0);
        dateAndTime.set(Calendar.MILLISECOND, 0);


        Intent intent = getIntent();
        categoryId = (String) intent.getSerializableExtra("categoryId");
        currentDateTime = findViewById(R.id.current_date);
        setInitialDateTime();


        List<QueueEntity> queueEntities = new ArrayList<>();
        QueueEntity entity = new QueueEntity();
        entity.setName("Name");
        entity.setStartTime(0L);
        entity.setEndTime(0L);
        entity.setDuration(0);
        queueEntities.add(entity);


        queuesAdapter = new QueuesAdapter(this, queueEntities);
        ListView listView = findViewById(R.id.queue_list);
        listView.setAdapter(queuesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QueuesActivity.this, TimeSlotsActivity.class);
                intent.putExtra("queue", queuesAdapter.getItem(position));
                startActivity(intent);
            }
        });


    }

    public void setDate(View v) {
        new DatePickerDialog(QueuesActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {
        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        new LoadQueuesTask().execute();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };


    @SuppressLint("StaticFieldLeak")
    class LoadQueuesTask extends AsyncTask<Void, Void, List<QueueEntity>> {


        @Override
        protected List<QueueEntity> doInBackground(Void... params) {
            Calendar startTime = (Calendar) dateAndTime.clone();
            startTime.set(Calendar.HOUR, 0);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.SECOND, 0);
            startTime.set(Calendar.MILLISECOND, 0);

            Calendar endTime = (Calendar) dateAndTime.clone();
            endTime.set(Calendar.HOUR, 23);
            endTime.set(Calendar.MINUTE, 59);
            endTime.set(Calendar.SECOND, 59);
            ;

            @SuppressLint("DefaultLocale") Request request = new Request.Builder()
                    .url(Constants.BASE_URL + "/queues" +
                            String.format("?categoryId=%s" +
                                    "&startDate=%d" +
                                    "&endDate=%d", categoryId, startTime.getTimeInMillis(), endTime.getTimeInMillis()))
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                List<QueueEntity> queueEntities = objectMapper.readValue(responseString,
                        new TypeReference<List<QueueEntity>>() {
                        });
                return queueEntities;


            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<QueueEntity> result) {
            super.onPostExecute(result);
            queuesAdapter.clear();
            queuesAdapter.addAll(result);
            queuesAdapter.notifyDataSetChanged();
        }
    }
}
