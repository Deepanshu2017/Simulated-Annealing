package graph;

/**
 * Created by deepanshu on 6/1/16.
 */

import org.graphstream.ui.view.Viewer;
import sa.City;
import sa.Tour;
import sa.TourManager;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class CreateGraph {

    protected String stylesheet =
            "node { " +
                "fill-color: green; text-color: blue; text-alignment: under;" +
            "}" +
            "edge { " +
                "fill-color: red;" +
            "}" +
            "graph { "+
                "fill-color: black;" +
            " }";

    private Graph graph;

    public CreateGraph() {
        graph = new SingleGraph("Simulated Annealing");
        Viewer viewer = graph.display();
        viewer.disableAutoLayout();
        graph.addAttribute("ui.stylesheet", stylesheet);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
    }

    public void init() {
        int totalNumberOfCities = TourManager.numberOfCities();
        for (int i = 0; i < totalNumberOfCities; ++i) {
            City city = TourManager.getCity(i);
            int x = city.getX(), y = city.getY();
            graph.addNode("" + x + ", " + y);
            Node node = graph.getNode("" + x + ", " + y);
            node.addAttribute("ui.label", node.getId());
            node.setAttribute("x", x);
            node.setAttribute("y", y);
            sleep();
        }
    }

    public void set(Tour tour) {
        ArrayList tour_real = tour.getTour();
        int length = tour_real.size();
        for (int i = 0; i < length - 1; ++i) {
            City city_1 = (City)tour_real.get(i);
            City city_2 = (City)tour_real.get(i + 1);
            Node node_1 = graph.getNode("" + city_1.getX() + ", " + city_1.getY());
            Node node_2 = graph.getNode("" + city_2.getX() + ", " + city_2.getY());
            node_1.setAttribute("x", city_1.getX());
            node_2.setAttribute("x", city_2.getX());
            node_1.setAttribute("y", city_1.getY());
            node_2.setAttribute("y", city_2.getY());
            graph.addEdge(i + "", node_2, node_1, false);
            sleep();
        }
    }

    private void addEdge(Iterator iterator, ArrayList<Edge> edges) {
        while (iterator.hasNext()) {
            edges.add((Edge)iterator.next());
        }
    }

    private void addEdge(ArrayList<Edge> edges, Node node_1, Node node_2, ArrayList<Edge> changeEdge) {
        for (Edge edge : edges) {
            Node node0 = edge.getNode0();
            Node node1 = edge.getNode1();
            if (node_2.getId().equals(node1.getId())) {
                graph.addEdge(edge.getId(), node0, node_1, false);
                changeEdge.add(graph.getEdge(edge.getId()));
            } else {
                graph.addEdge(edge.getId(), node1, node_1, false);
                changeEdge.add(graph.getEdge(edge.getId()));
            }
        }
    }

    private Node addNode(int x, int y) {
        graph.addNode("" + x + ", " + y);
        Node node_1 = graph.getNode("" + x + ", " + y);
        node_1.setAttribute("x", x);
        node_1.setAttribute("y", y);
        node_1.addAttribute("ui.label", node_1.getId());
        node_1.addAttribute("ui.style", "fill-color: blue; text-color: blue; text-alignment: under;");
        return node_1;
    }

    private void removeCommon(ArrayList<Edge> edges_1, ArrayList<Edge> edges_2, ArrayList<Edge> common) {
        for (Edge edge_1 : edges_1) {
            common.addAll(edges_2.stream().filter(edge_2 -> edge_1.getId().equals(edge_2.getId())).map(edge_2 -> edge_1).collect(Collectors.toList()));
        }
        edges_1.removeAll(common);
        edges_2.removeAll(common);

        for (Edge edge : edges_1)
            edge.addAttribute("ui.style", "fill-color: Green;");
        for (Edge edge : edges_2)
            edge.addAttribute("ui.style", "fill-color: Green;");
        for (Edge edge : common)
            edge.addAttribute("ui.style", "fill-color: Green;");
    }

    public void updateColor(ArrayList<Edge> changeEdge, Node node_1, Node node_2) {
        for (Edge edge : changeEdge)
            edge.addAttribute("ui.style", "fill-color: Red;");

        node_1.addAttribute("ui.style", "fill-color: green; text-color: blue; text-alignment: under;");
        node_2.addAttribute("ui.style", "fill-color: green; text-color: blue; text-alignment: under;");
    }

    public void update(City city_1, City city_2) {
        ArrayList<Edge> changeEdge = new ArrayList<>();
        int x_1 = city_1.getX(), y_1 = city_1.getY();
        int x_2 = city_2.getX(), y_2 = city_2.getY();
        Node node_1 = graph.getNode("" + x_1 + ", " + y_1);
        Node node_2 = graph.getNode("" + x_2 + ", " + y_2);
        ArrayList<Edge> edge_1 = new ArrayList<>();
        ArrayList<Edge> edge_2 = new ArrayList<>();
        ArrayList<Edge> common = new ArrayList<>();
        addEdge(node_1.getEdgeIterator(), edge_1);
        addEdge(node_2.getEdgeIterator(), edge_2);
        removeCommon(edge_1, edge_2, common);

        System.out.println("Before Node_1: " + node_1.toString());
        System.out.println("Before Node_2: " + node_2.toString());

        graph.removeNode(node_1.getId());
        graph.removeNode(node_2.getId());
        node_1 = addNode(x_2, y_2);
        node_2 = addNode(x_1, y_1);

        System.out.println("Node_1: " + node_1.toString());
        System.out.println("Node_2: " + node_2.toString());
        System.out.println("edge_1: " + edge_1.toString());
        System.out.println("edge_2: " + edge_2.toString());
        System.out.println("common: " + common.toString());

        addEdge(edge_1, node_1, node_2, changeEdge);
        addEdge(edge_2, node_2, node_1, changeEdge);

        for (Edge edge : common) {
            Node node1 = edge.getNode0();
            Node node2 = edge.getNode1();
            graph.addEdge(edge.getId(), node_2, node_1, false);
            changeEdge.add(graph.getEdge(edge.getId()));
        }

        sleep();
        updateColor(changeEdge, node_1, node_2);
        System.out.println(node_1.getEdgeSet().toString());
        System.out.println(node_2.getEdgeSet().toString());
        System.out.println();
        sleep();
    }

    protected void sleep() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}