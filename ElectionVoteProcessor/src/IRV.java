package p1;
import java.util.*;

/**
* IRV.java  
* @author Kyle Bekken,John Cullom, Anna Frenz, and Naviin Vejaya Kumar
* Contains all functionalities of an IRV voting type. It inherits all available functionalities from VotingSystem and contains attributes to store the shuffle status and a candidate data structure that keeps track of all the candidates and the ballots of their respective votes.
*/
public class IRV extends VotingSystem {
    private boolean isShuffle = true;
    private ArrayList<Candidate> candidateDataStruct;
    private ArrayList<Candidate> winners;
    private ArrayList<Candidate> losers;
    

    /**
    * Class constructor.
    */
    public IRV(){
        super(); 
        this.candidateDataStruct = new ArrayList<Candidate>();
        this.winners = new ArrayList<Candidate>();
        this.losers = new ArrayList<Candidate>();
    }

    /**
    * Sets shuffle mechanism to be off.
    *
    */
    public void shuffleOff(){
        this.isShuffle = false;
    }
    
    /**
    * Gets the shuffle boolean.
    *
    */
    public boolean isShuffleOn(){
        return this.isShuffle;
    }

    /**
    * Places ballots into a random order if shuffle is set to on.
    *
    */
    public void shuffleBallots(){
        if (isShuffle){
            Collections.shuffle(this.getBallots());
        }
    }

