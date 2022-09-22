package p1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class BallotTest {
    
    private Ballot ballot1 = new Ballot(1, 5);;

   
    public void setUp() {
        this.ballot1 = new Ballot(1, 5);
        // before each test, it will call this function. This is where you set up all of the mock objects that do the testing.
        ballot1.setCurrentVote(1);
        
        ballot1.setCurrentRank(1);
        
        
        
    }

    @Test
    public void testConstructorOPL(){
    setUp();
        //setUp();
        System.out.println("Ballot: Testing Constructor for OPL");
        assertEquals(1, ballot1.getBallotId());
        assertEquals(5, ballot1.getFirstChoice());
    }

    @Test
    public void testConstructorIRV(){
    setUp();
        //setUp();
        System.out.println("Ballot: Testing Constructor for IRV");
        String input = "3,1,2,,";
        Ballot testBallot = new Ballot(input);

        assertEquals(3, (int) testBallot.getChoices().get(0));
        assertEquals(1, (int) testBallot.getChoices().get(1));
        assertEquals(2, (int) testBallot.getChoices().get(2));
        assertEquals(0, (int) testBallot.getChoices().get(3));
        assertEquals(0, (int) testBallot.getChoices().get(4));
    }

    @Test
    public void testGetBallotId(){
    setUp();
        //setUp();
        System.out.println("Ballot: Testing Get Ballot ID");
        int expected1 = 1;
        int actual1 = ballot1.getBallotId();

        assertEquals(expected1, actual1);

        ballot1.setBallotId(5);

        int expected5 = 5;
        int actual5 = ballot1.getBallotId();

        assertEquals(expected5, actual5);

    }

    @Test
    public void testGetCurrentVote(){
    setUp();
        //setUp();
        System.out.println("Ballot: Testing Get Current Vote");
        int expected1 = 1;
        int actual1 = ballot1.getCurrentVote();

        assertEquals(expected1, actual1);

    }

    @Test
    public void testGetCurrentRank(){
    setUp();
        System.out.println("Ballot: Testing Get Current Rank");
        int expected1 = 1;
        int actual1 = ballot1.getCurrentRank();



        assertEquals(expected1, actual1);


    }

    @Test
    public void testGetChoices(){
    setUp();
        //setUp();
        System.out.println("Ballot: Testing Get Choices");
        String input = "1,,2,3";
        Ballot testBallot = new Ballot(input);
        assertEquals(1, (int) testBallot.getChoices().get(0));
        assertEquals(0, (int) testBallot.getChoices().get(1));
        assertEquals(2, (int) testBallot.getChoices().get(2));
        assertEquals(3, (int) testBallot.getChoices().get(3));

    }



}
