// java file for Defines an event in the university system
import java.util.Comparator; //Import the Comparator interface

public class Event {
    String eventId;          // Unique ID for the event
    String eventType;        //  (e.g., class, seminar, meeting)
    String eventLocation;    
    int startTime;            
    int endTime;             
    int numericalPriority;   // Importance (1 = mandatory, 3 = optional)

    //Constructs a new Event object with the specified details
    public Event(String eventId, String eventType, String eventLocation, int startTime, int endTime, int numericalPriority) {
        this.eventId = eventId;
        this.eventType = eventType; 
        this.eventLocation = eventLocation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numericalPriority = numericalPriority;
    }

    // Getters to read event details
    public String getEventId() { return eventId; }
    public String getEventType() { return eventType; } 
    public String getEventLocation() { return eventLocation; }
    public int getStartTime() { return startTime; }
    public int getEndTime() { return endTime; }
    public int getNumericalPriority() { return numericalPriority; }

    @Override
    public String toString() {
        
        return "ID: " + eventId + ", Type: " + eventType + ", Loc: " + eventLocation + ", Time: " + startTime + "-" + endTime + ", Pri: " + numericalPriority;
    }

    // Comparator to defines how events are sorted in the priority queue
    public static Comparator<Event> getComparator() {
        return (eventOne, eventTwo) -> {
            int priorityComparison = Integer.compare(eventTwo.numericalPriority, eventOne.numericalPriority);
            if (priorityComparison != 0) {
                return priorityComparison;
            }
            return Integer.compare(eventTwo.startTime, eventOne.startTime);
        };
    }
}