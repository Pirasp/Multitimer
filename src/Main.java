import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {

	//Array of timer names (also gives the ammount of timers
	public static ArrayList<String> timerNames= new ArrayList<String>();
	//Array of timer Values (milliseconds)
	public static ArrayList<Long> timerVals = new ArrayList<Long>();
	//Activated Timer (index of the privious arrays)
	public static int activetimer = 1; //-1 as in no timer is activated

	public static void main(String[] args) {

		//initialisation with 10 timers (runtime expansion planned)
		for (int i=0; i<10; i++){
			timerNames.add(Integer.toString(i));
			timerVals.add(0L);
		}

		//timer thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				//timing here
			}
		}).start();

		//UI
		JFrame frame = new JFrame("Multitimer");
		new Thread(new Runnable() {
			@Override
			public void run() {
				frame .setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				JButton button = new JButton(Long.toString(timerVals.get(1)));
				frame.setSize(300, 300);
				frame.getContentPane().add(BorderLayout.SOUTH, button);

				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button.setText(Long.toString(timerVals.get(1)));
						frame.revalidate();
						frame.repaint();
					}
				});

				frame .setVisible(true);
			}
		}).start();
	}
}
