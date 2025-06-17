// Runs the University Smart System  menu and output
import java.util.*;// Import necessary utility classes

public class Main {
    public static void main(String[] args) {
        UniversitySystem system = new UniversitySystem();
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Welcome to the University Smart System!");// Display a welcome message

        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            String userChoice = inputScanner.nextLine();

            switch (userChoice) { // Use a switch statement to handle different menu options
                case "1":
                    handleAddEvent(system, inputScanner);
                    break;
                case "2":
                    handleViewUpcomingEvents(system);
                    break;
                case "3":
                    handleCancelEvent(system, inputScanner);
                    break;
                case "4":
                    handleAddHall(system, inputScanner);
                    break;
                case "5":
                    handleViewHallSchedule(system, inputScanner); 
                    break;
                case "6":
                    handleFindRoute(system, inputScanner);
                    break;
                case "7":
                    System.out.println("Exiting. Goodbye!");
                    inputScanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("\n--- Press Enter to continue ---");
            inputScanner.nextLine();
        }
    }

    private static void printMenu() { //main menu options
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add New Event");
        System.out.println("2. View Top Upcoming Events");
        System.out.println("3. Cancel Event");
        System.out.println("4. Add New Hall");
        System.out.println("5. View Hall Schedule"); 
        System.out.println("6. Find Route on Map");
        System.out.println("7. Exit");
        System.out.println("------------");
    }

    private static void handleAddEvent(UniversitySystem system, Scanner scanner) {
        System.out.print(
                "Enter ID, Type (e.g., class, seminar), Location (Hall ID), StartTime, EndTime, Priority (e.g., E001 class LT1 0900 1000 1): "); // Clarified Location as Hall ID
        String[] parts = scanner.nextLine().split(" ");

        String eventId = parts[0];
        String eventType = parts[1];
        String eventLocation = parts[2]; 
        int startTime = Integer.parseInt(parts[3]);
        int endTime = Integer.parseInt(parts[4]);
        int priorityValue = Integer.parseInt(parts[5]);

        boolean added = system.addNewEvent(eventId, eventType, eventLocation, startTime, endTime, priorityValue);
        if (added) {
            System.out.println("Event " + eventId + " added.");
        } else {
            System.out.println("Event " + eventId + " could not be added (ID might exist).");
        }
    }

    private static void handleViewUpcomingEvents(UniversitySystem system) {
        System.out.println("\n--- Top Upcoming Events ---");
        List<Event> events = system.getTopUpcomingEvents(3);
        if (events.isEmpty()) {
            System.out.println("No upcoming events.");
        } else {
            events.forEach(System.out::println);
        }
    }

    private static void handleCancelEvent(UniversitySystem system, Scanner scanner) {
        System.out.print("Enter Event ID to cancel: ");
        String id = scanner.nextLine();
        boolean cancelled = system.cancelEvent(id);
        if (cancelled) {
            System.out.println("Event " + id + " cancelled.");
        } else {
            System.out.println("Event " + id + " not found or could not be cancelled.");
        }
    }

    private static void handleAddHall(UniversitySystem system, Scanner scanner) { 
        System.out.print("Enter Hall ID to add: "); 
        String id = scanner.nextLine();
        system.hallManager.addNewHall(id);
        System.out.println("Hall '" + id + "' has been added."); 
    }

    private static void handleViewHallSchedule(UniversitySystem system, Scanner scanner) { 
        System.out.print("Enter Hall ID to view schedule: "); 
        String id = scanner.nextLine();
     
        system.hallManager.printHallSchedule(id); 
    }

    private static void handleFindRoute(UniversitySystem system, Scanner scanner) {
        System.out.print("Enter Start Location: ");
        String startLoc = scanner.nextLine();
        System.out.print("Enter End Location: ");
        String endLoc = scanner.nextLine();
        
        Map<String, Integer> distances = system.findShortestPathsFrom(startLoc);

        Integer distanceToEnd = distances.get(endLoc);

        if (distanceToEnd != null && distanceToEnd != Integer.MAX_VALUE) {
            System.out.println("Shortest distance from " + startLoc + " to " + endLoc + ": " + distanceToEnd + " units.");
        } else {
            System.out.println("No route found between " + startLoc + " and " + endLoc + ".");
        }
    }
}