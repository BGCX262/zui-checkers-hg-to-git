package zui.checkers.agents;

import java.util.Iterator;
import java.util.Set;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;

import zui.checkers.Game;
import zui.checkers.game.Move;
import zui.checkers.pieces.Piece;

public class MinimaxAgent extends Agent {

	private Set<Move> allMoves;
	private Iterator<Move> moveToCompute;
	
    public MinimaxAgent(Game game, int timeToThink, int attackDir) {
        super(game, timeToThink, attackDir);
    }

    @Override
    public Move act() {
    	
    	if(allMoves == null) { //sem ide v prvom volani act()
    		
    		//TODO dorobit clonovanie mapy
    		
    		allMoves = getAllMoves();
    		moveToCompute = allMoves.iterator();
    		setNextMove(allMoves.iterator().next());
    		
    		// vratim prvy mozny tah, je to robene takto aby som si neposunul povodny iterator
    		return getNextMove();
    		
    	}
    	
    	
    	
        return getBestMove();
    }
    
    private Move getBestMove() {
    	if(moveToCompute.hasNext()) {
    		Move m = moveToCompute.next();
    		int score = playGame(m);
    		if(score > getNextMove().score) {
        		setNextMove(m);
        	}
    	}else{
    		//TODO dosli mi moznosti, musim dat vediet ze moze ist dalsi
    		Thread actThread = Thread.currentThread();
    		synchronized(actThread) {
                actThread.notifyAll();
            }
    		
    	}
    	
    	return getNextMove();
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
    		int playedScore = playGame(m);
    		
    		if(player == this ) { // max
    			if(score < playedScore ) {
    				score = playedScore;
    			}
    		}else{ //min
    			if(score > playedScore ) {
    				score = playedScore;
    			}
    		}
    		
    	}
    	
    	return score;
    }

	@Override
    public void doTurnCleanup() {
	    super.doTurnCleanup();
	    allMoves = null;
	    
	    //TODO vratit klonovanu mapu naspat
    }
    
    

}
