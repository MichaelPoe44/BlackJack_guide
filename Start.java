
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
    JButton[] dealerButtons = new JButton[13];
    JButton[] playerButtons = new JButton[13];
    JButton go;
    String[] faceValues = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
    String[] values = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
    //labels
    JLabel dealerCardLabel;
    JLabel dealerHeader;
    JLabel playerCard1Label;
    JLabel playerCard2Label;
    JLabel playerHeader;
    JLabel actionHeader;
    // classes
    Blackjack game;
    ImageIcon dealerImage;
    ImageIcon playerImage1;
    ImageIcon playerImage2;
    // text fonts
    Font font = new Font("Arial", Font.PLAIN, 24);
    //variables
    int dealerCard;
    int[] playerCards = new int[2];
    boolean playerCardOneReady = true;
    boolean firstCardPlaced = false;
    boolean secondCardPlaced = false;
    boolean dealerCardPlaced = false;
    String playerCard1;
    String playerCard2;
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
        for (int i=0, j=30; i < dealerButtons.length; i++, j+=50){
            dealerButtons[i] = new JButton(faceValues[i]);
            dealerButtons[i].setActionCommand("D"+values[i]); // start with D then value
            dealerButtons[i].setBounds(j,130,40,40);
            dealerButtons[i].addActionListener(this);
            frame.add(dealerButtons[i]);
        }
        
       //making player cards
        playerButtons[0] = new JButton("A");
        playerButtons[0].setBounds(30,480,40,40);
        playerButtons[0].addActionListener(this);
        playerButtons[0].setActionCommand("P1"); // all player buttons start with P then has the value
        frame.add(playerButtons[0]);

        for (int i=1, j=30; i < playerButtons.length; i++, j+=50){
            playerButtons[i] = new JButton(String.format("%d",i+1));
            playerButtons[i].setActionCommand(String.format("P%d", i+1)); // start with P then value
            playerButtons[i].setBounds(j,480,40,40);
            playerButtons[i].addActionListener(this);
            frame.add(playerButtons[i]);
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
        dealerHeader = new JLabel("Dealer Card:");
        dealerHeader.setBounds(30,50,200,90);
        dealerHeader.setFont(font);
        frame.add(dealerHeader);

        dealerCardLabel = new JLabel();
        dealerCardLabel.setBounds(220,30,60,87);
        frame.add(dealerCardLabel);





        // player header
        playerHeader = new JLabel("Player Cards:");
        playerHeader.setBounds(30,400,250,90);
        playerHeader.setFont(font);
        frame.add(playerHeader);

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
            if (input.length() < 3){
                dealerCard = Integer.parseInt(input.substring(1,2));
                dealerImage = getImageIcon(input.substring(1,2), "diamonds");
                
            }
            else {
                dealerCard = 10;  
                dealerImage = getImageIcon(input.substring(1, 3), "diamonds");
            }
            // if dealer card is an A will set header value to A (instead of 1)
            // if (dealerCard == 1){
            //     dealerHeader.setText("Dealer Card:  A");
            //     dealerImage = getImageIcon('A', "diamonds");
            // }
            
            dealerHeader.setText("Dealer Card:  ");
            dealerCardPlaced = true;
            dealerCardLabel.setIcon(dealerImage);
            System.out.println("Dealer: "+dealerCard);
            return;
        }



        //if a player button is pressed
        else {
            
            if (playerCardOneReady){
                if (input.length() < 3){
                    playerCards[0] = Integer.parseInt(input.substring(1, 2));
                }
                else {
                    playerCards[0] = 10;
                }
                playerCardOneReady = false;
                
                if (playerCards[0] == 1) playerCard1 = "A";
                else playerCard1 = String.valueOf(playerCards[0]);
            }
            else {
                if (input.length() < 3){
                    playerCards[1] = Integer.parseInt(input.substring(1, 2));
                }
                else {
                    playerCards[1] = 10;
                }
                playerCardOneReady = true;
                if (playerCards[1] == 1) playerCard2 = "A";
                else playerCard2 = String.valueOf(playerCards[1]);
                secondCardPlaced = true;
            }


            if (!firstCardPlaced){
                playerHeader.setText("Player Cards: "+playerCard1);
                firstCardPlaced = true;
            }
            else {
                playerHeader.setText("Player Cards: "+playerCard1+", "+playerCard2);
            }
            
            System.out.println("Player: "+playerCards[0]+", "+playerCards[1]);
            return;
        }
    }


}


