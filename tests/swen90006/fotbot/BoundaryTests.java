package swen90006.fotbot;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

//By extending PartitioningTests, we inherit tests from the script
public class BoundaryTests
    extends PartitioningTests
{
    /*
    //Add another test
    @Test public void anotherTest()
    {
	//include a message for better feedback
	final int expected = 2;
	final int actual = 2;
	assertEquals("Some failure message", expected, actual);
    }
    */

    private List<Integer> list(Integer [] elements)
    {
        return new ArrayList<Integer>(Arrays.asList(elements));
    }


    // register EC1 valid input, username doesn’t exist, username.length() = 3
    @Test(expected = InvalidUsernameException.class) 
    public void resister_EQ1()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Re1", "AZaz129@");
    }

    // register EC1 valid input, username doesn’t exist, username.length() = 0
    @Test(expected = InvalidUsernameException.class) 
    public void resister_EQ1_2()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("", "AZaz129@");
    }

    // register EC2 Valid input, username does not exist, username.length() = 4, password.length() = 7
    @Test(expected = InvalidPasswordException.class) 
    public void resister_EQ2()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Reg2", "Abcd12@");
    }

    // register EC2 Valid input, username does not exist, username.length() = 4, password.length() = 0
    @Test(expected = InvalidPasswordException.class) 
    public void resister_EQ2_2()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Reg2", "");
    }

    // register EC3 Valid input, username does not exist, username.length() = 4, password.length() = 8, no special char
    @Test(expected = InvalidPasswordException.class) 
    public void resister_EQ3()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Reg3", "ABYZ1289");
    }

    // register EC4 Valid input, username does not exist, username.length() = 4, password.length() = 8, special char
    @Test public void resister_EQ4()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Register4", "AZabcxyz@");
        assertTrue(fotbot.isUser("Register4"));
    }

    // register EC5 valid input, username exists
    @Test(expected = DuplicateUserException.class) 
    public void resister_EQ5()
    throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException
    {
        fotbot.register("Reg5", "ABYZ1289@");
        fotbot.register("Reg5", "ABYZ1289@");
    }

    

    //update EC1 Valid input, username exists, correct password, days since last update - steps.length = 0
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

    //update EC2 Valid input, username exists, correct password, days since last update - steps.length = 1
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

    //update EC3 Valid input, username exists, correct password, days since last update - steps.length = 2
    @Test public void update_EQ3()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(4);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "password1!", newSteps);

        List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        List<Integer> expected = list(new Integer [] {0, 0, 1000, 2000});
        assertEquals(expected, steps);	
    }

    // update EC4 Valid input, username exists, incorrect password
    @Test(expected = IncorrectPasswordException.class) 
    public void update_EQ4()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username1 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "Incor12@", newSteps);

        //List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        //List<Integer> expected = list(new Integer [] {0, 1000, 2000});
        //assertEquals(expected, steps);	
    }

    // update EC5 Valid input, username does not exist
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



    // getSteps EC1 valid input, username exists, correct password, targetUser exists, targetUser=username
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

    // getSteps EC2 valid input, username exists, correct password, targetUser exists, targetUser=friend of username
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

    // getSteps EC3 valid input, username exists, correct password, targetUser exists, targetUser= FotBot.ADMIN_USERNAME
    @Test public void getSteps_EQ3()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        //as admin, it should return empty list
        List<Integer> steps = fotbot.getStepData("userName1", "password1!", FotBot.ADMIN_USERNAME);
        List<Integer> expected = list(new Integer [] {});
        assertEquals(expected, steps);	
    }

    // getSteps EC4 valid input, username exists, correct password, targetUser exists, targetUser= neither username, admin nor friend of username
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

    // valid input, username exists, correct password, targetUser does not exist
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

    // getSteps EC6 valid input, username exists, incorrect password
    @Test(expected = IncorrectPasswordException.class) 
    public void getSteps_EQ6()
    throws NoSuchUserException, IncorrectPasswordException
    {
        fotbot.incrementCurrentDay(3);

        List<Integer> newSteps = list(new Integer [] {1000, 2000});

        //username2 is created in the setUp() method, which is run before every test
        fotbot.update("userName1", "incorr1!", newSteps);

        //as userName2 is not related to userName1 it should return empty list
        //List<Integer> steps = fotbot.getStepData("userName1", "password1!", "userName1");
        //List<Integer> expected = list(new Integer [] {});
        //assertEquals(expected, steps);	 
    }

    // getSteps EC7 valid input, username does not exist
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
