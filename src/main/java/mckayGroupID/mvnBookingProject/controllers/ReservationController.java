package mckayGroupID.mvnBookingProject.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mckayGroupID.mvnBookingProject.entity.Reservation;
import mckayGroupID.mvnBookingProject.requests.ReservationRequest;
import mckayGroupID.mvnBookingProject.service.AuthService;
import mckayGroupID.mvnBookingProject.service.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	ReservationService service;
	
	@Autowired 
	private AuthService authService;
	
	@RequestMapping(value="/reservations", 
			method=RequestMethod.POST)
	public Reservation addReservation(@RequestBody ReservationRequest request) {
		return service.createReservation(request.getUserId(), request.getEventId(),
			request.getInvoiceId());
	}
	
	@RequestMapping(value="/reservations")
	public Iterable<Reservation> getReservations() {
		return service.getReservations();
	}
	
	@RequestMapping(value="/reservations/{id}")
	public Reservation getReservation(@PathVariable Long id) {
		return service.getReservation(id);
	}
	
	@RequestMapping(value="/reservations/{id}", method=RequestMethod.PUT)
	public Reservation updateReservation(@PathVariable Long id, 
			@RequestBody Reservation reservation) {
		return service.updateReservation(id, reservation);
	}
	
	@RequestMapping(value="/reservations/{id}", method=RequestMethod.DELETE)
	public void deleteReservation(HttpServletRequest request, @PathVariable Long id) {
		if (authService.isAdmin(request)) {
			service.deleteReservation(id);
		} else {
			System.out.println("You do not have Admin permissions.");
		}
	}
}
