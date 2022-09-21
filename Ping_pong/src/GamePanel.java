import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable{
	
	static final int game_width = 1000;
	static final int game_height = (int)(game_width * (0.5555));
	static final Dimension screen_size = new Dimension(game_width, game_height);
	static final int ball_d = 20;
	static final int paddle_width = 25;
	static final int paddle_height = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	GamePanel(){
		newPaddles();
		newBall();
		score = new Score(game_width, game_height);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(screen_size);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newBall() {
		random = new Random();
		ball = new Ball((game_width/2) - (ball_d/2),(game_height/2) - (ball_d/2), ball_d, ball_d);
	}
	
	public void newPaddles() {
		paddle1 = new Paddle(0,(game_height/2) - (paddle_height/2),paddle_width,paddle_height,1);
		paddle2 = new Paddle(game_width - paddle_width,(game_height/2) - (paddle_height/2),paddle_width,paddle_height,2);
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	
	public void checkCollision() {
		
		//Ball bounces back while hitting the wall
		if(ball.y <= 0) {
			ball.setYDirextion(-ball.yVelocity);
		}
		if(ball.y >= game_height - ball_d) {
			ball.setYDirextion(-ball.yVelocity);
		}
		
		//Ball bounces back while hitting panel
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.yVelocity > 0)
				ball.yVelocity++;            //Optional for more difficulty
			else{
				ball.yVelocity--;
			}
			ball.setXDirextion(ball.xVelocity);
			ball.setYDirextion(ball.yVelocity);
		}
		
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.yVelocity > 0)
				ball.yVelocity++;			//Optional for more difficulty
			else{
				ball.yVelocity--;
			}
			ball.setXDirextion(-ball.xVelocity);
			ball.setYDirextion(-ball.yVelocity);
		}
		
		//The panels stop at the border of the screen
		if(paddle1.y <= 0) {
			paddle1.y = 0;
		}
		if(paddle1.y >= (game_height - paddle_height)) {
			paddle1.y = game_height - paddle_height;
		}
		
		if(paddle2.y <= 0) {
			paddle2.y = 0;
		}
		if(paddle2.y >= (game_height - paddle_height)) {
			paddle2.y = game_height - paddle_height;
		}
		
		//Point scoring system
		if(ball.x <= 0) {
			score.player2++;
			//newPaddles();
			newBall();
		}
		if(ball.x >= game_width-ball_d) {
			score.player1++;
			//newPaddles();
			newBall();
		}
	}
	
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}
