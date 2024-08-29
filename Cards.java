import javax.swing.*;
import java.awt.Image;


class Cards {
    

    public Cards(String value, String suit){
        String URL = "PNG-cards/"+value+"_of_"+suit+".png";
        ImageIcon originalIcon = new ImageIcon(URL);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(60, 87,4);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
    }

}

