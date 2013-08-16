package edu.utah.bmi.ibiomes.dictionary.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntry;
import edu.utah.bmi.ibiomes.dictionary.core.DictionaryEntryValue;
import edu.utah.bmi.ibiomes.dictionary.lucene.LuceneDictionary;


/**
 * REST interface to lookup entries in the dictionary
 * @author Julien Thibault, University of Utah
 *
 */
@Controller
@RequestMapping(value = "/services/lookup")
public class LookupService 
{
	private final Logger logger = Logger.getLogger(LookupService.class);
	
    @Autowired
    private ServletContext context;
    
    private LuceneDictionary dictionary;
	private HTMLUtils htmlUtils;
    
    private class LookupResult {
    	public String uid;
    	public String term;
    	public String description;
    	public String attributeType;
    }
    
	private LuceneDictionary getDictionary()
	{
		if (dictionary == null){
			WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
			dictionary = (LuceneDictionary)wContext.getBean("luceneDictionary");
			logger.info("Lucene dictionary loaded (" + dictionary.getNumberOfEntries() + ")");
			System.out.println("Lucene dictionary loaded (" + dictionary.getNumberOfEntries() + ")");
			
			htmlUtils = new HTMLUtils();
		}
		return dictionary;
	}
	
	/**
	 * Lookup entries by term
	 * @param term Term to lookup
	 * @throws Exception 
	 */
	@RequestMapping(value = "/byterm", method = RequestMethod.GET)
	@ResponseBody
	public List<LookupResult> findEntriesByTerm(
			@RequestParam("term") String term,
			@RequestParam(value="htmlEncoding",required=false,defaultValue="false") boolean htmlEncoding) throws Exception{
		term = term + "*";
		List<DictionaryEntry> matches = getDictionary().lookupTerm(LuceneDictionary.LOOKUP_FIELD_TERM, term);
		return entriesToLookupResults(matches, htmlEncoding);
	}

	/**
	 * Lookup entries by description
	 * @param description Description to lookup
	 * @throws Exception 
	 */
	@RequestMapping(value = "/bydesc", method = RequestMethod.GET)
	@ResponseBody
	public List<LookupResult> findEntriesByDescription(
			@RequestParam("term") String description,
			@RequestParam(value="htmlEncoding",required=false,defaultValue="false") boolean htmlEncoding) throws Exception{
		description = description + "*";
		List<DictionaryEntry> matches = getDictionary().lookupTerm(LuceneDictionary.LOOKUP_FIELD_DESCRIPTION, description);
		return entriesToLookupResults(matches, htmlEncoding);
	}
	
	/**
	 * Lookup entries by UID
	 * @param uid UID to lookup
	 * @throws Exception 
	 */
	@RequestMapping(value = "/byuid", method = RequestMethod.GET)
	@ResponseBody
	public DictionaryEntry findEntriesByUID(
			@RequestParam("uid") String uid,
			@RequestParam(value="htmlEncoding",required=false,defaultValue="false") boolean htmlEncoding) throws Exception{
		List<DictionaryEntry> matches = getDictionary().lookupTerm(LuceneDictionary.LOOKUP_FIELD_UID, uid);
		if (matches.size()>0){
			DictionaryEntry match = matches.get(0);
			if (htmlEncoding){
				DictionaryEntryValue field = match.getValue(LuceneDictionary.LOOKUP_FIELD_TERM);
				if (field != null && field.getValue()!=null){
					String original = field.getValue();
					match.replaceValue(LuceneDictionary.LOOKUP_FIELD_TERM, 
							htmlUtils.normalizeStringToHTML(original));
				}
			}
			return match;
		}
		else return null;
	}

	/**
	 * Lookup entries by attribute type
	 * @param type Attribute type to lookup
	 * @throws Exception 
	 */
	@RequestMapping(value = "/bytype", method = RequestMethod.GET)
	@ResponseBody
	public DictionaryEntry findEntriesByAttributeType(
			@RequestParam("type") String type) throws Exception{
		List<DictionaryEntry> matches = getDictionary().lookupTerm(LuceneDictionary.LOOKUP_FIELD_ATTRIBUTE_TYPE, type);
		if (matches.size()>0)
			return matches.get(0);
		else return null;
	}
	
	private LookupResult entryToLookupResult(DictionaryEntry entry, boolean htmlEncoding){
		LookupResult result = new LookupResult();
		DictionaryEntryValue uid = entry.getValue(LuceneDictionary.LOOKUP_FIELD_UID);
		DictionaryEntryValue term = entry.getValue(LuceneDictionary.LOOKUP_FIELD_TERM);
		DictionaryEntryValue description = entry.getValue(LuceneDictionary.LOOKUP_FIELD_DESCRIPTION);
		DictionaryEntryValue attributeType = entry.getValue(LuceneDictionary.LOOKUP_FIELD_ATTRIBUTE_TYPE);
		if (uid!=null)
			result.uid = uid.getValue();
		if (term!=null){
			result.term = term.getValue();
			if (htmlEncoding)
				result.term = htmlUtils.normalizeStringToHTML(result.term);
		}
		if (description!=null)
			result.description = description.getValue();
		if (attributeType!=null)
			result.attributeType = attributeType.getValue().replaceAll("_", " ");
		return result;
	}
	
	private List<LookupResult> entriesToLookupResults(List<DictionaryEntry> entries, boolean htmlEncoding){
		List<LookupResult> results = new ArrayList<LookupService.LookupResult>();
		for (DictionaryEntry entry : entries){
			results.add(entryToLookupResult(entry,htmlEncoding));
		}
		return results;
	}
}
