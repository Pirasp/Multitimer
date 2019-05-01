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
	public static int activetimer = -1; //-1 as in no timer is activated
	//timing variable for saves every 3 mins
	public static int secsto3min = 0;
	//ammount of timers
	public static int ammount = 10;

	public static void main(String[] args) {

		JFrame frame = new JFrame("Multitimer");
		ArrayList<JButton> buttons = new ArrayList<JButton>();

		//initialisation with 10 timers (runtime expansion planned)
		for (int i=0; i<10; i++){
			timerNames.add(Integer.toString(i));
			timerVals.add(1L);
		}

		//UI

		JButton stbutton = new JButton("stop");
		JPanel timerpane = new JPanel();
		timerpane.setLayout(new BoxLayout(timerpane, BoxLayout.Y_AXIS));

		//TODO!!!---------------------------------------
		//Refactor timerbuttons into loops and arrays

		//timerbuttons in loop and add them to timerpane
		for (int i=0; i<ammount; i++){
			buttons.add(new JButton(stringify(i)));
			timerpane.add(buttons.get(i));
		}

		frame.getContentPane().add(BorderLayout.CENTER, timerpane);

		new Thread(new Runnable() {
			@Override
			public void run() {
				frame .setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setSize(300, 500);
				frame.getContentPane().add(BorderLayout.SOUTH, stbutton);

				stbutton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = -1;
						//button.setText(Long.toString(timerVals.get(1)));
						//frame.revalidate();
						//frame.repaint();
					}
				});

				//adding actionlisteners
				for(int i=0; i<ammount; i++){
					final int l = i;
					buttons.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							activetimer = l;
						}
					});
				}

				frame .setVisible(true);
			}
		}).start();

		//timer thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(;;){
					if(activetimer >= 0){
						int lactive = activetimer;
						long start = System.currentTimeMillis();
						try {
							Thread.sleep(1000);
							secsto3min++;
						}catch (Exception e){}
						Long time = timerVals.get(lactive);
						timerVals.set(lactive, time + (System.currentTimeMillis()-start));

						//update timers
						for (int i=0; i<ammount; i++){
							buttons.get(i).setText(stringify(i));
						}

						frame.revalidate();
						frame.repaint();
						if(secsto3min > 3*60){
							save();
						}
					}
				}
			}
		}).start();
	}

	public static void save(){

	}
	public static String stringify(int f){
		long timeS = timerVals.get(f)/1000;
		return ((timeS/60)/60+":"+(timeS/60)%60+":"+timeS%60);

	}
}