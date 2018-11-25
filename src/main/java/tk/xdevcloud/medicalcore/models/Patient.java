package tk.xdevcloud.medicalcore.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.UUID;
import javax.persistence.GenerationType;
import org.hibernate.validator.constraints.NotEmpty;
import java.io.Serializable;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "patients")
public class Patient implements Serializable  {

	private static final long serialVersionUID = 2256673111627911565L;
	
	@Expose(serialize = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Expose
    @NotEmpty(message="First Name is required")
    @Column(name = "first_name")
    private String firstName;
	
	@Expose
    @NotEmpty(message="Last Name is required")
    @Column(name = "last_name")
    private String lastName;
	
	@Expose
    @NotEmpty(message="Id Number is required")
    @Column(name = "id_number")
    private String IdNumber;
	
    @Expose
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID uuid;
    

    public Patient(String firstName, String lastName, String IdNumber) {
    	
        this.firstName = firstName;
        this.lastName = lastName;
        this.IdNumber = IdNumber;
        this.uuid = UUID.randomUUID();
    }

    public Patient() {
    	
    	   this.uuid = UUID.randomUUID();
    	   
    }
    

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }
    
    public void setIdNumber(String IdNumber) {

        this.IdNumber = IdNumber;
    }
   
    public String getFirstName() {

        return this.firstName;
    }

    public String getLastName() {

        return this.lastName;
    }

    public String getIdNumber() {

        return this.IdNumber;
    }
    
    public Integer getId() {
    	
    	return this.id;
    }
    
    public UUID getuuid() {
    	
    	return this.uuid;
    }
    
    @Override
   	public int hashCode() {
   		final int prime = 31;
   		int result = 1;
   		result = prime * result + ((IdNumber == null) ? 0 : IdNumber.hashCode());
   		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
   		result = prime * result + ((id == null) ? 0 : id.hashCode());
   		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
   		return result;
   	}


   	@Override
   	public boolean equals(Object obj) {
   		if (this == obj)
   			return true;
   		if (obj == null)
   			return false;
   		if (getClass() != obj.getClass())
   			return false;
   		Patient other = (Patient) obj;
   		if (IdNumber == null) {
   			if (other.IdNumber != null)
   				return false;
   		} else if (!IdNumber.equals(other.IdNumber))
   			return false;
   		if (firstName == null) {
   			if (other.firstName != null)
   				return false;
   		} else if (!firstName.equals(other.firstName))
   			return false;
   		if (id == null) {
   			if (other.id != null)
   				return false;
   		} else if (!id.equals(other.id))
   			return false;
   		if (lastName == null) {
   			if (other.lastName != null)
   				return false;
   		} else if (!lastName.equals(other.lastName))
   			return false;
 
   		return true;
   	}
  

}
