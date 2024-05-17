package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Doctor")
@XmlType(propOrder = {})
*/
public class Doctor implements Serializable {
	
 private static final long serialVersionUID = 7882901115979698067L;
 //@XmlTranscient
 private Integer id;
 //@XmlElement
 private String type;
 //@XmlElement
 private String email;
 //@XmlElement
 private Integer phone;
 //@XmlAttribute
 private String name;
<<<<<<< HEAD
 private byte[] licensePDF; //esto es binary objects que es un requisito. Es añadir eso y olvidarnos
 private LinkedList <Patient> patients;
=======
 //@XmlElement (name = "Patient")
 //@XmlElementWrapper(name = "Patients")
 private List <Patient> patients;
>>>>>>> branch 'master' of https://github.com/nataliagarciasanchez/FertilityClinic.git
 
 public Doctor(Integer id,String type,String email,Integer phone,String name,List <Patient> patients) {
     this.id=id;
     this.type=type;
     this.email=email;
     this.phone=phone;
     this.name=name;
     this.patients=patients;
 }
 

public Doctor() {
	super();
	patients = new LinkedList<Patient>();
}


@Override
public int hashCode() {
	return Objects.hash(email, id, name, patients, phone, type);
}



@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Doctor other = (Doctor) obj;
	return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
			&& Objects.equals(patients, other.patients) && Objects.equals(phone, other.phone) && type == other.type;
}


@Override
public String toString() {
	return "Doctor [id=" + id + ", type=" + type + ", email=" + email + ", phone=" + phone + ", name=" + name
			+ ", patients=" + patients + "]";
}


public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
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

public List<Patient> getPatients() {
	return patients;
}

public void setPatients(List<Patient> patients) {
	this.patients = patients;
}
 


 
 
}
