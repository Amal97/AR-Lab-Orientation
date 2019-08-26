package com.project.laborientation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
//import android.transition.Scene;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.NotYetAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.rendering.ViewSizer;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.project.laborientation.CustomArFragment;
import com.project.laborientation.R;
import com.project.laborientation.Util.Classifier;
import com.project.laborientation.Util.ImageUtils;
import com.project.laborientation.Util.MSCognitiveServicesClassifier;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;


    public class ImageArActivity extends AppCompatActivity implements Scene.OnUpdateListener{

        private MSCognitiveServicesClassifier classifier;
        private CustomArFragment arFragment;
        protected boolean computing = true;
        protected byte[][] yuvBytes=new byte[3][];
        protected int yRowStride;
        protected int previewWidth = 0;
        protected int previewHeight = 0;
        private int[] rgbBytes = null;
        private ImageReader imageReader;
        protected Bitmap rgbFrameBitmap = null;
        private static final int MINIMUM_PREVIEW_SIZE = 320;
        private String cameraId;
        private int sensorOrientation;
        private Image image;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_image_ar);

            arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
            arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdate);
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            classifier = new MSCognitiveServicesClassifier(ImageArActivity.this);
            try {
                cameraId = manager.getCameraIdList()[0];
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                final Size inputSize = new Size(640, 480);
                Size previewSize =
                        chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                                inputSize.getWidth(),
                                inputSize.getHeight());
                previewWidth = previewSize.getWidth();
                previewHeight = previewSize.getHeight();

                rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);
                yuvBytes = new byte[3][];
                sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            } catch (CameraAccessException e) {
                Log.e("TestActivity", e.getMessage());
            }

        }

        public void setupDatabase(Config config, Session session){
            Bitmap foxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fox);
            Bitmap waveformBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waveform);
            Bitmap socketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.socket);

            AugmentedImageDatabase aid = new AugmentedImageDatabase(session);
            aid.addImage("fox", foxBitmap);
            aid.addImage("waveform", waveformBitmap);
            aid.addImage("socket", socketBitmap);

            config.setAugmentedImageDatabase(aid);
        }





        @Override
        public void onUpdate(FrameTime frameTime) {
            Frame frame = arFragment.getArSceneView().getArFrame();
            Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);

            image = null;
            //We need wait until we have some size from onPreviewSizeChosen
            if (previewWidth == 0 || previewHeight == 0) {
                return;
            }

            rgbBytes = new int[previewWidth * previewHeight];

            try {
                image = frame.acquireCameraImage();

                if (image == null) {
                    Log.e("ImageArActivity", "No image");
                    return;
                }

                if (!computing) {
                    image.close();
                    return;
                }
                final Image.Plane[] planes = image.getPlanes();
                fillBytes(planes, yuvBytes);
                yRowStride = planes[0].getRowStride();
                final int uvRowStride = planes[1].getRowStride();
                final int uvPixelStride = planes[1].getPixelStride();

                ImageUtils.convertYUV420ToARGB8888(
                        yuvBytes[0],
                        yuvBytes[1],
                        yuvBytes[2],
                        previewWidth,
                        previewHeight,
                        yRowStride,
                        uvRowStride,
                        uvPixelStride,
                        rgbBytes);

                image.close();
                rgbFrameBitmap.setPixels(rgbBytes, 0, previewWidth, 0, 0, previewWidth, previewHeight);


                Classifier.Recognition r = classifier.classifyImage(rgbFrameBitmap, getOrientation());

                Log.e("ImageArActivity", r.getTitle());
                Log.e("ImageArActivity", r.getId());

                if (r.getConfidence() > 0.7) {
                    images.size();
//                    if(images. isEmpty()){
//                        Log.e("ImageArActivity", "im empty");
//                    }
//                    else{
//                        Log.e("ImageArActivity", "NOT empty");
//
//                    }
                    //computing = false;
                }
            } catch (final Exception e) {
                Log.e("ImageArActivity", e.toString());
                if (image != null) {
                    image.close();
                }
            }





            //    try (Image i = frame.acquireCameraImage()) {
            //    FirebaseVisionImage firebaseImage = FirebaseVisionImage.fromMediaImage(i, 0);
            //   labelObject(firebaseImage);


//        } catch (NotYetAvailableException e) {
//            e.printStackTrace();
//        }


//            for(AugmentedImage image : images){
//                if(image.getTrackingState() == TrackingState.TRACKING){
//                    if(image.getName().equals("fox")){
//                        Anchor anchor = image.createAnchor(image.getCenterPose());
//                        createModel(anchor, "fox");
//                    }
//                    if(image.getName().equals("waveform")){
//                        Anchor anchor = image.createAnchor(image.getCenterPose());
//                        createModel(anchor, "waveform");
//                    }
//                    if(image.getName().equals("socket")){
//                        Anchor anchor = image.createAnchor(image.getCenterPose());
//                        createModel(anchor, "socket");
//                    }
//
//                }
//            }
        }

        String oldObject;

