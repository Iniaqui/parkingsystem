package com.parkit.parkingsystem;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;


@ExtendWith(MockitoExtension.class)
class ParkingServiceIncommingVehiculeTest {
	private static ParkingService parkingService;
	
	@Mock
    private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;
	
	@BeforeEach
	private void setUpTest(){
		
		try {
			when(inputReaderUtil.readSelection()).thenReturn(1);
			
			when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(3);
			
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("MAU199");
			
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(false);
			when(ticketDAO.saveTicket(any(Ticket.class))).thenReturn(true);
			
			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
			
			
		}catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
		
		
	}
	@Test
	public void processIncommingVehicultest() {
		parkingService.processIncomingVehicle();
		 verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	     verify(ticketDAO,Mockito.times(1)).saveTicket(any(Ticket.class));
		
	}

}
