package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
  public static ReservationService INSTANCE = new ReservationService();

  private Collection<Reservation> reservations = new ArrayList<Reservation>();
  private Collection<IRoom> rooms = new ArrayList<IRoom>();

  public Collection<IRoom> getAllRooms() {
    return rooms;
  }

  public void addRoom(IRoom room) {
    rooms.add(room);
  }

  public IRoom getARoom(String roomId) {
    Optional<IRoom> optionalRoom = rooms.stream().filter(room -> room.getRoomNumber().equals(roomId)).findFirst();
    if (optionalRoom.isPresent()) {
      return optionalRoom.get();
    } else {
      throw new IllegalArgumentException("Room not found");
    }
  }

  public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
    Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
    reservations.add(reservation);
    return reservation;
  }

  public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
    return reservations.stream().filter(reservation -> {
      return reservation.getCheckInDate() == checkInDate && reservation.getCheckOutDate() == checkOutDate;
    }).map(reservation -> reservation.getRoom()).toList();
  }

  public Collection<Reservation> getCustomersReservations(Customer customer) {
    return reservations.stream().filter(reservation -> reservation.getCustomer().equals(customer)).toList();
  }

  public void printAllReservation() {
    reservations.forEach(System.out::println);
  }
}
