
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Administrator;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	//Simple CRUD -------------------------------------------------------------

	public Configuration create() {
		Configuration res;
		res = new Configuration();
		return res;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> res;
		res = this.configurationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Configuration findOne(final int configurationId) {
		Configuration res;
		res = this.configurationRepository.findOne(configurationId);
		Assert.notNull(res);
		return res;
	}

	public Configuration save(final Configuration c) {
		Assert.isTrue(this.checkAdminPrincipal());
		Assert.notNull(c);
		return this.configurationRepository.save(c);

	}

	public void delete(final Configuration c) {
		Assert.notNull(c);
		this.configurationRepository.delete(c);
	}

	//Other business methods --------------------------------------

	private boolean checkAdminPrincipal() {
		final boolean res;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();

		res = principal != null;

		return res;
	}
}
