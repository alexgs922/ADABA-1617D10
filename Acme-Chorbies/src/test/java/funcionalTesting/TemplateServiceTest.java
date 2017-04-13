
package funcionalTesting;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ChorbiService;
import services.TemplateService;
import utilities.AbstractTest;
import domain.Coordinate;
import domain.Template;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TemplateServiceTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private TemplateService	templateService;

	@Autowired
	private ChorbiService	chorbiService;


	protected void templateEditCoordinate(final String username, final String country,

	final String state, final String province, final String city, final Template template, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {

			this.authenticate(username);

			Coordinate coordinate;
			coordinate = template.getCoordinate();

			coordinate.setCity(city);
			coordinate.setCountry(country);
			coordinate.setProvince(province);
			coordinate.setState(state);

			template.setCoordinate(coordinate);
			this.templateService.save(template);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}
		this.templateService.flush();

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverEditCoordinate() {

		final Template template1 = this.chorbiService.findOneToSent(63).getTemplate(); // Template del chorbi con id = 63

		final Object testingData[][] = {

			//Editar una Coordinate correctamente

			{
				"chorbi1", "Francia", "Francia", "Paris", "Paris", template1, null
			},

			//Intentar editar una coordinate con el pais en blanco

			{
				"chorbi1", "", "Francia", "Paris", "Paris", template1, null
			},

			//Intentar editar una coordinate con la ciudad en blanco

			{
				"chorbi1", "Francia", "Francia", "Paris", "", template1, null
			}

		};

		for (int i = 0; i < testingData.length; i++)

			this.templateEditCoordinate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],

			(String) testingData[i][3], (String) testingData[i][4], (Template) testingData[i][5],

			(Class<?>) testingData[i][6]);

	}

}
