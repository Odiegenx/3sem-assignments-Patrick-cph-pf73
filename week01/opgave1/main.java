package opgave1;

import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        // Opgave 1:
        ArithmeticOperation addtion = (a,b) -> a+b;
        ArithmeticOperation substraction = (a,b) -> a-b;
        ArithmeticOperation multiplication = (a,b) -> a*b;
        ArithmeticOperation division = (a,b) -> a/b;
        ArithmeticOperation modulus = (a,b) -> a%b;
        ArithmeticOperation power = (a,b) -> (int) Math.pow(a,b);
        int[] aarray = {3,7,5};
        int[] barray = {4,8,44,55};
        System.out.println(operate(3, 4, addtion));
        System.out.println(operate(3, 4, substraction));
        System.out.println(operate(3, 4, multiplication));
        System.out.println(operate(3, 4, division));
        System.out.println(operate(3, 4, modulus));
        System.out.println(operate(3, 4, power));
        System.out.println(Arrays.toString(operate(aarray, barray, addtion)));
        System.out.println(Arrays.toString(operate(aarray, barray, substraction)));
        System.out.println(Arrays.toString(operate(aarray, barray, multiplication)));
        System.out.println(Arrays.toString(operate(aarray, barray, division)));
        System.out.println(Arrays.toString(operate(aarray, barray, modulus)));
        System.out.println(Arrays.toString(operate(aarray, barray, power)));
    }
    static int operate(int a,int b, ArithmeticOperation op){
        return op.perform(a,b);
    }
    static int[] operate(int[] a,int[] b,ArithmeticOperation op){
        int arrayLength = a.length <= b.length ? a.length : b.length;
        int[] newArray = new int[arrayLength];
        for (int i = 0;i < a.length ; i++){
            newArray[i] = operate(a[i],b[i],op);
        }
        return newArray;
    }
}
