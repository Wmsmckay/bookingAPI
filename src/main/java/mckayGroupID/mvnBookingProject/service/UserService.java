package mckayGroupID.mvnBookingProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mckayGroupID.mvnBookingProject.entity.User;
import mckayGroupID.mvnBookingProject.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	public User getUser(Long id) {
		return repo.findOne(id);
	}
	
	public Iterable<User> getUsers(){
		return repo.findAll();
	}
	
	public void deleteUser(Long id) {
		repo.delete(id);
	}
	
	public User updateUser(Long id, User user) {
		User foundUser = repo.findOne(id);
		if (foundUser != null) {
			foundUser.setFirstName(user.getFirstName());
			foundUser.setLastName(user.getLastName());
			repo.save(foundUser);
		}
		return foundUser;
	}
	
	public User createUser(User user) {
		return repo.save(user);
	}
}
