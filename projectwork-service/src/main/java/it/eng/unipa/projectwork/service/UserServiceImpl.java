package it.eng.unipa.projectwork.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.eng.unipa.projectwork.dao.DAO;
import it.eng.unipa.projectwork.model.User;

@Stateless
public class UserServiceImpl implements UserService{
	
	@EJB
	DAO dao;

	@Override
	public List<User> allUsers() {
		return dao.find(User.class);
	}

	@Override
	public User getUser(String username) {
		return dao.load(User.class,username);
	}
	
	@Override
	public User getUserFromUsernameTelegram(String usernameTelegram) {
		Map<String,Object> m = new HashMap<>();
		m.put("usernameTelegram", usernameTelegram);
		List<User> l = dao.find(User.class, "select u from User u where u.usernameTelegram = :usernameTelegram", m);
		return l.isEmpty()?null:l.get(0);
	}

}
