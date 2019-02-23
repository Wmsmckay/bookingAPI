package mckayGroupID.mvnBookingProject.controllers;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mckayGroupID.mvnBookingProject.entity.Credentials;
import mckayGroupID.mvnBookingProject.entity.Event;
import mckayGroupID.mvnBookingProject.entity.Invoice;
import mckayGroupID.mvnBookingProject.entity.Reservation;
import mckayGroupID.mvnBookingProject.entity.User;
import mckayGroupID.mvnBookingProject.service.AuthService;
import mckayGroupID.mvnBookingProject.service.EventsService;
import mckayGroupID.mvnBookingProject.service.InvoiceService;
import mckayGroupID.mvnBookingProject.service.ReservationService;
import mckayGroupID.mvnBookingProject.service.UserService;

@RestController
public class UserController {
	
	@Autowired 
	private AuthService authService;
	
	@Autowired
	UserService service;
	
	@Autowired
	EventsService eventsService;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	InvoiceService invoiceService;
	
	
	// admin only
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public User addUser(@RequestBody User user) {
		
		
		return service.createUser(user);
	}
	
	@RequestMapping(value="/users")
	public Iterable<User> getUsers() {
		return service.getUsers();
	}
	
	@RequestMapping(value="/users/{id}")
	public User getUser(@PathVariable Long id) {
		return service.getUser(id);
	}
	
	// admin or individual user only
	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT)
	public User updateUser(@PathVariable long id, @RequestBody User user) {
		return service.updateUser(id, user);
	}
	
	// admin and individual user only
	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
	public void deleteUser(HttpServletRequest request, @PathVariable Long id) {
		if (authService.isAdmin(request)) {
			service.deleteUser(id);
			System.out.println("User deleted.");
		}else {
			System.out.println("You do not have Admin permissions.");
		}
	}
	
	//creates and event under the user with the given id
	@RequestMapping(value="/users/{userId}/events", method=RequestMethod.POST)
	public Event addEvent(@PathVariable Long userId, @RequestBody Event event,
			Invoice invoice) {
		Event newEvent = eventsService.createEvent(userId, event, invoice);
		Invoice newInvoice = invoiceService.createInvoice(invoice);
		reservationService.createReservation(userId, newEvent.getEventID(), newInvoice.getInvoiceID());
		return newEvent;
	}
	
	@RequestMapping(value="users/register", method=RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody Credentials credentials){
		try {
			return new ResponseEntity<Object>(authService.register(credentials), HttpStatus.CREATED);
		} catch (AuthenticationException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="users/login", method=RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Credentials credentials){
		try {
			return new ResponseEntity<Object>(authService.login(credentials), HttpStatus.OK);
		} catch (AuthenticationException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
}
