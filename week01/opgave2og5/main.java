package opgave2og5;

import java.util.Arrays;

public class main {
    public static void main(String[] args){
        System.out.println("2.");
        int[] aarray = {3,7,5,6};
        System.out.println(Arrays.toString(map(aarray, (x) -> x * 2)));
        System.out.println(Arrays.toString(filter(aarray, (x) -> x > 4)));
        System.out.println("---------- \n5.");
        //// 5
        System.out.println("Using method reference: ");
        System.out.println(Arrays.toString(map(aarray,main::transformingMethod)));
        System.out.println(Arrays.toString(filter(aarray, main::validatingMethod)));
        ////
    }
    static int[] map(int[]a,MyTransformingType op){
        int[] newMapArray = new int[a.length];
        for(int i=0; i < newMapArray.length;i++){
             newMapArray[i] = op.transform(a[i]);
        }
        return newMapArray;
    }
    static int[] filter(int[]a,MyValidatingType op){
        int[] tempFilterArray = new int[a.length];
        int count = 0;
        for(int i=0; i < tempFilterArray.length;i++){
            if(op.validate(a[i])){
                tempFilterArray[count] = a[i];
                count++;
            }
        }
        return Arrays.copyOf(tempFilterArray,count);
    }

    //// 5.
    static int transformingMethod(int x){
        return x*2;
    }
    static boolean validatingMethod(int x){
        return x > 4 ? true : false;
    }
    ////
}
