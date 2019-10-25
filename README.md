# AR-Lab-Orientation

## To run:
### Requirements: 
* An Android smartphone with Android 7.0 (API level 24) or above
* Latest version of Google Play Store
* Latest version of ARCore

1. Copy "app-debug.apk" from: “AR-Lab-Orientation/app/build/outputs/apk/debug” to your Android smartphone.
2. Install the "app-debug.apk" by opening the apk on the smartphone.
3. Make sure the application have permission to use the camera.

## To develop: 
### Requirements: 
* Android Studio
* Smartphone/Emulator

### Adding more categories
In order to add more categories to the application, a model with the specific category needs to be trained. Once this model is trained, you will need to add the category onto the local database in the QuizDbHelper.java class. In the fillCategoriesTable function, add the new category onto the database. This also requires the onCreate function of the QuizDbHelper to be called again. Once this is done, edit the OptionsDialog.java class to add that specific category so that it can be handled. The corresponding activities related to the activity will also need to be changed. 
If an AR activity needs to be added for the specific category, a button needs to be created for the category like the other buttons. These buttons can be found in the layout folder (located at AR-Lab-Orientation/app/res/layout/). Once the button is created, go to the ImageARActivity.java class and augment the button once its been recognized through the onUpdate function. 
### Changing/Adding specific activities
#### Video Activity
The video activity is in the VideoActivity.java class. In order to change the video, go to the onInitializationSuccess function and change the id of the Youtube video for the given category. 
#### PdfActivity
The PDF activity is in PdfActivity.java class. However, the pdf is chosen in OptionsDialog.java class. In the function startPdf, the file name of the pdf for the given category is specified. In order to change this, add a pdf to the assets folder (located at AR-Lab-Orientation/app/assets/) and change the pdf name to the name of the pdf file.
#### Interactive Activity
The most complicated activity to format is the Interactive activity. This relies on colors being compared with touch. In order to add a new image to be interacted with, copy the image you would like to use, and format the image so that the specific things you would like to be interacted with are specific colors. Now, copy both images in the drawable folder (located at AR-Lab-Orientation/app/res/drawable/). Reference the colored in image at the setDrawableImage function, and the original image in the setInitialImage function corresponding to the specific object. Depending on whether this is a new category or an existing category, you may need to create a new function corresponding to handle the clicks. This function needs to be added in the handleColor function, with the specific toasts added to correspond with the hex color.
 


