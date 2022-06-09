package src.main;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.*;

public class Screen {
    Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    Map currentMap = new Map();

    Screen() {
        new MyFrame();
        new InterfacePanel();
    }

    void LoadMapOn() {
        new LoadFrame();
    }

    void GameOn(int player_num) {
        new GameFrame(player_num);
    }

    void InterfaceOn() {
        new InterfacePanel();
    }

    public class MyFrame extends JFrame { // Frame 기본 틀 구현
        JPanel p;
        MyFrame() {
            setTitle("Bridge Game");
            this.setBounds(300, 100, 800, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ImageIcon icon = new ImageIcon("./image/icon2.png");
            Image ic = icon.getImage();
            setIconImage(ic);
        }

        class backPanel extends JPanel {
            @Override
            public void setBackground(Color bg) {
                super.setBackground(new Color(255, 245, 228));
            }

            String image_name;
            ImageIcon i = new ImageIcon(image_name);
            Image im = i.getImage();

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(im, 0, 0, getWidth(), getHeight(), this);
            }
        }

        @Override
        public void setTitle(String title) {
            super.setTitle(title);
        }
        
        public JPanel currentMapPanel(int x, int y, int width, int height) {
            p = new JPanel();
            p.setBackground(new Color(255, 245, 228));
            p.setLayout(null);
            p.setBounds(x, y, width, height);
            p.setVisible(true);
            int tileSize = Math.floorDiv(width, Math.max(currentMap.Mapsize_row, currentMap.Mapsize_col));
            for (int i = 0; i < currentMap.Mapsize_row; i++) {
                for (int j = 0; j < currentMap.Mapsize_col; j++) {
                    if (currentMap.isValidChar(currentMap.current_map[i][j])) {
                        JButton cell = new JButton();
                        if (1 - currentMap.Map_row_Min == i && 0 == j) {
                            set_btn_image(cell, "./image/Start ground.png", tileSize - 5, tileSize - 5);
                        } else {
                            switch (currentMap.current_map[i][j]) {
                                case 'C':
                                case 'B':
                                case 'b':
                                    set_btn_image(cell, "./image/ground.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'S':
                                    set_btn_image(cell, "./image/Saw.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'P':
                                    set_btn_image(cell, "./image/Driver.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'H':
                                    set_btn_image(cell, "./image/Hammer.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'E':
                                    set_btn_image(cell, "./image/End ground.png", tileSize - 5, tileSize - 5);
                                    break;
                                case '=':
                                    set_btn_image(cell, "./image/bridge.png", tileSize - 5, tileSize - 5);
    
                            }
                        }
                        cell.setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                        p.add(cell);
                    }
    
                }
            }
            return p;
    
        }

    }

    public class InterfacePanel extends MyFrame { // MyFrame을 통해 Interface 구현
        InterfacePanel() {
            backPanel background_interface = new backPanel();
            background_interface.i = new ImageIcon("./image/Interface Frame.png");
            background_interface.im = background_interface.i.getImage();
            background_interface.setLayout(null);
            JButton load_btn = new JButton();
            set_btn_image(load_btn, "./image/Load Button.png", 480, 50);
            load_btn.setBounds(180, 380, 480, 50);
            JButton play_btn = new JButton();
            set_btn_image(play_btn, "./image/Play Button.png", 480, 50);
            play_btn.setBounds(180, 510, 480, 50);
            JButton exit_btn = new JButton();
            set_btn_image(exit_btn, "./image/Exit Button.png", 480, 50);
            exit_btn.setBounds(180, 640, 480, 50);
            JButton selected_arrow = new JButton();
            set_btn_image(selected_arrow, "./image/Arrow_Right.png", 45, 45);
            selected_arrow.setBounds(100, 100, 45, 45);
            selected_arrow.setVisible(false);
            load_btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    selected_arrow.setLocation(125, 384);
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
                    selected_arrow.setLocation(125, 514);
                    selected_arrow.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    selected_arrow.setVisible(false);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    JFrame select_player_num = new JFrame();
                    select_player_num.setLayout(null);
                    select_player_num.setAlwaysOnTop(true);
                    Point frame_location = getFrameLocationX();
                    Fix_location();
                    JButton TwoPlayer_btn = new JButton();
                    set_btn_image(TwoPlayer_btn, "./image/2 Player Button.png", 360, 40);
                    TwoPlayer_btn.setBounds(35, 75, 360, 40);
                    JButton ThreePlayer_btn = new JButton();
                    set_btn_image(ThreePlayer_btn, "./image/3 Player Button.png", 360, 40);
                    ThreePlayer_btn.setBounds(35, 190, 360, 40);
                    JButton FourPlayer_btn = new JButton();
                    set_btn_image(FourPlayer_btn, "./image/4 Player Button.png", 360, 40);
                    FourPlayer_btn.setBounds(35, 305, 360, 40);
                    select_player_num.add(TwoPlayer_btn);
                    select_player_num.add(ThreePlayer_btn);
                    select_player_num.add(FourPlayer_btn);
                    TwoPlayer_btn.addMouseListener(new MouseInputAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            GameOn(2);
                            select_player_num.setVisible(false);
                            setVisible(false);
                        }
                    });

                    ThreePlayer_btn.addMouseListener(new MouseInputAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            GameOn(3);
                            select_player_num.setVisible(false);
                            setVisible(false);
                        }
                    });

                    FourPlayer_btn.addMouseListener(new MouseInputAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            GameOn(4);
                            select_player_num.setVisible(false);
                            setVisible(false);
                        }
                    });
                    int temp_x, temp_y;
                    select_player_num.setUndecorated(true);
                    select_player_num.setBackground(new Color(255, 245, 228, 227));
                    temp_x = frame_location.x + 792; // Player 수 설정 화면이 밖으로 나가지 않게 함
                    temp_y = frame_location.y + 370;
                    if (frame_location.x + 1222 > screen_size.width) {
                        temp_x = screen_size.width - 430;
                    }
                    if (frame_location.y + 792 > screen_size.height) {
                        temp_y = screen_size.height - 422;
                    }
                    select_player_num.setBounds(temp_x, temp_y, 430, 422);
                    select_player_num.setVisible(true);
                    setVisible(true);
                }
            });

            exit_btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    selected_arrow.setLocation(125, 644);
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

        public Point getFrameLocationX() {
            return this.getLocation();
        }

        public void Fix_location() {
            this.setEnabled(false);
        }

    }

    public class GameFrame extends MyFrame {
        boolean isMenuOpen = false;
        Point frame_location;
        JFrame menu_cover;
        JFrame move_input;
        JFrame card_frame;
        JButton enter_btn;
        JButton done_btn;
        Game g;
        JPanel Player_UI;
        JPanel Dice_UI;
        backPanel Info_UI;
        JPanel Card_UI;
        JPanel Map_UI;
        JPanel Status_UI;
        JLabel[] labels_Turn;
        JLabel[] labels_Dice;
        JLabel[] labels_Num_forDice;
        JLabel[] labels_Num_forBridge;
        JLabel[] labels_Player;
        JLabel[] labels_Status;
        JLabel[] labels_Player_Pawn;
        JLabel[] labels_Card;
        String move_str;
        JTextField input;
        int tileSize;

        GameFrame(int player_num) {
            setSize(1000,800);
            g = new Game(currentMap, player_num);
            frame_location = getFrameLocationX();
            enter_btn = new JButton();
            tileSize =  Math.floorDiv(550, Math.max(currentMap.Mapsize_row, currentMap.Mapsize_col));
            backPanel background_Game = new backPanel();
            background_Game.setLayout(null);
            background_Game.setBackground(new Color(255, 245, 228));
            background_Game.setBounds(0,0,800,800);
            Map_UI = new JPanel();
            Map_UI.setLayout(null);
            Map_UI.setBackground(new Color(0,0,0,0));
            Map_UI.setBounds(50,50,600,600);
            pawn_init();
            viewPawn();
            background_Game.add(Map_UI);
            background_Game.add(currentMapPanel(100, 100, 550, 550));
            Player_UI = new JPanel();
            Player_UI.setLayout(null);
            Player_UI.setBackground(new Color(255, 245, 228));
            Player_UI.setBounds(160, 0, 480, 100);
            Dice_UI = new JPanel();
            Dice_UI.setLayout(null);
            Dice_UI.setBackground(new Color(255, 245, 228));
            Dice_UI.setBounds(0, 660, 100, 100);
            Dice_UI.setVisible(false);
            Info_UI = new backPanel();
            Info_UI.i = new ImageIcon("./image/Bridge Card Information.png");
            Info_UI.im = Info_UI.i.getImage();
            Info_UI.setLayout(null);
            Info_UI.setBounds(200, 660, 360, 100);
            Info_UI.setVisible(false);
            Status_UI = new JPanel();
            Status_UI.setLayout(null);
            Status_UI.setBackground(new Color(255,235,228,244));
            Status_UI.setBounds(780,0,200,800);
            labels_init();
            card_frame_init();
            viewStatus();
            Player_UI.add(labels_Turn[0]);
            JButton menu_btn = new JButton();
            set_btn_image(menu_btn, "./image/menu_icon2.png", 80, 80);
            menu_btn.setBounds(10, 10, 80, 80);
            JButton skip_btn = new JButton();
            set_btn_image(skip_btn, "./image/Skip Button.png", 100, 100);
            skip_btn.setBounds(680, 660, 100, 100);
            JButton dice_btn = new JButton();
            set_btn_image(dice_btn, "./image/Dice Button.png", 100, 100);
            dice_btn.setBounds(0, 660, 100, 100);
            JButton move_btn = new JButton();
            set_btn_image(move_btn, "./image/Move Button.png", 100, 100);
            move_btn.setBounds(100, 660, 100, 100);
            move_btn.setVisible(false);
            done_btn = new JButton();
            set_btn_image(done_btn, "./image/Done Button.png", 100, 100);
            done_btn.setBounds(680, 660, 100, 100);
            done_btn.setVisible(false);
            JButton card_btn = new JButton();
            set_btn_image(card_btn, "./image/Card Button.png", 100, 100);
            card_btn.setBounds(580,660,100,100);
            background_Game.add(Player_UI);
            background_Game.add(Dice_UI);
            background_Game.add(Info_UI);
            background_Game.add(Status_UI);
            background_Game.add(menu_btn);
            background_Game.add(skip_btn);
            background_Game.add(dice_btn);
            background_Game.add(move_btn);
            background_Game.add(done_btn);
            background_Game.add(card_btn);
            this.add(background_Game);
            this.setVisible(true);
            this.setResizable(false);
            menu_btn.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if(!card_frame.isVisible())
                    {
                    menu_cover = new JFrame();
                    menu_cover.setLayout(null);
                    menu_cover.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    Fix_location();
                    JButton resume_btn = new JButton();
                    set_btn_image(resume_btn, "./image/Resume Button.png", 480, 50);
                    resume_btn.setBounds(150, 160, 480, 50);
                    JButton return_btn = new JButton();
                    set_btn_image(return_btn, "./image/Return Button.png", 480, 50);
                    return_btn.setBounds(150, 360, 480, 50);
                    JButton exit_btn = new JButton();
                    set_btn_image(exit_btn, "./image/Exit Game Button.png", 480, 50);
                    exit_btn.setBounds(150, 560, 480, 50);
                    menu_cover.add(resume_btn);
                    menu_cover.add(return_btn);
                    menu_cover.add(exit_btn);
                    frame_location = getFrameLocationX();
                    menu_cover.setBounds(frame_location.x + 10, frame_location.y + 30, 780, 760);
                    menu_cover.setUndecorated(true);
                    menu_cover.setBackground(new Color(255, 245, 128, 122));
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
            }
            });

            skip_btn.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(!card_frame.isVisible())
                    {
                    Dice_UI.setVisible(false);
                    dice_btn.setVisible(true);
                    move_btn.setVisible(false);
                    g.skipTurn();
                    Player_UI.removeAll();
                    Player_UI.add(labels_Turn[g.turn_player]);
                    Player_UI.revalidate();
                    Player_UI.repaint();
                    }
                }
            });

            dice_btn.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(!card_frame.isVisible())
                    {
                    skip_btn.setVisible(false);
                    g.rollDice();
                    Dice_UI.removeAll();
                    Dice_UI.add(labels_Dice[g.dice-1]);
                    Dice_UI.revalidate();
                    Dice_UI.repaint();
                    Dice_UI.setVisible(true);
                    dice_btn.setVisible(false);
                    viewInfo();
                    if (g.canNotMove()) {
                        done_btn.setVisible(true);
                    } else {
                        move_btn.setVisible(true);
                    }
                }
                }
            });

            move_btn.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(!card_frame.isVisible())
                    {
                    inputFrame_init();
                    move_btn.setVisible(false);
                    }
                }
            });

            done_btn.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(!card_frame.isVisible())
                    {
                    if(g.gameOver)
                    {
                        printWinner();
                        gameOver();
                    }
                    else
                    {
                    Dice_UI.setVisible(false);
                    dice_btn.setVisible(true);
                    move_btn.setVisible(false);
                    done_btn.setVisible(false);
                    skip_btn.setVisible(true);
                    Info_UI.setVisible(false);
                    g.nextTurn();
                    Player_UI.removeAll();
                    Player_UI.add(labels_Turn[g.turn_player]);
                    Player_UI.revalidate();
                    Player_UI.repaint();
                    }
                }
            }
            });

            enter_btn.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(!card_frame.isVisible())
                    {
                    doneTexting();
                    }
                }
            });

            card_btn.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(card_frame.isVisible()) card_frame.setVisible(false);
                    else
                    {
                        viewCard(0);
                    }
                }
            });

           
        }

        public void card_frame_init()
        {
            card_frame = new JFrame();
                        card_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        card_frame.setLayout(null);
                        card_frame.setAlwaysOnTop(true);
                        card_frame.setUndecorated(true);
                        card_frame.setBackground(new Color(255,245,228,112));
                        card_frame.setBounds(frame_location.x+100,frame_location.y+100,600,600);
                        Card_UI = new JPanel();
                        Card_UI.setLayout(null);
                        Card_UI.setBackground(new Color(0,0,0,0));
                        Card_UI.setBounds(0,0,600,600);
                        card_frame.add(Card_UI);
                        card_frame.setVisible(false);
        }

        public void labels_init() {
            JLabel Turn_1 = new JLabel(new ImageIcon("./image/1 Turn.png"));
            JLabel Turn_2 = new JLabel(new ImageIcon("./image/2 Turn.png"));
            JLabel Turn_3 = new JLabel(new ImageIcon("./image/3 Turn.png"));
            JLabel Turn_4 = new JLabel(new ImageIcon("./image/4 Turn.png"));
            labels_Turn = new JLabel[] { Turn_1, Turn_2, Turn_3, Turn_4 };
            for (int i = 0; i < 4; i++) {
                labels_Turn[i].setBounds(0, 0, 480, 100);
            }

            JLabel Dice_1 = new JLabel(imageSetSize(new ImageIcon("./image/Dice 1.png"), 100, 100));
            JLabel Dice_2 = new JLabel(imageSetSize(new ImageIcon("./image/Dice 2.png"), 100, 100));
            JLabel Dice_3 = new JLabel(imageSetSize(new ImageIcon("./image/Dice 3.png"), 100, 100));
            JLabel Dice_4 = new JLabel(imageSetSize(new ImageIcon("./image/Dice 4.png"), 100, 100));
            JLabel Dice_5 = new JLabel(imageSetSize(new ImageIcon("./image/Dice 5.png"), 100, 100));
            JLabel Dice_6 = new JLabel(imageSetSize(new ImageIcon("./image/Dice 6.png"), 100, 100));
            labels_Dice = new JLabel[] { Dice_1, Dice_2, Dice_3, Dice_4, Dice_5, Dice_6 };
            for (int i = 0; i < 6; i++) {
                labels_Dice[i].setBounds(0, 0, 100, 100);
            }

            JLabel player_1 = new JLabel(imageSetSize(new ImageIcon("./image/Player 1.png"), 400,400));
            JLabel player_2 = new JLabel(imageSetSize(new ImageIcon("./image/Player 2.png"), 400,400));
            JLabel player_3 = new JLabel(imageSetSize(new ImageIcon("./image/Player 3.png"), 400,400));
            JLabel player_4 = new JLabel(imageSetSize(new ImageIcon("./image/Player 4.png"), 400,400));
            labels_Player = new JLabel[] {player_1,player_2,player_3,player_4};
            for(int i=0;i<g.player_Num;i++)
            {
                labels_Player[i].setBounds(200,200,400,400);
            }

            JLabel status_1 = new JLabel(imageSetSize(new ImageIcon("./image/Player 1.png"), 100,100));
            JLabel status_2 = new JLabel(imageSetSize(new ImageIcon("./image/Player 2.png"), 100,100));
            JLabel status_3 = new JLabel(imageSetSize(new ImageIcon("./image/Player 3.png"), 100,100));
            JLabel status_4 = new JLabel(imageSetSize(new ImageIcon("./image/Player 4.png"), 100,100));
            labels_Status = new JLabel[] {status_1,status_2,status_3,status_4};
           

            JLabel Num_0 = new JLabel(imageSetSize(new ImageIcon("./image/Num 0.png"), 30, 40));
            JLabel Num_1 = new JLabel(imageSetSize(new ImageIcon("./image/Num 1.png"), 30, 40));
            JLabel Num_2 = new JLabel(imageSetSize(new ImageIcon("./image/Num 2.png"), 30, 40));
            JLabel Num_3 = new JLabel(imageSetSize(new ImageIcon("./image/Num 3.png"), 30, 40));
            JLabel Num_4 = new JLabel(imageSetSize(new ImageIcon("./image/Num 4.png"), 30, 40));
            JLabel Num_5 = new JLabel(imageSetSize(new ImageIcon("./image/Num 5.png"), 30, 40));
            JLabel Num_6 = new JLabel(imageSetSize(new ImageIcon("./image/Num 6.png"), 30, 40));
            labels_Num_forDice = new JLabel[] {Num_0,Num_1,Num_2,Num_3,Num_4,Num_5,Num_6,};
            for(int i =0;i<7;i++)
            {
                labels_Num_forDice[i].setBounds(0,0,30,40);
            }

            JLabel Num_00 = new JLabel(imageSetSize(new ImageIcon("./image/Num 0.png"), 30, 40));
            JLabel Num_11 = new JLabel(imageSetSize(new ImageIcon("./image/Num 1.png"), 30, 40));
            JLabel Num_22 = new JLabel(imageSetSize(new ImageIcon("./image/Num 2.png"), 30, 40));
            JLabel Num_33 = new JLabel(imageSetSize(new ImageIcon("./image/Num 3.png"), 30, 40));
            JLabel Num_44 = new JLabel(imageSetSize(new ImageIcon("./image/Num 4.png"), 30, 40));
            JLabel Num_55 = new JLabel(imageSetSize(new ImageIcon("./image/Num 5.png"), 30, 40));
            JLabel Num_66 = new JLabel(imageSetSize(new ImageIcon("./image/Num 6.png"), 30, 40));
            labels_Num_forBridge = new JLabel[] {Num_00,Num_11,Num_22,Num_33,Num_44,Num_55,Num_66,};
            for(int i =0;i<7;i++)
            {
                labels_Num_forBridge[i].setBounds(0,0,30,40);
            }

            
        }

        public void pawn_init()
        {
            JLabel Player_Pawn_1 = new JLabel(imageSetSize(new ImageIcon("./image/Player 1_pawn.png"),tileSize,tileSize));
            JLabel Player_Pawn_2 = new JLabel(imageSetSize(new ImageIcon("./image/Player 2_pawn.png"),tileSize,tileSize));
            JLabel Player_Pawn_3 = new JLabel(imageSetSize(new ImageIcon("./image/Player 3_pawn.png"),tileSize,tileSize));
            JLabel Player_Pawn_4 = new JLabel(imageSetSize(new ImageIcon("./image/Player 4_pawn.png"),tileSize,tileSize));
            labels_Player_Pawn = new JLabel[] {Player_Pawn_1,Player_Pawn_2,Player_Pawn_3,Player_Pawn_4};
            for(int i =0;i<g.player_Num;i++)
            {
                labels_Player_Pawn[i].setBounds(0,0,tileSize,tileSize);
                labels_Player_Pawn[i].setBackground(new Color(0,0,0,0));
            }
        }
        public void inputFrame_init() {
            move_input = new JFrame();
            move_input.setLayout(null);
            move_input.setAlwaysOnTop(true);
            frame_location = getFrameLocationX();
            int temp_x = frame_location.x + 240;
            int temp_y = frame_location.y + 790;
            if (temp_y > screen_size.height)
                temp_y = screen_size.height - 70;
            move_input.setBounds(temp_x, temp_y, 330, 70);
            move_input.setUndecorated(true);
            move_input.setBackground(new Color(255, 245, 228, 227));
            input = new JTextField();
            input.setBounds(30, 20, 200, 30);
            input.setText("");
            input.setCaretPosition(0);
            input.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (input.getText().length() >= g.dice - g.players[g.turn_player].num_bridges()) {
                        e.consume(); // 주사위 눈 이상의 입력은 받지 않음.
                        if (e.getKeyChar() == '\n') {
                            doneTexting();
                        }

                    }
                    if (!isValidMove(e.getKeyChar())) {
                        e.consume(); // Valid한 입력이 아닌 경우 받지 않음.
                        JOptionPane.showMessageDialog(null,
                                "Please Enter the character {U,D,L,R,u,d,l,r} to move your pawn.\nOR, Please check your keyboard input langauge.",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            });
            move_input.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowOpened(WindowEvent e) {
                    input.requestFocus();
                }
            });
            set_btn_image(enter_btn, "./image/Enter Button.png", 70, 29);
            enter_btn.setBounds(250, 41, 70, 29);
            move_input.add(input);
            move_input.add(enter_btn);
            move_input.setVisible(true);
        }

        public void viewStatus()
        {
            Status_UI.removeAll();
            for(int i=0;i<g.player_Num;i++)
            {
                JPanel p = new JPanel();
                p.setLayout(null);
                p.setBackground(new Color(0,0,0,0));
                p.setBounds(0,190*i,200,190);
                labels_Status[i].setBounds(50,50,100,100);
                JLabel player_label = new JLabel();
                player_label.setText("Player "+(i+1));
                player_label.setBounds(75,10,150,30);
                JLabel score_label = new JLabel();
                score_label.setText("score : "+g.players[i].Score);
                score_label.setBounds(75,160,150,15);
                JLabel cards_label = new JLabel();
                cards_label.setText("cards : "+g.players[i].cards.size());
                cards_label.setBounds(75,175,150,15);
                p.add(player_label);
                p.add(labels_Status[i]);
                p.add(score_label);
                p.add(cards_label);
                Status_UI.add(p);
            }
        }

        public void doneTexting() {
            move_str = input.getText();
            if (!g.move(move_str)) {
                JOptionPane.showMessageDialog(null,
                        "Entered String is not valid move String.\nPlease Enter valid Move String.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                input.setText("");
                input.setCaretPosition(0);
                move_input.setVisible(false);
                inputFrame_init();
            } else {
                Info_UI.setVisible(false);
                move_input.setVisible(false);
                done_btn.setVisible(true);
                PawnReset();
                g.calculate_Score(g.turn_player);
                viewStatus();
            }
        }

        public void viewInfo() {
            int bridge_cnt = g.players[g.turn_player].num_bridges();
            int canMove = g.dice-bridge_cnt;
            if(canMove<0)canMove=0;
            Info_UI.removeAll();
            labels_Num_forBridge[bridge_cnt].setBounds(140,10,30,40);
            Info_UI.add(labels_Num_forBridge[bridge_cnt]);
            labels_Num_forDice[canMove].setBounds(210,55,30,40);
            Info_UI.add(labels_Num_forDice[canMove]);
            Info_UI.revalidate();
            Info_UI.repaint();
            Info_UI.setVisible(true);
        }

        public void viewPawn()
        {
            for(int i = 0; i<g.player_Num;i++)
            {   
                Map_UI.remove(labels_Player_Pawn[i]);
                labels_Player_Pawn[i].setBounds(g.players[i].current_location[1]*tileSize+50,g.players[i].current_location[0]*tileSize+50,tileSize,tileSize);
                Map_UI.add(labels_Player_Pawn[i]);
            }
            Map_UI.revalidate();
            Map_UI.repaint();
        }

        public void viewCard(int page)
        {            
            Card_UI.removeAll();
            JScrollBar s = new JScrollBar(1,page,1,0,2);
            if(g.players[g.turn_player].cards.size()>16)
            {
            s.setBounds(590,0,10,600);
            Card_UI.add(s);
            }
            JLabel card_label=new JLabel();
            if(page==0)
            {
            for(int i=0;i<g.players[g.turn_player].cards.size();i++)
            {
                switch(g.players[g.turn_player].cards.get(i))
                {
                    case '=': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Bridge Card.png"), 120, 150));break;
                    case 'P': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Driver Card.png"), 120, 150));break;
                    case 'H': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Hammer Card.png"), 120, 150));break;
                    case 'S': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Saw Card.png"), 120, 150));break;
                }
                card_label.setBounds(24+(144*(i%4)),150*(i/4),120,150);
                Card_UI.add(card_label);
            }
            }
            else
            {
                for(int i=16;i<g.players[g.turn_player].cards.size();i++)
                {
                    switch(g.players[g.turn_player].cards.get(i))
                    {
                        case '=': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Bridge Card.png"), 120, 150));break;
                        case 'P': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Driver Card.png"), 120, 150));break;
                        case 'H': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Hammer Card.png"), 120, 150));break;
                        case 'S': card_label= new JLabel(imageSetSize(new ImageIcon("./image/Saw Card.png"), 120, 150));break;
                    }
                    card_label.setBounds(24+(144*((i-16)%4)),150*((i-16)/4),120,150);
                    Card_UI.add(card_label);
                }
            }
            Card_UI.revalidate();
            Card_UI.repaint();
            Card_UI.setVisible(true);
            card_frame.setVisible(true);
            s.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    Card_UI.setVisible(false);
                   viewCard(s.getValue());
                }
            
            });
        }

        public void gameOver()
        {
            setVisible(false);
        }

        public void printWinner()
        {
            JFrame WinnerFrame = new JFrame();
            WinnerFrame.setLayout(null);
            WinnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ImageIcon icon = new ImageIcon("./image/icon2.png");
            Image ic = icon.getImage();
            WinnerFrame.setIconImage(ic);
            JLabel player_label = new JLabel();
            player_label.setText("Player "+(g.winner_num+1));
            player_label.setBounds(310,140,200,60);
            Font f = new Font("Cascadia Code",Font.BOLD,50);
            player_label.setFont(f);
            JButton menu_btn = new JButton();
            set_btn_image(menu_btn, "./image/Menu Icon.png", 80, 80);
            menu_btn.setBounds(580, 625, 80, 80);
            JButton exit_btn = new JButton();
            set_btn_image(exit_btn, "./image/Exit Icon.png", 80, 80);
            exit_btn.setBounds(660, 625, 80, 80);
            backPanel background_Winner = new backPanel();
            background_Winner.i = new ImageIcon("./image/Winner Frame.png");
            background_Winner.im = background_Winner.i.getImage();
            background_Winner.setLayout(null);
            background_Winner.add(player_label);
            background_Winner.add(labels_Player[g.winner_num]);
            background_Winner.add(menu_btn);
            background_Winner.add(exit_btn);
            background_Winner.setBounds(0,0,800,800);
            WinnerFrame.add(background_Winner);
            WinnerFrame.setBounds(300,100,800,800);
            WinnerFrame.setVisible(true);

            menu_btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    InterfaceOn();
                    WinnerFrame.setVisible(false);
                }
            });

            exit_btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.exit(0);
                }
            });
        }

        @Override
        public JPanel currentMapPanel(int x, int y, int width, int height)
        {
            JPanel p = new JPanel();
            p.setBackground(new Color(255, 245, 228));
            p.setLayout(null);
            p.setBounds(x, y, width, height);
            p.setVisible(true);
            int tileSize = Math.floorDiv(width, Math.max(currentMap.Mapsize_row, currentMap.Mapsize_col));
            for (int i = 0; i < currentMap.Mapsize_row; i++) {
                for (int j = 0; j < currentMap.Mapsize_col; j++) {
                    if (currentMap.isValidChar(currentMap.current_map[i][j])) {
                        JButton cell = new JButton();
                        if (1 - currentMap.Map_row_Min == i && 0 == j) {
                            set_btn_image(cell, "./image/Start ground.png", tileSize - 5, tileSize - 5);
                        } else {
                            switch (currentMap.current_map[i][j]) {
                                case 'C':
                                case 'B':
                                case 'b':
                                    set_btn_image(cell, "./image/ground.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'S':
                                    set_btn_image(cell, "./image/Saw.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'P':
                                    set_btn_image(cell, "./image/Driver.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'H':
                                    set_btn_image(cell, "./image/Hammer.png", tileSize - 5, tileSize - 5);
                                    break;
                                case 'E':
                                    set_btn_image(cell, "./image/End ground.png", tileSize - 5, tileSize - 5);
                                    break;
                                case '=':
                                    set_btn_image(cell, "./image/bridge.png", tileSize - 5, tileSize - 5);
                            }
                        }
                        cell.setBounds(j * tileSize, i * tileSize, tileSize, tileSize);
                        cell.addMouseListener(new MouseAdapter(){
                            @Override
                            public void mouseExited(MouseEvent e) {
                                PawnReset();
                            }
                        });
                        p.add(cell);
                    }
    
                }
            }
            return p;

           
    
        }

        public void PawnReset()
        {
            
            viewPawn();
            this.revalidate();
            this.repaint();
        }

        public Point getFrameLocationX() {
            return this.getLocation();
        }

        public void Fix_location() {
            this.setEnabled(false);
        }

        public void Free_location() {
            this.setEnabled(true);
        }

        public boolean isValidMove(char c) {
            switch (c) {
                case 'U':
                case 'D':
                case 'L':
                case 'R':
                case 'u':
                case 'd':
                case 'l':
                case 'r':
                case '\b':
                case '\u007f':
                case '\n':
                    return true;
            }
            return false;
        }

    }

    public class LoadFrame extends MyFrame {

        JPanel mapLayout = new JPanel();

        LoadFrame() {
            backPanel background_Load = new backPanel();
            background_Load.i = new ImageIcon("./image/Load Map Frame.png");
            background_Load.im = background_Load.i.getImage();
            background_Load.setLayout(null);
            JButton select_File_btn = new JButton();
            set_btn_image(select_File_btn, "./image/Select File Icon.png", 80, 80);
            select_File_btn.setBounds(660, 490, 80, 80);
            JButton menu_btn = new JButton();
            set_btn_image(menu_btn, "./image/Menu Icon.png", 80, 80);
            menu_btn.setBounds(660, 625, 80, 80);
            mapLayout.setBounds(25, 165, 580, 580);
            mapLayout.setLayout(null);
            mapLayout.setBackground(new Color(255, 235, 228));
            mapLayout.add(currentMapPanel(0, 0, 580, 580));

            select_File_btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    boolean isvalidfile = false;
                    while (!isvalidfile) {
                        currentMap.selectMap();
                        currentMap.printCurrentMapFile();
                        try {
                            isvalidfile = currentMap.mapInit();
                        } catch (Exception ignore) {
                        }
                        if (!isvalidfile) {
                            map_load_error();
                            currentMap.MapFile = currentMap.recent_validMapFile;
                            try {
                                currentMap.mapInit();
                            } catch (Exception ignore) {
                            }
                        }
                    }
                    mapLayout.removeAll();
                    mapLayout.add(currentMapPanel(0, 0, 580, 580));
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

        public void map_load_error() {
            JOptionPane.showMessageDialog(null,
                    "Selected Map file is not valid file.\nPlease Select another valid map file.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    
    

    ImageIcon imageSetSize(ImageIcon icon, int i, int j) {
        Image ximg = icon.getImage();
        Image yimg = ximg.getScaledInstance(i, j, Image.SCALE_SMOOTH);
        ImageIcon xyimg = new ImageIcon(yimg);
        return xyimg;
    }

    void set_btn_image(JButton target, String Filename, int width, int height) {
        ImageIcon i = new ImageIcon(Filename);
        i = imageSetSize(i, width, height);
        target.setIcon(i);
        target.setBorderPainted(false);
        target.setFocusPainted(false);
        target.setContentAreaFilled(false);
    }

}
