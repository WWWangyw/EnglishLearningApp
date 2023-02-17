package cn.edu.appenglistlearning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DifficultWordAdapter extends BaseAdapter {

    private List<Word> list = new ArrayList<>();
    //LayoutInflater将布局文件实例化为View对象
    private LayoutInflater layoutInflater;

    public DifficultWordAdapter(List<Word> list, Context context) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //将布局文件实例化为View对象
        View view = layoutInflater.inflate(R.layout.list_item,null);
        //从布局取textview
        TextView textView = (TextView)view.findViewById(R.id.translate);
        TextView textView1 = (TextView)view.findViewById(R.id.word);
        TextView textView2 = (TextView)view.findViewById(R.id.id);
        TextView textView3 = (TextView)view.findViewById(R.id.counts);
        //取当前需要显示的对象
        Word word = list.get(position);


        textView1.setText(word.word);
        textView.setText(word.translate);
        textView2.setText(word.id+".");
        textView3.setText(word.counts+"");
        textView.setTextColor(R.color.colorBlack);
        textView1.setTextColor(R.color.colorBlack);
        textView2.setTextColor(R.color.colorBlack);
        //给textview赋值
        //textView.setSelected(true);
        return view;
    }
}
