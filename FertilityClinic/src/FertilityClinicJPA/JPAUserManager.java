package FertilityClinicJPA;

public class JPAUserManager implements UserManager {
	private EntityManager em;
	
	public JPAUserManager() {
		super();
		this.connect();
	}
	

	@Override
	
	public User checkPassword(String email, String pass) {
		User user;
		Query query = em.createNativeQuery("SELECT * from users where email =? and password=?", User.class);
		query.setParameter(1, email);
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			byte[] pw = md.digest();
			
			q.setParameter(2, pw);
			
		}catch(Exception e)
		{e.printStackTrace();}
			
		
		try {
			user = (User) query.getSingleResult();
			
		}catch(NoResultException e) {}
		
		return user;
	}

	}
@Override
public void connect() {
	
	
	em = Persistence.createEntityManagerFactory("vetclinic-provider").createEntityManager();

	em.getTransaction().begin();
	em.createNativeQuery("PRAGMA foreign_keys = ON").executeUpdate();
	em.getTransaction().commit();
	
	if(this.getRoles().isEmpty())
	{
		Role owner = new Role("owner");
		Role vet = new Role("vet");
		this.newRole(owner);
		this.newRole(vet);
	}
	
}

@Override
public List<Role> getRoles() {
	Query query = em.createNativeQuery("SELECT * FROM roles", Role.class);
	List<Role> roles = (List<Role>) query.getResultList();
	
	return roles;
}

@Override
public void newRole(Role role) {
	em.getTransaction().begin();
	em.persist(role);
	em.getTransaction().commit();
	
}

@Override
public void newUser(User user) {
	// TODO Auto-generated method stub
	em.getTransaction().begin();
	em.persist(user);
	em.getTransaction().commit();
}

@Override
public void disconnect() {
	em.close();
}

@Override
public Role getRole(Integer id) {
	Query query = em.createNativeQuery("SELECT * FROM roles where id="+id, Role.class);
	Role role = (Role) query.getSingleResult();
	
	return role;
}

@Override
public User getUser(String email) {
	Query query = em.createNativeQuery("SELECT * FROM users where email="+email, User.class);
	User user = (User) query.getSingleResult();
	
	return user;
}

@Override
public void changePassword(User u, String new_passwd) {

	
}



}


	
	
	
	
	
	
	
	
	
}
