package zui.checkers.pieces;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import zui.checkers.agents.Agent;
import zui.checkers.game.Move;

/**
 * Pesiak.
 * 
 * @author miso, lukas
 *
 */
public class Bishop extends Piece {
	
	private Map tmpMap;

    public Bishop(Agent agent) {
        super(agent);
    }

    @Override
    protected String getImageId() {
        return "bishop";
    }

	/* (non-Javadoc)
	 * @see zui.checkers.pieces.Piece#getValidSteps()
	 */
	@Override
	public Set<Move> getValidSteps( ) {
		Set<Move> moves = new HashSet();

		moves = getValidStepsRecursively(false,0, null);
		
		return moves;
	}
	
	private Set<Move> getValidStepsRecursively(boolean onlyScore, int score, Move ancestorMove) {
		Set<Move> moves = new HashSet();
		
		Move mr = getValid(getRDiagonal(), false, onlyScore, score);
		Move ml = getValid(getLDiagonal(), true, onlyScore, score);
		
		getNextMoves(mr, moves, score, ancestorMove);
		getNextMoves(ml, moves, score, ancestorMove);
		
		return moves;
	}
	
	
	private void getNextMoves(Move ml,  Set<Move> moves, int score, Move ancestorMove) {
//		Set<Move> buffMoves = new HashSet<Move>();
		if(ml != null) {
			moves.add(ml);
			ml.setAncestorMove(ancestorMove);
			if(ml.score > 0) {
				Piece tmpPiece = this.clone();
				
				doMove(ml);

				moves.addAll(getValidStepsRecursively(true, (score + 1), ml));
				
				// krok spat
				doReverseMove(this, ml, tmpPiece);
			}
		}
	}
	
	

	/**
	 * @param Diagonal figurky na danej diagonale
	 * @param isLeft je lava diagonala?
	 * @param onlyScore vrati iba pohyb ktory preskocil superovho 
	 * @return pohyb vyhovujuci kriteriam
	 */
	private Move getValid( List<Piece> Diagonal, boolean isLeft, boolean onlyScore, int previousScore) {
		Piece strickenPiece = null;
		int indexMe = Diagonal.indexOf(this) + getAgent().attackDir;
		
		int length = 0;
		int to;
		if(getAgent().attackDir == 1) {
			to = Diagonal.size();
			
		}else{
			to = 1;
			
		}
		
		int score = 0;
		for(int i = indexMe; (i * getAgent().attackDir) < to; i += getAgent().attackDir) {
			length += getAgent().attackDir;
			
			if( score == 0 && Math.abs(length) > 1) { //max 1 figurka / skok
				return null;
			}
			
			Piece piece = Diagonal.get(i);
			if(piece == null) { // volne policko hracej plochy
				if(onlyScore) { 
					if(Math.abs(length) == 1 ) { // volne policko hned v prvom pokuse znamena ze neskorujem
						return null;
					}
				}
				
				return getMove(length, isLeft, score+previousScore, strickenPiece);
			}else{ //obsadene policko
				if(piece.getAgent().equals(this.getAgent())) { //nemozem preskakovat svojich
					return null;
				}
				
				strickenPiece =  piece;
				
				score++;
			}
		}
		
		return null;
	}
    
	
	/**
	 * Vrati pohyb danej dlzky, neoveruje ci je dane miesto volne
	 * 
	 * @param length dlzka skoku
	 * @param isLeft leva diagonala?
	 * @param score skore vyhodenych figurok
	 * @return
	 */
	private Move getMove(int length, boolean isLeft, int score, Piece strickenPiece) {
		if(isLeft) {
			return new Move(this, getX()+(length * -1), getY()+length, true, score, strickenPiece);
		}else{
			return new Move(this, getX()+length, getY()+length, true, score, strickenPiece);
		}
		
	}

	@Override
    public Piece clone(){
	    Bishop b = new Bishop(this.getAgent());
	    b.setX(getX());
	    b.setY(getY());
	    b.setMap(getMap());
	    return b;
    }
	
	public Piece clone(Map m) {
		Piece b = clone();
		b.setMap(m);
		return b;
	}
	
	

}
