package cn.edu.appenglistlearning;

public class Basic {
    String [] explains;

    String phonetic;

    public String getPhonetic() {
        return phonetic;
    }

    public String[] getExplains() {
        return explains;
    }

    public String getStrings(String [] strings){
        String str = "\n";
        for (String s:strings
        ) {
            str = str+s+"\n";
        }
        return str;
    }
}
