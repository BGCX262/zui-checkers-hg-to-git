package zui.checkers.pieces;

import java.awt.Image;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;

import zui.checkers.agents.Agent;
import zui.checkers.game.Move;

/**
 * Trieda reprezentujuca figurku patriacu konktretnemu hracovi/agentovi.
 * 
 * @author miso, lukas
 *
 */
public abstract class Piece {
    
    private Agent agent;
    
    private Map map;
    
    private int x;
    
    private int y;
    
    private Image image = null;
    
    protected Piece(Agent agent) {
        this.agent = agent;
    }
    
    protected void setMap(Map map) {
        this.map = map;
    }
    
    protected Map getMap() {
    	return map;
    }
    
    protected void setX(int x) {
        this.x = x;
    }
    
    protected void setY(int y) {
        this.y = y;
    }
    
    
    
    public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
     * @return Hrac/agent, ktoremu patri figurka na tomto policku, alebo
     * <tt>null</tt> ak na tomto policku nestoji ziadna figurka.
     */
    public Agent getAgent() {
        return agent;
    }
    
    /**
     * @return Vrati "pravu" diagonalu prechadzajucu polickom, na ktorom stoji
     * tato figurka.
     */
    public List<Piece> getRDiagonal() {
        return map.getRDiagonalAt(x, y);
    }
    
    /**
     * @return Vrati "lavu" diagonalu prechadzajucu polickom, na ktorom stoji
     * tato figurka.
     */
    public List<Piece> getLDiagonal() {
        return map.getLDiagonalAt(x, y);
    }
    
    /**
     * @return Image figurky.
     */
    public Image getImage() {
        if (image == null) {
            image = new ImageIcon(Piece.class.getResource(
                    "/zui/checkers/img/" + getImageId() + getAgent().attackDir + ".png")).getImage();
        }
        return image;
    }
    
    public void doMove(Move move) {
    	map.removePiece(this);
    	move.removePiecesOnMove(map);
    	map.setPiece(move.x, move.y, this);
    }
    
    public void doReverseMove(Piece actualPiece, Move ml, Piece tmpPiece) {
    	doMove(new Move(actualPiece, tmpPiece.getX(), tmpPiece.getY(), true, 0, null));
    	
    	// vratit vyhodene figurky
    	ml.addPiecesOnMove(map);
	    
    }
    
    protected abstract String getImageId();
    
    /**
     * @return mnozina moznych pohybov
     */
    public abstract Set<Move> getValidSteps();
    
}
