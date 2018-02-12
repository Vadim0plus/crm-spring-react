package crm.repository;

import crm.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository repository;

	@Test
	public void testExample() {
		Category category = repository.findOne(Long.valueOf(1));
		assertThat(category.getId()).isEqualTo(1);
	}

}
