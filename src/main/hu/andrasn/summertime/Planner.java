package hu.andrasn.summertime;

import java.util.List;

import hu.andrasn.summertime.data.DestinationDto;
import hu.andrasn.summertime.formatter.DestinationOutputFormatter;
import hu.andrasn.summertime.parser.DestinationParser;

public class Planner {
	
	private DestinationParser destinationParser;
	private DestinationOutputFormatter outputFormatter;
	
	public Planner(DestinationParser destinationParser, DestinationOutputFormatter outputFormatter) {
		this.destinationParser = destinationParser;
		this.outputFormatter = outputFormatter;
	}	
	
	public String plan(List<String> inputDestinations) {
		List<DestinationDto> destinations = destinationParser.parse(inputDestinations);
		LinkedList<String> orderedDestinations = orderDestinations(destinations);		
		return outputFormatter.format(orderedDestinations.iterator());
	}
	
	private LinkedList<String> orderDestinations(List<DestinationDto> destinations) {
		LinkedList<String> orderedDestinations = new LinkedList<>();
		for (DestinationDto destination : destinations) {
			boolean loopCheckPassed = false;
			if (!orderedDestinations.contains(destination.getName())) {
				orderedDestinations.add(destination.getName());
				loopCheckPassed = true;
			}
			
			if (destination.hasDependency()) {
				if (!orderedDestinations.contains(destination.getDependency())) {
					orderedDestinations.insertBefore(destination.getDependency(), destination.getName());
					loopCheckPassed = true;
				}
			} else {
				loopCheckPassed = true;
			}
			
			if (!loopCheckPassed) {
				throw new IllegalArgumentException("A loop detected among the destinations");
			}
		}
		return orderedDestinations;
	}

}
