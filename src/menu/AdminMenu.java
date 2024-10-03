package menu;

import java.util.Scanner;

import api.AdminResource;
import model.Room;
import model.RoomType;

public class AdminMenu {
  public static AdminMenu INSTANCE = new AdminMenu();

  public void displayMenu(Scanner scanner) {
    do {
      System.out.println("Welcome to the Admin Menu!");
      System.out.println("1. See all customers");
      System.out.println("2. See all Rooms");
      System.out.println("3. See all Reservations");
      System.out.println("4. Add a Room");
      System.out.println("5. Back to Main Menu");

      System.out.print("Enter your choice: ");
      String data = scanner.nextLine();
      try {
        int choice = Integer.parseInt(data);
        switch (choice) {
          case 1:
            System.out.print("\033\143");
            System.out.println("SEE ALL CUSTOMERS");
            AdminResource.INSTANCE.displayAllCustomers();
            break;
          case 2:
            System.out.print("\033\143");
            System.out.println("SEE ALL ROOMS");
            AdminResource.INSTANCE.displayAllRooms();
            break;
          case 3:
            System.out.print("\033\143");
            System.out.println("SEE ALL RESERVATIONS");
            AdminResource.INSTANCE.displayAllReservations();
            break;
          case 4:
            System.out.print("\033\143");
            System.out.println("ADD A ROOM");
            addARoom(scanner);
            break;
          case 5:
            return;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } catch (NumberFormatException ex) {
        System.out.println("Invalid choice. Please try again.");
        continue;
      }
    } while (true);
  }

  private void addARoom(Scanner scanner) {
    System.out.print("Enter room number: ");
    String roomId = (scanner.nextLine()).trim();
    boolean isInvalidRoomPrice = false;
    double roomPrice = 0;
    do {
      System.out.print("Enter room price: ");
      String roomPriceStr = scanner.nextLine();
      try {
        roomPrice = Double.parseDouble(roomPriceStr);
        isInvalidRoomPrice = false;
      } catch (NumberFormatException ex) {
        System.out.println("Invalid room price. Please try again.");
        isInvalidRoomPrice = true;
      }
    } while (isInvalidRoomPrice);

    RoomType roomType = RoomType.SINGLE;
    boolean isInvalidRoomType = false;
    do {
      System.out.println("Select room type");
      System.out.println("(y). SINGLE");
      System.out.println("(other). DOUBLE");
      System.out.print("Your choice: ");
      String selection = scanner.nextLine();
      
      if (selection.toLowerCase().trim() == "y") {
        roomType = RoomType.SINGLE;
      }
      else {
        roomType = RoomType.DOUBLE;
      }
      isInvalidRoomType = false;
    }
    while (isInvalidRoomType);

    Room room = new Room(roomId, roomPrice, roomType);
    if (AdminResource.INSTANCE.addRoom(room)) {
      System.out.println("Added room successfully");
    }
    else {
      System.out.println("A room with the same room number already exists");
    }
  }
}
