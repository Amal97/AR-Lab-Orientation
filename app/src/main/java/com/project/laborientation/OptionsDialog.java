package com.project.laborientation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;

import com.project.laborientation.CameraActivity;
import com.project.laborientation.Quiz.Category;
import com.project.laborientation.Quiz.QuizDbHelper;
import com.project.laborientation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OptionsDialog extends DialogFragment {
    private static final String TAG = "OptionsDialog";
    private String object;
    private int categoryID;
    private String categoryName;
    //TODO: This whole class needs refactoring
    //TODO look up what not null does?
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        object = bundle.getString("OBJECT");
        initializeVariables();
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        //TODO: options_array is static right now, options likely to change depending
        builder.setTitle(getTitle())
                .setItems(R.array.options_array, (DialogInterface dialog, int which) -> {
                        if (which == 0) {
                            startVideo();
                        } else if (which == 1) {
                            startQuiz();
                        } else {
                            startInteractive();
                        }
                    }
                );
        return builder.create();
    }

    private String getTitle() {
        HashMap<String, String> mapObjects = new HashMap<>();
        mapObjects.put("oscilloscope", "Oscilloscope");
        mapObjects.put("multimeter", "Multimeter");
        mapObjects.put("powersupply", "Power Supply");
        mapObjects.put("waveformgenerator", "Waveform Generator");
        mapObjects.put("phoebe", "Phoebe");
        return mapObjects.get(object);
    }

    private int getObjectId() {
        HashMap<String, Integer> mapObjects = new HashMap<>();
        mapObjects.put("oscilloscope", 1);
        mapObjects.put("multimeter", 2);
        mapObjects.put("powersupply", 3);
        mapObjects.put("waveformgenerator", 4);
        mapObjects.put("phoebe", 5);
        return mapObjects.get(object);
    }

    private void initializeVariables() {
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(getActivity());
        Category selectedCategory = dbHelper.getCategory(getObjectId());
        categoryID = selectedCategory.getId();
        categoryName = selectedCategory.getName();
        Log.i(TAG, categoryName);
    }

    private void startQuiz(){
        Intent myIntent = new Intent(getActivity(), QuizActivity.class);
        myIntent.putExtra(CameraActivity.EXTRA_CATEGORY_ID, categoryID);
        myIntent.putExtra(CameraActivity.EXTRA_CATEGORY_Name, categoryName);
        startActivityForResult(myIntent, 1);
    }

    private void startVideo() {
        Intent myIntent = new Intent(getActivity(), VideoActivity.class);
        myIntent.putExtra(CameraActivity.EXTRA_CATEGORY_ID, categoryID);
        myIntent.putExtra(CameraActivity.EXTRA_CATEGORY_Name, categoryName);
        startActivity(myIntent);
    }

    private void startInteractive() {
        Intent myIntent = new Intent(getActivity(), InteractiveActivity.class);
        myIntent.putExtra(CameraActivity.EXTRA_CATEGORY_ID, categoryID);
        myIntent.putExtra(CameraActivity.EXTRA_CATEGORY_Name, categoryName);
        startActivity(myIntent);
    }

}
