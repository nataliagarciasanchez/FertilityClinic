package FertilityClinicJPA;

import java.security.MessageDigest;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
//import java.persistnce.TypedQuery;

import FertilityClinicInterfaces.UserManager;
import FertilityClinicPOJOs.Role;
import FertilityClinicPOJOs.User;

public class JPAUserManager implements UserManager {
	private EntityManager em;
	
	public JPAUserManager() {
		super();
		this.connect();
	}
	

	@Override
	
	public User checkPassword(String email, String pass) {
		User user=null;
		try {
			Query query = em.createNativeQuery("SELECT * from users where email =? and password=?", User.class);
			query.setParameter(1, email);
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			byte[] pw = md.digest();
			
			q.setParameter(2, pw);
			
		}catch(Exception e)
		{e.printStackTrace();}
			
		
		try {
			user = (User) query.getSingleResult();
			
		}catch(NoResultException e) {
			System.out.println("\"No user found with the provided email and password.")
		}
		
		return user;
	}

	}
@Override
public void connect() {
	
	
	em = Persistence.createEntityManagerFactory("fertilityclinic-provider").createEntityManager();

	em.getTransaction().begin();
	em.createNativeQuery("PRAGMA foreign_keys = ON").executeUpdate();
	em.getTransaction().commit();
	
	if(this.getRoles().isEmpty())
	{
		Role manager = new Role("manager");
		Role doctor = new Role("doctor");
		Role patient=new Role ("patient");
		this.newRole(manager);
		this.newRole(doctor);
		this.newRole(patient);
	}
	
}

@Override
public List<Role> getRoles() {
	    List<Role> roles = null;
	    try {
	    	Query query = em.createNativeQuery("SELECT * FROM roles", Role.class);
	    	List<Role> roles = (List<Role>) query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return roles;
	}

@Override
public void newRole(Role role) {
	try {
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
	


@Override
public void newUser(User user) {
	try {
	em.getTransaction().begin();
	em.persist(user);
	em.getTransaction().commit();
}catch (Exception e) {
	e.printStackTrace();
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }
}
}

@Override
public void disconnect() {
	em.close();
}

@Override
public Role getRole(Integer id) {
	Role role=null;
	try {
	Query query = em.createNativeQuery("SELECT * FROM roles where id="+id, Role.class);
	role = (Role) query.getSingleResult();
	}catch(NoResultException nre) {
		System.out.println("No role found with id: "+id);
	}catch(Exception e) {
		e.printStackTrace();
	}
	return role;
}

@Override
public User getUser(String email) {
	Query query = em.createNativeQuery("SELECT * FROM users where email="+email, User.class);
	User user = (User) query.getSingleResult();
	
	return user;
}

@Override
public void changePassword(User user, String new_passwd) {
	 try {
	        Query query = em.createNativeQuery("UPDATE users SET password = ? WHERE id = ?");
	        query.setParameter(1, user.getEmail());
	        
	        query.setParameter(2, new_passwd);
	        query.executeUpdate();
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	    }
}



}


	
	
	
	
	
	
	
	
	
}
