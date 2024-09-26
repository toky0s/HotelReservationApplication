package menu;

public class AdminMenu {
  public static AdminMenu INSTANCE = new AdminMenu();

  public void displayMenu() {
    System.out.println("Welcome to the Admin Menu!");
    System.out.println("1. See all customers");
    System.out.println("2. See all Rooms");
    System.out.println("3. See all Reservations");
    System.out.println("4. Add a Room");
    System.out.println("5. Back to Main Menu");
  }
}
