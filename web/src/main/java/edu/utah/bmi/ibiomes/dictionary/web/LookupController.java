package edu.utah.bmi.ibiomes.dictionary.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author Julien Thibault, University of Utah
 *
 */
@Controller
public class LookupController {

    @Autowired
    private HttpServletRequest request;

	@RequestMapping("/lookup")
	protected ModelAndView handleRequestInternal() throws Exception {
		
		//prepare response
		ModelAndView mav = new ModelAndView("lookup");
		return mav;
	}
}
