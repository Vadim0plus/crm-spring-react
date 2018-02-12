package crm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "EMPLOYEES")
public class Employee {
	@GeneratedValue
	@Id
	@JsonIgnore
	@JsonProperty
	private Long id;
	private String LastName;
	private String FirstName;
	private String BirthDate;
	private String Photo;
	@Size(max = 1000)
	private String Notes;
}
