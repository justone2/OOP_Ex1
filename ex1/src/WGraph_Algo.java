package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    enum Type {
        Save,
        Load
    }

    private weighted_graph g;
    private double shortestPathValue;

    public WGraph_Algo(weighted_graph g) {
        init(g);
    }

    public void init(weighted_graph g) {
        this.g = g;
        this.shortestPathValue = 0.0;
    }

    public weighted_graph getGraph() {
        return this.g;
    }

    public weighted_graph copy() {
        weighted_graph newGraph = new WGraph_DS();

        if(g.getV() == null)
            return null;
        //Copy Nodes
        for(node_info n : g.getV()) {
            int key = n.getKey();

            newGraph.addNode(key);
            node_info node = newGraph.getNode(key);
            node.setTag(n.getTag());
            node.setInfo(n.getInfo());

            ArrayList<node_info> neighbours = (ArrayList<node_info>)this.g.getV(key);

            if(neighbours != null) {
                for(node_info n2 : neighbours) {
                    newGraph.connect(key, n2.getKey(), n2.getTag());
                }
            }
        }

        return newGraph;
    }

    public boolean isConnected() {
        Collection<node_info> ver = g.getV();

        if(ver == null)
            return true;

        Iterator<node_info> iterator = ver.iterator();

        if (!iterator.hasNext())
            return true;

        boolean[] visited = new boolean[g.nodeSize()];

        int i = 1;

        Stack<node_info> stack = new Stack<>();

        node_info start = iterator.next();

        stack.push(start);
        visited[start.getKey()] = true;

        while(!stack.isEmpty()) {
            node_info node = stack.pop();

            for(node_info neighbour : g.getV(node.getKey())) {
                if(!visited[neighbour.getKey()]) {
                    stack.push(neighbour);
                    visited[neighbour.getKey()] = true;
                    i++;
                }
            }
        }

        if(i != g.getV().size())
            return false;

        return true;
    }

    public double shortestPathDist(int src, int dest) {
        if(src == dest)
            return 0;

        CalculateDist(src);

        node_info result = g.getNode(dest);

        if(result == null)
            return -1;

        String info = result.getInfo();
        String[] args = info.split(",");

        double path = Double.parseDouble(args[1]);

        return path == 0 ? -1 : path;
    }

    public List<node_info> shortestPath(int src, int dest) {
        if(src == dest)
            return null;

        CalculateDist(src);

        node_info destNode = g.getNode(dest);
        node_info srcNode = g.getNode(src);

        if(destNode == null || srcNode == null)
            return null;

        Stack<node_info> stack = new Stack<>();
        ArrayList<node_info> result = new ArrayList<>();

        stack.add(destNode);

        node_info temp = destNode;

        while(temp.getKey() != srcNode.getKey()) {
            String info = temp.getInfo();
            String[] args = info.split(",");

            int key = Integer.parseInt(args[0]);

            node_info node = g.getNode(key);

            if(node != null) {
                stack.add(node);
                temp = node;
            }
        }

        while(!stack.isEmpty())
            result.add(stack.pop());

        return result;
    }

    public void CalculateDist(int src) {
        node_info srcNode = g.getNode(src);

        if(srcNode == null)
            return;

        for(node_info n : g.getV())
            n.setInfo(src+","+0);

        Stack<Integer> stack = new Stack<>();

        boolean[] visited = new boolean[g.nodeSize()];

        stack.add(src);

        while(!stack.isEmpty()) {
            int id = stack.pop();

            node_info node = g.getNode(id);

            if(node == null)
                continue;

            if(!visited[id]) {
                visited[id] = true;

                for (node_info ni : g.getV(node.getKey())) {
                    int id2 = ni.getKey();

                    stack.add(id2);
                    node_info temp = g.getNode(id2);

                    String tempData = temp.getInfo();
                    String nodeData = node.getInfo();

                    String[] tempArgs = tempData.split(",");
                    String[] nodeArgs = nodeData.split(",");

                    double path = g.getEdge(id, id2);
                    double dist = Double.parseDouble(tempArgs[1]);
                    double distsrc = Double.parseDouble(nodeArgs[1]);

                    if (distsrc + path < dist || dist == 0) {
                        temp.setInfo(node.getKey() + "," + (distsrc + path));
                        visited[temp.getKey()] = false;
                    }
                }
            }
        }
    }

    public boolean save(String file) {
        return file(file, Type.Save);
    }
    public boolean load(String file) {
        return file(file, Type.Load);
    }

    public boolean file(String path, Type t) {
        try {
            switch(t) {
                case Load: {
                    FileInputStream f = new FileInputStream(new File(path));
                    ObjectInputStream o = new ObjectInputStream(f);
                    this.g = (WGraph_DS) o.readObject();
                    o.close();
                    f.close();
                }
                case Save: {
                    FileOutputStream f = new FileOutputStream(new File(path));
                    ObjectOutputStream o = new ObjectOutputStream(f);
                    o.writeObject(this.g);
                    o.close();
                    f.close();
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found\n");
        } catch (IOException e) {
            System.out.println("Error initializing stream\n");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
