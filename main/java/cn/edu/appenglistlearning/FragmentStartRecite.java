package cn.edu.appenglistlearning;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;



public class FragmentStartRecite extends Fragment implements View.OnClickListener{
    DBReviewWords dbReviewWords;
    TextView tv_word,tv_translate;
    Button button_renshi,button_burenshi,button_next;
    ArrayList<Word> words;
    ImageView imgBacktohome4;
    int i = 0;   //背诵数量
    int a = 1;   //删除数量
    //创建一个视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbReviewWords = new DBReviewWords(getActivity(),"tb_dictrecite",null,1);

        View view = inflater.inflate(R.layout.fragment_startreciteword,container, false);
        return view;
    }

    //创建时显示
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        words = getWords();
        String word = null;
        try {
            word = words.get(i).word;
        }
        catch (Exception e){
            ToastUtil.showMsg(getActivity(),"没有单词了");
        }
        tv_word = view.findViewById(R.id.recite_tv_word);
        tv_translate = view.findViewById(R.id.recite_tv_translate);
        button_renshi = view.findViewById(R.id.recite_btn_renshi);
        button_burenshi = view.findViewById(R.id.recite_btn_burenshi);
        button_next = view.findViewById(R.id.recite_btn_next);
        setListener();
        tv_word.setText(word);

        imgBacktohome4=view.findViewById(R.id.img_backtohome4);
        imgBacktohome4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void setListener()
    {
        button_next.setOnClickListener(this);
        button_burenshi.setOnClickListener(this);
        button_renshi.setOnClickListener(this);
    }

    private ArrayList<Word> getWords(){
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = dbReviewWords.getReadableDatabase().query("tb_dictrecite",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Word word = new Word();
            //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
            word.word = cursor.getString(cursor.getColumnIndex("word"));
            word.translate = cursor.getString(cursor.getColumnIndex("translate"));
            words.add(word);
        }
        return words;
    }
    public void ifBackToFirst(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("已经背到最后了").setMessage("是否回到第一个单词")
                .setIcon(R.drawable.icon_image)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        i = 0;
                        try{
                            if(words.isEmpty()){
                                tv_word.setText("");
                                tv_translate.setText("");
                                ToastUtil.showMsg(getActivity(),"没有单词了");
                            }else{
                                tv_word.setText(words.get(i).word);
                                tv_translate.setText("");
                            }
                        }catch (Exception e){
                            ToastUtil.showMsg(getActivity(),"没有单词了");
                        }
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                tv_translate.setText("");
                tv_word.setText("");
            }
        }).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recite_btn_renshi: {                                                    //点击认识，删除单词，跳入下一个单词
                if (words.size() == 0)//如果单词库为空
                {
                    ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                } else if (words.size() == i+1) { //如果单词到最后一个
                    words.remove(i);
                    ifBackToFirst();
                } else if(words.size() == i) {  //删掉最后一个后的i处理
                    ifBackToFirst();
                }else{    //如果单词不是最后一个
                    words.remove(i);
                    ToastUtil.showMsg(getActivity(), "删除成功");
                    i++;
                    i = i - a;
                    tv_word.setText(words.get(i).word);
                    tv_translate.setText("");
                }
                dbReviewWords.onUpgrade(dbReviewWords.getReadableDatabase(), 0, 0);   //更新数据库
                for (Word word : words) {
                    dbReviewWords.writeData(dbReviewWords.getReadableDatabase(), word.word, word.translate);
                }
                break;
            }
            case R.id.recite_btn_burenshi:{             //点击不认识，显示翻译
                if(words.size() == i){
                    ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                }else{
                    tv_translate.setText(words.get(i).translate);
                }
                break;
            }
            case R.id.recite_btn_next:{                //点击下一个，显示下一个单词
                if (i >= words.size()-1)    //如果是最后一个
                {
                    ifBackToFirst();
                }else{
                    i++;
                    tv_word.setText(words.get(i).word);
                    tv_translate.setText("");
                }
                break;
            }
        }
    }
    class Word{
        String word;
        String translate;
    }

}