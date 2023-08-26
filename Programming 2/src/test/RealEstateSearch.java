package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class RealEstateSearch {

    //file path
    private static final String FILE_PATH = "D:\\test\\listings.txt";
    private static final HashMap<String, String[]> homelyListings = new HashMap<>();

    private static final String[] types = {"LISTING_TYPE: ", "LOCATION: ", "TYPE: ", "APARTMENT_DESCRIPTION: "
            , "SURFACE_AREA: ", "FLOOR: ", "TOTAL_FLOORS: ", "CONSTRUCTION: ", "PRICE: ", "LOAN: ", "BALCONY: "
            , "ELEVATOR: ", "AVAILABILITY: ", "AGENT_NAME: ", "AGENT_CONTACT: ", "AGENT_EMAIL: "};
    private static final HashMap<Integer, String[]> tempMap = new HashMap<>();

    private static final int KEY_INDEX = 0;
    private static final int LISTING_TYPE_INDEX = 1;
    private static final int LOCATION_INDEX = 2;
    private static final int TYPE_INDEX = 3;
    private static final int APARTMENT_DESCRIPTION_INDEX = 4;
    private static final int SURFACE_AREA_INDEX = 5;
    private static final int FLOOR_INDEX = 6;
    private static final int TOTAL_FLOORS_INDEX = 7;
    private static final int CONSTRUCTION_YEAR_INDEX = 8;
    private static final int PRICE_INDEX = 9;
    private static final int LOAN_INDEX = 10;
    private static final int BALCONY_INDEX = 11;
    private static final int ELEVATOR_INDEX = 12;
    private static final int AVAILABILITY_INDEX = 13;
    private static final int AGENT_NAME_INDEX = 14;
    private static final int AGENT_CONTACT_INDEX = 15;
    private static final int AGENT_EMAIL_INDEX = 16;

    private static int n = 0;

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] listingData = line.split(",");
                String key = listingData[KEY_INDEX];
                String[] listingInfo = new String[listingData.length - 1];
                System.arraycopy(listingData, 1, listingInfo, 0, listingData.length - 1);
                homelyListings.put(key, listingInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        populateHomelyListings(); // Populate the HomelyListings HashMap with data
        welcomeMessage();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Are you interested in buying or renting? Enter either 1, 2, or 3:");
            System.out.println("1. Buy");
            System.out.println("2. Rent");
            System.out.println("3. Both");
            int buyRentChoice = scanner.nextInt();

            System.out.println("Select the real-estate type you would like to enquire about. Enter either 1 or 2:");
            System.out.println("1. Residence");
            System.out.println("2. Commercial");
            int residenceCommercialChoice = scanner.nextInt();

            System.out.println("Select the surface area of the place you're interested in. Enter either 1, 2, 3, or 4:");
            System.out.println("1. Small (under 30 sq.m.)");
            System.out.println("2. Medium (>30 sq.m. and <= 60 sq.m.)");
            System.out.println("3. Large (>60 sq.m. and <= 150 sq.m.)");
            System.out.println("4. Very large (>150 sq.m.)");
            int surfaceAreaChoice = scanner.nextInt();

            searchHomelyListings(buyRentChoice, residenceCommercialChoice, surfaceAreaChoice);

            System.out.println("If you would like to know more about any of the above listed place(s), enter the corresponding number. ");
            System.out.println("If you want to search again, enter 'BACK'.");
            System.out.println("If you want to end your search query, enter 'END'.");
            System.out.print("Your input: ");
            String userInput = scanner.next().trim();

            if (userInput.equalsIgnoreCase("END")) {
                endProgram();
                break;
            } else if (userInput.equalsIgnoreCase("BACK")) {
                continue;
            }

            int selectedListingIndex = Integer.parseInt(userInput);
            if (selectedListingIndex >= 0 && selectedListingIndex < n) {
                displayListingDetails(tempMap.get(selectedListingIndex));
            }

            order(tempMap.get(selectedListingIndex));
        }
    }

    private static void welcomeMessage() {
        System.out.println("Welcome to Homely Inc. real-estate service. Here, you can enquire about the real estates that are available either for buying or renting. We offer both residential and commercial spaces.");
    }

    private static void populateHomelyListings() {
        // Populate the HomelyListings HashMap with data
        // Example data for demonstration purposes
        homelyListings.put("1", new String[]{"Residence", "City: Myllys", "Type: Apartment", "Apartment description: 2B-2H-1K-1T", "Area: 54 sq.m.", "Availability: Vacant"});
        homelyListings.put("2", new String[]{"Residence", "City: Zeus", "Type: Studio", "Apartment description: 2H-1K-1T", "Area: 33 sq.m.", "Availability: Soon to be vacant"});
    }


    private static void searchHomelyListings(int buyRentChoice, int residenceCommercialChoice, int surfaceAreaChoice) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        if (buyRentChoice == 1) {
            sb1.append("BY");
        } else if (buyRentChoice == 2) {
            sb1.append("RN");
        }
        if (residenceCommercialChoice == 1) {
            sb2.append("RES");
        } else if (buyRentChoice == 2) {
            sb2.append("COM");
        }
        switch (surfaceAreaChoice) {
            case 1:
                sb3.append("SML");
                break;
            case 2:
                sb3.append("MED");
                break;
            case 3:
                sb3.append("LRG");
                break;
            case 4:
                sb3.append("VLG");
                break;
        }
        System.out.println("Matching your inputs, the following properties are available:");
        n = 1;
        for (String key : homelyListings.keySet()) {
            if (key.contains(sb1) && key.contains(sb2) && key.contains(sb3)) {
                tempMap.put(n, homelyListings.get(key));
                String[] strings = homelyListings.get(key);
                System.out.print(n + ".");
                System.out.println("  City:" + strings[2]);
                System.out.println("  Type:" + strings[3]);
                System.out.println("  Apartment description:" + strings[4]);
                System.out.println("  Area:" + strings[5]);
                System.out.println("  Availability:" + strings[13]);
                n++;
            }
        }
    }


    private static void displayListingDetails(String[] listingInfo) {
        System.out.println("Listing Details:");
        for (int i = 0; i < listingInfo.length; i++) {
            System.out.println(types[i] + listingInfo[i]);
        }
    }

    private static void order(String[] listingInfo) {
        System.out.println("1. Book a showing");
        System.out.println("2. Go back to the results");
        System.out.println("3. Search again");
        System.out.println("4. End");

        Scanner scanner = new Scanner(System.in);
        int option = -1;
        while (option < 1 || option > 4) {
            System.out.print("Your input (Enter 1, 2, 3, or 4): ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (option) {
                case 1:
                    bookShowingConfirmation(listingInfo);
                    break;
                case 2:
                    // Go back to the results
                    displayListingDetails(listingInfo);
                    break;
                case 3:
                    // Search again
                    break;
                case 4:
                    // End
                    endProgram();
                    break;
            }
        }
    }

    private static void bookShowingConfirmation(String[] listingInfo) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter your gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter your date of birth (yyyy-MM-dd): ");
        String dobInput = scanner.nextLine();
        LocalDate dateOfBirt = LocalDate.parse(dobInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter your email address: ");
        String emailAddress = scanner.nextLine();

        System.out.print("Enter the date for the showing (yyyy-MM-dd): ");
        LocalDate showingDate = null;
        while (showingDate == null) {
            try {
                String dateInput = scanner.nextLine();
                showingDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate today = LocalDate.now();
                LocalDate maxDate = today.plusMonths(1);
                if (showingDate.isBefore(today) || showingDate.isAfter(maxDate)) {
                    System.out.println("Please enter a date between " + today + " and " + maxDate);
                    showingDate = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter a valid date (yyyy-MM-dd).");
            }
        }

        System.out.print("Enter the time for the showing (HH:mm): ");
        LocalTime showingTime = null;
        while (showingTime == null) {
            try {
                String timeInput = scanner.nextLine();
                showingTime = LocalTime.parse(timeInput, DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime minTime = LocalTime.of(9, 0);
                LocalTime maxTime = LocalTime.of(16, 0);
                if (showingTime.isBefore(minTime) || showingTime.isAfter(maxTime)) {
                    System.out.println("Please enter a time between " + minTime + " and " + maxTime);
                    showingTime = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid time format. Please enter a valid time (HH:mm).");
            }
        }

        System.out.println("\nThank you for booking a showing with Homely Inc.");
        System.out.println("Our agent Ms. " + listingInfo[AGENT_NAME_INDEX] + " will be delighted to show you your chosen place and answer all your queries to help you make the right decision.");
        System.out.println("We will also send you two reminders before the showing day to make sure you don’t miss it.");
        System.out.println("For any questions or cancellation of the showing, contact us at homely.homes@homely.com");

        PriorityQueue<Reminder> reminders = new PriorityQueue<>();
        LocalDateTime showingDateTime = showingDate.atTime(showingTime);
        // Calculate the time differences for reminders
        long daysUntilShowing = LocalDateTime.now().until(showingDateTime, java.time.temporal.ChronoUnit.DAYS);

        // Add reminders to the queue
        if (daysUntilShowing >= 3) {
            LocalDateTime reminderDateTime = showingDateTime.minusDays(3);
            String message = "test.Reminder #1: Your house/office showing with Ms. " + listingInfo[AGENT_NAME_INDEX] + " from Homely Inc. is scheduled for " +
                    showingDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ", which is in 3 days.";
            reminders.add(new Reminder(message, reminderDateTime));
        }

        long hoursUntilShowing = LocalDateTime.now().until(showingDateTime, java.time.temporal.ChronoUnit.HOURS);
        if (hoursUntilShowing >= 24) {
            LocalDateTime reminderDateTime = showingDateTime.minusHours(24);
            String message = "Last test.Reminder: Your house/office showing with Ms. Marge Marlow from Homely Inc. is scheduled for " +
                    showingDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ", which is in 24 hours.";
            reminders.add(new Reminder(message, reminderDateTime));

        }

        new Thread(() -> {
            // Send and remove reminders from the queue
            while (!reminders.isEmpty()) {
                Reminder reminder = reminders.peek();
                if (!reminder.getReminderDateTime().isAfter(LocalDateTime.now())) {
                    System.out.println(reminder.getMessage());
                    reminders.poll();
                }
            }
        }).start();


    }


    private static void endProgram() {
        System.out.println("Thank you for choosing Homely Inc. We hope you found what you were looking for. If not, then visit us soon, and give us another chance to meet your requirement. You can also get in touch with us at homely.homes@homely.com.");
        System.exit(0);
    }


}