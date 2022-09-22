package p1;

import java.util.*;
import java.io.*;
import java.util.Scanner;



/**
* Main.java  
* @author Kyle Bekken,John Cullom, Anna Frenz, and Naviin Vejaya Kumar
* Main class is responsible for reading in election data from the CSV file and calling
* the appropriate methods to run the program.
*
*/
public class Main {
    private int vt = -1;
    private OPL opl;
    private IRV irv;
    private PO po;
 

    public Main(){}

    /**
    * Reads election information off of the CSV file(s). 
    *
    * @param folder  name of the folder containing CSV file(s).
    * @param shuffleOff key in argument "S" to turn off the shuffle.
    */
    public void readIn(String folder, String shuffleOff) throws IOException {
        // try reading in the file with a scanner
        try{
            //Get list of files from directory
            File directoryPath = new File(folder);
            File filesList[] = directoryPath.listFiles();

            //Create audit and media file
            File fp2 = new File("misc/auditFile.txt");
            fp2.delete();
            File fp3 = new File("misc/resultFile.txt");
            fp3.delete();
            File fp4 = new File("misc/invalidBallots.txt");
            fp4.delete();

            int ballotId = 1;
            //read all files from directory
            for(File fp : filesList) {
                if(!(fp.getName().equals(".DS_Store"))){
                    System.out.println("File name: "+fp.getName());
                    Scanner sc = new Scanner(fp);
                    int i = 0;
                    
                    // Read Lines from the CSV file.
                    while (sc.hasNextLine()){
                        
                        if(i == 0){ // get voting type
                            
                            String line = sc.nextLine();
                            if(line.equals("IRV")){
                                if (this.irv == null){
                                    this.irv = new IRV();
                                    this.vt = 1;
                                    this.irv.writeToAuditFile("IRV Election\n");
                                    this.irv.writeToResultFile("IRV Election\n");
                                }
                            }else if (line.equals("OPL")){
                                if (this.opl == null){
                                    this.opl = new OPL();
                                    this.vt = 2;
                                    this.opl.writeToAuditFile("OPL Election\n");
                                    this.opl.writeToResultFile("OPL Election\n");
                                }
                            }else if (line.equals("PO")){
                                if (this.po == null){
                                    this.po = new PO();
                                    this.vt = 3;
                                    this.po.writeToAuditFile("PO Election\n");
                                    this.po.writeToResultFile("PO Election\n");
                                }
                            }else{
                                System.out.println("Unknown voting type: redo CSV file");
                            }
                            
                        }
                        else{
                            if(this.vt == -1){
                                break;
                            }
                            if(this.vt == 1){ // IRV
                                if (shuffleOff.equalsIgnoreCase("s")){
                                    this.irv.shuffleOff();
                                }
                                if(i == 1){ // get number of candidates
                                    String input = sc.nextLine();
                                    if (this.irv.getNumOfCandidates() == 0){
                                        this.irv.writeToAuditFile(Integer.parseInt(input) + " candidates running\n");
                                        this.irv.setNumberOfCandidates(Integer.parseInt(input));
                                    }
                                }else if(i == 2){ // number of seats
                                    String input = sc.nextLine();
                                    if (this.irv.getNumOfSeats() == 0){
                                        this.irv.writeToAuditFile(Integer.parseInt(input) + " number of seats to give out\n");
                                        this.irv.setNumOfSeats(Integer.parseInt(input));
                                    }
                                }
                                else if(i == 3){ // number of ballots
                                    String input = sc.nextLine();
                                    this.irv.writeToAuditFile("File "+ fp.getName() + " : " + Integer.parseInt(input) + " number of ballots\n");
                                    this.irv.setNumOfBallots(this.irv.getNumOfBallots() + Integer.parseInt(input));
                                }else if(i == 4){ // candidates in file
                                    String input = sc.nextLine();
                                    if (this.irv.getCandidates().size() == 0){
                                        String[] splitted = input.split(",");   
                                        for(int j = 0; j < splitted.length; j ++){
                                            // look for candidate to see if it's initiated
                                            Candidate newCandidate = new Candidate(splitted[j], j + 1, 0);
                                            this.irv.addCandidate(newCandidate);
                                            this.irv.writeToAuditFile("Candidate " + splitted[j] + "\n");
                                        }
                                    }
                                }else{ // ballot to be cast
                                
                                    // ballot to be cast
                                
                                    //checks validity
                                    String input = sc.nextLine();
                                    String[] temp = input.split(",", -1);
                                                
                                    //clears whitespace from array
                                    List<String> list = new ArrayList<String>();
                                    for(String s : temp) {
                                   		if(s != null && s.length() > 0) {
                    	            		list.add(s);
                                        }
                                    }
                                    temp = list.toArray(new String[list.size()]);
                                    if((this.irv.getNumOfCandidates() % 2) == 0){
                                    	if(temp.length >= (this.irv.getNumOfCandidates() / 2)){
                                    		Ballot newBallot = new Ballot(input);
                                    		newBallot.setBallotId(ballotId);
                                    		this.irv.storeBallot(newBallot);
                                    		ballotId++;
                                    	} else{
                                    		this.irv.writeToInvalidFile(input + "\n");
                                    	}
                                    }else{
                                    	int newMax = this.irv.getNumOfCandidates() + 1;
                                    	if(temp.length >= (newMax / 2)){
                                    		Ballot newBallot = new Ballot(input);
                                    		newBallot.setBallotId(ballotId);
                                    		this.irv.storeBallot(newBallot);
                                    		ballotId++;
                                    	}else{
                                    		this.irv.writeToInvalidFile(input + "\n");
                                    	}
                                    }
                                
                                }
                                
                            }else if (this.vt == 2){ // OPL
                                if(i == 1){ // get number of candidates
                                    String input = sc.nextLine();
                                    if (this.opl.getNumOfCandidates() == 0){
                                        this.opl.writeToAuditFile(Integer.parseInt(input) + " candidates running\n");
                                        this.opl.setNumberOfCandidates(Integer.parseInt(input));
                                    }
                                }
                                else if(i == 2){ // number of seats
                                    String input = sc.nextLine();
                                    if (this.opl.getNumOfSeats() == 0){
                                        this.opl.writeToAuditFile(Integer.parseInt(input) + " number of seats to give out\n");
                                        this.opl.setNumOfSeats(Integer.parseInt(input));
                                    }
                                    
                                }else if(i == 3){ // num of ballots
                                    String input = sc.nextLine();
                                    this.opl.writeToAuditFile("File "+fp.getName() + " : " + Integer.parseInt(input) + " number of ballots\n");
                                    this.opl.setNumOfBallots(this.opl.getNumOfBallots() + Integer.parseInt(input));
                                }else if(i == 4){ // candidates in file
                                    String input = sc.nextLine();
                                    if (this.opl.getParties().size() == 0){
                                        String newInput = input.replace("[", "");
                                        newInput = newInput.replace("]", "");
                                        String[] splitted = newInput.split(",");
                                        for(int j = 0; j < splitted.length; j += 2){
                                            
                                            // look for party to see if it'ss initiated
                                            if(j == 0){
                                                this.opl.addParty(new Party(splitted[1], 0, 0));
                                                Candidate newCandidate = new Candidate(splitted[0], 1, 0);
                                                this.opl.getParties().get(0).addCandidate(newCandidate);
                                                this.opl.writeToAuditFile("Candidate " + splitted[0] + " of party " +  this.opl.getParties().get(0).getName() + "\n");
                                            }else{
                                                boolean foundParty = false;
                                                for(int k = 0; k < this.opl.getParties().size(); k++){
                                                    if(splitted[j + 1].equals(this.opl.getParties().get(k).getName())){
                                                        foundParty = true;
                                                    }
                                                }
                                                if(!foundParty){
                                                    this.opl.addParty(new Party(splitted[j+1], 0, 0));
                                                }
                                                // add the candidate to the party
                                                for(int k = 0; k < this.opl.getParties().size(); k++){
                                                    if(splitted[j + 1].equals(this.opl.getParties().get(k).getName())){
                                                        Candidate newCandidate = new Candidate(splitted[j], (j / 2) + 1, 0);
                                                        this.opl.getParties().get(k).addCandidate(newCandidate);
                                                        this.opl.writeToAuditFile("Candidate " + splitted[j] + " of party " +  this.opl.getParties().get(k).getName() + "\n");
                                                    }
                                                }
                                            }
                                        }
                                    }   
                                    
                                }else{ // ballot to be cast
                                    // cast a ballot and add it to the votingsystem's data structure
                                    String input = sc.nextLine();
                                    //System.out.println(input);
                                    String[] splitted = input.split(",");
                                    int candidateChoice = splitted.length;
                                    //int ballotId = i - 4;
                                    Ballot newBallot = new Ballot(ballotId, candidateChoice);
                                    this.opl.storeBallot(newBallot);
                                    for(int k = 0; k < this.opl.getParties().size(); k++){
                                        for(int j = 0; j < this.opl.getParties().get(k).getCandidates().size(); j++){
                                            if(candidateChoice == this.opl.getParties().get(k).getCandidates().get(j).getId()){
                                                //System.out.println(this.opl.getParties().get(k).getCandidates().get(j).getName());
                                                // vote for this candidate: add to both party and candidate
                                                this.opl.getParties().get(k).getCandidates().get(j).addVote();
                                                this.opl.getParties().get(k).getCandidates().get(j).addBallot(newBallot);
                                                this.opl.getParties().get(k).addVote();
                                                this.opl.writeToAuditFile("Ballot number " + ballotId + " voted for " +  this.opl.getParties().get(k).getCandidates().get(j).getName() + " of party " + this.opl.getParties().get(k).getName() + "\n");
                                                ballotId++;
                                            }
                                        }
                                    }

                                }
                            }else if (this.vt == 3){ // PO
                                if(i == 1){ // get number of candidates
                                    String input = sc.nextLine();
                                    if (this.po.getNumOfCandidates() == 0){
                                        this.po.writeToAuditFile(Integer.parseInt(input) + " candidates running\n");
                                        this.po.setNumberOfCandidates(Integer.parseInt(input));
                                    }
                                }else if(i == 2){ // candidates in file
                                    String input = sc.nextLine();
                                    if (this.po.getCandidates().size() == 0){
                                        String newInput = input.replace("[", "");
                                        newInput = newInput.replace("]", "");
                                        String[] splitted = newInput.split(",");  
                                        for(int j = 0; j < splitted.length; j +=2){
                                            // look for candidate to see if it's initiated
                                            Candidate newCandidate = new Candidate(splitted[j], j + 1, 0);
                                            this.po.addCandidate(newCandidate);
                                            this.po.writeToAuditFile("Candidate " + splitted[j] + "\n");
                                        }
                                    }
                                }else if(i == 3){ // num of ballots
                                    String input = sc.nextLine();
                                    this.po.writeToAuditFile("File "+fp.getName() + " : " + Integer.parseInt(input) + " number of ballots\n");
                                    this.po.setNumOfBallots(this.po.getNumOfBallots() + Integer.parseInt(input));
                                }else{ // ballot to be cast
                                    // cast a ballot and add it to the votingsystem's data structure
                                    String input = sc.nextLine();
                                    String[] splitted = input.split(",");
                                    int candidateChoice = splitted.length;
                                    Ballot newBallot = new Ballot(ballotId, candidateChoice);
                                    this.po.storeBallot(newBallot);
                                    this.po.getCandidates().get(candidateChoice - 1).addVote();
                                    // write the ballot choice to audit file
                                    this.po.writeToAuditFile("Ballot number " + ballotId + " voted for " +  this.po.getCandidates().get(candidateChoice - 1).getName() + "\n");
                                    ballotId++;
                                }
                            }
                        }
                        i++;
                    }
                    sc.close();
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Input File Not Found");
            return;
        }
    }

    public static void main(String[] args) throws IOException{
        Main controller = new Main();
        // read folder in
        if(args.length < 1){
            System.out.println("invalid arguments: needs a folder");
            return;
        }

        // Resize array if shuffle is turned off
        String[] newArray = new String[2];
        newArray[0] = args[0];
        

        if (args.length == 1){
            newArray[1] = "";
        }
        else if (args.length == 2){
            newArray[1] = args[1];
        }

        // Reading files within folders
        controller.readIn(newArray[0], newArray[1]); 
        if(controller.vt == 2){
            controller.opl.runOPL();
        }else if (controller.vt == 1){
            controller.irv.runIRV();
        }else if (controller.vt == 3){
            controller.po.runPO();
        }
        // decide the type of voting system from first line
        // create instance of Voting system 
    }
}
