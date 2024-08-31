
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;


class Start{
    public static void main(String[] args){
        Window window = new Window();
    }
        

}



class Window extends JFrame implements ActionListener{


    /////////////////
    JFrame frame;
    //buttons
    JButton dealerCardButtons;
    JButton playerButtons;
    JButton go;
    final String[] faceValues = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
    final String[] values = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
    //labels
    JLabel dealerCardLabel;
    JLabel dealerHeader;
    JLabel playerCard1Label;
    JLabel playerCard2Label;
    JLabel playerHeader;
    JLabel actionHeader;
    // BlackJack class
    Blackjack game;
    // text fonts
    final Font font = new Font("Arial", Font.PLAIN, 24);
    //variables
    int dealerCard;
    int[] playerCards = new int[2];
    boolean playerCardOneReady = true;
    boolean secondCardPlaced = false;
    boolean dealerCardPlaced = false;
    ImageIcon dealerImage;
    ImageIcon playerImage1;
    ImageIcon playerImage2;
    String suit = "clubs";
    String dealerCardString;
    String player1stCardString;
    String player2ndCardString;
    ////////////////

    //constructor
    public Window(){
        
        makeFrame();
        makeButtons();
        makeLabels();
        frame.setVisible(true);

    }

    // makes frame
    private void makeFrame(){
        frame = new JFrame("BlackJack Bot");
        frame.setDefaultCloseOperation(2);
        frame.setSize(700,700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

    }

    private void makeButtons(){
        //making dealer card buttons
        for (int i=0, j=30; i < 13; i++, j+=50){
            dealerCardButtons = new JButton(faceValues[i]);
            dealerCardButtons.setActionCommand("DC"+values[i]); // start with D then value
            dealerCardButtons.setBounds(j,130,40,40);
            dealerCardButtons.addActionListener(this);
            frame.add(dealerCardButtons);
        }
        // makes dealer suit buttons
        JButton dealerSuitButtons;
        for (int i=0, j=40; i < 4; i++, j+=30){
            dealerSuitButtons = new JButton(values[i]);
            dealerSuitButtons.setActionCommand("DS"+values[i]);
            dealerSuitButtons.setBounds(j,180,20,20);
            dealerSuitButtons.addActionListener(this);
            frame.add(dealerSuitButtons);
        }
        
       //making player buttons
        for (int i=0, j=30; i < 13; i++, j+=50){
            playerButtons = new JButton(faceValues[i]);
            playerButtons.setActionCommand("PC"+values[i]); // start with P then value
            playerButtons.setBounds(j,480,40,40);
            playerButtons.addActionListener(this);
            frame.add(playerButtons);
        }

        //making go button 
        go = new JButton("Go");
        go.setBounds(30,265,60,60);
        go.addActionListener(this);
        frame.add(go);
    }

    //makes lables
    private void makeLabels(){
        // dealer header
        dealerHeader = new JLabel("Dealer Card:  ");
        dealerHeader.setBounds(30,50,200,90);
        dealerHeader.setFont(font);
        frame.add(dealerHeader);
        
        //dealer card label
        dealerCardLabel = new JLabel();
        dealerCardLabel.setBounds(220,30,60,87);
        frame.add(dealerCardLabel);


        // player header
        playerHeader = new JLabel("Player Cards:");
        playerHeader.setBounds(30,400,250,90);
        playerHeader.setFont(font);
        frame.add(playerHeader);

        //player 1st card label
        playerCard1Label = new JLabel();
        playerCard1Label.setBounds(220,380,60,87);
        frame.add(playerCard1Label);

        //player 2nd card label
        playerCard2Label = new JLabel();
        playerCard2Label.setBounds(300,380,60,87);
        frame.add(playerCard2Label);


        //Action header
        actionHeader = new JLabel();
        actionHeader.setBounds(150,255,500,90);
        actionHeader.setFont(font);
        frame.add(actionHeader);


    }


    private ImageIcon getImageIcon(String value, String suit){
        String URL = "PNG-cards/"+value+"_of_"+suit+".png";
        ImageIcon originalIcon = new ImageIcon(URL);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(60, 87,4);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        return resizedIcon;
    }
    






    // processes button clicks
    public void actionPerformed(ActionEvent e){
        //takes the action command of the button pressed
        String input = e.getActionCommand();
        //ImageIcon image;



        // if go button
        if (input.equals("Go")){
            String text;
            if (!secondCardPlaced || !dealerCardPlaced){
                text = "Invalid";
            }
            else{
                game = new Blackjack();
                text = game.process(dealerCard, playerCards);
            }
            
            actionHeader.setText(text);
            return;
        }


        
        // if a dealer button is pressed
        else if (input.charAt(0) == 'D'){
            // if a card button is pressed
            if (input.charAt(1) == 'C'){
                if (input.length() < 4){
                    dealerCardString = input.substring(2,3);
                    dealerCard = Integer.parseInt(input.substring(2,3)); 
                }  

                else {
                    dealerCardString = input.substring(2, 4);
                    dealerCard = 10;  
                }
            }
            // if a suit button is pressed
            else {
                int x = Integer.parseInt(input.substring(2, 3));
                if (x == 1) suit = "clubs";
                else if (x == 2) suit = "diamonds";
                else if (x == 3) suit = "hearts";
                else if (x == 4) suit = "spades";
            }
            
            dealerCardPlaced = true;

            dealerImage = getImageIcon(dealerCardString, suit);
            dealerCardLabel.setIcon(dealerImage);
            System.out.println("Dealer: "+dealerCard);
            return;
        }



        //if a player button is pressed
        else {
            
            if (playerCardOneReady){
                if (input.length() < 4){
                    playerCards[0] = Integer.parseInt(input.substring(2, 3));
                    player1stCardString = input.substring(2,3);
                }
                else {
                    playerCards[0] = 10;
                    player1stCardString = input.substring(2,4);
                }

                playerCardOneReady = false;
                playerImage1 = getImageIcon(player1stCardString,"clubs");
                playerCard1Label.setIcon(playerImage1);
            }

            else {
                if (input.length() < 4){
                    playerCards[1] = Integer.parseInt(input.substring(2, 3));
                    player2ndCardString = input.substring(2,3);
                }
                else {
                    playerCards[1] = 10;
                    player2ndCardString = input.substring(2,4);
                }

                playerCardOneReady = true;
                playerImage2 = getImageIcon(player2ndCardString, "clubs");
                playerCard2Label.setIcon(playerImage2);
                secondCardPlaced = true;
            }


        }
    }


}


