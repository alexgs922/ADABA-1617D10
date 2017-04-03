
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}


	//Services

	@Autowired
	private ChorbiService	chorbiService;


	//Browse the chorbies who has registered to the system
	@RequestMapping(value = "/listChorbies", method = RequestMethod.GET)
	public ModelAndView listChorbies() {

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

		result = new ModelAndView("chorbi/displayProfile");
		result.addObject("chorbi", chorbi);
		result.addObject("requestURI", "chorbi/profile.do?chorbiId=" + chorbiId);

		return result;
	}

	//See people who like him/her

	@RequestMapping(value = "/listChorbiesWhoLike", method = RequestMethod.GET)
	public ModelAndView listChorbiesWhoLike(@RequestParam final int chorbiId) {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Chorbi chorbi;
		chorbi = this.chorbiService.findOne(chorbiId);
		chorbies = this.chorbiService.findAllChorbiesWhoLikeThem(chorbi);

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbies);
		result.addObject("requestURI", "chorbi/listWhoLikeThem.do?chorbiId=" + chorbiId);

		return result;
	}
}
