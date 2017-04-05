
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
	//Seg�n nuestro populate.xml deber�a mostrar 5 chorbies sin bannear, 6 incluyendo los banneados y el chorbi3 es el banneado.
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
			//1. Se recojan de la base de datos el n�mero correcto total de chorbies que deber�a (5)
			//2. El usuario que accede est� logueado en el sistema
			//3. No se muestre en el listado ningun chorbi banneado ( sin chorbi3)
			//4. Que los chorbies que se deben mostrar sean los correctos (mediante el username)

			// Para el caso de uso probaremos que:
			//(Para admin)
			//1. Se recojan de la base de datos el n�mero correcto total de chorbies que deber�a (6)
			//2. El usuario que accede est� logueado en el sistema
			//3. Se muestren en el listado los chorbies banneados (incluido chorbi3)
			//4. Que los chorbies que se deben mostrar sean los correctos (mediante el username)
			//5. Que el chorbi baneado sea el correcto (chorbi3)

			//DETALLES DE CADA PRUEBA:
			//1. Se deben recoger datos correctos, sin ningun chorbi banneado y con un size de 5
			//2. Se deben recoger datos incorrectos, aparecen chorbies banneados y el size no es 5
			//3. No se recogen los datos y se produce una excepci�n porque no hay ning�n usuario logueado en el sistema
			//4. Se deben recoger datos correctos para el listado de admin (incluyendo los baneados), ya que el admin est� autenticado
			//5. No se recogen los datos correctos, pues no aparecen los chorbies banneados
			//6. No se deben recoger datos, ya que el chorbi 3 est� banneado y no se puede loguear.

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

	//CASO DE USO 2,3 y 4:   ---------------------------------------------------------------------------------
	//2 : BROWSE THE LIST OF CHORBIES WHO HAVE REGISTERED TO THE SYSTEM AND NAVIGATE TO THE CHORBIES WHO LIKE THEM
	//3 : MY LIKES, donde cada usuario autenticado como principal ve un listado de chorbies a los que ha dado su like 
	//4 : PEOPLE WHO I LIKE, donde cada usuario autenticado como principal ve un listado de chorbies que le han dado like
	//Se ha decidido probar los tres casos en conjunto, ya que la funcionalidad es practicamente la misma, pues hacen uso del mismo m�todo de servicio, unicamente difieren en el controlador.

	//Estos casos de uso a�aden funcionalidad al anterior, hace que en el listado de chorbies se a�adan dos enlaces:
	// El primero, donde podemos ver las personas que han dado like al chorbi de esa fila
	// El segundo, donde podemos ver las personas a las que el chorbi de esa fila ha dado like
	//Igual que en el caso anterior, no deben mostrarse los chorbies banneados

	//Option controlar� si el listado es del primer tipo o del segundo
	protected void templateListUseCase2(final String username, final int resultsSize, final int option, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {

			this.authenticate(username);

			Collection<Chorbi> res = null;
			final Chorbi principal = this.chorbiService.findByPrincipal();

			if (option == 1)
				res = this.chorbiService.findAllChorbiesWhoLikeThem(principal);
			else
				res = this.chorbiService.findAllChorbiesWhoLikedByThisUser(principal);

			Assert.isTrue(res.size() == resultsSize);

			for (final Chorbi c : res) {
				Assert.isTrue(c.isBan() == false);
				Assert.isTrue(c.getId() != principal.getId());

			}

			this.unauthenticate();
			this.chorbiService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverListUseCase2() {

		final Object testingData[][] = {

			// Para el caso de uso probaremos que:
			//1. Se recojan de la base de datos el n�mero correcto total de chorbies que deber�a para cada uno y en cada opci�n
			//2. El usuario que accede est� logueado en el sistema
			//3. No se muestre en el listado ningun chorbi banneado (sin chorbi3)
			//4. Que los chorbies que se deben mostrar sean los correctos (mediante el username)
			//5. Que no se puede acceder a los datos del chorbi3, por estar banneado

			//DETALLES DE CADA PRUEBA:
			//1 a 10 --> resultados positivos: comprobando que se recoja la cantidad de chorbies correcta para cada caso y que no est�n banneados.
			//11 y 12 --> resultados negativos : no se debe poder acceder a esta informaci�n, ya que el chorbi3 no se puede autenticar al estar banneado
			{
				"chorbi1", 1, 1, null
			}, {
				"chorbi1", 1, 2, null
			}, {
				"chorbi2", 1, 1, null
			}, {
				"chorbi2", 1, 2, null
			}, {
				"chorbi4", 0, 1, null
			}, {
				"chorbi4", 0, 2, null
			}, {
				"chorbi5", 0, 1, null
			}, {
				"chorbi5", 0, 2, null
			}, {
				"chorbi6", 0, 1, null
			}, {
				"chorbi6", 0, 2, null
			}, {
				"chorbi3", 1, 1, IllegalArgumentException.class
			}, {
				"chorbi3", 1, 2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListUseCase2((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);

	}

}