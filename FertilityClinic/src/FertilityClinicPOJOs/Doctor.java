package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.LinkedList;


public class Doctor implements Serializable {
	
 private static final long serialVersionUID = 7882901115979698067L;
 
 private Integer id;
 private TypeDoctor type;
 private String email;
 private Integer phone;
 private String name;
 private LinkedList <Patient> patients;
 
 public Doctor(Integer id,TypeDoctor type,String email,Integer phone,String name,LinkedList <Patient> patients) {
     this.id=id;
     this.type=type;
     this.email=email;
     this.phone=phone;
     this.name=name;
     this.patients=patients;
 }
 
}
