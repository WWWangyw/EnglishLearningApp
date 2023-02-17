package cn.edu.appenglistlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //用于记录上个选择的Fragment
    public static int lastFragment;
    private Fragment[] fragments;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragHome;
    private Fragment fragWordList;
    private Fragment fragMyPage;

    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        initFragment();
    }


    private void initView() {
        // 初始化控件对象
        bottomNavigationView = findViewById(R.id.bottom_nav);
        linearLayout = findViewById(R.id.linear_frag_container);

    }


    private  void initFragment(){
        fragHome=new FragmentHome();
        fragWordList=new FragmentWordList();
        fragMyPage=new FragmentMyPage();
        fragments = new Fragment[]{fragHome, fragWordList, fragMyPage};//底端三个导航栏，主页，复习，我

        switch (lastFragment) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_frag_container, fragHome).show(fragHome).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_frag_container, fragWordList).show(fragWordList).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_frag_container, fragMyPage).show(fragMyPage).commit();
                break;
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bnavigation_home:
                        if (lastFragment != 0) {
                            switchFragment(lastFragment, 0);
                            lastFragment = 0;
                        }
                        return true;
                    case R.id.bnavigation_word:
                        if (lastFragment != 1) {
                            switchFragment(lastFragment, 1);
                            lastFragment = 1;
                        }
                        return true;
                    case R.id.bnavigation_me:
                        if (lastFragment != 2) {
                            switchFragment(lastFragment, 2);
                            lastFragment = 2;
                        }
                        return true;
                }
                return true;
            }
        });
    }

    private void switchFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastIndex]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.linear_frag_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
