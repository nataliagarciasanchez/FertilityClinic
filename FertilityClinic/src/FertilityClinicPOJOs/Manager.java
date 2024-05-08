package FertilityClinicPOJOs;

import java.io.Serializable;

public class Manager implements Serializable {
	
	private static final long serialVersionUID = -3660639125794454545L;
	private Integer id;
	private String email;
	private Integer phone;
	private String name;
    private Integer bankAc;
	
	public Manager(Integer id, String email, Integer phone, String name, Integer bankAc) {
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.bankAc = bankAc;
	}

}
