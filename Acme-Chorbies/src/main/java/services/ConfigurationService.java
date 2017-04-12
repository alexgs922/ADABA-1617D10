
package services;

import java.util.ArrayList;
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

	public Integer getHoraConfiguration(final Configuration c) {

		Integer horas = c.getHour();
		Integer minutos = c.getMinute();
		Integer segundos = c.getSecond();

		final Integer horaTotal;

		while (minutos > 60) {
			minutos = minutos - 60;
			horas = horas + 1;

		}

		while (segundos > 60) {
			segundos = segundos - 60;
			minutos = minutos + 1;
		}

		horaTotal = horas + minutos + segundos;

		return horaTotal;

	}

	public Configuration findConfiguation() {
		final Configuration c = this.configurationRepository.findConfiguration();
		return c;
	}

	/*public Collection<String> findAllBanners() {
		final Collection<String> res = new ArrayList<String>();
		final Configuration c = this.findConfiguation();
		Assert.notNull(c);
		res.addAll(c.getBanners());
		return res;

	}*/
}
