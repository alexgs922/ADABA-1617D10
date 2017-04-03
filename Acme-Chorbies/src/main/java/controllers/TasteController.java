
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.TasteService;
import domain.Chorbi;
import domain.Taste;

@Controller
@RequestMapping("/chorbi/chorbi")
public class TasteController {

	// Constructors -----------------------------------------------------------

	public TasteController() {
		super();
	}


	//Services -----------------------------------------------------------

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private TasteService	tasteService;


	// Create ------------------------------------------------

	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public ModelAndView like(@RequestParam final int chorbiId) {
		ModelAndView result;
		Taste taste;
		final Chorbi chorbiToLike = this.chorbiService.findOne(chorbiId);

		taste = this.tasteService.create(chorbiToLike);
		result = this.createEditModelAndView(taste);
		result.addObject("requestURI", "chorbi/chorbi/like.do?chorbiId=" + chorbiId);
		result.addObject("cancelURL", "chorbi/profile.do?chorbiId=" + chorbiId);
		result.addObject("chorbiId", chorbiId);

		return result;
	}

	//Save ------------------------------------------------

	@RequestMapping(value = "/like", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Taste taste, final BindingResult binding, @RequestParam final int chorbiId) {
		ModelAndView result;

		final Chorbi chorbiToLike = this.chorbiService.findOne(chorbiId);
		final Chorbi principal = this.chorbiService.findByPrincipal();

		taste = this.tasteService.reconstruct(taste, binding, chorbiToLike);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(taste);
			result.addObject("requestURI", "chorbi/chorbi/like.do?chorbiId=" + chorbiId);
			result.addObject("cancelURL", "chorbi/profile.do?chorbiId=" + chorbiId);
			result.addObject("chorbiId", chorbiId);
		} else
			try {
				this.tasteService.save(taste);
				result = new ModelAndView("redirect:/chorbi/listWhoLikedThis.do?chorbiId=" + principal.getId());
			} catch (final Throwable th) {
				result = this.createEditModelAndView(taste, "taste.commit.error");
			}

		return result;

	}

	// Delete  --------------------------------------------------------------

	@RequestMapping(value = "/cancelLike", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int chorbiId) {

		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(chorbiId);
		final Chorbi principal = this.chorbiService.findByPrincipal();

		this.tasteService.delete(chorbi);

		result = new ModelAndView("redirect:/chorbi/listWhoLikedThis.do?chorbiId=" + principal.getId());

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Taste taste) {
		ModelAndView result;

		result = this.createEditModelAndView(taste, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Taste taste, final String message) {
		ModelAndView result;

		result = new ModelAndView("taste/edit");
		result.addObject("taste", taste);
		result.addObject("message", message);

		return result;
	}

}
