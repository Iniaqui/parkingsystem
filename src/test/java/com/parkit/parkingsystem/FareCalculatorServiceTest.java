package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceTest {
	
	

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
       // fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
        fareCalculatorService = new FareCalculatorService();
       
    }
    
    @Test
    public void calculateFareCar(){
    	System.out.println("Debut test de calculateFareCar---------------------------------");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
        System.out.println("Fin test de calculateFareCar ---------------------------------------");
    }
    
    @Test
    public void calculateFareBike(){
    	 System.out.println("Debut test de calculateFareBike ---------------------------------------");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        System.out.println(ticket.getPrice());
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
        System.out.println("Fin test de calculateFareBike ---------------------------------------");
    }
    
    @Test
    public void calculateFareUnkownType(){
    	System.out.println("Debut test de calculateFareUnkownType ---------------------------------------");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
        System.out.println("Fin test de calculateFareUnkownType ---------------------------------------");
        
    }
   
    @Test
    public void calculateFareBikeWithFutureInTime(){
    	System.out.println("Debut test de calculateFareBikeWithFutureInTime ---------------------------------------");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
        System.out.println("Fin test de calculateFareBikeWithFutureInTime ---------------------------------------");
    }
    
    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
    	System.out.println("Debut test de calculateFareBikeWithLessThanOneHourParkingTime ---------------------------------------");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
        System.out.println("Fin test de calculateFareBikeWithLessThanOneHourParkingTime ---------------------------------------");
    }
    
    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
    	System.out.println("Debut test de calculateFareCarWithLessThanOneHourParkingTime ---------------------------------------");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        System.out.println("Price : "+ticket.getPrice());
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
        System.out.println("Fin test de calculateFareCarWithLessThanOneHourParkingTime ---------------------------------------");
    }
    
    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
    	System.out.println("Debut test de calculateFareCarWithMoreThanADayParkingTime ---------------------------------------");
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
        System.out.println("Fin test de calculateFareCarWithMoreThanADayParkingTime ---------------------------------------");
    }
    
    @Test
    public void calculateFareVehiculWithLessOfHalfAnHour() {//Moins de 30 minute pour la gratuité 
	   System.out.println("Debut test de calculateFareVehiculWithLesOfHalfAnHour ---------------------------------------");
    	Date inTime = new Date();
    	inTime.setTime(System.currentTimeMillis()-(20*60*1000));//30 minutes parking time should give free
    	Date outTime = new Date();
    	ParkingSpot  parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
    	
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ticket.setParkingSpot(parkingSpot);
    	fareCalculatorService.calculateFare(ticket);
    	assertEquals(0,ticket.getPrice());
    	System.out.println("Fin test de calculateFareVehiculWithLesOfHalfAnHour ---------------------------------------");
    }
    
    @Test
    public void reductionTest() {
    	 System.out.println("Debut test de la reduction de 5%  ---------------------------------------");
    	 //GIVEN
    	 ticket.setVehicleRegNumber("ABCED");
    	 TicketDAO ticketDAO = Mockito.mock(TicketDAO.class);
    	 fareCalculatorService.setTicketDAO(ticketDAO);
    	 Date inTime = new Date();
    	 inTime.setTime(System.currentTimeMillis()-(60*60*1000));
    	 Date outTime = new Date();
    	 ParkingSpot  parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
     	ticket.setInTime(inTime);
     	ticket.setOutTime(outTime);
     	ticket.setParkingSpot(parkingSpot);
     	 when(ticketDAO.checkExistingVehiculPark(ticket.getVehicleRegNumber())).thenReturn(2);
     	 
     	//THEN
     	fareCalculatorService.calculateFare(ticket);
     	//ASSERT
     	assertEquals(ticket.getPrice(),Fare.CAR_RATE_PER_HOUR*0.95);
    	 System.out.println("Fin test de la reduction de 5%  ---------------------------------------");

    }
}
