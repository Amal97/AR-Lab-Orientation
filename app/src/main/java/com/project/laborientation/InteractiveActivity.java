package com.project.laborientation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class InteractiveActivity extends AppCompatActivity {
    ImageView mEquipment;
    String categoryName;
    Drawable transparentEquipmentDrawable;
    private static final String TAG = "InteractiveActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive);
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(CameraActivity.EXTRA_CATEGORY_Name).toLowerCase();
        setInitialImage(categoryName);
        setDrawableImage(categoryName);
        mEquipment = findViewById(R.id.object_image);
        mEquipment.setOnTouchListener((View view, MotionEvent motionEvent) -> {
            int positionX = Math.round(motionEvent.getX());
            int positionY = Math.round(motionEvent.getY());
            int rgb = getColour(positionX, positionY);
            handleColor(Integer.toHexString(rgb), categoryName);
            return false;
        });
    }

    private int getColour( int x, int y)
    {
        Log.d(TAG, "Identifying color");
        LinearLayout mInteractiveLayout = findViewById(R.id.interactive_layout);
        transparentEquipmentDrawable.setBounds(0, 0, mInteractiveLayout.getWidth(), mInteractiveLayout.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(mInteractiveLayout.getWidth(), mInteractiveLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        transparentEquipmentDrawable.draw(canvas);
        return bitmap.getPixel(x, y);
    }

    private void setDrawableImage(String object) {
        switch (object) {
            case "oscilloscope":
                break;
            case "power supply":
                transparentEquipmentDrawable =  ContextCompat.getDrawable(this, R.drawable.best_transparent);
            case "waveform generator":
                break;
            case "multimeter":
                break;
            case "phoebe":
                //Do nothing
                break;
            default:
                break;
        }
    }
    private void setInitialImage(String object) {
        ImageView objectImage = findViewById(R.id.object_image);
        switch (object) {
            case "oscilloscope":
                break;
            case "power supply":
                objectImage.setImageResource(R.drawable.best);
                break;
            case "waveform generator":
                break;
            case "multimeter":
                break;
            case "phoebe":
                //Do nothing
                break;
            default:
                break;
        }
    }
    private void handleColor(String colorHex, String object) {
        LinearLayout interactiveLayout = findViewById(R.id.interactive_layout);
        switch (object) {
            case "oscilloscope":
                handleOscilloscope(colorHex, interactiveLayout);
                break;
            case "power supply":
                handlePowerSupply(colorHex, interactiveLayout);
                break;
            case "waveform generator":
                handleWaveformGenerator(colorHex, interactiveLayout);
                break;
            case "multimeter":
                handleMultimeter(colorHex, interactiveLayout);
                break;
            case "phoebe":
                //Do nothing
                break;
                default:
                    break;
        }

    }

    private void handleOscilloscope(String colorHex, LinearLayout interactiveLayout) {

    }

    private void handleMultimeter(String colorHex, LinearLayout interactiveLayout) {

    }

    private void handlePowerSupply(String colorHex, LinearLayout interactiveLayout) {
        switch (colorHex) {
            case "ffffaec7":
                Snackbar.make(interactiveLayout, "Power button", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Slave Voltage", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "Current", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff0ed145":
                Snackbar.make(interactiveLayout, "Master Voltagre", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffeb1c24":
                Snackbar.make(interactiveLayout, "Knobs", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Screen", Snackbar.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }
    }

    private void handleWaveformGenerator(String colorHex, LinearLayout interactiveLayout) {

    }

}
