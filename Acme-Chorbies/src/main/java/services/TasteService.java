
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TasteRepository;
import domain.Taste;

@Service
@Transactional
public class TasteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TasteRepository	tasteRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public TasteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Taste create() {
		Taste result;

		result = new Taste();

		return result;

	}

	public Collection<Taste> findAll() {
		Collection<Taste> result;
		result = this.tasteRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Taste findOne(final int tasteId) {
		Taste result;
		result = this.tasteRepository.findOne(tasteId);
		Assert.notNull(result);
		return result;
	}

	public Taste save(final Taste taste) {
		Assert.notNull(taste);
		return this.tasteRepository.save(taste);
	}

	public void delete(final Taste taste) {
		Assert.notNull(taste);
		Assert.isTrue(taste.getId() != 0);

		this.tasteRepository.delete(taste);
	}

	// Other business methods ----------------------------------------------
}
