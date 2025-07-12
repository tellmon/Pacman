package defalt;

import javax.swing.JFrame;

public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SelectOptions panel = new SelectOptions();

		JFrame frame = new JFrame("Pacman Level Chooser");
		frame.setContentPane(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Automatically sizes the frame to fit the panel
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true); // Make it visible!
	}
}

