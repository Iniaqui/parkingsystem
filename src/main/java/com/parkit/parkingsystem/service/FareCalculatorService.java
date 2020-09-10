package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	private boolean isFree;
	private double tauxPerCent =0.95;
	
	
	TicketDAO ticketDAO=new TicketDAO();

	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO=ticketDAO;
	}
	public void calculateFare(Ticket ticket) {
		System.out.println(ticketDAO.checkExistingVehiculPark(ticket.getVehicleRegNumber()));
		double price=0;
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		// TODO: Some tests are failing here. Need to check if this logic is correct
		isFree = fareFreeForLessHalfAnHour(ticket);//Boolean qui determine si le vehicule a fait moins de 30 minutes
		
		double duration = this.duration(ticket);//Calcule la durée de stationnement
		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			if(isFree ==true) {
				ticket.setPrice(0);
			}
			else {
				price =duration / (1000 * 3600) * Fare.CAR_RATE_PER_HOUR;
				System.out.println(price);
				if(ifReduction(ticket.getVehicleRegNumber())) {
					System.out.println(duration / (1000 * 3600));
					price =price*tauxPerCent;
					System.out.println(price);
					
				}
				ticket.setPrice(price);
			
				
			}
			break;
		}
		case BIKE: {
			if(isFree== true) {
				ticket.setPrice(0);
			}
			else {
				price =duration / (1000 * 3600) * Fare.BIKE_RATE_PER_HOUR;
				if(ifReduction(ticket.getVehicleRegNumber())) {
					price =price*tauxPerCent;
				}
				ticket.setPrice(price);
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}

	}

	public double duration(Ticket ticket) {
		if (ticket.getInTime() != null && ticket.getOutTime() != null) {
			return ticket.getOutTime().getTime() - ticket.getInTime().getTime();
		} else {
			return 0;
		}
	}

	public boolean fareFreeForLessHalfAnHour(Ticket ticket) {
		double free = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
		System.out.println("Gratuit : " + free);
		if (free <= (30*60*1000)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean ifReduction(String vehicleRegNumber ) {
		
		if(ticketDAO.checkExistingVehiculPark(vehicleRegNumber)>1) {
			return true;
		}
		else
			return false;
		
	}
	

}