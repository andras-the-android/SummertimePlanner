package hu.andrasn.summertime;

import java.util.ArrayList;
import java.util.List;

import hu.andrasn.summertime.data.DestinationDto;
import hu.andrasn.summertime.formatter.DestinationOutputFormatter;
import hu.andrasn.summertime.parser.DestinationParser;

public class Planner {
	
	private DestinationParser destinationParser;
	private DestinationOutputFormatter outputFormatter;
	
	private List<DestinationDto> destinations = new ArrayList<>();
	private LinkedList<String> orderedDestinations = new LinkedList<>();
	
	public Planner(DestinationParser destinationParser, DestinationOutputFormatter outputFormatter) {
		this.destinationParser = destinationParser;
		this.outputFormatter = outputFormatter;
	}	
	
	public String plan(List<String> inputDestinations) {
		destinations = destinationParser.parse(inputDestinations);
		orderDestinations();		
		return outputFormatter.format(orderedDestinations.iterator());
	}
	
	private void orderDestinations() {
//		orderedDestinations.addAll(destinations.keySet());
		for (DestinationDto destination : destinations) {
			if (!orderedDestinations.contains(destination.getName())) {
				orderedDestinations.add(destination.getName());
			}
			if (destination.hasDependency()) {
				if (!orderedDestinations.contains(destination.getDependency())) {
					orderedDestinations.insertBefore(destination.getDependency(), destination.getName());
				}
			}
		}
	}

}
