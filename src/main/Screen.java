package src.main;


import javax.swing.*;


import java.awt.*;
import java.io.File;

public class Screen {
    MyFrame currentFrame;
    Screen()
    {
        new InterfaceFrame();
    }
    
void LoadMapOn()
{
    this.currentFrame = new LoadFrame();
}

void GameOn(){
    this.currentFrame = new GameFrame();
}

void InterfaceOn()
{
    this.currentFrame = new InterfaceFrame();
}

void setVisible()
{
    this.currentFrame.setVisible(true);
}

public class MyFrame extends JFrame{
    MyFrame(){
        setTitle("Bridge Game");
        setSize(800,800);
           
    }

    class backPanel extends JPanel{
        String image_name = "./image/Background.png";
        ImageIcon i = new ImageIcon(image_name);
        Image im = i.getImage();
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(im, 0, 0, getWidth(),getHeight(),this);
        }
    }

    ImageIcon imageSetSize(ImageIcon icon, int i , int j){
        Image ximg = icon.getImage();
        Image yimg = ximg.getScaledInstance(i,j, Image.SCALE_SMOOTH);
        ImageIcon xyimg = new ImageIcon(yimg);
        return xyimg;
    }

    void set_btn_image(JButton target,String Filename,int width, int height)
    {
        ImageIcon i = new ImageIcon(Filename);
        i = imageSetSize(i, width, height);
        target.setIcon(i);
        target.setBorderPainted(false);
        target.setFocusPainted(false);
        target.setContentAreaFilled(false);
    }

    @Override
    public void setTitle(String title) {
        // TODO Auto-generated method stub
        super.setTitle(title);
    }


}

public class InterfaceFrame extends MyFrame{

    InterfaceFrame()
    {
        backPanel background = new backPanel();
        background.setLayout(null);
        JButton load_btn = new JButton();
        load_btn.setPreferredSize(new Dimension(480,50));
        set_btn_image(load_btn, "./image/Load Button.png", 480, 50);
        load_btn.setBounds(180,380,480,50);
        JButton play_btn = new JButton();
        play_btn.setPreferredSize(new Dimension(480,50));
        set_btn_image(play_btn, "./image/Play Button.png", 480, 50);
        play_btn.setBounds(180,510,480,50);
        JButton exit_btn = new JButton();
        exit_btn.setPreferredSize(new Dimension(480,50));
        set_btn_image(exit_btn, "./image/Exit Button.png", 480, 50);
        exit_btn.setBounds(180,640,480,50);
        background.add(load_btn);
        background.add(play_btn);
        background.add(exit_btn);
        this.add(background);
        this.setBounds(300,100,800,800);
        this.setVisible(true);
        this.setResizable(false);
    }

   
    
}

public class GameFrame extends MyFrame{
   GameFrame(){
       setTitle("Bridge Game");
   }
}

public class LoadFrame extends MyFrame{
    LoadFrame()
    {
        setTitle("Bridge Game Load Map");
    }
}

}

