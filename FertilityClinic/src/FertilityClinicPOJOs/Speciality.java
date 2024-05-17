package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Speciality implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
    private List<Doctor> doctors;
    
    public Speciality (Integer id, String name, List<Doctor> doctors) {
		this.id = id;
		this.name = name;
		this.doctors = new ArrayList<Doctor>();
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

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Speciality [id=" + id + ", name=" + name + ", doctors=" + doctors + "]";
	}
}