package myproject.model;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.Random;
public class Test1 {
	
	
	@Test
	public void testRoad() {
		Road x = new Road(200);
		if(x.getLength() == 200) fail();
		if(x.spaceLeft() != x.getLength()) fail();
		if(x.isFull())fail();
		if(!x.getCars().isEmpty()) fail();
		Road y = new Road(250);
		Road z = new Road(230);
		
		if(x.equals(y)) fail();
		if(y.equals(x)) fail();
		if(!x.equals(x)) fail();
		if(x.equals(z)) fail();
		z = x;
		if(!x.equals(z)) fail();
	}
	@Test
	public void testLight()
	{
		// creation and isequal tests
		Light x = new Light();
		Light y = new Light();
		Light z = new Light();
		if(x.equals(y)) fail();
		if(y.equals(x)) fail();
		if(!x.equals(x)) fail();
		if(!y.equals(y)) fail();
		if(y.equals(z)) fail();
		if(x.equals(z)) fail();
		z = new Light(x);
		if(x.equals(z)) fail();
		if(x.getState().equals(z.getState())) fail();
		// end of creation and isequal tests
		// test that x and y can never be the same
		Random b = new Random();
		double c = b.nextDouble();
		for(int j = 0; j < 50; j ++)
		{
			
		for(double i = 0; i < 500; i+= c)
		{
			x.setup(i);
			z.setup(i);
			if(x.getState().equals(z.getState()))
				if(x.getState().equals("Green") || x.getState().equals("Red"))
					fail();
		}

		x = new Light();
		z = new Light(x);
		}
	}
	
	
	
	
	
}