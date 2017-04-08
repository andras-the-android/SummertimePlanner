package hu.andrasn.summertime.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.andrasn.summertime.data.DestinationDto;

public class DestinationParserImpl implements DestinationParser  {
	
	@Override
	public List<DestinationDto> parse(List<String> inputDestinations) {
		List<DestinationDto> destinations = new ArrayList<>();
		
		if (inputDestinations != null) {
			for (String inputDestination : inputDestinations) {			
				check(inputDestination);				
				putToList(destinations, inputDestination);
			}
		}	
		
		return destinations;
	}

	private void check(String inputDestination) {
		if (inputDestination == null) {
			throw new IllegalArgumentException("Input destination is null");
		}
		
		if (!inputDestination.contains("=>")) {
			throw new IllegalArgumentException("Input destination doesn't contain \"=>\": " + inputDestination);
		}
		
		if (inputDestination.split("=>").length > 2) {
			throw new IllegalArgumentException("Input destination can contain only one \"=>\": " + inputDestination);
		}
	}
	
	private void putToList(List<DestinationDto> destinations, String inputDestination) {
		String[] parsedInputRow = inputDestination.split("=>");
		DestinationDto dto = new DestinationDto();
		dto.setName(parsedInputRow[0].trim());
		String dependency = "";
		if (parsedInputRow.length > 1) {
			dependency = parsedInputRow[1].trim();
		}
		dto.setDependency(dependency);
		destinations.add(dto);
	}


}
