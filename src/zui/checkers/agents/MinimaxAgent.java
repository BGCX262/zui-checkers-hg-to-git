package zui.checkers.agents;

import java.util.Iterator;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;

import zui.checkers.Game;
import zui.checkers.game.Move;
import zui.checkers.pieces.Piece;

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
    	Piece p = m.piece;
    	p.doMove(m);
    	
    	if(getGame().isEnd()) {
    		return getScore();
    	}
    	
    	Agent player = p.getAgent().getOpponent();
    	int score = 0;
    	for(Iterator<Move> i = player.getAllMoves().iterator(); i.hasNext();){
    		Move move = i.next();
    		
    		if(player.equals(this)) { // max
    			
    		}else{ //min
    			
    		}
    		score = playGame(m);
    	}
    	return 0;
    }

}
