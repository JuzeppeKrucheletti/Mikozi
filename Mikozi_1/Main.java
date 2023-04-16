import java.util.*;
import java.io.*;
class Af {
    String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    Integer a;
    Integer b;
    public Af(int a, int b){
        this.a = a;
        this.b = b;
    }
    public String GetCipher(String str){
        String cipher = "";
        for(int i = 0; i < str.length(); i++){
            int num = alphabet.indexOf(str.charAt(i));
            cipher = cipher+alphabet.charAt((a*num+b)%alphabet.length());
        }
        return cipher;
    }
}
class SR {
    String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    String key;
public SR(String key){
    this.key = key;
}
public String GetText(String str){
    String text = "";
    for(int i = 0; i < str.length(); i++){
        text = text + alphabet.charAt(key.indexOf(str.charAt(i)));
    }
    return text;
}
}
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Af A = new Af(17,24);
        System.out.println("Зашифрованный с помощью афинного шифра текст:\n"+A.GetCipher("криптосистема"));
        SR S = new SR("ИЮЧЪЩЁЗМШСХУРЦЭЬЫНФДБТВЛГАПЯЕОЖКЙ");
        System.out.println("Расшифровка шифртекста простой замены:\n"+S.GetText("ЩМЁУТНБД"));
    }
}
