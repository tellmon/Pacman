package defalt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class SelectOptions extends JPanel implements ActionListener {
/*** */private static final long serialVersionUID = 1L;
	
    public static String map = "hard"; // hard = hard.txt normal = normal.txt easy = easy.txt
    public static int tickspeed = 40; // hard = 15 normal = 40 and easy = 50
    public static int pelletTimerSpeed = 1000; // hard = 100 normal is 1000 and easy is 2000

	// JTextField
	 JTextField difficatlyTextFile;

	// JButton
	  JButton submitButton;

	// label to display text
	  JLabel difficatlyJLabel;

	public SelectOptions() {
		
		// create a label to display text
		difficatlyJLabel = new JLabel("Difficatly: hard, normal, easy");
		difficatlyTextFile = new JTextField(10);

		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		submitButton = new JButton("submit");
		submitButton.setBackground(Color.WHITE);
		
		JPanel mainPanel = new JPanel();	
		
		// add buttons and textfield to panel
		mainPanel.add(difficatlyJLabel);
		mainPanel.add(difficatlyTextFile);
		
		mainPanel.add(submitButton);

		submitButton.setBorder(emptyBorder);
		submitButton.setFocusable(false);
		submitButton.addActionListener(this);
		
		this.add(mainPanel);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
       
	   if ("exit".equals(difficatlyTextFile.getText())) {
		   System.exit(0);
	   }

	   else if ("hard".equals(difficatlyTextFile.getText())) {
			map = "hard";
			tickspeed = 15;
			pelletTimerSpeed = 100;
		}
		
		else if ("normal".equals(difficatlyTextFile.getText())) {
			map = "normal";
			tickspeed = 30;
			pelletTimerSpeed = 1000;
		}
		
		else if ("easy".equals(difficatlyTextFile.getText())) {
			map = "easy";
			tickspeed = 40;
			pelletTimerSpeed = 2000;
		}

        else{
			map = "maze";
			tickspeed = 5;
			pelletTimerSpeed = 1;
		}
        
		PacmanMain screen = new PacmanMain();
		
		screen.loadMap(map+".txt");
		
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.setTitle("Pacman");
		frame.setSize(800, 800);
		frame.setVisible(true);
    }
}
