package RBT;

import RBT.RedBlackTree.Node;
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class RedBlackViewer extends JFrame {

    /**
     *
     * @author spirifoxy
     */
    private static final RedBlackTree.Node nil = new RedBlackTree.Node(-1, null, null);
    private static final int BLACK = 1;
    private static final int RED = 0;

    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();

    int xRoot = 300, yRoot = 0;
    int xLeft = 100;
    int xRight = 500;

    public RedBlackViewer(RedBlackTree rbt) {
        super("RBT");

        String text = getNodeInfo(rbt.getRoot());
        Object root = graph.insertVertex(parent, null, text, xRoot, 20, 80, 60, "ROUNDED;fillColor=black");
        printTree(rbt.getRoot(), root, xRoot, yRoot, 1, -1);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    public void printTree(Node node, Object root, int x, int y, int delta, int side) {
        if (node.id == nil.id) {
            return;
        }
        mxStylesheet stylesheet = graph.getStylesheet();

        Map<String, Object> vertexStyleRed = new HashMap<String, Object>();
        vertexStyleRed.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        vertexStyleRed.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ff0000");

        Map<String, Object> vertexStyleBlack = new HashMap<String, Object>();
        vertexStyleBlack.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        vertexStyleBlack.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#000000");

        String text;

        if (side == -1) {
            x = xLeft;
        } else {
            x = xRight;
        }

        y += 80;

        text = getNodeInfo(node.left);
        graph.getModel().beginUpdate();
        if (node.left.color == 1) { //черный
            stylesheet.setDefaultVertexStyle(vertexStyleBlack);
        } else {
            stylesheet.setDefaultVertexStyle(vertexStyleRed);
        }
        graph.setStylesheet(stylesheet);
        Object leftChild = graph.insertVertex(parent, null, text, x -= 40, y, 80, 40);
        graph.getModel().endUpdate();

        if (delta > 0) {
            x = xRight;
            delta--;
        }

        text = getNodeInfo(node.right);
        graph.getModel().beginUpdate();
        if (node.right.color == 1) { //черный
            stylesheet.setDefaultVertexStyle(vertexStyleBlack);
        } else {
            stylesheet.setDefaultVertexStyle(vertexStyleRed);
        }
        graph.setStylesheet(stylesheet);
        Object rightChild = graph.insertVertex(parent, null, text, x += 180, y, 80, 40);
        graph.getModel().endUpdate();

        graph.insertEdge(parent, null, "", root, leftChild);
        graph.insertEdge(parent, null, "", root, rightChild);

        printTree(node.left, leftChild, x, y, delta, -1);
        printTree(node.right, rightChild, x, y, delta, 1);
    }

    public String getNodeInfo(Node node) {
        String text;
        if (node.id == nil.id) {
            text = "NIL";
            return text;
        }
        text = node.id + "\n" + node.name + "\n";
        for (int i = 0; i < node.ex.length; i++) {
            text += node.ex[i] + " ";
        }
        return text;
    }
}
