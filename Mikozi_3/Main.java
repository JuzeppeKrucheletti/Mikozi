import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Main {

    static class BigInt{
        BigInteger p;
        BigInteger q;
        BigInteger n;
        BigInteger phi;

        BigInteger x;
        BigInteger y;

        BigInt(String s1, String s2) {
            p = new BigInteger(s1);
            q = new BigInteger(s2);
            n = p.multiply(q);
            phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        }

        BigInt(BigInteger p, BigInteger q) {
            this.p = p;
            this.q = q;
            n = p.multiply(q);
            phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        }

        public void setN(BigInteger n) {
            this.n = n;
        }

        public void setP(BigInteger p) {
            this.p = p;
        }

        public void setQ(BigInteger q) {
            this.q = q;
        }

        public void setPhi(BigInteger phi) {
            this.phi = phi;
        }

        public void setX(BigInteger x) {
            this.x = x;
        }

        public void setY(BigInteger y) {
            this.y = y;
        }

        public BigInteger getN() {
            return n;
        }

        public BigInteger getP() {
            return p;
        }

        public BigInteger getQ() {
            return q;
        }

        public BigInteger getPhi() {
            return phi;
        }

        public BigInteger getX() {
            return x;
        }

        public BigInteger getY() {
            return y;
        }

    }

    static BigInteger E_GCD(BigInt A) {
        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ZERO;

        BigInteger a = A.getP();
        BigInteger b = A.getQ();

        BigInteger u = BigInteger.ZERO;
        BigInteger v = BigInteger.ONE;

        while (!b.equals(BigInteger.ZERO)) {
            BigInteger q = a.divide(b);
            BigInteger b1 = b;
            b = a.subtract(q.multiply(b));
            a = b1;
            BigInteger u1 = u;
            u = x.subtract(q.multiply(u));
            x = u1;
            BigInteger v1 = v;
            v = y.subtract(q.multiply(v));
            y = v1;
        }

        A.setX(x);
        A.setY(y);

        return a;
    }

    static BigInteger Generation(BigInt A, String e_) {
        System.out.println("P = " + A.getP());
        System.out.println("Q = " + A.getQ());
        System.out.println("N = " + A.getN());
        System.out.println("Phi = " + A.getPhi());


        BigInteger e = new BigInteger(e_);
        BigInt B = new BigInt(e, A.getPhi());
        BigInteger g = E_GCD(B);

        if (!g.equals(BigInteger.ONE)) {
            System.out.println("Неверные числа.");
            return BigInteger.valueOf(-1);
        }
        else {
            BigInteger x = B.getX();
            return x.mod(A.getPhi());
        }
    }

    static BigInteger bin_pow(BigInteger x, BigInteger n, BigInteger mod) {
        if (n.equals(BigInteger.ONE)) {
            return x;
        }
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            BigInteger t = bin_pow(x, n.divide(BigInteger.valueOf(2)), mod);
            t = t.mod(mod);
            return (t.multiply(t)).mod(mod);
        }
        else {
            BigInteger t = (bin_pow(x, n.subtract(BigInteger.ONE), mod));
            t = t.mod(mod);
            return (t.multiply(x)).mod(mod);
        }
    }

    static BigInteger Encr(BigInteger x, BigInteger n, BigInteger mod) {
        return bin_pow(x, n, mod);
    }

    static BigInteger Decr(BigInteger y, BigInteger n, BigInteger mod) {
        return bin_pow(y, n, mod);
    }

    public static void main(String[] args) {
        String p = "238152302818741";
        String q = "248915649177017";
        String e = "23698707635302804768789375937";
        String X1 = "19720251342243551814671982083";
        String Y2 = "54701360477698356356384573421";
        BigInt A = new BigInt(p, q);

        BigInteger d = Generation(A, e);
        System.out.println("Личный ключ: " + d);

        BigInteger res_encr = Encr(new BigInteger(X1), new BigInteger(e), A.getN());
        System.out.println("Результат шифрования: " + res_encr);

        BigInteger res_decr1 = Decr(res_encr, d, A.getN());
        System.out.println("Результат дешифрования Y1: " + res_decr1);

        BigInteger res_decr2 = Decr(new BigInteger(Y2), d, A.getN());
        System.out.println("Результат дешифрования Y2: " + res_decr2);
    }
}