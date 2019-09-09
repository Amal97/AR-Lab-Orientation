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
                transparentEquipmentDrawable =  ContextCompat.getDrawable(this, R.drawable.waveformgenerator);
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
                objectImage.setImageResource(R.drawable.waveformgenerator2);
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
                Snackbar.make(interactiveLayout, "Power button: Turn the oscilloscope on and off", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff89001c":
                Snackbar.make(interactiveLayout, "Screen, options will be in the bottom of the screen and signals will be in the center", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffeb1c24":
                Snackbar.make(interactiveLayout, "Use this knob to scale signals horizontally", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff7f26":
                Snackbar.make(interactiveLayout, "Menu/Zoom: Zoom on a specific section of the signal, or set time mode.", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "<> Move signal view sideways", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffdeca6":
                Snackbar.make(interactiveLayout, "Run/Stop: Stop and run the signals at any point", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffef200":
                Snackbar.make(interactiveLayout, "Single: Run the signal once", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc4ff0d":
                Snackbar.make(interactiveLayout, "Use this to choose options", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff0ed145":
                Snackbar.make(interactiveLayout, "Cursors: Use this to enable the on-screen cursors.", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb83dba":
                Snackbar.make(interactiveLayout, "Acquire: How the waveform is shown in the oscilloscope, choose from normal, peak detect, averaging and high resolution", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Display: Options for the display, such as clearing, persisting and freezing", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff641d6b":
                Snackbar.make(interactiveLayout, "Trigger on a rising, falling or alternating edge", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff024000":
                Snackbar.make(interactiveLayout, "Mode coupling: Retain signals // TODO", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00a8f3":
                Snackbar.make(interactiveLayout, "Quick Measure: Use to measure signals in the wave", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb7aadf":
                Snackbar.make(interactiveLayout, "Level: Use to choose the trigger point", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff8cfffa":
                Snackbar.make(interactiveLayout, "Autoscale: Finds and displays all active channels, sets edge trigger mode on highest-numbered channel, " +
                        "sets vertical sensitivity on channels, time base to display ~1.8 period", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Save/Recall: 10 setups and traces can be saved and recalled using internal non-volatile memory.", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffeaec7":
                Snackbar.make(interactiveLayout, "Print: Print signal", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00fdba":
                Snackbar.make(interactiveLayout, "Utility: Press to use utilities such as I/O, File explorer, Language etc", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb2be96":
                Snackbar.make(interactiveLayout, "Pulse Width: Trigger when a positive- or negative-going pulse is less than, greater than, or " +
                        "within a specified range  on any of the source channels", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff0076":
                Snackbar.make(interactiveLayout, "Pattern: Trigger at the beginning of a pattern of high, low, and don't care levels and/or a rising " +
                        "or falling edge established across any of the channels, but only after a pattern has been established for a minimum of 2 nsec.", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffffff":
                Snackbar.make(interactiveLayout, "More: Additional trigger options", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff63709a":
                Snackbar.make(interactiveLayout, "Use to adjust vertical scaling", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffcd97eb":
                Snackbar.make(interactiveLayout, "1: Use to view signals from probe 1", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffa04475":
                Snackbar.make(interactiveLayout, "Math: Use for math calculations", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff43929f":
                Snackbar.make(interactiveLayout, "2: Use to view signals from probe 2", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffe05656":
                Snackbar.make(interactiveLayout, "Label: Use to label signals in the oscilloscope", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff601e1f":
                Snackbar.make(interactiveLayout, "Use to move the signal up and down", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc3c3c3":
                Snackbar.make(interactiveLayout, "Probes: Use to measure signals", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff694545":
                Snackbar.make(interactiveLayout, "External Trigger: Trigger the signal using an external tool", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff434379":
                Snackbar.make(interactiveLayout, "Probe compensation: Use to calibrate the probes and ensure that they are working properly", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffebe6c8":
                Snackbar.make(interactiveLayout, "USB: Plug your USB here to store patterns", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff07074d":
                Snackbar.make(interactiveLayout, "Intensity: Set the brightness of the signals", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb0ff70":
                Snackbar.make(interactiveLayout, "Power button: Use this to turn the oscilloscope on and off", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff576f21":
                Snackbar.make(interactiveLayout, "Use these buttons to choose between options shown in screen", Snackbar.LENGTH_LONG)
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
                Snackbar.make(interactiveLayout, "Screen: Use the screen to view measurements", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc3c3c3":
                Snackbar.make(interactiveLayout, "Power button: This is used to turn the multimeter on and off",
                        Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff585858":
                Snackbar.make(interactiveLayout, "DC V: Use this button to measure DC voltage, " +
                        "when pressed with a shift use it to measure the DC current", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff89001c":
                Snackbar.make(interactiveLayout, "AC V: Use this button measure AC voltage, " +
                        "when pressed with a shift use it to measure the the AC current", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffeb1c24":
                Snackbar.make(interactiveLayout, "2 ohms: This button is used to measure resistance", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff7f26":
                Snackbar.make(interactiveLayout, "Freq: Use to measure frequency. " +
                        "When used with shift use it to measure period", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "Cont: Use to test for continuity. Press with shift to check" +
                        "for diodes", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffdeca6":
                Snackbar.make(interactiveLayout, "Null: Use this to make null (relative) meaasurements. " +
                        "Use with shift to make decibel measurements.", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffef200":
                Snackbar.make(interactiveLayout, "Min/max: Use this to store the maximum and minimum readings " +
                        "during a series of measurements. " +
                        "Use with shift to make decibels with reference to milliwats measurements.", Snackbar.LENGTH_LONG)
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
                Snackbar.make(interactiveLayout, "˄: Use this button to increase the range and to disable autoranging. " +
                        "It can also be used with a shift to set the resolution to 4½ digits", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00a8f3":
                Snackbar.make(interactiveLayout, "˅: Use this button to decrease the range and to disable autoranging. " +
                        "It can also be used with a shift to set the resolution to 5½ digits ", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Auto/man: " +
                        "It can also be used with a shift to set the resolution to 4½ digits", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb83dba":
                Snackbar.make(interactiveLayout, "Single: Enables single trigger and triggers the multimeter. " +
                        "When pressed with shift, it toggles between auto-trigger and reading hold", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffeaec7":
                Snackbar.make(interactiveLayout, "Shift: Used to trigger alternate actions for buttons", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Measures using a reference voltage", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fff8981d":
                Snackbar.make(interactiveLayout, "Measures things using a signal voltage", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb7aadf":
                Snackbar.make(interactiveLayout, "Terminal: Choose weather to use front or rear terminals", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff601e1f":
                //TODO: Not sure what this is?
                Snackbar.make(interactiveLayout, "Fused", Snackbar.LENGTH_LONG)
                        .show();
                break;
        }

    }

    private void handlePowerSupply(String colorHex, LinearLayout interactiveLayout) {
        switch (colorHex) {
            case "ffffaec7":
                Snackbar.make(interactiveLayout, "Power button: Use this to turn the power supply on and off", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Slave Voltage: Adjusting output voltage when slave is at a constant voltage mode", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "Current: Adjusting output current when slave/master is at a constant current mode", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff0ed145":
                Snackbar.make(interactiveLayout, "Master Voltage: Adjusting output voltage when master is at a constant voltage mode", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffeb1c24":
                Snackbar.make(interactiveLayout, "Master and slave output, ground terminals. " +
                        "Switches in the center control whether the power supply is in series mode, parallel or independent.", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Screen: Shows the voltage and current of master and slave. Master is on the right hand side, " +
                        "and slave is on the left. Voltage is above and current is below.", Snackbar.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }
    }

    private void handleWaveformGenerator(String colorHex, LinearLayout interactiveLayout) {
        switch (colorHex) {
            case "ffffffff":
                Snackbar.make(interactiveLayout, "Waveforms: Choose what waveform you would like to generate", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc3c3c3":
                Snackbar.make(interactiveLayout, "Screen: View options and settings for the waveform generated", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff585858":
                Snackbar.make(interactiveLayout, "Parameters: Choose the parameters for the wave (Eg. Frequency, Amplitude, Offset, Phase)", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff89001c":
                Snackbar.make(interactiveLayout, "Units: CHoose units for your parameters", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffff7f26":
                Snackbar.make(interactiveLayout, "Sweep: Configure linear, logarithmic, list sweep signals", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffffca18":
                Snackbar.make(interactiveLayout, "Burst: Configure a burst signal", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffdeca6":
                Snackbar.make(interactiveLayout, "System: System operation options", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffef200":
                Snackbar.make(interactiveLayout, "Numbers: Select values", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffc4ff0d":
                Snackbar.make(interactiveLayout, "Knob: Use to increase/decrease values", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff0ed145":
                Snackbar.make(interactiveLayout, "<>: Use to toggle between digits", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff8cfffa":
                Snackbar.make(interactiveLayout, "Trigger: Set up trigger/sync setup", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff00a8f3":
                Snackbar.make(interactiveLayout, "Channel: Set up settings for the output", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ff3f47cc":
                Snackbar.make(interactiveLayout, "Sync: Signal to sync the waveform generated", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb83dba":
                Snackbar.make(interactiveLayout, "Output: Output for the waveform", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "fffeaec7":
                Snackbar.make(interactiveLayout, "Options: Use to select options on the screen", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb97b56":
                Snackbar.make(interactiveLayout, "Power button: Use to turn the waveform generator on and off", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffb7aadf":
                Snackbar.make(interactiveLayout, "USB: Use to store signals generated on USB", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case "ffeb1c24":
                Snackbar.make(interactiveLayout, "Modulate: Configure a modulated wave", Snackbar.LENGTH_LONG)
                        .show();
                break;
        }

    }

}
