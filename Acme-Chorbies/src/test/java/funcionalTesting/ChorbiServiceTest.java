
package funcionalTesting;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ChorbiService;
import utilities.AbstractTest;
import domain.Chorbi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChorbiServiceTest extends AbstractTest {

	//The SUT ---------------------------------------------------------------------------------

	@Autowired
	private ChorbiService	chorbiService;


	//CASO DE USO 1: BROWSE THE LIST OF CHORBIES WHO HAVE REGISTERED TO THE SYSTEM  ---------------------------------------------------------------------------------
	//Este caso de uso debe generar, para todos los autenticados, una vista de un listado en el que aparezcan todos los chorbies del sistema, excepto los baneados, para los chorbies, y los banneados inclusive para el admin.
	//Según nuestro populate.xml debería mostrar 5 chorbies sin bannear, 6 incluyendo los banneados y el chorbi3 es el banneado.
	protected void templateListUseCase1(final String username, final int option, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			Collection<Chorbi> res = null;

			if (option == 1)
				res = this.chorbiService.findAllNotBannedChorbies();
			if (option == 2)
				res = this.chorbiService.findAll();

			if (username.equals("admin")) {

				Assert.isTrue(res.size() == 6);
				for (final Chorbi chorbi : res) {
					Assert.isTrue((chorbi.getUserAccount().getUsername().equals("chorbi1")) || (chorbi.getUserAccount().getUsername().equals("chorbi2")) || (chorbi.getUserAccount().getUsername().equals("chorbi3"))
						|| (chorbi.getUserAccount().getUsername().equals("chorbi4")) || (chorbi.getUserAccount().getUsername().equals("chorbi5")) || (chorbi.getUserAccount().getUsername().equals("chorbi6")));
					if (chorbi.getUserAccount().getUsername().equals("chorbi3"))
						Assert.isTrue(chorbi.isBan() == true);
					else
						Assert.isTrue(chorbi.isBan() == false);
				}

			} else {

				for (final Chorbi chorbi : res) {
					Assert.isTrue(chorbi.isBan() == false);
					Assert.isTrue((chorbi.getUserAccount().getUsername().equals("chorbi1")) || (chorbi.getUserAccount().getUsername().equals("chorbi2")) || (chorbi.getUserAccount().getUsername().equals("chorbi4"))
						|| (chorbi.getUserAccount().getUsername().equals("chorbi5")) || (chorbi.getUserAccount().getUsername().equals("chorbi6")));
				}

				Assert.isTrue(res.size() == 5);
			}

			this.unauthenticate();
			this.chorbiService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverListUseCase1() {

		final Object testingData[][] = {

			// Para el caso de uso probaremos que:
			//(Para chorbies)
			//1. Se recojan de la base de datos el número correcto total de chorbies que debería
			//2. El usuario que accede esté logueado en el sistema
			//3. No se muestre en el listado ningun chorbi banneado
			//4. Que los chorbies que se deben mostrar sean los correctos (mediante el username)

			// Para el caso de uso probaremos que:
			//(Para admin)
			//1. Se recojan de la base de datos el número correcto total de chorbies que debería
			//2. El usuario que accede esté logueado en el sistema
			//3. Se muestren en el listado los chorbies banneados
			//4. Que los chorbies que se deben mostrar sean los correctos (mediante el username)
			//5. Que el chorbi baneado sea el correcto

			//DETALLES DE CADA PRUEBA:
			//1. Se deben recoger datos correctos, sin ningun chorbi banneado y con un size de 5
			//2. Se deben recoger datos incorrectos, aparecen chorbies banneados y el size no es 5
			//3. No se recogen los datos y se produce una excepción porque no hay ningún usuario logueado en el sistema
			//4. Se deben recoger datos correctos para el listado de admin (incluyendo los baneados), ya que el admin está autenticado
			//5. No se recogen los datos correctos, pues no aparecen los chorbies banneados
			//6. No se deben recoger datos, ya que el chorbi 3 está banneado y no se puede loguear.

			{
				"chorbi1", 1, null
			}, {
				"chorbi2", 2, IllegalArgumentException.class
			}, {
				null, 1, IllegalArgumentException.class
			}, {
				"admin", 2, null
			}, {
				"admin", 1, IllegalArgumentException.class
			}, {
				"chorbi3", 1, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListUseCase1((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}
}
