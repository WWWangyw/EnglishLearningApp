package cn.edu.appenglistlearning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityForgetPassword extends AppCompatActivity{
    TextView txtForgetWord;
    EditText editTextName;
    EditText editTextPasswordNew;
    //private DBUserInfor DBUserInfor;

    //读取数据
    private SharedPreferences sharedPreferences;
    //写入数据
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        txtForgetWord = findViewById(R.id.txt_forget_info);

        editTextName = findViewById(R.id.forget_username);
        editTextPasswordNew = findViewById(R.id.forget_password);
        //DBUserInfor = new DBUserInfor(this);
        //sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
        //editor = sharedPreferences.edit();
        txtForgetWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    //public void onClick(View view){
    //switch (view.getId()){
    // case R.id.txt_change_info:
    //   Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
    //  //updateData();
    // break;
    // }
    //   }
    public void updateData(){
        String username = editTextName.getText().toString().trim();
        String userpasswordNew = editTextPasswordNew.getText().toString().trim();

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpasswordNew)){
                boolean flag=SPDataUtils.saveUserInfo(this,username,userpasswordNew);
                Toast.makeText(this,  "重置成功", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, ActivityLoginS.class);
                startActivity(intent2);

                finish();
        }
        else{
            Toast.makeText(this, "未完善信息，修改失败", Toast.LENGTH_SHORT).show();
        }
    }
}
