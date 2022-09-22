package p1;
import java.util.ArrayList;

/**
* Candidate.java  
* @author Kyle Bekken,John Cullom, Anna Frenz, and Naviin Vejaya Kumar
* Contains all functionalities to store the required functionalities of a candidate. It contains attributes to store the candidate's name, ID, votes, party, and a ballot data structure that keeps track of all the ballots.
*/
public class Candidate {
    private String name;
    private int id;
    private int votes;
    private Party party;
    private boolean winner;
    public boolean loser;
    private ArrayList<Ballot> ballotData;
    private ArrayList<Integer> votesAllocated;

    /**
    * Class constructor.
    * <p>
    * Intitalizes the name, id and votes fields of a candidate.
    */
    Candidate(String name, int id, int votes)
    {
        this.name = name;
        this.id = id;
        this.votes = votes;
        this.ballotData = new ArrayList<Ballot>();
        this.votesAllocated = new ArrayList<Integer>();
        this.winner = false;
        this.loser = false;
    }

    /**
    * Class constructor.
    * <p>
    * Intitalizes the name, id, votes and party fields of a candidate.
    */
    Candidate(String name, int id, int votes, Party party)
    {
        this.name = name;
        this.id = id;
        this.votes = votes;
        this.party = party;
    }

    /**
    * Returns the name of a Candidate.
    *
    * @return name of a Candidate.
    */
    public String getName(){
        return this.name;
    }

    /**
    * Sets the name of a Candidate.
    *
    * @param name name of a Candidate.
    */
    public void setName(String name){
        this.name = name;
    }

    /**
    * Returns the ID of a Candidate.
    *
    * @return ID of a Candidate.
    */
    public int getId(){
        return this.id;
    }

    /**
    * Sets the id of a Candidate.
    *
    * @param id id of a Candidate.
    */
    public void setId(int id){
        this.id = id;
    }

    /**
    * Returns the number of votes of a Candidate.
    *
    * @return number of votes of a Candidate.
    */
    public int getVotes(){
        return this.votes;
    }

    /**
    * Adds one to the number of votes of a Candidate.
    *
    */
    public void addVote(){
        this.votes = this.votes + 1;
    }

    /**
    * Returns the party of a Candidate.
    *
    * @return party of a Candidate.
    */
    public Party getParty(){
        return this.party;
    }

    /**
    * Sets the party of a Candidate.
    *
    * @param party party of a Candidate.
    */
    public void setParty(Party party){
        this.party = party;
    }

    /**
    * Returns true if the Candidate has been declared a winner.
    *
    * @return true if the Candidate has been declared a winner.
    */
    public boolean getWinner(){
        return this.winner;
    }

    /**
    * Sets status to true if the Candidate has been declared a loser.
    *
    * @param result is the Candidate a loser.
    */
    public void setLoser(boolean result){
        this.loser = result;
    }

        /**
    * Returns true if the Candidate has been declared a loser.
    *
    * @return true if the Candidate has been declared a loser.
    */
    public boolean getLoser(){
        return this.loser;
    }

    /**
    * Sets status to true if the Candidate has been declared a winner.
    *
    * @param result is the Candidate a winner.
    */
    public void setWinner(boolean result){
        this.winner = result;
    }

    /**
    * Returns the array containing ballots counted towards the Candidate.
    *
    * @return array containing ballots counted towards the Candidate.
    */
    public ArrayList<Ballot> getBallotData(){
        return this.ballotData;
    }

    /**
    * Add new votes to be allocated to a candidate.
    *
    * @param newVote vote to be added.
    */
    public void addToVotesAllocated(int newVote){
        this.votesAllocated.add(newVote);
    }

    /**
    * Returns an arrayList to get votes that are allocated to a candidate.
    *
    * @return array list containing allocated votes counted towards the Candidate.
    */
    public ArrayList<Integer> getVotesAllocated(){
        return this.votesAllocated;
    }

    /**
    * Adds a new ballot object to the ballot data structure.
    *
    * @param newBallot new ballot object.
    */
    public void addBallot(Ballot newBallot){
        this.ballotData.add(newBallot);
    }
}