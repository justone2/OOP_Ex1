package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public class WGraph_DS implements Serializable, weighted_graph {
    private static final long serialVersionUID = 1L;

    private HashMap<Integer, node_info> v;
    private HashMap<Vector, Double> graph_edges;

    private int edge_counter;
    private int node_counter;
    private int mode_counter;

    public WGraph_DS() {
        this.v = new HashMap<>();
        this.graph_edges = new HashMap<>();

        edge_counter = 0;
        node_counter = 0;
        mode_counter = 0;
    }

    public node_info getNode(int key) {
        if(this.v.containsKey(key))
            return this.v.get(key);

        return null;
    }

    public boolean hasEdge(int node1, int node2) {
        return this.graph_edges.containsKey(getVector(node1, node2));
    }

    public double getEdge(int node1, int node2) {
        Vector vec = getVector(node1, node2);

        if(this.graph_edges.containsKey(vec))
            return this.graph_edges.get(vec);

        return -1;
    }

    public void addNode(int key) {
        if(!v.containsKey(key)) {
            NodeInfo node = new NodeInfo(key);

            v.put(key, node);

            node_counter++;
            mode_counter++;
        }
    }

    public void connect(int node1, int node2, double w) {
        Vector vec = getVector(node1, node2);

        if(node1 != node2 && !graph_edges.containsKey(vec)) {
            graph_edges.put(vec, w);

            edge_counter++;
            mode_counter++;
        }
    }

    public Collection<node_info> getV() { return this.v.values(); }

    /** O(n) **/
    public Collection<node_info> getV(int node_id) {
        ArrayList<node_info> list = new ArrayList<>();

        for(node_info temp : v.values()) {
            Vector vec = getVector(node_id, temp.getKey());

            if(graph_edges.containsKey(vec)) {
                temp.setTag(graph_edges.get(vec));
                list.add(temp);
            }
        }

        return list;
    }

    public node_info removeNode(int key) {
        if(this.v.containsKey(key)) {
            node_info node = this.v.get(key);

            this.v.remove(key);

            mode_counter++;
            node_counter--;

            if(v.values().size() > 0) {
                for (node_info temp : v.values()) {
                    Vector vec = getVector(key, temp.getKey());

                    removeEdge(temp.getKey(), key);
                }
            }

            return node;
        }

        return null;
    }

    public void removeEdge(int node1, int node2) {
        Vector vec = getVector(node1, node2);

        if(this.graph_edges.containsKey(vec)) {
            graph_edges.remove(vec);

            mode_counter++;
            edge_counter--;
        }
    }

    public int getMC() {                    return this.mode_counter; }
    public int nodeSize() {                 return this.node_counter; }
    public int edgeSize() {                 return this.edge_counter; }

    @Override
    public String toString() {
        return "Nodes[" + node_counter + "]: " + v.toString()
                + "\nEdges[" + edge_counter + "]: " + graph_edges.toString() + "\nMode Count: " + mode_counter;
    }

    public static Vector getVector(int node1, int node2) {
        Vector vec = new Vector();

        if(node1 < node2) {
            node1 += node2;
            node2 = node1 - node2;
            node1 -= node2;
        }

        vec.add(node1);
        vec.add(node2);

        return vec;
    }

    public class NodeInfo implements Serializable, node_info {
        private int key;
        private String info;
        private double tag;

        public NodeInfo(int key) {
            this.key = key;
            this.info = "";
            this.tag = 0.0;
        }

        public int getKey() {               return this.key; }
        public String getInfo() {           return this.info; }
        public double getTag() {            return this.tag;  }

        public void setInfo(String info) {  this.info = info;  }
        public void setTag(double tag) {    this.tag = tag;  }

        @Override
        public String toString() {
            return "[K:" + this.key
                    + ",I:" + ((this.info == "") ? "null" : this.info)
                    + ",T:" + String.format("%.2f",this.tag) + "]";
        }
    }
}
