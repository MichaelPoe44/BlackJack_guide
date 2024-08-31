
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
    final String[] suits = {"clubs", "diamonds","hearts","spades"};
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
    boolean firstCardPlaced = false;
    boolean secondCardPlaced = false;
    boolean dealerCardPlaced = false;
    ImageIcon dealerImage;
    ImageIcon playerImage1;
    ImageIcon playerImage2;
    String dealerSuit = "clubs";
    String player1stSuit = "clubs";
    String player2ndSuit = "clubs";
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
        for (int i=0, x=30; i < 13; i++, x+=50){
            dealerCardButtons = new JButton(faceValues[i]);
            dealerCardButtons.setActionCommand("DC"+values[i]); // start with D, then card indecator, then value
            dealerCardButtons.setBounds(x,130,40,40);
            dealerCardButtons.addActionListener(this);
            frame.add(dealerCardButtons);
        }
        // makes dealer suit buttons
        JButton dealerSuitButtons;
        for (int i=0, x=40; i < 4; i++, x+=30){
            ImageIcon suitImage = getImageIcon("0", suits[i],20,29);
            dealerSuitButtons = new JButton(suitImage);
            dealerSuitButtons.setActionCommand("DS"+values[i]);
            dealerSuitButtons.setBounds(x,180,20,20);
            dealerSuitButtons.addActionListener(this);
            frame.add(dealerSuitButtons);
        }
        
       //making player buttons
        for (int i=1, y=430; i<3; i++, y+=80){
            for (int j=0, x=30; j<13; j++, x+=50){
                playerButtons = new JButton(faceValues[j]);
                playerButtons.setActionCommand("P"+String.valueOf(i)+"C"+values[j]); // start with P, then number indecator
                playerButtons.setBounds(x,y,40,40);                   //then card indecator, then value
                playerButtons.addActionListener(this);
                frame.add(playerButtons);
            }
        }
        JButton playerSuitButtons;
        for (int i=1, y=480; i<3; i++, y+=80){
            for (int j=0, x=40; j<4; j++, x+=30){  
                ImageIcon suitImage = getImageIcon("0", suits[j],20,29);
                playerSuitButtons = new JButton(suitImage);
                playerSuitButtons.setActionCommand("P"+String.valueOf(i)+"S"+values[j]);
                playerSuitButtons.setBounds(x,y,20,20);
                playerSuitButtons.addActionListener(this);
                frame.add(playerSuitButtons);
            }
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
        playerHeader.setBounds(30,350,250,90);
        playerHeader.setFont(font);
        frame.add(playerHeader);

        //player 1st card label
        playerCard1Label = new JLabel();
        playerCard1Label.setBounds(220,330,60,87);
        frame.add(playerCard1Label);

        //player 2nd card label
        playerCard2Label = new JLabel();
        playerCard2Label.setBounds(300,330,60,87);
        frame.add(playerCard2Label);


        //Action header
        actionHeader = new JLabel();
        actionHeader.setBounds(150,255,500,90);
        actionHeader.setFont(font);
        frame.add(actionHeader);


    }


    private ImageIcon getImageIcon(String value, String suit, int width, int height){
        String URL = "PNG-cards/"+value+"_of_"+suit+".png";
        ImageIcon originalIcon = new ImageIcon(URL);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height,4);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        return resizedIcon;
    }
    






    // processes button clicks
    public void actionPerformed(ActionEvent e){
        //takes the action command of the button pressed
        String input = e.getActionCommand();

        // if go button
        if (input.equals("Go")){
            String text;
            if (!secondCardPlaced || !dealerCardPlaced || !firstCardPlaced){
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
                if (x == 1) dealerSuit = "clubs";
                else if (x == 2) dealerSuit = "diamonds";
                else if (x == 3) dealerSuit = "hearts";
                else if (x == 4) dealerSuit = "spades";
            }
            
            dealerCardPlaced = true;

            dealerImage = getImageIcon(dealerCardString, dealerSuit,60, 87);
            dealerCardLabel.setIcon(dealerImage);
            return;
        }



        //if a player button is pressed
        else {
            // if button effects 1st card
            if (input.charAt(1) == '1'){
                // if button effects The Card number
                if (input.charAt(2) == 'C'){
                    if (input.length() < 5){
                        playerCards[0] = Integer.parseInt(input.substring(3, 4));
                        player1stCardString = input.substring(3,4);
                    }
                    else {
                        playerCards[0] = 10;
                        player1stCardString = input.substring(3,5);
                    }
                    firstCardPlaced = true;
                }
                // if button effects the suit
                else if (input.charAt(2) == 'S'){
                    int x = Integer.parseInt(input.substring(3, 4));
                    if (x == 1) player1stSuit = "clubs";
                    else if (x == 2) player1stSuit = "diamonds";
                    else if (x == 3) player1stSuit = "hearts";
                    else if (x == 4) player1stSuit = "spades";
                }
                playerImage1 = getImageIcon(player1stCardString,player1stSuit,60, 87);
                playerCard1Label.setIcon(playerImage1);
            }


            else if (input.charAt(1) == '2'){
                if (input.charAt(2) == 'C'){
                    if (input.length() < 5){
                        playerCards[1] = Integer.parseInt(input.substring(3, 4));
                        player2ndCardString = input.substring(3,4);
                    }
                    else {
                        playerCards[1] = 10;
                        player2ndCardString = input.substring(3,5);
                    }
                    secondCardPlaced = true;
                }
                else if (input.charAt(2) == 'S'){
                    int x = Integer.parseInt(input.substring(3, 4));
                    if (x == 1) player2ndSuit = "clubs";
                    else if (x == 2) player2ndSuit = "diamonds";
                    else if (x == 3) player2ndSuit = "hearts";
                    else if (x == 4) player2ndSuit = "spades";
                }

                
                playerImage2 = getImageIcon(player2ndCardString, player2ndSuit,60, 87);
                playerCard2Label.setIcon(playerImage2);
            }


        }
    }


}


