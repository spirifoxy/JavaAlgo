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
    private static final Node nil = new Node(-1, null, null);
    private static RedBlackTree rbtNew = new RedBlackTree();;

    private static Node createNodeFromString(String line) {
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
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода");
            throw new RuntimeException(e);
        }
        return node;
    }
    
    private static void createTaskTree(Node node) {
         if (node.id == nil.id) {
            return;
        }
        boolean isGoodStudent = true;
        for (int i = 0; i < node.ex.length; i++) {
            if (node.ex[i]< 25) {
                isGoodStudent = false;
                break;
            }
        }
        if (isGoodStudent) {
            Node tNode = new Node (node.id, node.name, node.ex);
            rbtNew.insert(tNode);
        }
        createTaskTree(node.left);
        createTaskTree(node.right);
    }
    
    private static RedBlackTree readFile(String fileName) {
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
        RedBlackTree rbt;
        rbt = readFile("tree.dat");

        
        Scanner scan = new Scanner(System.in);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String menuStr;
        while (true) {
            menuStr = "\n1.- Добавить узел\n"
                    + "--2.- Нарисовать\n"
                    + "--3.- Вывод в консоль\n"
                    + "4.- Новое дерево\n";
            if (!rbtNew.isEmpty()) {
                    menuStr += "--5.- Нарисовать\n"
                            + "--6.- Вывод в консоль\n";
            }
            menuStr += "0.- Удалить дерево\n";
            System.out.println(menuStr);
            int choice = scan.nextInt();

            String line;
            
            Node node;
            switch (choice) {
                case 1:
                    while (!"stop".equals(line = in.readLine())) {
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
                    rbt.printTree(rbt.getRoot());
                    break;
                case 4:
                    /*
                    Выделить в новое дерево информацию о студентах, не имеющих неудовлетворительных оценок
                     */
                    rbtNew = new RedBlackTree();
                    createTaskTree(rbt.getRoot());
                    break;
                case 5:
                    if (rbtNew.isEmpty()) {
                        break;
                    }
                    RedBlackViewer frameNew = new RedBlackViewer(rbtNew);
                    frameNew.setSize(800, 600);
                    frameNew.setVisible(true);
                    break;
                case 6:
                    if (rbtNew.isEmpty()) {
                        break;
                    }
                    rbtNew.printTree(rbtNew.getRoot());
                    break;
                case 0:
                    /*
                    
                     */
                    break;
            }
        }
    }
}
