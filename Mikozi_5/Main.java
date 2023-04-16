import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;
import java.math.BigInteger;
class ES{
    BigInteger q, p, g;
    BigInteger e, d;
    BigInteger r, s;
    String M;
    public ES(){
    }
    BigInteger[] gcd (BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};
        }
        BigInteger x;
        BigInteger y;
        BigInteger x1 = BigInteger.ONE;
        BigInteger x2 = BigInteger.ZERO;
        BigInteger y1 = BigInteger.ONE;
        BigInteger y2 = BigInteger.ONE;
        while(b.compareTo(BigInteger.ZERO)==1){
            BigInteger q = a.divide(b);
            BigInteger r = a.subtract(q.multiply(b));
            x = x2.subtract(q.multiply(x1));
            y = y2.subtract(q.multiply(y1));

            a = b;
            b = r;
            x2 = x1;
            x1 = x;
            y2 = y1;
            y1 = y;
        }
        return new BigInteger[]{a, x2, y2};
    }

    BigInteger div_mod(BigInteger a, BigInteger mod){
        return gcd(mod,a)[1];
    }
    BigInteger BinPow(BigInteger a, BigInteger n, BigInteger m){
        if (n.equals(BigInteger.ZERO))
            return BigInteger.ONE;
        if ((n.mod(BigInteger.TWO)).equals(BigInteger.ONE))
            return ((BinPow(a, n.subtract(BigInteger.ONE), m)).multiply(a)).mod(m);
        else {
            BigInteger b = BinPow (a, n.divide(BigInteger.TWO), m);
            return (b.multiply(b)).mod(m);
        }
    }
    void Gen(BigInteger q){
        this.q = q;
        if(!q.isProbablePrime(10)){
            System.out.println("Число q не является простым.");
        }
        //1,2,3
        Random rand = new Random();
        BigInteger R;
        BigInteger R_0 = q.add(BigInteger.ONE).multiply(BigInteger.valueOf(4));
        while(true){
            do {
                R = new BigInteger((int)(Math.random() * R_0.bitLength()+1), rand);
            }
            while (R.compareTo(R_0) == 1 || R.mod(BigInteger.TWO).equals(BigInteger.ONE));
            p = q.multiply(R).add(BigInteger.ONE);
            if (BinPow(BigInteger.TWO, q.multiply(R), p).equals(BigInteger.ONE) && !BinPow(BigInteger.TWO, R, p).equals(BigInteger.ONE)) break;
        }
        //4,5
        BigInteger X;
        while(true) {
            do {
                X = new BigInteger((int) (Math.random() * p.bitLength() + 1), rand);
            }
            while (X.compareTo(p) == 1);
            g = BinPow(X, R, p);
            if(!g.equals(BigInteger.ONE)) break;
        }
        //6
        do {
            d = new BigInteger((int) (Math.random() * q.bitLength() + 1), rand);
        }
        while (d.compareTo(q) == 1);
        //7
        e = BinPow(g, d, p);
        System.out.println("Параметры ЭЦП:\nq = "+q+"\np = "+p+"\ng = "+g+"\nОткрытый и личный ключ:\ne = "+e+"\nd = "+d);
    }

    void Sign(String M) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(M.getBytes(StandardCharsets.UTF_8));
        BigInteger m = new BigInteger(hash);
        BigInteger k;
        Random rand = new Random();
        do {
            k = new BigInteger((int) (Math.random() * q.bitLength() + 1), rand);
        }
        while (k.compareTo(q) == 1 || k.equals(BigInteger.ZERO));
        r = BinPow(g,k,p);
        s = ((div_mod(k,q)).multiply(m.subtract(d.multiply(r)))).mod(q);
        System.out.println("\nПодпись:\nr = "+r+"\ns = "+s);

    }
    boolean Verify(String M) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(M.getBytes(StandardCharsets.UTF_8));
        BigInteger m = new BigInteger(hash);
        if(r.compareTo(p)==1 || r.compareTo(BigInteger.ZERO) == 0){
            System.out.println("Подпись некорректна.");
            return false;
        }
        BigInteger C1 = BinPow(e,r,p).multiply(BinPow(r,s,p)).mod(p);
        BigInteger C2 = BinPow(g, m, p);
        if(C1.compareTo(C2)==0){
            System.out.println("Подпись корректна.");
            return true;
        }
        else {
            System.out.println("Подпись некорректна.");
            return false;
        }
    }


}
public class Main {


    public static void main(String[] args) throws NoSuchAlgorithmException {
        ES es = new ES();
        Scanner scan = new Scanner(System.in);
        String str;
        System.out.println("Введите 1, 2, 3 или 4 в зависимости от того, какую команду желаете выполнить:\n1. Gen\n2. Sign\n3. Verify\n4. Exit");
        while (true){
            str = scan.nextLine();
            if(str.equals("1")){
                System.out.println("Введите параметр q:");
                str = scan.nextLine();
                es.Gen(new BigInteger(str));
            }
            else if(str.equals("2")){
                System.out.println("Введите строку M:");
                str = scan.nextLine();
                es.Sign(str);
            }
            else if(str.equals("3")){
                System.out.println("Введите строку M:");
                str = scan.nextLine();
                es.Verify(str);
            }
            else if(str.equals("4")){
                break;
            }
            else {
                System.out.println("Некорректно введена команда, попробуйте снова.");
            }
            System.out.println("Введите команду:");
        }

    }
}