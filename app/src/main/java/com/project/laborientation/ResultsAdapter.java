package com.project.laborientation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.laborientation.Quiz.Category;
import com.project.laborientation.Quiz.QuizDbHelper;

import java.util.HashMap;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {
    private List<Category> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public View view;
        public TextView name;
        public TextView score;
        private Context context;
        public MyViewHolder(View v) {
            super(v);
            view = v;
            name = v.findViewById(R.id.categoryName);
            score = v.findViewById(R.id.categoryScore);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context = view.getContext();
            Intent myIntent = new Intent(context, QuizActivity.class);
            Log.e("ResultsAdapter", name.getText().toString());
            myIntent.putExtra("extraCategoryName", name.getText().toString());
            myIntent.putExtra("extraCategoryID", getObjectId(name.getText().toString()));
            context.startActivity(myIntent);
        }

        private int getObjectId(String object) {
            HashMap<String, Integer> mapObjects = new HashMap<>();
            mapObjects.put("Oscilloscope", 1);
            mapObjects.put("Multimeter", 2);
            mapObjects.put("Power Supply", 3);
            mapObjects.put("Waveform Generator", 4);
            mapObjects.put("Phoebe", 5);
            return mapObjects.get(object);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResultsAdapter(List<Category> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResultsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.results_textview, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        int score = mDataset.get(position).getScore();
        if (score == 3) {
            holder.view.setBackgroundColor(Color.rgb(0,100,0));
        }
        holder.score.setText(String.format("%d/3",score));
        holder.name.setText(mDataset.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
