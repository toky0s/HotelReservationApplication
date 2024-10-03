package menu;

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
  
  public static void initializeDummryData() {
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

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
      // john.doe@example.com
      reservationService.reserveARoom(customers[0],rooms[0],new SimpleDateFormat("dd/MM/yyyy").parse("30/09/2024"),new SimpleDateFormat("dd/MM/yyyy").parse("02/10/2024"));
      reservationService.reserveARoom(customers[0],rooms[6],new SimpleDateFormat("dd/MM/yyyy").parse("12/10/2024"),new SimpleDateFormat("dd/MM/yyyy").parse("22/10/2024"));

      reservationService.reserveARoom(customers[1],rooms[1],new SimpleDateFormat("dd/MM/yyyy").parse("11/08/2024"),new SimpleDateFormat("dd/MM/yyyy").parse("15/08/2024"));
      reservationService.reserveARoom(customers[2],rooms[2],new SimpleDateFormat("dd/MM/yyyy").parse("12/07/2024"),new SimpleDateFormat("dd/MM/yyyy").parse("20/07/2024"));
      reservationService.reserveARoom(customers[3],rooms[3],new SimpleDateFormat("dd/MM/yyyy").parse("04/06/2024"),new SimpleDateFormat("dd/MM/yyyy").parse("05/07/2024"));
    } catch (Exception e) {
      System.out.println("Create dummy reservation failed: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    initializeDummryData();
    MainMenu mainMenu = MainMenu.INSTANCE;
    mainMenu.displayMenu();
    // Add other menu options and functionality as needed.
  }
}
