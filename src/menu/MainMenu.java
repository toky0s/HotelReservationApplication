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
    System.out.println("Available rooms:");
    rooms.forEach(System.out::println);

    IRoom selectedRoom = null;
    boolean isInvalidRoom = false;
    do {
      if (isInvalidRoom) {
        System.out.println("Please enter a valid room number");
      }

      System.out.print("Enter room number to reserve: ");
      String roomNumber = scanner.nextLine();
      try {
        selectedRoom = reservationService.getARoom(roomNumber);
        isInvalidRoom = false;
      } catch (IllegalArgumentException ex) {
        isInvalidRoom = true;
        System.out.println(ex.getMessage());
      }
    } while (isInvalidCheckOutDate);

    boolean canBookThisRoom = reservationService.canBookThisRoom(selectedRoom.getRoomNumber(),
        currentCustomer.getEmail());
    if (canBookThisRoom) {
      reservationService.reserveARoom(currentCustomer, selectedRoom, checkInDate, checkOutDate);
      System.out.println("Room reserved successfully!");
    } else {
      System.out.println("A user should not be able to book a single room twice for the same date range.");
    }
  }

  public static Date convertStringToDate(String dateString) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    try {
      return formatter.parse(dateString);
    } catch (ParseException e) {
      System.out.println("Date format is invalid.");
      return null;
    }
  }
}
