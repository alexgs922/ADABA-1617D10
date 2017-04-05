
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TemplateRepository;
import domain.Template;

@Service
@Transactional
public class TemplateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TemplateRepository	templateRepository;

	@Autowired
	private ChorbiService	chorbiService;

	// Constructors -----------------------------------------------------------

	public TemplateService() {
		super();
	}

	//Creates -----------------------------------------------------------------

	public Template create() {
		Template res;
		res = new Template();
		res.setChorbies(chorbiService.findAll());
		return res;
	}

	//Simple CRUD -------------------------------------------------------------

	public Collection<Template> findAll() {
		Collection<Template> res;
		res = this.templateRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Template findOne(final int templateId) {
		Template res;
		res = this.templateRepository.findOne(templateId);
		Assert.notNull(res);
		return res;
	}

	public Template save(final Template t) {
		Assert.notNull(t);
		return this.templateRepository.save(t);

	}
	
	public Template saveAndFlush(Template t){
		Assert.notNull(t);
		return this.templateRepository.saveAndFlush(t);
		
	}
	
	public void delete(final Template t) {
		Assert.notNull(t);
		this.templateRepository.delete(t);
	}

	//Other business methods --------------------------------------

}
