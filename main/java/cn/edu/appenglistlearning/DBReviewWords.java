package cn.edu.appenglistlearning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;

/**
 * Created by Administrator on 2019/4/27.
 */

public class DBReviewWords extends SQLiteOpenHelper {

    final String Create_Table_SQL="create table tb_dictrecite (_id integer primary key autoincrement,word,translate)";

    public DBReviewWords(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_Table_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("delete from tb_dictrecite");
        sqLiteDatabase.execSQL("update sqlite_sequence SET seq = 0 where name ='tb_dictrecite'");
    }

    //单词的添加方法
    public void writeData(SQLiteDatabase sqLiteDatabase,String word,String translate){
        ContentValues values = new ContentValues();
        values.put("word",word);
        values.put("translate",translate);
        sqLiteDatabase.insert("tb_dictrecite",null,values);//保存功能
    }

    //获取单词列表
    public ArrayList<Word> getWords(){
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("tb_dictrecite",null,null,null,null,null,null);
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
