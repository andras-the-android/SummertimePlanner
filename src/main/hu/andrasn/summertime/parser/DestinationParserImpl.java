package hu.andrasn.summertime.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.andrasn.summertime.data.DestinationDto;

public class DestinationParserImpl implements DestinationParser  {
	
	@Override
	public List<DestinationDto> parse(List<String> inputDestinations) {
		List<DestinationDto> destinations = new ArrayList<>();
		
		if (inputDestinations != null) {
			for (String inputDestination : inputDestinations) {			
				checkFormat(inputDestination);				
				putToList(destinations, inputDestination);
			}
			checkIntegrity(destinations);
		}	
		
		return destinations;
	}

	private void checkFormat(String inputDestination) {
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
	
	private void checkIntegrity(List<DestinationDto> destinations) {
		Set<String> destinationSet = new HashSet<>();
		for (DestinationDto destination : destinations) {
			if (destinationSet.contains(destination.getName())) {
				throw new IllegalStateException("Duplicated destination: " + destination.getName());
			}
			destinationSet.add(destination.getName());			
		}
		for (DestinationDto destination : destinations) {
			if (destination.hasDependency() && !destinationSet.contains(destination.getDependency())) {
				throw new IllegalArgumentException("Non-existing dependency: " + destination.getDependency());
			}
		}
	}


}
