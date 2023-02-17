package cn.edu.appenglistlearning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityRegisterS extends AppCompatActivity implements View.OnClickListener {
    private TextView registerSlogan;

    private String realCode;

    private Button btnRegister;

    private ImageView imgBacktoLogin;

    private EditText edtxtRegistName;
    private EditText edtxtRegistPassword;

    private EditText edtxtRegistCode;
    private ImageView imgRegistCode;



    //读取数据
    private SharedPreferences sharedPreferences;
    //写入数据
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        //将验证码用图片的形式显示出来
        imgRegistCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();

        //实例化sharedPreferences
        sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
        //实例化editor
        editor = sharedPreferences.edit();
    }

    private void initView(){
        registerSlogan=findViewById(R.id.register_slogan);

        btnRegister = findViewById(R.id.button_registerCheck);
        imgBacktoLogin = findViewById(R.id.img_backtoLogin);

        edtxtRegistName = findViewById(R.id.register_username);
        edtxtRegistPassword = findViewById(R.id.register_password);
        edtxtRegistCode = findViewById(R.id.register_code);
        imgRegistCode = findViewById(R.id.register_imagecode);

        imgBacktoLogin.setOnClickListener(this);
        imgRegistCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_backtoLogin: //返回登录页面
                Intent intent1 = new Intent(this, ActivityLoginS.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.register_imagecode:    //改变随机验证码的生成
                imgRegistCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.button_registerCheck:    //注册按钮
                //注册验证
                checkandSaveData();
                break;
        }
    }
    private void checkandSaveData() {
        String username = edtxtRegistName.getText().toString().trim();
        String userpassword = edtxtRegistPassword.getText().toString().trim();
        String phoneCode = edtxtRegistCode.getText().toString().toLowerCase();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpassword)&&!TextUtils.isEmpty(phoneCode)){
            if(phoneCode.equals(realCode)){
                boolean flag=SPDataUtils.saveUserInfo(this,username,userpassword);
                Intent intent2 = new Intent(this, ActivityLoginS.class);
                startActivity(intent2);
                finish();
                Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}

