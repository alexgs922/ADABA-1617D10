
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CreditCardRepository	creditCardRepository;


	// Constructors -----------------------------------------------------------

	public CreditCardService() {
		super();
	}

	//Simple CRUD -------------------------------------------------------------

	public CreditCard create() {
		CreditCard res;
		res = new CreditCard();
		return res;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> res;
		res = this.creditCardRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard res;
		res = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(res);
		return res;
	}

	public CreditCard save(final CreditCard c) {
		Assert.notNull(c);
		return this.creditCardRepository.save(c);

	}

	public void delete(final CreditCard c) {
		Assert.notNull(c);
		this.creditCardRepository.delete(c);
	}

	//Other business methods --------------------------------------
}
