import java.util.ArrayList;

class RequestsProcess {
    private Graph graph;

    private ArrayList<Request> requests;
    RequestsProcess(Graph graph, ArrayList<Request> requests){
        this.graph = graph;
        this.requests = requests;
    }

    void process(Request currentRequest,int indexOfProcess) {
        for (int j=indexOfProcess-1; j>=0; j--) {
            Request req = requests.get(j);
            if (req.isDone)
                break;
            if( req.endTime <= currentRequest.startTime ){
                graph.changeTrafficOfPath(req.pathEdgesOne,req.pathEdgesTwo, -1);
                req.isDone = true;
            }
        }
        Vertex v = graph.findShortestPath(currentRequest.srcId,currentRequest.dstId);

        currentRequest.pathEdgesOne = graph.getPathEdges(v,0);
        currentRequest.pathEdgesTwo = graph.getPathEdges(v,1);

        double time = estimateTime(currentRequest.pathEdgesOne);
        currentRequest.setTime(time);
        currentRequest.printPath();
        System.out.println("time: "+time);
        graph.changeTrafficOfPath(currentRequest.pathEdgesOne,currentRequest.pathEdgesTwo, 1);

    }

    private double estimateTime(ArrayList<Edge> edges){
        double sumOfWeights =0;
        for (Edge edge : edges){
            sumOfWeights += Graph.calculateWeight(edge);
        }
        return 120*sumOfWeights;
    }
}
