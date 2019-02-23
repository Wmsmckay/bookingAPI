package mckayGroupID.mvnBookingProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mckayGroupID.mvnBookingProject.entity.Event;
import mckayGroupID.mvnBookingProject.entity.Reservation;
import mckayGroupID.mvnBookingProject.repository.EventRepository;
import mckayGroupID.mvnBookingProject.repository.InvoiceRepository;
import mckayGroupID.mvnBookingProject.repository.ReservationRepository;
import mckayGroupID.mvnBookingProject.repository.UserRepository;

@Service
public class ReservationService {
	@Autowired
	ReservationRepository repo;
	
	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	InvoiceRepository invoiceRepo;
	
	@Autowired
	InvoiceService invoiceService;
	
	public Reservation getReservation(Long id) {
		return repo.findOne(id);
	}
	
	public Iterable<Reservation> getReservations(){
		return repo.findAll();
	}
	
	public void deleteReservation(Long id) {
		repo.delete(id);
	}
	
	public Reservation updateReservation(Long id, Reservation reservation) {
		Reservation foundReservation = repo.findOne(id);
		if (foundReservation != null) {
			foundReservation.setReservationID(reservation.getReservationID());
			foundReservation.setEvent(reservation.getEvent());
			foundReservation.setUser(reservation.getUser());
			repo.save(foundReservation);
		}
		return foundReservation;
	}
	
	public Reservation createReservation(Long userId, Long eventId, Long invoiceId) {
		Reservation reservation = new Reservation();
		System.out.println(reservation);
		System.out.println(reservation.getEvent());
		Event event = eventRepo.findOne(eventId);
		reservation.setUser(userRepo.findOne(userId));
		reservation.setEvent(event);
		event.setCurrentNumberOfAttendees(event.getCurrentNumberOfAttendees() + 1);
		return repo.save(reservation);
	}
}
