package cn.edu.appenglistlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentWordList extends Fragment {

    TextView textView_reviewword,textView_consolidateword,textView_allword,
            textView_difficultword ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wordlist,container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_reviewword=view.findViewById(R.id.self_tv_reviewwords);
        textView_consolidateword = view.findViewById(R.id.self_tv_consolidatewords);
        textView_allword = view.findViewById(R.id.self_tv_allwords);
        textView_difficultword = view.findViewById(R.id.self_tv_difficultwords);

        //点击复习单词库
        textView_reviewword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityReviewList.class);
                startActivity(intent);
            }
        });


        //点击巩固单词库
        textView_consolidateword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityConsolidateList.class);
                startActivity(intent);
            }
        });

        //点击单词库
        textView_allword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityALLWordList.class);
                startActivity(intent);
            }
        });
        //点击difficult
        textView_difficultword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityDifficultList.class);
                startActivity(intent);
            }
        });
    }
    public void setMenuVisibility(boolean menuVisibile){
        super.setMenuVisibility(menuVisibile);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisibile?View.VISIBLE:View.GONE);
        }
    }
}

