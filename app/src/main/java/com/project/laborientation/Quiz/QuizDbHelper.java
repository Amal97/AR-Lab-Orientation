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
        QuizTracker tracker5 = new QuizTracker(Category.PHOEBE, "Phoebe", c5_counter);
        addQuestionTracker(tracker5);
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
                if(totalQuestions == correctAnswers){
                    ContentValues values = new ContentValues();
                    values.put(QuizTrackerTable.ANSWERED_CORRECTLY, correctAnswers);

                    db.update(QuizTrackerTable.TABLE_NAME, values, "_id = ?", new String[] {Integer.toString(categoryID)});
                }

            } while (c.moveToNext());

        }
    }

    private void addCategory(Category category){
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable(){
        Question q1 = new Question("Oscilloscope, A is correct", "A", "B", "C", 1,
                Category.OSCILLOSCOPE);
        addQuestion(q1);
        Question q2 = new Question("Oscilloscope, B is correct", "A", "B", "C", 2,
                Category.OSCILLOSCOPE);
        addQuestion(q2);
        Question q3 = new Question("MULTIMETER, C is correct", "A", "B", "C", 3,
                Category.MULTIMETER);
        addQuestion(q3);
        Question q4 = new Question("MULTIMETER, A is correct", "A", "B", "C", 1,
                Category.MULTIMETER);
        addQuestion(q4);
        Question q5 = new Question("POWER_SUPPLY, B is correct", "A", "B", "C", 2,
                Category.POWER_SUPPLY);
        addQuestion(q5);
        Question q6 = new Question("POWER_SUPPLY, C is correct", "A", "B", "C", 3,
                Category.POWER_SUPPLY);
        addQuestion(q6);
        Question q7 = new Question("WAVEFORM_GENERATOR, A is correct", "A", "B", "C", 1,
                Category.WAVEFORM_GENERATOR);
        addQuestion(q7);
        Question q8 = new Question("WAVEFORM_GENERATOR, B is correct", "A", "B", "C", 2,
                Category.WAVEFORM_GENERATOR);
        addQuestion(q8);
        Question q9 = new Question("When was Phoebe born?", "20/05/2011", "20/02/2011", "02/20/2011", 2,
                Category.PHOEBE);
        addQuestion(q9);
        Question q10 = new Question("What breed is Phoebe?", "Beagle", "Jack Russell", "Fox Terrier", 2,
                Category.PHOEBE);
        addQuestion(q10);
        Question q11 = new Question("Which of these tricks can Phoebe NOT do?", "Handshake", "Sit", "Heel", 3,
                Category.PHOEBE);
        addQuestion(q11);
        Question q12 = new Question("How old is Phoebe?", "8", "5", "2", 1,
                Category.PHOEBE);
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
        if(question.getCategoryID() == 5){
            c5_counter++;
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
