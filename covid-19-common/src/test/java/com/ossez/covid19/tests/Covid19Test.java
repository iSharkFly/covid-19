/*
 * Copyright (C) 2016 Verani Realty, Inc Authors
 */
package com.ossez.covid19.tests;

import com.ossez.covid19.common.Factory;
import com.ossez.covid19.common.ValidationException;
import com.ossez.covid19.common.backPage.LogEntry;
import com.ossez.covid19.common.backPage.LogType;
import com.ossez.covid19.common.backPage.User;
import com.ossez.covid19.common.backPage.factories.LogFactory;
import com.ossez.covid19.common.backPage.factories.UserFactory;
import com.ossez.covid19.common.crm.ContactMethod;
import com.ossez.covid19.common.crm.InformationRequest;
import com.ossez.covid19.common.crm.Lead;
import com.ossez.covid19.common.dao.factories.Covid19Factory;
import com.ossez.covid19.common.mls.County;
import com.ossez.covid19.common.mls.ListingCriteria;
import com.ossez.covid19.common.mls.factories.CountyFactory;
import com.ossez.covid19.common.mls.factories.StyleFactory;
import com.ossez.covid19.common.mls.factories.TownFactory;
import com.ossez.covid19.common.models.Covid19Current;
import com.ossez.covid19.common.models.Style;
import com.ossez.covid19.common.models.Town;
import com.ossez.covid19.common.web.Account;
import com.ossez.covid19.common.web.AccountActionType;
import com.ossez.covid19.common.web.AccountContact;
import com.ossez.covid19.common.web.factories.AccountFactory;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class Covid19Test extends TestCase {
	@Before
	@Override
	protected void setUp() throws Exception {
		Factory.beginTransaction();
	}
	
	@After
	@Override
	protected void tearDown() throws Exception {
		Factory.rollbackTransaction();
	}

	/**
	 * Tests search functionality for the customer object.
	 */
	public void testGetCovid19Current() throws ValidationException {
		Covid19Current covid19Current = new Covid19Current();
		Covid19Factory.get(1L);

		// make sure the customer was found
		Assert.assertNotNull(covid19Current);

	}
	
	/**
	 * Tests create / delete of a customer.
	 */
	@Test
	public void testCreateDelete() throws ValidationException {
		try {
		Account account = new Account();
		account.setLastEmailUpdate(new Date());
		account.setConfirmed(false);
		account.setContactMethod(ContactMethod.Phone);
		account.setGeneralEmails(true);
		account.setBuyersNews(true);
		account.setEmailUpdates(true);
		account.setOpenHouses(true);
		account.setIncludeSold(true);
		account.setNewsletter(true);
		account.setSpecialPromos(true);
		account.setOptOut(false);
		account.setBounceCount(0);
		account.setBadEmail(false);
		account.setWelcomeText("blah blah");
		account.setDisabled(false);
		account.setAgent(null);

		AccountContact contact = new AccountContact();
		contact.setDescription("Home?");
		contact.setFirstName("Test");
		contact.setLastName("Customer");
		contact.setEmail("test@customer.com");
		contact.setPassword("testing");
		contact.setAddress1("123 Test Rd");
		contact.setAddress2("Unit 111");
		contact.setCity("City");
		contact.setState("NH");
		contact.setZip("03301");
		contact.setPhone("6035551234");
		contact.setMobile("6035551234");
		contact.setFax("6035551234");
		contact.setPrimary(true);

		account.addContact(contact);

		AccountFactory.save(account);

//		account = AccountFactory.get(account.getId());

		Assert.assertNotNull(account);

		AccountFactory.delete(account);

//		account = AccountFactory.get(account.getId());
		} catch (Exception ex) {
			Factory.rollbackTransaction();
			ex.printStackTrace();
			Assert.fail();
		}
	}

	public void testAuthentication() throws ValidationException {
		Account account = new Account();

		AccountContact contact = new AccountContact();
		contact.setEmail("test@customer.com");
		contact.setPassword("password");
		contact.setFirstName("test");
		contact.setLastName("testing");
		contact.setAddress1("123 Test Rd");
		contact.setCity("City");
		contact.setState("NH");
		contact.setZip("03301");
		contact.setPhone("6035551234");

		account.addContact(contact);

		AccountFactory.save(account);

		contact = AccountFactory.getByAuthentication("test@customer.com", "password");
		
		Assert.assertNotNull(contact);
	}

	/**
	 * Tests search functionality for the customer object.
	 */
	public void testSearch() throws ValidationException {
		Account account = new Account();
		AccountContact contact = new AccountContact();
		contact.setFirstName("Test");
		contact.setLastName("Customer");
		contact.setEmail("test@customer.com");
		contact.setPassword("password");
		contact.setAddress1("123 Test Rd");
		contact.setCity("City");
		contact.setState("NH");
		contact.setZip("03301");
		contact.setPhone("6035551234");
		account.addContact(contact);

		AccountFactory.save(account);

		// search by first name
		List<Account> accounts = AccountFactory.search("test");

		// make sure the customer was found
		Assert.assertNotNull(accounts);
		Assert.assertNotSame(0, accounts.size());

		// search by email
		accounts = AccountFactory.search("test@cust");

		// make sure the customer was found
		Assert.assertNotNull(accounts);
		Assert.assertNotSame(0, accounts.size());

		// search by last name
		accounts = AccountFactory.search("customer");

		// make sure the customer was found
		Assert.assertNotNull(accounts);
		Assert.assertNotSame(0, accounts.size());
	}

	/**
	 * Tests to make sure a lead can be assigned to a customer, and that a lead
	 * can be retrieved for a customer.
	 */
	@Test
	public void testLead() throws ValidationException {
		Account account = new Account();
		AccountContact contact = new AccountContact();
		contact.setFirstName("Test");
		contact.setLastName("Customer");
		contact.setEmail("test@customer.com");
		contact.setPassword("password");
		contact.setPhone("6035551234");
		contact.setAddress1("123 Test Rd");
		contact.setCity("City");
		contact.setState("NH");
		contact.setZip("03301");
		account.addContact(contact);

		AccountFactory.save(account);

		Lead lead = new Lead();
		lead.setAccount(account);
		lead.updateContactDetails();

		InformationRequest request = new InformationRequest();
//		request.setLeadType(eadFactory.getType("Verani.com"));

		lead.addRequest(request);

//		LeadFactory.save(lead, null);

//		Assert.assertNotNull(LeadFactory.getByCustomer(account));
	}

	/**
	 * Tests adding / reading from the customer history.
	 */
	@Test
	public void testHistory() throws ValidationException {
		User user = new User();
		user.setLogin("Testing");
		user.setPassword("testtest");
		user.setFirstName("test");
		user.setLastName("testing");

		UserFactory.save(user);

		Account account = new Account();
		AccountContact contact = new AccountContact();
		contact.setFirstName("Test");
		contact.setLastName("Customer");
		contact.setEmail("test@customer.com");
		contact.setPassword("password");
		contact.setPhone("6035551234");
		contact.setAddress1("123 Test Rd");
		contact.setCity("City");
		contact.setState("NH");
		contact.setZip("03301");
		account.addContact(contact);

		AccountFactory.save(account);

		LogEntry log = new LogEntry();
		log.setMessage("Some evnet");
		log.setConfirmed(false);
		log.setUser(user);
//		log.setObjectId(account.getId());
		log.setType(LogType.Customer);

		LogFactory.save(log);

		AccountFactory.save(account);

//		account = AccountFactory.get(account.getId());

		// make sure the log entry exists
		List<LogEntry> logs = AccountFactory.getLogs(account);
		Assert.assertNotNull(logs);
		Assert.assertNotSame(0, logs.size());
	}

	/**
	 * Tests favorites and listing views.
	 */
	@Test
	public void testListings() throws ValidationException {
		Account account = new Account();
		AccountContact contact = new AccountContact();
		contact.setFirstName("Test");
		contact.setLastName("Customer");
		contact.setEmail("test@customer.com");
		contact.setPassword("password");
		contact.setPhone("6035551234");
		contact.setAddress1("123 Test Rd");
		contact.setCity("City");
		contact.setState("NH");
		contact.setZip("03301");
		account.addContact(contact);

		AccountFactory.save(account);

		Style style = new Style();
		style.setName("Test Style");

		StyleFactory.save(style);
		
		County county = new County("Test County", "NH");
		CountyFactory.save(county);

		Town town = new Town();
		town.setName("Testing");
		town.setState("NH");
		town.setCounty(county);

		TownFactory.save(town);
		
		// create a listing
//		Listing
//		Listing listing = new Listing();
////		listing.setMlsNumber(123456);
//		listing.setStreetNumber("123");
//		listing.setStreetName("test");
//		listing.setListPrice(150000);
//		listing.setBathrooms(1);
//		listing.setBedrooms(2);
//		listing.setFinishedSqft(1200);
//		listing.setLotSize(0.75f);
//		listing.setStyle(style);
//		listing.setTown(town);
//		listing.setType(ListingType.Condominium);
//		listing.setFeed(FeedFactory.get("NNEREN"));
//		listing.setAgentMlsId("yada");
//		listing.setState("NH");
//		listing.setZip("03303");
//
//		ListingFactory.save(listing);
//
//		account.addFavorite(listing);
//		account.recordView(listing);

		AccountFactory.save(account);
		Factory.getSession().flush();
		Factory.getSession().clear();

		// make sure the information was saved
//		account = AccountFactory.get(account.getId());

		Assert.assertNotNull(account.getFavorites());
		Assert.assertNotSame(0, account.getFavorites().size());
	}

	/**
	 * Tests saving customer searches.
	 */
	@Test
	public void testSavedSearches() throws ValidationException {
		Account account = new Account();
		AccountContact contact = new AccountContact();
		contact.setFirstName("Test");
		contact.setLastName("Customer");
		contact.setEmail("test@customer.com");
		contact.setPassword("password");
		contact.setPhone("6035551234");
		contact.setAddress1("123 Test Rd");
		contact.setCity("City");
		contact.setState("NH");
		contact.setZip("03301");
		account.addContact(contact);

		AccountFactory.save(account);

		// create listing criteria
		ListingCriteria criteria = new ListingCriteria();
		criteria.setPriceRange(100000, 250000);
		criteria.setBedrooms(2);

		account.saveSearch("TEST!!!", false, criteria);

		// make sure the search was saved
		Assert.assertNotNull(account.getSavedSearches());
		Assert.assertNotSame(0, account.getSavedSearches().size());
	}

	@Test
	public void testCustomerActions() {
		Account account = new Account();
		AccountFactory.save(account);
		
		AccountFactory.recordAction(account, AccountActionType.ListingView, 123456);
	}
}
