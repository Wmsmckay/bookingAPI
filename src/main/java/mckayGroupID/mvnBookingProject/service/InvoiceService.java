package mckayGroupID.mvnBookingProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mckayGroupID.mvnBookingProject.entity.Invoice;
import mckayGroupID.mvnBookingProject.repository.InvoiceRepository;

@Service
public class InvoiceService {
	
	@Autowired
	InvoiceRepository repo;
	
	public Invoice getInvoice(Long id) {
		return repo.findOne(id);
	}
	
	public Iterable<Invoice> getInvoices(){
		return repo.findAll();
	}
	
	public void deleteInvoice(Long id) {
		repo.delete(id);
	}
	
	public Invoice updateInvoice(Long id, Invoice invoice) {
		Invoice foundInvoice = repo.findOne(id);
		if (foundInvoice != null) {
			foundInvoice.setInvoiceID(invoice.getInvoiceID());
			foundInvoice.setInvoicePaid(invoice.isInvoicePaid());
			repo.save(foundInvoice);
		}
		return foundInvoice;
	}
	
	public Invoice createInvoice(Invoice invoice) {
		return repo.save(invoice);
	}
}