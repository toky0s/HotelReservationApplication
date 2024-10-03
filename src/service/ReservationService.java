package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomType;

public class ReservationService {
  private static ReservationService INSTANCE;
  private Collection<Reservation> reservations = new ArrayList<Reservation>();
  private Collection<IRoom> rooms = new ArrayList<IRoom>();

  public static ReservationService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ReservationService();
    }
    return INSTANCE;
  }

  public Collection<IRoom> getAllRooms() {
    return rooms;
  }

  private boolean isRoomExists(String roomId) {
    return rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomId.trim()));
  }

  public void addRoom(IRoom room) {
    if (ReservationService.INSTANCE.isRoomExists(room.getRoomNumber())) {
      throw new IllegalStateException("Room with room ID: " + room.getRoomNumber() + " already exists");
    }
    else {
      rooms.add(room);
    }
  }

  public IRoom getARoom(String roomId) throws IllegalArgumentException {
    Optional<IRoom> optionalRoom = rooms.stream().filter(room -> room.getRoomNumber().equals(roomId.trim())).findFirst();
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

  public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
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

  public Collection<IRoom> searchAvailableRooms(Date checkInDate, Date checkOutDate) {
      List<IRoom> availableRooms = new ArrayList<>(rooms);
  
      for (Reservation reservation : reservations) {
          IRoom room = reservation.getRoom();
          Date reservationCheckInDate = reservation.getCheckInDate();
          Date reservationCheckOutDate = reservation.getCheckOutDate();
  
          if (checkInDate.before(reservationCheckOutDate) && checkOutDate.after(reservationCheckInDate)) {
              availableRooms.remove(room);
          }
      }
  
      return availableRooms;
  }

  public Collection<IRoom> searchRecommendedRooms(Date checkInDate, Date checkOutDate) {
      Collection<IRoom> availableRooms = searchAvailableRooms(checkInDate, checkOutDate);
  
      Collection<IRoom> recommendedRooms = availableRooms.stream()
          .sorted(Comparator.comparingDouble(IRoom::getRoomPrice))
          .collect(Collectors.toList());
  
      return recommendedRooms;
  }

  // A user should not be able to book a single room twice for the same date range.
  public boolean canBookThisRoom(String roomId, String userEmail) {
    List<Reservation> userReservations = reservations.stream()
        .filter(reservation -> reservation.getCustomer().getEmail().equals(userEmail))
        .collect(Collectors.toList());
  
    return userReservations.stream()
        .noneMatch(reservation -> {
          Date checkInDate = reservation.getCheckInDate();
          Date checkOutDate = reservation.getCheckOutDate();
          return reservation.getRoom().getRoomNumber().equals(roomId) &&
              reservation.getRoom().getRoomType() == RoomType.SINGLE &&
              (checkInDate.before(checkInDate) && checkOutDate.after(checkInDate) ||
               checkInDate.before(checkOutDate) && checkOutDate.after(checkOutDate));
        });
  }
}
