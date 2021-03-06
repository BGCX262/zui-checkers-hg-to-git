package zui.checkers.agents;

import java.util.Iterator;
import java.util.Set;

import zui.checkers.Game;
import zui.checkers.game.Move;
import zui.checkers.pieces.Piece;

public class MinimaxAgent extends Agent {

    private static final int LEVEL = 6;
    
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
    		if( allMoves != null) {
	    		moveToCompute = allMoves.iterator();
	    		setNextMove(allMoves.iterator().next());
    		}
    		
    		// vratim prvy mozny tah, je to robene takto aby som si neposunul povodny iterator
    		return getNextMove();
    		
    	}
    	
    	
    	
        return getBestMove();
    }
    
    private Move getBestMove() {
    	Move m;
    	if(moveToCompute.hasNext()) {
    		m = moveToCompute.next();
    		int score = playGame(m, LEVEL);
    		if(score > getNextMove().score) {
        		setNextMove(m);
        	}
    	}else{
    		// dosli mi moznosti, musim dat vediet ze moze ist dalsi
    		m = getNextMove();
    		m.setTemporary(false);
    	}
    	
    	return getNextMove();
    }
    
    private int playGame(Move m, int level) {
        if (--level == 0) {
            return getScore();
        }
        
    	Piece p = m.piece;
    	Piece tmpPiece = p.clone();
    	p.doMove(m);
    	
    	if(!p.getAgent().hasMoves()) { //koniec rekurzie
    		// krok spat
			p.doReverseMove(p, m, tmpPiece);
    		return getScore();
    	}
    	
    	Agent player = p.getAgent().getOpponent(); // vymenim hracov
    	int score = 0;
    	for(Iterator<Move> i = player.getAllMoves().iterator(); i.hasNext();){ // prejdem hracove moznosti
    		Move move = i.next();
    		int playedScore = playGame(move, level);
    		if(player == this ) { // ak robim tah ja hladam maximum
    			if(score < playedScore ) {
    				score = playedScore;
    			}
    		}else{ // ak robi tah super hladam minimum
    			if(score > playedScore ) {
    				score = playedScore;
    			}
    		}
    		
    	}
    	p.doReverseMove(p, m, tmpPiece);
    	return score;
    }

	@Override
    public void doTurnCleanup() {
	    super.doTurnCleanup();
	    allMoves = null;
	    
	    // vratit klonovanu mapu naspat
//	    getGame().setMap(getTmpMap());
    }

	@Override
    public void doBeforeAct() {
	    super.doBeforeAct();
//	    setTmpMap(getGame().getMap().clone());
    }
    
    

}
