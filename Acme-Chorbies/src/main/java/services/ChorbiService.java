
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import domain.Actor;
import domain.Chorbi;

@Service
@Transactional
public class ChorbiService {

	// ---------- Repositories----------------------

	@Autowired
	private ChorbiRepository	chorbiRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods ----------------------------------------------------

	public Chorbi create() {
		Chorbi result;

		result = new Chorbi();

		return result;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> res;
		res = this.chorbiRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Chorbi findOne(final int chorbiId) {
		Chorbi res;
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		res = this.chorbiRepository.findOne(chorbiId);
		Assert.notNull(res);

		return res;
	}

	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi);

		return this.chorbiRepository.save(chorbi);
	}

	public void delete(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		Assert.isTrue(chorbi.getId() != 0);

		this.chorbiRepository.delete(chorbi);
	}

	// Other business methods ----------------------------------------------------

	public Collection<Chorbi> findAllNotBannedChorbies() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Chorbi> chorbiesToShow;

		chorbiesToShow = this.chorbiRepository.findAllNotBannedChorbies();

		Assert.notNull(chorbiesToShow);

		return chorbiesToShow;
	}

	public Collection<Chorbi> findAllChorbiesWhoLikeThem(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Chorbi> chorbies;

		chorbies = this.chorbiRepository.findAllChorbiesWhoLikeThem(chorbi.getId());

		Assert.notNull(chorbies);

		return chorbies;
	}

	public Collection<Chorbi> findAllChorbiesWhoLikedByThisUser(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Chorbi> chorbies;

		chorbies = this.chorbiRepository.findAllChorbiesWhoLikedByThisUser(chorbi.getId());

		Assert.notNull(chorbies);

		return chorbies;

	}

}