    /**
    * Determines the winners of an IRV election
    *
    */
    public void runIRV(){
        int curWinner = 0;
        //number of votes needed for first round winner
        double droop = java.lang.Math.floor(getNumOfBallots() / (getNumOfSeats() + 1)) + 1.0;

        //shuffle ballots
        this.shuffleBallots();

        //loop through all ballots storing first vote to desired candidate 
        //System.out.println("starting first pass");
        for(int i = 0; i < this.getBallots().size(); i++ ){
            // for(int j = 0; j < this.getBallots().get(i).getChoices().size();j++){
            // Get Simulate the runoff
            int cur = this.getBallots().get(i).getCurrentRank();
            int count = 0;
            boolean foundRightVote = false;
            while(!foundRightVote){
                if(this.getBallots().get(i).getChoices().get(count) == cur){
                    // if we find a winner for the ballot that hasn't won yet
                    if(!this.getCandidates().get(count).getWinner()){
                        this.getCandidates().get(count).addToVotesAllocated(this.getBallots().get(i).getBallotId());
                        this.getCandidates().get(count).addVote();
                        writeToAuditFile("Ballot " + this.getBallots().get(i).getBallotId() + " given to " + this.getCandidates().get(count).getName() + "\n");
                        //System.out.println("Ballot " + this.getBallots().get(i).getBallotId() + " given to " + this.getCandidates().get(count).getName() + "\n");
                        
                        // if they reach droop, take them out 
                        if(this.getCandidates().get(count).getVotes() >= droop){
                            this.getCandidates().get(count).setWinner(true);
                            this.winners.add(this.getCandidates().get(count));
                            // write to audit file now:
                            curWinner++;
                            writeToAuditFile(this.getCandidates().get(count).getName() + " is given seat " + curWinner + "\n");
                            writeToResultFile(this.getCandidates().get(count).getName() + " is given seat " + curWinner + "\n");
                            //System.out.println("first pass droop winner!");
                        }
                        foundRightVote = true;
                    }else{
                        cur ++;
                        if(count > this.getBallots().get(i).getChoices().size()){
                            break;
                        }
                    }
                }
                count++;
            }
            this.getBallots().get(i).setCurrentRank(cur);
        }   
  
        // start next runoff
        int runOffCount = 0;
        while(winners.size() < this.getNumOfSeats()){
            runOffCount ++;
            if(winners.size() + losers.size() > this.getNumOfCandidates() - 1){
                break;
            }
            // check for biggest loser: then reallocate their votes
            int min = 0;
            while(this.getCandidates().get(min).getLoser()){
                min++;
            }
            for(int i = 0; i < this.getNumOfCandidates(); i++){
                //System.out.println(this.getCandidates().get(i).getName() + " has this many votes: " + this.getCandidates().get(i).getVotes());
                if(this.getCandidates().get(i).getVotes() < this.getCandidates().get(min).getVotes() && !this.getCandidates().get(i).getWinner() && !this.getCandidates().get(i).getLoser()){
                    //System.out.println(this.getCandidates().get(i).getName() + " i has " + this.getCandidates().get(i).getVotes() + ", " + this.getCandidates().get(min).getName() + " min has " + this.getCandidates().get(min).getVotes());
                    min = i;

                }else if(this.getCandidates().get(i).getVotes() == this.getCandidates().get(min).getVotes() && !this.getCandidates().get(i).getWinner() && !this.getCandidates().get(i).getLoser() && i != min){
                    boolean flip = this.coinFlip();
                    writeToAuditFile("Tie between Candidate " + this.getCandidates().get(i).getName() + " and Candidate " + this.getCandidates().get(min).getName() + " in determining who has the least number of votes in a cycle\n");
                    if(flip){
                        min = i;
                    }
                    writeToAuditFile("Candidate " + this.getCandidates().get(min).getName() + " won the coin flip: assigned to potentially lose\n");
                }
            }
            writeToAuditFile(this.getCandidates().get(min).getName() + " has the lowest number of votes for runoff number " + runOffCount + "\n");
            this.losers.add(this.getCandidates().get(min));
            this.getCandidates().get(min).setLoser(true);
            // Reallocate the votes.
            
            for(int i = 0; i < this.getCandidates().get(min).getVotesAllocated().size(); i++){

                int reallocatedId = this.getCandidates().get(min).getVotesAllocated().get(i);
                
                //writeToAuditFile("Ballot " + reallocatedId + " being reallocated from " + this.getCandidates().get(min).getName() + "\n");
                Ballot ballotToReAllocate = this.getBallots().get(i);
                for(int j = 0; j < this.getBallots().size(); j++){
                    if(this.getBallots().get(j).getBallotId() == reallocatedId){
                        ballotToReAllocate = this.getBallots().get(j);
                    }
                }
                ballotToReAllocate.setCurrentRank(ballotToReAllocate.getCurrentRank() + 1);
                //System.out.println(ballotToReAllocate.getCurrentRank() + ", " + reallocatedId + ", " + this.getCandidates().get(min).getName());
                //System.out.print("Ballot " + reallocatedId + " being reallocated from " + this.getCandidates().get(min).getName() + "\n");
                int count = 0;
                boolean foundRightVote = false;
                int cur = ballotToReAllocate.getCurrentRank();
                // for(int j = 0; j < ballotToReAllocate.getChoices().size(); j++){
                //     System.out.println(ballotToReAllocate.getChoices().get(j) + ", " + reallocatedId +  ", " + ballotToReAllocate.getBallotId());
                // }
                //System.out.println("");
                while(!foundRightVote){
                    if(count == ballotToReAllocate.getChoices().size()){
                        break;
                    }
                    //System.out.print(count + ", cur: " + cur + "\n");
                    //System.out.println(ballotToReAllocate.getChoices().get(count) + ", " + cur);
                    if(ballotToReAllocate.getChoices().get(count) == cur){
                        // if we find a winner for the ballot that hasn't won yet
                        if(!this.getCandidates().get(count).getWinner() && !this.getCandidates().get(count).getLoser()){
                            this.getCandidates().get(count).addToVotesAllocated(reallocatedId);
                            this.getCandidates().get(count).addVote();
                            //System.out.print("reallocating vote\n");
                            writeToAuditFile("Ballot " + reallocatedId + " reallocated to " + this.getCandidates().get(count).getName() + " from candidate " + this.getCandidates().get(min).getName() + "\n");
                            // if they reach droop, take them out 
                            if(this.getCandidates().get(count).getVotes() >= droop){
                                this.getCandidates().get(count).setWinner(true);
                                this.winners.add(this.getCandidates().get(count));
                                // write to audit file now:
                                curWinner++;
                                
                                writeToAuditFile(this.getCandidates().get(count).getName() + " is given seat " + curWinner + "\n");
                                writeToResultFile(this.getCandidates().get(count).getName() + " is given seat " + curWinner + "\n");
                               
                            }
                            foundRightVote = true;
                        }else{
                            //System.out.println("cur from " + cur + " to " + (cur + 1));
                            if(cur < ballotToReAllocate.getChoices().size()){
                                cur ++;
                                count = -1;
                                if(count > this.getBallots().get(i).getChoices().size()){
                                    break;
                                }
                            }
                        }
                    }

                    count++;
                }

            }
        }
        // if there are not enough winners, pull off the most recent losers to fill the seats
        int remainingSeats = this.getNumOfSeats() - this.winners.size();
        for(int i = 0; i < remainingSeats; i++){
            // add the most recent loser
            this.winners.add(this.losers.get(this.losers.size() - 1));
            //System.out.println(this.winners.get(0).getName());
            this.losers.get(this.losers.size() - 1).setWinner(true);
            this.losers.get(this.losers.size() - 1).setLoser(false);
            curWinner++;
            writeToAuditFile(this.winners.get(curWinner - 1).getName() + " is given seat " + curWinner + " by appointing loser\n");
            writeToResultFile(this.winners.get(curWinner - 1).getName() + " is given seat " + curWinner + "\n");
            //System.out.println("winner is " + this.winners.get(curWinner - 1).getName());
            this.losers.remove(this.losers.size() - 1);
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%20s %20s %20s %20s", "Candidates", "Votes", "Seat Won","                % Of Votes To % Of Seats");
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for(int i = 0; i < this.getCandidates().size(); i++){
            int winner = 0;
            if(this.getCandidates().get(i).getWinner()){
                winner = 1;
            }
            float percentOfVotes = ((float) this.getCandidates().get(i).getVotesAllocated().size() / (float) getNumOfBallots()) * 100;
            float percentOfSeats = ((float) winner / (float) this.getNumOfSeats()) * 100;
            //String percentToPercent = "% / " + percentOfSeats +"%";
            System.out.format("%20s %20d %20b", this.getCandidates().get(i).getName(), this.getCandidates().get(i).getVotesAllocated().size(), this.getCandidates().get(i).getWinner());
            System.out.printf("                  ");
            System.out.printf("%.2f " ,percentOfVotes);
            System.out.printf("  /  ");
            System.out.printf("%.2f ", percentOfSeats);
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

   /**
    * Returns an ArrayList of candidates in the election 
    *
    * @return ArrayList of candidates.
    */
    public ArrayList<Candidate> getCandidates(){
        return this.candidateDataStruct;
    }


    /**
    * Adds a candidate to the candidate data structure.
    *
    * @param c Candidate to be added.
    */
    public void addCandidate(Candidate c){
        this.candidateDataStruct.add(c);
    }

}
