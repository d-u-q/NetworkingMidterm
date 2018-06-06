/**
 *
 * @author Kian Murdock
 */
import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    
    public static void main(String[] args) throws Exception{
        String line = "--------------------------------------------";
        Socket cs = new Socket("localhost", 8888);
        PrintStream check = new PrintStream(cs.getOutputStream());
        check.println("Connection established.");        
        DataOutputStream outStream = new DataOutputStream(cs.getOutputStream());
        DataInputStream inStream = new DataInputStream(cs.getInputStream());
        InputStreamReader isr = new InputStreamReader(inStream);
        BufferedReader br = new BufferedReader(isr);
        
        
        Scanner kb = new Scanner(System.in);
        boolean cont = true;
        int n = 0;
        //get user's intent
        do{
            do{
                System.out.println("\nDisplay(1), Deposit(2), Withdraw(3), Exit(4)");
                System.out.println(line);
                //input validation
                try{
                    n=0;
                    n = kb.nextInt();        
                    outStream.writeInt(n);
                    if(n>4||n<=0){
                        System.out.println("Invalid Selection.");
                        n=0;                        
                    }                    
                } catch(java.util.InputMismatchException z){
                    System.out.println("Invalid Selection.");
                    kb.next();
                }
            }while(n==0);                   
                    
            //ask the user how much to deposit/withdraw
            if(n>1 && n<4){
                while (true){
                    System.out.print("How much?: ");
                    //more input validation
                    try{
                        double a = kb.nextDouble();                        
                        outStream.writeDouble(a);
                        break;
                    }catch(java.util.InputMismatchException y){
                        System.out.println("Invalid entry.");
                        kb.next();
                    }
                }
            }
            
            else if(n==4){
                cont=false;
                break;
            }
            System.out.println(br.readLine());
        }while(cont);
        cs.close();
        kb.close();
        outStream.close();
        inStream.close();
        br.close();
        isr.close();
    }
}
