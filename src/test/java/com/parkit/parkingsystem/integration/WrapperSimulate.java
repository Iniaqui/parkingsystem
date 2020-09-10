package com.parkit.parkingsystem.integration;

import java.io.InputStream;
import java.util.Scanner;

public class WrapperSimulate {
	
	    private Scanner scannerForReadSelection = null;
	    private Scanner scannerForRegVehNumber = null;
	    public WrapperSimulate(InputStream r,InputStream s) {
	    	scannerForReadSelection=new Scanner(s);
	    	scannerForRegVehNumber = new Scanner(r);
	    }
	    public int getReadSelection() {
	    	return scannerForReadSelection.nextInt();
	    }
	    public String getRegVehNumber() {
	    	return scannerForRegVehNumber.nextLine();
	    }
	
}
