package hu.andrasn.summertime;

import java.util.Arrays;

import hu.andrasn.summertime.formatter.DestinationOutputFormatterImpl;
import hu.andrasn.summertime.parser.DestinationParserImpl;

public class Main {

	public static void main(String[] args) {
		Planner planner = new Planner(new DestinationParserImpl(), new DestinationOutputFormatterImpl());
		System.out.println(planner.plan(Arrays.asList(args)));
	}

}
