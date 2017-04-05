
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.ConfigurationService;
import services.TemplateService;
import domain.Chorbi;
import domain.Configuration;
import domain.Coordinate;
import domain.CreditCard;
import domain.Template;

@Controller
@RequestMapping("/template")
public class TemplateController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public TemplateController() {
		super();
	}


	//Services

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private TemplateService			templateService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listChorbies() {

		ModelAndView result;

		final Chorbi c = this.chorbiService.findByPrincipal();
		final Template template = c.getTemplate();
		final Collection<Coordinate> coordinates = template.getCoordinates();

		result = new ModelAndView("template/list");
		result.addObject("template", template);
		result.addObject("coordinates", coordinates);
		result.addObject("requestURI", "template/list.do");

		return result;

	}

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public ModelAndView listByFinder() {
		ModelAndView result;
		Collection<Chorbi> cs;

		final Chorbi t1 = this.chorbiService.findByPrincipal();
		final Template template = t1.getTemplate();

		//Credit card para comprobar la validez cada vez que se busque
		final CreditCard c = t1.getCreditCard();

		if (template == null) {
			final Collection<Chorbi> cs2 = this.chorbiService.findAll();
			result = new ModelAndView("template/result");
			result.addObject("chorbies", cs2);
			result.addObject("requestURI", "template/result.do");
			return result;

		} else {

			final Configuration confi = this.configurationService.findConfiguation();
			final Integer horaTotal = this.configurationService.getHoraConfiguration(confi);

			final Date momentTemplate = template.getMoment();
			if (momentTemplate != null) {

				final Date currentDate = new Date();
				final long mm = momentTemplate.getTime();

				final long diff = (currentDate.getTime() - 10000) - momentTemplate.getTime();
				final long minutes = (diff / 1000) / 60;

				//cambiar horaTotal por 720 para comprobar que funciona ya que no existe configuration
				if (minutes > horaTotal || template.getChorbies().isEmpty()) {
					cs = this.templateService.findChorbiesByMyTemplate(template);
					template.setMoment(new Date());

					template.setChorbies(cs);
					this.templateService.save(template);
				} else
					cs = template.getChorbies();
			} else {
				cs = this.templateService.findChorbiesByMyTemplate(template);
				template.setMoment(new Date());

				template.setChorbies(cs);
				this.templateService.save(template);
			}

			result = new ModelAndView("template/result");
			result.addObject("chorbies", cs);
			result.addObject("requestURI", "template/result.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int templateId) {
		ModelAndView result;
		Template template;
		try {
			template = this.templateService.findOneToEdit(templateId);
			result = this.createEditModelAndView(template);

		} catch (final Throwable oops) {
			final Template template2 = this.templateService.create();
			result = this.createEditModelAndView(template2, "template.commit.error");
		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("template") @Valid Template template, final BindingResult binding) {
		ModelAndView result;
		//Template template;

		if (binding.hasErrors()) {

			if (binding.getGlobalError() != null)
				result = this.createEditModelAndView(template, binding.getGlobalError().getCode());
			else
				result = this.createEditModelAndView(template);

		} else
			try {

				template = this.templateService.reconstruct(template);
				this.templateService.saveEdit(template);
				result = new ModelAndView("redirect:/template/list.do");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(template, "template.commit.error");
			}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Template template) {
		ModelAndView result;
		result = this.createEditModelAndView(template, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Template template, final String message) {
		ModelAndView result;

		result = new ModelAndView("template/edit");
		result.addObject("template", template);
		result.addObject("message", message);
		return result;

	}
}
