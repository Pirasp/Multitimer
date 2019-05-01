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

	public static void main(String[] args) {

		JFrame frame = new JFrame("Multitimer");

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

		//timerbuttons
		JButton timer0 = new JButton(Long.toString(timerVals.get(0)));
		JButton timer1 = new JButton(Long.toString(timerVals.get(1)));
		JButton timer2 = new JButton(Long.toString(timerVals.get(2)));
		JButton timer3 = new JButton(Long.toString(timerVals.get(3)));
		JButton timer4 = new JButton(Long.toString(timerVals.get(4)));
		JButton timer5 = new JButton(Long.toString(timerVals.get(5)));
		JButton timer6 = new JButton(Long.toString(timerVals.get(6)));
		JButton timer7 = new JButton(Long.toString(timerVals.get(7)));
		JButton timer8 = new JButton(Long.toString(timerVals.get(8)));
		JButton timer9 = new JButton(Long.toString(timerVals.get(9)));

		timerpane.add(timer0);
		timerpane.add(timer1);
		timerpane.add(timer2);
		timerpane.add(timer3);
		timerpane.add(timer4);
		timerpane.add(timer5);
		timerpane.add(timer6);
		timerpane.add(timer7);
		timerpane.add(timer8);
		timerpane.add(timer9);

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
				timer0.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 0;
					}
				});
				timer1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 1;
					}
				});
				timer2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 2;
					}
				});
				timer3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 3;
					}
				});
				timer4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 4;
					}
				});
				timer5.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 5;
					}
				});
				timer6.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 6;
					}
				});
				timer7.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 7;
					}
				});
				timer8.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 8;
					}
				});
				timer9.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = 9;
					}
				});

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
						timer0.setText(stringify(0));
						timer1.setText(stringify(1));
						timer2.setText(stringify(2));
						timer3.setText(stringify(3));
						timer4.setText(stringify(4));
						timer5.setText(stringify(5));
						timer6.setText(stringify(6));
						timer7.setText(stringify(7));
						timer8.setText(stringify(8));
						timer9.setText(stringify(9));

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