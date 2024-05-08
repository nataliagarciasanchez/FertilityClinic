package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.LinkedList;


public class Doctor implements Serializable {
	
 private static final long serialVersionUID = 7882901115979698067L;
 
 private int id;
 private TypeDoctor type;
 private String email;
 private int phone;
 private String name;
 private LinkedList <Patient> patients;
 
 public Doctor(int id,TypeDoctor type,String email,int phone,String name,LinkedList <Patient> patients) {
     this.id=id;
     this.type=type;
     this.email=email;
     this.phone=phone;
     this.name=name;
     this.patients=patients;
 }
 
}
