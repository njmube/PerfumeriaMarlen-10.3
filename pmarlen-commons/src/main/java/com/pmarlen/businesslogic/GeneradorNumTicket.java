package com.pmarlen.businesslogic;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class GeneradorNumTicket{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    private static final DecimalFormat    dfId  = new DecimalFormat("00");
    private static final DecimalFormat    dfTicketDigits  = new DecimalFormat("0000000000");

    public static String getNumTicket(int usuarioId,int almacenId){
        String prenumticket = sdf.format(new Date())+"|"+dfId.format(usuarioId)+"|"+dfId.format(almacenId);
        System.err.println("System time millis="+System.currentTimeMillis() );
        System.err.println("prenumticket="+prenumticket);
        int hashCode = prenumticket.hashCode();
        String numTicket = null;
        if(hashCode < 0){
            numTicket = "1"+dfTicketDigits.format(-1*hashCode);    
        } else {
            numTicket = "9"+dfTicketDigits.format(hashCode);    
        }
        return numTicket;
    }

    public static void main(String args[]){
        //System.out.println(getNumTicket(Integer.parseInt(args[0]),Integer.parseInt(args[1])));    
		System.out.println(getNumTicket(1,1));    
    }
}
