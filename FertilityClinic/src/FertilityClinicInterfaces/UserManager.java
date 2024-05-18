package FertilityClinicInterfaces;

import java.util.List;

import FertilityClinicPOJOs.Role;
import FertilityClinicPOJOs.User;


public interface UserManager {
	
	public void connect();
	public void disconnect();
	public void newRole(Role role);
	public void newUser(User user);
	public List<Role> getRoles();
	public Role getRole(Integer id);
	public User getUser(String email);
	public User checkPassword(String email, String pass);
	public void changePassword(User user, String new_passwd);


}
