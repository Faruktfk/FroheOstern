package window;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;



public class OsterEi {
	
	GraphicsZeichnen graphics = new GraphicsZeichnen();
	ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("osternIcon.png"));

	public static void main(String[] args) {
		new OsterEi();
	}

	public OsterEi() {
		JFrame frame = new JFrame("FROHE OSTERN");
		frame.setSize(1536, 824);
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(graphics);
		frame.setIconImage(icon.getImage());
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_Q:
					System.exit(0);
					break;

				case KeyEvent.VK_SPACE:
					graphics.start();
					graphics.strtBtn.setVisible(false);
					break;

				}
			}
		});
	}

	public static int getRandom(boolean negative) {
		Random random = new Random();
		if(negative) {
			return -1*(random.nextInt(60) + 1);
		}else {
			return random.nextInt(70)+1;
		}
	}
	
	public static int[] getRasenLocs(int zahl, int moveEverything, boolean isXAchse) {
		Random r = new Random();	
		if(isXAchse) {
			return new int[] {-695+zahl+moveEverything, -695+zahl+moveEverything+r.nextInt(5), -685+zahl+moveEverything, -687+zahl+moveEverything+r.nextInt(5), -675+zahl+moveEverything, -671+zahl+moveEverything+r.nextInt(5), -663+zahl+moveEverything};
		}else {
			return new int[] {750+zahl, 730+zahl, 750+zahl, 730+zahl, 750+zahl, 723+zahl, 750+zahl};
		}
		
	}
	
	@SuppressWarnings("serial")
	public class GraphicsZeichnen extends JPanel {
		JButton strtBtn;
		
		String[] imgLLooking = new String[] { "looking -1.png", "looking -2.png","jump -0.png","sitting.png"};
		String[] imgLJumping = new String[] {"jump -2.png", "jump -3.png", "jump -4.png", "jump -5.png", "jump -2.png", "jump -3.png", "jump -4.png", "jump -5.png", "jump -5.png", "jump -2.png"};
//		String[] imgLLooking = new String[] { ".\\res\\looking -1.png", ".\\res\\looking -2.png",".\\res\\jump -0.png",".\\res\\sitting.png"};
//		String[] imgLJumping = new String[] {".\\res\\jump -2.png", ".\\res\\jump -3.png", ".\\res\\jump -4.png", ".\\res\\jump -5.png", ".\\res\\jump -2.png", ".\\res\\jump -3.png", ".\\res\\jump -4.png", ".\\res\\jump -5.png", ".\\res\\jump -5.png", ".\\res\\jump -2.png"};
		
		JLabel lookingHase, jumpingHase;
		boolean hase1EyesOpen = true;
		boolean hase2EyesOpen = true;
		
		
		int tSlow = 0;
		int tFast = 0;

		int moveEvertyhing = 850;
		int wind = 0;

		int velocityS = 1;
		int velocityF = 15;
		
		int velocity2Layer = 50;
		int velocity3Layer = 4;
		

		double yRabbit = 0.0;
		boolean rabbitStop = false;
		
		int[] yGebirgeMitRandom;
		
		Timer timerSlow = new Timer(700, (e) -> {

			tSlow++;
			if (tSlow % 3 == 0) {
				velocityS *= -1;
			}
			wind += velocityS;
			repaint();

			if (tSlow % 5 == 0 && hase1EyesOpen) {
				hase1EyesOpen = false;
				lookingHase.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imgLLooking[1])));
				
			}
			if (tSlow % 2 == 0 && !hase1EyesOpen) {
				hase1EyesOpen = true;
				lookingHase.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imgLLooking[0])));
				
			}
			if (tSlow % 7 == 0 && hase2EyesOpen) {
				hase2EyesOpen = false;
				if(rabbitStop) {
					jumpingHase.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imgLLooking[3])));
				}
			}
			if (tSlow % 3 == 0 && !hase2EyesOpen) {
				hase2EyesOpen = true;
				if(rabbitStop) {
					jumpingHase.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imgLLooking[2])));
				}
			}
			
			

		});

		Timer timerFast = new Timer(150, (e) -> {
			tFast++;
			if (moveEvertyhing > 0) {
				
				moveEvertyhing += -1 * velocityF;
				repaint();
				lookingHase.setLocation(1000 + moveEvertyhing, 570);
				yRabbit+= 0.05;
				jumpingHase.setLocation(400, jumpingHase.getLocation().y-(int)yRabbit);
				jumpingHase.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imgLJumping[tFast%10])));


			}else if(moveEvertyhing == -5) {
				moveEvertyhing--;
				jumpingHase.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imgLLooking[2])));
				rabbitStop = true;
				
			}

		});

		Timer timeColors = new Timer(10, (e) ->{
			System.out.println("Hola");
		});
		
		public GraphicsZeichnen() {
			strtBtn = new JButton("START");
			strtBtn.setBackground(new Color(255, 255, 255));
			strtBtn.setFont(new Font("Calibri",Font.BOLD, 17));
			strtBtn.setSize(100, 50);
			strtBtn.setLocation(720, 250);
			strtBtn.setFocusable(false);
			strtBtn.setVisible(true);
			this.add(strtBtn);
			strtBtn.repaint();
			strtBtn.addActionListener(e -> {strtBtn.setVisible(false);start();});
			
			lookingHase = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(imgLLooking[0])));
			lookingHase.setVisible(true);
			lookingHase.setSize(77, 115);
			lookingHase.setLocation(1000 + moveEvertyhing, 570);
			
			jumpingHase = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(imgLLooking[2])));
			jumpingHase.setVisible(true);
			jumpingHase.setSize(141, 100);
			jumpingHase.setLocation(400, 650);

			this.setLayout(null);
			this.add(lookingHase);
			this.add(jumpingHase);
			timerSlow.start();
			
			yGebirgeMitRandom = new int[]{700, 400, 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 400+getRandom(false), 350+getRandom(true), 700};
		
		}

		public void start() {
			timerFast.start();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(new Color(204, 229, 255));

			Graphics2D g2D = (Graphics2D) g;

			// Background
			g2D.setColor(new Color(204, 229, 255));
			g2D.drawRect(-1000, -1000, 3500, 2800);

			// Sonne
			g2D.setColor(new Color(255, 255, 210));
			g2D.fillArc(315, -600+(1 * wind), 870, 782, 180, 180);
			g2D.setColor(new Color(255, 255, 150));
			g2D.fillArc(435, -350 + (5 * wind), 630, 490, 180, 180);
			g2D.setColor(new Color(255, 255, 0));
			g2D.fillArc(525, -250, 450, 350, 180, 180);

			// Hügel-Background & Berg 	
			int[] xGebirge = {-100, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1050, 1100, 1150, 1200, 1250, 1300, 1350, 1400, 1450, 1500, 1550, 1550};
			int[] yGebirge = yGebirgeMitRandom;
			g2D.setColor(new Color(196,216,237));
			g2D.fillPolygon(xGebirge, yGebirge,xGebirge.length);
			
			int[] x1 = {-170+moveEvertyhing/velocity2Layer, 180+moveEvertyhing/velocity2Layer, 610+moveEvertyhing/velocity2Layer};
			int[] y1 = {900, 150, 900};
			g2D.setColor(new Color(102, 153,204));
			g2D.fillPolygon(x1, y1, x1.length);
			
			int[] x2 = {89+moveEvertyhing/velocity2Layer,  120+moveEvertyhing/velocity2Layer, 150+moveEvertyhing/velocity2Layer, 180+moveEvertyhing/velocity2Layer,210+moveEvertyhing/velocity2Layer,240+moveEvertyhing/velocity2Layer,270+moveEvertyhing/velocity2Layer, 278+moveEvertyhing/velocity2Layer, 180+moveEvertyhing/velocity2Layer};
			int[] y2 = {343, 320, 343,320, 343, 320, 343,320, 150};
			g2D.setColor(Color.WHITE);
			g2D.fillPolygon(x2, y2, x2.length);
			
			g2D.setColor(new Color(0, 230, 0));
			g2D.fillArc(450+(moveEvertyhing/(velocity3Layer*3)), 460, 300, 100, 0, 180);
			g2D.setColor(new Color(0, 150, 0));
			g2D.fillArc(350+(moveEvertyhing/(velocity3Layer*2)), 437, 1100, 900, 0, 180);
			g2D.setColor(new Color(100, 220, 0));
			g2D.fillArc(950 +(moveEvertyhing/velocity3Layer), 300, 900, 900, 0, 180);
			g2D.setColor(new Color(0, 180, 0));
			g2D.fillArc(-110 +(moveEvertyhing/velocity3Layer), 400, 900, 900, 0, 180);

			
			// Hügel-Foreground
			g2D.setColor(new Color(0, 133, 0));
			g2D.fillArc(-1000 + moveEvertyhing, 650, 3000, 300, 0, 180);

			// Baum
			g2D.setColor(new Color(128, 43, 0));
			g2D.fillRect(moveEvertyhing + 1300, 400, 100, 300);
			g2D.fillRect(moveEvertyhing + 1170, 450, 200, 30);
			int[] x = { moveEvertyhing + 1350, moveEvertyhing + 1250, moveEvertyhing + 1450 };
			int[] y = { 650, 750, 750 };
			g2D.fillPolygon(x, y, 3);
			g2D.setColor(new Color(0, 90, 0));
			g2D.fillArc(-1 * wind + moveEvertyhing + 1250, 100, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1340, 90, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1400, 130, 100, 100, 0, 360);
			g2D.fillArc(-1 * wind + moveEvertyhing + 1210, 150, 300, 300, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1190, 130, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1150, 200, 100, 100, 0, 360);
			g2D.fillArc(-1 * wind + moveEvertyhing + 1140, 270, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1200, 340, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1410, 370, 100, 100, 0, 360);
			g2D.fillArc(-1 * wind + moveEvertyhing + 1340, 390, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1270, 380, 100, 100, 0, 360);
			g2D.fillArc(-1 * wind + moveEvertyhing + 1430, 320, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1460, 250, 100, 100, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1430, 180, 100, 100, 0, 360);
			g2D.fillArc(moveEvertyhing + 1110, 430, 80, 80, 0, 360);
			g2D.fillArc(-1 * wind + moveEvertyhing + 1140, 410, 50, 50, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1110, 420, 50, 50, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1090, 440, 50, 50, 0, 360);
			g2D.fillArc(wind + moveEvertyhing + 1110, 470, 50, 50, 0, 360);
			g2D.setColor(new Color(36, 143, 36));
			g2D.setStroke(new BasicStroke(13));
			g2D.drawArc(wind + moveEvertyhing + 1300, 340, 100, 100, 0, 70);
			g2D.drawArc(-1 * wind + moveEvertyhing + 1200, 170, 100, 100, 270, 70);
			g2D.drawArc(-2 * wind + moveEvertyhing + 1370, 130, 100, 100, 180, 70);
			g2D.setColor(new Color(77, 26, 0));
			g2D.fillArc(moveEvertyhing + 1370, 530, 50, 50, 0, 360);
			g2D.setColor(new Color(26, 9, 0));
			g2D.fillArc(moveEvertyhing + 1378, 537, 35, 35, 0, 360);

			// Korb & Eier
			g2D.setColor(new Color(230, 153, 0));
			g2D.fillArc(moveEvertyhing + 650, 670, 230, 25, 0, 360);
			g2D.fillRect(moveEvertyhing + 650, 631, 230, 50);
			g2D.drawArc(moveEvertyhing + 640, 500, 251, 185, 0, 360);
			g2D.setPaint(new GradientPaint(820, 615, new Color(83, 255, 50), 840, 652, new Color(0, 115, 153), true));
			g2D.fillArc(moveEvertyhing + 826, 590, 40, 52, 0, 360);
			g2D.setPaint(
					new GradientPaint(770, 595, new Color(255, 255, 102), 820, 622, new Color(153, 20, 255), true));
			g2D.fillArc(moveEvertyhing + 780, 585, 52, 70, 0, 360);
			g2D.setPaint(
					new GradientPaint(700, 585, new Color(255, 26, 140), 752, 622, new Color(255, 255, 128), true));
			g2D.fillArc(moveEvertyhing + 700, 585, 52, 70, 0, 360);
			g2D.setPaint(new GradientPaint(730, 620, new Color(102, 255, 255), 772, 612, new Color(254, 0, 255), true));
			g2D.fillArc(moveEvertyhing + 738, 590, 52, 70, 0, 360);
			g2D.setPaint(new GradientPaint(650, 585, new Color(255, 153, 0), 700, 612, new Color(204, 255, 204), true));
			g2D.fillArc(moveEvertyhing + 665, 590, 43, 60, 0, 360);
			g2D.setStroke(new BasicStroke(14));
			g2D.setColor(new Color(230, 153, 0));
			g2D.drawArc(moveEvertyhing + 655, 567, 220, 90, 195, 150);
			g2D.drawLine(moveEvertyhing+644, 590,moveEvertyhing+676, 648);
			g2D.drawLine(moveEvertyhing+887, 590,moveEvertyhing+856, 648);

			// Rote-Schleife
			int[] xpoints = { moveEvertyhing + 800, moveEvertyhing + 795, moveEvertyhing + 810, moveEvertyhing + 870,
					moveEvertyhing + 862 };
			int[] ypoints = { 486, 536, 526, 492, 545 };
			int nPoints = xpoints.length;
			g2D.setColor(Color.RED);
			g2D.fillPolygon(xpoints, ypoints, nPoints);
			g2D.fillArc(moveEvertyhing + 820, 505, 20, 20, 0, 360);
			g2D.setStroke(new BasicStroke(8));
			g2D.drawLine(moveEvertyhing + 820, 516, moveEvertyhing + 820 + (-3 * wind), 546);
			g2D.drawLine(moveEvertyhing + 840, 516, moveEvertyhing + 840 + (-3 * wind), 546);

			g2D.setFont(new Font("Calibri", Font.BOLD, 100+wind*2));
			g2D.setPaint(new GradientPaint(650+moveEvertyhing, 585+wind*10, new Color(255+getRandom(true), 63+getRandom(false), 200+getRandom(true)), 700+moveEvertyhing, 612+wind*10, new Color(204+getRandom(true), 155+getRandom(false), 204+getRandom(true)), true));
			g2D.drawString("FROHE OSTERN", 450-wind, 220+wind);
			
			//Rasen 
			g2D.setColor(new Color(51, 255, 173));
			g2D.setStroke(new BasicStroke(2));
			g2D.drawPolyline(getRasenLocs(0, moveEvertyhing, true), getRasenLocs(0, moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(130,moveEvertyhing, true), getRasenLocs(-30,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(190,moveEvertyhing, true), getRasenLocs(-20,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(290,moveEvertyhing, true), getRasenLocs(-20,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(340,moveEvertyhing, true), getRasenLocs(-50,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(450 ,moveEvertyhing, true), getRasenLocs(-5,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(570 ,moveEvertyhing, true), getRasenLocs(-30,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(650 ,moveEvertyhing, true), getRasenLocs(-10,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(760 ,moveEvertyhing, true), getRasenLocs(-70,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(730 ,moveEvertyhing, true), getRasenLocs(-30,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(880 ,moveEvertyhing, true), getRasenLocs(-55,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(830 ,moveEvertyhing, true), getRasenLocs(-5,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(950 ,moveEvertyhing, true), getRasenLocs(0,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(980 ,moveEvertyhing, true), getRasenLocs(-50,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1050 ,moveEvertyhing, true), getRasenLocs(10,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1130 ,moveEvertyhing, true), getRasenLocs(-30,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1250 ,moveEvertyhing, true), getRasenLocs(0,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1280 ,moveEvertyhing, true), getRasenLocs(-60,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1360 ,moveEvertyhing, true), getRasenLocs(-10,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1480 ,moveEvertyhing, true), getRasenLocs(5,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1580 ,moveEvertyhing, true), getRasenLocs(-15,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1605 ,moveEvertyhing, true), getRasenLocs(-70,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1685 ,moveEvertyhing, true), getRasenLocs(-5,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1790,moveEvertyhing, true), getRasenLocs(-40,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1810,moveEvertyhing, true), getRasenLocs(20,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1870,moveEvertyhing, true), getRasenLocs(-60,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1930,moveEvertyhing, true), getRasenLocs(-30,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(1980,moveEvertyhing, true), getRasenLocs(0,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(2015,moveEvertyhing, true), getRasenLocs(0,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(2080,moveEvertyhing, true), getRasenLocs(15,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(2100,moveEvertyhing, true), getRasenLocs(-55,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(2140,moveEvertyhing, true), getRasenLocs(0,moveEvertyhing, false), 7);
			g2D.drawPolyline(getRasenLocs(2180,moveEvertyhing, true), getRasenLocs(-35,moveEvertyhing, false), 7);
			
		}
		
	}

}

