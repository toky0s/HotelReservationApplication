package menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class MainMenu {
  public static MainMenu INSTANCE = new MainMenu();
  private static Customer currentCustomer;
  private static CustomerService customerService = CustomerService.getInstance();
  private static ReservationService reservationService = ReservationService.getInstance();

  public void displayMenu() {

    do {
      System.out.println("1. Find and reserve a room");
      System.out.println("2. See my reservations");
      System.out.println("3. Create an account");
      System.out.println("4. Admin");
      System.out.println("5. Exit");
      System.out.print("Enter your choice: ");

      Scanner scanner = new Scanner(System.in);
      String data = scanner.nextLine();

      try {
        int choice = Integer.parseInt(data);
        switch (choice) {
          case 1:
            System.out.print("\033\143");
            System.out.println("FIND AND RESERVE A ROOM");
            findRoom(scanner);
            break;
          case 2:
            System.out.print("\033\143");
            System.out.println("SEE MY RESERVATIONS");
            seeMyReservations(scanner);
            break;
          case 3:
            System.out.print("\033\143");
            System.out.println("CREATE AN ACCOUNT");
            createAnAccount(scanner);
            break;
          case 4:
            System.out.print("\033\143");
            AdminMenu.INSTANCE.displayMenu(scanner);
            break;
          case 5:
            System.exit(0);
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } catch (NumberFormatException ex) {
        System.out.println("Invalid choice. Please try again.");
        continue;
      }

    } while (true);
  }

  public void createAnAccount(Scanner scanner) {
    System.out.print("Enter first name: ");
    String firstName = scanner.nextLine();
    System.out.print("Enter last name: ");
    String lastName = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    try {
      CustomerService.getInstance().addCustomer(email, firstName, lastName);
      System.out.println("Create an account successfully");
      currentCustomer = customerService.getCustomer(email);
      System.out.println(
          "Welcome " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + " to Hotel Application");
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public void seeMyReservations(Scanner scanner) {
    if (currentCustomer == null) {

      boolean isInvalidCustomer = false;
      do {
        if (isInvalidCustomer) {
          System.out.println("Please enter a valid customer");
        }
        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();
        try {
          currentCustomer = customerService.getCustomer(customerEmail);
          System.out.println(
              "Welcome " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName()
                  + " to Hotel Application");
          isInvalidCustomer = false;
        } catch (IllegalArgumentException ex) {
          isInvalidCustomer = true;
          System.out.println(ex.getMessage());
        }
      } while (isInvalidCustomer);
    }
    System.out.println("Your reservations:");
    Collection<Reservation> reservations = reservationService.getCustomersReservations(currentCustomer);
    reservations.forEach(System.out::println);
  }

  public static void findRoom(Scanner scanner) {
    System.out.println("1. Find and reserve a room");

    // Enter customer
    if (currentCustomer == null) {
      boolean isInvalidCustomer = false;
      do {
        if (isInvalidCustomer) {
          System.out.println("Please enter a valid customer");
        }
        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();
        try {
          currentCustomer = customerService.getCustomer(customerEmail);
          System.out.println(
              "Welcome " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName()
                  + " to Hotel Application");
          isInvalidCustomer = false;
        } catch (IllegalArgumentException ex) {
          isInvalidCustomer = true;
          System.out.println(ex.getMessage());
        }
      } while (isInvalidCustomer);
    }

    Date checkInDate = null;
    boolean isInvalidCheckInDate = false;
    do {
      if (isInvalidCheckInDate) {
        System.out.println("Please enter a valid date");
      }
      System.out.print("Enter check-in date (dd/MM/yyyy): ");
      String checkInDateString = scanner.nextLine();
      checkInDate = convertStringToDate(checkInDateString);
      isInvalidCheckInDate = checkInDate == null;
    } while (isInvalidCheckInDate);

    Date checkOutDate = null;
    boolean isInvalidCheckOutDate = false;
    do {
      if (isInvalidCheckOutDate) {
        System.out.println("Please enter a valid date");
      }
      System.out.print("Enter check-out date (dd/MM/yyyy): ");
      String checkOutDateString = scanner.nextLine();
      checkOutDate = convertStringToDate(checkOutDateString);
      isInvalidCheckOutDate = checkOutDate == null;
    } while (isInvalidCheckOutDate);

    Collection<IRoom> rooms = reservationService.searchAvailableRooms(checkInDate, checkOutDate);
    if (rooms.isEmpty()) {
      searchRecommnedRooms(checkInDate, checkOutDate);
    }
    else {
      selecteAvailableRoom(rooms, scanner, checkInDate, checkOutDate);
    }
  }

  /**
   * Search for recommended rooms. If there are no available rooms for the customer's date range, a search will
   * be performed that displays recommended rooms on alternative dates. The recommended room search will
   * add seven days to the original check-in and check-out dates to see if the hotel has any availabilities and then
   * display the recommended rooms/dates to the customer.
   * 
   * Example: Ifthe customers date range search is 1/1/2020 - 1/5/2020 and all rooms are booked, the system
   * wilf search again for recommended rooms using the date range 1/8/2020 - 1/12/2020 If there are no
   * recommended rooms, the system will not return any rooms.
  */
  private static void searchRecommnedRooms(Date originalCheckInDate, Date originalCheckOutDate)  {
    Date alternativeCheckInDate = new Date(originalCheckInDate.getTime() + 7 * 24 * 60 * 60 * 1000);
    Date alternativeCheckOutDate = new Date(originalCheckOutDate.getTime() + 7 * 24 * 60 * 60 * 1000);
  
    Collection<IRoom> alternativeRooms = reservationService.searchAvailableRooms(alternativeCheckInDate, alternativeCheckOutDate);
  
    if (alternativeRooms.isEmpty()) {
      System.out.println("Out of rooms");
    }
    else {
      System.out.println("Recommended rooms for alternative dates:");
      alternativeRooms.forEach(System.out::println);
      System.out.println("Alternative check-in date: " + convertDateToString(alternativeCheckInDate));
      System.out.println("Alternative check-out date: " + convertDateToString(alternativeCheckOutDate));
    }
  }

  private static void selecteAvailableRoom(Collection<IRoom> rooms, Scanner scanner, Date checkInDate, Date checkOutDate){
    System.out.println("Available rooms:");
    rooms.forEach(System.out::println);

    IRoom selectedRoom = null;
    boolean isInvalidRoom = false;

    do {
      if (isInvalidRoom) {
        System.out.println("Please enter a valid room number!");
      }

      System.out.print("Enter room number to reserve: ");
      String roomNumber = scanner.nextLine();
      try {
        // Your room number has to be included in the rooms.
        boolean isRoomNumberBelongToList = rooms.stream().anyMatch(r -> r.getRoomNumber().equals(roomNumber.trim()));
        if (isRoomNumberBelongToList) {
          selectedRoom = reservationService.getARoom(roomNumber);
          isInvalidRoom = false;
        }
        else {
          isInvalidRoom = true;
        }
      } catch (IllegalArgumentException ex) {
        isInvalidRoom = true;
        System.out.println(ex.getMessage());
      }
    } while (isInvalidRoom);

    boolean canBookThisRoom = reservationService.canBookThisRoom(selectedRoom.getRoomNumber(),
        currentCustomer.getEmail());
    if (canBookThisRoom) {
      reservationService.reserveARoom(currentCustomer, selectedRoom, checkInDate, checkOutDate);
      System.out.println("Room reserved successfully!");
    } else {
      System.out.println("A user should not be able to book a single room twice for the same date range.");
    }
  }

  private static Date convertStringToDate(String dateString) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    try {
      return formatter.parse(dateString);
    } catch (ParseException e) {
      System.out.println("Date format is invalid.");
      return null;
    }
  }

  private static String convertDateToString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    return formatter.format(date);
  }
}
