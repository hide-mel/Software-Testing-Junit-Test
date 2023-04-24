package swen90006.fotbot;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class PartitioningTests
{

    /*
    protected FotBot fotbot;

    
    //Any method annotated with "@Before" will be executed before each test,
    //allowing the tester to set up some shared resources.
    @Before public void setUp()
	throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
	fotbot = new FotBot();
	fotbot.register("userName1", "password1!");
    }

    //Any method annotated with "@After" will be executed after each test,
    //allowing the tester to release any shared resources used in the setup.
    @After public void tearDown()
    {
    }

    //Any method annotation with "@Test" is executed as a test.
    @Test public void aTest()
    {
	//the assertEquals method used to check whether two values are
	//equal, using the equals method
	final int expected = 2;
	final int actual = 1 + 1;
	assertEquals(expected, actual);
    }

    @Test public void anotherTest()
	throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
	fotbot.register("userName2", "password2!");

	//the assertTrue method is used to check whether something holds.
	assertTrue(fotbot.isUser("userName2"));
	assertFalse(fotbot.isUser("nonUser"));
    }

    @Test public void sampleFotBotTest()
	throws NoSuchUserException, IncorrectPasswordException	
    {
	fotbot.incrementCurrentDay(2);

	List<Integer> newSteps = list(new Integer [] {1000, 2000});

	//username1 is created in the setUp() method, which is run before every test
	fotbot.update("userName1", "password1!", newSteps);

	List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
	List<Integer> expected = list(new Integer [] {1000, 2000});
	assertEquals(expected, steps);	
    }
  
    //To test that an exception is correctly throw, specify the expected exception after the @Test
    @Test(expected = java.io.IOException.class) 
    public void anExceptionTest()
	throws Throwable
    {
	throw new java.io.IOException();
    }

    //This test should fail.
    //To provide additional feedback when a test fails, an error message
    //can be included
    @Test public void aFailedTest()
    {
	//include a message for better feedback
	final int expected = 2;
	final int actual = 1 + 2;
	//Uncomment the following line to make the test fail
	//assertEquals("Some failure message", expected, actual);
    }

    private List<Integer> list(Integer [] elements)
    {
	return new ArrayList<Integer>(Arrays.asList(elements));
    }
*/

    protected FotBot fotbot;

    private List<Integer> list(Integer [] elements)
    {
        return new ArrayList<Integer>(Arrays.asList(elements));
    }

    //add a default user and friend
    @Before public void setUp()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException, NoSuchUserException, IncorrectPasswordException
    {
        fotbot = new FotBot();
        fotbot.register("userName1", "password1!");
        //resister a friend of userName1
        fotbot.register("friendUsername1", "fpassword1!");
        //add a friend of userName1
        fotbot.addFriend("userName1", "password1!", "friendUsername1");
        //add friendUsername1 as friend of userName1
        fotbot.addFriend("friendUsername1", "fpassword1!", "userName1");
        //resister another non friend user
        fotbot.register("userName2", "password2!");
    }

    // register EC1 username.length()<4
    @Test(expected = InvalidUsernameException.class) 
    public void resister_EQ1()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("R1", "AZaz129@");
    }

    // register EC2 password.length()<8
    @Test(expected = InvalidPasswordException.class) 
    public void resister_EQ2()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Register2", "Abc1@");
    }

    // register EC3 no special char in password
    @Test(expected = InvalidPasswordException.class) 
    public void resister_EQ3()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Register3", "ABYZ1289");
    }

    // register EC4 at least 1 special char in password so no exception
    @Test public void resister_EQ4()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Register4", "AZabcxyz@");
        assertTrue(fotbot.isUser("Register4"));
    }

    // register EC5 username already exists
    @Test(expected = DuplicateUserException.class) 
    public void resister_EQ5()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Register5", "ABYZ1289@");
        fotbot.register("Register5", "ABYZ1289@");
    }

    

    //update EC1 days since last update - steps.length = 0
    @Test public void update_EQ1()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(2);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "password1!", newSteps);

        List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        List<Integer> expected = list(new Integer [] {1000, 2000});
        assertEquals(expected, steps);	
    }

    //update EC2 days since last update - steps.length = 1
    @Test public void update_EQ2()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "password1!", newSteps);

        List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        List<Integer> expected = list(new Integer [] {0, 1000, 2000});
        assertEquals(expected, steps);	
    }

    //update EC3 days since last update - steps.length > 1
    @Test public void update_EQ3()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(7);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "password1!", newSteps);

        List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        List<Integer> expected = list(new Integer [] {0, 0, 0, 0, 0, 1000, 2000});
        assertEquals(expected, steps);	
    }

    // update EC4 incorrect password
    @Test(expected = IncorrectPasswordException.class) 
    public void update_EQ4()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "Incorrect12@", newSteps);

        //List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        //List<Integer> expected = list(new Integer [] {0, 1000, 2000});
        //assertEquals(expected, steps);	
    }

    // update EC5 username does not exist
    @Test(expected = NoSuchUserException.class) 
    public void update_EQ5()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("nonExist", "password1!", newSteps);

        //List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        //List<Integer> expected = list(new Integer [] {0, 1000, 2000});
        //assertEquals(expected, steps);	
    }



    // getSteps EC1 targetUser = username
    @Test public void getSteps_EQ1()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "password1!", newSteps);

        List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        List<Integer> expected = list(new Integer [] {0, 1000, 2000});
        assertEquals(expected, steps);	
    }

    // getSteps EC2 targetUser = friend of username
    @Test public void getSteps_EQ2()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //friendUsername1 is created in the setUp() method, which is run before every test
        fotbot.update("friendUsername1", "fpassword1!", newSteps);

        List<Integer> steps = fotbot.getStepData("userName1", "password1!", "friendUsername1");
        List<Integer> expected = list(new Integer [] {0, 1000, 2000});
        assertEquals(expected, steps);	
    }

    // getSteps EC3 username = "FotBot.ADMIN_USERNAME"
    @Test public void getSteps_EQ3()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        //as admin, it should return empty list
        List<Integer> steps = fotbot.getStepData("userName1", "password1!", FotBot.ADMIN_USERNAME);
        List<Integer> expected = list(new Integer [] {});
        assertEquals(expected, steps);	
    }

    // getSteps EC4 targetUser = neither username, admin or friend of username
    @Test public void getSteps_EQ4()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username2 is created in the setUp() method, which is run before every test
        fotbot.update("userName2", "password2!", newSteps);

        //as userName2 is not related to userName1 it should return empty list
        List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName2");
        List<Integer> expected = list(new Integer [] {});
        assertEquals(expected, steps);	

        //List<Integer> expected = list(new Integer [] {0, 1000, 2000});
        //assertEquals("This failed because user cannot view non-related user info.", expected, steps);
    }

    // getSteps EC5 targetUser does not exist
    @Test(expected = NoSuchUserException.class) 
    public void getSteps_EQ5()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //nonExist is non existed user
        fotbot.update("nonExist", "password1!", newSteps);

        //List<Integer> steps = fotbot.getStepData("userName1", "password1!", "nonExist");
        //List<Integer> expected = list(new Integer [] {1000, 2000});
        //assertEquals(expected, steps);	 
    }

    // getSteps EC6 incorrect password
    @Test(expected = IncorrectPasswordException.class) 
    public void getSteps_EQ6()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username2 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "incorrect1!", newSteps);

        //as userName2 is not related to userName1 it should return empty list
        //List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        //List<Integer> expected = list(new Integer [] {});
        //assertEquals(expected, steps);	 
    }

    // getSteps EC7 username does not exist
    @Test(expected = NoSuchUserException.class) 
    public void getSteps_EQ7()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username2 is created in the setUp() method, which is run before every test
        fotbot.update("userName123", "password1!", newSteps);

        //as userName2 is not related to userName1 it should return empty list
        //List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        //List<Integer> expected = list(new Integer [] {});
        //assertEquals(expected, steps);	 
    }
}
