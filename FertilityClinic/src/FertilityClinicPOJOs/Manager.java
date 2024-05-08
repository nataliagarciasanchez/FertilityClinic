package FertilityClinicPOJOs;

import java.io.Serializable;

public class Manager implements Serializable {
	
	private static final long serialVersionUID = -3660639125794454545L;
	private int id;
	private String email;
	private int phone;
	private String name;
    private int bankAc;
	
	public Manager(int id, String email, int phone, String name, int bankAc) {
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.bankAc = bankAc;
	}

}
