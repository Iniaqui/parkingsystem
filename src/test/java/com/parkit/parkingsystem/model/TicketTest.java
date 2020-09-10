package com.parkit.parkingsystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TicketTest {

	@Test
	void setIdTest() {
		Ticket ticket=new Ticket();
		ticket.setId(5);
		assertEquals(5,ticket.getId());
	}

}
