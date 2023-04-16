import java.util.*;
import java.io.*;
class SP {
    Integer X;
    Integer k = 3403;
    Integer[] R1 = {1, 4, 7, 10, 2, 5, 8, 11};
    Integer[] R2 = {2, 5, 8, 11, 3, 6, 9, 12};
    Integer[] R3 = {3, 6, 9, 12, 10, 4, 7, 1};
    String Hex ="0123456789abcdef";
    String S1 = "1d297a608c45f3be";
    String S2 = "baf50ce8623917d4";
    Integer[] P = {1, 4, 7, 2, 5, 8, 3, 6};
     public SP(Integer X){
         this.X = X;
    }
    public String GetCipher(){
        //Сложение по раундовым ключам
         String k_bin = Integer.toBinaryString(k);
         String cipher = "";
         String R1_bin = "";
         String R2_bin = "";
         String R3_bin = "";
         for(int i = 0; i < R1.length; i++){
             R1_bin+=k_bin.charAt(R1[i]-1);
             R2_bin+=k_bin.charAt(R2[i]-1);
             R3_bin+=k_bin.charAt(R3[i]-1);
         }
         Integer R1_int = Integer.parseInt(R1_bin, 2);
         Integer R2_int = Integer.parseInt(R2_bin, 2);
         Integer R3_int = Integer.parseInt(R3_bin, 2);
         X = (X+R1_int)%256;
         X = (X+R2_int)%256;
         X = (X+R3_int)%256;
        //Разбиение на T1, T2
        String X_bin = Integer.toBinaryString(X);
        while (X_bin.length()!=8){
            X_bin = "0"+X_bin;
        }
        String T1 = X_bin.substring(0,4);
        String T2 = X_bin.substring(4,8);
        //S1, S2
        String T1_hex = Integer.toHexString(Integer.parseInt(T1, 2));
        String T2_hex = Integer.toHexString(Integer.parseInt(T2, 2));
        T1_hex = ""+S1.charAt(Hex.indexOf(T1_hex));
        T2_hex = ""+S2.charAt(Hex.indexOf(T2_hex));
        T1 = Integer.toBinaryString(Integer.parseInt(T1_hex, 16));
        T2 = Integer.toBinaryString(Integer.parseInt(T2_hex, 16));
        while(T1.length()!=4){
            T1 = "0"+T1;
        }
        while(T2.length()!=4){
            T2 = "0"+T2;
        }
        X_bin = T1 + T2;
        //P-блок
        for(int i = 0; i < P.length; i++){
            cipher+=X_bin.charAt(P[i]-1);
        }

         return cipher;
    }
}

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        SP sp = new SP(70);
        System.out.println("Зашифрованное число 70 (01000110):\n"+sp.GetCipher());
        SP sp_2 = new SP(78);
        System.out.println("Зашифрованное число 78 (01010110):\n"+sp_2.GetCipher());
    }
}