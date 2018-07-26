//Aashia Mehta
//4-21-14
/* Educational Blood Typing Game */

import javax.swing.*; //import libraries
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class BloodTyping extends JFrame  implements ItemListener { //apply JFrame

	public static void main(String[]args){ //main method
		BloodTyping bloodtyping = new BloodTyping(); //instance needed to run constructor
	} //close main

	//global variables	
	JPanel cards;            // Panel that holds the 3 card panels
	BloodStarting card1;     // 1st card for Game Instructions
	BloodTypingGame card2;   // 2nd card for Blood Typing Game
	BloodTransfusing card3;  // 3rd card for Blood Transfusion Game

	BloodTubes bloodtubes; //bloodtubes class initialization
	Image[] tubes = new Image[6]; //make array for images
	ImageIcon[] imgIcons = new ImageIcon[6]; //make image icon array using images

	// Variables to store answer selected by the user
	String answerBloodGroup; //A, B, AB, O
	String answerRhStatus;   // +, -
	String answer;           // A+, A- etc.

	// Variables that store the blood type for a new randomly selected game
	String userBloodGroup; //A, B, AB, O
	String userRhStatus;   // +, -
	String user;           // A+, A- etc.

	JLabel labelA;
	JLabel labelB;
	JLabel labelRh;

	Buttons buttons; //buttons class initialization
	JButton jbNewGame; // Button for starting a new game
	String NEW_GAME = "New Game"; // Text for button for starting a new game
	JButton jbBloodTransfusingGame; // Button for going to Blood Transfusion Game when you win
	String GO_BLOOD_TRANSFUSING_GAME = "Go to Blood Transfusing Game"; // Text for going to blood transfusing game

	JLabel jlWinLose;
	Image imgWin;
	Image imgLose;
	Image imgGameStart;
	
	Image [] winArr = new Image[4]; //images for animation when you win
	Image [] loseArr = new Image[4]; //images for animation when you lose

	ImageIcon iconWin;
	ImageIcon iconLose;
	ImageIcon iconGameStart;

	JRadioButton a,b,ab,o,positive,negative;
	ButtonGroup letters;
	ButtonGroup rhsigns;

	JLabel jlUserAnswer;
	JLabel jlAnswerText;
	
	Font f;
	Color c;
	
	// Strings for the 3 different cards for the game
    String START_PANEL = "Game Instructions";
    String BLOOD_TYPING_GAME = "Blood Typing Game";
    String BLOOD_TRANSFUSING_GAME = "Blood Transfusing Game";
    
    JComboBox cb; // Combox Box for selecting the cards for the game
    
	int win = 0; // 0 - Start, 1 - Won, 2 - Lost
	
	Timer timer; //timer initialization

	public BloodTyping() { //make constructor for frame
		super("Blood Typing Tackle"); //set title of window
		setSize(810, 760); //set window size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true); //set if you can make window small/big
		setLocation(10,50); //set location
		
		Container c = getContentPane();
		
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { START_PANEL, BLOOD_TYPING_GAME, BLOOD_TRANSFUSING_GAME };
        cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
		
    	c.add(comboBoxPane, BorderLayout.NORTH);

    	// Create the 3 cards
		card1 = new BloodStarting(); 
    	card2 = new BloodTypingGame();
    	card3 = new BloodTransfusing(this);

		cards = new JPanel(new CardLayout()); //apply card panel
		cards.add(card1, START_PANEL);
		cards.add(card2, BLOOD_TYPING_GAME);
		cards.add(card3, BLOOD_TRANSFUSING_GAME);
		
        add(cards, BorderLayout.CENTER);
		
        initNewGame(); //refers to new game
		//setContentPane(bloodtubes);
		setVisible(true);

	} //close BloodTyping constructor
	
	/** This method gets called when the user selects one of the combo box items
	 *  and it switches the card to show depending which combo box item was picked.
	 */
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
     
	/** This method initializes a completely new game for Blood Type */
	void initNewGame() {

		// Reset all the radio buttons
		letters.clearSelection();
		rhsigns.clearSelection();

		// clean up old answers
		userBloodGroup = null;
		userRhStatus = null;
		user = null;
		jlUserAnswer.setText("");

		int a = randomFlip();
		int b = randomFlip();
		int rh = randomFlip();

		// show the right tube images for the blood group
		if (a == 1) {
			labelA.setIcon(imgIcons[0]);
		} else {
			labelA.setIcon(imgIcons[1]);
		}

		if (b == 1) {
			labelB.setIcon(imgIcons[2]);
		} else {
			labelB.setIcon(imgIcons[3]);
		}

		// show the right tube images for rh
		if (rh == 1) {
			labelRh.setIcon(imgIcons[4]);
		} else {
			labelRh.setIcon(imgIcons[5]);
		}

		// save the answer based on what blood group and rh were randomly generated
		// so you can compare it later to the user's response
		if (a == 1 && b == 1) {
			answerBloodGroup = "AB";
		} else if (a == 1) {
			answerBloodGroup = "A";
		} else if ( b == 1) {
			answerBloodGroup = "B";
		} else {
			answerBloodGroup = "O";
		}

		if (rh == 1) {
			answerRhStatus = "+";
		} else {
			answerRhStatus = "-";
		}

		answer = answerBloodGroup + answerRhStatus;

		// reset the win lose text
		jlWinLose.setText("");
		// hide the button for going to blood transfusing game
		jbBloodTransfusingGame.setVisible(false); //http://stackoverflow.com/questions/1917365/hide-a-button-from-layout-in-java-swing
		
		if (timer != null) timer.stop();
		win = 0;

		System.out.println("Answer = " + answer);

	}
	
	// Returns either a 0 or 1 randomly
	private int randomFlip() {
		if (Math.random() < 0.5) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public class WinAction implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			win = 1;
			repaint();  // Call the repaint() method in the panel class.
		}
	}
	
	public class LoseAction implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			win = 2;
			repaint();  // Call the repaint() method in the panel class.
		}
	}
	
	class WinLosePanel extends JPanel {

		int animation_frame = 0;

		WinLosePanel() { //method that makes array for animation images (win and lose)
			winArr[0] = Toolkit.getDefaultToolkit().getImage("FirstWin.jpg");
			winArr[1] = Toolkit.getDefaultToolkit().getImage("SecondWin.jpg");
			winArr[2] = Toolkit.getDefaultToolkit().getImage("ThirdWin.jpg");
			winArr[3] = Toolkit.getDefaultToolkit().getImage("FourthWin.jpg");
			
			loseArr[0] = Toolkit.getDefaultToolkit().getImage("FirstLose.jpg");
			loseArr[1] = Toolkit.getDefaultToolkit().getImage("SecondLose.jpg");
			loseArr[2] = Toolkit.getDefaultToolkit().getImage("ThirdLose.jpg");
			loseArr[3] = Toolkit.getDefaultToolkit().getImage("FourthLose.jpg");

			//starting image
			imgGameStart = Toolkit.getDefaultToolkit().getImage("Starting.jpg"); //get image 1
		
			setPreferredSize(new Dimension(420, 220)); //set panel size where images are
			setBackground(Color.BLACK);
			repaint();
		}
		

		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (animation_frame > 3) animation_frame = 0;
			if (win == 1) { //if win is 1, then apply the image array for winning
				g.drawImage(winArr[animation_frame++], 0, 0, null);
			} else if(win == 2){ //if win is 2, then apply the image array for losing
				g.drawImage(loseArr[animation_frame++], 0, 0, null);
			} else { //if not, then apply starting image
				g.drawImage(imgGameStart, 0, 0, null);
				repaint();
			}
		}
	}

	class BloodTypingGame extends JPanel implements ActionListener {


		BloodTypingGame() {
			buttons = new Buttons(); //create panel (buttons) instance
			add(buttons); //setContentPane to buttons panel
			//setContentPane(buttons);

			jbNewGame = new JButton("New Game"); //make a new game button
			jbNewGame.addActionListener(this); //add action listener for button
			add(jbNewGame); //add button

			bloodtubes = new BloodTubes(); //make class for blood tubes (A,B,Rh)
			add(bloodtubes); //add class

			WinLosePanel jpWinLose = new WinLosePanel(); //make panel
			add(jpWinLose); //add panel
	

			// Create label for displaying whether the user Won or Lost 
			jlWinLose = new JLabel("Welcome", JLabel.CENTER);
			jlWinLose.setVerticalTextPosition(JLabel.CENTER);
			c = new Color(100,9,9);
			jlWinLose.setForeground(c);
			f = new Font("Serif", Font.BOLD, 40);
			jlWinLose.setFont(f);

			add(jlWinLose);

			jbBloodTransfusingGame = new JButton(GO_BLOOD_TRANSFUSING_GAME);
			jbBloodTransfusingGame.addActionListener(this);
			add(jbBloodTransfusingGame);
			jbBloodTransfusingGame.setVisible(false); //http://stackoverflow.com/questions/1917365/hide-a-button-from-layout-in-java-swing
			
			setBackground(Color.BLACK);
		}
		
		// This method is called when the user pressed the "New Game" button in the 
		// Blood Typing card
		public void actionPerformed(ActionEvent e) { // New Game button action
			String action = e.getActionCommand();
			if (NEW_GAME.equals(action)) { //if the button NEW GAME is clicked
				initNewGame(); //start a new game
			} else if (GO_BLOOD_TRANSFUSING_GAME.equals(action)) { //if the GO TO BLOOD TRANSFUSING GAME button is clicked
				System.out.println("Go to Blood transfusing game.");
				// go to the blood transfusing game
		        CardLayout cl = (CardLayout)(cards.getLayout()); //change to card layout
		        cl.show(cards, BLOOD_TRANSFUSING_GAME); //change card to third one
		        cb.setSelectedIndex(2);
			} else {
				System.err.println("Error in Blood Typing Game");
			}
		}


		
	} // end BloodTypingGame
	


	/** This class lays out the panel for the blood type game. It draws all the radio buttons
	 * that the user selects to choose an answer based on the random game showed to the user.
	 */
	class Buttons extends JPanel implements ActionListener{ //Buttons class
		boolean result;
		JLabel jlResult;
		JPanel bloodType;
		JPanel rh;
		String bloodGroup = "";
		String rhStatus = "";
		int ANIMATION_SPEED = 300; //ms

		Buttons(){ //constructor of Buttons
			a = new JRadioButton("Type A", false); //JRadioButton instance for blood type A
			b = new JRadioButton("Type B", false); //JRadioButton instance for blood type B
			ab = new JRadioButton("Type AB", false); //JRadioButton instance for blood type AB
			o = new JRadioButton("Type O", false); //JRadioButton instance for blood type O

			bloodType = new JPanel(new GridLayout(4, 1));
			bloodType.add(a); //add button for blood type A
			bloodType.add(b); //add button for blood type B
			bloodType.add(ab); //add button for blood type AB
			bloodType.add(o); //add button for blood type O

			a.addActionListener(this); //add ActionListener for blood type A
			b.addActionListener(this); //add ActionListener for blood type B
			ab.addActionListener(this); //add ActionListenerfor blood type AB
			o.addActionListener(this); //add ActionListener for blood type O

			//letter group adds each of the blood types
			letters = new ButtonGroup(); //Button Group instance for letter blood types (A,B,AB,O)
			//letters.setToolTipText("Pick one");
			letters.add(a); 
			letters.add(b);
			letters.add(ab);
			letters.add(o);

			//add(bloodType, BorderLayout.NORTH);

			positive = new JRadioButton("Rh+", false); //JRadioButton instance for Rh positive
			negative = new JRadioButton("Rh-", false); //JRadioButton instance for Rh negative

			rh = new JPanel(new GridLayout(2, 1));
			rh.add(positive); //add button for blood type Rh positive
			rh.add(negative); //add button for blood type Rh negative

			positive.addActionListener(this); //add ActionListener for blood type Rh positive
			negative.addActionListener(this); //add ActionListener for blood type Rh negative

			//Rh positive and negative group adds both signs
			rhsigns = new ButtonGroup();//Button Group instance for Rh blood types (+,-)
			//rhsigns.setToolTipText("Pick one");
			rhsigns.add(positive);
			rhsigns.add(negative);

			//add(rh, BorderLayout.CENTER);

			JPanel answer = new JPanel(new GridLayout(1, 2));
			jlAnswerText = new JLabel("               Your Answer: "); //JTextField instance for entire written blood type
			jlUserAnswer = new JLabel(""); //JTextField instance for entire written blood type
			answer.add(jlAnswerText);
			answer.add(jlUserAnswer);

			// Add all the components
			add(bloodType);
			add(rh);
			add(answer); //add text field

			setBackground(Color.BLACK);
		}
		

		/** This method checks all the radio buttons the user has pressed
		 * and forms the answer the user has given. It then checks to see
		 * if the user's answer matches the picture that was displayed in the game.
		 * If the user's answer is correct, then it displays a "You Win" icon.
		 * If the user's answer is wrong, then it asks the user to "Try Again".
		 */
		public void actionPerformed(ActionEvent e) { //event handler method for ActionListener
			String action = e.getActionCommand();
			if ("Type A".equals(action)) {
				System.out.println("Type A");
				userBloodGroup = "A";
			} else if ("Type B".equals(action)) {
				System.out.println("Type B");
				userBloodGroup = "B";
			} else if ("Type AB".equals(action)) {
				System.out.println("Type AB");
				userBloodGroup = "AB";
			} else if ("Type O".equals(action)) {
				System.out.println("Type O");
				userBloodGroup = "O";
			} else if ("Rh+".equals(action)) {
				System.out.println("Rh+");
				userRhStatus = "+";
			} else if ("Rh-".equals(action)) {
				System.out.println("Rh-");
				userRhStatus = "-";
			} else {
				System.err.println("Error");
			}
			user = userBloodGroup + userRhStatus;

			// if both Blood group and Rh values are set check if the answer matches
			if (userBloodGroup != null && userRhStatus != null) { 

				// Set the text field in the UI that display's user's answer
				jlUserAnswer.setText(user);
				// Check if user's response matches the true answer
				// and display the right icon depending on whether
				// the user's answer was right or wrong.
				if (answer.equals(user)) {
					win = 1;
					System.out.println("You Win");
					//jlWinLose.setIcon(iconWin);
					jlWinLose.setText("You Win");
					// Create animation
				    WinAction winAction = new WinAction();
				    timer = new Timer(ANIMATION_SPEED, winAction);
				    timer.start();
					
					// Show button for the user to go to the blood transfusing game
					jbBloodTransfusingGame.setVisible(true); // hide the button initially till the user wins
					card3.setRecipientBloodType(user); // pass the correct answer to the Blood Transfusing Game
				} else {
					win = 2;
					System.out.println("You Lose  ");
					//jlWinLose.setIcon(iconLose);
					jlWinLose.setText("You Lose");
					// Hide button for the user to go the blood tranfusion game
				    LoseAction loseAction = new LoseAction();
				    timer = new Timer(ANIMATION_SPEED, loseAction);
				    timer.start();
				    
					jbBloodTransfusingGame.setVisible(false); // hide the button initially till the user wins
				}
			}

		}
	} //close Buttons class
	
	/** This class lays out the panel for displaying the blood filled tubes
	 * for the blood type game. It loads all the images for all the different
	 * blood types. It also sets tool tips for each blood type tube to be displayed.
	 */
	class BloodTubes extends JPanel { //BloodTubes class

		BloodTubes(){ //constructor of BloodTubes
			setLayout(new GridLayout(1, 3)); //set layout to border layout

			tubes[0] = Toolkit.getDefaultToolkit().getImage("BloodTubeA.png"); //get image 1
			tubes[1] = Toolkit.getDefaultToolkit().getImage("BloodTubeNotA.png"); //get image 1
			tubes[2] = Toolkit.getDefaultToolkit().getImage("BloodTubeB.png"); //get image 1
			tubes[3] = Toolkit.getDefaultToolkit().getImage("BloodTubeNotB.png"); //get image 1
			tubes[4] = Toolkit.getDefaultToolkit().getImage("BloodTubeRh+.png"); //get image 1
			tubes[5] = Toolkit.getDefaultToolkit().getImage("BloodTubeRh-.png"); //get image 1

			for (int i = 0; i < 6; i++) {
				imgIcons[i] = new ImageIcon(tubes[i]);
			}


			labelA = new JLabel("A", imgIcons[0], JLabel.CENTER);
			labelB = new JLabel("B", imgIcons[2], JLabel.CENTER );
			labelRh = new JLabel("Rh", imgIcons[4], JLabel.CENTER );
			
			labelA.setToolTipText("<html>"
                    + "One's immune system produces antibodies and antigens,"
                    +"<br>"
                    + "which are made of protein. Antigens are glycoprotein markers that"
                    +"<br>"
                    + "are found on the outside of red blood cells. They determine a person's"
                    +"<br>"
					+ "ABO blood type. Antibodies match up with foreign antigens.They find"
					+"<br>"
					+ "their corresponding antigens and attack by creating 'clumps.'"
					+ "</html>");
			
			labelB.setToolTipText("<html>"
                    + "One's immune system produces antibodies and antigens,"
                    +"<br>"
                    + "which are made of protein. Antigens are glycoprotein markers that"
                    +"<br>"
                    + "are found on the outside of red blood cells. They determine a person's"
                    +"<br>"
					+ "ABO blood type. Antibodies match up with foreign antigens.They find"
					+"<br>"
					+ "their corresponding antigens and attack by creating 'clumps.'"
					+ "</html>");
			
			
			labelRh.setToolTipText("<html>"
                    + "Positive and negative blood types are controlled for the trait called"
                    +"<br>"
                    + "the Rh factor. This gene's possible alleles are: + and -"
                    +"<br>"
                    + "The Rh gene has instructions for an antigen called D. "
					+ "</html>");

			labelA.setVerticalTextPosition(JLabel.BOTTOM);
			labelB.setVerticalTextPosition(JLabel.BOTTOM);
			labelRh.setVerticalTextPosition(JLabel.BOTTOM);

			add(labelA);
			add(labelB);
			add(labelRh);
			
			//initNewGame();
		}
	} //close BloodTubes class
}