package RBT;

import RBT.RedBlackTree.Node;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author spirifoxy
 */
public class RedBlackTask {

    public static Node createNodeFromString(String line) {
        Node node;
        String name;
        int id;
        int[] ex = new int[5];

        try {
            String[] str = line.split(" ");

            name = str[0];
            id = Integer.parseInt(str[1]);
            for (int i = 0; i < 5; i++) {
                ex[i] = Integer.parseInt(str[i + 2]);
            }
            node = new Node(id, name, ex);
        }
        catch (NumberFormatException e) {
            System.out.println("Ошибка ввода");
            throw new RuntimeException(e);
        }
        return node;
    }

    public static RedBlackTree readFile(String fileName) {
        RedBlackTree rbt = new RedBlackTree();
        Node node;
        File file = new File(fileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    node = createNodeFromString(line);
                    rbt.insert(node);
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rbt;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        RedBlackTree rbt = new RedBlackTree();

        rbt = readFile("tree.dat");

        Scanner scan = new Scanner(System.in);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        
        while (true) {
            
            System.out.println("\n1.- Добавить узел\n"
                    + "2.- Нарисовать\n"
                    + "3.- Новое дерево\n"
                    + "4.- Вывод в консоль\n"
                    + "5.- Удалить дерево\n");
            int choice = scan.nextInt();

            String line;
            Node node;
            switch (choice) {
                case 1:
                    while ( !"stop".equals(line= in.readLine())) {
                        node = createNodeFromString(line);
                        rbt.insert(node);
                    }
                    rbt.printTree(rbt.getRoot());
                    break;
                case 2:
                    RedBlackViewer frame = new RedBlackViewer(rbt);
                    frame.setSize(800, 600);
                    frame.setVisible(true);
                    break;
                case 3:
                    /*
                    
                    */
                    break;
                case 4:
                    rbt.printTree(rbt.getRoot());
                    break;
                case 5:
                    /*
                    
                    */
                    break;
            }
        }
    }
}
