package cn.edu.appenglistlearning;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityConsolidateList extends AppCompatActivity {

    DBReviewWords dbOpenHelper;
    ListView listView;
    List<Map<String,Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidatewords);
        dbOpenHelper = new DBReviewWords(ActivityConsolidateList.this,"tb_dictrecite",null,1);
        ArrayList<Word> words = dbOpenHelper.getWords();
        listView = (ListView) findViewById(R.id.list1);
        list = new ArrayList<Map<String, Object>>();
        listView.setAdapter(new WordAdapter(words,getApplication()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.findViewById(R.id.translate).setSelected(true);
                view.findViewById(R.id.word).setSelected(true);
            }
        });

    }
}
