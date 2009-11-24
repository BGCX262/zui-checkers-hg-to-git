package zui.checkers;

import zui.checkers.agents.ABCutAgent;
import zui.checkers.agents.Agent;
import zui.checkers.agents.CutOffSearchAgent;
import zui.checkers.agents.HumanAgent;
import zui.checkers.agents.MinimaxAgent;
import zui.checkers.game.Move;
import zui.checkers.pieces.Bishop;
import zui.checkers.pieces.Map;
import zui.checkers.pieces.Piece;

public class Game {

    public static final String[] supportedAgents = {
        HumanAgent.class.getName(),
        ABCutAgent.class.getName(),
        CutOffSearchAgent.class.getName(),
        MinimaxAgent.class.getName()
    };
    
    public static final String[] supportedAgentsTitles = {
        "Clovek",
        "A-B orezavanie",
        "Cut-off searching",
        "Minimax"
    };
    
    private Agent agent01 = null;
    
    private Agent agent02 = null;
    
    private Agent agentOnTurn = null;
    
    /** Roznodnutie hraca (priebezne/definitivne), ktory je prave na tahu. */
    private Move agentOnTurnMove = null;
    
    private Thread checkersControllerThread;
    
    private Map map;
    
    private GUI gui;
    
    private Piece selectedPiece; 
    
    public Game(GUI gui) {
        this.gui = gui;
        this.map = new Map();
    }
    
    public void initAgent(int agentId, int agentType, int attackDir, int timeToThink) {
        if (agentId == 1) {
            agent01 = createAgent(agentType, attackDir, timeToThink);
            map.setPiece(1, 1, new Bishop(agent01));
            map.setPiece(2, 2, new Bishop(agent01));
            map.setPiece(3, 1, new Bishop(agent01));
            map.setPiece(4, 2, new Bishop(agent01));
            map.setPiece(5, 1, new Bishop(agent01));
            map.setPiece(6, 2, new Bishop(agent01));
            map.setPiece(7, 1, new Bishop(agent01));
            map.setPiece(8, 2, new Bishop(agent01));
        } else if (agentId == 2) {
            agent02 = createAgent(agentType, attackDir, timeToThink);
            map.setPiece(1, 7, new Bishop(agent02));
            map.setPiece(2, 8, new Bishop(agent02));
            map.setPiece(3, 7, new Bishop(agent02));
            map.setPiece(4, 8, new Bishop(agent02));
            map.setPiece(5, 7, new Bishop(agent02));
            map.setPiece(6, 8, new Bishop(agent02));
            map.setPiece(7, 7, new Bishop(agent02));
            map.setPiece(8, 8, new Bishop(agent02));
            
            //TODO debugovanie preskocenia, odstranit neskor!
            map.setPiece(3, 3, new Bishop(agent02));
            map.setPiece(3, 5, new Bishop(agent02));
        } else {
            throw new IllegalArgumentException("Hrac cislo " + agentId + " nie je validnym hracom.");
        }
    }
    
    private Agent createAgent(int agentType, int attackDir, int timeToThink) {
        try {
            return (Agent) Class.forName(supportedAgents[agentType])
                .getConstructor(Game.class, int.class, int.class)
                .newInstance(this, timeToThink, attackDir);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Nepodarilo sa vytvorit agenta.", e);
        }
    }
    
    public Map getMap() {
        return map;
    }
    
    public void start() {
        if (agent01 == null || agent02 == null) {
            gui.showMessage("Vyberte hracov.");
        } else {
            agentOnTurn = agent01;
            gui.setAgentOnTurnHighlighted(getAgentOnTurnId());
            gui.repaintBoard();
            checkersControllerThread = new CheckersControllerThread();
            checkersControllerThread.start();
        }
    }
    
    public void stopAndDestroy() {
        // TODO do mem cleanup
        // agent01.destroy();
        // agent02.destroy();
        // map.destroy();
        // ...
        checkersControllerThread.interrupt();
        checkersControllerThread = null;
    }
    
    public synchronized Agent getAgentOnTurn() {
        return agentOnTurn;
    }
    
