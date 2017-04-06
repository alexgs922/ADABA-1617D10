
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/administrator/chorbi")
public class AdministratorChorbiController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorChorbiController() {
		super();
	}


	// Services -------------------------------------------------------------------

	@Autowired
	private ChorbiService	chorbiService;


	//List all chorbies (banned and not banned) --------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Chorbi> chorbiesToShow;

		chorbiesToShow = this.chorbiService.findAllChorbies();

		result = new ModelAndView("administrator/chorbi/list");
		result.addObject("chorbies", chorbiesToShow);
		result.addObject("requestURI", "administrator/chorbi/list.do");

		return result;

	}

	// Ban and Unban chorbi ------------------------------------------------------------------------------

	@RequestMapping(value = "/banChorbi", method = RequestMethod.GET)
	public ModelAndView banChorbi(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(chorbiId);

		try {
			this.chorbiService.banChorbi(chorbi);
			result = new ModelAndView("redirect:list.do");

		} catch (final Throwable th) {
			result = new ModelAndView("forbiddenOperation");

		}

		return result;

	}

}