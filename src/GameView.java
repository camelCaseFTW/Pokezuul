import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

class GameView extends JFrame implements GameListener {
	
	private static final long serialVersionUID = 1L;

	private static final String newline = "\n";
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu gameMenu = new JMenu("Game");
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem saveGame = new JMenuItem("Save");
	private JMenuItem openGame = new JMenuItem("Open");
	private JMenuItem quitGame = new JMenuItem("Quit");

	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem undoGame = new JMenuItem("Undo");
	private JMenuItem redoGame = new JMenuItem("Redo");

	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem helpGame = new JMenuItem("Detailed Help");

	private static JTextField commandInput = new JTextField(25);
	private static JButton commandButton = new JButton("Process Command");

	private JPanel mainPanel;
	private JPanel picturePanel;
	private JPanel commandPanel;	
	
	private JTextArea messageDisplayer;
	private JScrollPane scrollPane;
	
	private GameSystem game_model;

	private DrawingArea drawing2D;
	private Drawing3DArea drawing3D;
	
	public GameView(GameSystem g) {
		
		game_model = g;
		drawing2D = new DrawingArea(game_model);
		drawing3D = new Drawing3DArea(game_model);
		
		gameMenu.add(newGame);
		gameMenu.add(saveGame);
		saveGame.setEnabled(false);
		gameMenu.add(openGame);
		gameMenu.add(quitGame);
		
		editMenu.add(undoGame);
		undoGame.setEnabled(false);
		editMenu.add(redoGame);
		redoGame.setEnabled(false);
		
		helpMenu.add(helpGame);
		helpGame.setEnabled(false);
		
		menuBar.add(gameMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		

		picturePanel = new JPanel();
		commandPanel = new JPanel();
		
		messageDisplayer = new JTextArea(5, 30);
		messageDisplayer.setEditable(false);
		messageDisplayer.setBorder(BorderFactory.createEtchedBorder());
		scrollPane = new JScrollPane(messageDisplayer);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		commandPanel.add(new JLabel("Command:"));
		commandPanel.add(commandInput);
		commandInput.setEditable(false);
		commandPanel.add(commandButton);
		commandButton.setEnabled(false);

		///////////////////////////////////////////////
		drawing2D.setBackground(Color.white);
		drawing2D.setBorder(BorderFactory.createEtchedBorder());
		
		drawing2D.setAlignmentX(Component.CENTER_ALIGNMENT);
		drawing3D.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//picturePanel.setLayout(new BoxLayout(picturePanel, BoxLayout.Y_AXIS));
		picturePanel.setLayout(new GridBagLayout());
		/*
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		*/
		picturePanel.add(drawing3D);
        /*
		c.weightx = 0;
		c.weighty = 0;
		//c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;		
		*/
		picturePanel.add(drawing2D);
        /*
		c.gridx = 1;
		c.gridy = 1;
		picturePanel.add(new JPanel());
		*/
        //... Set window characteristics
        //setContentPane(picturePanel);
        //setLocationRelativeTo(null);  // Center window.

		/////////////////////////////////////////////
		
		mainPanel.add(picturePanel);
		mainPanel.add(scrollPane);
		mainPanel.add(commandPanel);
		
		this.setLayout(new FlowLayout());
		this.setJMenuBar(menuBar);
		this.setContentPane(mainPanel);
		this.pack();
		this.setTitle("Zuul");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setResizable(false);
		dspMessage("Game > New Game to begin your adventure!");
	}

	public String getUserInput() {
		return commandInput.getText();
	}
	
	public void enableCommandPanel() {
		commandInput.setEditable(true);
		commandButton.setEnabled(true);
	}
	
	public void enableGameButtons() {
		undoGame.setEnabled(true);
		redoGame.setEnabled(true);
		helpGame.setEnabled(true);
	}
	
	public void disableGameButtons() {
		undoGame.setEnabled(false);
		redoGame.setEnabled(false);
		helpGame.setEnabled(false);		
	}
	
	public void disableCommandPanel() {
		commandInput.setEditable(false);
		commandButton.setEnabled(false);
	}
	
	public void resetUserInput() {
		commandInput.setText("");
	}

	void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}
	
	public void addCommandListener(ActionListener listener) {
		commandButton.addActionListener(listener);
		commandInput.addActionListener(listener);
	}
	
	public void addNewGameListener(ActionListener listener) {
		newGame.addActionListener(listener);
	}
	
	public void addQuitGameListener(ActionListener listener) {
		quitGame.addActionListener(listener);
	}
	
	public void addHelpGameListener(ActionListener listener) {
		helpGame.addActionListener(listener);
	}
	
	public void addWindowListener(WindowListener listener) {
		
	}
	
	public void dspMessage(String message) {
        messageDisplayer.append(message + newline);
        messageDisplayer.setCaretPosition(messageDisplayer.getDocument().getLength());		
	}
	
	public void commandProcessed(GameEvent e) {
		this.drawing2D.repaint();
		this.drawing3D.repaint();
		dspMessage(e.getGameStatus());
	}
	
	public void endGame() {
		disableCommandPanel();
		disableGameButtons();
	}

	public static void main(String[] args) {
		GameSystem g = new GameSystem();
		GameView v= new GameView(g);
		g.addGameListener(v);
		
		@SuppressWarnings("unused")
		GameController c = new GameController(v, g);
		
		v.setVisible(true);
		v.setLocationRelativeTo(null);
	}

	public static void setCommandInput(String string) {
		commandInput.setText(string);
		commandButton.doClick();
		
	}
}