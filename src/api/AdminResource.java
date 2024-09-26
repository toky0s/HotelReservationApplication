package api;

import java.util.Collection;
import java.util.List;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
  public static final AdminResource INSTANCE = new AdminResource();
  private static final CustomerService CUSTOMER_SERVICE = CustomerService.INSTANCE;
  private static final ReservationService RESERVERY_SERVICE = ReservationService.INSTANCE;

  public Customer getCustomer(String email) {
    return CUSTOMER_SERVICE.getCustomer(email);
  }
  
  public void addRoom(List<IRoom> rooms) {
    rooms.forEach(r -> RESERVERY_SERVICE.addRoom(r));
  }

  public Collection<IRoom> getAllRooms() {
    return RESERVERY_SERVICE.getAllRooms();
  }

  public Collection<Customer> getAllCustomers() {
    return CUSTOMER_SERVICE.getAllCustomers();
  }
  public void displayAllReservations() {
    RESERVERY_SERVICE.printAllReservation();
  }
}
