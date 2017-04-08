package hu.andrasn.summertime.formatter;

import java.util.Iterator;

public class DestinationOutputFormatterImpl implements DestinationOutputFormatter {
	
	public static final String SEPARATOR = " => ";

	@Override
	public String format(Iterator<String> iterator) {
		if (iterator == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		while (iterator.hasNext()) {
			if (sb.length() > 0) {
				sb.append(SEPARATOR);
			}
			sb.append(iterator.next());
		}
		return sb.toString();
	}

}
