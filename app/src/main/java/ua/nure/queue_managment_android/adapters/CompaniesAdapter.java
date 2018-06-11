package ua.nure.queue_managment_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.nure.queue_managment_android.R;
import ua.nure.queue_managment_android.model.CompanyEntity;

public class CompaniesAdapter extends ArrayAdapter<CompanyEntity> {

    private final List<CompanyEntity> allCompanies;
    private final Context context;
    private final List<CompanyEntity> values;


    public CompaniesAdapter(@NonNull Context context, @NonNull List<CompanyEntity> objects) {
        super(context, -1, objects);
        this.context = context;
        this.allCompanies = objects;
        this.values = new ArrayList<>(allCompanies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.company_row_layout, parent, false);
        if (values.size() == 0) {
            return rowView;
        }
        TextView nameView = rowView.findViewById(R.id.companyName);
        nameView.setText(allCompanies.get(position).getName());
        return rowView;
    }



}
