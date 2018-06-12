package ua.nure.queue_managment_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import ua.nure.queue_managment_android.model.CompanyEntity;

public class CompanyActivity extends AppCompatActivity {

    private CompanyEntity company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Intent intent = getIntent();
        company = (CompanyEntity) intent.getSerializableExtra("company");


        TextView textName = findViewById(R.id.company_details_name);
        textName.setText(company.getName());

        WebView webDescription = findViewById(R.id.company_description);
        webDescription.loadData(company.getDescription(), "text/html", "UTF-8");



    }

    public void onTreeButtonClick(View view) {
        Intent intent = new Intent(this, FindCategoryActivity.class);
        intent.putExtra("company", company);
        startActivity(intent);
    }

    private class CustomImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {
            byte[] decodedString = Base64.decode(source, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return new BitmapDrawable(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }
}
