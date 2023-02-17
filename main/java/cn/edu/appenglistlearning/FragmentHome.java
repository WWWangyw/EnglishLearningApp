package cn.edu.appenglistlearning;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentHome  extends Fragment  {
    private TextView txtUsername;

    private CardView cardInputWords;
    private RelativeLayout rlInputWords;
    private TextView txtInputWords;
    private ImageView imgInputWords;

    private CardView cardSearchWords;
    private TextView txtSearchWords;
    private ImageView imgSearchWords;

    private CardView cardReviewWords;
    private TextView txtReviewWords;
    private ImageView imgReviewWords;

    private CardView cardUnorderedStart;
    private TextView txtUnorderedStart;

    FragmentInputWords fragmentInputWords;
    FragmentReviewWords fragmentReviewWords;
    FragmentSearchWords fragmentSearchWords;
    FragmentStartRecite fragmentStartRecite;

    //读取数据
    private SharedPreferences sharedPreferences;
    //写入数据
    private SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initView();
        //实例化sharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("userdata", Activity.MODE_PRIVATE);
        //获取ID
        txtUsername.setText(sharedPreferences.getString("username",""));


        txtInputWords .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.linear_frag_container,fragmentInputWords).commitAllowingStateLoss();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.linear_frag_container, fragmentInputWords);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        txtSearchWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.linear_frag_container, fragmentSearchWords).commit();
                //Intent intent = new Intent(getActivity(), fragmentSearchWords.class);
                //startActivity(intent);

                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.linear_frag_container, fragmentSearchWords);
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });

        txtReviewWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.linear_frag_container,fragmentReviewWords).commitAllowingStateLoss();

                FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                ft3.replace(R.id.linear_frag_container, fragmentReviewWords);
                ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft3.addToBackStack(null);
                ft3.commit();
            }
        });

        txtUnorderedStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().replace(R.id.linear_frag_container,fragmentStartRecite).commitAllowingStateLoss();
                FragmentTransaction ft4 = getFragmentManager().beginTransaction();
                ft4.replace(R.id.linear_frag_container, fragmentStartRecite);
                ft4.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft4.addToBackStack(null);
                ft4.commit();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initView(){
        txtUsername=getActivity().findViewById(R.id.txt_username);
       cardInputWords=getActivity().findViewById(R.id.card_input_words);
       // cardInputWords.setOnClickListener((View.OnClickListener) this);
        rlInputWords=getActivity().findViewById(R.id.RL_input_words);
        txtInputWords=getActivity().findViewById(R.id.txt_input_word);
        //txtInputWords.setOnClickListener((View.OnClickListener) this);
        imgInputWords=getActivity().findViewById(R.id.img_Input_words);
        //imgInputWords.setOnClickListener((View.OnClickListener) this);

        cardSearchWords=getActivity().findViewById(R.id.card_search_words);
        //cardSearchWords.setOnClickListener((View.OnClickListener) this);
        txtSearchWords=getActivity().findViewById(R.id.txt_search_word);
        //txtSearchWords.setOnClickListener((View.OnClickListener) this);
        imgSearchWords=getActivity().findViewById(R.id.img_recite_word);
        //imgSearchWords.setOnClickListener((View.OnClickListener) this);

        cardReviewWords=getActivity().findViewById(R.id.card_review_words);
        //cardReviewWords.setOnClickListener((View.OnClickListener) this);
        txtReviewWords=getActivity().findViewById(R.id.txt_review_word);
        //txtSearchWords.setOnClickListener((View.OnClickListener) this);
        imgReviewWords=getActivity().findViewById(R.id.img_review_word);
        //imgSearchWords.setOnClickListener((View.OnClickListener) this);

        cardUnorderedStart=getActivity().findViewById(R.id.card_unordered_start);
        txtUnorderedStart=getActivity().findViewById(R.id.text_unordered_start);

        fragmentInputWords=new FragmentInputWords();
        fragmentSearchWords=new FragmentSearchWords();
        fragmentReviewWords=new FragmentReviewWords();
        fragmentStartRecite=new FragmentStartRecite();

    }
    public void setMenuVisibility(boolean menuVisibile){
        super.setMenuVisibility(menuVisibile);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisibile?View.VISIBLE:View.GONE);
        }
    }
}