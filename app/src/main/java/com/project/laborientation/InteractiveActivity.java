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
        categoryName = intent.getStringExtra(CameraActivity.EXTRA_CATEGORY_Name).toLowerCase();
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
                transparentEquipmentDrawable = ContextCompat.getDrawable(this, R.drawable.oscilloscope1);
                break;
            case "power supply":
                transparentEquipmentDrawable =  ContextCompat.getDrawable(this, R.drawable.powersupply1);
                break;
            case "waveform generator":
                transparentEquipmentDrawable =  ContextCompat.getDrawable(this, R.drawable.waveformgenerator1);
                break;
            case "multimeter":
                transparentEquipmentDrawable =  ContextCompat.getDrawable(this, R.drawable.multimeter1);
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
                objectImage.setImageResource(R.drawable.oscilloscope0);
                break;
            case "power supply":
                objectImage.setImageResource(R.drawable.powersupply0);
                break;
            case "waveform generator":
                objectImage.setImageResource(R.drawable.waveformgenerator0);
                break;
            case "multimeter":
                objectImage.setImageResource(R.drawable.multimeter0);
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
        switch (colorHex) {
            case "ffafe9d1":
                Snackbar.make(interactiveLayout, "Power button", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff89001c":
                Snackbar.make(interactiveLayout, "Screen", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffeb1c24":
                Snackbar.make(interactiveLayout, "Scale Horizontal", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff7f26":
                Snackbar.make(interactiveLayout, "Menu/Zoom", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "Move Horizontal", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffdeca6":
                Snackbar.make(interactiveLayout, "Run/Stop", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffef200":
                Snackbar.make(interactiveLayout, "Single", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc4ff0d":
                Snackbar.make(interactiveLayout, "Knob turning around", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff0ed145":
                Snackbar.make(interactiveLayout, "Cursors", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb83dba":
                Snackbar.make(interactiveLayout, "Acquire", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Display", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff641d6b":
                Snackbar.make(interactiveLayout, "Edge trigger", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff024000":
                Snackbar.make(interactiveLayout, "Mode coupling", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00a8f3":
                Snackbar.make(interactiveLayout, "Quick Measure", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb7aadf":
                Snackbar.make(interactiveLayout, "Level", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff8cfffa":
                Snackbar.make(interactiveLayout, "Autoscale", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Save/Recall", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffeaec7":
                Snackbar.make(interactiveLayout, "Print", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00fdba":
                Snackbar.make(interactiveLayout, "Utility", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb2be96":
                Snackbar.make(interactiveLayout, "Pulsewidth", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff0076":
                Snackbar.make(interactiveLayout, "Pattern", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffffff":
                Snackbar.make(interactiveLayout, "More", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff63709a":
                Snackbar.make(interactiveLayout, "Green and yellow", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffcd97eb":
                Snackbar.make(interactiveLayout, "1", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffa04475":
                Snackbar.make(interactiveLayout, "Math", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff43929f":
                Snackbar.make(interactiveLayout, "2", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffe05656":
                Snackbar.make(interactiveLayout, "Label", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff601e1f":
                Snackbar.make(interactiveLayout, "Up and down", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc3c3c3":
                Snackbar.make(interactiveLayout, "Probes", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff694545":
                Snackbar.make(interactiveLayout, "External Trigger", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff434379":
                Snackbar.make(interactiveLayout, "Probe compensation", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffebe6c8":
                Snackbar.make(interactiveLayout, "USB", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff07074d":
                Snackbar.make(interactiveLayout, "Intensity", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb0ff70":
                Snackbar.make(interactiveLayout, "Power button", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff576f21":
                Snackbar.make(interactiveLayout, "Options", Snackbar.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }
        Log.e(TAG, colorHex);
    }

    private void handleMultimeter(String colorHex, LinearLayout interactiveLayout) {
        switch (colorHex) {
            case "ffffffff":
                Snackbar.make(interactiveLayout, "Screen", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc3c3c3":
                Snackbar.make(interactiveLayout, "Power", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff585858":
                Snackbar.make(interactiveLayout, "DC V", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff89001c":
                Snackbar.make(interactiveLayout, "AC V", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffeb1c24":
                Snackbar.make(interactiveLayout, "2 ohms", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff7f26":
                Snackbar.make(interactiveLayout, "Frequency", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "Cont", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffdeca6":
                Snackbar.make(interactiveLayout, "Null", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffef200":
                Snackbar.make(interactiveLayout, "Min/max", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc4ff0d":
                Snackbar.make(interactiveLayout, "<", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff0ed145":
                Snackbar.make(interactiveLayout, ">", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff8cfffa":
                Snackbar.make(interactiveLayout, "^", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00a8f3":
                Snackbar.make(interactiveLayout, "v", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Auto/man", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb83dba":
                Snackbar.make(interactiveLayout, "Single", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffeaec7":
                Snackbar.make(interactiveLayout, "Shift", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Hi/low", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fff8981d":
                Snackbar.make(interactiveLayout, "Probes", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb7aadf":
                Snackbar.make(interactiveLayout, "Terminal", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff601e1f":
                Snackbar.make(interactiveLayout, "Fused", Snackbar.LENGTH_LONG)
                        .show();
                break;
        }

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
        switch (colorHex) {
            case "ffffffff":
                Snackbar.make(interactiveLayout, "^", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc3c3c3":
                Snackbar.make(interactiveLayout, "v", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff585858":
                Snackbar.make(interactiveLayout, ">", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffe3bdd4":
                Snackbar.make(interactiveLayout, "<", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff576f21":
                Snackbar.make(interactiveLayout, "Probes?", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff89001c":
                Snackbar.make(interactiveLayout, "Screen", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff7f26":
                Snackbar.make(interactiveLayout, "Knob", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "Power", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffdeca6":
                Snackbar.make(interactiveLayout, "AM", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffef200":
                Snackbar.make(interactiveLayout, "FM", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc4ff0d":
                Snackbar.make(interactiveLayout, "FSK", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff0ed145":
                Snackbar.make(interactiveLayout, "Burst", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff8cfffa":
                Snackbar.make(interactiveLayout, "Sweep", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00a8f3":
                Snackbar.make(interactiveLayout, "Arb", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Enter", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb83dba":
                Snackbar.make(interactiveLayout, "Freq", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffeaec7":
                Snackbar.make(interactiveLayout, "Ampl", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Offset", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fff8981d":
                Snackbar.make(interactiveLayout, "Recall", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb7aadf":
                Snackbar.make(interactiveLayout, "Single", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffebe6c8":
                Snackbar.make(interactiveLayout, "Shift", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff601e1f":
                Snackbar.make(interactiveLayout, "Enter Number", Snackbar.LENGTH_LONG)
                        .show();
                break;
        }

    }

}
