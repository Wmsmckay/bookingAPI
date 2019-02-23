package mckayGroupID.mvnBookingProject.repository;

import org.springframework.data.repository.CrudRepository;

import mckayGroupID.mvnBookingProject.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByUsername(String username);
	
}