    public synchronized int getAgentOnTurnId() {
        if (agentOnTurn == null) {
            return -1;
        } else if (agent01 == agentOnTurn) {
            return 1;
        } else {
            return 2;
        }
    }
    
    /**
     * @return Protihraca agenta <tt>agent</tt>.
     */
    public Agent getOpponent(Agent agent) {
        if (agent == agent01) {
            return agent02;
        } else {
            return agent01;
        }
    }
    
    public void doMove(Move m) {
        System.out.println("game - doing move " + m);
    	Piece p = m.piece;
    	p.doMove(m);
    	gui.repaintBoard();
    }
    
    private synchronized void setNextAgentOnTurn() {
        agentOnTurnMove = null;
    	agentOnTurn = getOpponent(getAgentOnTurn());
    	gui.setAgentOnTurnHighlighted(getAgentOnTurnId());
    	System.out.println("game - agent " + getAgentOnTurnId() + " is on turn");
    }
    
    public GUI getGui() {
    	return gui;
    }
    
    public boolean isEnd() {
    	if(agent01 == null || agent02 == null) {
    		return true;
    	}
    	
    	if (agent01.getPieces().size() == 0 || 
    			agent02.getPieces().size() == 0) {
    		return true;
    	}
    	
    	if(agentOnTurn.hasMoves()) {
    		return false;
    	}
    	
    	return false;
    }
    
    class CheckersControllerThread extends Thread {
        
        public CheckersControllerThread() {
            super("CheckersController");
        }
        
        @Override
        public void run() {
            Thread actThread;
            while ( true /** nie je koniec hry */) {
                actThread = new CheckersControllerActThread();
                actThread.start();
                try {
                    // pockame na agenta
                    System.out.println("game - waiting for act() to finish");
                    if (agentOnTurn.getTimeToThink() == -1) {
                        // agent ma na rozmyslanie neobmedzeny cas
                        actThread.join();
                    } else {
                        // agent ma na rozmyslanie presne stanoveny limit
                        actThread.join(agentOnTurn.getTimeToThink());
                    }
                } catch (InterruptedException e) {
                    // Tento thread moze byt preruseny pri stopnuty hry, nie je to chyba.
                    // TODO Pri stopnuti hry korektne stiahnut so sebou aj actThread.
                }

                // Dame threadu moznost urobit cleanup. V tomto case sa agent moze nachadzat
                // v dvoch stavoch:
                // 1) vratil z metody act() a dopocital sa k svojmu konecnemu/definitivnemu tahu
                // 2) agent este stale pocita, agent zatial nevratil ziadny konecny vysledok
                // V oboch pripadoch je korektne agenta najprv upozornit, ze jeho tah sa skoncil
                // a nasledne prerusit jeho thread.
                agentOnTurn.doTurnCleanup();
                actThread.interrupt();
                
                System.out.println("game - act() finished with value " + agentOnTurnMove);
                if (agentOnTurnMove == null) {
                    setNextAgentOnTurn();   // agent sa nijako nerozhodol, jeho tah prepada
                } else {
                    doMove(agentOnTurnMove);
                    setNextAgentOnTurn();
                }
            }
            
        }
        
    }
    
    /**
     * Thread, ktory ma nastarosti volanie metody <tt>act()</tt> agenta, ktory
     * je prave na tahu.
     * 
     * Thread, ktory ma nastarosti volanie lifecycle (turncycle ;-) ) metody
     * agenta. Tymito metodami su {@link Agent#act()} a {@link Agent#doTurnCleanup()}.
     * @author miso
     *
     */
    class CheckersControllerActThread extends Thread {
        
        public CheckersControllerActThread() {
            super("CheckersController-act");
        }
        
        @Override
        public void run() {
            while (true) {
                agentOnTurnMove = agentOnTurn.act();
                if (agentOnTurnMove == null) {
                    System.out.println("act - no move computed");
                    break;
                } else if (agentOnTurnMove.isTemporary()) {
                    System.out.println("act - move is temporary");
                    continue;
                } else {
                    System.out.println("act - move is definitive decision");
                    break;
                }
            }
            System.out.println("act - agent "+ getAgentOnTurnId() +" finished acting");
        }
        
    }

}
