package model;

public class FreeRoom extends Room {
	public FreeRoom(String roomNumber, RoomType roomType) {
		super(roomNumber, 0d, roomType);
	}
	
	@Override
	public String toString() {
		return "{FreeRoom No: " + roomNumber + ", type: " + roomType + ", price: " + price + "}";
	}
}
