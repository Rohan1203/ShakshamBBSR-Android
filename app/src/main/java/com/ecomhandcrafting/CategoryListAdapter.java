package com.ecomhandcrafting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ecomhandcrafting.model.Category;

import java.util.List;

public class CategoryListAdapter extends ArrayAdapter<Category> {

    private Context context;
    private List<Category> categorys;

    public CategoryListAdapter(Context context, List<Category> category) {
        super(context,R.layout.product_list_layout, category);
        this.context = context;
        this.categorys = categorys;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.product_list_layout, parent, false);
        Category category = categorys.get(position);
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        textViewName.setText(category.getCatName());


        return convertView;
    }
}
