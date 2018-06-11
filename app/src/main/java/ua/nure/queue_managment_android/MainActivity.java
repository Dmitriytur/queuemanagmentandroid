package ua.nure.queue_managment_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ua.nure.queue_managment_android.adapters.CompaniesAdapter;
import ua.nure.queue_managment_android.constants.Constants;
import ua.nure.queue_managment_android.model.CompanyEntity;

public class MainActivity extends AppCompatActivity {
    ObjectMapper objectMapper = new ObjectMapper();
    OkHttpClient client = new OkHttpClient();


    private CompaniesAdapter companiesAdapter;


    LoadCompaniesTask loadCompaniesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCompaniesTask = new LoadCompaniesTask();
        loadCompaniesTask.execute();


        List<CompanyEntity> companyEntities = new ArrayList<>();
        CompanyEntity entity = new CompanyEntity();
        entity.setName("Name");
        companyEntities.add(entity);

        companiesAdapter = new CompaniesAdapter(this, companyEntities);
        ListView listView = findViewById(R.id.companiesList);
        listView.setAdapter(companiesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CompanyActivity.class);
                intent.putExtra("company", companiesAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }



    @SuppressLint("StaticFieldLeak")
    class LoadCompaniesTask extends AsyncTask<Void, Void, List<CompanyEntity>> {


        @Override
        protected List<CompanyEntity> doInBackground(Void... params) {
            Request request = new Request.Builder()
                    .url(Constants.BASE_URL + "/companies")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                return objectMapper.readValue(responseString,
                        new TypeReference<List<CompanyEntity>>() {
                        });


            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<CompanyEntity> result) {
            super.onPostExecute(result);
            companiesAdapter.clear();
            companiesAdapter.addAll(result);
            companiesAdapter.notifyDataSetChanged();
        }
    }
}
