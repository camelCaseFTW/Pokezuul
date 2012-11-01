import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

class GameView extends JFrame {
	
	private static final String newline = "\n";
	
	
	private JMenu gameMenu = new JMenu("Game");
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem saveGame = new JMenuItem("Save");
	private JMenuItem openGame = new JMenuItem("Open");
	private JMenuItem quitGame = new JMenuItem("Quit");

	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem undoGame = new JMenuItem("Undo");
	private JMenuItem redoGame = new JMenuItem("Redo");

	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem helpGame = new JMenuItem("Help Content");

	private JTextField commandInput = new JTextField(25);
	private JButton commandButton = new JButton("Process Command");

	private JPanel mainPanel;
	private JPanel picturePanel;
	private JPanel commandPanel;	
	
	private JTextArea messageDisplayer;
	private JScrollPane scrollPane;
	
	private Game game_model;

	public GameView() {
		//game_model = game;

		mainPanel = new JPanel();
		picturePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		commandPanel = new JPanel();
		
		messageDisplayer = new JTextArea(5, 30);
		messageDisplayer.setEditable(false);
		messageDisplayer.setBorder(BorderFactory.createEtchedBorder());
		scrollPane = new JScrollPane(messageDisplayer);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		commandPanel.add(new JLabel("Command:"));
		commandPanel.add(commandInput);
		commandPanel.add(commandButton);
		
		picturePanel.add(new JLabel("MOO"));
		
		mainPanel.add(picturePanel);
		mainPanel.add(messageDisplayer);
		mainPanel.add(commandPanel);
		
		this.setLayout(new FlowLayout());
		this.setContentPane(mainPanel);
		this.pack();
		this.setTitle("Zuul");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public String getUserInput() {
		return commandInput.getText();
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
	
	public void dspMessage(String message) {
        messageDisplayer.append(message + newline);
        messageDisplayer.setCaretPosition(messageDisplayer.getDocument().getLength());		
	}

	public static void main(String[] args) {
		GameView v = new GameView();
		GameController c = new GameController(v);
		
		v.setVisible(true);
		for(int i = 0; i < 3; i++)
			v.dspMessage("WASSAP");
		System.out.println("HI");
	}

}