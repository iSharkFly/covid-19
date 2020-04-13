package com.ossez.covid19.service;

import com.ossez.covid19.common.mls.feeds.FeedException;
import com.ossez.covid19.common.mls.feeds.FeedStep;
import com.ossez.covid19.common.mls.feeds.ListingFeed;

import java.util.List;
import java.util.Properties;

public class NH implements ListingFeed {
	@Override
	public void run(List<FeedStep> list, String s) throws FeedException {

	}

	@Override
	public void setProperties(Properties properties) {

	}

	@Override
	public void setLimit(int i) {

	}

	@Override
	public void setDryRun(boolean b) {

	}

	@Override
	public void setForce(boolean b) {

	}

	@Override
	public void setMlsNumbers(List<Integer> list) {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getUpdateStatus(String s) {
		return null;
	}

	@Override
	public void addProgressMessage(String s, String s1) {

	}


//	private Logger log = LoggerFactory.getLogger(NH.class);
//	private boolean dryRun = false;
//	private int limit = 0;
//	private Properties properties = null;
//	private Connection nnerenConnection = null;
//	private boolean force = false;
//	private static Pattern properCasePattern = Pattern.compile("(\\w)(\\w+)([\\s\\/\\+]+)?");
//	private Map<ListingType, Map<String, FeatureValue>> featureCache = new HashMap<ListingType, Map<String, FeatureValue>>();
//	private Map<String, Town> townCache = new HashMap<String, Town>();
//	private Map<String, Style> styleCache = new HashMap<String, Style>();
//	private Map<String, Agent> agentCache = new HashMap<String, Agent>();
//	private Map<String, County> countyCache = new HashMap<String, County>();
//	private int newListings = 0;
//	private int updatedListings = 0;
//	private int newPhotos = 0;
//	private int updatedPhotos = 0;
//	private Feed feed = null;
//	private Map<String,String> statusMessages = new HashMap<String,String>();
//	private List<Integer> mlsNumbers = new ArrayList<Integer>();
//
//	/* (non-Javadoc)
//	 * @see com.verani.common.mls.idx.ListingFeed#setDryRun(boolean)
//	 */
//	@Override
//	public void setDryRun(boolean dryRun) {
//		this.dryRun = dryRun;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.verani.common.mls.idx.ListingFeed#setLimit(int)
//	 */
//	@Override
//	public void setLimit(int limit) {
//		this.limit = limit;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.verani.common.mls.idx.ListingFeed#setProperties(java.util.Properties)
//	 */
//	@Override
//	public void setProperties(Properties properties) {
//		this.properties = properties;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.verani.common.mls.idx.ListingFeed#setForce(boolean)
//	 */
//	@Override
//	public void setForce(boolean force) {
//		this.force = force;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.verani.common.mls.idx.ListingFeed#getName()
//	 */
//	@Override
//	public String getName() {
//		return "NH";
//	}
//
//	/* (non-Javadoc)
//	 * @see com.verani.common.mls.idx.ListingFeed#setMlsNumber(int)
//	 */
//	@Override
//	public void setMlsNumbers(List<Integer> mlsNumbers) {
//		this.mlsNumbers = mlsNumbers;
//	}
//
//	/**
//	 * Gets the set list of mls numbers that this feed will update.
//	 * @return
//	 */
//	public List<Integer> getMlsNumbers() {
//		return mlsNumbers;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.verani.common.mls.idx.ListingFeed#run(java.util.List)
//	 */
//	@Override
//	public void run(List<FeedStep> steps, String progressToken) throws FeedException {
//		this.log.info("Starting NH feed");
//		this.log.debug("Initializing hibernate");
//
//		// check to see if a feed exists in the db
//		Factory.beginTransaction();
//
//		this.feed = FeedFactory.get("NNEREN");
//
//		if (this.feed == null) {
//			this.feed = new Feed("NNEREN", "New hampshire nneren feed");
//			FeedFactory.save(this.feed);
//		}
//
//		if (this.dryRun)
//			Factory.rollbackTransaction();
//		else
//			Factory.commitTransaction();
//
//		try {
//			this.log.debug("Connecting to nneren");
//			// connect to the database
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			this.nnerenConnection = DriverManager.getConnection(this.properties.getProperty("nnerenConnectionUrl"));
//		} catch (Exception ex) {
//			this.log.error("Error connecting to nneren database", ex);
//		}
//
//		// execute the specified steps
//		for (FeedStep step : steps) {
//			switch (step) {
//			case UpdateListings:
//				this.updateListings();
//				break;
//			case UpdateImages:
//				this.updateImages(progressToken);
//				break;
//			case PruneListings:
//				this.pruneListings();
//				break;
//			case UpdateFeatures:
//				this.updateFeatures();
//				break;
//			case UpdateTowns:
//				this.updateTowns();
//				break;
//			case UpdateListing:
//				this.updateListing(this.mlsNumbers);
//				break;
//			case UpdateOfficeMlsNumbers:
//				this.updateOfficeMlsNumbers();
//				break;
//			}
//		}
//
//		try {
//			this.log.debug("Disconnecting from nneren");
//			// disconnect
//			this.nnerenConnection.close();
//			this.log.debug("Disconnected from nneren");
//		} catch (SQLException ex) {
//			this.log.error("Error disconnecting from nneren database", ex);
//		}
//	}
//
//	private void updateOfficeMlsNumbers() {
//		this.updateOfficeMlsNumbers("RES");
//		this.updateOfficeMlsNumbers("LAND");
//		this.updateOfficeMlsNumbers("COM");
//		this.updateOfficeMlsNumbers("MLT");
//	}
//
//	private void updateOfficeMlsNumbers(String type) {
//		this.log.info("Updating office mls numbers for " + type);
//		try {
//			Statement statement = this.nnerenConnection.createStatement();
//			ResultSet result = statement.executeQuery("select [MLS Number], [List Office] from IDX3_" + type + " (nolock);");
//			while (result.next()) {
//				Factory.beginTransaction();
//
//				Listing listing = ListingFactory.get(result.getInt("MLS Number"));
//				if (listing != null) {
//					if (listing.getAgent() != null && listing.getStatus() != ListingStatus.Closed && Utility.isEmpty(listing.getOfficeMlsId())) {
//						this.log.debug("Processing listing " + result.getInt("MLS Number"));
//						listing.setOfficeMlsId(result.getString("List Office"));
//						ListingFactory.save(listing);
//					}
//				}
//
//				Factory.commitTransaction();
//			}
//
//			result.close();
//			statement.close();
//		} catch (SQLException ex) {
//			this.log.error("Error updating office mls numbers.", ex);
//		}
//	}
//
//	private void updateListing(List<Integer> mlsNumbers) {
//		try {
//			Factory.beginTransaction();
//
//			// load the feature cache
//			this.initFeatureCache();
//			this.initTownCache();
//			this.initStyleCache();
//			this.initAgentCache();
//
//			if (this.dryRun)
//				Factory.rollbackTransaction();
//			else
//				Factory.commitTransaction();
//
////			File file = new File("D:\\mlsNumbers.txt");
////
////			mlsNumbers = MlsUtility.getMlsNumbersFromFile(file);
//			String mlsNos = StringUtils.join(mlsNumbers,",");
//
//
//			this.log.info("Updating Listing(s) #: " + mlsNos);
//
//			Statement statement = this.nnerenConnection.createStatement();
//			ResultSet result = statement.executeQuery("select * from IDX3_RES (nolock) where [MLS" +
//					" Number] IN (" + mlsNos + ")");
//			while (result.next()) {
//				this.updateListing(result, "RES");
//			}
//
//			result.close();
//			statement.close();
//
//			statement = this.nnerenConnection.createStatement();
//			result = statement.executeQuery("select * from IDX3_MLT (nolock) where [Mls Number] IN (" + mlsNos + ")");
//			while (result.next()) {
//				this.updateListing(result, "MLT");
//			}
//
//			result.close();
//			statement.close();
//
//			statement = this.nnerenConnection.createStatement();
//			result = statement.executeQuery("select * from IDX3_COM (nolock) where [Mls Number] IN (" + mlsNos + ")");
//			while (result.next()) {
//				this.updateListing(result, "COM");
//			}
//
//			result.close();
//			statement.close();
//
//			statement = this.nnerenConnection.createStatement();
//			result = statement.executeQuery("select * from IDX3_LAND (nolock) where [Mls Number] IN (" + mlsNos + ")");
//			while (result.next()) {
//				this.updateListing(result, "LAND");
//			}
//
//			result.close();
//			statement.close();
//		} catch (Exception ex) {
//			this.log.error("Error processing Listing(s) #: " + StringUtils.join(mlsNumbers,",") + " listings", ex);
//			Factory.rollbackTransaction();
//		}
//	}
//
//	private void updateTowns() {
//		this.log.info("Updating towns");
//		Factory.beginTransaction();
//
//		try {
//			Statement statement = this.nnerenConnection.createStatement();
//			ResultSet result = statement.executeQuery(
//					"select distinct town, county, state from IDX3_RES (nolock) union " +
//					"select distinct town, county, state from IDX3_MLT (nolock) union " +
//					"select distinct town, county, state from IDX3_LAND (nolock) union " +
//					"select distinct town, county, state from IDX3_COM (nolock);"
//			);
//
//			// loop through each town
//			while (result.next()) {
//				String name = result.getString("town").trim();
//				String state = result.getString("state").trim();
//				String countyName = result.getString("county").trim();
//				countyName = countyName.substring(0, countyName.length() - 2);
//
//				// get the county
//				County county = CountyFactory.get(countyName, state);
//
//				if (county == null) {
//					county = new County(countyName, state);
//					CountyFactory.save(county);
//					this.log.trace("Adding county: " + countyName + ", " + state);
//				}
//
//				// get the town from the database
//				Town town = TownFactory.get(name, state);
//
//				if (town == null)
//					town = new Town(name, state, county);
//				else
//					town.setCounty(county);
//
//				this.log.trace("Updating town: " + name + ", " + state + " with county " + countyName);
//
//				TownFactory.save(town);
//			}
//
//			result.close();
//			statement.close();
//
//			if (this.dryRun)
//				Factory.rollbackTransaction();
//			else
//				Factory.commitTransaction();
//		} catch (Throwable ex) {
//			this.log.error("Error processing towns", ex);
//
//			if (Factory.getSession().getTransaction().isActive())
//				Factory.rollbackTransaction();
//		}
//	}
//
//	private void pruneListings() {
//		List<Integer> mlsNumbers = new ArrayList<Integer>();
//
//		try {
//			// Sanity check must pass to continue.
//			if(checkKeeplistSanityLevel() == false)
//				return;
//
//			// normal run.
//			this.log.info("Getting listings from NNEREN.");
//			Statement statement = this.nnerenConnection.createStatement();
//			ResultSet result = statement.executeQuery("select LM_MST_MLS_NO from IDX3_KEEPLIST (nolock);");
//
//			while (result.next()) {
//				mlsNumbers.add(result.getInt("LM_MST_MLS_NO"));
//			}
//
//			result.close();
//			statement.close();
//
//			this.log.info("Getting listings from our database.");
//
//			// now update any withdrawn listings
//			Factory.beginTransaction();
//			List<Integer> currentMlsNumbers = ListingFactory.getMlsNumbersByFeed(this.feed, 0);
//			Factory.commitTransaction();
//
//			int count = 0;
//
//			this.log.info("Updating withdrawn listings.");
//
//			for (int mlsNumber : currentMlsNumbers) {
//				if (!mlsNumbers.contains(mlsNumber)) {
//					try {
//						Factory.beginTransaction();
//
//						Listing listing = ListingFactory.get(mlsNumber);
//						listing.setStatus(ListingStatus.Withdrawn);
//						listing.setDelisted(new Date());
//						listing.setUpdated(new Date());
//
//						ListingFactory.save(listing);
//
//						Factory.commitTransaction();
//					} catch (Exception ex) {
//						Factory.rollbackTransaction();
//						this.log.error("Could not update listing " + mlsNumber, ex);
//					}
//
//					count++;
//				}
//			}
//
//			this.log.info("Marked " + count + " listings as withdrawn.");
//			this.log.info("Updating active listings.");
//
//			count = 0;
//
//			// now make sure everything in the keep list is also active on our site
//			for (int mlsNumber : mlsNumbers) {
//				if (!currentMlsNumbers.contains(mlsNumber)) {
//					try {
//						Factory.beginTransaction();
//
//						Listing listing = ListingFactory.get(mlsNumber);
//
//						if (listing != null) {
//							listing.setStatus(ListingStatus.Active);
//							listing.setUpdated(new Date());
//
//							ListingFactory.save(listing);
//
//							count++;
//						}
//
//						if(this.dryRun)
//							Factory.rollbackTransaction();
//						else
//							Factory.commitTransaction();
//					} catch (Exception ex) {
//						Factory.rollbackTransaction();
//						this.log.error("Could not update listing " + mlsNumber, ex);
//					}
//				}
//			}
//
//			this.log.info("Marked " + count + " listings as active.");
//		} catch (SQLException ex) {
//			this.log.error("Error pruning listings.", ex);
//
//			if (Factory.getSession().getTransaction().isActive())
//				Factory.rollbackTransaction();
//		}
//	}
//
//	private void updateImages(String progressToken) {
//		this.log.info("Updating listing images.");
//		Date startTime = new Date();
//
//		if (this.mlsNumbers == null || this.mlsNumbers.size() == 0) {
//			Factory.beginTransaction();
//			// get all active listings for this feed
//			List<Integer> listings = ListingFactory.getMlsNumbersByFeed(this.feed, this.limit);
//			Factory.commitTransaction();
//
//			this.updateImages(listings, progressToken);
//		} else {
//			this.updateImages(this.mlsNumbers, progressToken);
//		}
//
//		String diffTime = Integer.toString(DateUtility.diffMinutes(new Date(), startTime));
//
//		this.log.info("Added " + this.newPhotos);
//		this.log.info("Updated " + this.updatedPhotos);
//		this.log.info("Took " + diffTime + " minutes");
//		this.addProgressMessage(progressToken, "Added " + this.newPhotos + "\n");
//		this.addProgressMessage(progressToken, "Updated " + this.updatedPhotos + "\n");
//		this.addProgressMessage(progressToken, "Took " + diffTime + " minutes\n");
//	}
//
//	/**
//	 * This goes through the given list and updates images for each one.
//	 *
//	 * In the interest of not making a query that is too long, this queries the NNEREN server on a listing-by-listing basis instead of getting the info all at once.
//	 * @param mlsNumbers
//	 * @param progressToken
//	 */
//	private void updateImages(List<Integer> mlsNumbers, String progressToken) {
//		for(Integer l: mlsNumbers)
//		{
//			Factory.beginTransaction();
//			try {
//				Date updated = null;
//				int photoCount = 0;
//				Listing listing = ListingFactory.get(l.intValue());
//
//				if(listing == null){
//					this.log.error("MLS number not found in our database: " + l.toString());
//					continue;
//				}
//
//				// if the listing has custom photos, do not update the photos
//				if (listing.isCustomPhotos()){
//					this.log.info("The listing #" + l.toString() + " is flagged as having custom photos in our database, skipping update.");
//					this.addProgressMessage(progressToken, "The listing #" + l.toString() + " is flagged as having custom photos in our database, skipping update.");
//					continue;
//				}
//
//				this.log.trace("Checking photos for listing " + l.toString());
//				// get the updated date from nneren
//				Statement statement = this.nnerenConnection.createStatement();
//				ResultSet result = statement.executeQuery("select p_mod_date_time, Photos from IDX3_" + this.translateType(listing.getType()) + " (nolock) where [MLS Number] = " + l.toString() + ";");
//				if (result.next()) {
//					updated = result.getTimestamp("p_mod_date_time");
//					photoCount = result.getInt("Photos");
//				}
//				result.close();
//				statement.close();
//
//				// check if the images have been updated
//				if (listing.getPhotoUpdated() == null
//						|| (updated != null && listing.getPhotoUpdated().before(updated))
//						|| (photoCount > 0 && listing.getPhotoCount() < photoCount) || this.force) {
//
//					this.log.trace("Updating photos for listing " + listing.getMlsNumber());
//					this.addProgressMessage(progressToken, "Updating photos for listing " + listing.getMlsNumber() + ". " + photoCount + " photos in the MLS.\n");
//
//					// get the photos now
//					statement = this.nnerenConnection.createStatement();
//					result = statement.executeQuery("select * from IDX_IMAGES_FULL (nolock) where mls_no = " + l.toString() + ";");
//
//					// we have to maintain a separate index because innovia sometimes throws in a null in the middle of the list of photos
//					int photoIndex = 0;
//
//					if (result.next()) {
//						// loop through each image
//						for (int i = 1; i <= 24; i++) {
//							byte [] imageData = result.getBytes("image" + i);
//
//							if (imageData != null) {
//								// get the current image
//								ListingPhoto photo = listing.getPhoto(photoIndex);
//
//								// if the photo was not found create it
//								if (photo == null) {
//									this.log.trace("Adding photo " + photoIndex);
//									this.addProgressMessage(progressToken, "Adding photo " + photoIndex + "\n");
//
//									photo = new ListingPhoto();
//									photo.setImageNumber(photoIndex);
//									listing.addPhoto(photo);
//									this.newPhotos++;
//								} else {
//									this.log.trace("Updating photo " + photoIndex);
//									this.addProgressMessage(progressToken, "Updating photo " + photoIndex + "\n");
//									this.updatedPhotos++;
//								}
//
//								// update the photo
//								photo.setImage(imageData);
//								photo.setUpdated(new Date());
//								photo.setCaption("");
//
//								photoIndex++;
//							}
//						}
//					}
//
//					result.close();
//					statement.close();
//					// update the photo updated column
//					listing.setPhotoUpdated(new Date());
//					// mark the listing as updated so the indexer will pick up the potential change to the photo count
//					listing.setUpdated(new Date());
//					// update the listing which will save all of the photos
//					ListingFactory.save(listing);
//				}
//
//				if (this.dryRun)
//					Factory.rollbackTransaction();
//				else
//					Factory.commitTransaction();
//			} catch (Exception ex) {
//				Factory.rollbackTransaction();
//				this.log.error("Error processing images for listing: " + l.toString(), ex);
//				this.addProgressMessage(progressToken, "Error processing images for listing: " + l.toString() + "\n");
//			}
//		}
//	}
//
//	/**
//	 * Updates all listings.
//	 * @throws FeedException
//	 */
//	private void updateListings() throws FeedException {
//		this.log.info("Updating listing data");
//
//		Factory.beginTransaction();
//
//		// load the feature cache
//		this.initFeatureCache();
//		this.initTownCache();
//		this.initStyleCache();
//		this.initAgentCache();
//
//		if (this.dryRun)
//			Factory.rollbackTransaction();
//		else
//			Factory.commitTransaction();
//
//		// process all listings
//		this.updateListings("RES");
//		this.updateListings("MLT");
//		this.updateListings("LAND");
//		this.updateListings("COM");
//
//		this.log.info("Updated " + this.updatedListings + " listings\nAdded " + this.newListings + " listings");
//	}
//
//	private void initAgentCache() {
//		this.log.debug("Initializing agent cache");
//		List<AgentMlsNumber> mlsNumbers = AgentFactory.getAllMlsNumbers();
//		for (AgentMlsNumber mlsNumber : mlsNumbers) {
//			this.agentCache.put(mlsNumber.getMlsNumber() + "_" + mlsNumber.getState(), mlsNumber.getAgent());
//		}
//	}
//
//	private void initTownCache() {
//		this.log.debug("Initializing town cache");
//		List<Town> towns = TownFactory.getAll();
//
//		for (Town town : towns) {
//			this.townCache.put(town.getName() + "_" + town.getState(), town);
//		}
//
//		// get counties
//		List<County> counties = CountyFactory.getAll();
//
//		for (County county : counties) {
//			this.countyCache.put(county.getName() + "_" + county.getState(), county);
//		}
//	}
//
//	private void initStyleCache() {
//		this.log.debug("Initializing style cache");
//		List<Style> styles = StyleFactory.getAll();
//
//		for (Style style : styles) {
//			this.styleCache.put(style.getName(), style);
//		}
//	}
//
//	/**
//	 * Updates the listings for the specified listing type.
//	 * @param listingType
//	 */
//	private void updateListings(String listingType) {
//		this.log.info("Updating " + listingType + " listings");
//
//		try {
//			// get the listings for this type
//			Statement statement = this.nnerenConnection.createStatement();
//			ResultSet result = statement.executeQuery("select" + (this.limit > 0 ? " top " + this.limit : "") + " * from IDX3_" + listingType + " (nolock);");
//			while (result.next()) {
//				this.updateListing(result, listingType);
//			}
//
//			result.close();
//			statement.close();
//		} catch (SQLException ex) {
//			this.log.error("Error processing " + listingType + " listings", ex);
//			Factory.rollbackTransaction();
//		}
//	}
//
//	/**
//	 * Updates a listing based on the data in the current row of the specified result set.
//	 * @param result
//	 */
//	private void updateListing(ResultSet result, String listingType) {
//		int mlsNumber = 0;
//
//		try {
//			Factory.beginTransaction();
//
//			mlsNumber = result.getInt("MLS Number");
//			this.log.trace("Checking listing " + mlsNumber);
//
//			// check to see if the listing exists
//			Listing listing = ListingFactory.get(mlsNumber);
//
//			// check to see if the listing needs to be updated
//			if (listing == null || listing.getMlsUpdated().compareTo(result.getTimestamp("mod_date_time")) == -1 || this.force) {
//				Town town = this.getTown(result.getString("Town"), result.getString("State"), result.getString("County"));
//
//				// make sure that the town was either found or created, if the town is null it means either the town name or state was blank
//				if (town != null) {
//					// if the listing does not exist add it
//					if (listing == null) {
//						listing = new Listing();
//						listing.setAdded(new Date());
//						this.newListings++;
//					} else {
//						// check to see if the price has changed
//						if (listing.getListPrice() != result.getFloat("List Price"))
//							listing.setPriceChanged(new Date());
//
//						// check to see if the listing is back on the market
//						if (listing.getStatus() == ListingStatus.Withdrawn && "A".equals(result.getString("Status")))
//							listing.setBackOnMarket(new Date());
//
//						// if the listing is going from any status but active, trigger a photo update
//						if (listing.getStatus() != ListingStatus.Active && "A".equals(result.getString("Status")))
//							listing.setPhotoUpdated(null);
//
//						this.updatedListings++;
//					}
//
//					// set the listing type
//					if (listingType.equals("RES")) {
//						if (result.getString("Property Type").equals("T"))
//							listing.setType(ListingType.Condominium);
//						else if (result.getString("Property Type").equals("O"))
//							listing.setType(ListingType.MobileHome);
//						else
//							listing.setType(ListingType.Residential);
//					} else if (listingType.equals("COM")) {
//						listing.setType(ListingType.Commercial);
//					} else if (listingType.equals("LAND")) {
//						listing.setType(ListingType.Land);
//					} else if (listingType.equals("MLT")) {
//						listing.setType(ListingType.MultiFamily);
//					}
//
//					// set all of the properites
//					listing.setMlsNumber(mlsNumber);
//					listing.setListerOfficeName(result.getString("Office Name"));
//					listing.setListerAgentName(result.getString("Agent Name"));
//					listing.setOfficeMlsId(result.getString("List Office"));
//
//					// get the features
//					this.updateListingFeatures(result, listing);
//
//					// set properties for everything except commercial
//					if (listing.getType() != ListingType.Commercial) {
//						listing.setWaterFront(result.getDouble("Water Frnt Ft") > 0);
//						listing.setHighSchool(result.getString("High School"));
//						listing.setJrHighSchool(result.getString("Jr. High/Middle School"));
//						listing.setElementarySchool(result.getString("Elementary School"));
//						listing.setSchoolDistrict(result.getString("District"));
//					}
//
//					// street number
//					if (listing.getType() == ListingType.Land)
//						listing.setStreetNumber(result.getString("Lot Number"));
//					else
//						listing.setStreetNumber(result.getString("House Number"));
//
//					listing.setStreetName(result.getString("Street Name"));
//
//					listing.setFeed(this.feed);
//					listing.setState(result.getString("State"));
//					listing.setZip(result.getString("Zip Code"));
//					listing.setAgentMlsId(result.getString("List Agent") == null ? "" : result.getString("List Agent").trim());
//
//					// get the agent
//					if (result.getString("List Agent") != null)
//						listing.setAgent(this.agentCache.get(result.getString("List Agent").trim() + "_NH"));
//
//					listing.setStatus(result.getString("Status").equals("K") ? ListingStatus.Contingent : ListingStatus.Active);
//					listing.setListPrice(result.getFloat("List Price"));
//					listing.setTaxes(result.getInt("Taxes"));
//					listing.setTown(town);
//					listing.setCounty(result.getString("County"));
//					listing.setLotSize(result.getFloat("LotSize Acres"));
//
//					// updated dates
//					listing.setUpdated(new Date());
//					listing.setMlsUpdated(result.getTimestamp("mod_date_time"));
//
//					// Filter out bad characters
//					String filterRegex = ConfigSettingFactory.getValue(ConfigSetting.FEED_DESCRIPTION_FILTER,ConfigSetting.FEED_DESCRIPTION_FILTER_DEFAULT);
//					filterRegex = filterRegex.replaceAll("[\\.\\*\\+\\?\\(\\)]", ""); // try to prevent somebody messing around.
//					String desc = null;
//					try {
//						 desc = result.getString("Public Remarks").replaceAll(filterRegex, "");
//					}
//					catch (Exception ex) {
//						log.error("NH Feeds Public Remarks format ERROR:" + ex);
//					}
////					String desc = result.getString("Public Remarks").replaceAll(filterRegex, "");
//					listing.setDescription(desc);
//
//					// style
//					// commercial and land have different feature names for style
//					String styleFeatureName = "Style";
//					if (listing.getType() == ListingType.Land || listing.getType() == ListingType.Commercial)
//						styleFeatureName = "Type";
//
//					listing.setStyle(this.getStyle(listing.getFeature(styleFeatureName), listing.getType()));
//
//					// residential specific properties
//					if (listing.getType() == ListingType.Residential || listing.getType() == ListingType.Condominium || listing.getType() == ListingType.MobileHome) {
//						listing.setBedrooms(result.getInt("Bedrooms") > 99 ? 99 : result.getInt("Bedrooms"));
//						int bathrooms = result.getInt("Full Baths") + result.getInt("3/4 Baths") + result.getInt("Half Bath");
//						listing.setBathrooms(bathrooms > 99 ? 99 : bathrooms);
//						listing.setFullBathrooms(result.getInt("Full Baths"));
//						listing.setRooms(result.getInt("Total Rooms"));
//						ListingFeature amenities = listing.getFeature("Amenities");
//						listing.setHasMasterBath(amenities != null && amenities.contains("Master BR with BA"));
//
//						// basement
//						ListingFeature basement = listing.getFeature("basement");
//						listing.setHasBasement(basement != null && !basement.valueEquals("none") && !basement.valueEquals("slab"));
//						listing.setFinishedSqft(result.getInt("Total Fin Sqft"));
//						listing.setYearBuilt(result.getInt("Apx Yr Built"));
//
//						// garage stuff
//						listing.setHasGarage(result.getString("LM_MST_WGAR") != null && (result.getString("LM_MST_WGAR").equals("Under") || result.getString("LM_MST_WGAR").equals("Attached") || result.getString("LM_MST_WGAR").equals("Detached")));
//
//						// adult community
//						ListingFeature adultFeature = listing.getFeature("Occupant Restrictions");
//						listing.setAdultCommunity(adultFeature != null && adultFeature.contains("Cert. Adult Park"));
//						listing.setSeasonal(Utility.equals(result.getString("Seasonal Y/N"), "Y"));
//					} else if (listing.getType() == ListingType.MultiFamily) {
//						int bathrooms = result.getInt("Unit 1 Bath") + result.getInt("Unit 2 Bath") + result.getInt("Unit 3 Bath") + result.getInt("Unit 4 Bath");
//						listing.setBathrooms(bathrooms > 99 ? 99 : bathrooms);
//						listing.setNumberOfUnits(result.getInt("Total Units"));
//
//						// basement
//						ListingFeature basement = listing.getFeature("basement");
//						listing.setHasBasement(basement != null && !basement.valueEquals("none") && !basement.valueEquals("slab"));
//					} else if (listing.getType() == ListingType.Commercial) {
//						listing.setFinishedSqft(result.getInt("Total Available Sqft"));
//						listing.setNumberOfUnits(result.getInt("Units#"));
//						listing.setYearBuilt(result.getInt("Apx Yr Built"));
//					}
//
//					// if the latitude / longitude has not been set update it
//					if (listing.getLatitude() == 0 || listing.getLongitude() == 0)
//						ListingFactory.updatePosition(listing);
//
//					// update details
//					this.updateDetails(result, listing);
//
//					// save the listing
//					ListingFactory.save(listing);
//				}
//			}
//
//			if (this.dryRun)
//				Factory.rollbackTransaction();
//			else
//				Factory.commitTransaction();
//		} catch (Exception ex) {
//			this.log.error("Error processing listing " + mlsNumber, ex);
//
//			if (Factory.getSession().getTransaction() != null && Factory.getSession().getTransaction().isActive())
//				Factory.rollbackTransaction();
//		}
//	}
//
//	private void updateListingFeatures(ResultSet result, Listing listing) throws SQLException {
//		String features = "";
//		int index = 0;
//
//		// get the coded features
//		if (listing.getType() == ListingType.Residential || listing.getType() == ListingType.Condominium || listing.getType() == ListingType.MobileHome)
//			features = result.getString("Coded Features");
//		else
//			features = result.getString("CFF");
//
//		// default to blank string
//		if (features == null)
//			features = "";
//
//		List<FeatureValue> newFeatures = new ArrayList<FeatureValue>();
//
//		// loop through each coded feature
//		while (index < features.length()) {
//			// try to get the feature value
//			FeatureValue feature = this.featureCache.get(listing.getType()).get(features.substring(index, index + 3));
//
//			// if the feature value was found add it to the listing features
//			if (feature != null) {
//				// merge the feature into the current session, this is necessary because the feature cache is loaded outside of the current session
//				feature = FeatureValueFactory.get(feature.getId());
//
//				newFeatures.add(feature);
//			}
//
//			index += 3;
//		}
//
//		// make sure the listing only has the features that come from the MLS
//		listing.setFeatureValues(newFeatures);
//	}
//
//	/**
//	 * Gets an existing town or creates a new one based on the name and state passed.
//	 * @param name
//	 * @param state
//	 * @return
//	 */
//	private Town getTown(String name, String state, String county) {
//		name = name.trim();
//		state = state.trim();
//
//		// see if the town exists
//		Town town = this.townCache.get(name + "_" + state);
//
//		// the town does not exist, create it
//		if (town == null) {
//			// try and get the town from the DB
//			town = TownFactory.get(name, state);
//
//			// town is brand new, add it
//			if (town == null && !Utility.isEmpty(name) && !Utility.isEmpty(state)) {
//				town = new Town(name, state, this.getCounty(county, state));
//				TownFactory.save(town);
//			}
//
//			this.townCache.put(name + "_" + state, town);
//		}
//
//		return town;
//	}
//
//	private County getCounty(String name, String state) {
//		name = name.trim().substring(0, state.length() - 2);
//		state = state.trim();
//
//		// see if the county exists
//		County county = this.countyCache.get(name + "_" + state);
//
//		// if the county does not exist, create it
//		if (county == null) {
//			// first see if it exists in the database
//			county = CountyFactory.get(name, state);
//
//			// county does not exist, create it
//			if (county == null) {
//				county = new County(name, state);
//				CountyFactory.save(county);
//
//				this.log.trace("Adding county: " + name + ", " + state);
//			}
//
//			this.countyCache.put(name + "_" + state, county);
//		}
//
//		return county;
//	}
//
//	/**
//	 * Gets the style based on the name and type.
//	 * @param styleName
//	 * @param type
//	 * @return
//	 */
//	private Style getStyle(ListingFeature styleFeature, ListingType type) {
//		Style style = null;
//
//		// make sure the name is not null
//		if (styleFeature == null) {
//			style = this.getStyle("Other");
//		} else {
//			String styleName = styleFeature.getFirstValue();
//
//			// normalize styles
//			if (type == ListingType.Residential || type == ListingType.Condominium) {
//				if (styleFeature.contains("1 story")) {
//					if (type == ListingType.Condominium)
//						styleName = "Ranch";
//					else
//						styleName = "Other";
//
//				} else if (styleFeature.contains("2 story") || styleFeature.contains("1 1/2 story") || styleFeature.contains("1 3/4 story") || styleFeature.contains("2 1/2 story") || styleFeature.contains("3 story") || styleFeature.contains("4 story"))
//					styleName = "Multi Level";
//			}
//
//			style = this.getStyle(styleName);
//		}
//
//		if (style == null)
//			return this.getStyle("Other");
//
//		return style;
//	}
//
//	/**
//	 * Ensures a style exists with the specified name.
//	 * @param styleName
//	 * @param type
//	 * @return
//	 */
//	private Style getStyle(String styleName) {
//		if (Utility.isEmpty(styleName))
//			return null;
//
//		// get the style from the cache
//		Style style = this.styleCache.get(styleName);
//
//		// no style exists, create one
//		if (style == null) {
//			// try and get it from the db
//			style = StyleFactory.get(styleName);
//
//			// style does not exist, create it
//			if (style == null) {
//				style = new Style(styleName);
//				StyleFactory.save(style);
//			}
//			// add to the cache
//			this.styleCache.put(styleName, style);
//		}
//
//		return style;
//	}
//
//	/**
//	 * Updates the details of a listing.
//	 * @param listing
//	 */
//	private void updateDetails(ResultSet result, Listing listing) {
//		try {
//			listing.addDetail("Lot", result.getString("Lot"));
//
//			if (listing.getType() != ListingType.Commercial) {
//				listing.addDetail("FloodZoneYN", result.getString("Flood Zone Y/N"));
//				listing.addDetail("SurveyedYN", result.getString("Surveyed Y/N"));
//				listing.addDetail("TaxYear", result.getString("Tax Yr"));
//			}
//
//			if (listing.getType() == ListingType.Residential || listing.getType() == ListingType.Condominium || listing.getType() == ListingType.MobileHome) {
//				listing.addDetail("GarageSize", result.getString("Garage Capacity"));
//				listing.addDetail("TotalRooms", result.getString("Total Rooms"));
//				listing.addDetail("Association Fee", result.getString("Condo Assoc Fees"));
//				listing.addDetail("Association Name", result.getString("Village/Project Name"));
//				listing.addDetail("FullBaths", result.getString("Full Baths"));
//				listing.addDetail("HalfBaths", result.getString("Half Bath"));
//				listing.addDetail("TQBaths", result.getString("3/4 Baths"));
//				listing.addDetail("MasterBedroomSize", result.getString("Master BR Size"));
//				listing.addDetail("Bedroom2Size", result.getString("2nd BR Size"));
//				listing.addDetail("Bedroom3Size", result.getString("3rd BR Size"));
//				listing.addDetail("Bedroom4Size", result.getString("4th BR Size"));
//				listing.addDetail("LivingRoomSize", result.getString("Living Rm Size"));
//				listing.addDetail("DiningRoomSize", result.getString("Dining Rm Size"));
//				listing.addDetail("KitchenSize", result.getString("Kitchen Size"));
//				listing.addDetail("FamilyRoomSize", result.getString("Family Room Size"));
//				listing.addDetail("DenSize", result.getString("Den Size"));
//				listing.addDetail("OtherRoomSize", result.getString("Other Rm Size"));
//				listing.addDetail("Assessment", result.getString("Assessment #"));
//				listing.addDetail("garageType", result.getString("LM_MST_WGAR"));
//				listing.addDetail("garageCapacity", Integer.toString(result.getInt("Garage Capacity")));
//
//				if (listing.getType() == ListingType.MobileHome) {
//					listing.addDetail("SerialNumber", result.getString("Serial #"));
//					listing.addDetail("MobileMake", result.getString("Mobile Make"));
//					listing.addDetail("MobileModel", result.getString("Model #"));
//					listing.addDetail("TransferFee", result.getString("Transfer Fee"));
//				}
//			} else if (listing.getType() == ListingType.Land) {
//				listing.addDetail("Deed", result.getString("Deed"));
//				listing.addDetail("LandType", result.getString("Property Type"));
//				listing.addDetail("EstOpenSpace", result.getString("Est Open Space %"));
//				listing.addDetail("Zoning", result.getString("Zoning"));
//			} else if (listing.getType() == ListingType.MultiFamily) {
//				listing.addDetail("ManagementExp", result.getString("Management Exp"));
//				listing.addDetail("MaintenanceExp", result.getString("Maintenance Exp"));
//				listing.addDetail("Unit1RentAmount", result.getString("Unit 1 Rent Amount"));
//				listing.addDetail("Unit2RentAmount", result.getString("Unit 2 Rent Amount"));
//				listing.addDetail("Unit3RentAmount", result.getString("Unit 3 Rent Amount"));
//				listing.addDetail("Unit4RentAmount", result.getString("Unit 4 Rent Amount"));
//				listing.addDetail("Unit1Bedrooms", result.getString("Unit 1 Bedrooms"));
//				listing.addDetail("Unit2Bedrooms", result.getString("Unit 2 Bedrooms"));
//				listing.addDetail("Unit3Bedrooms", result.getString("Unit 3 Bedrooms"));
//				listing.addDetail("Unit4Bedrooms", result.getString("Unit 4 Bedrooms"));
//				listing.addDetail("Unit1Bathrooms", result.getString("Unit 1 Bath"));
//				listing.addDetail("Unit2Bathrooms", result.getString("Unit 2 Bath"));
//				listing.addDetail("Unit3Bathrooms", result.getString("Unit 3 Bath"));
//				listing.addDetail("Unit4Bathrooms", result.getString("Unit 4 Bath"));
//				listing.addDetail("Unit1TotalRooms", result.getString("Unit 1 Total Rooms"));
//				listing.addDetail("Unit2TotalRooms", result.getString("Unit 2 Total Rooms"));
//				listing.addDetail("Unit3TotalRooms", result.getString("Unit 3 Total Rooms"));
//				listing.addDetail("Unit4TotalRooms", result.getString("Unit 4 Total Rooms"));
//				listing.addDetail("Unit1FloorLocation", result.getString("Unit 1 Floor Location"));
//				listing.addDetail("Unit2FloorLocation", result.getString("Unit 2 Floor Location"));
//				listing.addDetail("Unit3FloorLocation", result.getString("Unit 3 Floor Location"));
//				listing.addDetail("Unit4FloorLocation", result.getString("Unit 4 Floor Location"));
//				listing.addDetail("Unit1RentAgreement", result.getString("Unit 1 Rent Agreement"));
//				listing.addDetail("Unit2RentAgreement", result.getString("Unit 2 Rent Agreement"));
//				listing.addDetail("Unit3RentAgreement", result.getString("Unit 3 Rent Agreement"));
//				listing.addDetail("Unit4RentAgreement", result.getString("Unit 4 Rent Agreement"));
//				listing.addDetail("Unit1Deposit", result.getString("Unit 1 Deposit"));
//				listing.addDetail("Unit2Deposit", result.getString("Unit 2 Deposit"));
//				listing.addDetail("Unit3Deposit", result.getString("Unit 3 Deposit"));
//				listing.addDetail("Unit4Deposit", result.getString("Unit 4 Deposit"));
//				listing.addDetail("Unit1Tenant", result.getString("Unit 1 Tenant"));
//				listing.addDetail("Unit2Tenant", result.getString("Unit 2 Tenant"));
//				listing.addDetail("Unit3Tenant", result.getString("Unit 3 Tenant"));
//				listing.addDetail("Unit4Tenant", result.getString("Unit 4 Tenant"));
//				listing.addDetail("Unit1TenantPhone", result.getString("Unit 1 Tenant Phone"));
//				listing.addDetail("Unit2TenantPhone", result.getString("Unit 2 Tenant Phone"));
//				listing.addDetail("Unit3TenantPhone", result.getString("Unit 3 Tenant Phone"));
//				listing.addDetail("Unit4TenantPhone", result.getString("Unit 4 Tenant Phone"));
//			} else if (listing.getType() == ListingType.Commercial) {
//				listing.addDetail("Signage", result.getString("Signage"));
//				listing.addDetail("BuildingSqft", result.getString("Building Sqft"));
//				listing.addDetail("Floors", result.getString("Floors#"));
//				listing.addDetail("CeilingHeight", result.getString("Ceiling Height"));
//				listing.addDetail("DriveDoors", result.getString("Drive/Doors#"));
//				listing.addDetail("TrafficCount", result.getString("Traffic Count"));
//				listing.addDetail("NOI", result.getString("NOI"));
//				listing.addDetail("Insurance", result.getString("Insurance"));
//				listing.addDetail("Management", result.getString("Management"));
//				listing.addDetail("CAM", result.getString("CAM#"));
//				listing.addDetail("LeasePrice", result.getString("Lease Price"));
//				listing.addDetail("Parking", result.getString("Parking Spaces#"));
//				listing.addDetail("Zoning", result.getString("Zoning"));
//				listing.addDetail("BuildingSqft", result.getString("Building Sqft"));
//				listing.addDetail("Lease", result.getString("Lease /$"));
//			}
//		} catch (SQLException ex) {
//			this.log.error("Error processing details for listing " + listing.getMlsNumber(), ex);
//		}
//	}
//
//	/**
//	 * Gets all features from nneren.
//	 */
//	private void updateFeatures() {
//		this.log.debug("Updating features and values.");
//
//		try {
//			int count = 0;
//			Factory.beginTransaction();
//			Statement statement = this.nnerenConnection.createStatement();
//			ResultSet result = statement.executeQuery("select * from IDX_CFF (nolock);");
//			while (result.next()) {
//				this.updateFeature(result);
//
//				if (count % 20 == 0) {
//					Factory.getSession().flush();
//					Factory.getSession().clear();
//				}
//
//				count++;
//			}
//			result.close();
//			statement.close();
//
//			// commit or roll back the transaction
//			if (this.dryRun)
//				Factory.rollbackTransaction();
//			else
//				Factory.commitTransaction();
//
//		} catch (SQLException ex) {
//			this.log.error("Error loading initial features", ex);
//			Factory.rollbackTransaction();
//		}
//	}
//
//	private void updateFeature(ResultSet result) throws SQLException {
//		// normalize the name
//		String name = this.getFeatureNameMapping(properCase(result.getString("MM_CFH_FTR_TITL")).trim());
//		String value = result.getString("MM_CFD_LONG_DSC").trim();
//		Feature feature = FeatureFactory.getByName(name);
//
//		this.log.trace("Checking feature '" + name + "' with value of '" + value + "'");
//
//		// if the feature does not exist create it
//		if (feature == null) {
//			this.log.debug("Creating feature '" + name + "'");
//			feature = new Feature();
//			feature.setName(name);
//
//			FeatureFactory.save(feature);
//		}
//
//		// check the feature value
//		FeatureValue featureValue = FeatureValueFactory.get(feature, value);
//
//		// the feature value does not exist, create it
//		if (featureValue == null) {
//			this.log.debug("Creating feature value '" + value + "'");
//			FeatureValueFactory.save(new FeatureValue(feature, value));
//		}
//	}
//
//	private void initFeatureCache() throws FeedException {
//		try {
//
//			// initialize the cache map
//			for (ListingType type : ListingType.values()) {
//				this.featureCache.put(type, new HashMap<String, FeatureValue>());
//			}
//
//			Map<ListingType, Map<String, Feature>> features = this.initFeatureNameCache();
//			Map<Feature, Map<String, FeatureValue>> values = this.initFeatureValueCache(features);
//
//			Statement statement = this.nnerenConnection.createStatement();
//			// get all features from nneren
//			ResultSet result = statement.executeQuery("select * from IDX_CFF (nolock);");
//			while (result.next()) {
//				int type = result.getInt("MM_CFD_PROP_FMT");
//
//				// residential is a special case
//				if (type == 1) {
//					// loop through each residential type
//					for (ListingType listingType : new ListingType [] { ListingType.Residential, ListingType.Condominium, ListingType.MobileHome }) {
//						this.initFeatureCacheForType(result, listingType, features, values);
//					}
//				} else {
//					switch (type) {
//					case 2:
//						this.initFeatureCacheForType(result, ListingType.Land, features, values);
//						break;
//					case 3:
//						this.initFeatureCacheForType(result, ListingType.Commercial, features, values);
//						break;
//					case 4:
//						this.initFeatureCacheForType(result, ListingType.MultiFamily, features, values);
//						break;
//					}
//				}
//			}
//			result.close();
//			statement.close();
//		} catch (SQLException ex) {
//			this.log.error("Failed loading feature cache", ex);
//			throw new FeedException("Failed loading feature cache for NH feed.", ex);
//		}
//	}
//
//	private void initFeatureCacheForType(ResultSet result, ListingType listingType, Map<ListingType, Map<String, Feature>> features, Map<Feature, Map<String, FeatureValue>> values) throws SQLException {
//		// get the feature
//		Feature feature = features.get(listingType).get(this.getFeatureNameMapping(NH.properCase(result.getString("MM_CFH_FTR_TITL")).trim()));
//		// get the value
//		FeatureValue value = values.get(feature).get(result.getString("MM_CFD_LONG_DSC").trim());
//		// add to the cache
//		this.featureCache.get(listingType).put(result.getString("CFF"), value);
//	}
//
//	private String getFeatureNameMapping(String name) {
//		if (name.equals("Fuel"))
//			return "Heat Fuel";
//		else
//			return name;
//	}
//
//	private Map<ListingType, Map<String, Feature>> initFeatureNameCache() {
//		Map<ListingType, Map<String, Feature>> featureCache = new HashMap<ListingType, Map<String, Feature>>();
//		for (ListingType type : ListingType.values()) {
//			Map<String, Feature> features = featureCache.get(type);
//			features = new HashMap<String, Feature>();
//			featureCache.put(type, features);
//
//			// get features for this type
//			List<Feature> featureList = FeatureFactory.getAll();
//
//			for (Feature feature : featureList) {
//				features.put(feature.getName(), feature);
//			}
//		}
//
//		return featureCache;
//	}
//
//	private Map<Feature, Map<String, FeatureValue>> initFeatureValueCache(Map<ListingType, Map<String, Feature>> features) {
//		Map<Feature, Map<String, FeatureValue>> values = new HashMap<Feature, Map<String,FeatureValue>>();
//
//		// loop through all of the features
//		for (Map<String, Feature> featureMap : features.values()) {
//			for (Feature feature : featureMap.values()) {
//				// get all feature values for the feature
//				List<FeatureValue> featureValues = FeatureValueFactory.getByFeature(feature);
//
//				// create a map of the values
//				Map<String, FeatureValue> valueMap = new HashMap<String, FeatureValue>();
//				for (FeatureValue value : featureValues) {
//					valueMap.put(value.getValue(), value);
//				}
//				values.put(feature, valueMap);
//			}
//		}
//
//		return values;
//	}
//
//	public static String properCase(String input) {
//		Matcher matcher = properCasePattern.matcher(input);
//		StringBuffer result = new StringBuffer();
//
//		while (matcher.find()) {
//			result.append(matcher.group(1).toUpperCase() + matcher.group(2).toLowerCase() + matcher.group(3));
//		}
//
//		return result.toString();
//	}
//
//	private String translateType(ListingType type) {
//		switch (type) {
//		case Commercial:
//			return "COM";
//		case MultiFamily:
//			return "MLT";
//		case Land:
//			return "LAND";
//		default:
//			return "RES";
//		}
//	}
//
//	/**
//	 * Checks sanity of NNEREN keeplist. It sends emails as appropriate if it's not sane, or re-entered sanity.
//	 * @return
//	 */
//	private boolean checkKeeplistSanityLevel()
//	{
//		this.log.info("Checking sanity of NNEREN Keeplist");
//		Factory.beginTransaction();
//
//		try {
//			Statement statement2 = this.nnerenConnection.createStatement();
//			ResultSet result2 = statement2.executeQuery("select COUNT(LM_MST_MLS_NO) AS 'ActiveCount' from IDX3_KEEPLIST (nolock);");
//			int nnerenActiveCount = -1;
//			while (result2.next()) {
//				nnerenActiveCount = result2.getInt("ActiveCount");
//			}
//
//			result2.close();
//			statement2.close();
//
//			int veraniActiveCount = (Integer)Factory.createCriteria(Listing.class)
//				.add(Restrictions.eq("feed", this.feed))
//				.add(Restrictions.or(Restrictions.eq("status", ListingStatus.Active), Restrictions.eq("status", ListingStatus.Contingent)))
//				.setProjection(Projections.rowCount())
//				.uniqueResult();
//
//			// Check sanity.
//			int nnerenVeraniDiff = veraniActiveCount - nnerenActiveCount;
//			int nnerenPruneStopThreshold = ConfigSettingFactory.getInteger("nnerenPruneStopThreshold", "500");
//			boolean nnerenIsInsane = false; // we beg to differ.
//			// we don't check the other direction, which is when we have far less listings than NNEREN does. This is ok. It promotes healing I suppose.
//			if(nnerenActiveCount < veraniActiveCount && nnerenVeraniDiff >= nnerenPruneStopThreshold)
//			{
//				this.log.info("Keeplist is outside of sanity range. Difference is " + nnerenVeraniDiff + " listings.");
//				this.log.debug("Verani NNEREN active/contingents: " + veraniActiveCount);
//				this.log.debug("Innovia NNEREN active/contingents: " + nnerenActiveCount);
//				this.log.debug("Difference: " + nnerenVeraniDiff);
//				this.log.debug("nnerenPruneStopThreshold: " + nnerenPruneStopThreshold);
//				nnerenIsInsane = true; // not surprising.
//			}
//
//			// Abort if the prune listings step is already disabled.
//			boolean nnerenPruneDisabled = ConfigSettingFactory.getBoolean("nnerenPruneDisabled", false);
//			if(nnerenPruneDisabled){
//				this.log.info("PruneListings step is currently disabled. Aborting this step.");
//				this.log.info("To re-enable, delete the nnerenPruneDisabled config setting, or set it to false");
//				this.log.info("Sanity level is controlled by the nnerenPruneStopThreshold config setting, which is currently set to " + nnerenPruneStopThreshold);
//
//				// Send alert email that nneren keeplist appears ok now if so.
//				if(nnerenIsInsane == false){
//					// Send email to important person, with meaningful info on how to re-enable of course.
//					this.log.info("NNEREN Keeplist re-entered sane levels - difference is only "  + nnerenVeraniDiff + " listings. It should be re-enabled.");
//					MailMessage message = new MailMessage();
//					message.setFrom("webrequest@verani.com");
//					message.setSubject("NNEREN Keeplist re-entered sane levels - difference is only "  + nnerenVeraniDiff + " listings");
//					message.addTo(ConfigSettingFactory.getValue("webAlertEmail", "webrequest@verani.com"));
//
//					message.setBody(Calendar.getInstance().getTime().toString() + "\n\n" +
//							"Keeplist re-entered sane levels. Difference is only " + nnerenVeraniDiff + " listings.\n" +
//							"Verani NNEREN active/contingents: " + veraniActiveCount + "\n" +
//							"Innovia NNEREN active/contingents: " + nnerenActiveCount + "\n" +
//							"Difference: " + nnerenVeraniDiff + "\n" +
//							"nnerenPruneStopThreshold: " + nnerenPruneStopThreshold + "\n\n" +
//							"PruneListings step is currently disabled and not running, but it should be re-enabled.\n" +
//							"To re-enable, delete the nnerenPruneDisabled config setting, or set it to false.\n" +
//							"Sanity level is controlled by the nnerenPruneStopThreshold config setting, which is currently set to " + nnerenPruneStopThreshold);
//
//					message.send();
//
//					this.log.info("PruneListings step is currently disabled, but keeplist is within threshold. This step could be enabled now.");
//				}
//				Factory.commitTransaction(); //commits the emails.
//				return false;
//			}
//
//			// If keeplist diff is outside of sanity level, abort the process and set the config value that stops it to true.
//			if(nnerenIsInsane){
//				this.log.info("NNEREN Keeplist outside of threshold, disabling Prunelistings step.");
//				ConfigSettingFactory.save("nnerenPruneDisabled", "true", null, 15);
//
//				// Send email to important person, with meaningful info on how to re-enable of course.
//				MailMessage message = new MailMessage();
//				message.setFrom("webrequest@verani.com");
//				message.setSubject("PruneListings aborted - NNEREN Keeplist - difference is "  + nnerenVeraniDiff + " listings");
//				message.addTo(ConfigSettingFactory.getValue("webAlertEmail", "webrequest@verani.com"));
//
//				message.setBody(Calendar.getInstance().getTime().toString() + "\n\n" +
//						"Keeplist is outside of sanity range. Difference is " + nnerenVeraniDiff + " listings.\n" +
//						"Verani NNEREN active/contingents: " + veraniActiveCount + "\n" +
//						"Innovia NNEREN active/contingents: " + nnerenActiveCount + "\n" +
//						"Difference: " + nnerenVeraniDiff + "\n" +
//						"nnerenPruneStopThreshold: " + nnerenPruneStopThreshold + "\n\n" +
//						"PruneListings step is now disabled, and the step was aborted.\n" +
//						"To re-enable, delete the nnerenPruneDisabled config setting, or set it to false.\n" +
//						"Sanity level is controlled by the nnerenPruneStopThreshold config setting, which is currently set to " + nnerenPruneStopThreshold);
//
//				message.send();
//
//				// have to output message again b/c it wasn't disabled before.
//				this.log.info("PruneListings step is currently disabled. Aborting this step.");
//				this.log.info("To re-enable, delete the nnerenPruneDisabled config setting, or set it to false");
//				this.log.info("Sanity level is controlled by the nnerenPruneStopThreshold config setting, which is currently set to " + nnerenPruneStopThreshold);
//
//				Factory.commitTransaction(); //commits the emails.
//				return false;
//			}
//		} catch (Exception ex) {
//			this.log.error("Error while checking keeplist sanity level.", ex);
//
//			if (Factory.getSession().getTransaction().isActive())
//				Factory.rollbackTransaction();
//			return false;
//		}
//		Factory.commitTransaction();
//		return true;
//	}
//
//	@Override
//	public String getUpdateStatus(String progressToken) {
//		return this.statusMessages.get(progressToken);
//	}
//
//	@Override
//	public void addProgressMessage(String progressToken, String message) {
//		if(!Utility.isEmpty(progressToken)){
//			message = ((this.statusMessages.get(progressToken) != null) ? this.statusMessages.get(progressToken) : "")  + message;
//			this.statusMessages.put(progressToken, message);
//		}
//	}
}
