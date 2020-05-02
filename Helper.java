import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
	//用List
	public static List<Task2Result> regex2(String s, String pattern){
        List<Task2Result> result = new ArrayList<Task2Result>();
        //Whether or not to match exactly
        boolean exactlyMatch = false;
        //pattern's length
        int patternLen = pattern.length();
        if(!pattern.contains("*")){
            exactlyMatch = true;
            pattern = pattern + "*";
        }
        //精确匹配时由于Pattern.compile后group(0)只会截取pattern模式的字符
        //不能group整个单词，所以就认为增加一个* 
        String startWithString = "";
        if(!pattern.startsWith("?") && !pattern.startsWith("*")){
            startWithString = pattern.charAt(0) + "";
            pattern = "*" + pattern;
        }
        //?在单词前
        if(pattern.startsWith("?")){
            //startWithString = pattern.charAt(0) + "";
            pattern = "*" + pattern;
        }
        //System.out.println("pattern1=" + pattern);
        pattern = pattern.replace("?","[a-zA-Z]{1}");
        pattern = pattern.replace("*","[a-zA-Z]{0,}");
        //System.out.println("pattern2=" + pattern);
        Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        while(m.find()){
            String ms = m.group(0);
            //System.out.println("group(0)"+ms);
            boolean isAdd = true;
            //如果pattern里没有*，只有？则表示是精确匹配，包括长度要一致 
            if(exactlyMatch){
                if(ms.length()!=patternLen) isAdd = false;
            }

            if(!"".equals(startWithString)){
                if(!ms.toLowerCase().startsWith(startWithString.toLowerCase())) isAdd = false;
            }
            //元素是否存在
            boolean isExist = false;
            if(isAdd){
                for (int i=0;i<result.size();i++){
                    Task2Result tr = result.get(i);
                    if(tr.key.equals(ms)){
                        tr.count = tr.count + 1;
                        isExist = true;
                        break;
                    }
                }
                if(!isExist) result.add(new Task2Result(ms,1));
            }
        }
        return result;
    }
}