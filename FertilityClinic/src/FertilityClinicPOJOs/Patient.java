package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
		private Integer phone;
		//@XmlElement
		private double height;
		//@XmlElement
		private double weight;
		//@XmlElement
		private String bloodType;
		//@XmlElement
		private String gender;
		//@XmlTranscient
		private List<Doctor> doctors;
		//@XmlElement
		
		private Treatments treatmet;
	
	
	

	public Patient(Integer id, String name, Date dob, String email, Integer phone, double height, double weight,
				String bloodType, String gender, Treatments treatmet) {
			super();
			this.id = id;
			this.name = name;
			this.dob = dob;
			this.email = email;
			this.phone = phone;
			this.height = height;
			this.weight = weight;
			this.bloodType = bloodType;
			this.gender = gender;
			this.treatmet = treatmet;
		}
	

	public Patient(Integer id, String name, Date dob, String email, Integer phone, double height, double weight,
			String bloodType, String gender, List<Doctor> doctors, Treatments treatmet) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.email = email;
		this.phone = phone;
		this.height = height;
		this.weight = weight;
		this.bloodType = bloodType;
		this.gender = gender;
		this.doctors = doctors;
		this.treatmet = treatmet;
	}
	
		
	public Patient() {
		super();
		doctors = new ArrayList<Doctor>();
	}
	


	@Override
	public int hashCode() {
		return Objects.hash( bloodType, dob, doctors, email, gender, height, id, name, phone, treatmet, weight);
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
		return Objects.equals(bloodType, other.bloodType)
				&& Objects.equals(dob, other.dob) && Objects.equals(doctors, other.doctors)
				&& Objects.equals(email, other.email) && Objects.equals(gender, other.gender)
				&& Double.doubleToLongBits(height) == Double.doubleToLongBits(other.height)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone) && Objects.equals(treatmet, other.treatmet)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}
	

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", dob=" + dob + ", email=" + email + ", phone=" + phone
				+ ", height=" + height + ", weight=" + weight + ", bloodType=" + bloodType + ", gender=" + gender
				+ ", doctor=" + doctors + ", treatmet=" + treatmet + "]";
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

	public Integer getPhone() {
		return phone;
	}

	public void setPhoneN(Integer phone) {
		this.phone = phone;
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

	public Treatments getTreatmet() {
		return treatmet;
	}

	public void setTreatmet(Treatments treatmet) {
		this.treatmet = treatmet;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	

	
	
}
