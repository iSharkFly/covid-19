package com.ossez.covid19.service.cloud;
//package com.verani.feeds.mls.cloud;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Properties;
//import java.util.UUID;
//
//import org.apache.log4j.Logger;
//
//import com.bigllc.retsiq.simpleclient.ObjectRecord;
//import com.bigllc.retsiq.simpleclient.RETSConnection;
//import com.bigllc.retsiq.simpleclient.RETSUserSession;
//import com.verani.common.Factory;
//import com.verani.common.backPage.factories.ConfigSettingFactory;
//import com.verani.common.cloud.CloudFiles;
//import com.verani.common.mls.Listing;
//import com.verani.common.mls.ListingType;
//import com.verani.common.mls.factories.FeedFactory;
//import com.verani.common.mls.factories.ListingFactory;
//import com.ossez.reoc.mls.rets.NnerenRetsFeed;
//
///**
// * This class will bring us up-to-speed with listing photos in the Cloud.
// * The photos will be pulled directly from corresponding RETS feed and then
// * stored in Rackspace's Cloud Files CDN (and we will use SSL URL to avoid SSL mixed content).
// * 
// * @author 	Mark Kelleher (mark.kelleher@verani.com)
// * @date	Sep 24, 2014
// * @jira	BVR-190
// *
// */
//public class CloudPhotos {
//
//	private static Logger log = Logger.getLogger(CloudPhotos.class);
//	private static final String PHOTO_PROCESS_LOG_FILE_NAME = "processed-listings.log";
//	private static Properties properties = new Properties();
//	
//	/**
//	 * Main command line method that will do all the dirty work.
//	 * 
//	 * @author 	Mark Kelleher (mark.kelleher@verani.com)
//	 * @date	Sep 24, 2014
//	 *
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
//		String tmpImageStorageLocationOnServer = "", path = "", imageStorageLocationInCloudFiles = "";
//		Map<Integer, Integer> listingPhotos = new HashMap<Integer, Integer>();
//		List<Integer> mlsNumbersAlreadyProcessed = null;
//		
//		String feedTypeToProcess = "NNEREN";
//		int processLimit = 0;
//		
//		// if passing argument, then set feed type
//		if( args[0]!=null && !args[0].isEmpty() )
//			feedTypeToProcess = args[0];
//		// if passing LIMIT number to process, then set it.
//		if( args[1]!=null && !args[1].isEmpty() )
//			processLimit = Integer.parseInt(args[1]);
//		
//		log.info("Beginning pulling "+(processLimit==0?"ALL":String.valueOf(processLimit))+" active "+feedTypeToProcess+" images from CDN to host in Rackspace Cloud Files.");
//		
//		try{
//			
//			// load properties file
//			properties.load(CloudPhotos.class.getClassLoader().getResourceAsStream(feedTypeToProcess+".properties"));
//			
//			// Setup some vars
//			Listing listing 		= null;
//			String listingImagePath = "";
//			int totalPhotoCount 	= 0;
//			int counter				= 0;
//			
//			String loginurl 			= properties.getProperty("rets.url");
//			String username 			= properties.getProperty("rets.user");
//			String password 			= properties.getProperty("rets.password");
//			String objectPath 			= properties.getProperty("rets.objectPath");
//			String requestPath			= "";
//			File directory 				= null;
//			List<ObjectRecord> objects 	= null;
//
//			// open database connection			
//			Factory.beginTransaction();
//			
//			// set the temp location where to store the files on server
//			path = ConfigSettingFactory.getValue("tmpListingImagePath", "/tmp/cloud-images/");
//			tmpImageStorageLocationOnServer = path + UUID.randomUUID().toString()+"/";
//			
//			// set the destination Cloud location
//			imageStorageLocationInCloudFiles = ConfigSettingFactory.getValue("listingImagePathCloudFiles", "localhost-images");
//			
//			// get list of all active or active w/ contract NNEREN and MLSPin listings from db
//			List<Integer> mlsNumbers 		= null;
//			
//			// go get the mlsnumbers for the correct feed
//			mlsNumbers = ListingFactory.getMlsNumbersByFeed(FeedFactory.get(feedTypeToProcess),0);
//
//			log.info("Found "+NumberFormat.getNumberInstance(Locale.US).format(mlsNumbers.size())+" listings to images pull for.");
//			
//			// check to see if log file exists (used to exclude MLS#s already processed)
//			File logFile = new File(path+PHOTO_PROCESS_LOG_FILE_NAME);
//			if(logFile.exists() && !logFile.isDirectory()){
//				mlsNumbersAlreadyProcessed = processLogFile(logFile);
//				log.info("Heads up! Log file found that "+NumberFormat.getNumberInstance(Locale.US).format(mlsNumbersAlreadyProcessed.size())+" listings have already been processed and will be skipped this time around.");
//			}
//			// Create the connection class
//			RETSConnection connection = new RETSConnection(loginurl);
//
//			// Authenticate the user and get session
//			RETSUserSession session = connection.getSession(username, password);
//			
//			// remove the MLS#s already processed
//			if(mlsNumbersAlreadyProcessed!=null&&mlsNumbersAlreadyProcessed.size()>0){
//				// remove from current list
//				mlsNumbers.removeAll(mlsNumbersAlreadyProcessed);
//				log.info("Pruned list to process for listings and have "+NumberFormat.getNumberInstance(Locale.US).format(mlsNumbers.size())+" left.");
//				// add to list of removes
//				for(int mlsNumber : mlsNumbersAlreadyProcessed)
//					listingPhotos.put(mlsNumber, 0);
//			} // End if
//						
//			for(int mlsNumber : mlsNumbers){
//				
//				// stop processing if limit has been set
//				if( counter == processLimit && processLimit > 0 )
//					break;
//				
//				// get listing info to setup the directory location
//				try{
//					
//					// get the listing
//					listing = ListingFactory.get(mlsNumber);
//					
//					log.info("Processing For MlsNumber : " + listing.getMlsNumber());
//					// set total # of photos
//					totalPhotoCount = listing.getPhotoCount();					
//					
//					// set the image url path
//					listingImagePath = NnerenRetsFeed.getListingImageUrlPath(listing);
//					log.info("listingImagePath >> NnerenRetsFeed  >> For MlsNumber : " + listing.getMlsNumber() + " listingImagePath >> : " + listingImagePath);
//					
//					// set the location to store images
//					directory = new File(tmpImageStorageLocationOnServer + listingImagePath);
//					directory.mkdirs();
//					
//					// some RETS image server logic
//					if(feedTypeToProcess.equalsIgnoreCase("MLSPinRets")){
//						// MLSPin is a pain and you must specify photo location for Commercial listings special
//						if(listing.getType()==ListingType.Commercial){
//							requestPath = String.format(objectPath,"COMM",String.valueOf(mlsNumber));
//							log.debug("Yo! We got a commercial listing and pulled MLSPin Commercial photos specially.");
//						}else
//							requestPath = String.format(objectPath,"RESI",String.valueOf(mlsNumber));
//					}else
//						requestPath = String.format(objectPath,String.valueOf(mlsNumber));
//					
//					
//					log.debug("Getting photos from: "+requestPath +" and downloading to: "+tmpImageStorageLocationOnServer + listingImagePath);
//					
//					// call the photos	
//					objects = session.getObjects(requestPath, directory);
//					
//				}catch(Exception ex){
//					storeProcessedMlsNumbers(listingPhotos,path);
//					log.error(ex.fillInStackTrace());
//					ex.printStackTrace();
//				} // End try/catch
//				
//				// record this mls number has been processed
//				listingPhotos.put(mlsNumber, totalPhotoCount);				
//			
//				counter++;
//				
//			} // End mls number loop
//			
//			// write the list to file
//			storeProcessedMlsNumbers(listingPhotos,path);			
//			
//			// Logout from the session
//			session.logout();
//			
//			// after you got all the images, then push them all up to Cloud server
//			
//			try{
//				
//				log.info("Pushing listing images to Rackspace Cloud Files CDN for MlsNumber..." + listing.getMlsNumber() 
//						+ " tmpImageStorageLocationOnServer : " + tmpImageStorageLocationOnServer + " imageStorageLocationInCloudFiles : " + imageStorageLocationInCloudFiles);
//				
//				// Use the Rackspace Cloud Files API/Examples to push the entire folder /tmp/cloud-images/
//				CloudFiles.uploadDirectoryContentsToCloud(tmpImageStorageLocationOnServer, imageStorageLocationInCloudFiles);
//				 
//				log.info("Successfully moved files to Rackspace Cloud Files CDN and removed local copies for MlsNUmber " +  listing.getMlsNumber());
//				
//			}catch(Exception e){
//				log.error("Error occured while trying to PUSH NnerenRets images to Rackspace CloudFiles" + e.getMessage());
//				e.printStackTrace();
//			}
//			
//		}catch(Exception ex){
//			System.out.println("Problem processing mls numbers to get photos. [check the log: "+path+"processed-listings.log]");
//			log.error("Problem processing mls numbers to get photos. [check the log: "+path+"processed-listings.log]" + ex.getMessage());
//			ex.printStackTrace();
//		} // End try/catch
//		
//		log.info("FINISHED : Downloading all ");
//
//	} // End main
//	
//	/**
//	 * Open log file and put in HashMap in memory.
//	 * 
//	 * @author 	Mark Kelleher (mark.kelleher@verani.com)
//	 * @date	Sep 25, 2014
//	 *
//	 * @param logFile
//	 * @return
//	 */
//	private static List<Integer> processLogFile(File logFile){
//		
////		Map<Integer, Integer> tempList = new HashMap<Integer, Integer>();
//		List<Integer> tempList = new ArrayList();
//		
//		try{
//			
//		    BufferedReader br = new BufferedReader(new FileReader(logFile));
//		    String line =  null;
//		    HashMap<String,String> map = new HashMap<String, String>();
//
//		    while((line=br.readLine())!=null){
//		        String str[] = line.split(",");
////	            tempList.put(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
//		        tempList.add(Integer.parseInt(str[0]));
//		    }	
//		    
//		}catch(Exception ex){
//			log.error("processLogFile : " + ex.getMessage()); 
//			ex.printStackTrace();
//		} // End try/catch	
//		
//		return tempList;
//		
//	} // End processLogFile
//	
//	/**
//	 * Will take the current hashmap and store to disk in CSV format.
//	 * Can be used to reference or pick up where we left off if something fails.
//	 * 
//	 * @author 	Mark Kelleher (mark.kelleher@verani.com)
//	 * @date	Sep 24, 2014
//	 *
//	 * @param processedListings
//	 */
//	private static void storeProcessedMlsNumbers(Map<Integer, Integer> processedListings, String path){
//		
//		try{
//			
//			FileWriter writer = new FileWriter(path+PHOTO_PROCESS_LOG_FILE_NAME);
//			
//		    Iterator it = processedListings.entrySet().iterator();
//		    
//		    // loop the map and create csv for storage
//		    while (it.hasNext()) {
//		        Map.Entry pairs = (Map.Entry)it.next();
//		        writer.append(pairs.getKey() + "," + pairs.getValue());
//	            writer.append('\n');
//		    }
//		    
//		    writer.flush();
//		    writer.close();
//		    
//		}catch(IOException io){
//			System.out.println("Problem writing CSV log to disk.");
//			log.error("Problem writing CSV log to disk." + io.getMessage()); 
//			io.printStackTrace();
//		} // End try/catch
//		
//	} // End storeProcessedMlsNumbers
//
//	/**********************************************/
//	/******** OLD CODE TO REFERENCE ***************/
//
////	String urlFormatNneren 	= "http://invweb03.offutt-innovia.com/nne/%sjpg%s/%s/%s.jpg"; // old high-res url
////	String urlFormatMlsPin 	= "http://media.mlspin.com/photo.aspx?mls=%s&h=%s&w=%s&n=%s"; // old high-res url
////	String currentUrlFormat = "";
////	String stringMlsNumber 	= "";
////	String imageNumberString= "";
////	String externalURL		= "";
////	URL url					= null;
////	BufferedImage image 	= null;
////	File outputfile 		= null;
//	
////	// loop list
////	for(int mlsNumber : mlsNumbers){
////		
////		// has this mlsNumber already been processed?
////		// if yes, then ignore
////		if(listingPhotos.get(mlsNumber)==null){
////			
////			counter++;
////			
////			try{
////			
////				// get the listing
////				listing = ListingFactory.get(mlsNumber);
////				
////				// set the image url path
////				listingImagePath = NnerenRetsFeed.getListingImageUrlPath(listing);
////				
////				// set the url format
////				if(listing.getFeed().getName().equalsIgnoreCase("NNEREN"))
////					currentUrlFormat = urlFormatNneren;
////				else if(listing.getFeed().getName().equalsIgnoreCase("MLSPinRets"))
////					currentUrlFormat = urlFormatMlsPin;
////				
////				// set total # of photos
////				totalPhotoCount = listing.getPhotoCount();
////				
////				// go get the photos
////				for (int i = 1; i < totalPhotoCount+1; i++) {
////
////					log.debug("i = "+i+" and photoCount = "+totalPhotoCount);
////						
////					stringMlsNumber = Integer.toString(listing.getMlsNumber());
////					
////					// create the URL to get image from
////					if(i==1)
////						imageNumberString = "";
////					else
////						imageNumberString = String.valueOf(i);
////					
////					externalURL = String.format(currentUrlFormat,"high",imageNumberString,stringMlsNumber.substring(stringMlsNumber.length()-4,stringMlsNumber.length()),stringMlsNumber);
////
////					log.debug("External Image "+imageNumberString+" URL: "+externalURL);
////					
////					// for this listing... go get the HIGH RES photos
////					url = new URL(externalURL);
////					
////					try{
////					
////						// read the url
////				        image = ImageIO.read(url);
////				        
////				        // write the image to tmp disk location to be pushed to cloud later
////				        // Images will be downloaded to folder structure /tmp/cloud-images/<street number>-<street name>-<town>-<state>-<zip>-<mls number>/<mls number>_<photo number>.jpg
////				        outputfile = new File(tmpImageStorageLocationOnServer + listingImagePath + listing.getMlsNumber() + "_" + (i-1) + ".jpg");
////				        outputfile.mkdirs();
////				        ImageIO.write(image, "jpg", outputfile);
////				        
////					}catch (IIOException ex){
////						// do nothing if image not found... just ignore and proceed on.
////						log.warn("ERROR : Skipping download of HIGH RES image #"+i+" for listing: "+Integer.toString(listing.getMlsNumber()));
////					}
////					
////					// reset some variables
////					image 	= null;
////					url 	= null;
////					
////				} // end photo loop	
////				
////				// record this mls number has been processed
////				listingPhotos.put(mlsNumber, totalPhotoCount);
////			
////			}catch(Exception ex){
////				storeProcessedMlsNumbers(listingPhotos,path);
////			} // End try/catch
////			
////			if(counter%BATCH_PROCESS_STORE_CSV == 0)
////				storeProcessedMlsNumbers(listingPhotos,path);					
////			
////		}else
////			log.trace("Already processed mls number: "+mlsNumber+", skipping photos for it.");
////		
////		/************* TESTING ************************/
////		if(counter==11) break;
////		
////	} // loop mls numbers
////	
////	log.info("Downloaded photos for "+counter+" listings. Now it's time to PUSH to cloud.");
////	
////	// write the list to file
////	storeProcessedMlsNumbers(listingPhotos,path);
////		
//	
//} // End CloudPhotos
