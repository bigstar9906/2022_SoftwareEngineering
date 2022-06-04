package src.main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOError;
import java.io.IOException;

public class Screen {
    MyFrame currentFrame;
    Map currentMap = new Map();
    Screen()
    {
        new InterfaceFrame();
    }
    
void LoadMapOn()
{
    this.currentFrame = new LoadFrame();
    setVisible(false);
}

void GameOn(){
    this.currentFrame = new GameFrame();
    setVisible(true);
}

void InterfaceOn()
{
    this.currentFrame = new InterfaceFrame();
    setVisible(true);
}

void setVisible(boolean b)
{
    this.currentFrame.setVisible(b);
}

public class MyFrame extends JFrame{            //Frame 기본 틀 구현
    MyFrame(){
        setTitle("Bridge Game");
        this.setBounds(300,100,800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
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

public class InterfaceFrame extends MyFrame{                //MyFrame을 통해 Interface 구현

    InterfaceFrame()
    {
        backPanel background = new backPanel();
        background.setLayout(null);
        JButton load_btn = new JButton();
        set_btn_image(load_btn, "./image/Load Button.png", 480, 50);
        load_btn.setBounds(180,380,480,50);
        JButton play_btn = new JButton();
        set_btn_image(play_btn, "./image/Play Button.png", 480, 50);
        play_btn.setBounds(180,510,480,50);
        JButton exit_btn = new JButton();
        set_btn_image(exit_btn, "./image/Exit Button.png", 480, 50);
        exit_btn.setBounds(180,640,480,50);
        JButton selected_arrow = new JButton();
        set_btn_image(selected_arrow, "./image/Arrow_Right.png",45,45);
        selected_arrow.setBounds(100,100,45,45);
        selected_arrow.setVisible(false);
        load_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                selected_arrow.setLocation(125,384);
                selected_arrow.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                selected_arrow.setVisible(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                LoadMapOn();
                setVisible(false);
            }
        });

        play_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                selected_arrow.setLocation(125,514);
                selected_arrow.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                selected_arrow.setVisible(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                GameOn();
                setVisible(false);
            }
        });

        exit_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                selected_arrow.setLocation(125,644);
                selected_arrow.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                selected_arrow.setVisible(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        background.add(load_btn);
        background.add(play_btn);
        background.add(exit_btn);
        background.add(selected_arrow);
        this.add(background);
        this.setVisible(true);
        this.setResizable(false);
    }

   
    
}

public class GameFrame extends MyFrame{
   GameFrame(){
       setTitle("Bridge Game");
       currentMap.printCurrentMapFile();
   }
}

public class LoadFrame extends MyFrame{
    LoadFrame()
    {
        boolean isvalidfile=false;
        setTitle("Bridge Game Load");
        while(!isvalidfile)
        {
        currentMap.selectMap();
        currentMap.printCurrentMapFile();
        try
        {
            isvalidfile = currentMap.mapInit();
        }
        catch(Exception ignore){}
        if(!isvalidfile)
        {
            map_load_error();
            currentMap.MapFile = currentMap.recent_validMapFile;
            try
            {
            currentMap.mapInit();
            }
            catch(Exception ignore) {}
        }
        }
        InterfaceOn();
    }


    public void map_load_error()
    {
        JOptionPane.showMessageDialog(null, "Selected Map file is not valid file.\nPlease Select another valid map file.", "ERROR",JOptionPane.ERROR_MESSAGE);
    }
}

}

