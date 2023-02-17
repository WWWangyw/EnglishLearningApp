package cn.edu.appenglistlearning;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityALLWordList extends AppCompatActivity {

    DBAllWords allWords;
    DBOpenHelper dbOpenHelper;
    DBDifficultWords dbDifficultWords;
    ListView listView;
    List<Map<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allwords);
        allWords = new DBAllWords(ActivityALLWordList.this,"tb_words",null,1);
        dbOpenHelper = new DBOpenHelper(ActivityALLWordList.this,"tb_dict",null,1);
        dbDifficultWords = new DBDifficultWords(ActivityALLWordList.this,"tb_dwords",null,1);


        final ArrayList<Word> words = allWords.getWords();

        listView = (ListView) findViewById(R.id.list2);

        listView.setAdapter(new WordAdapter(words,getApplication()));

        //长按加入复习
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = words.get(i).word;
                String translate = words.get(i).translate;
                dbOpenHelper.writeData(word,translate);
                ToastUtil.showMsg(ActivityALLWordList.this,"已重新加入复习单词表");
                if(dbDifficultWords.isWordExist(word))
                    ToastUtil.showMsg(ActivityALLWordList.this,"已重新加入复习单词表");
                else
                    dbDifficultWords.writeData(word,translate);
                return true;
            }
        });

        /*list = new ArrayList<Map<String, Object>>();
        for (int i = 0;i < words.size() ;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",words.get(i).id+".");
            map.put("word", words.get(i).word);
            map.put("translate", words.get(i).translate);
            list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(allWordsBase.this,list, R.layout.list_item1,
                new String[]{"id","word","translate"},new int[]{R.id.id,R.id.word,R.id.translate});

        listView.setAdapter(simpleAdapter);*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = words.get(i).word;
                String translate = words.get(i).translate;
                OnExit(word,translate);
                view.findViewById(R.id.translate).setSelected(true);
                view.findViewById(R.id.word).setSelected(true);
            }
        });
    }

    /**
     * 进入dialog对话框
     * @param a
     */
    public void OnExit(final String a,String b)
    {

        /**
         * 设置没有标题栏
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Mdialog mdialog = new Mdialog(this);  //实例化自定义对话框
        //对话框设置默认值
        mdialog.editText_word.setText(a);
        mdialog.editText_translate.setText(b);
        final SQLiteDatabase readableDatabase = allWords.getReadableDatabase();
        final Context context = getApplication();
        ToastUtil.showMsg(context,a);
        //给取消按钮设置监听事件
        mdialog.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mdialog.isShowing()){
                    mdialog.dismiss();  //关闭对话框
                }
            }
        });

        //给确定按钮设置监听事件
        mdialog.ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdialog.isShowing()){
                    String word = mdialog.editText_word.getText().toString();
                    String translate = mdialog.editText_translate.getText().toString();
                    ContentValues values = new ContentValues();
                    values.put("word",word);
                    values.put("translate",translate);
                    int updateCount = readableDatabase.update("tb_words",values,"word = ?",new String[]{a});
                    ToastUtil.showMsg(context,"更新了"+updateCount+"处！");
                    mdialog.dismiss();      //关闭对话框
                }
            }
        });
        mdialog.show();
    }
}