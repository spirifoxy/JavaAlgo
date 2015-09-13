package RBT;

/**
 *
 * @author spirifoxy
 */
public class RedBlackTree {

    private static final int BLACK = 1;
    private static final int RED = 0;

    public static class Node {

        Node left = nil, right = nil, parent = nil;
        int color = BLACK;
        String name;
        int id;
        int[] ex = new int[5];

        public Node(int id, String name, int[] ex) {
            this.id = id;
            this.name = name;
            this.ex = ex;
        }
        
        public Node getParent() {
            return parent;
        }
    }

    private static final Node nil = new Node(-1, null, null);
    private Node root;

    public RedBlackTree() {
        root = nil;
    }

    private boolean isEmpty() {
        return root == nil;
    }
    
    public Node getRoot() {
        return root;
    }
    
    
    private void deleteTree() {
        root = nil;
    }

    public void insert(Node node) {
        Node temp = root;
        if (root == nil) {
            root = node;
        } else {
            node.color = RED;
            while (true) {
                if (node.id < temp.id) {
                    if (temp.left == nil) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.id > temp.id) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    private void fixTree(Node node) {
        while (node.parent.color == RED) {
            Node uncle = nil;
            if (node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;

                if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.right) {
                    //нужен двойной поворот
                    node = node.parent;
                    rotateLeft(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                rotateRight(node.parent.parent);
            } else {
                uncle = node.parent.parent.left;
                if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    //нужен двойной поворот
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                rotateLeft(node.parent.parent);
            }
        }
        root.color = BLACK;
    }

    void rotateLeft(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {    //поворот корня
            Node right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    void rotateRight(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {    //поворот корня
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    
    public void printTree(Node node) {
        if (node == nil) {
            return;
        }
        printTree(node.left);
        System.out.print(((node.color == RED) ? "Цвет: К " : "Цвет: Ч ") + "ID: " + node.id + " Родитель: " + node.parent.id + "\n");
        printTree(node.right);
    }
}
