package cn.edu.appenglistlearning;

import android.content.Context;
import android.content.SharedPreferences;

public class SPDataUtils {
    private static final String mFileName="userdata";
    public static boolean saveUserInfo(Context context,String username,String userpassword){
        boolean flag=false;
        SharedPreferences sp=context.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("username",username);
        editor.putString("userpassword",userpassword);
        editor.commit();
        flag=true;
        return flag;
    }

    public static User getuser(Context context){
        User user=null;
        SharedPreferences sp=context.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        String username=sp.getString("username","");
        String userpassword=sp.getString("userpassword","");

        user=new User();
        user.setName(username);
        user.setPassword(userpassword);
        return user;
    }

}
