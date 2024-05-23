package FertilityClinicInterfaces;
import java.util.*;
import FertilityClinicPOJOs.Doctor;
import FertilityClinicPOJOs.Manager;
public interface ManagerManager {

	public void addManager(Manager newManager);
<<<<<<< HEAD

=======
	public Manager viewMyInfo(Integer id);
	public void modifyManagerInfo(Integer managerId, String email, Integer phoneN, String name);
	public Manager getManagerByEmail(String email);
>>>>>>> branch 'master' of https://github.com/nataliagarciasanchez/FertilityClinic.git
	
	
	
	
	

}
