package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardFormTest {

	/**
	 * Tests if a combination of form and rotation should have a possible connection on the left side
	 */
	@Test
	public void testLeftConnection() {
		assertTrue(CardForm.CROSS.hasLeftConnection(Rotation.NORTH));
		assertTrue(CardForm.CROSS.hasLeftConnection(Rotation.SOUTH));
		assertTrue(CardForm.CROSS.hasLeftConnection(Rotation.WEST));
		assertTrue(CardForm.CROSS.hasLeftConnection(Rotation.EAST));

		assertFalse(CardForm.END.hasLeftConnection(Rotation.NORTH));
		assertFalse(CardForm.END.hasLeftConnection(Rotation.SOUTH));
		assertTrue(CardForm.END.hasLeftConnection(Rotation.WEST));
		assertFalse(CardForm.END.hasLeftConnection(Rotation.EAST));

		assertFalse(CardForm.STRAIGHT.hasLeftConnection(Rotation.NORTH));
		assertFalse(CardForm.STRAIGHT.hasLeftConnection(Rotation.SOUTH));
		assertTrue(CardForm.STRAIGHT.hasLeftConnection(Rotation.WEST));
		assertTrue(CardForm.STRAIGHT.hasLeftConnection(Rotation.EAST));

		assertFalse(CardForm.CURVE.hasLeftConnection(Rotation.NORTH));
		assertTrue(CardForm.CURVE.hasLeftConnection(Rotation.SOUTH));
		assertTrue(CardForm.CURVE.hasLeftConnection(Rotation.WEST));
		assertFalse(CardForm.CURVE.hasLeftConnection(Rotation.EAST));
		
		assertTrue(CardForm.T_CROSS.hasLeftConnection(Rotation.NORTH));
		assertTrue(CardForm.T_CROSS.hasLeftConnection(Rotation.SOUTH));
		assertTrue(CardForm.T_CROSS.hasLeftConnection(Rotation.WEST));
		assertFalse(CardForm.T_CROSS.hasLeftConnection(Rotation.EAST));
	}
	
	/**
	 * Tests if a combination of form and rotation should have a possible connection on the right side
	 */
	@Test
	public void testRightConnection() {
		assertTrue(CardForm.CROSS.hasRightConnection(Rotation.NORTH));
		assertTrue(CardForm.CROSS.hasRightConnection(Rotation.SOUTH));
		assertTrue(CardForm.CROSS.hasRightConnection(Rotation.WEST));
		assertTrue(CardForm.CROSS.hasRightConnection(Rotation.EAST));

		assertFalse(CardForm.END.hasRightConnection(Rotation.NORTH));
		assertFalse(CardForm.END.hasRightConnection(Rotation.SOUTH));
		assertFalse(CardForm.END.hasRightConnection(Rotation.WEST));
		assertTrue(CardForm.END.hasRightConnection(Rotation.EAST));

		assertFalse(CardForm.STRAIGHT.hasRightConnection(Rotation.NORTH));
		assertFalse(CardForm.STRAIGHT.hasRightConnection(Rotation.SOUTH));
		assertTrue(CardForm.STRAIGHT.hasRightConnection(Rotation.WEST));
		assertTrue(CardForm.STRAIGHT.hasRightConnection(Rotation.EAST));

		assertTrue(CardForm.CURVE.hasRightConnection(Rotation.NORTH));
		assertFalse(CardForm.CURVE.hasRightConnection(Rotation.SOUTH));
		assertFalse(CardForm.CURVE.hasRightConnection(Rotation.WEST));
		assertTrue(CardForm.CURVE.hasRightConnection(Rotation.EAST));
		
		assertTrue(CardForm.T_CROSS.hasRightConnection(Rotation.NORTH));
		assertTrue(CardForm.T_CROSS.hasRightConnection(Rotation.SOUTH));
		assertFalse(CardForm.T_CROSS.hasRightConnection(Rotation.WEST));
		assertTrue(CardForm.T_CROSS.hasRightConnection(Rotation.EAST));
	}
	
	/**
	 * Tests if a combination of form and rotation should have a possible connection on the top side
	 */
	@Test
	public void testTopConnection() {
		assertTrue(CardForm.CROSS.hasTopConnection(Rotation.NORTH));
		assertTrue(CardForm.CROSS.hasTopConnection(Rotation.SOUTH));
		assertTrue(CardForm.CROSS.hasTopConnection(Rotation.WEST));
		assertTrue(CardForm.CROSS.hasTopConnection(Rotation.EAST));

		assertTrue(CardForm.END.hasTopConnection(Rotation.NORTH));
		assertFalse(CardForm.END.hasTopConnection(Rotation.SOUTH));
		assertFalse(CardForm.END.hasTopConnection(Rotation.WEST));
		assertFalse(CardForm.END.hasTopConnection(Rotation.EAST));

		assertTrue(CardForm.STRAIGHT.hasTopConnection(Rotation.NORTH));
		assertTrue(CardForm.STRAIGHT.hasTopConnection(Rotation.SOUTH));
		assertFalse(CardForm.STRAIGHT.hasTopConnection(Rotation.WEST));
		assertFalse(CardForm.STRAIGHT.hasTopConnection(Rotation.EAST));

		assertTrue(CardForm.CURVE.hasTopConnection(Rotation.NORTH));
		assertFalse(CardForm.CURVE.hasTopConnection(Rotation.SOUTH));
		assertTrue(CardForm.CURVE.hasTopConnection(Rotation.WEST));
		assertFalse(CardForm.CURVE.hasTopConnection(Rotation.EAST));
		
		assertTrue(CardForm.T_CROSS.hasTopConnection(Rotation.NORTH));
		assertFalse(CardForm.T_CROSS.hasTopConnection(Rotation.SOUTH));
		assertTrue(CardForm.T_CROSS.hasTopConnection(Rotation.WEST));
		assertTrue(CardForm.T_CROSS.hasTopConnection(Rotation.EAST));
	}
	
	/**
	 * Tests if a combination of form and rotation should have a possible connection on the bottom side
	 */
	@Test
	public void testBottomConnection() {
		assertTrue(CardForm.CROSS.hasBottomConnection(Rotation.NORTH));
		assertTrue(CardForm.CROSS.hasBottomConnection(Rotation.SOUTH));
		assertTrue(CardForm.CROSS.hasBottomConnection(Rotation.WEST));
		assertTrue(CardForm.CROSS.hasBottomConnection(Rotation.EAST));

		assertFalse(CardForm.END.hasBottomConnection(Rotation.NORTH));
		assertTrue(CardForm.END.hasBottomConnection(Rotation.SOUTH));
		assertFalse(CardForm.END.hasBottomConnection(Rotation.WEST));
		assertFalse(CardForm.END.hasBottomConnection(Rotation.EAST));

		assertTrue(CardForm.STRAIGHT.hasBottomConnection(Rotation.NORTH));
		assertTrue(CardForm.STRAIGHT.hasBottomConnection(Rotation.SOUTH));
		assertFalse(CardForm.STRAIGHT.hasBottomConnection(Rotation.WEST));
		assertFalse(CardForm.STRAIGHT.hasBottomConnection(Rotation.EAST));

		assertFalse(CardForm.CURVE.hasBottomConnection(Rotation.NORTH));
		assertTrue(CardForm.CURVE.hasBottomConnection(Rotation.SOUTH));
		assertFalse(CardForm.CURVE.hasBottomConnection(Rotation.WEST));
		assertTrue(CardForm.CURVE.hasBottomConnection(Rotation.EAST));
		
		assertFalse(CardForm.T_CROSS.hasBottomConnection(Rotation.NORTH));
		assertTrue(CardForm.T_CROSS.hasBottomConnection(Rotation.SOUTH));
		assertTrue(CardForm.T_CROSS.hasBottomConnection(Rotation.WEST));
		assertTrue(CardForm.T_CROSS.hasBottomConnection(Rotation.EAST));
	}

}
