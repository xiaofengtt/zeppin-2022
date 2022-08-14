package com.makati.common.util;

import java.util.Random;

/**
 * Created by hello on 2018/10/29.
 */
public class UserCountUtil {

   /* public static void main(String[] args) {

         System.out.println(getFirstAcount()+getAcount());
    }*/


    public static String getAcount(){

        Random rand = new Random();
        char[] letters=new char[]{'a','b','c','d','e','f','g','h','i',
                'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                '0','1','2','3','4','5','6','7','8','9'};

        String str = "";
        int index;
        boolean[] flags = new boolean[letters.length];//默认为false
        for(int i=0;i<10;i++){
            do{
                index = rand.nextInt(letters.length);
            }
            while(flags[index]==true);

            char c = letters[index];
            str += c;
            flags[index]=true;
        }

        return str;
    }


    public static String getFirstAcount(){

        Random rand = new Random();
        char[] letters=new char[]{'a','b','c','d','e','f','g','h','i',
                'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        String str = "";
        int index;
        boolean[] flags = new boolean[letters.length];//默认为false
        for(int i=0;i<1;i++){
            do{
                index = rand.nextInt(letters.length);
            }
            while(flags[index]==true);

            char c = letters[index];
            str += c;
            flags[index]=true;
        }

        return str;
    }

}
