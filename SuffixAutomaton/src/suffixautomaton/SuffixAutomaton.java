package suffixautomaton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * <p>
 * 6. Даны строки S и T, требуется найти их наидлиннейшую общую подстроку за
 * О(|S| + |T|)</p>
 *
 * @author spirifoxy
 */
public class SuffixAutomaton {

    //конструктор состояния
    private static class state {

        int len, link;
        HashMap<Character, Integer> next = new HashMap<Character, Integer>(); //список переходов

        public state() {
        }
    }

    private static state[] st;
    private static int sz;      //размер 
    private static int last;    //состояние, соответствующее всей текущей строке до добавления символа 

    //инициализация автомата
    private static void saInit() {
        sz = last = 0;
        if (st[0] == null) {
            st[0] = new state();
        }
        st[0].len = 0;
        st[0].link = -1; //ссылка на несущещствующее состояние
        ++sz;

    }

    //добавление очередного символа в конец строки, перестраивая автомат
    private static void saExtend(char c) {
        int cur = sz++;
        if (st[cur] == null) {
            st[cur] = new state();
        }
        st[cur].len = st[last].len + 1;

        int p;
        for (p = last; p != -1 && !st[p].next.containsKey(c); p = st[p].link) {
            st[p].next.put(c, cur);
        }
        if (p == -1) {
            st[cur].link = 0;
        } else {
            int q = st[p].next.get(c);
            if (st[p].len + 1 == st[q].len) {
                st[cur].link = q;
            } else {
                //необходимо клонирование
                int clone = sz++;
                if (st[clone] == null) {
                    st[clone] = new state();
                }
                st[clone].len = st[p].len + 1;
                st[clone].next.putAll(st[q].next);

                st[clone].link = st[q].link;
                for (; p != -1 && st[p].next.get(c) == q; p = st[p].link) {
                    st[p].next.remove(c);
                    st[p].next.put(c, clone);
                }
                st[q].link = st[cur].link = clone;
            }
        }
        last = cur;
    }

    /**
     * 
     * @param s Первая строка
     * @param t Вторая строка
     * @return  Наидлиннейшая общая подстрока
     */
    private static String lcs(String s, String t) {
        
        saInit();
        //построение автомата по строке S
        for (int i = 0; i < (int) s.length(); ++i) {
            saExtend(s.charAt(i)); 
        }

        int v = 0, l = 0,
                best = 0, bestpos = 0;
        for (int i = 0; i < (int) t.length(); ++i) {
            while ((v > 0) && !st[v].next.containsKey(t.charAt(i))) {
                v = st[v].link;
                l = st[v].len;
            }
            if (st[v].next.containsKey(t.charAt(i))) {
                v = st[v].next.get(t.charAt(i));
                ++l;
            }
            if (l > best) {
                best = l;
                bestpos = i;
            }
        }
        return t.substring(bestpos - best + 1, bestpos + 1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String s, t, res;
        File textFile = new File("strings.txt");
        BufferedReader in = new BufferedReader(new FileReader(textFile));
        s = in.readLine();
        t = in.readLine();

        //выделение памяти по длиннейшей строке
        st = (s.length() > t.length())
                ? new state[(2 * s.length())]
                : new state[(2 * t.length())];

        res = lcs(s, t);
        System.out.println(res);
    }

}
