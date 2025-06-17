// Java file to Represents the university map 
import java.util.*;

public class Graph {
    // Stores connections
    private Map<String, List<Edge>> adjacencyList = new HashMap<>();

    // Inner class to Represents a path from one location to another
    public static class Edge {
        String destinationLocation; 
        int pathWeight;             //  time, distance

        public Edge(String destinationLocation, int pathWeight) {
            this.destinationLocation = destinationLocation;
            this.pathWeight = pathWeight;
        }
        public String getDestinationLocation() { return destinationLocation; }
        public int getPathWeight() { return pathWeight; }
    }

    // Adds a location (node) to the map
    public void addLocation(String locationName) {
        adjacencyList.putIfAbsent(locationName, new ArrayList<>());
    }
    
    // Adds a two-way path (edge) between two locations
    public void addPath(String locationOne, String locationTwo, int weightOfPath) {
        addLocation(locationOne);
        addLocation(locationTwo);
        
        adjacencyList.get(locationOne).add(new Edge(locationTwo, weightOfPath));
        adjacencyList.get(locationTwo).add(new Edge(locationOne, weightOfPath)); // Add path in both directions
    }

    // Gets all directly connected locations (neighbors) for a given location
    public List<Edge> getNeighbors(String currentLocation) {
        return adjacencyList.getOrDefault(currentLocation, Collections.emptyList());
    }

    // Gets all location names in the graph
    public Set<String> getAllLocations() {
        return adjacencyList.keySet();
    }
    
    // Checks if a location exists on the map
    public boolean containsLocation(String locationName) {
        return adjacencyList.containsKey(locationName);
    }
}