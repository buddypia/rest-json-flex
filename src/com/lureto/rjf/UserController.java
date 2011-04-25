package com.lureto.rjf;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	private static Logger logger = Logger.getLogger( UserController.class );
	
	@Autowired
	private PMF pmf;
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("users")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> listUsers() throws IOException {
        PersistenceManager pm = pmf.getManager();
		Query query = pm.newQuery(User.class);
		List<User> users;
		try {
			users = (List<User>) query.execute();
		} finally {
			query.closeAll();
		}
		return users;
	}
	
	@ModelAttribute("user")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public User saveUser( @RequestBody User user ) throws IOException {
		logger.debug(user);
        PersistenceManager pm = pmf.getManager();
        try {
            pm.makePersistent(user);
        } finally {
            pm.close();
        }		
		return user;
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE )
	public void deleteUser( @PathVariable String userId ) throws IOException {
		deleteUserIntenral(userId);
	}
	
	@RequestMapping(value = "/{userId}/delete", method = RequestMethod.POST )
	public void deleteUser2( @PathVariable String userId ) throws IOException {
		deleteUserIntenral(userId);
	}

	private void deleteUserIntenral( String userId ) throws IOException {
		logger.debug("Deleting user id > "+userId);
        PersistenceManager pm = pmf.getManager();
        try {
        	Long id = Long.parseLong(userId);
        	User user = pm.getObjectById( User.class, id ); 
            pm.deletePersistent(user);
        } finally {
            pm.close();
        }		
	}

	public PMF getPmf() {
		return pmf;
	}

	public void setPmf(PMF pmf) {
		this.pmf = pmf;
	}
	
}
