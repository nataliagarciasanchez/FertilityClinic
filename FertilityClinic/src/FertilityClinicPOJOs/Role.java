package FertilityClinicPOJOs;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity // Añadir la anotación @Entity para marcar esta clase como una entidad persistente
public class Role implements Serializable {

   
	private static final long serialVersionUID = -1008273412421410404L;

	@Id // Marcar el campo id como la clave primaria de la entidad
    @GeneratedValue(generator = "ROLE_ID_GEN")
    @TableGenerator(name = "ROLE_ID_GEN", table = "SEQUENCE_GENERATOR", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "ROLE_ID", initialValue = 1, allocationSize = 1)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY) // Relación uno a muchos con la clase User
    private List<User> users;

    public Role() {
        super();
    }

    public Role(String name) {
        super();
        this.name = name;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(users, other.users);
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + "]";
    }
}
