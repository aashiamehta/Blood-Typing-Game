import javax.swing.*; //import libraries
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class BloodStarting extends JPanel{
	JLabel labelA;
	JLabel labelB;
	Font f;
	Color colour;
	
	Image[] images = new Image[2]; //make array for images
	ImageIcon[] imgIcons = new ImageIcon[2]; //image icon array using images on top
	
	BloodStarting(){ //constructor of BloodStarting
		setLayout(new GridLayout(2, 1)); //set layout to border layout

		images[0] = Toolkit.getDefaultToolkit().getImage("BloodTypingInstructions.jpg"); //get image 1
		images[1] = Toolkit.getDefaultToolkit().getImage("BloodTransfusingInstructions.jpg"); //get image 1

		for (int i = 0; i < 2; i++) { //go through array using a for-loop
			imgIcons[i] = new ImageIcon(images[i]);
			
		f = new Font("Serif", Font.BOLD, 15); //change font of labels
		}

		labelA = new JLabel("<html>"
                    + "1) To determine the blood type, you need to use anti-serums"
                    +"<br>"
                    + "for each type of antigen. For instance, Anti-Serum A is added"
                    +"<br>"
                    + "to a sample of blood."
                    +"<br>"
                    +"<br>"
                    + "2) If you see clumps, this indicates a positive result and the"
                    +"<br>"
                    + "blood contains A antigens."
                    +"<br>"
                    +"<br>"
                    + "3) If the blood is normal, this indicates a negative result and"
                    +"<br>"
                    + "the blood does not have A antigens."
                    +"<br>"
                    +"<br>"
                    + "4) This process continues (Repeat Steps 2 & 3) by testing B and D"
                    +"<br>"
                    + "(Rh gene's antigen) anti-serums to determine the full blood type."
					+ "</html>", imgIcons[0], JLabel.CENTER); //blood typing directions, uses html to make new lines
		labelB = new JLabel("<html>"
                    + "1) To determine what blood types can be safely transfused, you"
                    +"<br>"
                    + "need to use the blood type."
                    +"<br>"
                    +"<br>"
                    + "2) Check the blood type. Refer to the chart for a proper transfusion."
                    +"<br>"
                    +"<br>"
                    + "3) Check the Rh factor. If the allele for the Rh gene is positive (+),"
                    +"<br>"
                    + "then you can transfuse positive (+) and negative (-) blood. If the"
                    +"<br>"
                    + "allele for the Rh gene is negative (-), then you can only transfuse"
                    +"<br>"
                    +"negative (-) blood."
					+ "</html>", imgIcons[1], JLabel.CENTER); //blood transfusing directions

		add(labelA); //add each direction (blood typing and transfusing)
		add(labelB);
		
		labelA.setFont(f); //set label font
		labelB.setFont(f);
		
		colour = new Color(100,9,9); //made a red-blood color to make text that color
		labelA.setForeground(colour); //made label text red-blood
		labelB.setForeground(colour); //made label text red-blood
		
		labelB.setToolTipText("O- is the universal donor. AB+ is the universal receiver."); //hover on label to know more
		
		//setBackground(Color.BLACK);
	}
	
}  // end class BloodStarting