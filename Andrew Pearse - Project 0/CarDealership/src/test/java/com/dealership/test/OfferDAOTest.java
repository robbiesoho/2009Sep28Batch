package com.dealership.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.dealership.models.Car;
import com.dealership.models.Offer;
import com.dealership.repository.OfferDAO;

public class OfferDAOTest {

	OfferDAO od;
	
	@Before
	public void setup() {
		od = new OfferDAO();
	}
	
	@Test
	public void updateOfferTest() {
		assertNotNull(od.update(new Offer(2, 0, 0, true, false, "arpearse")));
	}

	@Test
	public void createOfferTest() {
		assertNotNull(od.create(new Offer(4000, 10, "sbeve", new Car(1, null, null, 0, null, false))));
	}
	
	@Test
	public void deleteOfferTest() {
		assertEquals(1, od.delete(od.getNextOfferID()));
	}
}