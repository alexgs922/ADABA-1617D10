
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;

@Service
@Transactional
public class ChirpService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChirpRepository	chirpRepository;


	// Constructors -----------------------------------------------------------

	public ChirpService() {
		super();
	}

	//Simple CRUD -------------------------------------------------------------

	public Chirp create() {
		Chirp res;
		res = new Chirp();
		return res;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> res;
		res = this.chirpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Chirp findOne(final int chirpId) {
		Chirp res;
		res = this.chirpRepository.findOne(chirpId);
		Assert.notNull(res);
		return res;
	}

	public Chirp save(final Chirp c) {
		Assert.notNull(c);
		return this.chirpRepository.save(c);

	}

	public void delete(final Chirp c) {
		Assert.notNull(c);
		this.chirpRepository.delete(c);
	}

	//Other business methods --------------------------------------
}
