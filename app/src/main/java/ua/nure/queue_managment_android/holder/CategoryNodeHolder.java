package ua.nure.queue_managment_android.holder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

import ua.nure.queue_managment_android.QueuesActivity;
import ua.nure.queue_managment_android.R;
import ua.nure.queue_managment_android.model.CategoryEntity;


public class CategoryNodeHolder extends TreeNode.BaseNodeViewHolder<CategoryEntity> {

    public CategoryNodeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, final CategoryEntity value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_profile_node, null, false);



        TextView tvValue = view.findViewById(R.id.node_value);
        ImageView arrowDown =  view.findViewById(R.id.tree_arrow_down);
        ImageView leafArrow = view.findViewById(R.id.leaf_arrow);

        tvValue.setText(value.getValue());
        if (value.getOptions() == null || value.getOptions().size() == 0) {
            arrowDown.setVisibility(View.INVISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, QueuesActivity.class);
                    intent.putExtra("categoryId", value.getId());
                    context.startActivity(intent);
                }
            });
        } else {
            leafArrow.setVisibility(View.INVISIBLE);

        }

        return view;
    }


}