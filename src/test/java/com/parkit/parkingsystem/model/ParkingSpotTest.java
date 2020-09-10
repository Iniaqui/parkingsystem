package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;

class ParkingSpotTest {
	private static ParkingSpot parkingSpot;
	
	@BeforeEach
	private void setUpTest() {
		 parkingSpot = new ParkingSpot(5,ParkingType.CAR,false);
	}
	@Test
	void isAvailableTest() {
		boolean available = parkingSpot.isAvailable();
		assertEquals(available,false);
	}
	@Test
	public void setParkingTypeTest() {
		parkingSpot.setParkingType(ParkingType.BIKE);
		assertEquals(ParkingType.BIKE,parkingSpot.getParkingType());
	}
	@Test
	public void setIdTest() {
		parkingSpot.setId(10);
		assertEquals(10,parkingSpot.getId());
	}

}
