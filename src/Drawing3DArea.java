import java.awt.*;

import javax.swing.*;

public class Drawing3DArea extends JPanel {

	private static final long serialVersionUID = -6491486951861072281L;
	private static final int ORIGIN_X = 0;
	private static final int ORIGIN_Y = 0;
	private static final Color DEFAULT_COLOR = Color.BLACK;
	private final static float dash1[] = {10.0f};
    private final static BasicStroke DASHED_STROKE =
        new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f);
	private final static BasicStroke DEFAULT_STROKE = new BasicStroke();
	private final static BasicStroke BOLD_STROKE = new BasicStroke(3);
	
	private GameSystem game;
	
	Drawing3DArea(GameSystem g) {
		game = g;
		setPreferredSize(new Dimension(320, 320));
		setBackground(Color.white);
		setBorder(BorderFactory.createEtchedBorder());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		
		g2D.setColor(DEFAULT_COLOR);
		
		int componentHeight = getHeight();
		int componentWidth = getWidth();
		
		if (game.gameRunning()) {
			g2D.drawRect(componentWidth/3, componentHeight/3, componentWidth/3, componentHeight/3);
			
			g2D.drawLine(ORIGIN_X, ORIGIN_Y, componentWidth/3, componentHeight/3);
			g2D.drawLine(componentWidth, ORIGIN_Y, componentWidth*2/3, componentHeight/3);
			g2D.drawLine(ORIGIN_X, componentHeight, componentWidth/3, componentHeight*2/3);
			g2D.drawLine(componentWidth, componentHeight, componentWidth*2/3, componentHeight*2/3);		
		
			g2D.setStroke(BOLD_STROKE);
			g2D.setColor(Color.RED);
			g2D.drawLine(componentWidth/2, componentHeight/9, componentWidth/2, componentHeight*2/9);
			g2D.drawLine(componentWidth*7/18, componentHeight*3/18, componentWidth*11/18, componentHeight*3/18);

			g2D.drawString("N", componentWidth/2, componentHeight*1/18);
			g2D.drawString("S", componentWidth/2, componentHeight*5/18);
			g2D.drawString("W", componentWidth/3, componentHeight*3/18);
			g2D.drawString("E", componentWidth*2/3, componentHeight*3/18);
			
			g2D.setStroke(DEFAULT_STROKE);
			g2D.setColor(DEFAULT_COLOR);
			
        	if(game.getGame().getPlayer().getRoom().getExitRoom(Exit.west) != null) {
        		g2D.drawLine(componentWidth/9, componentHeight*8/9, componentWidth/9, componentHeight/2);
        		g2D.drawLine(componentWidth*2/9, componentHeight*7/9, componentWidth*2/9, componentHeight/2);
        		g2D.drawLine(componentWidth/9, componentHeight/2, componentWidth*2/9, componentHeight/2);
        	}
        	if(game.getGame().getPlayer().getRoom().getExitRoom(Exit.north) != null)
        	{
        		g2D.drawLine(componentWidth*4/9, componentHeight*2/3, componentWidth*4/9, componentHeight/2);
        		g2D.drawLine(componentWidth*5/9, componentHeight*2/3, componentWidth*5/9, componentHeight/2);
        		g2D.drawLine(componentWidth*4/9, componentHeight/2, componentWidth*5/9, componentHeight/2);
        	}
        	if(game.getGame().getPlayer().getRoom().getExitRoom(Exit.east) != null)
        	{
        		g2D.drawLine(componentWidth*8/9, componentHeight*8/9, componentWidth*8/9, componentHeight/2);
        		g2D.drawLine(componentWidth*7/9, componentHeight*7/9, componentWidth*7/9, componentHeight/2);
        		g2D.drawLine(componentWidth*7/9, componentHeight/2, componentWidth*8/9, componentHeight/2);		
        	}
        	if(game.getGame().getPlayer().getRoom().getExitRoom(Exit.south) != null)
        	{
        		g2D.setStroke(DASHED_STROKE);
        		g2D.drawLine(componentWidth*7/18, componentHeight, componentWidth*7/18, componentHeight*13/18);
        		g2D.drawLine(componentWidth*11/18, componentHeight, componentWidth*11/18, componentHeight*13/18);
        		g2D.drawLine(componentWidth*7/18, componentHeight*13/18, componentWidth*11/18, componentHeight*13/18);
			
			g2D.setStroke(DEFAULT_STROKE);
        	}
        	}
	}
}
