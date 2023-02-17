package cn.edu.appenglistlearning;


import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class ActivityChangeInfo extends AppCompatActivity {
    TextView txtChangeWord;
    EditText editTextName;
    EditText editTextPasswordOld;
    EditText editTextPasswordNew;
    //private DBUserInfor DBUserInfor;

    //读取数据
     private SharedPreferences sharedPreferences;
    //写入数据
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeinfo);
        txtChangeWord = findViewById(R.id.txt_change_info);

        editTextName = findViewById(R.id.resetusername_et);
        editTextPasswordOld = findViewById(R.id.resetuserpassword_et);
        editTextPasswordNew = findViewById(R.id.resetuserpassword_et2);
        //DBUserInfor = new DBUserInfor(this);
        //sharedPreferences = getSharedPreferences("userdata",MODE_PRIVATE);
        //editor = sharedPreferences.edit();
        txtChangeWord.setOnClickListener(new View.OnClickListener() {
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
        String userpasswordOld= editTextPasswordOld.getText().toString().trim();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpasswordOld)&&!TextUtils.isEmpty(userpasswordNew)){
            if(!(userpasswordNew.equals(userpasswordOld))){
                boolean flag=SPDataUtils.saveUserInfo(this,username,userpasswordNew);
                Toast.makeText(this,  "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, ActivityLoginS.class);
                startActivity(intent2);

                finish();
            }
            else{
                Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "未完善信息，修改失败", Toast.LENGTH_SHORT).show();
        }
    }
}

//TextUtils.isEmpty(username)==false &&