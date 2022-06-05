package src.main;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ColorUIResource;

import java.awt.*;
import java.awt.event.*;

public class Screen {
    Map currentMap = new Map();
    Screen()
    {
        new MyFrame();
        new InterfacePanel();
    }
    
void LoadMapOn()
{
    new LoadFrame();
}

void GameOn(){
    new GameFrame();
}

void InterfaceOn()
{
    new InterfacePanel();
}



public class MyFrame extends JFrame{            //Frame 기본 틀 구현
    MyFrame(){
        setTitle("Bridge Game");
        this.setBounds(300,100,800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("./image/icon2.png");
        Image ic = icon.getImage();
        setIconImage(ic);
    }

    class backPanel extends JPanel{
        @Override
        public void setBackground(Color bg) {
            super.setBackground(new Color(255,245,228));
        }
        String image_name;
        ImageIcon i = new ImageIcon(image_name);
        Image im = i.getImage();
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(im, 0, 0, getWidth(),getHeight(),this);
        }
    }

    
    

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }


}

public class InterfacePanel extends MyFrame{                //MyFrame을 통해 Interface 구현

    InterfacePanel()
    {
        backPanel background_interface = new backPanel();
        background_interface.i = new ImageIcon("./image/Interface Frame.png");
        background_interface.im = background_interface.i.getImage();
        background_interface.setLayout(null);
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

        background_interface.add(load_btn);
        background_interface.add(play_btn);
        background_interface.add(exit_btn);
        background_interface.add(selected_arrow);
        this.add(background_interface);
        this.setVisible(true);
        this.setResizable(false);
    }

   
    
}

public class GameFrame extends MyFrame{
    boolean isMenuOpen = false;
    JFrame menu_cover;
   GameFrame(){       
       backPanel background_Game = new backPanel();
       background_Game.setLayout(null);
       background_Game.setBackground(new Color(255,245,228));
       background_Game.add(currentMapPanel(100,100,600,600));
       JButton menu_btn = new JButton();
       set_btn_image(menu_btn, "./image/menu_icon2.png", 80, 80);
       menu_btn.setBounds(10,10,80,80);
       background_Game.add(menu_btn);    
       this.add(background_Game);
       this.setVisible(true);
       this.setResizable(false);
       menu_btn.addMouseListener(new MouseAdapter() {
        
        @Override
        public void mousePressed(MouseEvent e) {
           menu_cover = new JFrame();
           menu_cover.setLayout(null);
           menu_cover.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           Point frame_location = getFrameLocationX();
           Fix_location();
           JButton resume_btn = new JButton();
           set_btn_image(resume_btn, "./image/Resume Button.png", 480, 50);
           resume_btn.setBounds(150,160,480,50);
           JButton return_btn = new JButton();
           set_btn_image(return_btn, "./image/Return Button.png", 480, 50);
           return_btn.setBounds(150,360,480,50);
           JButton exit_btn = new JButton();
           set_btn_image(exit_btn, "./image/Exit Game Button.png", 480, 50);
           exit_btn.setBounds(150,560,480,50);
           menu_cover.add(resume_btn);
           menu_cover.add(return_btn);
           menu_cover.add(exit_btn);
           menu_cover.setBounds(frame_location.x+10,frame_location.y+30,780,760);
           menu_cover.setUndecorated(true);
           menu_cover.setBackground(new Color(255,245,128,122));
           menu_cover.setVisible(true);

            resume_btn.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Free_location();
                menu_cover.setVisible(false);
            }   
           });
            return_btn.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                InterfaceOn();
                menu_cover.setVisible(false);
                setVisible(false);            
            }   
           });

            exit_btn.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
                
            }   
           });

        }
        });
   }

   public Point getFrameLocationX()
       {
           return this.getLocation();
       }
    public void Fix_location()
    {
        this.setEnabled(false);
    }
    public void Free_location()
    {
        this.setEnabled(true);
    }



}

public class LoadFrame extends MyFrame{

    JPanel mapLayout = new JPanel();
    LoadFrame()
    {
        backPanel background_Load = new backPanel();
        background_Load.i = new ImageIcon("./image/Load Map Frame.png");
        background_Load.im = background_Load.i.getImage();
        background_Load.setLayout(null);
        JButton select_File_btn = new JButton();
        set_btn_image(select_File_btn, "./image/Select File Icon.png", 80, 80);
        select_File_btn.setBounds(660,490,80,80);
        JButton menu_btn = new JButton();
        set_btn_image(menu_btn, "./image/Menu Icon.png", 80, 80);
        menu_btn.setBounds(660,625,80,80);

        mapLayout.setBounds(25,165,580,580);
        mapLayout.setLayout(null);
        mapLayout.setBackground(new Color(255,235,228));
        int tileSize = Math.floorDiv(580, Math.max(currentMap.Mapsize_row,currentMap.Mapsize_col));
        System.out.println(tileSize);
        
        mapLayout.add(currentMapPanel(0,0,580, 580));

        select_File_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean isvalidfile=false;
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
                    mapLayout.removeAll();
                    mapLayout.add(currentMapPanel(0,0,580,580));
                    mapLayout.revalidate();
                    mapLayout.repaint();
            }
        });

        menu_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                InterfaceOn();
                setVisible(false);
            }
        });

        background_Load.add(select_File_btn);
        background_Load.add(menu_btn);
        background_Load.add(mapLayout);
        this.add(background_Load);
        this.setVisible(true);
        this.setResizable(false);
        
    }


    public void map_load_error()
    {
        JOptionPane.showMessageDialog(null, "Selected Map file is not valid file.\nPlease Select another valid map file.", "ERROR",JOptionPane.ERROR_MESSAGE);
    }
}


    public JPanel currentMapPanel(int x, int y,int width,int height)
    {
        JPanel p = new JPanel();
        p.setBackground(new Color(255,245,228));
        p.setLayout(null);
        p.setBounds(x,y,width,height);
        p.setVisible(true);
        int tileSize = Math.floorDiv(width, Math.max(currentMap.Mapsize_row,currentMap.Mapsize_col));
        for(int i = 0; i<currentMap.Mapsize_row;i++)
        {
            for(int j = 0; j<currentMap.Mapsize_col;j++)
            {
                if(currentMap.isValidChar(currentMap.current_map[i][j]))
                {
                    JButton cell = new JButton();
                    System.out.println(currentMap.Map_row_Min+" "+currentMap.Map_col_Min);
                    if(1-currentMap.Map_row_Min==i&&1-currentMap.Map_col_Min==j)
                    {
                        set_btn_image(cell,"./image/Start ground.png", tileSize-5, tileSize-5);
                    }
                    else{
                    switch(currentMap.current_map[i][j])
                    {
                        case 'C': case 'B' : case 'b' :
                        set_btn_image(cell, "./image/ground.png", tileSize-5, tileSize-5);
                        break;
                        case 'S' :
                        set_btn_image(cell, "./image/Saw.png", tileSize-5, tileSize-5);
                        break;
                        case 'P' :
                        set_btn_image(cell, "./image/Driver.png", tileSize-5, tileSize-5);
                        break;
                        case 'H' :
                        set_btn_image(cell, "./image/Hammer.png", tileSize-5, tileSize-5);
                        break;
                        case 'E' :
                        set_btn_image(cell, "./image/End ground.png", tileSize-5, tileSize-5);
                        break;
                        case '=' :
                        set_btn_image(cell, "./image/bridge.png", tileSize-5, tileSize-5);
                        
                    }
                    }
                    cell.setBounds(j*tileSize,i*tileSize,tileSize,tileSize);
                    p.add(cell);
                }

            }
        }
        return p;

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


}

