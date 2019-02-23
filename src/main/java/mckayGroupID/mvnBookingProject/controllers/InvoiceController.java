package mckayGroupID.mvnBookingProject.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mckayGroupID.mvnBookingProject.entity.Invoice;
import mckayGroupID.mvnBookingProject.service.AuthService;
import mckayGroupID.mvnBookingProject.service.InvoiceService;

@RestController
public class InvoiceController {
	
	@Autowired
	InvoiceService service;
	
	@Autowired 
	private AuthService authService;
	
	@RequestMapping(value="/invoice", method=RequestMethod.POST)
	public Invoice addInvoice(@RequestBody Invoice invoice) {
		return service.createInvoice(invoice);
	}
	
	@RequestMapping(value="/invoice")
	public Iterable<Invoice> getInvoices(){
		return service.getInvoices();
	}
	
	@RequestMapping(value="/invoice/{id}")
	public Invoice getInvoice(@PathVariable Long id) {
		return service.getInvoice(id);
	}
	
	@RequestMapping(value="/event{id}", method=RequestMethod.PUT)
	public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
		return service.updateInvoice(id, invoice);
	}
	
	@RequestMapping(value="/invoice/{id}", method=RequestMethod.DELETE)
	public void deleteInvoice(HttpServletRequest request, @PathVariable Long id) {
		if (authService.isAdmin(request)) {
			service.deleteInvoice(id);
		}else {
			System.out.println("You do not have Admin permissions.");
		}
		
	}
}
