package TestBookWorm;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import TestBookWorm.Object.BookTest;
import TestBookWorm.Object.UserTest;
import TestBookWorm.Object.AuthorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookTest.class,
        UserTest.class,
        AuthorTest.class

})

public class AllTests
{

}