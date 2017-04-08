package hu.andrasn.summertime.parser;

import java.util.List;

import hu.andrasn.summertime.data.DestinationDto;

public interface DestinationParser {

	List<DestinationDto> parse(List<String> inputDestinations);
}
