package cn.edu.appenglistlearning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;

public class DBAllWords extends SQLiteOpenHelper {

    final String Create_Table_SQL="create table tb_words (_id integer primary key autoincrement,word,translate)";

    public DBAllWords(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_Table_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("delete from tb_words");
        sqLiteDatabase.execSQL("update sqlite_sequence SET seq = 0 where name ='tb_words'");
    }

    //单词的添加方法
    public void writeData(String word,String translate){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("word",word);
        values.put("translate",translate);
        sqLiteDatabase.insert("tb_words",null,values);//保存功能
    }


    //判断是否单词已存在
    public boolean isWordExist(String word){
        Cursor cursor = getReadableDatabase().query("tb_words", new String[]{"word"}, "word = ?", new String[]{word}, null, null, null);
        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    //获取单词列表
    public ArrayList<Word> getWords(){
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("tb_words",null,null,null,null,null,null);
        int i = 1;
        while(cursor.moveToNext()){
            Word word = new Word();
            //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
            word.id = i;
            word.word = cursor.getString(cursor.getColumnIndex("word"));
            word.translate = cursor.getString(cursor.getColumnIndex("translate"));
            words.add(word);
            i++;
        }
        return words;
    }
}
