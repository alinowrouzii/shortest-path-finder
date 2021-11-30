import java.util.ArrayList;

public class Request {

    ArrayList<Edge> pathEdgesOne;
    ArrayList<Edge> pathEdgesTwo;

    String srcId;
    String dstId;
    double startTime;
    double timeLength;
    double endTime;
    boolean isDone;
    //this store id of pathes in arraylist
    Request(double startTime,String srcId,String dstId){
        this.srcId = srcId;
        this.dstId = dstId;
        this.startTime = startTime;
        this.isDone = false;
    }

    void setTime(double time) {
        this.timeLength = time;
        this.endTime = startTime + timeLength;
    }

    void printPath() {
        for (int i=pathEdgesOne.size()-1; i>=0; i--) {
            Edge edge = pathEdgesOne.get(i);
            System.out.print(edge.vertexId+" ");
        }
        System.out.print(dstId+" ");
        System.out.println();
//        System.out.print("traffic: ");
//        for (Edge edge: pathEdgesOne){
//            System.out.print(edge.traffic+" ");
//        }
//        System.out.println();
    }
    ArrayList<String> getPath(){
        ArrayList<String> result = new ArrayList<>();
        for (int i=pathEdgesOne.size()-1; i>=0; i--) {
            Edge edge = pathEdgesOne.get(i);
            result.add(edge.vertexId);
        }
        result.add(dstId);
        return result;
    }
}
