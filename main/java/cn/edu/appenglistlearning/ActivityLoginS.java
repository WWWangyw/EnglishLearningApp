package cn.edu.appenglistlearning;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class ActivityLoginS extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSlogan,loginCodeforget;
    private Button btnLogin,autoBtnLogin;
    private Button btnRegister;



    private EditText edtxtLoginName;
    private EditText edtxtLoginPassword;

    private RelativeLayout RLLogin;
    private LinearLayout linear_login;

    //读取数据
    private SharedPreferences sharedPreferences;
    //写入数据
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

       // if(sharedPreferences.getString("name","").isEmpty() == false&&sharedPreferences.getString("password","").isEmpty()==false){
           // Intent intent=new Intent();
           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
          //  intent.setClass(ActivityLoginS.this,MainActivity.class);
           // startActivity(intent);
        //}
        loginCodeforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ActivityLoginS.this,ActivityForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    /**
     * onCreate()中大的布局已经摆放好了，接下来就该把layout里的东西
     * 声明、实例化对象然后有行为的赋予其行为
     * 这样就可以把视图层View也就是layout 与 控制层 Java 结合起来了
     */
    private void initView() {
        // 初始化控件
        tvSlogan=findViewById(R.id.login_slogan);
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register);
        edtxtLoginName = findViewById(R.id.login_name);
        edtxtLoginPassword = findViewById(R.id.login_password);

        RLLogin = findViewById(R.id.RL_Login);

        // 设置点击事件监听器
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);



        loginCodeforget=findViewById(R.id.login_codeforget);
       //loginCodeforget.setOnClickListener(this);

        //实例化sharedPreferences，参数：文件名称，模式（通常使用PRIVATE）
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ActivityLoginS.this);
        //实例化editor
       // editor = sharedPreferences.edit();


    }




    public void onClick(View view) {
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.button_register:
                startActivity(new Intent(this, ActivityRegisterS.class));
                finish();
                break;

            case R.id.button_login:
                checkData();
                break;
            //case R.id.autologin_btn:
                //startActivity(new Intent(this,ActivityForgetPassword.class));
                //break;
        }
    }

    private void checkData() {
        String username = edtxtLoginName.getText().toString().trim();
        String userpassword = edtxtLoginPassword.getText().toString().trim();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpassword)){
            sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
            //获取ID
            String name=sharedPreferences.getString("username","");
            String password=sharedPreferences.getString("userpassword","");
            if(username.equals(name)&&userpassword.equals(password)){
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();//销毁此Activity
            }
            else{
                Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "请输入你的用户名或密码，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
}