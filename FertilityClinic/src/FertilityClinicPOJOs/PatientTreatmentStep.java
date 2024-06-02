package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.Objects;

public class PatientTreatmentStep implements Serializable {

    private static final long serialVersionUID = -4268307077984335270L;
	private int patientId;
    private int treatmentStepId;
    private boolean isCompleted;

    public PatientTreatmentStep(int patientId, int treatmentStepId, boolean isCompleted) {
        this.patientId = patientId;
        this.treatmentStepId = treatmentStepId;
        this.isCompleted = isCompleted;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getTreatmentStepId() {
        return treatmentStepId;
    }

    public void setTreatmentStepId(int treatmentStepId) {
        this.treatmentStepId = treatmentStepId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

   

    @Override
	public int hashCode() {
		return Objects.hash(isCompleted, patientId, treatmentStepId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientTreatmentStep other = (PatientTreatmentStep) obj;
		return isCompleted == other.isCompleted && patientId == other.patientId
				&& treatmentStepId == other.treatmentStepId;
	}

	@Override
    public String toString() {
        return "PatientTreatmentStep{" +
               "patientId=" + patientId +
               ", treatmentStepId=" + treatmentStepId +
               ", isCompleted=" + isCompleted +
               '}';
    }
}

