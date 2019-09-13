package com.project.laborientation.Quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.laborientation.Quiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 3;

    private static  QuizDbHelper instance;
    private SQLiteDatabase db;
    private int c1_counter;
    private int c2_counter;
    private int c3_counter;
    private int c4_counter;
    private int c5_counter;


    private QuizDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context){
        if(instance == null){
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        final String SQL_CREATE_TRACKER_TABLE = "CREATE TABLE " +
                QuizTrackerTable.TABLE_NAME + "( " +
                QuizTrackerTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizTrackerTable.COLUMN_NAME + " TEXT, " +
                QuizTrackerTable.TOTAL_QUESTIONS + " INTEGER, " +
                QuizTrackerTable.ANSWERED_CORRECTLY + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_TRACKER_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
        addTotalQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }


    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable(){
        Category c1 = new Category("Oscilloscope");
        addCategory(c1);
        Category c2 = new Category("Multimeter");
        addCategory(c2);
        Category c3 = new Category("Power Supply");
        addCategory(c3);
        Category c4 = new Category("Waveform Generator");
        addCategory(c4);
        Category c5 = new Category("Phoebe");
        addCategory(c5);
    }


    public void addTotalQuestions(){
        QuizTracker tracker1 = new QuizTracker(Category.OSCILLOSCOPE, "Oscilloscope", c1_counter);
        addQuestionTracker(tracker1);
        QuizTracker tracker2 = new QuizTracker(Category.MULTIMETER, "Multimeter", c2_counter);
        addQuestionTracker(tracker2);
        QuizTracker tracker3 = new QuizTracker(Category.POWER_SUPPLY, "Power Supply", c3_counter);
        addQuestionTracker(tracker3);
        QuizTracker tracker4 = new QuizTracker(Category.WAVEFORM_GENERATOR, "Waveform Generator", c4_counter);
        addQuestionTracker(tracker4);
    }

    private void addQuestionTracker(QuizTracker tracker){
        ContentValues cv = new ContentValues();
        cv.put(QuizTrackerTable.COLUMN_NAME, tracker.getName());
        cv.put(QuizTrackerTable.TOTAL_QUESTIONS, tracker.getTotalQuestions());
        cv.put(QuizTrackerTable.ANSWERED_CORRECTLY, tracker.getCorrectAnswered());

        db.insert(QuizTrackerTable.TABLE_NAME, null, cv);
    }

    public void addCorectAnswers(int categoryID, int correctAnswers){
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{Integer.toString(categoryID)};

        Cursor c = db.rawQuery("SELECT * FROM " + QuizTrackerTable.TABLE_NAME +
                " WHERE " + QuizTrackerTable._ID + " = ?", selectionArgs);

        if(c.moveToFirst()){
            do {
                int totalQuestions = c.getColumnIndex(QuizTrackerTable.TOTAL_QUESTIONS);

                ContentValues values = new ContentValues();
                values.put(QuizTrackerTable.ANSWERED_CORRECTLY, correctAnswers);

                db.update(QuizTrackerTable.TABLE_NAME, values, "_id = ?", new String[] {Integer.toString(categoryID)});

            } while (c.moveToNext());

        }
    }

    private void addCategory(Category category){
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable(){
        Question q1 = new Question("Typically oscilloscope represents ____", "current and time", "resistance and time", "voltage and time", 3,
                Category.OSCILLOSCOPE);
        addQuestion(q1);
        Question q2 = new Question("What display control(s) need to be adjusted on the oscilloscope in order to show fewer cycles of this signal on the screen, with a greater height (amplitude)? ",
                "The “timebase” control needs to be adjusted for fewer seconds per division, while the “vertical” control needs to be adjusted for fewer volts per division",
                "The “timebase” control needs to be adjusted for more seconds per division, while the “vertical” control needs to be adjusted for fewer volts per division",
                "The “timebase” control needs to be adjusted for fewer seconds per division, while the “horizontal” control needs to be adjusted for fewer volts per division", 1,
                Category.OSCILLOSCOPE);
        addQuestion(q2);
        Question q3 = new Question("What display control(s) need to be adjusted on the oscilloscope in order to show a normal-looking wave on the screen? ",
                "The “horizontal” control needs to be adjusted for a greater number of volts per division",
                "The “vertical” control needs to be adjusted for a greater number of volts per division.",
                "The “vertical” control needs to be adjusted for a lower number of volts per division.", 2,
                Category.OSCILLOSCOPE);
        addQuestion(q3);
        Question q4 = new Question("Digital multimeter is used for _____",
                "Measuring a.c. and d.c. current, voltage and resistance",
                "Measuring a.c. current and voltage",
                "Measuring d.c. current and resistance", 1,
                Category.MULTIMETER);
        addQuestion(q4);
        Question q5 = new Question("Quantities are digitised using _____",
                "D/A converter",
                "Amplifier",
                "A/D converter", 3,
                Category.MULTIMETER);
        addQuestion(q5);
        Question q6 = new Question("Current is converted to voltage ____",
                "Through a voltmeter",
                "Through a resistance",
                "Through an ammeter", 2,
                Category.MULTIMETER);
        addQuestion(q6);
        Question q7 = new Question("How to set the max current limit in a power supply?",
                "By turning the current knob",
                "You can't change the current limit",
                "Short the clips and then turn the knob",
                3,
                Category.POWER_SUPPLY);
        addQuestion(q7);
        Question q8 = new Question("How to put the power supply in parallel mode?",
                "Have the slave switch left and the master switch left",
                "Have the slave switch right and the master switch right",
                "Have the slave switch right and the master switch left ", 2,
                Category.POWER_SUPPLY);
        addQuestion(q8);
        Question q9 = new Question("What's the difference between Master and Slave voltage?",
                "There is no difference",
                "When power supply is in parallel or series the slave follows the master voltage",
                "The voltage is independent when the power supply is in series", 2,
                Category.POWER_SUPPLY);
        addQuestion(q9);
        Question q10 = new Question("Describe a square wave?",
                "A voltage that switches between two fixed voltage levels at regular intervals",
                "A wave in which the voltage exhibits symmetrical rise and fall times",
                "A type of sine wave that increases in frequency over some period of time", 1,
                Category.WAVEFORM_GENERATOR);
        addQuestion(q10);
        Question q11 = new Question("What is Rise Time?",
                "Amount of time required for a pulse edge to make a transition to a state opposite its current level; in the case of rise time, from low level to high level and, in the case of fall time, from high level to low level",
                "The difference in timing between two otherwise similar signals",
                "The amount of time it takes a wave to complete one cycle", 1,
                Category.WAVEFORM_GENERATOR);
        addQuestion(q11);
        Question q12 = new Question("What is period?",
                "The number of times a signal repeats in one second",
                "The amount of time it takes a wave to complete one cycle",
                "The amount of time that passes from the beginning of a cycle to the beginning of the next cycle", 2,
                Category.WAVEFORM_GENERATOR);
        addQuestion(q12);
    }

    private void addQuestion( Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_ANSWER_NR , question.getAnswerNr());
        cv.put(QuestionTable.COLUMN_CATEGORY_ID , question.getCategoryID());
        db.insert(QuestionTable.TABLE_NAME, null, cv);

        if(question.getCategoryID() == 1){
            c1_counter++;
        }
        if(question.getCategoryID() == 2){
            c2_counter++;
        }
        if(question.getCategoryID() == 3){
            c3_counter++;
        }
        if(question.getCategoryID() == 4){
            c4_counter++;
        }
    }

    public Category getCategory(int id){
        String[] stringId = new String[]{Integer.toString(id)};
        Category category = new Category();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME + " WHERE " +
                CategoriesTable._ID + " = ?", stringId);

        if(c.moveToFirst()){
            category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
            category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
        }
        c.close();
        return category;
    }

    public List<Category> getAllCategories(){
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if(c.moveToFirst()){
            do{
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while(c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public List<Category> getCategoryScores() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM quiz_completed", null);

        if(c.moveToFirst()){
            do{
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                category.setScore(c.getInt(c.getColumnIndex("correct_answers")));
                categoryList.add(category);
            } while(c.moveToNext());
        }

        c.close();
        return categoryList;

    }

    public ArrayList<Question> getQuestions(int categoryID){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{Integer.toString(categoryID)};

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME +
                " WHERE " + QuestionTable.COLUMN_CATEGORY_ID + " = ?", selectionArgs);

        if(c.moveToFirst()){
            do{
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }


}
