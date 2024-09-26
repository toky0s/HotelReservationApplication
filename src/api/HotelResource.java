package api;

import java.util.Collection;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {
  public static HotelResource INSTANCE = new HotelResource();

  public Customer getCustomer(String email) {
      return CustomerService.INSTANCE.getCustomer(email);
  }

  public void createACustomer(String email, String firstName, String lastName) {
    CustomerService.INSTANCE.addCustomer(email, firstName, lastName);
  }

  public IRoom getRoom(String roomNumber) {
    return ReservationService.INSTANCE.getARoom(roomNumber);
  }

  public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
    Customer customer = CustomerService.INSTANCE.getCustomer(customerEmail);
    return ReservationService.INSTANCE.reserveARoom(customer, room, checkInDate, checkOutDate);
  }

  public Collection<Reservation> getCustomersReservations(String customerEmail) {
    Customer customer = CustomerService.INSTANCE.getCustomer(customerEmail);
    return ReservationService.INSTANCE.getCustomersReservations(customer);
  }

  public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
    return ReservationService.INSTANCE.findRooms(checkIn, checkOut);
  }
}
