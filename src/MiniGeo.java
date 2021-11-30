import eu.jacquet80.minigeo.MapWindow;
import eu.jacquet80.minigeo.Point;
import eu.jacquet80.minigeo.Segment;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
// import java.util.Scanner;

public class MiniGeo {
    HashMap<String,Vertex> vertices;
    ArrayList<Request> requests;
    private static int color;
    MiniGeo(HashMap<String,Vertex> vertices, ArrayList<Request> requests){
        this.vertices = vertices;
        this.requests = requests;
        color = 0;
    }

    void plotMzp(Request req){
        // Scanner scanner = new Scanner(System.in);
        MapWindow window = new MapWindow();

        for(String vertexId: vertices.keySet()){
            for(Edge edge: vertices.get(vertexId).adjacent) {
                String dstId = edge.vertexId;
                Point pointOne = new Point(vertices.get(vertexId).latitude,vertices.get(vertexId).longitude);
                Point pointTwo = new Point(vertices.get(dstId).latitude,vertices.get(dstId).longitude);

                window.addSegment(new Segment(pointOne, pointTwo, Color.orange));
            }
        }

        Color color = getColor();

        for (int i=0; i<req.getPath().size()-1; i++) {
            String vertexOneId = req.getPath().get(i);
            String vertexTwoId = req.getPath().get(i+1);

            Point pointOne = new Point(vertices.get(vertexOneId).latitude,vertices.get(vertexOneId).longitude);
            Point pointTwo = new Point(vertices.get(vertexTwoId).latitude,vertices.get(vertexTwoId).longitude);

            window.addSegment(new Segment(pointOne, pointTwo, color));

        }
        //window.addSegment( new Segment( new Point(48, 2), new Point(45, -1), Color.RED));
        window.setVisible(true);

    }
    private Color getColor(){
        switch(color++%5){
            case 0: return Color.BLUE;
            case 1: return Color.GRAY;
            case 2: return Color.RED;
            case 3: return Color.BLACK;
            case 4: return Color.GREEN;
        }
        return Color.BLACK;
    }
}
