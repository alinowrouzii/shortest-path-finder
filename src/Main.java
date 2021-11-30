import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
// import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Graph graph;
    static ArrayList<Request> requests;

    public static void main(String[] args) {
        // String type = scanner.nextLine();
        String type = "file";
        if (type.contains("file")) {
            readFromFile();
        } else {
            readFromConsole();
        }

        requests = new ArrayList<>();
        RequestsProcess process = new RequestsProcess(graph, requests);
        MiniGeo minigeo = new MiniGeo(graph.vertices, requests);

        double startTime;
        String dst, src;
        int index = 0;
        System.out.println();
        while (true) {
            // startTime = scanner.nextDouble();
            startTime = 0;
            if (startTime == -1) {
                // System.out.println("hello");
                break;
            }
            src = scanner.next();
            dst = scanner.next();

            Request request = new Request(startTime, src, dst);
            requests.add(request);

            process.process(request, index);
            minigeo.plotMzp(request);

            index++;
        }

    }

    private static void readFromConsole() {
        initGraph(scanner);
    }

    private static void readFromFile() {

        // System.out.println("Enter name of file:");
        // String fileName = scanner.next();
        String fileName = "m1.txt";
        try {
            File myObj = new File("./maps/" + fileName);
            Scanner myReader = new Scanner(myObj);

            initGraph(myReader);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void initGraph(Scanner scanner) {
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();

        graph = new Graph(n);
        for (int i = 0; i < n; i++) {
            String id = scanner.next();
            double latitude = scanner.nextDouble();
            double longitude = scanner.nextDouble();

            graph.vertices.put(id, new Vertex(id, Integer.MAX_VALUE, latitude, longitude));
            // graph.adjacent.put(id, new LinkedList<>());
        }

        for (int i = 0; i < m; i++) {
            String srcId = scanner.next();
            String dstId = scanner.next();
            graph.addEdge(srcId, dstId);
        }
    }
}