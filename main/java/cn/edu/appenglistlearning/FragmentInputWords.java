package cn.edu.appenglistlearning;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class FragmentInputWords extends Fragment {
    private ImageView imgbacktohome;
    private TextView txtinputWord;

    private LinearLayout linearEnglish;
    private TextView txtWord_english;
    private EditText etWord_english;

    private LinearLayout linearChinese;
    private TextView txtWord_chinese;
    private EditText etWord_chinese;

    private CardView cardInputWords;
    private TextView txtInputWords;
    private ImageView imgInputWords;

    private DBOpenHelper dbOpenHelper;
    private DBAllWords allWords;
    private DBDifficultWords dbDifficultWords;

    private FragmentHome fragmentHome;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inputwords,container, false);
        dbOpenHelper = new DBOpenHelper(getActivity(),"tb_dict",null,1);
        allWords = new DBAllWords(getActivity(),"tb_words",null,1);
        dbDifficultWords = new DBDifficultWords(getActivity(),"tb_dwords",null,1);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etWord_english = view.findViewById(R.id.et_word_english);
        etWord_chinese = view.findViewById(R.id.et_word_chinese);
        cardInputWords = view.findViewById(R.id.card_input_words);

        initView();

        imgbacktohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        //点击录入按钮
        txtInputWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = etWord_english.getText().toString();
                String translate = etWord_chinese.getText().toString();
                //简单识别单词词性
                if(word.endsWith("ion")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(word.endsWith("ness")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(word.endsWith("ment")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(word.endsWith("ty")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(word.endsWith("acy")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(word.endsWith("ance")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(word.endsWith("ence")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(word.endsWith("ice")){
                    translate = "n. "+etWord_chinese.getText().toString();
                }else if(translate.endsWith("的")){
                    translate = "adj. "+etWord_chinese.getText().toString();
                }else if (translate.endsWith("地")){
                    translate = "adv. "+etWord_chinese.getText().toString();
                }else{
                    translate = etWord_chinese.getText().toString();
                }
                if (word.isEmpty() || etWord_chinese.getText().toString().isEmpty()){
                    ToastUtil.showMsg(getActivity(),"请输入数据");
                }else{
                    if(!allWords.isWordExist(word)) {
                        dbOpenHelper.writeData(word, translate);
                        allWords.writeData( word, translate);
                        ToastUtil.showMsg(getActivity(), "录入成功");
                        etWord_chinese.setText("");
                        etWord_english.setText("");
                        etWord_english.requestFocus();
                    }
                    else{
                        dbOpenHelper.writeData(word, translate);
                        ToastUtil.showMsg(getActivity(),"单词已存在！");
                        if(dbDifficultWords.isWordExist(word))
                            ToastUtil.showMsg(getActivity(),"单词已存在！");
                        else
                            dbDifficultWords.writeData(word,translate);
                        etWord_chinese.setText("");
                        etWord_english.setText("");
                        etWord_english.requestFocus();
                    }
                }
            }
        });
    }

    private void initView(){
        imgbacktohome=getActivity().findViewById(R.id.img_backtohome);
        txtinputWord=getActivity().findViewById(R.id.txtinputword);
        linearEnglish=getActivity().findViewById(R.id.linear_english);
        txtWord_english=getActivity().findViewById(R.id.tv_word_english);
        etWord_english=getActivity().findViewById(R.id.et_word_english);

        linearChinese=getActivity().findViewById(R.id.linear_chinese);
        txtWord_chinese=getActivity().findViewById(R.id.tv_word_chinese);
        etWord_chinese=getActivity().findViewById(R.id.et_word_chinese);

        cardInputWords=getActivity().findViewById(R.id.card_input_words);
        txtInputWords=getActivity().findViewById(R.id.txt_input_word);
        imgInputWords=getActivity().findViewById(R.id.img_input_word);

        fragmentHome=new FragmentHome();
    }

}
