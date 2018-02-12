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
@Table(name = "CUSTOMERS")
public class Customer {
	@Id
	@GeneratedValue
	@JsonProperty
	@JsonIgnore
	private Long id;
	@Size(max = 255)
	private String customerName;
	private String contactName;
	private String address;
	private String city;
	private String postalCode;
	private String country;

}
