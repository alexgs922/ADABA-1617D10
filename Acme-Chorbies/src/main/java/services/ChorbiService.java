
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Chorbi;
import domain.Coordinate;
import domain.Genre;
import domain.Relationship;
import domain.Taste;
import domain.Template;
import form.RegistrationForm;

@Service
@Transactional
public class ChorbiService {

	// ---------- Repositories----------------------

	@Autowired
	private ChorbiRepository	chorbiRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Md5PasswordEncoder	encoder;

	@Autowired
	private Validator			validator;

	@Autowired
	private TemplateService		templateService;


	// Simple CRUD methods ----------------------------------------------------

	public Chorbi create() {
		Chorbi result;
		result = new Chorbi();
		return result;
	}

	public Chorbi reconstruct(final RegistrationForm customerForm) {
		Chorbi result;
		UserAccount userAccount;
		Authority authority;
		Collection<Authority> authorities;
		String pwdHash;
		Coordinate coordinate;

		result = this.create();
		authorities = new HashSet<Authority>();
		userAccount = new UserAccount();
		coordinate = new Coordinate();

		result.setName(customerForm.getName());
		result.setSurName(customerForm.getSurName());
		result.setPhone(customerForm.getPhone());
		result.setEmail(customerForm.getEmail());
		result.setDescription(customerForm.getDescription());
		result.setPicture(customerForm.getPicture());

		if (customerForm.getGenre().equals("MALE"))
			result.setGenre(Genre.MALE);
		else
			result.setGenre(Genre.FEMALE);

		if (customerForm.getRelation().equals("ACTIVITIES"))
			result.setRelationship(Relationship.ACTIVITIES);

		else if (customerForm.getRelation().equals("FRIENDSHIP"))
			result.setRelationship(Relationship.FRIENDSHIP);

		else if (customerForm.getRelation().equals("LOVE"))
			result.setRelationship(Relationship.LOVE);

		//Validamos la fecha de nacimiento de la persona para saber si tiene 18 años.
		Assert.isTrue(ChorbiService.calcularEdad(customerForm.getBirthDate()) >= 18);
		result.setBirthDate(customerForm.getBirthDate());

		coordinate.setCity(customerForm.getCity());
		coordinate.setCountry(customerForm.getCountry());
		coordinate.setProvince(customerForm.getProvince());
		coordinate.setState(customerForm.getState());
		result.setCoordinate(coordinate);

		authority = new Authority();
		authority.setAuthority(Authority.CHORBI);
		authorities.add(authority);
		pwdHash = this.encoder.encodePassword(customerForm.getPassword(), null);
		userAccount.setAuthorities(authorities);
		userAccount.setPassword(pwdHash);
		userAccount.setUsername(customerForm.getUsername());
		result.setUserAccount(userAccount);

		return result;
	}

	public Chorbi reconstruct(final Chorbi chorbi, final BindingResult binding) {
		Chorbi result;
		if (chorbi.getId() == 0)
			result = chorbi;
		else {
			result = this.chorbiRepository.findOne(chorbi.getId());
			result = new Chorbi();
			result.setName(chorbi.getName());
			result.setSurName(chorbi.getSurName());
			result.setEmail(chorbi.getEmail());
			result.setPhone(chorbi.getPhone());
			this.validator.validate(result, binding);
		}
		return result;
	}

	public Chorbi findOne(final int chorbiId) {
		Chorbi res;
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		res = this.chorbiRepository.findOne(chorbiId);
		Assert.notNull(res);

		return res;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> res;
		res = this.chorbiRepository.findAll();
		return res;
	}

	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		return this.chorbiRepository.save(chorbi);

	}

	public Chorbi saveAndFlush(Chorbi chorbi, Template t) {
		Assert.notNull(chorbi);
		Assert.notNull(t);

		if (chorbi.getId() == 0) {

			t = this.templateService.saveAndFlush(t);
			chorbi.setTemplate(t);
			this.save(chorbi);
		} else
			chorbi = this.save(chorbi);
		return chorbi;

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

	public Collection<Chorbi> findAllChorbies() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Chorbi> chorbiesToShow;

		chorbiesToShow = this.chorbiRepository.findAll();

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

	public Collection<Chorbi> findAllChorbiesWhoLikedByThisUserForNotDoubleLike(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Chorbi> chorbies;

		chorbies = this.chorbiRepository.findAllChorbiesWhoLikedByThisUserForNotDoubleLike(chorbi.getId());

		Assert.notNull(chorbies);

		return chorbies;

	}

	public Chorbi findByPrincipal() {
		Chorbi result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);

		return result;
	}

	public Chorbi findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Chorbi result;

		result = this.chorbiRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<Taste> findAllMyTastesWithoutBannedChorbies(final Chorbi c) {
		Assert.notNull(c);
		Collection<Taste> t;

		t = this.chorbiRepository.findAllMyTastesWithoutBannedChorbies(c.getId());

		Assert.notNull(t);

		return t;
	}

	public Collection<Chorbi> findAllTastesToMeWithoutBannedChorbies(final Chorbi c) {
		Assert.notNull(c);
		Collection<Chorbi> t;

		t = this.chorbiRepository.findAllTastesToMeWithoutBannedChorbies(c.getId());

		Assert.notNull(t);

		return t;
	}

	public void banChorbi(final Chorbi chorbi) {
		Assert.notNull(chorbi);

		Assert.isTrue(chorbi.isBan() == false);

		chorbi.setBan(true);

		this.save(chorbi);

	}

	public void unBanChorbi(final Chorbi chorbi) {
		Assert.notNull(chorbi);

		Assert.isTrue(chorbi.isBan() == true);

		chorbi.setBan(false);

		this.save(chorbi);

	}

	private static int calcularEdad(final Date fecha) {
		final Calendar fechaNac = Calendar.getInstance();
		fechaNac.setTime(fecha);
		final Calendar today = Calendar.getInstance();
		int diffYear = today.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
		final int diffMonth = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
		final int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
		// Si está en ese año pero todavía no los ha cumplido
		if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0))
			diffYear = diffYear - 1;
		return diffYear;
	}
}
