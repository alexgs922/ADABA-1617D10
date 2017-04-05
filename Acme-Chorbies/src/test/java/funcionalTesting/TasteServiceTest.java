
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
import services.TasteService;
import utilities.AbstractTest;
import domain.Chorbi;
import domain.Taste;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TasteServiceTest extends AbstractTest {

	//The SUT ---------------------------------------------------------------------------------

	@Autowired
	private TasteService	tasteService;

	//Auxiliar Services  ---------------------------------------------------------------------------------

	@Autowired
	private ChorbiService	chorbiService;


	//CASO DE USO : LIKE ANOTHER CHORBI ---------------------------------------------------------------------------------

	//Prueba 1: comprobando que chorbi4 puede dar like a chorbi5 añadiendo un comentario (POSITIVO)
	//Se harán las siguientes comprobaciones:
	//1.Hay un taste más en la base de datos
	//2.Hay un taste más en la collection de likes dados para el principal
	//3.Que ese like de más es el mismo que acabamos de crear y guardar
	//4. Que la collection que tiene los chorbies que le gustan al principal tenga ahora al chorbi5
	//5. Que la collection que tiene los chorbies que le han dado like al chorbi 5 esté el principal

	@Test
	public void testCreateLikeWithComment() {

		this.authenticate("chorbi4");

		final Chorbi principal = this.chorbiService.findByPrincipal();

		final int beforeSave = principal.getGivenTastes().size();

		final Chorbi chorbiToLike = this.chorbiService.findOne(58);

		final Taste new_like = this.tasteService.create(chorbiToLike);
		new_like.setComment("comentario de prueba");
		final Taste reconstruct_like = this.tasteService.reconstruct(new_like, null, chorbiToLike);
		final Taste like = this.tasteService.save(reconstruct_like);

		this.tasteService.flush();

		final int afterSave = principal.getGivenTastes().size();

		Assert.isTrue(this.tasteService.findAll().size() == 5);
		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(afterSave == beforeSave + 1);
		Assert.isTrue(principal.getGivenTastes().contains(like));

		final Collection<Chorbi> chorbisWhoLikeToPrincipal = this.chorbiService.findAllChorbiesWhoLikedByThisUser(principal);
		final Collection<Chorbi> chorbisWhoGivesThemLikesToChorbiToLike = this.chorbiService.findAllChorbiesWhoLikeThem(chorbiToLike);

		Assert.isTrue(chorbisWhoLikeToPrincipal.contains(chorbiToLike));
		Assert.isTrue(chorbisWhoGivesThemLikesToChorbiToLike.contains(principal));

	}

	//Prueba 2: comprobando que chorbi4 puede dar like a chorbi5 sin añadir un comentario (POSITIVO)
	//Se harán las siguientes comprobaciones:
	//1.Hay un taste más en la base de datos
	//2.Hay un taste más en la collection de likes dados para el principal
	//3.Que ese like de más es el mismo que acabamos de crear y guardar
	//4. Que la collection que tiene los chorbies que le gustan al principal tenga ahora al chorbi5
	//5. Que la collection que tiene los chorbies que le han dado like al chorbi 5 esté el principal

	@Test
	public void testCreateLikeWithOutComment() {

		this.authenticate("chorbi4");

		final Chorbi principal = this.chorbiService.findByPrincipal();

		final int beforeSave = principal.getGivenTastes().size();

		final Chorbi chorbiToLike = this.chorbiService.findOne(58);

		final Taste new_like = this.tasteService.create(chorbiToLike);
		final Taste reconstruct_like = this.tasteService.reconstruct(new_like, null, chorbiToLike);
		final Taste like = this.tasteService.save(reconstruct_like);

		this.tasteService.flush();

		final int afterSave = principal.getGivenTastes().size();

		Assert.isTrue(this.tasteService.findAll().size() == 5);
		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(afterSave == beforeSave + 1);
		Assert.isTrue(principal.getGivenTastes().contains(like));

		final Collection<Chorbi> chorbisWhoLikeToPrincipal = this.chorbiService.findAllChorbiesWhoLikedByThisUser(principal);
		final Collection<Chorbi> chorbisWhoGivesThemLikesToChorbiToLike = this.chorbiService.findAllChorbiesWhoLikeThem(chorbiToLike);

		Assert.isTrue(chorbisWhoLikeToPrincipal.contains(chorbiToLike));
		Assert.isTrue(chorbisWhoGivesThemLikesToChorbiToLike.contains(principal));

	}

	//Prueba 3: comprobando que chorbi1 no puede dar like  dos veces a chorbi2 (NEGATIVO)

	@Test(expected = IllegalArgumentException.class)
	public void testCreateLikeTwice() {

		this.authenticate("chorbi1");

		final Chorbi chorbiToLike = this.chorbiService.findOne(55);

		final Taste new_like = this.tasteService.create(chorbiToLike);
		new_like.setComment("comentario de prueba");
		final Taste reconstruct_like = this.tasteService.reconstruct(new_like, null, chorbiToLike);
		this.tasteService.save(reconstruct_like);

		this.tasteService.flush();

	}

	//Prueba 4: comprobando que chorbi1 no puede darse like a sí mismo (NEGATIVO)

	@Test(expected = IllegalArgumentException.class)
	public void testCreateLikeToYourself() {

		this.authenticate("chorbi1");

		final Chorbi chorbiToLike = this.chorbiService.findOne(54);

		final Taste new_like = this.tasteService.create(chorbiToLike);
		new_like.setComment("comentario de prueba");
		final Taste reconstruct_like = this.tasteService.reconstruct(new_like, null, chorbiToLike);
		this.tasteService.save(reconstruct_like);

		this.tasteService.flush();

	}

	//Prueba 5: comprobando que chorbi1 no puede dar like a chorbi3 que está baneado (NEGATIVO)

	@Test(expected = IllegalArgumentException.class)
	public void testCreateLikeToBannedChorbi() {

		this.authenticate("chorbi1");

		final Chorbi chorbiToLike = this.chorbiService.findOne(56);

		final Taste new_like = this.tasteService.create(chorbiToLike);
		new_like.setComment("comentario de prueba");
		final Taste reconstruct_like = this.tasteService.reconstruct(new_like, null, chorbiToLike);
		this.tasteService.save(reconstruct_like);

		this.tasteService.flush();

	}

}
