package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.Objects;

public class TreatmentStep implements Serializable {

    private static final long serialVersionUID = -4731752480931299994L;
	private int id;
    private int treatmentID;
    private String stepDescription;
    private int stepOrder;

    public TreatmentStep(int id, int treatmentID, String stepDescription, int stepOrder) {
        this.id = id;
        this.treatmentID = treatmentID;
        this.stepDescription = stepDescription;
        this.stepOrder = stepOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

   

    @Override
	public int hashCode() {
		return Objects.hash(id, stepDescription, stepOrder, treatmentID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreatmentStep other = (TreatmentStep) obj;
		return id == other.id && Objects.equals(stepDescription, other.stepDescription) && stepOrder == other.stepOrder
				&& treatmentID == other.treatmentID;
	}

	@Override
    public String toString() {
        return "TreatmentStep{" +
               "id=" + id +
               ", treatmentID=" + treatmentID +
               ", stepDescription='" + stepDescription + '\'' +
               ", stepOrder=" + stepOrder +
               '}';
    }
}

