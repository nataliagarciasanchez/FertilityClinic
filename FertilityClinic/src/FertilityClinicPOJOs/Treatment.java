package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Treatment implements Serializable {

    private static final long serialVersionUID = 1408185163645741646L;
    private Integer treatmentID;
    private String name;
    private String description;
    private Integer durationInDays;
    private List<Patient> patients;
    private List<TreatmentStep> steps; // Lista de pasos en el tratamiento

    public Treatment(Integer treatmentID, String name, String description, Integer durationInDays,
                     List<Patient> patients, List<TreatmentStep> steps) {
        this.treatmentID = treatmentID;
        this.name = name;
        this.description = description;
        this.durationInDays = durationInDays;
        this.patients = patients;
        this.steps = steps != null ? steps : new ArrayList<>();
    }

    public Treatment(Integer treatmentID, String name, String description, Integer durationInDays) {
        this(treatmentID, name, description, durationInDays, new ArrayList<>(), new ArrayList<>());
    }

    public Integer getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(Integer treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getNameTreatment() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<TreatmentStep> getSteps() {
        return steps;
    }

    public void setSteps(List<TreatmentStep> steps) {
        this.steps = steps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, durationInDays, name, patients, treatmentID, steps);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Treatment other = (Treatment) obj;
        return Objects.equals(description, other.description) && Objects.equals(durationInDays, other.durationInDays)
                && Objects.equals(name, other.name) && Objects.equals(patients, other.patients)
                && Objects.equals(treatmentID, other.treatmentID) && Objects.equals(steps, other.steps);
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentID=" + treatmentID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", durationInDays=" + durationInDays +
                ", patients=" + patients +
                ", steps=" + steps +
                '}';
    }
}
