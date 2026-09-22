package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class SameGameModelTest {

    @Test
    public void testBoardSize() {
        SameGameModel model = new SameGameModel(8, 8, 3);

        assertEquals(8, model.getRows());
        assertEquals(8, model.getCols());
    }

    @Test
    public void testResetGame() {
        SameGameModel model = new SameGameModel(8, 8, 3);

        model.resetGame();

        assertEquals(0, model.getScore());
        assertFalse(model.isGameOver());
    }

    @Test
    public void testDifficultyValues() {
        assertEquals(8, Difficulty.EASY.getRows());
        assertEquals(8, Difficulty.EASY.getCols());
        assertEquals(3, Difficulty.EASY.getNumberOfColors());

        assertEquals(10, Difficulty.MEDIUM.getRows());
        assertEquals(10, Difficulty.MEDIUM.getCols());
        assertEquals(4, Difficulty.MEDIUM.getNumberOfColors());

        assertEquals(12, Difficulty.HARD.getRows());
        assertEquals(12, Difficulty.HARD.getCols());
        assertEquals(6, Difficulty.HARD.getNumberOfColors());
    }
}
