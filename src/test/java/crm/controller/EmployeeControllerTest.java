package crm.controller;

import crm.model.Employee;
import crm.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	EmployeeController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeRepository rep;

	private List<Employee> employees;

	private Employee single;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	@Before
	public void setup() {
		single = new Employee();
		single.setId(new Long(2));
		single.setLastName("Dave");
		single.setFirstName("John");
		single.setNotes("Hello, World");
		single.setPhoto("Photo");
		single.setBirthDate("1996-10-10");

	}

	@Test
	public void getEmployee() throws Exception {
		when(rep.findOne(single.getId())).thenReturn(single);
		this.mockMvc.perform(get("/employee/2"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
		//		.andExpect(jsonPath("$.id", is(single.getId())))
				.andExpect(jsonPath("$.lastName",is(single.getLastName())))
				.andExpect(jsonPath("$.firstName",is(single.getFirstName())));
	}

}
