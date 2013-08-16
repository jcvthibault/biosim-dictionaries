package edu.utah.bmi.ibiomes.dictionary.web;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntry;
import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue;
import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue.DictionaryAttributeType;
import edu.utah.bmi.ibiomes.dictionary.lucene.LuceneDictionary;


/**
 * 
 * @author Julien Thibault, University of Utah
 *
 */
@Controller
public class DictionaryController {

	private final Logger logger = Logger.getLogger(DictionaryController.class);

    @Autowired
    private ServletContext context;
    @Autowired
    private HttpServletRequest request;

    private LuceneDictionary getDictionary()
	{
		if (dictionary == null){
			WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
			dictionary = (LuceneDictionary)wContext.getBean("luceneDictionary");
			logger.info("Lucene dictionary loaded (" + dictionary.getNumberOfEntries() + ")");
			System.out.println("Lucene dictionary loaded (" + dictionary.getNumberOfEntries() + ")");
		}
		return dictionary;
	}

    private LuceneDictionary dictionary;
    
	@RequestMapping("/list")
	protected ModelAndView handleRequestInternal() throws Exception {
				
		String name = request.getParameter("name");
				
		List<DictionaryEntry> dataTable = null;
		List<String> dataHeaders = null;
		List<DictionaryAttributeType> dataTypes = null;

		if (name==null || name.length()==0)
			throw new Exception("Missing argument: name of the dictionary ([name])");

		name = name.toUpperCase().trim();
		String dictionaryName = name.toUpperCase().replaceAll("_", " ");
		String dictionaryDescription = "";
		
		//lookup concept definition
		List<DictionaryEntry> concepts = getDictionary().lookupTerm("ID_STR", name);
		if (concepts.size()>0){
			DictionaryEntryValue entryValue = concepts.get(0).getValue(LuceneDictionary.LOOKUP_FIELD_DESCRIPTION);
			if (entryValue!=null)
				dictionaryDescription = entryValue.getValue();
			entryValue = concepts.get(0).getValue(LuceneDictionary.LOOKUP_FIELD_TERM);
			if (entryValue!=null)
				dictionaryName = entryValue.getValue();
		}
		//lookup dictionary
		dataTable = getDictionary().lookupTerm(LuceneDictionary.LOOKUP_FIELD_ATTRIBUTE_TYPE, name);
		dataTable = prepareDataForHtml(dataTable);
		
		//get headers
		dataHeaders = dataTable.get(0).getAttributeNames();
		if (dataHeaders==null || dataHeaders.size()==0)
			throw new Exception("Cannot determine columns for dictionary: '"+name+"'");
		for (int h=0;h<dataHeaders.size();h++){
			dataHeaders.set(h, dataHeaders.get(h).replaceAll("_", " "));
		}
		//get column types
		dataTypes = dataTable.get(0).getAttributeTypes();
		
		//get data
		if (dataTable==null || dataTable.size()==0)
			throw new Exception("Cannot find any entry for dictionary: '"+name+"'");

		//prepare response
		ModelAndView mav = new ModelAndView("dictionary");
		
		mav.addObject("dataTable", dataTable);
		mav.addObject("dataHeaders", dataHeaders);
		mav.addObject("dataColumnTypes", dataTypes);
		mav.addObject("dictionaryName", dictionaryName);
		mav.addObject("dictionaryDescription", dictionaryDescription);

		return mav;
	}

	/**
	 * Add HTML encoding to strings if necessary
	 * @param dataTable
	 * @return
	 */
	private List<DictionaryEntry> prepareDataForHtml(
			List<DictionaryEntry> dataTable) {
		HTMLUtils utils = new HTMLUtils();
		for (DictionaryEntry entry : dataTable){
			List<String> attributeNames = entry.getAttributeNames();
			for (String field : attributeNames){
				String originalString = entry.getValue(field).getValue();
				entry.replaceValue(field, utils.normalizeStringToHTML(originalString));
			}
		}
		return dataTable;
	}
}
