package FertilityClinicPOJOs;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Objects;

public class Patient implements Serializable{
	
	private static final long serialVersionUID = 7256683528485457199L;
	private Integer id;
	private LocalDate dob;
	private String email;
	private Integer phoneN;
	private String name;
	private double height;
	private double weight;
	private BloodType bloodType;
	private Integer banckAc;
	private Gender gender;
	private LinkedList<Doctor> doctors;
	private Integer age;
	
	public Patient(Integer id, LocalDate dob, String email, Integer phoneN, String name, double height, double weight, BloodType bloodType, Integer banckAc, Gender gender, Integer age) {
		this.id = id;
		this.dob = dob;
		this.email = email;
		this.phoneN = phoneN;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.bloodType = bloodType;
		this.banckAc = banckAc;
		this.gender = gender;
		this.age=age;
	}
	

	public Patient() {
		super();
		doctors = new LinkedList<Doctor>();
	}


	@Override
	public int hashCode() {
		return Objects.hash(banckAc, bloodType, dob, doctors, email, gender, height, id, name, phoneN, weight, age);
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
		return Objects.equals(banckAc, other.banckAc) && bloodType == other.bloodType && Objects.equals(dob, other.dob)
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
				+ ", height=" + height + ", weight=" + weight + ", bloodType=" + bloodType + ", banckAc=" + banckAc
				+ ", gender=" + gender + ", age]"+ age;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
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

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public Integer getBanckAc() {
		return banckAc;
	}

	public void setBanckAc(Integer banckAc) {
		this.banckAc = banckAc;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public LinkedList<Doctor> getDoctors() {
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
