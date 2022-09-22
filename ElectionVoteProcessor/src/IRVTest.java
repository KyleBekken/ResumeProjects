package p1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class IRVTest {
    
    private IRV irv = new IRV();
    private Candidate candidate;

    public void setUp() {
        // before each test, it will call this function. This is where you set up all of the mock objects that do the testing.

        irv.shuffleOff();
	    this.irv = new IRV();
        this.candidate = new Candidate("name", 1, 20);

        irv.setVotingType(1);
        irv.setNumOfBallots(50);
        irv.setNumberOfCandidates(4);

        Ballot ballot = new Ballot(1, 5);
        irv.storeBallot(ballot);

        irv.addCandidate(candidate);
        
        
    }

    @Test
    public void testShuffleOff(){
        System.out.println("IRV: Testing Get Shuffle Off");
    	setUp();
        boolean expected = true;
        boolean actual = this.irv.isShuffleOn();

        assertEquals(expected, actual);
    }

    
    @Test
    public void testGetCandidates(){
    setUp();
        //setUp();
        System.out.println("IRV: Testing Get Candidates");
        int expected = 1;
        int actual = irv.getCandidates().get(0).getId();

        assertEquals(expected, actual);
    }


    @Test
    public void testGetVotingType(){
    setUp();
        System.out.println("IRV: Testing Get Voting Type");
        int expected = 1;
        int actual = irv.getVotingType();

        assertEquals(expected, actual);
    }

    
    @Test
    public void testGetNumOfBallots(){
    setUp();
        System.out.println("IRV: Testing Get Num of Ballots");
        int expected = 50;
        int actual = irv.getNumOfBallots();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumOfCandidates(){
    setUp();
        System.out.println("IRV: Testing Get Num of Candidates");
        int expected = 4;
        int actual = irv.getNumOfCandidates();

        assertEquals(expected, actual);
    }

    @Test
    public void testCoinFlip(){
    setUp();
        System.out.println("IRV: Testing Coin Flip");
        int heads = 0;
        for(int i = 0; i < 1000; i++){
            boolean result = this.irv.coinFlip();
            if(result){
                heads++;
            }
        }
        if(heads < 600 && heads > 400){
            assertEquals(true, true);
        }else{
            assertEquals(false, true);
        }
    }

    
    @Test
    public void testGetBallots(){
    setUp();
        System.out.println("IRV: Testing Get Ballots");
        int expected = 1;
        int actual = irv.getBallots().get(0).getBallotId();

        assertEquals(expected, actual);
    }
    
}
