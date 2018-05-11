package com.plexosysconsult.homemart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by senyer on 9/4/2016.
 */
public class AdapterSpinnerVariations extends ArrayAdapter<Item> {

    private Context context;
    private List<Item> variationList;

    public AdapterSpinnerVariations(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

        this.context = context;
        this.variationList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      //  return super.getView(position, convertView, parent);
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       // return super.getDropDownView(position, convertView, parent);

        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {

        return variationList.size();
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = LayoutInflater.from(context);

        View row=inflater.inflate(R.layout.spinner_row, parent, false);

        TextView option = (TextView) row.findViewById(R.id.tv_option);

        option.setText(variationList.get(position).getOptionUnit());



        return row;
    }
}

