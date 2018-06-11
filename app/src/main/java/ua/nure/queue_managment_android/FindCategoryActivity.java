package ua.nure.queue_managment_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.unnamed.b.atv.view.AndroidTreeView;

import ua.nure.queue_managment_android.model.CompanyEntity;
import ua.nure.queue_managment_android.util.AndroidTreeBuilder;

public class FindCategoryActivity extends AppCompatActivity {

    private CompanyEntity company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_category);

        Intent intent = getIntent();
        company = (CompanyEntity) intent.getSerializableExtra("company");


        AndroidTreeView tView = new AndroidTreeView(this, AndroidTreeBuilder.buildTree(company.getRootCategory(), this));
        LinearLayout containerView = findViewById(R.id.root_for_tree);
        containerView.addView(tView.getView());
    }
}
