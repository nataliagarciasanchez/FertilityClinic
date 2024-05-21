package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Speciality implements Serializable {
    
	private static final long serialVersionUID = 8613393528722283172L;
	private Integer id;
    private String name;

    public Speciality(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Speciality other = (Speciality) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
