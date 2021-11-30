import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

class Graph {
    // store id and vertex info
    HashMap<String, Vertex> vertices;
    private MinHeap heapVertices;
    private int verticesSize;

    Graph(int verticesSize) {
        this.vertices = new HashMap<>();
        this.verticesSize = verticesSize;
    }

    void addEdge(String srcId, String dstId) {

        double x1 = vertices.get(srcId).longitude;
        double y1 = vertices.get(srcId).latitude;
        double x2 = vertices.get(dstId).longitude;
        double y2 = vertices.get(dstId).latitude;

        double length = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        this.vertices.get(srcId).adjacent.add(new Edge(dstId, length));
        this.vertices.get(dstId).adjacent.add(new Edge(srcId, length));
    }

    void changeTrafficOfPath(ArrayList<Edge> pathEdgesOne, ArrayList<Edge> pathEdgesTwo, int amount) {
        for (Edge edge : pathEdgesOne) {
            edge.traffic += amount;
        }
        for (Edge edge : pathEdgesTwo) {
            edge.traffic += amount;
        }

    }

    Vertex findShortestPath(String sourceId, String destinationId) {

        initial();
        System.out.println(sourceId + " surce id");
        vertices.get(sourceId).distFromSrc = 0;
        // vertices[source].isExplored = true;
        heapVertices.insertKey(sourceId, 0);

        while (!vertices.get(destinationId).isExplored) {
            String currentVertexId = heapVertices.deleteMin();
            vertices.get(currentVertexId).isExplored = true;

            double distance = vertices.get(currentVertexId).distFromSrc;
            for (Edge edge : vertices.get(currentVertexId).adjacent) {
                String temp_vertexId = edge.vertexId;
                double dist_2 = vertices.get(temp_vertexId).distFromSrc;
                double edgeWeight = calculateWeight(edge);
                if (distance + edgeWeight < dist_2) {
                    // System.out.println("isEplored: "+ vertex);
                    // System.out.println("distance from src: "+ vertices[destination].distFromSrc
                    // );

                    if (dist_2 == Double.MAX_VALUE) {
                        // add to heapVertice;
                        heapVertices.insertKey(temp_vertexId, distance + edgeWeight);
                    } else {
                        // update heapValue
                        heapVertices.updateKey(temp_vertexId, distance + edgeWeight);
                    }
                    vertices.get(temp_vertexId).distFromSrc = distance + edgeWeight;
                    vertices.get(temp_vertexId).prev = vertices.get(currentVertexId);
                }
            }
        }

        return vertices.get(destinationId);
    }

    static double calculateWeight(Edge edge) {
        return edge.length * (1 + .3 * edge.traffic);
    }

    private void initial() {
        this.heapVertices = new MinHeap(this.verticesSize);

        for (String v : vertices.keySet()) {
            // System.out.println("graph id:"+ v);
            vertices.get(v).distFromSrc = Double.MAX_VALUE;
            vertices.get(v).isExplored = false;
            vertices.get(v).prev = null;
        }
    }

    /**
     *
     * @param v vertex that contains path
     * @return Array list of edges
     */
    ArrayList<Edge> getPathEdges(Vertex v, int mode) {
        ArrayList<Edge> result = new ArrayList<>();
        while (v.prev != null) {
            String vertexOneId = v.id;
            String vertexTwoId = v.prev.id;

            if (mode == 0) {
                // first find the edge
                LinkedList<Edge> edgesOne = vertices.get(vertexOneId).adjacent;
                for (Edge edge : edgesOne) {
                    if (edge.vertexId.equals(vertexTwoId)) {
                        result.add(edge);
                        break;
                    }
                }
            } else if (mode == 1) {
                LinkedList<Edge> edgesTwo = vertices.get(vertexTwoId).adjacent;
                for (Edge edge : edgesTwo) {
                    if (edge.vertexId.equals(vertexOneId)) {
                        result.add(edge);
                        break;
                    }
                }
            }
            v = v.prev;
        }
        return result;
    }
}

