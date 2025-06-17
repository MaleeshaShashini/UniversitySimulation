// Main file of the university system 
import java.util.*;

public class UniversitySystem {
    private MaxPriorityQueue<Event> eventPriorityQueue = new MaxPriorityQueue<>(Event.getComparator());
    public HallAvailability hallManager = new HallAvailability();
    private Graph campusMap = new Graph();
    private Map<String, Event> allEvents = new HashMap<>(); // Stores all events by their ID

    public UniversitySystem() {
        // Initial default halls and map data
        hallManager.addNewHall("LT1"); //halls
        hallManager.addNewHall("LabA");
        hallManager.addNewHall("ConfR1");
        hallManager.addNewHall("HALL01"); 
        hallManager.addNewHall("HALL04"); 
        hallManager.addNewHall("HALL06"); 
        
        campusMap.addLocation("MainGate"); //map locations
        campusMap.addLocation("AdminBlock");
        campusMap.addLocation("Library");
        campusMap.addLocation("Cafeteria");
        
        campusMap.addPath("MainGate", "AdminBlock", 5);//distance

        campusMap.addPath("AdminBlock", "Library", 3);
        campusMap.addPath("Library", "Cafeteria", 2);
        campusMap.addPath("AdminBlock", "Cafeteria", 4);
    }

    // Adds a new event
    public boolean addNewEvent(String eventId, String eventType, String eventLocation, int startTime, int endTime,
            int priorityValue) {
        if (allEvents.containsKey(eventId)) {
            return false; // Event ID already exists
        }

        //  Check if eventLocation (Hall ID) exists in the hallManager
        if (!hallManager.getAllHallIds().contains(eventLocation)) {
           
            hallManager.addNewHall(eventLocation);
            
        }


        Event newEvent = new Event(eventId, eventType, eventLocation, startTime, endTime, priorityValue);
        allEvents.put(eventId, newEvent);
        eventPriorityQueue.insertItem(newEvent);
        
        // Pass eventId to bookHallSlot
        hallManager.bookHallSlot(eventLocation, startTime, endTime, eventId);
        return true;
    }

    public List<Event> getTopUpcomingEvents(int count) {
        List<Event> topEvents = new ArrayList<>();
        List<Event> tempStorage = new ArrayList<>();

        for (int i = 0; i < count && !eventPriorityQueue.isEmpty(); i++) {
            Event event = eventPriorityQueue.extractMaxItem();
            topEvents.add(event);
            tempStorage.add(event);
        }
        for (Event event : tempStorage)
            eventPriorityQueue.insertItem(event);
        return topEvents;
    }

    // Cancels an event
    public boolean cancelEvent(String eventId) {
        Event eventToCancel = allEvents.remove(eventId); // Remove from the master HashMap
        if (eventToCancel == null) {
            return false; // Event not found
        }

        hallManager.releaseHallSlot(eventToCancel.getEventLocation(), eventToCancel.getStartTime(),
                eventToCancel.getEndTime());
        
        rebuildEventPriorityQueue(); // Rebuild queue to remove cancelled event
        return true; // Indicate successful cancellation
    }

    private void rebuildEventPriorityQueue() {
        eventPriorityQueue = new MaxPriorityQueue<>(Event.getComparator());
        for (Event event : allEvents.values()) {
            eventPriorityQueue.insertItem(event);
        }
    }

    // --- Map Routing ---
    public Map<String, Integer> findShortestPathsFrom(String startLocation) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> nodesToVisit = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue));

        for (String location : campusMap.getAllLocations()) {
            distances.put(location, Integer.MAX_VALUE);
        }
        distances.put(startLocation, 0);
        nodesToVisit.offer(new AbstractMap.SimpleEntry<>(startLocation, 0));

        while (!nodesToVisit.isEmpty()) {
            Map.Entry<String, Integer> currentEntry = nodesToVisit.poll();
            String currentLocation = currentEntry.getKey();
            int currentDistance = currentEntry.getValue();

            if (currentDistance > distances.get(currentLocation)) {
                continue;
            }

            for (Graph.Edge neighborEdge : campusMap.getNeighbors(currentLocation)) {
                String neighborLocation = neighborEdge.getDestinationLocation();
                int pathWeight = neighborEdge.getPathWeight();

                if (distances.get(currentLocation) + pathWeight < distances.get(neighborLocation)) {
                    distances.put(neighborLocation, distances.get(currentLocation) + pathWeight);
                    nodesToVisit.offer(new AbstractMap.SimpleEntry<>(neighborLocation, distances.get(neighborLocation)));
                }
            }
        }
        return distances;
    }

    // Checks if a location exists on the map
    public boolean doesLocationExist(String locationName) {
        return campusMap.containsLocation(locationName);
    }
}