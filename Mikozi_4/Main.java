import java.util.*;
import java.io.*;
import java.math.BigInteger;
class RSA{
    BigInteger p, q, e, X1, Y2;
    BigInteger n, phi, d;
    public RSA(BigInteger p, BigInteger q, BigInteger e, BigInteger X1, BigInteger Y2){
        this.p = p;
        System.out.println("p = "+p);
        this.q = q;
        System.out.println("q = "+q);
        this.e = e;
        System.out.println("e = "+e);
        this.X1 = X1;
        System.out.println("X1 = "+X1);
        this.Y2 = Y2;
        System.out.println("Y2 = "+Y2);
        n = p.multiply(q);
        System.out.println("n = "+n);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        System.out.println("phi = "+phi);
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

    void gcd_e_phi(){
        if(gcd(e,phi)[0].equals(BigInteger.ONE)){
            System.out.println("Число е удовлетворяет условию, НОД равен 1.");
        }
        else{
            System.out.println("Число е не удовлетворяет условию.");
        }
    }
    BigInteger getD(){
        return d = gcd(phi,e)[1];
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
    BigInteger get_cipher_X1(){
        return BinPow(X1, e, n);
    }
    BigInteger get_X1(){
        return BinPow(BinPow(X1, e, n), d, n);
    }
    BigInteger get_X2(){
        return BinPow(Y2, d, n);
    }
}
public class Main {
    public static void main(String[] args) {
        BigInteger p = new BigInteger("749491517671339");
        BigInteger q = new BigInteger("1074991257344881");
        BigInteger e = new BigInteger("224872067131777233484376270407");
        BigInteger X1 = new BigInteger("469827559223869083025116205997");
        BigInteger Y2 = new BigInteger("135039041969537192266152584876");
        RSA rsa = new RSA(p,q,e,X1,Y2);
        rsa.gcd_e_phi();
        System.out.println("Личный ключ d: "+rsa.getD().mod(rsa.phi));
        System.out.println("Результат шифрования X1: "+rsa.get_cipher_X1());
        System.out.println("Результат дешифрования Y1: "+rsa.get_X1());
        System.out.println("Результат дешифрования Y2: "+rsa.get_X2());
    }
}