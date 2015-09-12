package RBT;

/**
 *
 * @author spirifoxy
 */
public class RedBlackNode {

    RedBlackNode left, right;
    int element;
    int color;

    
    public RedBlackNode(int theElement) {
        this(theElement, null, null);
    }
    
    public RedBlackNode(int theElement, RedBlackNode lt, RedBlackNode rt) {
        left = lt;
        right = rt;
        element = theElement;
        color = 1;

    }
}
