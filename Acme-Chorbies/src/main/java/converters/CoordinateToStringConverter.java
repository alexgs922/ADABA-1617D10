
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Coordinate;

@Component
@Transactional
public class CoordinateToStringConverter implements Converter<Coordinate, String> {

	@Override
	public String convert(final Coordinate source) {
		String res;

		if (source == null)
			res = null;
		else
			res = String.valueOf(source.getId());

		return res;
	}

}
