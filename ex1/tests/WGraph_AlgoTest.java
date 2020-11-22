package ex1.tests;

import ex1.src.*;

import java.util.List;

public class WGraph_AlgoTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0,4, 29);
        g.connect(0,1, 41);
        g.connect(0,2, 17);
        g.connect(4,1, 8);
        g.connect(4,2, 9);
        g.connect(3,2, 10);

        Test2(g);

        System.out.println("Test 2 Runtime: " + (System.currentTimeMillis() - start)/1000F + "\n");

        start = System.currentTimeMillis();

        Test3(g);

        System.out.println("Test 3 Runtime: " + (System.currentTimeMillis() - start)/1000F + "\n");
    }

    // Shortest path + distance test
    public static void Test2(weighted_graph g) {
        weighted_graph_algorithms a = new WGraph_Algo(g);

        for(int i = 0; i < g.nodeSize(); i++) {
            for(int j = 0; j < g.nodeSize(); j++) {
                System.out.println("Shortest path from " + i + " to " + j + " is: " + a.shortestPathDist(i, j));
                List<node_info> path = a.shortestPath(i,j);
                System.out.println("Path: " + ((path == null) ? "None" : path.toString()) + "\n");
            }
        }
    }

    // Save, Load, Init and copy test
    public static void Test3(weighted_graph g) {
        weighted_graph_algorithms a = new WGraph_Algo(g);
        weighted_graph temp = new WGraph_DS();
        weighted_graph temp2 = new WGraph_DS();

        a.save("graph1.txt");
        a.load("mistake.txt"); // file not found exception
        a.load("graph1.txt");

        temp = a.getGraph();

        a.init(temp);

        temp2=a.copy();

        System.out.println(g);
        System.out.println(temp);
        System.out.println(temp2);
    }
}
