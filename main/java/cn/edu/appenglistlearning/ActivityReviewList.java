package cn.edu.appenglistlearning;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityReviewList extends AppCompatActivity {

    DBOpenHelper dbOpenHelper;
    ListView listView;
    TextView textView;
    List<Map<String,Object>> list;
    Mdialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewwords);

        dbOpenHelper = new DBOpenHelper(ActivityReviewList.this,"tb_dict",null,1);
        final ArrayList<Word> words = dbOpenHelper.getWords();
        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(new WordAdapter(words,getApplication()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.findViewById(R.id.translate).setSelected(true);
                view.findViewById(R.id.word).setSelected(true);
            }
        });
        //updateList();
    }


    private void updateList(){
        final ArrayList<Word> words = dbOpenHelper.getWords();
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0;i < words.size() ;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",words.get(i).id+".");
            map.put("word", words.get(i).word);
            map.put("translate", words.get(i).translate);
            list.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(ActivityReviewList.this,list, R.layout.list_item1,
                new String[]{"id","word","translate"},new int[]{R.id.id,R.id.word,R.id.translate});

        listView.setAdapter(simpleAdapter);
    }


}
