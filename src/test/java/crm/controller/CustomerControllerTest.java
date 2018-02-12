package crm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import crm.model.Customer;
import crm.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private CustomerController controller;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CustomerRepository repo;

	private Customer single;

	private List<Customer> customers;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	final ObjectMapper mapper = new ObjectMapper();

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);

		assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() {
		single = new Customer();
		Customer second = new Customer();

		single.setId(new Long(1));
		second.setId(new Long(2));
		single.setCustomerName("Alfreds Futterkiste");
		second.setCustomerName("Ana Trujillo Emparedados y helados");
		single.setContactName("Maria Anders");
		second.setContactName("Ana Trujillo");
		single.setAddress("Obere Str. 57");
		second.setAddress("Avda. de la Constitución 2222");
		single.setCity("Berlin");
		second.setCity("México D.F.");
		single.setPostalCode("12209");
		second.setPostalCode("05021");
		single.setCountry("Germany");
		second.setCountry("Mexico");


		customers = new ArrayList<>(2);
		customers.add(single);
		customers.add(second);
	}

	@Test
	public void getCustomer() throws Exception {
		when(repo.findOne(single.getId())).thenReturn(single);
		MvcResult result = this.mvc.perform(get("/customer/"+single.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();

		Customer resp = mapper.convertValue(
				JsonPath.parse(result.getResponse().getContentAsString()).
						read("$"), Customer.class);
		assertThat(resp).isEqualTo(single);
	}

	@Test
	public void getAllCustomers() throws Exception {
		when(repo.findAll()).thenReturn(customers);
		MvcResult result = this.mvc.perform(get("/customer"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();

		Customer[] resp = mapper.convertValue(
				JsonPath.parse(result.getResponse().getContentAsString())
				.read("$"),Customer[].class);
		assertThat(Arrays.asList(resp)).isEqualTo(customers);

	}

	@Test
	public void addCustomer() throws Exception {
		when(repo.save(single)).thenReturn(single);
		String request = json(single);
		this.mvc.perform(post("/customer")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(request))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
