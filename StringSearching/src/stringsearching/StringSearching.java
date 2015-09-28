package stringsearching;

import java.io.*;

/**
 * <p>
 * 13. Сравнить по быстродействию алгоритм Бойера-Мура и алгоритм Рабина-Карпа
 * при поиске в тексте одного из k образцов</p>
 *
 * @author spirifoxy
 */
public class StringSearching {

    private static long getCurrentTime() { //текущее время в мс
        return System.currentTimeMillis();
    }

    private static long countPastTime(long time) { //прошедшее время в мс
        return getCurrentTime() - time;
    }

    public static void main(String[] args) throws IOException {
        long timeOut;
        BoyerMoore bm;
        RabinKarp rk;
        File textFile;
        BufferedReader in;
        String text = "";
        String pattern, line;
        textFile = new File("text.txt");

        in = new BufferedReader(new FileReader(textFile));
        while ((line = in.readLine()) != null) {
            text += line;
        }

        in = new BufferedReader(new InputStreamReader(System.in));
        pattern = in.readLine();

        timeOut = getCurrentTime();
        bm = new BoyerMoore(pattern);
        String result = bm.search(text);
        System.out.println(result);
        timeOut = countPastTime(timeOut);
        System.out.println("BM time: " + timeOut);

        timeOut = getCurrentTime();
        rk = new RabinKarp(pattern);
        result = rk.search(text);
        System.out.println(result);
        timeOut = countPastTime(timeOut);
        System.out.println("RK time: " + timeOut);

        in.close();
    }

}
