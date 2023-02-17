package cn.edu.appenglistlearning;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




public class Mdialog1 extends Dialog {

    public Button cancel; //定义取消与确定按钮

    //自定义构造方法
    public Mdialog1(Context context) {
        super(context, R.style.mdialog);
        //通过LayouInflater来获取布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.medialog_layout1,null);
        cancel = (Button)view. findViewById(R.id.btn_cancel);    //获取取消按钮
        setContentView(view);
    }


}
