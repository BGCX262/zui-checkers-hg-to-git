package zui.checkers;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import zui.checkers.game.BoardMouseListener;
import zui.checkers.pieces.Map;
import zui.checkers.pieces.Piece;

/**
 *
 * @author miso, lukas
 */
public class MainFrame extends javax.swing.JFrame implements GUI {

    public static final String TIME_LIMIT = "Casovy limit";
    public static final String NEW_GAME = "Nova hra";
    public static final String START_GAME = "Start";

    private Game game;

    /** Creates new form MainFrame */
    public MainFrame() {
        initComponents();
        initSlider(agent01Slider, jLabel3);
        initSlider(agent02Slider, jLabel4);
        boardCanvas.setPreferredSize(new Dimension(GUI.PIECE_SIZE*Map.SIZE, GUI.PIECE_SIZE*Map.SIZE));

        setTitle("ZUI Dáma (Lukas Votypka, Michal Vician)");
        msgLabel.setText("Vitajte");
        gameButton.setText(START_GAME);
        setMinimumSize(new Dimension(800, 600));
        setBounds(0, 0, 800, 600);
    }

    private void initSlider(final JSlider slider, final JLabel label) {
        slider.setMaximum(100);
        slider.setMinimum(1);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (slider.getValue()<100) {
                    label.setText(TIME_LIMIT + " ("+ slider.getValue() +" sek.)");
                } else {
                    label.setText(TIME_LIMIT + " (neobmedzeny)");
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        gameButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        agent01Combo = new JComboBox(Game.supportedAgentsTitles);
        jLabel2 = new javax.swing.JLabel();
        agent02Combo = new JComboBox(Game.supportedAgentsTitles);
        agent01Slider = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        agent02Slider = new javax.swing.JSlider();
        jPanel2 = new javax.swing.JPanel();
        boardCanvas = new BoardCanvas();
        
        jPanel3 = new javax.swing.JPanel();
        msgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        gameButton.setText("Start novej hry");
        gameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Hrac 1");

        jLabel2.setText("Hrac 2");

        jLabel3.setText("Casovy limit v sekundach");

        jLabel4.setText("Casovy limit v sekundach");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(gameButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(agent02Combo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(agent01Combo, 0, 232, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(agent01Slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(agent02Slider, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(agent01Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(agent01Slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(agent02Combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addComponent(agent02Slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(gameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        boardCanvas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout boardCanvasLayout = new javax.swing.GroupLayout(boardCanvas);
        boardCanvas.setLayout(boardCanvasLayout);
        boardCanvasLayout.setHorizontalGroup(
            boardCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        boardCanvasLayout.setVerticalGroup(
            boardCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.add(boardCanvas, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.GridBagLayout());
        jPanel3.add(msgLabel, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameButtonActionPerformed
        if (gameButton.getText().equals(START_GAME)) {
            gameButton.setText(NEW_GAME);
            agent01Combo.setEnabled(false);
            agent01Slider.setEnabled(false);
            agent02Combo.setEnabled(false);
            agent02Slider.setEnabled(false);
            game = new Game(this);
            game.initAgent(1, agent01Combo.getSelectedIndex(), 1, getTimeLimitMillis(agent01Slider));
            game.initAgent(2, agent02Combo.getSelectedIndex(), -1, getTimeLimitMillis(agent02Slider));
            game.start();
            boardCanvas.addMouseListener(new BoardMouseListener(game));
        } else {
            gameButton.setText(START_GAME);
            agent01Combo.setEnabled(true);
            agent01Slider.setEnabled(true);
            agent02Combo.setEnabled(true);
            agent02Slider.setEnabled(true);
            
            game.stopAndDestroy();
            
            game = null;
            boardCanvas.repaint();
        }

    }//GEN-LAST:event_gameButtonActionPerformed

    private int getTimeLimitMillis(JSlider slider) {
        if (slider.getValue() == 100) {
            return -1;
        } else {
            return slider.getValue() * 1000;
        }
    }

    @Override
    public void showMessage(String message) {
        msgLabel.setText(message);
    }

    @Override
    public void setAgentOnTurnHighlighted(int agentId) {
        if (agentId == 1) {
            jLabel1.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD));
            jLabel2.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN));
        } else if (agentId == 2) {
            jLabel1.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN));
            jLabel2.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD));
        } else {
            jLabel1.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN));
            jLabel2.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN));
        }
    }
    
    @Override
    public void repaintBoard() {
        boardCanvas.repaint();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    class BoardCanvas extends JPanel {
        
        private final Image black = new ImageIcon(
                Piece.class.getResource("/zui/checkers/img/black.png")).getImage();
        private final Image white = new ImageIcon(
                Piece.class.getResource("/zui/checkers/img/white.png")).getImage();
        
        @Override
        public void paint(Graphics g) {
            Image img = null;
            
            int xFlag = GUI.PIECE_SIZE;
            int yFlag = GUI.PIECE_SIZE*Map.SIZE;
            for (int x = 1; x<=Map.SIZE; x++) {
                for (int y = 1; y<=Map.SIZE; y++) {
                    if (game != null) {
                        Piece p = game.getMap().getPieceAt(x, y);
                        if (p!=null) {
                            img = p.getImage();
                        }
                    }
                    if (img == null) {
                        img = ((x+y)%2==1) ? white: black;
                    }
                    g.drawImage(img,
                            x*xFlag-GUI.PIECE_SIZE,
                            yFlag-y*GUI.PIECE_SIZE, rootPane);
                    img = null;
                }
            }
        }

        

    }
    
	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox agent01Combo;
    private javax.swing.JSlider agent01Slider;
    private javax.swing.JComboBox agent02Combo;
    private javax.swing.JSlider agent02Slider;
    private javax.swing.JPanel boardCanvas;
    private javax.swing.JButton gameButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel msgLabel;
    // End of variables declaration//GEN-END:variables

	@Override
    public JPanel getBoard() {
	    return boardCanvas;
    }

}
