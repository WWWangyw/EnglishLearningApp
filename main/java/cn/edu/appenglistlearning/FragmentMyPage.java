package cn.edu.appenglistlearning;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyPage extends Fragment {

    private ImageView imgChangeinfo;
    private ImageView imgNews;
    private ImageView imgAppinfo;
    private ImageView imgContectus;
    Mdialog1 mdialog1;
    private Button btnLogout;
    private static List<Activity> activities = new ArrayList<>();

    //读取数据
    private SharedPreferences sharedPreferences;
    //写入数据
    private SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage,container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        imgChangeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityChangeInfo.class);
                startActivity(intent);
            }
        });

        imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityNews.class);
                startActivity(intent);
            }
        });

        imgAppinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAppInfo.class);
                startActivity(intent);
            }
        });

        imgContectus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

                final Mdialog1 mdialog = new Mdialog1(getActivity());  //实例化自定义对话框
                //给取消按钮设置监听事件
                mdialog.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mdialog.isShowing()){
                            mdialog.dismiss();  //关闭对话框
                        }
                    }
                });
                mdialog.show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //结束活动并退出登录
                for(Activity activity:activities){
                    if (!activity.isFinishing()){

                        activity.finish();
                    }
                }
                //返回登录界面
                Intent intent = new Intent(getActivity(), ActivityLoginS.class);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        imgChangeinfo=getActivity().findViewById(R.id.img_changeinfo);
        imgNews=getActivity().findViewById(R.id.img_news);
        imgAppinfo=getActivity().findViewById(R.id.img_appinfo);
        imgContectus=getActivity().findViewById(R.id.img_contectus);
        btnLogout=getActivity().findViewById(R.id.btn_logout);

        //实例化sharedPreferences，参数：文件名称，模式（通常使用PRIVATE）
        sharedPreferences = getActivity().getSharedPreferences("User",Activity.MODE_PRIVATE);
        //实例化editor
        editor = sharedPreferences.edit();
    }
    public void setMenuVisibility(boolean menuVisibile){
        super.setMenuVisibility(menuVisibile);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisibile?View.VISIBLE:View.GONE);
        }
    }

}
