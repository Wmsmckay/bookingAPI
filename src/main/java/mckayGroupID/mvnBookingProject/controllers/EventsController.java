package mckayGroupID.mvnBookingProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mckayGroupID.mvnBookingProject.entity.Event;
import mckayGroupID.mvnBookingProject.service.AuthService;
import mckayGroupID.mvnBookingProject.service.EventsService;

@RestController
public class EventsController {
	
	private static final Logger Logger = LogManager.getLogger(EventsController.class);
	
	@Autowired
	EventsService service;
	
	@Autowired 
	private AuthService authService;
	
	// returns all of the event in the list
	@RequestMapping("/events")
	public Iterable<Event> getEvents() {
		Logger.info("Info message from the logger.");
		return service.getEvents();
	}
	
	//returns a specific event by the id
	@RequestMapping(value="/events/{id}")
	public Event getEvent(@PathVariable Long id) {
		return service.getEvent(id);
	}
	
	//updates an event by the id
	@RequestMapping(value="/events/{id}", method=RequestMethod.PUT)
	public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
		return service.updateEvent(id, event);
	}
	
	// deletes an event by the id
	@RequestMapping(value="/events/{id}", method=RequestMethod.DELETE)
	public void deleteEvent(HttpServletRequest request, @PathVariable Long id) {
		if (authService.isAdmin(request)) {
			service.deleteEvent(id);
		}else {
			System.out.println("You do not have Admin permissions.");
		}
	}
	
	
}
