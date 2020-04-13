package com.ossez.covid19.service;

import com.ossez.covid19.common.Factory;
import com.ossez.covid19.common.mls.feeds.FeedStep;
import com.ossez.covid19.common.mls.feeds.ListingFeed;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//import org.apache.log4j.Level;


public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private static Options options = new Options();
	private static Properties properties = new Properties();

	private static CommandLine cl = null;
	private static Map<String, ListingFeed> feeds = new HashMap<String, ListingFeed>();
	private static boolean dryRun = false;
	private static List<ListingFeed> feedsToRun = new ArrayList<ListingFeed>();
	private static List<FeedStep> feedSteps = new ArrayList<FeedStep>();
	private static int limit = 0;
	private static boolean force = false;
	private static List<Integer> mlsNumbers = new ArrayList<Integer>();

	public static void main(String[] args) {

		// get the idx feed properties file
		Main.parseProperties();

		// load console options
		Main.parseCommandLine(args);

		logger.debug("Starting feeds...");
		System.out.println("starting feeds...");

		// execute the feeds
		Main.executeFeeds();

		Factory.close();
	}

	/**
	 * Executes the feeds specified in the feeds.properties file.
	 */
	private static void executeFeeds() {
		// run through all feeds specified
		for (ListingFeed feed : Main.feedsToRun) {
			feed.setDryRun(Main.dryRun);
			feed.setLimit(Main.limit);
			feed.setMlsNumbers(Main.mlsNumbers);

			// load the properties file for the feed
			Properties properties = new Properties();

			logger.debug("Loading: " + feed.getName() + ".properties");

//			try {
//				properties.load(Main.class.getClassLoader().getResourceAsStream(feed.getName() + ".properties"));
//			} catch (IOException ex) {
//				logger.error("Could not load " + feed.getName() + ".properties");
//				continue;
//			}

//			feed.setProperties(properties);
			feed.setForce(Main.force);
			// run the feed
			try {
				feed.run(Main.feedSteps, null);
			} catch (Exception ex) {
				logger.error("Error proccessing " + feed.getName() + " feed", ex);
			}
		}
	}

	/**
	 * Parses the properties file to get a list of all feeds.
	 */
	private static void parseProperties() {
		try {
			// load the properties file
			logger.debug("Parsing properties");
			Main.properties.load(Main.class.getClassLoader().getResourceAsStream("mls.properties"));

			// load the feeds

			logger.debug("Loading feeds");
			Set<Object> keys = Main.properties.keySet();

			for (Object k : keys) {

				String name = (String) k;
				String feedClass = Main.properties.getProperty(name);

				logger.trace("Loading feed class: " + k);

				// load the class
				Class<?> clazz = Class.forName(feedClass);

				// make sure the class implements ListingFeed
				if (ListingFeed.class.isAssignableFrom(clazz)) {
					logger.trace("Adding " + feedClass + " to list of feeds.");
					// instantiate the class
					Main.feeds.put(name, (ListingFeed) clazz.newInstance());
				} else {
					logger.error("Class " + feedClass + " does not implement the com.verani.common.mls.idx.ListingFeed interface.");
				}

			}

			// String[] feedClasses = Main.properties.getProperty("feeds").split(",");
			//
			// for (String feed : feedClasses) {
			// logger.trace("Loading feed class: " + feed);
			//
			// String name = feed.substring(0, feed.indexOf(";"));
			// String feedClass = feed.substring(feed.indexOf(";") + 1);
			//
			// // load the class
			// Class<?> clazz = Class.forName(feedClass);
			//
			// // make sure the class implements ListingFeed
			// if (ListingFeed.class.isAssignableFrom(clazz)) {
			// logger.trace("Adding " + feedClass + " to list of feeds.");
			// // instantiate the class
			// Main.feeds.put(name, (ListingFeed) clazz.newInstance());
			// } else {
			// logger.error("Class " + feedClass + " does not implement the com.verani.common.mls.idx.ListingFeed interface.");
			// }
			// }

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Could not parse feed properties", ex);
		}
	}

	/**
	 * Handles creation of console options.
	 */
	private static void parseCommandLine(String[] args) {
		// configure command line options
		Main.options.addOption("f", true, "List of which feeds to run.");
		Main.options.addOption("s", true, "List of which steps in the feed should be run.");
		Main.options.addOption("d", false, "Instructs the process to preform a \"dry\" run, meaning no data will actually be updated.");
		Main.options.addOption("l", true, "Max limit of listings to process.");
		Main.options.addOption("u", false, "Forces an update of any listings processed.");
		Main.options.addOption("i", true, "One or more MLS IDs used when updating a single listings (UpdateListing step). Comma separated");
		Main.options.addOption("v", false, "Enables verbose logging.");
		Main.options.addOption("m", true, "Name of the FLAT TXT file that will be used to load spacific MLSNumbers");

		String flatFileName = "";

		// parse command line options
		CommandLineParser parser = new GnuParser();

		try {
			Main.cl = parser.parse(Main.options, args);

			// make sure feeds were specified
			if (Main.cl.hasOption("f")) {
				// get the feeds to run
				for (String feed : Main.cl.getOptionValue("f").split(",")) {
					logger.debug("Adding feed " + feed + " to the list of feeds to run.");
					Main.feedsToRun.add(Main.feeds.get(feed));
				}
			} else {
				logger.error("No feeds have been specified.");
			}

			// make sure feed steps were specified
			if (Main.cl.hasOption("s")) {
				// get the feed steps
				for (String feedStep : Main.cl.getOptionValue("s").split(",")) {
					logger.trace("Adding feed step: " + feedStep);
					Main.feedSteps.add(FeedStep.valueOf(feedStep));
				}
			} else {
				logger.info(
						"No feed steps have been specified. [Defualt run sequence: UpdateListings -> UpdateImages -> PruneListings -> UpdateSoldListings]");
				Main.feedSteps.add(FeedStep.valueOf("UpdateListings"));
				Main.feedSteps.add(FeedStep.valueOf("UpdateImages"));
				Main.feedSteps.add(FeedStep.valueOf("PruneListings"));
				Main.feedSteps.add(FeedStep.valueOf("UpdateSoldListings"));
			}

			// if we want to load from FILE, the look for the -f option and get name of file
			if (Main.cl.hasOption("m"))
				flatFileName = Main.cl.getOptionValue("m");

			// add mls nos via input or flat file
			if (Main.cl.hasOption("i") || Main.cl.hasOption("m")) {

				String[] mlsNos = null;

				// do logic to see if flat file is going to be used.
				if (flatFileName != null && !flatFileName.isEmpty()) {
					File file = new File(flatFileName);
					if (file == null)
						throw new FileNotFoundException();
					String mlsstring = null;//MlsUtility.getMlsNumbersStringFromFile(file);
					mlsNos = mlsstring.split(",");
				} else if (Main.cl.hasOption("i")) {
					mlsNos = Main.cl.getOptionValue("i", "").split(",");
				} // End if/else

				int maxLen = 60001; // Hardcoded for safety. Limit the maximum number of listings that can be updated from the CLI to
									// prevent performance-expensive subqueries on the MLS provider's server.

				if (mlsNos.length > maxLen)
					throw new Exception("Maximum length of " + maxLen
							+ " numbers in the single update list exceeded. Do not pass more than this number of listing numbers. This is a safety measure.");

				Main.mlsNumbers = new ArrayList<Integer>();
				for (String mlsNo : mlsNos) {
					try {
						Main.mlsNumbers.add(Integer.parseInt(mlsNo.trim()));
					} catch (Exception e) {
						// Throw exception, but continue.
						logger.error("An error ocurred parsing list of single MLS numbers to update", e);
					}
				}

				logger.trace("Value of mls numbers: " + Main.mlsNumbers);
			}

			// if the verbose option is specified, change logging
			if (Main.cl.hasOption("v")) {
				// org.apache.log4j.Logger.getLogger(NH.class).setLevel(Level.TRACE);
				// org.apache.log4j.Logger.getLogger(MA.class).setLevel(Level.TRACE);
				// org.apache.log4j.Logger.getLogger(MreisRetsFeed.class).setLevel(Level.TRACE);
				// org.apache.log4j.Logger.getLogger(Main.class).setLevel(Level.TRACE);
				// org.apache.log4j.Logger.getLogger(NnerenRetsFeed.class).setLevel(Level.TRACE);
				// org.apache.log4j.Logger.getLogger(MlsPinRetsFeed.class).setLevel(Level.TRACE);
				// org.apache.log4j.Logger.getLogger(NnerenRetsSoldFeed.class).setLevel(Level.TRACE);
			}

			// get the dry run option
			Main.dryRun = Main.cl.hasOption("d");
			logger.trace("Value of dryRun: " + dryRun);

			// get the limit option
//			Main.limit = Utility.parseInt(Main.cl.getOptionValue("l", "0"));
			logger.trace("Value of limit: " + Main.limit);

			// get the force option
			Main.force = Main.cl.hasOption("u");
			logger.trace("Value of force: " + Main.force);
		} catch (Exception ex) {
			logger.error("An error ocurred parsing command line arguments", ex);
		}
	}
}
