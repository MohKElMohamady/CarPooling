package de.unidue.inf.is.utils;

public class StringUtil {
    private static String wordOnlyContainHyphen(String str){
        String[] splitStr = str.split("-");
        String temp="";
        int count=0;
        for (String s: splitStr) {
            s=s.toLowerCase();
            s=s.substring(0,1).toUpperCase()+s.substring(1);
            if (count==0){
                temp=temp+s;
                count++;
            }
            else{
                temp= temp+"-"+s;
            }
        }
        System.out.println("method 2 res= "+temp);
        return temp;
    }

    private static String wordOnlyContainSpace(String str){
        String[] splitStr = str.trim().split("\\s+");
        String temp="";
        int count=0;
        for (String s: splitStr) {
            s=s.toLowerCase();
            s=s.substring(0,1).toUpperCase()+s.substring(1);
            if (count==0){
                temp=temp+s;
                count++;
            }
            else{
                temp= temp+" "+s;
            }
        }
        System.out.println("method 1 res= "+temp);

        return temp;
    }

    public static String transformString(String str){
        if(str.contains("-")){
            return wordOnlyContainHyphen(str);
        }
        else{
            return wordOnlyContainSpace(str);
        }
    }

}
