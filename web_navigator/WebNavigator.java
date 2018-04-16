//Blake Crowther
package web_nav;

import java.util.Scanner;
import java.util.Stack;

public class WebNavigator {
	// Field
    private String current; // Tracks currently visited site
    private Stack<String> webHistory;
    private int currentPosition;
    private Stack<String> forwardHistory;
    
    // Constructor
    WebNavigator () {
    	webHistory = new Stack<String>();
    	forwardHistory = new Stack<String>();
        current = null;
        currentPosition = 0;
    }
    
    // Methods
    // [!] YOU DO NOT HAVE TO MODIFY THIS METHOD FOR YOUR SOLUTION
    public boolean getNextUserCommand (Scanner input) {
        String command = input.nextLine();
        String[] parsedCommand = command.split(" ");
        
        // Switch on the command (issued first in input line)
        switch(parsedCommand[0]) {
        case "exit":
            System.out.println("Goodbye!");
            return false;
        case "visit":
            visit(parsedCommand[1]);
            break;
        case "back":
            back();
            break;
        case "forward":
            forw();
            break;
        default:
            System.out.println("[X] Invalid command, try again");
        }
        
        System.out.println("Currently Visiting: " + current);
        
        return true;
    }
    
    /*
     *  Visits the current site, clears the forward history,
     *  and records the visited site in the back history
     */
    public void visit (String site) {
    	while(forwardHistory.size() != 0) {
    		forwardHistory.pop();
    	}
    	webHistory.push(site);
    	current = site;
    	currentPosition = 0;
    	
    }
    
    /*
     *  Changes the current site to the one that was last
     *  visited in the order on which visit was called on it
     */
    public void back () {
    	if(webHistory.size() == 1) {
    		return;
    	}
    	forwardHistory.push(webHistory.peek());
    	webHistory.pop();
    	current = webHistory.peek();
    	currentPosition++;
    }
    
    public void forw () {
    	if(currentPosition == 0) {
    		return;
    	}
    	webHistory.push(forwardHistory.peek());
    	forwardHistory.pop();
    	current = webHistory.peek();
    	currentPosition--;
    }
	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WebNavigator navi = new WebNavigator();
        
        System.out.println("Welcome to ForneyFox, enter a command from your ForneyFox user manual!");
        while (navi.getNextUserCommand(input)) {}
        System.out.println("Goodbye!");

	}

}
