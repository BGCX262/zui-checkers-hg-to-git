package zui.checkers.agents;

import javax.swing.JPanel;

import zui.checkers.Game;
import zui.checkers.game.BoardMouseListener;
import zui.checkers.game.Move;

public class HumanAgent extends Agent {
	

    public HumanAgent(Game game, int timeToThink, int attackDir) {
        super(game, timeToThink, attackDir);
    }

    @Override
    public Move act() {
    	setMouseListener();
    	
    	try{
    		wait();
    	}catch (Exception e) {
			throw new AssertionError("Wait interupted!");
		}
    	
        return getNextMove();
    }
    
    private void setMouseListener() {
    	getGame().removeBoardListener();
    	
    	JPanel canvas = getGame().getGui().getBoard();
    	canvas.addMouseListener(new BoardMouseListener(getGame(), this));
    }

}
