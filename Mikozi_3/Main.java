import java.util.*;
import java.io.*;
class GeffeGen {
    Integer[] a1 = {0,1,1,0,1};
    Integer[] a2 = {1,0,0,0,0,1,0};
    Integer[] a3 = {0,0,1,1,0,0,1,0};
    Integer[] c1 = {0,1,1,1,1};
    Integer[] c2 = {1,0,0,0,1,1,1};
    Integer[] c3 = {0,1,0,0,1,1,0,1};
    Integer n;
    Integer count1 = 0, count2 = 0, count3 = 0;
    Integer[] P1, P2, P3;
    Integer[][] A1, A2, A3;
    Integer[] GeffePosl;
    public GeffeGen(Integer n){
        this.n = n;
        GeffePosl = new Integer[n];
        P1 = new Integer[n];
        P2 = new Integer[n];
        P3 = new Integer[n];
        A1 = new Integer[(int)Math.pow(2, a1.length)][a1.length];
        A2 = new Integer[(int)Math.pow(2, a2.length)][a2.length];
        A3 = new Integer[(int)Math.pow(2, a3.length)][a3.length];

        Integer x1, x2, x3;
        for(int i = 0; i < n; i++){
            //
            if(i<Math.pow(2, a1.length)) A1[i] = a1.clone();
            if(i<Math.pow(2, a2.length)) A2[i] = a2.clone();
            if(i<Math.pow(2, a3.length)) A3[i] = a3.clone();
            //
            P1[i] = Rglos_1();
            P2[i] = Rglos_2();
            P3[i] = Rglos_3();
            GeffePosl[i] = ((P1[i]*P2[i])+((P1[i]+1)%2)*P3[i])%2;
            //System.out.println(GeffePosl[i]);
        }
    }
    public Integer Rglos_1(){
        Integer r = 0;
        Integer res = a1[0];
        for(int i = 0; i < c1.length; i++){
            r+=(c1[i]*a1[i]);
        }
        r%=2;
        for(int i = 1; i < c1.length; i++){
            a1[i-1] = a1[i];
        }
        a1[a1.length-1] = r;
        return res;
    }
    public Integer Rglos_2(){
        Integer r = 0;
        Integer res = a2[0];
        for(int i = 0; i < c2.length; i++){
            r+=(c2[i]*a2[i]);
        }
        r%=2;
        for(int i = 1; i < c2.length; i++){
            a2[i-1] = a2[i];
        }
        a2[a2.length-1] = r;
        return res;
    }
    public Integer Rglos_3(){
        Integer r = 0;
        Integer res = a3[0];
        for(int i = 0; i < c3.length; i++){
            r+=(c3[i]*a3[i]);
        }
        r%=2;
        for(int i = 1; i < c3.length; i++){
            a3[i-1] = a3[i];
        }
        a3[a3.length-1] = r;
        return res;
    }


    public String Count_0_1(){
        Integer a = 0, b = 0;
        for(int i = 0; i < n; i++){
            a+=GeffePosl[i];
        }
        b = n - a;
        return b.toString() + ", "+a.toString();
    }
    public String R(){
        Integer[] R = new Integer[5];
        String res = "";
        for(int i = 0; i < 5; i++){
            R[i] = 0;
            res +="r_"+(i+1)+": ";
            for(int j = 0; j < n - i - 1; j++){
                R[i]+= (int)Math.pow(-1, (GeffePosl[j]+GeffePosl[j+i+1])%2);
            }
            res+=R[i]+"\n";
        }

        return res;
    }
    public void Zyklus(){
        for(int i = 0; i < Math.pow(2, a1.length); i++){
            for(int j = 0; j < Math.pow(2, a1.length); j++){
                boolean b = true;
                for(int k = 0; k < a1.length; k++){
                    if(A1[i][k]!=A1[j][k]){
                        b = false;
                        break;
                    }
                }
                if(i!=j && b){
                    count1 = Math.abs(i-j);
                    System.out.println("Длина цикла РГЛОС №1: "+count1);
                    for(int k = i; k < j; k++){
                        System.out.print(P1[k]);
                    }
                    System.out.print("\n");
                    break;
                }
            }
           if(count1!=0) break;
        }
        for(int i = 0; i < Math.pow(2, a2.length); i++){
            for(int j = 0; j < Math.pow(2, a2.length); j++){
                boolean b = true;
                for(int k = 0; k < a2.length; k++){
                    if(A2[i][k]!=A2[j][k]){
                        b = false;
                        break;
                    }
                }
                if(i!=j && b){
                    count2 = Math.abs(i-j);
                    System.out.println("Длина цикла РГЛОС №2: "+count2);
                    for(int k = i; k < j; k++){
                        System.out.print(P2[k]);
                    }
                    System.out.print("\n");
                    break;
                }
            }
            if(count2!=0) break;
        }
        for(int i = 0; i < Math.pow(2, a3.length); i++){
            for(int j = 0; j < Math.pow(2, a3.length); j++){
                boolean b = true;
                for(int k = 0; k < a3.length; k++){
                    if(A3[i][k]!=A3[j][k]){
                        b = false;
                        break;
                    }
                }
                if(i!=j && b){
                    count3 = Math.abs(i-j);
                    System.out.println("Длина цикла РГЛОС №3: "+count3);
                    for(int k = i; k < j; k++){
                        System.out.print(P3[k]);
                    }
                    System.out.print("\n");
                    break;
                }
            }
            if(count3!=0) break;
        }

    }



}

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        GeffeGen G = new GeffeGen(10000);
        System.out.println("Число нулей и единиц: "+G.Count_0_1());
        System.out.println("Статистики r_i:\n"+G.R());
        G.Zyklus();
    }
}