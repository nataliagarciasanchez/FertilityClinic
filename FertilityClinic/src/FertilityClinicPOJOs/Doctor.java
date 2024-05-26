package FertilityClinicPOJOs;

import java.io.Serializable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Doctor")
@XmlType(propOrder = {}) //Pepe: no se como poner el orden
public class Doctor implements Serializable {
	
 private static final long serialVersionUID = 7882901115979698067L;
 @XmlTransient
 private Integer id;
 @XmlElement
 private String email;
 @XmlElement
 private Integer phone;
 @XmlAttribute
 private String name;
 @XmlElement
 private Speciality speciality;
 @XmlTransient
 private byte[] licensePDF; //esto es binary objects que es un requisito. Es añadir eso y olvidarnos
 @XmlElement (name = "Patient")
 @XmlElementWrapper(name = "Patients")
 private List <Patient> patients;
 
 
 
 public Doctor(Integer id, String email, Integer phone, String name, Speciality speciality, byte[] licensePDF) {
	this.id = id;
	this.email = email;
	this.phone = phone;
	this.name = name;
	this.speciality = speciality;
	this.licensePDF = licensePDF;
}
 
 
 
 public Doctor(Integer id, String email, Integer phone, String name, Speciality speciality) {
	super();
	this.id = id;
	this.email = email;
	this.phone = phone;
	this.name = name;
	this.speciality = speciality;
}
 



public Doctor() {
		super();
		patients = new LinkedList<Patient>();
	}
	 

 @Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Arrays.hashCode(licensePDF);
	result = prime * result + Objects.hash(email, id, name, patients, phone, speciality);
	return result;
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
	return Objects.equals(email, other.email) && Objects.equals(id, other.id)
			&& Arrays.equals(licensePDF, other.licensePDF) && Objects.equals(name, other.name)
		    && Objects.equals(patients, other.patients)
			&& Objects.equals(phone, other.phone) && Objects.equals(speciality, other.speciality);
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

public Speciality getSpeciality() {
	return speciality;
}

public void setSpeciality(Speciality speciality) {
	this.speciality = speciality;
}

public byte[] getLicensePDF() {
	return licensePDF;
}

public void setLicensePDF(byte[] licensePDF) {
	this.licensePDF = licensePDF;
}

public List<Patient> getPatients() {
	return patients;
}

public void setPatients(List<Patient> patients) {
	this.patients = patients;
}


@Override
public String toString() {
	return "Doctor [id=" + id + ", email=" + email + ", phone=" + phone + ", name=" + name + ", speciality="
			+ speciality + ", licensePDF=" + Arrays.toString(licensePDF) + ", patients="
			+ patients + "]";
}


 
}
