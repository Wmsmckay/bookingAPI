package mckayGroupID.mvnBookingProject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long invoiceID;
	private boolean invoicePaid;
	//private long reservationId;
	
	@ManyToOne
	@JoinColumn(name = "reservation_Id")
	private Reservation reservation;
	
	public Long getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(Long invoiceID) {
		this.invoiceID = invoiceID;
	}
	public boolean isInvoicePaid() {
		return invoicePaid;
	}
	public void setInvoicePaid(boolean invoicePaid) {
		this.invoicePaid = invoicePaid;
	}
}
