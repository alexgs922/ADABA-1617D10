
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CoordinateRepository;
import domain.Coordinate;

@Component
@Transactional
public class StringToCoordinateConverter implements Converter<String, Coordinate> {

	@Autowired
	CoordinateRepository	coordinateRepository;


	@Override
	public Coordinate convert(final String text) {
		Coordinate result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.coordinateRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
