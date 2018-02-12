package crm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "CATEGORIES")
public class Category {
	@GeneratedValue
	@Id
	private Long id;
	private String categoryName;
	@Size(max = 1000)
	private String description;
}
