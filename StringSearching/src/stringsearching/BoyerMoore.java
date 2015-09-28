package stringsearching;

/**
 *
 * @author spirifoxy
 */
public class BoyerMoore {

    private int[] symbolsShift; //таблица смещений
    private String pattern;

    //конструктор
    public BoyerMoore(String pattern) {
        final int alphabetSize = 256;
        this.pattern = pattern;
        symbolsShift = new int[alphabetSize];

        for (int i = 0; i < alphabetSize; i++) {
            symbolsShift[i] = -1;
        }
        //формируем смещения
        for (int i = 0; i < pattern.length(); i++) {
            symbolsShift[pattern.charAt(i)] = i;
        }
    }

    /**
     *
     * @param text Строка, в которой осуществляется поиск
     * @return result Строка, содержащая все позиции вхождения шаблона
     */
    public String search(String text) {
        String result = "";
        int pLength = pattern.length();
        int tLength = text.length();
        int skip;
        for (int i = 0; i <= tLength - pLength; i += skip) {
            skip = 0;
            for (int j = pLength - 1; j >= 0; j--) { //проверка с последнего сиивола
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    //смещение или на 1 символ, или на длину проверенной части шаблона
                    skip = Math.max(1, j - symbolsShift[text.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                result += i + " ";  // найдено
                skip = pLength;     // смещение на длину шаблона
            }
        }
        return result;
    }

}
