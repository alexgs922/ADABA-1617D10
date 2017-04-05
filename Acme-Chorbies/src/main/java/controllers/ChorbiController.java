
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.TasteService;
import services.TemplateService;
import domain.Chorbi;
import domain.Taste;
import domain.Template;
import form.RegistrationForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}


	//Services -----------------------------------------------------------

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private TasteService	tasteService;

	@Autowired
	private TemplateService	templateService;


	//Browse the chorbies who has registered to the system
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Chorbi> chorbiesToShow;

		chorbiesToShow = this.chorbiService.findAllNotBannedChorbies();

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbiesToShow);
		result.addObject("requestURI", "chorbi/list.do");

		return result;

	}

	//See Chorbi Profile
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;
		chorbi = this.chorbiService.findOne(chorbiId);
		boolean toLike;

		final Chorbi principal = this.chorbiService.findByPrincipal();

		Assert.isTrue(chorbi.isBan() == false);

		if (principal != null) {
			toLike = true;
			for (final Taste t : principal.getGivenTastes())
				if (t.getChorbi().getId() == chorbiId) {
					toLike = false;
					break;
				}
		} else
			toLike = false;

		result = new ModelAndView("chorbi/displayProfile");
		result.addObject("chorbi", chorbi);
		result.addObject("principal", principal);
		result.addObject("toLike", toLike);
		result.addObject("requestURI", "chorbi/profile.do?chorbiId=" + chorbiId);

		return result;
	}
	//See people who like him/her

	@RequestMapping(value = "/listWhoLikeThem", method = RequestMethod.GET)
	public ModelAndView listChorbiesWhoLike(@RequestParam final int chorbiId) {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Chorbi chorbi;
		chorbi = this.chorbiService.findOne(chorbiId);
		chorbies = this.chorbiService.findAllChorbiesWhoLikeThem(chorbi);

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("requestURI", "chorbi/listWhoLikeThem.do?chorbiId=" + chorbiId);

		return result;
	}

	//See people who like to him/her

	@RequestMapping(value = "/listWhoLikedThis", method = RequestMethod.GET)
	public ModelAndView listWhoLikedThis(@RequestParam final int chorbiId) {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Chorbi chorbi;
		chorbi = this.chorbiService.findOne(chorbiId);
		chorbies = this.chorbiService.findAllChorbiesWhoLikedByThisUser(chorbi);

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("requestURI", "chorbi/listWhoLikedThis.do?chorbiId=" + chorbiId);

		return result;
	}

	// Terms of Use -----------------------------------------------------------
	@RequestMapping("/dataProtection")
	public ModelAndView dataProtection() {
		ModelAndView result;
		result = new ModelAndView("chorbi/dataProtection");
		return result;

	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		RegistrationForm chorbi;

		chorbi = new RegistrationForm();
		result = this.createEditModelAndView(chorbi);

		return result;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("chorbi") @Valid final RegistrationForm form, final BindingResult binding) {

		ModelAndView result;
		Chorbi chorbi;
		if (binding.hasErrors()) {
			if (binding.getGlobalError() != null)
				result = this.createEditModelAndView(form, binding.getGlobalError().getCode());
			else
				result = this.createEditModelAndView(form);
		} else
			try {
				final Template t = this.templateService.create();
				chorbi = this.chorbiService.reconstruct(form);
				chorbi = this.chorbiService.saveAndFlush(chorbi, t);

				result = new ModelAndView("redirect:../security/login.do");
			} catch (final DataIntegrityViolationException exc) {
				result = this.createEditModelAndView(form, "chorbi.duplicated.user");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(form, "chorbi.commit.error");
			}
		return result;
	}

	// Other methods

	protected ModelAndView createEditModelAndView(final RegistrationForm chorbi) {
		ModelAndView result;
		result = this.createEditModelAndView(chorbi, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final RegistrationForm chorbi, final String message) {
		ModelAndView result;
		result = new ModelAndView("chorbi/register");
		result.addObject("chorbi", chorbi);
		result.addObject("message", message);
		return result;

	}

}
