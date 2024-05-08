package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.Objects;

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
	
	

	@Override
	public int hashCode() {
		return Objects.hash(bankAc, email, id, name, phone);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		return Objects.equals(bankAc, other.bankAc) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone);
	}


	

	@Override
	public String toString() {
		return "Manager [id=" + id + ", email=" + email + ", phone=" + phone + ", name=" + name + ", bankAc=" + bankAc
				+ "]";
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBankAc() {
		return bankAc;
	}

	public void setBankAc(Integer bankAc) {
		this.bankAc = bankAc;
	}
	
	

}
