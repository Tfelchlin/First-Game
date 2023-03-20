import javax.swing.*;

public class Frame extends JFrame {
    Frame(){
        this.add(new GamePannel());
        this.setTitle("Snake Knockoff");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
        this.setLocale(null);
    }
}
