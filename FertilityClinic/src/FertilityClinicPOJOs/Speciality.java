package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Speciality implements Serializable{
	
	private static final long serialVersionUID = 883349038959300781L;
	private Integer id;
	private String name;
   

    
    
    
	public Speciality(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	@Override
	public int hashCode() {
		return Objects.hash (id, name);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speciality other = (Speciality) obj;
		return  Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	@Override
	public String toString() {
		return "Speciality [id=" + id + ", name=" + name + "]";
	}



	
}