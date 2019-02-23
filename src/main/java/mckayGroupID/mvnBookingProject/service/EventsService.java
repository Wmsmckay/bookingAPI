package mckayGroupID.mvnBookingProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mckayGroupID.mvnBookingProject.entity.Event;
import mckayGroupID.mvnBookingProject.entity.Invoice;
import mckayGroupID.mvnBookingProject.entity.Reservation;
import mckayGroupID.mvnBookingProject.repository.EventRepository;
import mckayGroupID.mvnBookingProject.repository.ReservationRepository;
import mckayGroupID.mvnBookingProject.repository.UserRepository;

@Service
public class EventsService {
	
	@Autowired
	EventRepository repo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	InvoiceService invoiceService;
	
	public Event getEvent(Long id) {
		return repo.findOne(id);
	}
	
	public Iterable<Event> getEvents(){
		return repo.findAll();
	}
	
	public void deleteEvent(Long id) {
		repo.delete(id);
	}
	
	public Event updateEvent(Long id, Event event) {
		Event foundEvent = repo.findOne(id);
		if (foundEvent != null) {
			foundEvent.setCurrentNumberOfAttendees(event.getCurrentNumberOfAttendees());
			foundEvent.setEventDescription(event.getEventDescription());
			foundEvent.setUser(event.getUser());
			foundEvent.setEventName(event.getEventName());
			repo.save(foundEvent);
		}
		return foundEvent;
	}
	
	public Event createEvent(Long userId, Event event, Invoice invoice) {
		event.setUser(userRepo.findOne(userId));
		invoiceService.createInvoice(invoice);
		Long eventId = event.getEventID();
		Long invoiceId = invoice.getInvoiceID();
		return repo.save(event);
	}
}
