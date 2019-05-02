import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		//ui elements required for every timer
		ArrayList<JPanel> timerpanels = new ArrayList<>();
		ArrayList<JButton> names = new ArrayList<>(), hs = new ArrayList<>(), ms = new ArrayList<>(), ss = new ArrayList<>();
		ArrayList<JButton> buttons = new ArrayList<JButton>();

		//initialisation with 10 timers (runtime expansion planned)
		for (int i=0; i<10; i++){
			timerNames.add("Timer: "+ i);
			timerVals.add(1L);
		}

		//UI

		JButton stbutton = new JButton("stop");
		JPanel timerpane = new JPanel();
		timerpane.setLayout(new BoxLayout(timerpane, BoxLayout.Y_AXIS));

		//build timerpanels
		for(int i=0; i<ammount; i++){
			timerpanels.add(new JPanel());
			timerpanels.get(i).setLayout(new FlowLayout());
			names.add(new JButton(timerNames.get(i)));
			hs.add(new JButton(hos(i)));
			ms.add(new JButton(mis(i)));
			ss.add(new JButton(ses(i)));
			buttons.add(new JButton("start"));
			timerpanels.get(i).add(names.get(i));
			timerpanels.get(i).add(hs.get(i));
			timerpanels.get(i).add(new JLabel(":"));
			timerpanels.get(i).add(ms.get(i));
			timerpanels.get(i).add(new JLabel(":"));
			timerpanels.get(i).add(ss.get(i));
			timerpanels.get(i).add(buttons.get(i));
			buttons.get(i).setBackground(Color.GREEN);
			buttons.get(i).setPreferredSize(new Dimension(75, 30));
			names.get(i).setBackground(Color.LIGHT_GRAY);
			names.get(i).setPreferredSize(new Dimension(250, 30));
			hs.get(i).setBackground(Color.LIGHT_GRAY);
			hs.get(i).setPreferredSize(new Dimension(50, 30));
			ms.get(i).setBackground(Color.LIGHT_GRAY);
			ms.get(i).setPreferredSize(new Dimension(50, 30));
			ss.get(i).setBackground(Color.LIGHT_GRAY);
			ss.get(i).setPreferredSize(new Dimension(50, 30));
		}

		//timerbuttons in loop and add them to timerpane
		for (int i=0; i<ammount; i++){
			timerpane.add(timerpanels.get(i));
		}
		frame.getContentPane().add(BorderLayout.CENTER, timerpane);
		load();
		//UI thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				frame .setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setSize(550, 450);
				frame.setResizable(false);
				frame.getContentPane().add(BorderLayout.SOUTH, stbutton);
				stbutton.setBackground(Color.RED);

				stbutton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						activetimer = -1;
						for(int i=0; i<ammount; i++){
							buttons.get(i).setEnabled(true);
							buttons.get(i).setBackground(Color.GREEN);
						}
						save();
					}
				});

				//adding actionlisteners
				for(int i=0; i<ammount; i++){
					final int l = i;
					names.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String s = JOptionPane.showInputDialog(null,
									"Gib einen neuen Namen ein:",
									"Namensänderung",
									JOptionPane.PLAIN_MESSAGE);
							if (s != null) {
								timerNames.set(l, s);
							}
						}
					});
					hs.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String s, secs, mins;
							secs = ses(l);
							mins = mis(l);
							s = JOptionPane.showInputDialog(null,
									"Gib einen neuen Stundenwert ein:",
									"Wertänderung",
									JOptionPane.PLAIN_MESSAGE);
							if (s != null) {
								long newtime = (Long.valueOf(secs) * 1000);
								newtime += (Long.valueOf(mins) * 60) * 1000;
								newtime += ((Long.valueOf(s) * 60) * 60) * 1000;
								timerVals.set(l, newtime);
							}
						}
					});
					ms.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String s, secs, hous;
							secs = ses(l);
							hous = hos(l);
							s = JOptionPane.showInputDialog(null,
									"Gib einen neuen Minutenwert ein:",
									"Wertänderung",
									JOptionPane.PLAIN_MESSAGE);
							if (s != null) {
								long newtime = (Long.valueOf(secs) * 1000);
								newtime += (Long.valueOf(s) * 60) * 1000;
								newtime += ((Long.valueOf(hous) * 60) * 60) * 1000;
								timerVals.set(l, newtime);
							}
						}
					});
					ss.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String s, mins, hous;
							mins = mis(l);
							hous = hos(l);
							s = JOptionPane.showInputDialog(null,
									"Gib einen neuen Sekundenwert ein:",
									"Wertänderung",
									JOptionPane.PLAIN_MESSAGE);
							if(s != null) {
								long newtime = (Long.valueOf(s) / 1000);
								newtime += (Long.valueOf(mins) * 60) * 1000;
								newtime += ((Long.valueOf(hous) * 60) * 60) * 1000;
								timerVals.set(l, newtime);
							}
						}
					});

					ss.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

						}
					});
					buttons.get(i).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							activetimer = l;
							for(int i=0; i<ammount; i++){
								buttons.get(i).setEnabled(true);
								buttons.get(i).setBackground(Color.GREEN);
							}
							buttons.get(l).setEnabled(false);
							buttons.get(l).setBackground(Color.GRAY);
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
					//update timers
					for (int i=0; i<ammount; i++){

						names.get(i).setText(timerNames.get(i));
						hs.get(i).setText(hos(i));
						ms.get(i).setText(mis(i));
						ss.get(i).setText(ses(i));
					}
					//timer selection
					if(activetimer >= 0){
						int lactive = activetimer;
						long start = System.currentTimeMillis();
						try {
							Thread.sleep(1000);
							secsto3min++;
						}catch (Exception e){}
						Long time = timerVals.get(lactive);
						timerVals.set(lactive, time + (System.currentTimeMillis()-start));

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
		try {
			PrintWriter writer = new PrintWriter("Multitimer.txt");
			//writer.println(Integer.toString(ammount));
			for (int i = 0; i < ammount; i++) {
				writer.println(timerNames.get(i));
				writer.println(timerVals.get(i));
			}
			writer.close();
		}catch(Exception e){
			System.out.println("Failed to save!");
		}
	}
	public static void load(){
		//TODO this still has to be expanded once more timers can be added
		//read lines from file
		ArrayList<String> lines = new ArrayList<>();
		try (Scanner s = new Scanner(new FileReader("Multitimer.txt"))) {
			while (s.hasNext()) {
				lines.add(s.nextLine());
			}
		}
		catch (Exception e){
			System.out.println("loading failed! no file to load?");
		}
		//parse lines into appropriate lists
		try{
			boolean isName = true;
			int namesparsed = 0;
			int linesparsed = 0;
			for (int i=0; i<lines.size(); i++){
				if(isName){
					isName = false;
					String s = lines.get(i);
					timerNames.set(namesparsed, s);
					namesparsed++;
				}
				else {
					isName = true;
					String s = lines.get(i);
					timerVals.set(linesparsed, Long.valueOf(s));
					linesparsed++;
				}
			}
		}catch (Exception e){
			System.out.println("error while parsing file");
		}
	}

	public static String stringify(int f){
		long timeS = timerVals.get(f)/1000;
		return ((timeS/60)/60+":"+(timeS/60)%60+":"+timeS%60);
	}


	//"stringify" für ausgabe in textfields
	public static String hos(int f){
		long timeS = timerVals.get(f)/1000;
		return Long.toString((timeS/60)/60);
	}
	public static String mis(int f){
		long timeS = timerVals.get(f)/1000;
		return Long.toString((timeS/60)%60);
	}
	public static String ses(int f){
		long timeS = timerVals.get(f)/1000;
		return Long.toString(timeS%60);
	}
}