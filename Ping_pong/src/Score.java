import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;


public class Score extends Rectangle{
	
	static int game_width;
	static int game_height;
	int player2;
	int player1;
	
	Score(int game_width, int game_height){
		Score.game_width = game_width;
		Score.game_height = game_height;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.PLAIN,60));
		
		g.drawLine(game_width/2, 0, game_width/2, game_height);
		
		g.drawString(String.valueOf(player1), game_width/2 - 85, 50);
		g.drawString(String.valueOf(player2), game_width/2 + 20, 50);
	}
}