//        void labelObject(FirebaseVisionImage image) {
//            FirebaseLocalModel localModel = new FirebaseLocalModel.Builder("lab_model")
//                    .setAssetFilePath("data/manifest.json")
//                    .build();
//            FirebaseModelManager.getInstance().registerLocalModel(localModel);
//            FirebaseVisionOnDeviceAutoMLImageLabelerOptions labelerOptions =
//                    new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder()
//                            .setLocalModelName("lab_model")
//                            .setConfidenceThreshold(0)  // Evaluate your model in the Firebase console
//                            .build();
//            try {
//                FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(labelerOptions);
//                labeler.processImage(image)
//                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
//                            @Override
//                            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
//                                String currentObject = labels.get(0).getText();
//                                if((oldObject == null) || (oldObject != currentObject)) {
//                                    oldObject = currentObject;
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("OBJECT", currentObject);
//                                    DialogFragment optionFragment = new OptionsDialog();
//                                    optionFragment.setArguments(bundle);
//                                    optionFragment.show(getSupportFragmentManager(), "optionsDialog");
//
//                                    Toast.makeText(ImageArActivity.this, "FOUND", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(ImageArActivity.this, "Unable to detect object", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            } catch (Exception e) {
//            }
//        }

        protected void fillBytes(final Image.Plane[] planes, final byte[][] yuvBytes) {
            // Because of the variable row stride it's not possible to know in
            // advance the actual necessary dimensions of the yuv planes.
            for (int i = 0; i < planes.length; ++i) {
                final ByteBuffer buffer = planes[i].getBuffer();
                if (yuvBytes[i] == null) {
                    yuvBytes[i] = new byte[buffer.capacity()];
                }
                buffer.get(yuvBytes[i]);
            }
        }

        private void createModel(Anchor anchor, String object) {


            if(object == "fox") {
                ViewRenderable.builder()
                        .setView(this, R.layout.ar_text)
                        .build()
                        .thenAccept(viewRenderable -> placeModel(viewRenderable, anchor)).exceptionally(throwable -> null);
            }

            if(object == "waveform") {
                ViewRenderable.builder()
                        .setView(this, R.layout.ar_text)
                        .build()
                        .thenAccept(viewRenderable -> placeModel(viewRenderable, anchor)).exceptionally(throwable -> null);
            }
            if(object == "socket") {
                ViewRenderable.builder()
                        .setView(this, R.layout.ar_text)
                        .build()
                        .thenAccept(viewRenderable -> placeModel(viewRenderable, anchor)).exceptionally(throwable -> null);
            }

        }

        private Size chooseOptimalSize(final Size[] choices, final int width, final int height) {
            final int minSize = Math.max(Math.min(width, height), MINIMUM_PREVIEW_SIZE);
            final Size desiredSize = new Size(width, height);

            // Collect the supported resolutions that are at least as big as the preview Surface
            boolean exactSizeFound = false;
            final List<Size> bigEnough = new ArrayList<Size>();
            final List<Size> tooSmall = new ArrayList<Size>();
            for (final Size option : choices) {
                if (option.equals(desiredSize)) {
                    // Set the size but don't return yet so that remaining sizes will still be logged.
                    exactSizeFound = true;
                }

                if (option.getHeight() >= minSize && option.getWidth() >= minSize) {
                    bigEnough.add(option);
                } else {
                    tooSmall.add(option);
                }
            }

            if (exactSizeFound) {
                return desiredSize;
            }

            // Pick the smallest of those, assuming we found any
            if (bigEnough.size() > 0) {
                final Size chosenSize = Collections.min(bigEnough, new CompareSizesByArea());
                return chosenSize;
            } else {
                return choices[0];
            }
        }


        private void placeModel(ViewRenderable modelRenderable, Anchor anchor) {

            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());

            //set rotation in direction (x,y,z) in degrees 90
            node.setLocalRotation(Quaternion.axisAngle(new Vector3(-1, 0, 0), 90f));


            node.setParent(anchorNode);
            node.setRenderable(modelRenderable);

            node.select();

//        anchorNode.setLocalRotation(Quaternion.axisAngle(new Vector3 (1, 0, 0), 90f));
//
//        anchorNode.setRenderable(modelRenderable);
//        arFragment.getArSceneView().getScene().addChild(anchorNode);

        }
        class CompareSizesByArea implements Comparator<Size> {
            @Override
            public int compare(final Size lhs, final Size rhs) {
                // We cast here to ensure the multiplications won't overflow
                return Long.signum(
                        (long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
            }
        }
        private int getOrientation() {
            return getWindowManager().getDefaultDisplay().getRotation() + sensorOrientation;
        }
    }

