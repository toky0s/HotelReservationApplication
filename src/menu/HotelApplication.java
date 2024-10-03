package menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import model.Customer;
import model.FreeRoom;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

public class HotelApplication {
  private static CustomerService customerService = CustomerService.getInstance();
  private static ReservationService reservationService = ReservationService.getInstance();

  public static void initializeDummryData() {

    Customer[] customers = new Customer[] {
        new Customer("John", "Doe", "john.doe@example.com"),
        new Customer("Jane", "Smith", "jane.smith@example.com"),
        new Customer("Alice", "Johnson", "alice.johnson@example.com"),
        new Customer("Bob", "Williams", "bob.williams@example.com"),
        new Customer("Emily", "Brown", "emily.brown@example.com"),
    };
    Arrays.stream(customers).forEach(c -> customerService.addCustomer(c));

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
    Arrays.stream(rooms).forEach(r -> reservationService.addRoom(r));

    try {
      book(customers[0], rooms[0], "30/09/2024", "02/10/2024");
      book(customers[0], rooms[6], "12/10/2024", "22/10/2024");
      book(customers[1], rooms[1], "11/08/2024", "15/08/2024");
      book(customers[2], rooms[2], "12/07/2024", "20/07/2024");
      book(customers[3], rooms[3], "04/06/2024", "05/07/2024");
    } catch (Exception e) {
      System.out.println("Create dummy reservation failed: " + e.getMessage());
    }
  }

  private static void book(Customer customer, IRoom room, String checkIn, String checkOut) throws ParseException {
    reservationService.reserveARoom(customer, room, new SimpleDateFormat("dd/MM/yyyy").parse(checkIn), new SimpleDateFormat("dd/MM/yyyy").parse(checkOut));
  }

  public static void main(String[] args) {
    initializeDummryData();
    MainMenu mainMenu = MainMenu.INSTANCE;
    mainMenu.displayMenu();
    // Add other menu options and functionality as needed.
  }
}
