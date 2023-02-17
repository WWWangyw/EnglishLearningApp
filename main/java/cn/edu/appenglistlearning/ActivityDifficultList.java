package cn.edu.appenglistlearning;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import static cn.edu.appenglistlearning.R.layout.list_item;

public class ActivityDifficultList extends AppCompatActivity {

    DBDifficultWords dbDifficultWords;
    ListView listView;
    List<Map<String,Object>> list;
    TextView textView;
    Mdialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficultwords);

        dbDifficultWords = new DBDifficultWords(ActivityDifficultList.this,"tb_dwords",null,1);
        final ArrayList<Word> words = dbDifficultWords.getWords();

        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(new DifficultWordAdapter(words,getApplication()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.findViewById(R.id.translate).setSelected(true);
                view.findViewById(R.id.word).setSelected(true);
            }
        });
        //updateList();
    }

    //列表的显示
    private void updateList(){
        final ArrayList<Word> words = dbDifficultWords.getWords();
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0;i < words.size() ;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",words.get(i).id+".");
            map.put("word", words.get(i).word);
            map.put("translate", words.get(i).translate);
            map.put("counts",words.get(i).counts);
            list.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(ActivityDifficultList.this,list, list_item,
                new String[]{"id","word","translate","counts"},new int[]{R.id.id,R.id.word,R.id.translate,R.id.counts});

        listView.setAdapter(simpleAdapter);

    }
}