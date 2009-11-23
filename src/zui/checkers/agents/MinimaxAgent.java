package zui.checkers.agents;

import java.util.Iterator;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;

import zui.checkers.Game;
import zui.checkers.game.Move;

public class MinimaxAgent extends Agent {

    public MinimaxAgent(Game game, int timeToThink, int attackDir) {
        super(game, timeToThink, attackDir);
    }

    @Override
    public Move act() {
        return null;
    }
    
    private Move getBestMove() {
    	
    	for(Iterator<Move> i = getAllMoves().iterator(); i.hasNext();){
    		Move m = i.next();
    		playGame(m);
    	}
    	
    	return null;
    }
    
    private int playGame(Move m) {
    	
    	return 0;
    }

}
