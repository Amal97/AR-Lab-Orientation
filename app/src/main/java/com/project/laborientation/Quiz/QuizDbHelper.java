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

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();


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
