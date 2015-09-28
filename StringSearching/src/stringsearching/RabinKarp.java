package stringsearching;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author spirifoxy
 */
public class RabinKarp {

    private String pattern; //непосредственно шаблон
    private int pLength;    //и его длина
    private long primeNum;  //рандомное большое простое число
    private long patHash;   //хэш шаблона

    final int alphSize;
    private long alphSizeExp;   // alphSize^(pLength-1) % primeNum

    public RabinKarp(String pattern) { //конструктор
        this.pattern = pattern;
        pLength = pattern.length();
        primeNum = longRandomPrime();

        alphSize = 256;
        alphSizeExp = 1;
        for (int i = 1; i <= pLength - 1; i++) {
            alphSizeExp = (alphSize * alphSizeExp) % primeNum;
        }
        patHash = hash(pattern, pLength); //вычисление хэша шаблона
    }

    //получение случайного простого числа
    private long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    //хэширование
    private long hash(String key, int pLength) {
        long h = 0;
        for (int j = 0; j < pLength; j++) {
            h = (alphSize * h + key.charAt(j)) % primeNum;
        }
        return h;
    }

    //проверка на посимвольное соответствие
    private boolean check(String text, int i) {
        for (int j = 0; j < pLength; j++) {
            if (pattern.charAt(j) != text.charAt(i + j)) {
                return false;
            }
        }
        return true;
    }

    public String search(String text) {
        String result = "";
        int N = text.length();
        if (N < pLength) {
            return result;
        }
        long txtHash = hash(text, pLength);

        // проверка на совпадение в позиции 0
        if ((patHash == txtHash) && check(text, 0)) {
            result += "0 ";
        }

        // проверка на совпадение по хэшу; если есть - посимвольная проверка
        for (int i = pLength; i < N; i++) {
            // сдвиг на 1 символ по тексту 
            txtHash = (txtHash + primeNum - alphSizeExp * text.charAt(i - pLength) % primeNum) % primeNum;
            txtHash = (txtHash * alphSize + text.charAt(i)) % primeNum;

            // точная проверка
            int offset = i - pLength + 1;
            if ((patHash == txtHash) && check(text, offset)) {
                result += offset + " ";
            }
        }
        return result;
    }
}
