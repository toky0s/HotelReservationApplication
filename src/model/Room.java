package model;

public class Room implements IRoom {
	protected String roomNumber;
	protected Double price;
	protected RoomType roomType;
	
	public Room(String roomNumber, Double price, RoomType roomType) {
		this.price = price;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
	}

	@Override
	public String getRoomNumber() {
		return roomNumber;
	}

	@Override
	public Double getRoomPrice() {
		return price;
	}

	@Override
	public RoomType getRoomType() {
		return roomType;
	}

	@Override
	public boolean isFree() {
		return price == 0d;
	}
	
	@Override
	public String toString() {
		return "{Room No: " + roomNumber + ", type: " + roomType + ", price: " + price + "}";
	}
}
