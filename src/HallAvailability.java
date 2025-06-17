// file to Manages hall busy times and booked events
import java.util.*;

public class HallAvailability {
    // Stores busy times for each hall
    private Map<String, List<BookingSlot>> hallBookedSlots = new HashMap<>();

    // Inner class to hold booking details (start, end, and event ID)
    public static class BookingSlot {
        int startTime;
        int endTime;
        String eventId; // Store the event ID that booked this slot

        public BookingSlot(int startTime, int endTime, String eventId) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.eventId = eventId;
        }

        public int getStartTime() { return startTime; }
        public int getEndTime() { return endTime; }
        public String getEventId() { return eventId; }
    }

    // Adds a new hall silently.
    public void addNewHall(String hallId) {
        hallBookedSlots.putIfAbsent(hallId, new ArrayList<>());
    }
    
    // Books a hall slot, now associating it with an event ID.
    public void bookHallSlot(String hallId, int bookingStartTime, int bookingEndTime, String eventId) {
        // Ensure the hall exists before booking a slot
        addNewHall(hallId); // This ensures any hall used in an event is added to the hall list
        hallBookedSlots.get(hallId).add(new BookingSlot(bookingStartTime, bookingEndTime, eventId));
    }

    // Releases a hall slot
    public boolean releaseHallSlot(String hallId, int releasedStartTime, int releasedEndTime) {
        List<BookingSlot> busySlots = hallBookedSlots.get(hallId);
        if (busySlots == null) {
            return false; // Hall not found
        }
        for (int i = 0; i < busySlots.size(); i++) {
            BookingSlot slot = busySlots.get(i);
            if (slot.getStartTime() == releasedStartTime && slot.getEndTime() == releasedEndTime) {
                busySlots.remove(i);
                return true; // Slot successfully released
            }
        }
        return false; // No exact slot found
    }
    
    // Get all hall IDs
    public Set<String> getAllHallIds() { return hallBookedSlots.keySet(); }

    // Prints a hall's busy schedule, showing event details
    public void printHallSchedule(String hallId) {
        if (hallBookedSlots.containsKey(hallId)) {
            System.out.println("\n--- Schedule for Hall '" + hallId + "' ---");
            List<BookingSlot> slots = hallBookedSlots.get(hallId);
            if (slots.isEmpty()) {
                System.out.println("No events scheduled for this hall.");
            } else {
                // Sort slots by start time for better readability
                slots.sort(Comparator.comparingInt(BookingSlot::getStartTime));
                for (BookingSlot slot : slots) {
                    System.out.println("  Event ID: " + slot.getEventId() + " -> Time: " + slot.getStartTime() + " - " + slot.getEndTime());
                }
            }
        
        } else {
            System.out.println("Hall '" + hallId + "' not found in the system.");
        }
    }
}