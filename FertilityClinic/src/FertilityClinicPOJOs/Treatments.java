package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Treatments implements Serializable {

	
	
	private static final long serialVersionUID = 1408185163645741646L;
	private Integer treatmentID;
	private String name;
	private String description;
	private Integer durationInDays;
	private List<Patient> patients;
	
	
	public Treatments(Integer treatmentID, String name, String description, Integer durationInDays,
			List<Patient> patients) {
		super();
		this.treatmentID = treatmentID;
		this.name = name;
		this.description = description;
		this.durationInDays = durationInDays;
		this.patients = patients;
	}
	public Treatments(Integer treatmentID, String name, String description, Integer durationInDays) {
		super();
		this.treatmentID = treatmentID;
		this.name = name;
		this.description = description;
		this.durationInDays = durationInDays;
		
	}


	public Integer getTreatmentID() {
		return treatmentID;
	}


	public void setTreatmentID(Integer treatmentID) {
		this.treatmentID = treatmentID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getDurationInDays() {
		return durationInDays;
	}


	public void setDurationInDays(Integer durationInDays) {
		this.durationInDays = durationInDays;
	}


	public List<Patient> getPatients() {
		return patients;
	}


	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}


	@Override
	public int hashCode() {
		return Objects.hash(description, durationInDays, name, patients, treatmentID);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Treatments other = (Treatments) obj;
		return Objects.equals(description, other.description) && Objects.equals(durationInDays, other.durationInDays)
				&& Objects.equals(name, other.name) && Objects.equals(patients, other.patients)
				&& Objects.equals(treatmentID, other.treatmentID);
	}


	@Override
	public String toString() {
		return "Treatments [treatmentID=" + treatmentID + ", name=" + name + ", description=" + description
				+ ", durationInDays=" + durationInDays + ", patients=" + patients + "]";
	}
	
	
	
	
	

	
	
	

	

	
	

	
	
     
}
