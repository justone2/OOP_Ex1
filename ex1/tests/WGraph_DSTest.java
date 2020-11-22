package ex1.tests;

import ex1.src.*;

public class WGraph_DSTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Test1();
        System.out.println("Test 1 Runtime: " + (System.currentTimeMillis() - start)/1000F + "\n");
    }

    public static weighted_graph createGraph(int node_range, int edge_range) {
        weighted_graph g = new WGraph_DS();

        int i = 0;

        while(i < node_range) {
            g.addNode(i++);
        }

        while(g.edgeSize() < edge_range) {
            int a = (int)(Math.random() * node_range);
            int b = (int)(Math.random() * node_range);
            double w = Math.max(50,Math.random() * 100);

            g.connect(a, b, w);
        }

        return g;
    }

    // Random Graph Creator
    public static void Test1() {
        int i = 0;

        while(i++ < 5) {
            int nodes = Math.max(50,(int)(Math.random() * 1000000));
            int edges = nodes * Math.max(2,(int)(Math.random() * 10));
            weighted_graph g = createGraph(nodes, edges);
            System.out.println("Successfully created graph with " + nodes + " nodes and " + edges + " edges!");
        }
    }
}
