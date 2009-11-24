package zui.checkers.game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import zui.checkers.pieces.Map;
import zui.checkers.pieces.Piece;

/**
 * Akcia reprezentujuca pohyb figurky na suradnice <tt>(x, y)</tt>.
 * 
 * @author miso, lukas
 *
 */
public class Move {

    public final Piece piece;
    private Move ancestorMove;
    
    /**
    * <tt>true</tt> ak tento tah nie je definitivnym
    * rozhodnutim agenta, t.z. ze sa este moze zmenit.
    */
    private boolean tmp;
    
    public final int x;
    
    public final int y;
    
    public final Piece strickenPiece;
	public final int score;
    
    
    /**
     * @param piece Figurka, ktoru chceme tahat.
     * @param x x-ova suradnica tahu
     * @param y y-ova suradnica tahu
     * @param tmp <tt>true</tt> ak tento tah nie je definitivnym
     * rozhodnutim agenta, t.z. ze sa este moze zmenit.
     * @param strickenPieces preskocene figurky
     */
	public Move(Piece piece, int x, int y, boolean tmp, int score,
	        Piece strickenPiece) {
		this.piece = piece;
		this.x = x;
		this.y = y;
		this.tmp = tmp;
		this.score = score;
		this.strickenPiece = strickenPiece;
	}

	@Override
    public String toString() {
	    return "["+x+","+y+"], "+score;
    }
	
	public void setTemporary(boolean tmp) {
	    this.tmp = tmp;
	}
	
	public boolean isTemporary() {
	    return tmp;
	}
	
	public void setAncestorMove(Move m) {
		ancestorMove = m;
    }
	
	public Move getAncestorMove() {
    	return ancestorMove;
    }

	private Set<Move> getAncestorMoves() {
		Set<Move> buff = new HashSet<Move>();
		buff.add(this);
		Move m = ancestorMove; 
		while(m != null) {
			buff.add(m);
			m = m.getAncestorMove();
		}
		
		return buff;
	}

	public Set<Piece> getStrickenPieces() {
		Set<Piece> buff = new HashSet<Piece>();
    	for(Iterator<Move> i = getAncestorMoves().iterator(); i.hasNext();) {
    		Move m = i.next();
    		
    		if(m.strickenPiece != null) {
    			buff.add(m.strickenPiece);
    		}
    	}
    	
		return buff;
    }
	
	public void removePiecesOnMove(Map map) {
		Set<Piece> buffStrickenPieces =  getStrickenPieces(); 
		if(buffStrickenPieces.size() == 0) {
			return ;
		}
		
		for(Iterator<Piece> i = buffStrickenPieces.iterator(); i.hasNext();) {
			Piece p = i.next();
			map.removePiece(p);
		}
	}
	
	public void addPiecesOnMove(Map map) {
		Set<Piece> buffStrickenPieces =  getStrickenPieces(); 
		if(buffStrickenPieces.size() == 0) {
			return ;
		}
		
		for(Iterator<Piece> i = buffStrickenPieces.iterator(); i.hasNext();) {
			Piece p = i.next();
			map.setPiece(p.getX(), p.getY(), p);
		}
	}
	
	
}
