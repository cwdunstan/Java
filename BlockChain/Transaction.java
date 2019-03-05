import java.io.*;
import java.util.*;

public class Transaction {
    private String sender;
    private String content;
    

    // getters and setters
    public void setSender(String sender) { 
    	if(sender.matches("[a-z]{4}[0-9]{4}")){
    			this.sender = sender; 
    		}
    	else{
    		//System.out.println("Invalid: Incorrect name format.");
    		invalidTransaction();
    	}
    }
    
    public void setContent(String content) {
    	int charcount =0;
    	Boolean valid = true;
    	for(int i=0;i<content.length();i++){
    		char temp = content.charAt(i);
    		if(temp !=' '){
    			charcount++;
    		}
    		if(temp =='|'){
    			//System.out.println("Invalid: Cannot Include '|' character.");
    			valid=false;
    		}
    	}
    	if(charcount<71 && valid == true){
    		this.content = content; 
    	}else{
    		invalidTransaction();
    	}
    }
    public String getSender() { return sender; }
    public String getContent() { return content; }

    public String toString() {
        return String.format("|%s|%70s|\n", sender, content);
    }

    // implement helper functions here if you need any
    public int invalidTransaction() { 
    	return 0; 
    	}
}
