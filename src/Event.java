
public class Event {
	private String eventId;
	private String type;
	private String location;
    private int startTime;
    private int endTime;
    private String priorityLevel;
    private int priorityValue;
    private int capacityRequired;
	
    // Constructor to create an Event object
	public Event(String eventId, String type, String location, int startTime, int endTime, String priorityLevel,int priorityValue, int capacityRequired) {
		 this.eventId = eventId;
	     this.type = type;
	     this.type = type;
	     this.location = location;
	     this.startTime = startTime;
	     this.endTime = endTime;
	     this.priorityLevel = priorityLevel;
	     this.capacityRequired = capacityRequired;
	}
	
	
	private void setPriorityValue(String level) {
		switch (level.toLowerCase()) {
        case "mandatory":
            this.priorityValue = 3;
            break;
        case "high-profile":
            this.priorityValue = 2;
            break;
        case "optional":
            this.priorityValue = 1;
            break;
        default:
            this.priorityValue = 0;
            }
	}

}
