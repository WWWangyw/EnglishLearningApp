package cn.edu.appenglistlearning;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FragmentSearchWords extends Fragment implements View.OnClickListener{

    Context context = getActivity();

    private String TAG = "FragmentQuery";

    private ImageView imgbacktohome2;

    private EditText etSearchWord;

    private TextView tvPronounce,tvTranslate;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ;

    private TextView txtSearchWord;


    private TextView txtAddlistWord;

    DBOpenHelper dbOpenHelper;
    DBAllWords allWords;
    DBDifficultWords dbDifficultWords;


    //2.??????handler
    private Handler handler = new Handler(){
        //4.handler????????????????????????
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //1.????????????
            Word word = (Word) msg.obj;
            //2.????????????
            String yinbiao = null;
            Basic basic = null;
            String explains = null;
            try {
                basic = word.getBasic();
                explains = basic.getStrings(basic.getExplains());
                yinbiao = basic.getPhonetic();
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.showMsg(getActivity(),"???????????????");
            }

            //3.???????????????
            tvTranslate.setText(explains);
            tvPronounce.setText(yinbiao);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchwords,container, false);
        dbOpenHelper = new DBOpenHelper(getActivity(),"tb_dict",null,1);
        allWords = new DBAllWords(getActivity(),"tb_words",null,1);
        dbDifficultWords = new DBDifficultWords(getActivity(),"tb_dwords",null,1);
        return view;
    }

    //???????????????
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        imgbacktohome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().replace(R.id.linear_frag_container,fragmentHome).commitAllowingStateLoss();
                getFragmentManager().popBackStack();
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.txt_search_word:
                String word = etSearchWord.getText().toString();
                //????????????????????????
                queryWord(word);
                break;
            case R.id.img_deleteword:
               // etSearchWord.setText("");
                etSearchWord.clearComposingText();
                break;
            case R.id.txt_addlist_word:
                String word1 = etSearchWord.getText().toString();
                String translate = tvTranslate.getText().toString();
                if(word1.equals("") || translate.equals("????????????????????????")) {
                    ToastUtil.showMsg(getActivity(), "???????????????");
                }
                else {
                    dbOpenHelper.writeData(word1, translate);
                    if (allWords.isWordExist(word1)) {      //??????????????????
                        ToastUtil.showMsg(getActivity(), "??????????????????????????????????????????");
                        if (!dbDifficultWords.isWordExist(word1))   //difficult??????????????????
                            dbDifficultWords.writeData(word1, translate);   //?????????difficult?????????
                    } else {    //??????????????????
                        ToastUtil.showMsg(getActivity(), "????????????????????????");
                        allWords.writeData(word1, translate);
                    }
                }
                break;
        }
    }

    private void initView(){
        imgbacktohome2=getActivity().findViewById(R.id.img_backtohome2);

        etSearchWord=getActivity().findViewById(R.id.et_search_word);

        tvPronounce=getActivity().findViewById(R.id.tv_pronounce);
        tvTranslate=getActivity().findViewById(R.id.tv_translate);
        tvTranslate.setOnClickListener(this);

        txtSearchWord=getActivity().findViewById(R.id.txt_search_word);
        txtSearchWord.setOnClickListener(this);

        txtAddlistWord=getActivity().findViewById(R.id.txt_addlist_word);
        txtAddlistWord.setOnClickListener(this);

    }


    public void queryWord(String s) {
        String url = "http://fanyi.youdao.com/openapi.do?keyfrom=youdianbao&key=1661829537&type=data&doctype=json&version=1.1&q=" + s;
        //1.??????OkClient???Request??????
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        //2.??????Call??????
        Call call = okHttpClient.newCall(request);
        //3.??????Call?????????enqueue??????
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //1.??????????????????
                String str = response.body().string();
                Log.d(TAG, "onResponse: " + str);
                try {
                    //2.?????????json??????
                    JSONObject jsonObject = new JSONObject(str);
                    //3.??????java??????
                    Word word = (Word) JsonUitl.stringToObject(jsonObject.toString(), Word.class);
                    //4.??????message,????????????
                    Message message = new Message();
                    message.obj = word;
                    //5.??????message???handler
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
