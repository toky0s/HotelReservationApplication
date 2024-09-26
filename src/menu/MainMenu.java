package menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import model.Customer;
import model.FreeRoom;
import model.IRoom;
import model.Reservation;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

public class MainMenu {
  public static MainMenu INSTANCE = new MainMenu();

  public void initializeDummryData() {
    Customer[] customers = new Customer[] {
        new Customer("John", "Doe", "john.doe@example.com"),
        new Customer("Jane", "Smith", "jane.smith@example.com"),
        new Customer("Alice", "Johnson", "alice.johnson@example.com"),
        new Customer("Bob", "Williams", "bob.williams@example.com"),
        new Customer("Emily", "Brown", "emily.brown@example.com"),
    };
    Arrays.stream(customers).forEach(c -> CustomerService.INSTANCE.addCustomer(c));

    IRoom[] rooms = {
        new Room("101", 50.0, RoomType.SINGLE),
        new Room("102", 75.0, RoomType.DOUBLE),
        new Room("103", 50.0, RoomType.SINGLE),
        new Room("104", 75.0, RoomType.DOUBLE),
        new Room("105", 50.0, RoomType.SINGLE),
        new Room("106", 75.0, RoomType.DOUBLE),
        new Room("107", 50.0, RoomType.SINGLE),
        new Room("108", 75.0, RoomType.DOUBLE),
        new Room("109", 50.0, RoomType.SINGLE),
        new Room("110", 75.0, RoomType.DOUBLE),
        new FreeRoom("201", RoomType.SINGLE),
        new FreeRoom("202", RoomType.DOUBLE),
        new FreeRoom("203", RoomType.SINGLE),
        new FreeRoom("204", RoomType.DOUBLE),
        new FreeRoom("205", RoomType.SINGLE),
    };

    Arrays.stream(rooms).forEach(r -> ReservationService.INSTANCE.addRoom(r));
  }

  public void displayMenu() {

    do {
      System.out.println("1. Find and reserve a room");
      System.out.println("2. See my reservations");
      System.out.println("2. Create an account");
      System.out.println("3. Admin");
      System.out.println("4. Exit");
      System.out.print("Enter your choice: ");

      Scanner scanner = new Scanner(System.in);
      String data = scanner.nextLine();

      int choice = Integer.parseInt(data);
      switch (choice) {
        case 1:
          System.out.print("\033\143");
          findRoom(scanner);
          break;
        case 2:
          System.out.println("Menu 2");
          break;
        case 3:
          System.out.println("Menu 3");
          break;
        case 4:
          scanner.close();
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    } while (true);
  }

  public static void findRoom(Scanner scanner) {
    Date checkInDate = null;
    Date checkOutDate = null;
    boolean isInvalidCheckInDate = false;
    boolean isInvalidCheckOutDate = false;
    do {
      if (isInvalidCheckInDate) {
        System.out.println("Please enter a valid date");
      }
      System.out.print("Enter check-in date (dd/MM/yyyy): ");
      String checkInDateString = scanner.nextLine();
      checkInDate = convertStringToDate(checkInDateString);
      isInvalidCheckInDate = checkInDate == null;
    } while (isInvalidCheckInDate);

    do {
      if (isInvalidCheckOutDate) {
        System.out.println("Please enter a valid date");
      }
      System.out.print("Enter check-out date (dd/MM/yyyy): ");
      String checkOutDateString = scanner.nextLine();
      checkOutDate = convertStringToDate(checkOutDateString);
      isInvalidCheckOutDate = checkOutDate == null;
    } while (isInvalidCheckOutDate);

    ReservationService.INSTANCE.findRooms(checkInDate, checkOutDate);
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
