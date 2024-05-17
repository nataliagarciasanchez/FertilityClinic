package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Patient")
@XmlType(propOrder = {"los atributos Xml que sean elements"})
*/
public class Patient implements Serializable{
	
	private static final long serialVersionUID = 7256683528485457199L;
		//@XmlTransient
		private Integer id;
		//@XmlAttribute
		private String name;
		//@XmlJavaTypeAdapter(SQLDateAdapter.class)
		private Date dob;
		//@XmlElement
		private String email;
		//@XmlElement
		private Integer phoneN;
		//@XmlElement
		private double height;
		//@XmlElement
		private double weight;
		//@XmlElement
		private String bloodType;
		//@XmlElement
		private String gender;
		//@XmlTranscient
		private ArrayList<Doctor> doctors;
		//@XmlElement
		private Integer age;
	
	public Patient(Integer id, Date dob, String email, Integer phoneN, String name, double height, double weight, String bloodType, String gender, Integer age) {
		this.id = id;
		this.dob = dob;
		this.email = email;
		this.phoneN = phoneN;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.bloodType = bloodType;
		this.gender = gender;
		this.age=age;
	}
	

	public Patient() {
		super();
		doctors = new ArrayList<Doctor>();
	}


	@Override
	public int hashCode() {
		return Objects.hash( bloodType, dob, doctors, email, gender, height, id, name, phoneN, weight, age);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return  bloodType == other.bloodType && Objects.equals(dob, other.dob)
				&& Objects.equals(doctors, other.doctors) && Objects.equals(email, other.email)
				&& gender == other.gender && Double.doubleToLongBits(height) == Double.doubleToLongBits(other.height)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(phoneN, other.phoneN)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight)
				&& Objects.equals(other, other.age);
	}



	@Override
	public String toString() {
		return "Patient [id=" + id + ", dob=" + dob + ", email=" + email + ", phoneN=" + phoneN + ", name=" + name
				+ ", height=" + height + ", weight=" + weight + ", bloodType=" + bloodType
				+ ", gender=" + gender + ", age]"+ age;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhoneN() {
		return phoneN;
	}

	public void setPhoneN(Integer phoneN) {
		this.phoneN = phoneN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public List<Doctor> getDoctors() {
		return doctors;
	}


	public void setDoctors(LinkedList<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	
	}
}
