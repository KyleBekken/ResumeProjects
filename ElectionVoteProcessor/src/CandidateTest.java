package p1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class CandidateTest {
    
    private Candidate candidate = new Candidate("", 0, 0);


    public void setUp() {
        // before each test, it will call this function. This is where you set up all of the mock objects that do the testing.

        candidate.setName("Joe");
        candidate.setId(1);
        candidate.addVote();
        candidate.setWinner(true);
        candidate.setLoser(false);

        Party party = new Party("Party A", 0, 0);
        candidate.setParty(party);

        Ballot ballot = new Ballot(1, 5);
        candidate.addBallot(ballot);

        candidate.addToVotesAllocated(2);
        
    }

    @Test
    public void testConstructorIRV(){
    setUp();
    System.out.println("Candidate: Testing constructor for IRV");
        Candidate testCandidate = new Candidate("Goldy", 2, 3);
        String expectedName = "Goldy";
        String actualName = testCandidate.getName();
        assertEquals(expectedName, actualName);

        int expected = 2;
        int actual = testCandidate.getId();
        assertEquals(expected, actual);

        expected = 3;
        actual = testCandidate.getVotes();
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorOPL(){
    System.out.println("Candidate: Testing constructor for OPL");
    setUp();
        Party p = new Party("D", 0, 1);
        Candidate testCandidate = new Candidate("Goldy", 2, 3, p);
        String expectedName = "Goldy";
        String actualName = testCandidate.getName();
        assertEquals(expectedName, actualName);

        int expected = 2;
        int actual = testCandidate.getId();
        assertEquals(expected, actual);

        expected = 3;
        actual = testCandidate.getVotes();
        assertEquals(expected, actual);

        expectedName = "D";
        actualName = testCandidate.getParty().getName();
        assertEquals(expectedName, actualName);
    }


    @Test
    public void testGetName(){
    System.out.println("Candidate: Testing Get Name");
    setUp();
        String expected = "Joe";
        String actual = candidate.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetID(){
    System.out.println("Candidate: Testing Get ID");
    setUp();
        int expected = 1;
        int actual = candidate.getId();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetVotes(){
    System.out.println("Candidate: Testing Get Votes");
    setUp();
        int expected = 1;
        int actual = candidate.getVotes();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetParty(){
    System.out.println("Candidate: Testing Get Party");
    setUp();
        String expected = "Party A";
        String actual = candidate.getParty().getName();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetWinner(){
    System.out.println("Candidate: Testing Get Winner");
    setUp();
        boolean expected = true;
        boolean actual = candidate.getWinner();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetLoser(){
    System.out.println("Candidate: Testing Get Loser");
    setUp();
        boolean expected = false;
        boolean actual = candidate.getLoser();

        assertEquals(expected, actual);
    }

    /*
    @Test
    public void testGetBallotData(){
        String expected = ;
        String actual = party.getBallotData();

        assertEquals(expected, actual);
    }
    */

    @Test
    public void testGetBallotData(){
    setUp();
        //setUp();
        System.out.println("Candidate: Testing Get Ballot Data");
        int expected = 1;
        int actual = candidate.getBallotData().get(0).getBallotId();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetVotesAllocated(){
    setUp();
        //setUp();
        System.out.println("Candidate: Testing Votes Allocated");
        int expected = 2;
        int actual = candidate.getVotesAllocated().get(0);

        assertEquals(expected, actual);
    }
}
