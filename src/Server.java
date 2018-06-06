/**
 *
 * @author Kian Murdock
 */
import java.io.*;
import java.net.*;

public class Server {
    
    private static double accountBalance = 0.00;    
       
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(8888);
        Socket cs = ss.accept();
        InputStreamReader ir = new InputStreamReader(cs.getInputStream());
        BufferedReader br = new BufferedReader(ir);
        String check = br.readLine();
        System.out.println(check);
        
        DataInputStream inStream = new DataInputStream(cs.getInputStream());
        PrintStream outStream = new PrintStream(cs.getOutputStream());
        int inp;
        boolean cont= true;
        
        do{
            inp = inStream.readInt();
            switch(inp){
                case 1:
                    System.out.println("Display selected.");
                    sendBalance(outStream);
                    break;
                case 2:
                    System.out.println("Deposit selected.");
                    double amtD = inStream.readDouble();
                    deposit(amtD, outStream);
                    break;
                case 3:
                    System.out.println("Withdraw selected.");
                    double amtW = inStream.readDouble();
                    withdraw(amtW, outStream);
                    break;
                case 4:
                    System.out.println("Exit selected.");
                    cont = false;
                    break;
            }
        }while(cont);
        inStream.close();
        outStream.close();
        br.close();
        ir.close();
        cs.close();
        ss.close();
    }
    
    //send balance message through printstream
    public static void sendBalance(PrintStream o) throws Exception{
        String format = String.format("%.02f", accountBalance);
        o.println("Your balance is: $" + format);
    }
    
    //add given amount to balance & return message w/ new balance
    public static void deposit(double amt, PrintStream o)throws Exception{
        accountBalance+=amt;
        String format = String.format("%.02f", accountBalance);
        o.println("Your new balance is: $" + format);
    }
    
    //check for enough funds, withdraw given amount if there is
    public static void withdraw(double amt, PrintStream o) throws Exception{
        if(amt>accountBalance)
            o.println("Error: Not enough funds.");
        else{
            accountBalance-=amt;
            String format = String.format("%.02f", accountBalance);
            o.println("Your new balance is: $" + format);
        }
    }
}
