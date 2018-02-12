package crm.controller;

import crm.dto.EmployeeDTO;
import crm.model.Employee;
import crm.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeRepository er;

	@GetMapping("/{id}")
	public EmployeeDTO getEmployee(@PathVariable Long id) {
		validateEmployee(id);
		Employee e = er.findOne(id);
		EmployeeDTO dto = new EmployeeDTO();
		dto.setFirstName(e.getFirstName());
		dto.setLastName(e.getLastName());
		return dto;
	}

	@GetMapping("")
	public List<EmployeeDTO> getAllEmployees() {
		return StreamSupport.stream(er.findAll().spliterator(),false).map(employee -> {
			EmployeeDTO dto = new EmployeeDTO();
			dto.setFirstName(employee.getFirstName());
			dto.setLastName(employee.getLastName());
			return dto;
		}).collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<?> addEmployee(@RequestBody Employee e) {
		if(e != null) {
			Long id = er.save(e).getId();
			return ResponseEntity.created(ServletUriComponentsBuilder
					.fromCurrentRequest().path("/id")
					.buildAndExpand(id).toUri()).build();
		}
		return ResponseEntity.noContent().build();
	}

	private void validateEmployee(Long userId) {
		if (this.er.findOne(userId) == null) {throw new UserNotFoundException(userId);}
	}
}
