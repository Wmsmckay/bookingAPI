package mckayGroupID.mvnBookingProject.repository;

import org.springframework.data.repository.CrudRepository;

import mckayGroupID.mvnBookingProject.entity.Event;

public interface EventRepository extends CrudRepository<Event, Long>{
	
}
