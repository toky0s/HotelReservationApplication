package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
  private Customer customer;
  private IRoom room;
  private Date checkInDate;
  private Date checkOutDate;

  public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    this.customer = customer;
    this.room = room;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
  }

  // Getters and setters
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public IRoom getRoom() {
    return room;
  }

  public void setRoom(IRoom room) {
    this.room = room;
  }

  public Date getCheckInDate() {
    return checkInDate;
  }

  public void setCheckInDate(Date checkInDate) {
    this.checkInDate = checkInDate;
  }

  public Date getCheckOutDate() {
    return checkOutDate;
  }

  public void setCheckOutDate(Date checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  @Override
  public String toString() {
    return "{Reservation: Customer: " + customer + ", room: " + room + ", check-in: " + checkInDate + ", check-out: "
        + checkOutDate + "}";
  }

   @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation that = (Reservation) obj;
        return customer.getEmail().equals(that.customer.getEmail()) &&
               checkInDate.equals(that.checkInDate) &&
               checkOutDate.equals(that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer.getEmail(), checkInDate, checkOutDate, room.getRoomNumber());
    }
}
