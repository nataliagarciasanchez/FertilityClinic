package FertilityClinicPOJOs;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Appointment implements Serializable {
	
    private static final long serialVersionUID = -8317861314036604409L;
    
	private int id;
    private int patientId;
    private String description;
    private LocalTime time;
    private LocalDate date;
    private int doctorId;

    public Appointment() {
    }

    public Appointment(int id, int patientId, String description, LocalTime time, LocalDate date, int doctorId) {
        this.id = id;
        this.patientId = patientId;
        this.description = description;
        this.time = time;
        this.date = date;
        this.doctorId = doctorId;
    }
    
    
    @Override
	public int hashCode() {
		return Objects.hash(date, description, doctorId, id, patientId, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(date, other.date) && Objects.equals(description, other.description)
				&& doctorId == other.doctorId && id == other.id && patientId == other.patientId
				&& Objects.equals(time, other.time);
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", date=" + date +
                ", doctorId=" + doctorId +
                '}';
    }

}
