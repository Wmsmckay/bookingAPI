package mckayGroupID.mvnBookingProject.repository;

import org.springframework.data.repository.CrudRepository;

import mckayGroupID.mvnBookingProject.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long>{

}
