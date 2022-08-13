package cn.zeppin.utility;

import java.util.Random;

public class Shuangseqiu {


    public static void main(String[] args) {

        int arrayNums = 5; //机选5组
        for(int i=0;i<arrayNums;i++){
            String hm = getYZHM();
            System.out.println(hm);
        }
    }

    /**
     * 产生一组双色球选号
     * @return 一注号码
     */
    private static String getYZHM(){
       //33选6
        int[] exist = new int[6]; //默认全0
        for(int i=0;i<6;i++){
            int ball = getABall(33,exist);
            exist[i] = ball; //暂存已选的球
        }
        sort(exist); //排序
        //16选1
        int specialBall = getABall(16,null);

        //拼接字符串
        String hm = "";
        for(int i=0;i<6;i++){
            String num = exist[i] < 10 ? "0"+exist[i] : ""+ exist[i];
            if(i==0){
                hm+= num;
            }else{
                hm+= ","+ num;
            }
        }
        hm += "\t"+(specialBall<10? "0"+specialBall:""+specialBall);
        return hm;
    }

    /**
     * 摇出一个号码
     * @param total 球总数
     * @param exist 已经选出的球
     * @return 一个新号码
     */
    private static int getABall(int total,int[] exist){
        Random random = new Random();
        int ball = random.nextInt(total)+1;
        while(true){
            if(contains(exist,ball)){
                ball = random.nextInt(total)+1;
            }else{
                break; //取到了新球，结束
            }
        }
        return ball;
    }

    /**
     * 判断数组是否包含某个元素
     * @param array 数组
     * @param value 元素
     * @return 是否存在
     */
    private static boolean contains(int[] array,int value){
        boolean c = false;
        if(array == null){
            return false;
        }
        for(int i=0;i<array.length;i++){
            if(array[i] == value){
                c = true;
                break;
            }
        }
        return c;
    }

    /**
     * 排序
     * @param array 数组
     */
    private static void sort(int[] array){
        for(int i=0;i< array.length-1;i++){ //比多少次
            for(int j= i+1;j<array.length;j++){ //每次循环，将最小数提前
                if(array[i]>array[j]){ //小数冒泡
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }
}
