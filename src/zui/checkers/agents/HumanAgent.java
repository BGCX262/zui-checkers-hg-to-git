package zui.checkers.agents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import zui.checkers.GUI;
import zui.checkers.Game;
import zui.checkers.game.Move;
import zui.checkers.pieces.Map;
import zui.checkers.pieces.Piece;

public class HumanAgent extends Agent implements MouseListener {
	
    private Thread actThread;
    
    private Piece selectedPiece;
    
    private Move move;
    
    public HumanAgent(Game game, int timeToThink, int attackDir) {
        super(game, timeToThink, attackDir);
        game.getGui().getBoard().addMouseListener(this);
    }
    
    @Override
    public Move act() {
    	try{
    	    actThread = Thread.currentThread();
    	    synchronized(actThread) {
    	        // budeme spinkat kym si uzivatel nevyklika pohyb na hracej ploche
    	        System.out.println("human - agent " + getGame().getAgentOnTurnId());
    	        System.out.println("human - wating");
    	        actThread.wait();
    	        System.out.println("human - notified");
    	        System.out.println("human - computed move: " + move);
    	    }
    	} catch (Exception e) {
    	    // wait je preruseny iba ked agentovi vyprsal cas (timeout)
		}
    	
        return move;
    }
    
    @Override
    public synchronized void doTurnCleanup() {
        System.out.println("human - cleanup");
        selectedPiece = null;
        move = null;
    }
    
    @Override
    public synchronized void mouseClicked(MouseEvent e) {
        if(getGame().getAgentOnTurn() != this) {
            // Moze sa stat, ze mouse click event pride s oneskorenim a to v case,
            // ked uz dany hrac nie je na rade.
            return;
        }
        int x = e.getX() / GUI.PIECE_SIZE + 1;
        int y = Map.SIZE - ( e.getY() / GUI.PIECE_SIZE);
        
        Piece nowSelected = getGame().getMap().getPieceAt(x, y);
        System.out.println("human - click: prev selection " + selectedPiece + "; current selection " + nowSelected);
        if(selectedPiece!=null && nowSelected==null) {
                move = getMove(x, y);
                if (move == null) {
                    // Uzivatel sa chce pohnut na policko, na ktore sa podla
                    // pravidiel pohnut nemoze
                    doTurnCleanup();
                } else {
                    // mame figurku a vieme aj kam s nou pohnut
                    move.setTemporary(false);
                    synchronized(actThread) {
                        actThread.notifyAll();
                    }
                }
        } else {
            selectedPiece = nowSelected;
            if(selectedPiece == null) {
                ; // uzivatel nevybral ziadnu figurku
            } else if(selectedPiece.getAgent() != this) {
                // uzivatel vybral figurku hraca, ktory nie je na rade
                selectedPiece = null;
            }
        }
    }

    private Move getMove(int x, int y) {
        for(Iterator<Move> i = selectedPiece.getValidSteps().iterator();i.hasNext();) {
            Move m = i.next();
            System.out.println("valid step: "+m.toString());
            if( m.x == x && m.y == y) {
                return m;
            }
        }
        return null;
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
