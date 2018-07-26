import javax.swing.*; //import libraries
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class BloodTransfusing extends JPanel implements ItemListener, ActionListener {

	// Strings for the 8 different blood types
	String O_NEG = "O-";     // index 0
	String O_POS = "O+";     // index 1
	String A_NEG = "A-";     // index 2
	String A_POS = "A+";     // index 3
	String B_NEG = "B-";     // index 4
	String B_POS = "B+";     // index 5
	String AB_NEG = "AB-";   // index 6
	String AB_POS = "AB+";   // index 7

	// Complete matrix of answers for all blood types.	
	// Index corresponds to the blood types defined above
	// See http://en.wikipedia.org/wiki/Blood_type for complete matrix.
	// Row number corresponds to the blood type of the recipient.
	// Col number corresponds to the blood type of the donor.
	// true in the cell means the recipient can receive blood
	// from that donor. false in the cell means they cannot receive blood
	// from that donor.
	// e.g. if allAnswers[2][2] is true, then recipient of Blood Type A-
	// can receive blood from donor of type A-
	boolean[][] allAnswers = new boolean[8][8]; 

	// Array to store user's selected answer based on checkboxes in the UI.
	// Index corresponds to the blood types defined above
	// *true* for the answer means that the corresponding blood type
	// can be a donor to the recipient.
	// e.g. selectedAnswers[3] = true means a donor with bloodtype A+ can donate
	// blood to this user.
	// Note what the user selected here may be right or wrong
	// We need to check the users's answer for correctness separately.
	boolean[] selectedAnswers = new boolean[8]; 

	String recipientBloodType; // this stores the Blood Type of the Blood recipient from the answer from the blood type game
	// as a string
	int recipientBloodTypeIndex; // this is set to the index of the recipient blood type

	String markup_start = "<html>";

	String instructionText = 
			"Since you answered the correct blood type, you can now proceed to"
					+"<br>"
					+"safely transfuse this blood to different patients in need." 
					+"<br>"
					+"<br>"
					+ "Check all boxes for the blood types that this " 
					+ "<br>"
					+ "patient can receive through transfusion."
					+ "<br>"
					+"<br>"
					+"Remember the correct blood type was: ";

	String markup_end = "<br>"
			+" "
			+"<br>"
			+" "
			+"<br>"
			+" "
			+"</html>";

	JPanel jpTop; // Panel for holding instruction and correct answer from the user
	JPanel jpCheckBoxes; // Panel for holding the checkboxes

	JCheckBox cbAPos, cbANeg, cbBPos, cbBNeg, cbABPos, cbABNeg, cbOPos, cbONeg;

	JButton jbCheckAnswer;

	JLabel jlBloodTransfusionInstruction; // label for instructions to the user
	Font font1;
	Color color;

	String CHECK_ANSWER = "Check Answer";
	String PLAY_ANOTHER_GAME = "Play Another Game";
    String BLOOD_TYPING_GAME = "Blood Typing Game";

	JLabel jlYouWinEntirely;
	Font font2;

	Button playAnotherGame;
	BloodTyping bloodTyping; // reference to BloodTyping object

	BloodTransfusing(BloodTyping bloodTyping){ 
		//GridLayout gl = new GridLayout(4,1);
		//setLayout(gl);
		this.bloodTyping = bloodTyping;
	
		jpTop = new JPanel();
		jpCheckBoxes = new JPanel();

		String labelText = markup_start + instructionText + recipientBloodType + markup_end;
		jlBloodTransfusionInstruction = new JLabel(labelText, JLabel.CENTER );

		color = new Color(100,9,9);
		font1 = new Font("Serif", Font.BOLD, 23);
		jlBloodTransfusionInstruction.setFont(font1);
		jlBloodTransfusionInstruction.setForeground(color);
		jpTop.add(jlBloodTransfusionInstruction);

		//add(jpTop);
		add(jpTop, BorderLayout.NORTH);

		// Build the panel for the checkboxes for the 8 different blood types
		cbONeg = new JCheckBox(O_NEG, false);
		cbOPos = new JCheckBox(O_POS, false);
		cbANeg = new JCheckBox(A_NEG, false);
		cbAPos = new JCheckBox(A_POS, false);
		cbBNeg = new JCheckBox(B_NEG, false);
		cbBPos = new JCheckBox(B_POS, false);
		cbABNeg = new JCheckBox(AB_NEG, false);
		cbABPos = new JCheckBox(AB_POS, false);

		cbONeg.addItemListener(this);
		cbOPos.addItemListener(this);
		cbANeg.addItemListener(this);
		cbAPos.addItemListener(this);
		cbBNeg.addItemListener(this);
		cbBPos.addItemListener(this);
		cbABNeg.addItemListener(this);
		cbABPos.addItemListener(this);

		jpCheckBoxes.add(cbONeg);
		jpCheckBoxes.add(cbOPos);
		jpCheckBoxes.add(cbANeg);
		jpCheckBoxes.add(cbAPos);
		jpCheckBoxes.add(cbBNeg);
		jpCheckBoxes.add(cbBPos);
		jpCheckBoxes.add(cbABNeg);
		jpCheckBoxes.add(cbABPos);

		jbCheckAnswer = new JButton(CHECK_ANSWER);
		jbCheckAnswer.addActionListener(this);

		jpCheckBoxes.add(jbCheckAnswer);
		
		
		playAnotherGame = new Button(PLAY_ANOTHER_GAME);
		playAnotherGame.addActionListener(this);

		jpCheckBoxes.add(playAnotherGame);


		add(jpCheckBoxes);
		add(jpCheckBoxes, BorderLayout.CENTER);

		jlYouWinEntirely = new JLabel("",JLabel.CENTER);
		jlYouWinEntirely.setVerticalTextPosition(JLabel.CENTER);

		font2 = new Font("Serif", Font.BOLD, 140);
		jlYouWinEntirely.setForeground(color);
		jlYouWinEntirely.setFont(font2);
		
		add(jlYouWinEntirely, BorderLayout.SOUTH);
		
		
		initializeAllAnswers();
		
		setBackground(Color.BLACK);
	}


	// Helper method to initialize all the correct answers
	// in the allAnswers array we have
	void initializeAllAnswers() {
		// See http://en.wikipedia.org/wiki/Blood_type for complete matrix of correct answers

		// Row corresponds to the recipient of blood 
		// and Column corresponds to the donor of the blood
		// Remember Blood Type O is a universal donor.

		// We only need to save *true* value in array cells 
		// that is allowed for blood transfusion
		// Java automatically stores *false* values in the array
		// by default when we create the array.

		// Recipient is O-
		allAnswers[0][0] = true; // O-, O-

		// Recipient is O+
		allAnswers[1][0] = true; // O+, O-
		allAnswers[1][1] = true; // O+, O+

		// Recipient is A-
		allAnswers[2][0] = true; // A-, O- 
		allAnswers[2][2] = true; // A-, A-

		// Recipient is A+
		allAnswers[3][0] = true; // A+, O-
		allAnswers[3][1] = true; // A+, O+
		allAnswers[3][2] = true; // A+, A-
		allAnswers[3][3] = true; // A+, A+

		// Recipient is B-
		allAnswers[4][0] = true; // B-, O-
		allAnswers[4][4] = true; // B-, B-

		// Recipient is B+
		allAnswers[5][0] = true; // B+, O- 
		allAnswers[5][1] = true; // B+, O+
		allAnswers[5][4] = true; // B+, B-
		allAnswers[5][5] = true; // B+, B+

		// Recipient is AB-
		allAnswers[6][0] = true; // AB-, O-
		allAnswers[6][2] = true; // AB-, A-
		allAnswers[6][4] = true; // AB-, B- 
		allAnswers[6][6] = true; // AB-, AB-

		// Recipient is AB+
		// Remember AB+ is a universal recipient, 
		// that is it can receive blood from all other blood types
		allAnswers[7][0] = true; // AB+, O- 
		allAnswers[7][1] = true; // AB+, O+
		allAnswers[7][2] = true; // AB+, A-
		allAnswers[7][3] = true; // AB+, A+
		allAnswers[7][4] = true; // AB+, B-
		allAnswers[7][5] = true; // AB+, B+
		allAnswers[7][6] = true; // AB+, AB-
		allAnswers[7][7] = true; // AB+, AB+

	}

	/**This method is used to pass the answer from the BloodType game
	 * that the user played to the Blood Transfusion game.
	 */
	void setRecipientBloodType(String bloodType) {
		recipientBloodType = bloodType;
		String labelText = markup_start + instructionText + recipientBloodType + markup_end;
		jlBloodTransfusionInstruction.setText(labelText);
		recipientBloodTypeIndex = getRecipientBloodTypeIndex(bloodType);
	}

	/** This method is called when the user selects or deselects one of the
	 * checkboxes for the blood types for the donor of the blood.
	 * Depending on what checkboxes was checked or unchecked by the user, the corresponding
	 * array element in the array where we store user's answers is updated to
	 * true or false.
	 */
	public void itemStateChanged(ItemEvent e) {
		int index = 0;
		Object source = e.getItemSelectable();

		// Find the index of the blood type that was selected or deselected in the checkbox
		if (source == cbONeg) {
			index = 0;
		} else if (source == cbOPos) {
			index = 1;
		} else if (source == cbANeg) {
			index = 2;
		} else if (source == cbAPos) {
			index = 3;
		} else if (source == cbBNeg) {
			index = 4;
		} else if (source == cbBPos) {
			index = 5;
		} else if (source == cbABNeg) {
			index = 6;
		} else if (source == cbABPos) {
			index = 7;
		} else {
			System.err.println("Error in selecting checkboxes in BloodTransfusion");
		}

		// if checkbox was selected, then set the selectedAnswer for the user
		// for that blood type to true, or set it to false
		if (e.getStateChange() == ItemEvent.SELECTED ) {
			selectedAnswers[index] = true; // checkbox was selected
		} else {
			selectedAnswers[index] = false; // checkbox was deslected
		}

	}

	/** This method is called when the user presses
	 * the Check Answers button.
	 */
	public void actionPerformed(ActionEvent e) { //event handler method for ActionListener
		String action = e.getActionCommand();
		if (CHECK_ANSWER.equals(action)) {
			System.out.println("Check Answer");
			boolean correct = checkAnswerCorrect();
			if (correct == true) {
				System.out.println("All answers correct.");
				jlYouWinEntirely.setText("<html>"
						+ "You Win"
						+ "<br>"
						+ "The Tackle"
						+ "</html>");
			} else {
				System.out.println("One or more answers incorrect. Try again.");
				jlYouWinEntirely.setText("<html>"
						+ "You Lose"
						+ "<br>"
						+ "The Tackle"
						+ "</html>");
			}
		} else if (PLAY_ANOTHER_GAME.equals(action)) {
			System.out.println("Play another game, starting from blood typing.");
			// go to the blood transfusion game
			JPanel cards = (JPanel) getParent();
			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, BLOOD_TYPING_GAME);
			bloodTyping.initNewGame();
			//bt.cb.setSelectedIndex(2);
		} else {
			System.err.println("Error in Blood Typing Game");
		}

	}



	/** Converts index of the recipient blood type
	 * 
	 * @param recipient's BloodType
	 * @return returns a number between 0 and 7 depending on the blood type. 0 for O- and 7 for AB+.
	 */
	int getRecipientBloodTypeIndex(String recipientBloodType) {
		int index = 0;
		if (O_NEG.equals(recipientBloodType)) {
			index = 0;
		} else if (O_POS.equals(recipientBloodType)) {
			index = 1;
		} else if (A_NEG.equals(recipientBloodType)) {
			index = 2;
		} else if (A_POS.equals(recipientBloodType)) {
			index = 3;
		} else if (B_NEG.equals(recipientBloodType)) {
			index = 4;
		} else if (B_POS.equals(recipientBloodType)) {
			index = 5;
		} else if (AB_NEG.equals(recipientBloodType)) {
			index = 6;
		} else if (AB_POS.equals(recipientBloodType)) {
			index = 7;
		} else {
			System.err.println("Error in getRecipientBloodType");
		}
		return index;
	}

	/** check user's answer to correct answers
	 * 
	 * @return false if any incorrect answer
	 */
	boolean checkAnswerCorrect(){
		boolean correct = true;
		boolean[] correctAnswers = allAnswers[recipientBloodTypeIndex];
		for(int i = 0; i < 8; i++) {
			if (correctAnswers[i] != selectedAnswers[i]) {
				correct = false;
				break;
			}
		}
		return correct;
	}


}  // end class BloodTransfusing