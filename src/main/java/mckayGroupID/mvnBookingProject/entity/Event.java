package mckayGroupID.mvnBookingProject.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long eventID;
	private String eventName;
	private String eventDescription;
	private int currentNumberOfAttendees;
	private float eventPrice;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(mappedBy = "event")
	private List<Reservation> reservations;
	
	public Long getEventID() {
		return eventID;
	}
	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public int getCurrentNumberOfAttendees() {
		return currentNumberOfAttendees;
	}
	public void setCurrentNumberOfAttendees(int currentNumberOfAttendees) {
		this.currentNumberOfAttendees = currentNumberOfAttendees;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public float getEventPrice() {
		return eventPrice;
	}
	public void setEventPrice(float eventPrice) {
		this.eventPrice = eventPrice;
	}	 
}
