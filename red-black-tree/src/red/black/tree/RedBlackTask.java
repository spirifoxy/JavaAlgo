package red.black.tree;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author spirifoxy
 */
public class RedBlackTask {

    public static RedBlackTree readFile(String fileName) throws FileNotFoundException {
        
        RedBlackTree rbt = new RedBlackTree();
        
        File file = new File(fileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    String[] str = line.split("");
                    /*
                    name = str[0];
                    id = Integer.parseInt(str[1]);
                    for (int i=2; i<7; i++) {
                        ex[i] = Integer.parseInt(str[i]);
                    }
                    */
                }
            }
            finally {
                in.close();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rbt;
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }
}
