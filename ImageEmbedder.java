import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

public class ImageEmbedder extends JPanel implements ActionListener {
	private static final long serialVersionUID = 8943895826454065227L;
	static private final String newline = "\n";
    JButton encode, decode;
    JTextArea log;
    JFileChooser fc;
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == encode) {
            int returnVal = fc.showOpenDialog(ImageEmbedder.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Encoding: " + file.getName() + "." + newline);
                encode(file);
            } else {
                log.append("Encode cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == decode) {
            int returnVal = fc.showSaveDialog(ImageEmbedder.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Decoding: " + file.getName() + "." + newline);
                decode(file);
            } else {
                log.append("Decode cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
            
        }
    }
    
    public ImageEmbedder() {
        super(new BorderLayout());
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        encode = new JButton("Encode an image");
        encode.addActionListener(this);

        decode = new JButton("Decode an image");
        decode.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encode);
        buttonPanel.add(decode);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }
    
    private static void createGUI() {
        JFrame frame = new JFrame("Image Embedder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new ImageEmbedder());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void decode(File imageFile) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
        	System.out.println("Error in reading file.");
        }
        
        int messageIndex;
        int height = img.getHeight();
        int width = img.getWidth();
        int rgb;
        int red, green, blue, redAvg, greenAvg, blueAvg;
        String message = "";
        char[] lowerCase = new char[26];
        char[] upperCase = new char[26];
        char[] symbols = new char[32];
        
        lowerCase[0] = 'a';
        lowerCase[1] = 'b';
        lowerCase[2] = 'c';
        lowerCase[3] = 'd';
        lowerCase[4] = 'e';
        lowerCase[5] = 'f';
        lowerCase[6] = 'g';
        lowerCase[7] = 'h';
        lowerCase[8] = 'i';
        lowerCase[9] = 'j';
        lowerCase[10] = 'k';
        lowerCase[11] = 'l';
        lowerCase[12] = 'm';
        lowerCase[13] = 'n';
        lowerCase[14] = 'o';
        lowerCase[15] = 'p';
        lowerCase[16] = 'q';
        lowerCase[17] = 'r';
        lowerCase[18] = 's';
        lowerCase[19] = 't';
        lowerCase[20] = 'u';
        lowerCase[21] = 'v';
        lowerCase[22] = 'w';
        lowerCase[23] = 'x';
        lowerCase[24] = 'y';
        lowerCase[25] = 'z';
        
        upperCase[0] = 'A';
        upperCase[1] = 'B';
        upperCase[2] = 'C';
        upperCase[3] = 'D';
        upperCase[4] = 'E';
        upperCase[5] = 'F';
        upperCase[6] = 'G';
        upperCase[7] = 'H';
        upperCase[8] = 'I';
        upperCase[9] = 'J';
        upperCase[10] = 'K';
        upperCase[11] = 'L';
        upperCase[12] = 'M';
        upperCase[13] = 'N';
        upperCase[14] = 'O';
        upperCase[15] = 'P';
        upperCase[16] = 'Q';
        upperCase[17] = 'R';
        upperCase[18] = 'S';
        upperCase[19] = 'T';
        upperCase[20] = 'U';
        upperCase[21] = 'V';
        upperCase[22] = 'W';
        upperCase[23] = 'X';
        upperCase[24] = 'Y';
        upperCase[25] = 'Z';

        symbols[0] = '!';
        symbols[1] = '@';
        symbols[2] = '#';
        symbols[3] = '$';
        symbols[4] = '%';
        symbols[5] = '^';
        symbols[6] = '&';
        symbols[7] = '*';
        symbols[8] = '(';
        symbols[9] = ')';
        symbols[10] = '-';
        symbols[11] = '_';
        symbols[12] = '=';
        symbols[13] = '+';
        symbols[14] = '`';
        symbols[15] = '~';
        symbols[16] = '[';
        symbols[17] = '{';
        symbols[18] = ']';
        symbols[19] = '}';
        symbols[20] = ';';
        symbols[21] = ':';
        symbols[22] = '|';
        symbols[23] = '<';
        symbols[24] = ',';
        symbols[25] = '.';
        symbols[26] = '>';
        symbols[27] = '/';
        symbols[28] = '?';
        symbols[29] = '\\';
        symbols[30] = '\'';
        symbols[31] = '\"';

        for (int h = 2; h < height-1; h++) {
            for (int w = 1; w < width; w++) {
                rgb = img.getRGB(w, h+1);
                redAvg = (rgb >> 16 ) & 0x000000FF;
                greenAvg = (rgb >> 8 ) & 0x000000FF;
                blueAvg = (rgb) & 0x000000FF;
                
                rgb = img.getRGB(w, h-1);
                redAvg = (int) ((redAvg+(rgb >> 16 ) & 0x000000FF)/2);
                greenAvg = (int) ((greenAvg+(rgb >> 8 ) & 0x000000FF)/2);
                blueAvg = (int) ((blueAvg+(rgb) & 0x000000FF)/2);
                
                rgb = img.getRGB(w, h+1);
                red = (rgb >> 16 ) & 0x000000FF;
                green = (rgb >> 8 ) & 0x000000FF;
                blue = (rgb) & 0x000000FF;
                
                if(red != redAvg) {
                	messageIndex = redAvg-red;
                	message = message+symbols[messageIndex];
                }
                
                if(green != greenAvg) {
                	messageIndex = greenAvg-green;
                	message = message+lowerCase[messageIndex];
                }
                
                if(blue != blueAvg) {
                	messageIndex = blueAvg-blue;
                	message = message+upperCase[messageIndex];
                }
            }
        }

        System.out.println("The decoded message is: " +message);
    }
    
    public void encode(File imageFile) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
        	System.out.println("Error in reading file.");
        }
        
        int i = 0;
        int height = img.getHeight();
        int width = img.getWidth();
        int rgb;
        int red, green, blue, redAvg, greenAvg, blueAvg;
        char charIndex;
        String message = "Barthalomewl";
        char[] charArray = message.toCharArray();
        
        char[] lowerCase = new char[26];
        char[] upperCase = new char[26];
        char[] symbols = new char[32];
        
        lowerCase[0] = 'a';
        lowerCase[1] = 'b';
        lowerCase[2] = 'c';
        lowerCase[3] = 'd';
        lowerCase[4] = 'e';
        lowerCase[5] = 'f';
        lowerCase[6] = 'g';
        lowerCase[7] = 'h';
        lowerCase[8] = 'i';
        lowerCase[9] = 'j';
        lowerCase[10] = 'k';
        lowerCase[11] = 'l';
        lowerCase[12] = 'm';
        lowerCase[13] = 'n';
        lowerCase[14] = 'o';
        lowerCase[15] = 'p';
        lowerCase[16] = 'q';
        lowerCase[17] = 'r';
        lowerCase[18] = 's';
        lowerCase[19] = 't';
        lowerCase[20] = 'u';
        lowerCase[21] = 'v';
        lowerCase[22] = 'w';
        lowerCase[23] = 'x';
        lowerCase[24] = 'y';
        lowerCase[25] = 'z';
        
        upperCase[0] = 'A';
        upperCase[1] = 'B';
        upperCase[2] = 'C';
        upperCase[3] = 'D';
        upperCase[4] = 'E';
        upperCase[5] = 'F';
        upperCase[6] = 'G';
        upperCase[7] = 'H';
        upperCase[8] = 'I';
        upperCase[9] = 'J';
        upperCase[10] = 'K';
        upperCase[11] = 'L';
        upperCase[12] = 'M';
        upperCase[13] = 'N';
        upperCase[14] = 'O';
        upperCase[15] = 'P';
        upperCase[16] = 'Q';
        upperCase[17] = 'R';
        upperCase[18] = 'S';
        upperCase[19] = 'T';
        upperCase[20] = 'U';
        upperCase[21] = 'V';
        upperCase[22] = 'W';
        upperCase[23] = 'X';
        upperCase[24] = 'Y';
        upperCase[25] = 'Z';

        symbols[0] = '!';
        symbols[1] = '@';
        symbols[2] = '#';
        symbols[3] = '$';
        symbols[4] = '%';
        symbols[5] = '^';
        symbols[6] = '&';
        symbols[7] = '*';
        symbols[8] = '(';
        symbols[9] = ')';
        symbols[10] = '-';
        symbols[11] = '_';
        symbols[12] = '=';
        symbols[13] = '+';
        symbols[14] = '`';
        symbols[15] = '~';
        symbols[16] = '[';
        symbols[17] = '{';
        symbols[18] = ']';
        symbols[19] = '}';
        symbols[20] = ';';
        symbols[21] = ':';
        symbols[22] = '|';
        symbols[23] = '<';
        symbols[24] = ',';
        symbols[25] = '.';
        symbols[26] = '>';
        symbols[27] = '/';
        symbols[28] = '?';
        symbols[29] = '\\';
        symbols[30] = '\'';
        symbols[31] = '\"';
        
        for(int h = 2; h < height-1; h++) {
        	for(int w = 1; w < width; w++) {
        		if(i < charArray.length) {
        			charIndex = charArray[i];
        			for(int j = 0; j < symbols.length; j++) {
        				if(charIndex == symbols[j]) {
        					rgb = img.getRGB(w, h+1);
        	                redAvg = (rgb >> 16 ) & 0x000000FF;
        	                greenAvg = (rgb >> 8 ) & 0x000000FF;
        	                blueAvg = (rgb) & 0x000000FF;
        	                
        	                rgb = img.getRGB(w, h-1);
        	                redAvg = (int) ((redAvg+(rgb >> 16 ) & 0x000000FF)/2);
        	                greenAvg = (int) ((greenAvg+(rgb >> 8 ) & 0x000000FF)/2);
        	                blueAvg = (int) ((blueAvg+(rgb) & 0x000000FF)/2);
        	                
        	                red = j << 16;
        	                rgb = rgb+red;
        	                img.setRGB(w, h, rgb);
        				}
        			}
        	
        			for(int k = 0; k < lowerCase.length; k++) {
        				if(charIndex == lowerCase[k]) {
        					rgb = img.getRGB(w, h+1);
        	                redAvg = (rgb >> 16 ) & 0x000000FF;
        	                greenAvg = (rgb >> 8 ) & 0x000000FF;
        	                blueAvg = (rgb) & 0x000000FF;
        	                
        	                rgb = img.getRGB(w, h-1);
        	                redAvg = (int) ((redAvg+(rgb >> 16 ) & 0x000000FF)/2);
        	                greenAvg = (int) ((greenAvg+(rgb >> 8 ) & 0x000000FF)/2);
        	                blueAvg = (int) ((blueAvg+(rgb) & 0x000000FF)/2);
        	                
        	                green = k << 8;
        	                rgb = rgb+green;
        	                img.setRGB(w, h, rgb);
        				}
        			}
        	
        			for(int l = 0; l < upperCase.length; l++) {
        				if(charIndex == upperCase[l]) {
        					rgb = img.getRGB(w, h+1);
        	                redAvg = (rgb >> 16 ) & 0x000000FF;
        	                greenAvg = (rgb >> 8 ) & 0x000000FF;
        	                blueAvg = (rgb) & 0x000000FF;
        	                
        	                rgb = img.getRGB(w, h-1);
        	                redAvg = (int) ((redAvg+(rgb >> 16 ) & 0x000000FF)/2);
        	                greenAvg = (int) ((greenAvg+(rgb >> 8 ) & 0x000000FF)/2);
        	                blueAvg = (int) ((blueAvg+(rgb) & 0x000000FF)/2);
        	                
        	                blue = l;
        	                rgb = rgb+blue;
        	                img.setRGB(w, h, rgb);
        				}
        			}
        		}
        		i++;
        	}
        }
        
      	imageFile.mkdirs(); 
      	
      	try {
    	  imageFile.createNewFile();
      	} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println("The message has been sucessfully encoded.");
    }
    
    public static void main(String args[]) {
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createGUI();
            }
        });
    }
}