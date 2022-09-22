package p1;
import java.util.ArrayList;

/**
* Ballot.java  
* @author Kyle Bekken,John Cullom, Anna Frenz, and Naviin Vejaya Kumar
* Contains all functionalities to store the required functionalities of a ballot. It contains attributes to store the ballot ID and the ranking of the candidates within each ballot. Each ballot could have anything between one to four candidates.
*/
public class Ballot {
    private int ballotId;
    private int firstChoice;
 
    private int currentVote = 0;
    private int currentRank = 1;
    private ArrayList<Integer> choices;

    /**
    * Class constructor for IRV.
    * <p>
    * Intitalizes the list of choices for each ballot.
    */
    public Ballot(String input){
        if(input.substring(input.length() - 1).equals(",")){
            input = input + "0";
        }
        this.choices = new ArrayList<Integer>();
        String[] choicesArray = input.split(",");
        for(int i = 0; i < choicesArray.length; i++){
            if(choicesArray[i].equals("") || choicesArray[i].equals("0")){
                this.choices.add(0);
            }else{
                this.choices.add(Integer.parseInt(choicesArray[i]));
            }
        }
    }

    /**
    * Class constructor.
    * <p>
    * Intitalizes the ballot id and first choice fields of a ballot.
    */
    public Ballot(int ballotId, int firstChoice){
        this.ballotId = ballotId;
        this.firstChoice = firstChoice;
    }

    /**
    * Returns the ID of a ballot.
    *
    * @return ID of a ballot.
    */
    public int getBallotId(){
        return this.ballotId;
    }

    /**
    * Returns the candidate ID of the vote of a ballot.
    *
    * @return ID of a candidate.
    */
    public int getFirstChoice(){
        return this.firstChoice;
    }

    /**
    * Sets the id of a ballot.
    *
    * @param newId id of a ballot.
    */
    public void setBallotId(int newId){
        this.ballotId = newId;
    }

    /**
    * Returns integer indicating current candidate that ballot’s vote would count for.
    *
    * @return current vote of the ballot.
    */
    public int getCurrentVote(){
        return this.currentVote;
    }

    /**
    * Returns an integer value of the current choice that the ballot’s vote is counting towards.
    *
    * @return current rank of the ballot.
    */
    public int getCurrentRank(){
        return this.currentRank;
    }

    /**
    * Sets the integer value of the current candidate that the ballot’s vote would go towards.
    *
    * @param newVote current vote of the ballot.
    */
    public void setCurrentVote(int newVote){
        this.currentVote = newVote;
    }

    /**
    * Sets the integer value of the current choice that the ballot’s vote is counting towards.
    *
    * @param newRank current rank of the ballot.
    */
    public void setCurrentRank(int newRank){
        this.currentRank = newRank;
    }

    /**
    * Returns an array list containing the ballots desired votes
    *
    * @return the arraylist of choices.
    */
    public ArrayList<Integer> getChoices(){
        return this.choices;
    }
}