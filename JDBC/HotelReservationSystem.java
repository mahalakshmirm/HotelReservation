package Hotel_Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Hotel_Reservation
{
	private static final String dburl = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String user = "root";
    private static final String password = "root";
    public static void main(String[] args) 
    {
    	try
    	{
    		 Class.forName("com.mysql.cj.jdbc.Driver");
    	}
    	catch (ClassNotFoundException e) 
    	{
            System.out.println(e.getMessage());
        }
    	try
    	{
    		Connection con = DriverManager.getConnection(dburl, user, password);
    		Scanner scn=new Scanner(System.in);
    		while(true)
    		{
    			  System.out.println("\n\t\t\t\t\t HOTEL RESERVATION SYSTEM");
	                System.out.println("\t\t\t\t\t ***** *********** ******");
	                System.out.println("1. RESERVE ROOM");
	                System.out.println("2. VIEW RESERVATIONS");
	                System.out.println("3. VIEW AVAILABLE ROOMS");
	                System.out.println("4. VIEW RESERVED ROOMS");
	                System.out.println("5. GET ROOM NUMBER");
	                System.out.println("6. UPDATE RESERVATIONS");
	                System.out.println("7. DELETE RESERVATIONS");
	                System.out.println("8. EXIT");
	                System.out.print("Choose an option: ");
	                int choice = scn.nextInt();
	                switch(choice)
	                {
	                	case 1:
	                		reserveRoom(con, scn);
	                		break;
	                	case 2:
	                		viewReservationDetails(con);
	                		break;
	                	case 3:
	                		viewAvailableRooms(con);
	                		break;
	                	case 4:
	                		viewReservedRooms(con);
	                		break;
	                	case 5:
	                		getRoomNo(con, scn);
	                		break;
	                	case 6:
	                		updateReservations(con, scn);
	                		break;
	                	case 7:
	                		deleteReservations(con, scn);
	                		break;
	                	case 8:
	                		exit(scn,con);
	                		return;
	                	default:
	                		System.out.println("Invalid choice... try again");
	                }
    		}
    	}
    	catch (SQLException | InterruptedException e)
    	{
            e.printStackTrace();
    	}
    }
    private static void reserveRoom(Connection con, Scanner scn) 
    {
    	 viewAvailableRooms(con);
	     try 
	     {
	    	 scn.nextLine();
	    	 System.out.print("Enter guest name: ");
	         String guest_name = scn.nextLine();       
	         System.out.print("Enter guest contact: ");
	         String contact = scn.next();
	         System.out.print("Enter room number from the above list: ");
	         int room = scn.nextInt();
	         String checkQuery = "SELECT ROOM_STATUS FROM ROOMS WHERE ROOM_NUMBER = ?";
	         try (PreparedStatement checkStmt = con.prepareStatement(checkQuery))
	         {
	        	 checkStmt.setInt(1, room);
	             ResultSet rs = checkStmt.executeQuery();
	             if (rs.next())
	             {
	            	 String status = rs.getString("ROOM_STATUS");
	                 if (!status.equalsIgnoreCase("Available")) 
	                 {
	                	 System.out.println("Room already reserved!");
	                     return;
	                 }
	             } 
	             else
	             {
	            	 System.out.println("Invalid room number.");
	                 return;
	             }
	         }
	         String query = "INSERT INTO RESERVATIONS (GUEST_NAME, GUEST_CONTACT, ROOM_NUMBER) VALUES (?, ?, ?)";
	         try (PreparedStatement pstmt = con.prepareStatement(query)) 
	         {
	        	 pstmt.setString(1, guest_name);
	             pstmt.setString(2, contact);
	             pstmt.setInt(3, room);
	             pstmt.executeUpdate();
	        }
	        String updateRoomStatus = "UPDATE ROOMS SET ROOM_STATUS = 'Reserved' WHERE ROOM_NUMBER = ?";
	        try (PreparedStatement pstmt2 = con.prepareStatement(updateRoomStatus))
	        {
	        	pstmt2.setInt(1, room);
	        	pstmt2.executeUpdate();
	        }
	        System.out.println("Reservation successful and room marked as Reserved!");
	    }
	    catch (SQLException e)
	    {
		    e.printStackTrace();
	    }
    }
    private static void viewReservationDetails(Connection con) 
    {
    	String query = "SELECT R_ID, GUEST_NAME, GUEST_CONTACT, ROOM_NUMBER, R_DATE FROM RESERVATIONS";
    	 try (Statement stmt = con.createStatement();
	          ResultSet rs = stmt.executeQuery(query)) 
    	 {
    		 System.out.println("\nCURRENT RESERVATIONS LIST");
	         System.out.println("+------+-------------+---------------+----------+-------------------------+");
	         System.out.println("| R_ID | GUEST_NAME  | CONTACT       | ROOM_NO  | DATE                    |");
	         System.out.println("+------+-------------+---------------+----------+-------------------------+");
	         while (rs.next())
	         {
	        	 System.out.printf("| %-5d | %-11s | %-13s | %-8d | %-19s |\n",
	             rs.getInt("R_ID"), rs.getString("GUEST_NAME"), rs.getString("GUEST_CONTACT"),
	             rs.getInt("ROOM_NUMBER"), rs.getTimestamp("R_DATE").toString());
	             System.out.println("+------+-------------+---------------+----------+-------------------------+");
	         }
    	 }
    	 catch (SQLException e) 
    	 {
    		 e.printStackTrace();
	     }
    }
    private static void viewAvailableRooms(Connection con)
    {
    	 String query = "SELECT ROOM_NUMBER, ROOM_TYPE FROM ROOMS WHERE ROOM_STATUS = 'Available'";
	     try (Statement stmt = con.createStatement();
	          ResultSet rs = stmt.executeQuery(query)) 
	     {
	            System.out.println("\nAVAILABLE ROOMS:");
	            System.out.println("+------------+------------+");
	            System.out.println("| Room No    | Room Type  |");
	            System.out.println("+------------+------------+");
	            while (rs.next()) 
	            {
	                System.out.printf("| %-10d | %-10s |\n",
	                rs.getInt("ROOM_NUMBER"), rs.getString("ROOM_TYPE"));
	            }
	            System.out.println("+------------+------------+");
	     } 
	     catch (SQLException e) 
	     {
	            e.printStackTrace();
	    }
    }
    private static void viewReservedRooms(Connection con)
    {
    	 String query = "SELECT ROOM_NUMBER, ROOM_TYPE FROM ROOMS WHERE ROOM_STATUS = 'Reserved'";
	     try (Statement stmt = con.createStatement();
	     ResultSet rs = stmt.executeQuery(query)) 
	     {
	    	 System.out.println("\nRESERVED ROOMS:");
	    	 System.out.println("+------------+------------+");
	    	 System.out.println("| Room No    | Room Type  |");
	    	 System.out.println("+------------+------------+");
	    	 while (rs.next()) 
	    	 {
	    		 System.out.printf("| %-10d | %-10s |\n",
	    		 rs.getInt("ROOM_NUMBER"), rs.getString("ROOM_TYPE"));
	    	 }
	    	 System.out.println("+------------+------------+");
	     } 
	     catch (SQLException e) 
	     {
	    	 e.printStackTrace();
	     }
    }
    private static void getRoomNo(Connection con, Scanner scn)
    {
    	try 
    	{
    		System.out.print("Enter reservation ID: ");
	        int id = scn.nextInt();
	        scn.nextLine();
	        System.out.print("Enter guest name: ");
	        String gname = scn.nextLine();
	        System.out.println();
	        String query = "SELECT ROOM_NUMBER FROM RESERVATIONS WHERE R_ID = ? AND GUEST_NAME = ?";            
	        try (PreparedStatement pstmt = con.prepareStatement(query))
	        {
                pstmt.setInt(1, id);
                pstmt.setString(2, gname);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next())
                {
                	int room = rs.getInt("ROOM_NUMBER");
	                System.out.println("ROOM NUMBER: " + room);
                }
                else
                {
                	System.out.println("No reservation found.");
                }
	        }
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}          
    }
    private static void updateReservations(Connection con, Scanner scn) 
    {
        try 
        {
            System.out.print("Enter reservation ID to update: ");
            int id = scn.nextInt();
            scn.nextLine();

            if (!reservationExists(con, id)) 
            {
                System.out.println("Reservation not found.");
                return;
            }

            // Fetch current room number for the given reservation ID
            int oldRoom = -1;
            String getOldRoomQuery = "SELECT ROOM_NUMBER FROM RESERVATIONS WHERE R_ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(getOldRoomQuery)) 
            {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) 
                {
                    oldRoom = rs.getInt("ROOM_NUMBER");
                } 
                else 
                {
                    System.out.println("No reservation found.");
                    return;
                }
            }

            System.out.print("Enter new guest name: ");
            String name = scn.nextLine();
            System.out.print("Enter new contact number: ");
            String contact = scn.nextLine();
            System.out.print("Enter new Room number: ");
            int newRoom = scn.nextInt();

            // Check if the new room is available
            String checkRoomQuery = "SELECT ROOM_STATUS FROM ROOMS WHERE ROOM_NUMBER = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkRoomQuery)) 
            {
                checkStmt.setInt(1, newRoom);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) 
                {
                    String status = rs.getString("ROOM_STATUS");
                    if (!status.equalsIgnoreCase("Available")) 
                    {
                        System.out.println("Selected room is already reserved! Try another room.");
                        return;
                    }
                } 
                else 
                {
                    System.out.println("Invalid room number.");
                    return;
                }
            }

            // Update reservation with new details
            String updateReservationQuery = "UPDATE RESERVATIONS SET GUEST_NAME = ?, GUEST_CONTACT = ?, ROOM_NUMBER = ? WHERE R_ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(updateReservationQuery)) 
            {
                pstmt.setString(1, name);
                pstmt.setString(2, contact);
                pstmt.setInt(3, newRoom);
                pstmt.setInt(4, id);
                int count = pstmt.executeUpdate();

                if (count > 0) 
                {
                    // Mark old room as "Available"
                    String updateOldRoomStatus = "UPDATE ROOMS SET ROOM_STATUS = 'Available' WHERE ROOM_NUMBER = ?";
                    try (PreparedStatement pstmt2 = con.prepareStatement(updateOldRoomStatus)) 
                    {
                        pstmt2.setInt(1, oldRoom);
                        pstmt2.executeUpdate();
                    }

                    // Mark new room as "Reserved"
                    String updateNewRoomStatus = "UPDATE ROOMS SET ROOM_STATUS = 'Reserved' WHERE ROOM_NUMBER = ?";
                    try (PreparedStatement pstmt3 = con.prepareStatement(updateNewRoomStatus)) 
                    {
                        pstmt3.setInt(1, newRoom);
                        pstmt3.executeUpdate();
                    }

                    System.out.println("Reservation updated successfully!");
                } 
                else 
                {
                    System.out.println("Update failed.");
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    
    private static void deleteReservations(Connection con, Scanner scn)
    {
    	 try 
    	 {
    		 System.out.print("Enter reservation ID to delete: ");
	         int id = scn.nextInt();
	         int roomNo = -1;
	         String getRoom = "SELECT ROOM_NUMBER FROM RESERVATIONS WHERE R_ID = ?";
	         try (PreparedStatement pstmt = con.prepareStatement(getRoom))
	         {
	        	 pstmt.setInt(1, id);
	             ResultSet rs = pstmt.executeQuery();
	             if (rs.next())
	             {
	            	 roomNo = rs.getInt("ROOM_NUMBER");
	             } 
	             else
	             {
	            	 System.out.println("Reservation ID not found.");
	                 return;
	             }
	         }
	         String deleteQuery = "DELETE FROM RESERVATIONS WHERE R_ID = ?";
	         try (PreparedStatement pstmt = con.prepareStatement(deleteQuery))
	         {
	        	 pstmt.setInt(1, id);
	             pstmt.executeUpdate();
	         }
	         String updateRoomStatus = "UPDATE ROOMS SET ROOM_STATUS = 'Available' WHERE ROOM_NUMBER = ?";
	         try (PreparedStatement pstmt2 = con.prepareStatement(updateRoomStatus)) 
	         {
	        	 pstmt2.setInt(1, roomNo);
	        	 pstmt2.executeUpdate();
	         }
	         System.out.println("Reservation deleted and room marked as Available.");
    	 } 
    	 catch (SQLException e) 
    	 {
	      	e.printStackTrace();
	     }
    }
    private static boolean reservationExists(Connection con, int id)
    {
    	String query = "SELECT R_ID FROM RESERVATIONS WHERE R_ID = ?";
	    try (PreparedStatement pstmt = con.prepareStatement(query)) 
	    {
	    	pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        return rs.next();
	    }
	    catch (SQLException e)
	    {
            e.printStackTrace();
            return false;
        }
    }
    public static void exit(Scanner scn,Connection con) throws InterruptedException 
    {
    	System.out.println("Exiting system...");
        Thread.sleep(1000);
        try
        {
        	if (con != null && !con.isClosed())
        	{
        		con.close(); 	
        	}	      
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        scn.close(); 
        System.out.println("Thank you for using the Hotel Reservation System!");
        System.exit(0); 
    }
}
    
