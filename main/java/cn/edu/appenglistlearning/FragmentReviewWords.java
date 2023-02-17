package cn.edu.appenglistlearning;

import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;



import java.util.ArrayList;


public class FragmentReviewWords extends Fragment implements View.OnClickListener{
    DBReviewWords dbReviewWords;
    DBOpenHelper dbOpenHelper;
    TextView tv_word,tv_translate;
    Button button_renshi,button_burenshi,button_next;
    ArrayList<Word> words;
    ImageView imgBacktohome3;
    int count = 0;
    int i = 0;   //背诵数量
    int a = 1;   //删除数量
    //创建一个视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbOpenHelper = new DBOpenHelper(getActivity(),"tb_dict",null,1);
        dbReviewWords = new DBReviewWords(getActivity(),"tb_dictrecite",null,1);
        count = getWords().size();  //总单词数

        View view = inflater.inflate(R.layout.fragment_reviewwords,container, false);
        return view;
    }

    //创建时显示
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        words = getWords();
        i = (int)(Math.random()*words.size());
        String word = null;
        try {
            word = words.get(i).word;
        }
        catch (Exception e){
            ToastUtil.showMsg(getActivity(),"没有单词了");
        }
        tv_word = view.findViewById(R.id.review_tv_word);
        tv_translate = view.findViewById(R.id.review_tv_translate);
        button_renshi = view.findViewById(R.id.review_btn_renshi);
        button_burenshi = view.findViewById(R.id.review_btn_burenshi);
        button_next = view.findViewById(R.id.review_btn_next);
        imgBacktohome3=view.findViewById(R.id.img_backtohome3);
        setListener();

        tv_word.setText(word);

        imgBacktohome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().replace(R.id.linear_frag_container,fragmentHome).commitAllowingStateLoss();
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
        Cursor cursor = dbOpenHelper.getReadableDatabase().query("tb_dict",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Word word = new Word();
            //利用getColumnIndex：String 来获取列的下标，再根据下标获取cursor的值
            word.word = cursor.getString(cursor.getColumnIndex("word"));
            word.translate = cursor.getString(cursor.getColumnIndex("translate"));
            words.add(word);
        }
        return words;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.review_btn_renshi: {                                                    //点击认识，删除单词，跳入下一个单词
                if (words.size() == 0)//如果单词库为空
                {
                    ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                } else{    //如果单词不是最后一个
                    dbReviewWords.writeData(dbReviewWords.getReadableDatabase(),words.get(i).word,words.get(i).translate);
                    words.remove(i);
                    count--;
                    ToastUtil.showMsg(getActivity(), "删除成功");
                    i = (int)(Math.random()*words.size());
                    try {
                        tv_word.setText(words.get(i).word);
                    } catch (Exception e) {
                        e.printStackTrace();
                        tv_word.setText("");
                        ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                    }
                    tv_translate.setText("");
                }
                dbOpenHelper.onUpgrade(dbOpenHelper.getReadableDatabase(), 0, 0);   //更新数据库
                for (Word word : words) {
                    dbOpenHelper.writeData(word.word, word.translate);
                }
                break;
            }
            case R.id.review_btn_burenshi:{                                                      //点击不认识，显示翻译
                if(words.size() == 0){
                    ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                }else{
                    tv_translate.setText(words.get(i).translate);
                }
                break;
            }
            case R.id.review_btn_next:{                                                          //点击下一个，显示下一个单词
                i = (int)(Math.random()*words.size());
                try {
                    tv_word.setText(words.get(i).word);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showMsg(getActivity(), "已背到最后啦！");
                }
                tv_translate.setText("");
                break;
            }
        }
    }

    class Word{
        String word;
        String translate;
    }

}