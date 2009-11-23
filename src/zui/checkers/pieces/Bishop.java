package zui.checkers.pieces;

import java.awt.Image;
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

		moves = getValidStepsRecursively(false);
		
		return moves;
	}
	
	private Set<Move> getValidStepsRecursively(boolean onlyScore) {
		Set<Move> moves = new HashSet();
		
		Move mr = getValid(getRDiagonal(), false, onlyScore);
		Move ml = getValid(getLDiagonal(), true, onlyScore);
		
		getNextMoves(mr, moves);
		getNextMoves(ml, moves);
		
		return moves;
	}
	
	
	private void getNextMoves(Move ml,  Set<Move> moves) {
//		Set<Move> buffMoves = new HashSet<Move>();
		if(ml != null) {
			moves.add(ml);
			if(ml.score > 0) {
				Piece tmpPiece = this.clone();
				
				doMove(ml);

				moves.addAll(getValidStepsRecursively(true));
				
				// krok spat
				doMove(new Move(this, tmpPiece.getX(), tmpPiece.getY(), true, 0));
			}
		}
	}
	
	/**
	 * @param Diagonal figurky na danej diagonale
	 * @param isLeft je lava diagonala?
	 * @param onlyScore vrati iba pohyb ktory preskocil superovho 
	 * @return pohyb vyhovujuci kriteriam
	 */
	private Move getValid( List<Piece> Diagonal, boolean isLeft, boolean onlyScore) {
		
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
				
				return getMove(length, isLeft, score);
			}else{ //obsadene policko
				if(piece.getAgent().equals(this.getAgent())) { //nemozem preskakovat svojich
					return null;
				}
				
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
	private Move getMove(int length, boolean isLeft, int score) {
		if(isLeft) {
			return new Move(this, getX()+(length * -1), getY()+length, true, score);
		}else{
			return new Move(this, getX()+length, getY()+length, true, score);
		}
		
	}

	@Override
    protected Bishop clone(){
	    Bishop b = new Bishop(this.getAgent());
	    b.setX(getX());
	    b.setY(getY());
	    b.setMap(getMap());
	    return b;
    }
	
	

}
