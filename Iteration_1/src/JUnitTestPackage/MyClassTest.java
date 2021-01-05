/**MET CS 622
 * Assignment 1
 * class myClassTest
 * by Iryna Chervachidze
 * May 16, 2020
 */
package JUnitTestPackage;
import static org.junit.Assert.*;
import org.junit.*;

import org.junit.Test;

//This class test MyClass
public class MyClassTest {

	@Test
	public void testReturnOne() {
		MyClass myclass = new MyClass();
		assertEquals(1, myclass.returnOne());
	}
}
