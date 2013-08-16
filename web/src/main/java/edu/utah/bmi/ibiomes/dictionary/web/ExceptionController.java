package edu.utah.bmi.ibiomes.dictionary.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Exception controller
 * @author Julien Thibault, University of Utah
 *
 */
@Controller
public class ExceptionController {

    @Autowired
    private HttpServletRequest request;
    
	@RequestMapping("/error")
	protected ModelAndView showError() throws Exception {
		
		ModelAndView mav = new ModelAndView("exception");
		
		Throwable exception = (Throwable)request.getAttribute("exception");
		if (exception == null)
			exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
		
		if (exception != null)
		{
			mav.addObject("errorTitle", "Error");
			mav.addObject("errorMsg", exception.getLocalizedMessage());
			mav.addObject("errorTrace", getCustomStackTrace(exception));
		}
		else {
			mav.addObject("errorTitle", "Error (unknown)");
			mav.addObject("errorMsg", "No description available.");
		}		
		return mav;
	}
	
	/**
	 * 
	 * @param aThrowable
	 * @return
	 */
	private static String getCustomStackTrace(Throwable aThrowable) 
	{
	    //add the class name and any message passed to constructor
	    final StringBuilder result = new StringBuilder( "Error: " );
	    result.append(aThrowable.toString());
	    final String NEW_LINE = "<br/>";
	    result.append(NEW_LINE);

	    //add each element of the stack trace
	    for (StackTraceElement element : aThrowable.getStackTrace() ){
	      result.append( element );
	      result.append( NEW_LINE );
	    }
	    return result.toString();
	}
}
