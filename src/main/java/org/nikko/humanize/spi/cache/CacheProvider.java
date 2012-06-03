package org.nikko.humanize.spi.cache;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.nikko.humanize.util.RelativeDate;

/**
 * Facade to access resource caches. Includes string arrays, bundles and formats
 * by locale.
 * 
 * @author mfornos
 * 
 */
public interface CacheProvider {

	boolean containsBundle(Locale locale);

	boolean containsNumberFormat(String cache, Locale locale);

	boolean containsStrings(String cache, Locale locale);

	ResourceBundle getBundle(Locale locale);

	NumberFormat getNumberFormat(String cache, Locale locale);

	String[] getStrings(String cache, Locale locale);

	ResourceBundle putBundle(Locale locale, ResourceBundle bundle);

	NumberFormat putNumberFormat(String cache, Locale locale, NumberFormat format);

	String[] putStrings(String cache, Locale locale, String[] split);

	RelativeDate getRelativeDate(Locale locale);

	boolean containsRelativeDate(Locale locale);

	RelativeDate putRelativeDate(Locale locale, RelativeDate instance);
	
}
