package zui.checkers.agents;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import zui.checkers.Game;
import zui.checkers.game.Move;
import zui.checkers.pieces.Map;
import zui.checkers.pieces.Piece;

public abstract class Agent {
    
    /**
     * Smer utoku tohoto hraca/agenta. <tt>1</tt> znaci ze sa hrac
     * pohybuje po y-ovej osi smerom hore, <tt>-1</tt> ze dole.
     */
    public final int attackDir;
    
    public final int timeToThink;
    
    private final Game game;
    
    private Move nextMove;
    
    private Map tmpMap;
    
    /**
     * @param game Hra, ktoru tento hrac bude hrat.
     * @param timeToThink Casovy limit, ktory ma tento hrac na premyslenie tahu. <tt>-1</tt>
     * znaci neobmedzeny cas.
     * @param attackDir Smer utoku. Pozri {@link #attackDir}.
     */
    public Agent(Game game, int timeToThink, int attackDir) {
        if (Math.abs(attackDir) != 1) {
            throw new IllegalArgumentException("Hodnota musi byt 1 alebo -1. [" + attackDir + "]");
        }
        this.attackDir = attackDir;
        this.timeToThink = timeToThink;
        this.game = game;
    }
    
    /**
     * @return Vrati protihraca tohoto agenta.
     */
    public Agent getOpponent() {
        return game.getOpponent(this);
    }
    
    /**
     * @return Vrati zoznam figurok tohoto hraca.
     */
    public Set<Piece> getPieces() {
        return game.getMap().getPieces(this);
    }
    
    /**
     * @return Vrati zoznam figurok protihraca.
     */
    public Set<Piece> getOpponentPieces() {
        return game.getMap().getPieces(getOpponent());
    }
    
    /**
     * Metoda je zavolana, ked je agent na tahu.
     * <p>Tato metoda bude zavolana v danom casovom limite viac krat, ak agent
     * oznaci vrateny vysledok ako "docasny" ({@link Move#tmp}). Ak agent oznaci
     * vrateny vysledok ako jeho definitivne rozhodnutie (<tt>{@link Move#tmp} == false</tt>), tak
     * uz tato metoda nebude v danom tahu viac zavolana.
     * <ul>
     * <li>Ak agent vrati pocas vsetkych volani tejto metody v danom tahu iba docasne vysledky, tak
     * sa za konecne rozhodnutie agenta povazuje ten posledny.</li>
     * <li>Ak agent nevrati ziadny vysledok do vyprsania casoveho limitu, tak tah prepada v prospech supera.</li>
     * </ul>
     * </p>
     */
    public abstract Move act();
    
    /**
     * Tato metoda je volana tesne po vrateni sa z metody <tt>act()</tt> a zaroven
     * tesne pred tym, ako sa dostane na tah novy hrac.
     * Agent si moze vo volani tejto metody urobit poriadok a pripravit sa tak
     * na buduce volania metody <tt>act()</tt>.
     */
    public void doTurnCleanup() {
    }
    
    /**
     * Zavola sa ked na agenta prisiel rad, napriklad na uchovanie povodnej mapy
     */
    public void doBeforeAct() {
    }
    
    public final int getScore() {
    	return getPieces().size() - getOpponentPieces().size();
    }
    
    public Game getGame() {
    	return game;
    }
    
    /**
     * @return Cas, ktory ma agent na rozmyslenie si svojho tahu v milisekundach.
     */
    public int getTimeToThink() {
        return timeToThink;
    }

	protected Set<Move> getAllMoves() {
		Set<Move> buff = new HashSet<Move>();
		for( Iterator<Piece> i = getPieces().iterator(); i.hasNext();) {
			Piece p = i.next();
			buff.addAll(p.getValidSteps());
		}
		
		return buff;
	}
    
	public boolean hasMoves() {
		for( Iterator<Piece> i = getPieces().iterator(); i.hasNext();) {
			Piece p = i.next();
			if (p.getValidSteps().size() > 0) {
				return true;
			}
		}
		
		return false;
	}

	public Move getNextMove() {
    	return nextMove;
    }

	public void setNextMove(Move nextMove) {
		//musim nastavit score hry, nie skore skoku
		nextMove.score = getScore();
    	this.nextMove = nextMove;
    }

	public Map getTmpMap() {
    	return tmpMap;
    }

	public void setTmpMap(Map tmpMap) {
    	this.tmpMap = tmpMap;
    }

}
