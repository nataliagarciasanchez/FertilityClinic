package FertilityClinicPOJOs;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient implements Serializable{
	
	private static final long serialVersionUID = 7256683528485457199L;
	private int id;
	private LocalDate dob;
	private String email;
	private int phoneN;
	private String name;
	private double height;
	private double weight;
	private BloodType bloodType;
	private int banckAc;
	private Gender gender;
	
	public Patient(int id, LocalDate dob, String email, int phoneN, String name, double height, double weight, BloodType bloodType, int banckAc, Gender gender) {
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
	
}
