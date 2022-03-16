import java.util.HashMap;
import java.util.Scanner;


public class Solution {
    
    static HashMap<Long, SlowCalculator> threadCalMap = new HashMap<>();
    
    public static void main( String[] args ) {

        Solution solution = new Solution();
        Scanner sc = new Scanner( System.in );
        
        boolean continuous = true;
        while( continuous ){
            
            System.out.println( "Enter a command" );
            String commend = sc.next();

            switch( commend ){
                case "start":
                    solution.startCalWithInput( sc.next() );
                    break;
                case "cancel":
                    solution.cancelCalWithInput( sc.next() );
                    break;
                case "running":
                    solution.getAllRunningCalculator();
                    break;
                case "get":
                    solution.getTheResultOfN( sc.next() );
                    break;
                case "exit":
                    solution.exitWithFinish( );
                    continuous = false;
                    break;
                case "abort":
                    solution.exitWithoutFinish( );
                    continuous = false;
                    break;
                default:
                    System.out.println( "Invalid command" );
            }
        }
    }

    public void startCalWithInput( String stringN ){

        SlowCalculator slowCalculator = new SlowCalculator( Long.parseLong( stringN ) );
        slowCalculator.getThread().start();
        threadCalMap.put( Long.parseLong( stringN ), slowCalculator );
    }

    public void cancelCalWithInput( String stringN ){
    
        for ( Long longN: threadCalMap.keySet() ){
            if ( longN == Long.parseLong( stringN ) ){
                threadCalMap.get( Long.parseLong( stringN ) ).setIfCancelled( true );
            }
        }
    }

    public void getAllRunningCalculator(){

        String out = " calculations running: "; 
        int num = 0;
        for( Long longN: threadCalMap.keySet() ){
            if ( threadCalMap.get( longN ).getIfFinished() == false &&
                threadCalMap.get( longN ).getIfCancelled() == false ){
                out += longN + " ";
                ++num;
            }
        }
        System.out.println( num + out );
    }

    public void getTheResultOfN( String stringN ){

        Integer result = null;
        for( Long longN: threadCalMap.keySet() ){
            if ( longN == Long.parseLong( stringN ) &&
                threadCalMap.get(longN).getIfFinished() == true ){
                result = threadCalMap.get( longN ).getResult();
            }
        }
        if ( result != null ){
            System.out.println( "result is " +  result );
        } else {
            System.out.println( "calculating" );
        }
    }

    public void exitWithFinish( ){
        for( Long longN : threadCalMap.keySet() ){
            try {
                threadCalMap.get( longN ).getThread().join();
            } catch ( InterruptedException e ){
                System.out.println( "join failed" );
            }
        }
    }

    public void exitWithoutFinish( ){
        for( Long longN : threadCalMap.keySet() ){
            threadCalMap.get( longN ).setIfCancelled( true );
        }
    }
}