class MinHeap {
    private double[] heapValue;
    private String[] heapToVertex;
    // store id of vertex as a key and index of vertex in heap as a value
    private HashMap<String, Integer> vertexToHeap;
    int currentSize;

    MinHeap(int size) {
        this.heapValue = new double[size];
        this.vertexToHeap = new HashMap<>();
        this.heapToVertex = new String[size];

        currentSize = 0;
    }

    private void minHeapify(int index) {

        int min = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < currentSize && heapValue[left] < heapValue[min])
            min = left;

        if (right < currentSize && heapValue[right] < heapValue[min])
            min = right;

        if (min != index) {
            // first swap heapValue
            swap(index, min);

            minHeapify(min);
        }
    }

    private void swap(int indexOne, int indexTwo) {
        double temp = heapValue[indexOne];
        heapValue[indexOne] = heapValue[indexTwo];
        heapValue[indexTwo] = temp;

        // then swap heap to vertex
        String temp_2 = heapToVertex[indexOne];
        heapToVertex[indexOne] = heapToVertex[indexTwo];
        heapToVertex[indexTwo] = temp_2;

        String vertexId_1 = heapToVertex[indexTwo];
        String vertexId_2 = heapToVertex[indexOne];

        int temp_3 = vertexToHeap.get(vertexId_1);
        vertexToHeap.put(vertexId_1, vertexToHeap.get(vertexId_2));
        vertexToHeap.put(vertexId_2, temp_3);
    }

    String deleteMin() {
        // System.out.println("current size: "+ currentSize);
        if (currentSize > 0) {
            String temp = heapToVertex[0];
            // TODO: check that comment
            // heapValue[0] = heapValue[currentSize-1] ;
            swap(currentSize - 1, 0);
            currentSize--;
            minHeapify(0);
            return temp;
        }
        return (-1 + "");
    }

    void updateKey(String vertexId, double newDistance) {
        System.out.println("-----------Under test------------");
        System.out.println(vertexToHeap);
        System.out.println(vertexId);
        System.out.println("----------------=====------------");
        int index = vertexToHeap.get(vertexId);

        if (newDistance < heapValue[index]) {
            heapValue[index] = newDistance;

            int parent = (index - 1) / 2;
            while (index > 0 && heapValue[parent] > heapValue[index]) {
                swap(parent, index);
                index = parent;
                parent = (index - 1) / 2;
            }
        } else if (newDistance > heapValue[index]) {
            heapValue[index] = newDistance;
            minHeapify(index);
        }
    }

    void insertKey(String vertexId, double distance) {
        int index = currentSize;
        currentSize++;

        heapValue[index] = distance;
        heapToVertex[index] = vertexId;
        vertexToHeap.put(vertexId, index);

        int parent = (index - 1) / 2;
        while (index > 0 && heapValue[parent] > heapValue[index]) {
            swap(parent, index);
            index = parent;
            parent = (index - 1) / 2;
        }
    }
}

class Vertex {
    String id;
    LinkedList<Edge> adjacent;
    double distFromSrc;
    boolean isExplored;
    double latitude;
    double longitude;
    Vertex prev;

    Vertex(String id, double distFromSrc, double latitude, double longitude) {
        this.id = id;
        this.adjacent = new LinkedList<>();
        this.distFromSrc = distFromSrc;
        this.latitude = latitude;
        this.longitude = longitude;
        isExplored = false;
        prev = null;
    }

    public static ArrayList<String> getPath(Vertex vertex) {
        Vertex v = vertex;
        ArrayList<String> path = new ArrayList<>();
        while (v != null) {
            path.add(v.id);

            v = v.prev;
        }
        return path;
    }
}

class Edge {
    String vertexId;
    double length;
    double traffic;

    Edge(String vertexId, double length) {
        this.vertexId = vertexId;
        this.length = length;
        this.traffic = 0;
    }
}
