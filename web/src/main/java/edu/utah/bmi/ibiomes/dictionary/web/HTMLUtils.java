/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2013  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.dictionary.web;

public class HTMLUtils {
	
	private class SpecialHTMLCharacter{
		String character;
		String htmlCode;
		String htmlEntity;
		public SpecialHTMLCharacter(String character, String htmlCode, String htmlEntity){
			this.character = character;
			this.htmlCode = htmlCode;
			this.htmlEntity = htmlEntity;
		}
	}
	
	private final SpecialHTMLCharacter[] characterTable = {
			new SpecialHTMLCharacter("À","&#192;","&Agrave;"),
			new SpecialHTMLCharacter("Á","&#193;","&Aacute;"),
			new SpecialHTMLCharacter("Â","&#194;","&Acirc;"),
			new SpecialHTMLCharacter("Ã","&#195;","&Atilde;"),
			new SpecialHTMLCharacter("Ä","&#196;","&Auml;"),
			new SpecialHTMLCharacter("Å","&#197;","&Aring;"),
			new SpecialHTMLCharacter("Æ","&#198;","&AElig;"),
			new SpecialHTMLCharacter("Ç","&#199;","&Ccedil;"),
			new SpecialHTMLCharacter("È","&#200;","&Egrave;"),
			new SpecialHTMLCharacter("É","&#201;","&Eacute;"),
			new SpecialHTMLCharacter("Ê","&#202;","&Ecirc;"),
			new SpecialHTMLCharacter("Ë","&#203;","&Euml;"),
			new SpecialHTMLCharacter("Ì","&#204;","&Igrave;"),
			new SpecialHTMLCharacter("Í","&#205;","&Iacute;"),
			new SpecialHTMLCharacter("Î","&#206;","&Icirc;"),
			new SpecialHTMLCharacter("Ï","&#207;","&Iuml;"),
			new SpecialHTMLCharacter("Ð","&#208;","&ETH;"),
			new SpecialHTMLCharacter("Ñ","&#209;","&Ntilde;"),
			new SpecialHTMLCharacter("Ò","&#210;","&Ograve;"),
			new SpecialHTMLCharacter("Ó","&#211;","&Oacute;"),
			new SpecialHTMLCharacter("Ô","&#212;","&Ocirc;"),
			new SpecialHTMLCharacter("Õ","&#213;","&Otilde;"),
			new SpecialHTMLCharacter("Ö","&#214;","&Ouml;"),
			new SpecialHTMLCharacter("Ø","&#216;","&Oslash;"),
			new SpecialHTMLCharacter("Ù","&#217;","&Ugrave;"),
			new SpecialHTMLCharacter("Ú","&#218;","&Uacute;"),
			new SpecialHTMLCharacter("Û","&#219;","&Ucirc;"),
			new SpecialHTMLCharacter("Ü","&#220;","&Uuml;"),
			new SpecialHTMLCharacter("Ý","&#221;","&Yacute;"),
			new SpecialHTMLCharacter("Þ","&#222;","&THORN;"),
			new SpecialHTMLCharacter("ß","&#223;","&szlig;"),
			new SpecialHTMLCharacter("à","&#224;","&agrave;"),
			new SpecialHTMLCharacter("á","&#225;","&aacute;"),
			new SpecialHTMLCharacter("â","&#226;","&acirc;"),
			new SpecialHTMLCharacter("ã","&#227;","&atilde;"),
			new SpecialHTMLCharacter("ä","&#228;","&auml;"),
			new SpecialHTMLCharacter("å","&#229;","&aring;"),
			new SpecialHTMLCharacter("æ","&#230;","&aelig;"),
			new SpecialHTMLCharacter("ç","&#231;","&ccedil;"),
			new SpecialHTMLCharacter("è","&#232;","&egrave;"),
			new SpecialHTMLCharacter("é","&#233;","&eacute;"),
			new SpecialHTMLCharacter("ê","&#234;","&ecirc;"),
			new SpecialHTMLCharacter("ë","&#235;","&euml;"),
			new SpecialHTMLCharacter("ì","&#236;","&igrave;"),
			new SpecialHTMLCharacter("í","&#237;","&iacute;"),
			new SpecialHTMLCharacter("î","&#238;","&icirc;"),
			new SpecialHTMLCharacter("ï","&#239;","&iuml;"),
			new SpecialHTMLCharacter("ð","&#240;","&eth;"),
			new SpecialHTMLCharacter("ñ","&#241;","&ntilde;"),
			new SpecialHTMLCharacter("ò","&#242;","&ograve;"),
			new SpecialHTMLCharacter("ó","&#243;","&oacute;"),
			new SpecialHTMLCharacter("ô","&#244;","&ocirc;"),
			new SpecialHTMLCharacter("õ","&#245;","&otilde;"),
			new SpecialHTMLCharacter("ö","&#246;","&ouml;"),
			new SpecialHTMLCharacter("ø","&#248;","&oslash;"),
			new SpecialHTMLCharacter("ù","&#249;","&ugrave;"),
			new SpecialHTMLCharacter("ú","&#250;","&uacute;"),
			new SpecialHTMLCharacter("û","&#251;","&ucirc;"),
			new SpecialHTMLCharacter("ü","&#252;","&uuml;"),
			new SpecialHTMLCharacter("ý","&#253;","&yacute;"),
			new SpecialHTMLCharacter("þ","&#254;","&thorn;"),
			new SpecialHTMLCharacter("ÿ","&#255;","&yuml;")
	};
	
	
	/**
	 * Convert special characters to html codes 
	 * @param str String
	 * @return Updated string
	 */
	public String normalizeStringToHTML(String str) {

		for (SpecialHTMLCharacter specChar : characterTable){
			str = str.replaceAll(specChar.character,specChar.htmlCode);
		}
		return str;
	}
	
	/**
	 * Convert html codes to actual characters 
	 * @param str String
	 * @return Updated string
	 */
	public String normalizeStringFromHTML(String str) {

		for (SpecialHTMLCharacter specChar : characterTable){
			str = str.replaceAll(specChar.htmlCode, specChar.character);
			str = str.replaceAll(specChar.htmlEntity, specChar.character);
		}
		return str;
	}
}
