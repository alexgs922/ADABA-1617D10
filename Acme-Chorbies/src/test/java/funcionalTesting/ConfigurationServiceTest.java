
package funcionalTesting;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ConfigurationService;
import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationService	configurationService;


	//CASO DE USO DESTINADO A PROBAR LA EDICI�N DE LA CONFIGURACI�N DE CACHEADO DE LOS RESULTADOS DE LAS BUSQUEDAS MEDIANTE EL TEMPLATE
	protected void templateEditConfiguration(final String username, final int hours, final int minutes, final int seconds, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Configuration c = this.configurationService.findConfiguration();

			final int hour_inicial = c.getHour();
			final int minutes_inicial = c.getMinute();
			final int seconds_inicial = c.getSecond();

			c.setHour(hours);
			c.setMinute(minutes);
			c.setSecond(seconds);

			this.configurationService.save(c);
			this.configurationService.flush();

			Assert.isTrue(hour_inicial != c.getHour());
			Assert.isTrue(minutes_inicial != c.getMinute());
			Assert.isTrue(seconds_inicial != c.getSecond());

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverEditBanner() {

		final Object testingData[][] = {
			{	//Edici�n correcta de la configuracion
				"admin", 14, 25, 41, null
			}, {//S�lo el administrador puede editar una configuration
				"chorbi1", 14, 25, 41, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditConfiguration((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);

	}

}
