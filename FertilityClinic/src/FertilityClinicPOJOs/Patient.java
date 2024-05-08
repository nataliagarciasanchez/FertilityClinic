package FertilityClinicPOJOs;

import java.io.Serializable;
import java.time.LocalDate;

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
	
	public Patient(Integer id, LocalDate dob, String email, Integer phoneN, String name, double height, double weight, BloodType bloodType, Integer banckAc, Gender gender) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
