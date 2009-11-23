package zui.checkers.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Set;

import zui.checkers.GUI;
import zui.checkers.Game;
import zui.checkers.pieces.Map;
import zui.checkers.pieces.Piece;

public class BoardMouseListener implements MouseListener {

	private Game game;
	
	private Piece selectedPiece;
	
	@Override
	public void mouseClicked(MouseEvent e) {

		int x = e.getX() / GUI.PIECE_SIZE + 1;
		int y = Map.SIZE - ( e.getY() / GUI.PIECE_SIZE);
		
		Piece nowSelected = game.getMap().getPieceAt(x, y);
		System.out.println("now selected piece: "+nowSelected);
		if(selectedPiece != null) { //ak uz mam vybratu figurku, srpavim tah
			if( nowSelected == null ) {
				Move m = getMove(x, y);
				System.out.println("selected move: "+m);
				if(m != null) {
					game.doMove(m);
					selectedPiece = null;
				}
				
				return ;
			}
		}
		
		selectedPiece = nowSelected;
		
		if( selectedPiece == null) { // netrafil ziadnu figurku
			return ;
		}
		if( !selectedPiece.getAgent().equals(game.getAgentOnTurn())) { // figurka hraca ktory nie je na rade
			selectedPiece = null;
			return ;
		}
		
//		Set<Move> moves = selectedPiece.getValidSteps();
//		for(Iterator<Move> i = moves.iterator(); i.hasNext();) {
//			Move m = i.next();
//			System.out.println("moves: "+m.toString());
//		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public BoardMouseListener(Game game) {
		super();
		this.game = game;
	}
	
	private Move getMove(int x, int y) {
		
		for(Iterator<Move> i = selectedPiece.getValidSteps().iterator();i.hasNext();) {
			Move m = i.next();
			System.out.println("moves: "+m.toString());
			if( m.x == x && m.y == y) {
				return m;
			}
		}
		
		return null;
	}
	
	

}
