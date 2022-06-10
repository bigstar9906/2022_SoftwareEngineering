package src.v_console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CLI {
    Map currentMap;
    Game g;
    CLI()
    {
        currentMap = new Map();
        menu_interface();

    }


   

    public void menu_interface()
    {
        System.out.println("*****************************************");
        System.out.println("*                                       *");
        System.out.println("*                 MENU                  *");
        System.out.println("*                                       *");
        System.out.println("*                                       *");
        System.out.println("*          ENTER L KEY to LOAD          *");
        System.out.println("*       ENTER G KEY to START GAME       *");
        System.out.println("*          ENTER E KEY to EXIT          *");
        System.out.println("*                                       *");
        System.out.println("*                                       *");
        System.out.println("*****************************************");

        System.out.print("\nEnter KEY : ");
        
        int input_key = keyInput_i();
        while(!isValidKey_menu(input_key))
        {
            System.out.println("Please Enter valid key");
            input_key=keyInput_i();
        }
        switch(input_key)
        {
            case 76: case 108:
            load_Map();
            break;
            case 71: case 103:
            Game_interface();
            break;
            case 69: case 101:
            System.exit(0);
        }
       
    }

    public void load_Map()
    {
        boolean isvalidfile = false;
        while (!isvalidfile) {
            currentMap.selectMap();
            currentMap.printCurrentMapFile();
            try {
                isvalidfile = currentMap.mapInit();
            } catch (Exception ignore) {
            }
            if (!isvalidfile) {
                currentMap.MapFile = currentMap.recent_validMapFile;
                try {
                    System.out.println("Load Recent Valid Map File");
                    System.out.println(currentMap.MapFile.getName());
                    currentMap.mapInit();
                } catch (Exception ignore) {
                }
            }
        }
        System.out.println("Current Map :");
        currentMap.printMap();
        System.out.println();
        System.out.println("Press M to go back to MENU");
        int goBack = keyInput_i();
        while(goBack!=77&&goBack!=109)
        {
            System.out.println("Press M to go back to MENU");
            goBack = keyInput_i();
        }
        menu_interface();
    }

    public void Game_interface()
    {
        System.out.println("*****************************************");
        System.out.println("*                                       *");
        System.out.println("*              START  GAME              *");
        System.out.println("*                                       *");
        System.out.println("*                                       *");
        System.out.println("*     ENTER 2 KEY to 2 Player Mode      *");
        System.out.println("*     ENTER 3 KEY to 3 Player Mode      *");
        System.out.println("*     ENTER 4 KEY to 4 Player Mode      *");
        System.out.println("*                                       *");
        System.out.println("*                                       *");
        System.out.println("*****************************************");
        System.out.println();
        System.out.print("Enter Key { 2 , 3 , 4 } :");
        int player_Num = keyInput_i();
        while(player_Num<50||player_Num>52)
        {
            System.out.print("Enter Key { 2 , 3 , 4 } :");
            player_Num = keyInput_i();
        }
        g = new Game(currentMap,player_Num-48);
        while(!g.gameOver)
        {
        print_GameMap();
        print_PlayerStatus();
        System.out.println("******************************************************************************");
        System.out.println("*                                                                            *");
        System.out.println("*      ENTER R to Roll Dice        ENTER S to Skip Turn(Bridge Card-1)       *");
        System.out.println("*                                                                            *");
        System.out.println("*      ENTER C to view Cards       ENTER P to Pause Game                     *");
        System.out.println("*                                                                            *");
        System.out.println("******************************************************************************");
        System.out.println();
        System.out.print("Enter Key { R , S , C , P } :");
        int game_menu = keyInput_i();
        while(!isValidKey_game(game_menu))
        {
            System.out.print("Enter Key { R , S , C , P } :");
            game_menu = keyInput_i();
        }
        switch(game_menu)
        {
            case 82: case 114:
            rollDice_Move();
            g.nextTurn();
            break;
            case 83: case 115:
            g.skipTurn();
            break;
            case 67: case 99:
            pirnt_Cards();
            System.out.println("PRESS ENTER TO CONTINUE");
            keyInput_i();
            break;
            case 80: case 112:
            print_Pause();
            break;
        }

        }
        GAMEOVER();
    }

    public void print_GameMap()
    {
        int row = g.game_Map.Mapsize_row;
        int col = g.game_Map.Mapsize_col;
        int[] player_1_location = g.players[0].current_location;
        int[] player_2_location = g.players[1].current_location;
        int[] player_3_location = new int[2];
        int[] player_4_location = new int[2];
        System.out.println(g.player_Num);
        if(g.player_Num==3)
        {
            player_3_location = g.players[2].current_location;
        }
        if(g.player_Num==4)
        {
            player_3_location = g.players[2].current_location;
            player_4_location = g.players[3].current_location;
        }
        for(int i=0;i<row;i++)
        {
        for(int j=0;j<col;j++)System.out.print("*******");
        System.out.println();
        for(int j=0;j<col;j++)
        {
            System.out.print("* ");
            if(player_1_location[0]==i&&player_1_location[1]==j)System.out.print("1");
            else System.out.print(" ");
            System.out.print(" ");
            if(player_2_location[0]==i&&player_2_location[1]==j)System.out.print("2");
            else System.out.print(" ");
            System.out.print(" *");            
        }
        System.out.println();
        for(int j=0;j<col;j++)
        {
            System.out.print("*  ");
            if(g.game_Map.isValidChar( g.game_Map.current_map[i][j])) System.out.print(g.game_Map.current_map[i][j]);
            else System.out.print(" ");
            System.out.print("  *");
        }
        System.out.println();
        for(int j=0;j<col;j++)
        {
            System.out.print("* ");
            if(g.player_Num>2)
            {
            if(player_3_location[0]==i&&player_3_location[1]==j)System.out.print("3");
            else System.out.print(" ");
            }
            else System.out.print(" ");
            System.out.print(" ");
            if(g.player_Num==4)
            {
            if(player_4_location[0]==i&&player_4_location[1]==j)System.out.print("4");
            else System.out.print(" ");
            }
            else System.out.print(" ");
            System.out.print(" *");            
        }
        System.out.println();
        for(int j=0;j<col;j++)System.out.print("*******");
        System.out.println();
        }
    }

    public void print_PlayerStatus()
    {
        for(int i=0;i<g.player_Num;i++)System.out.print("  Player "+(i+1)+"  ");
        System.out.println();
        for(int i=0;i<g.player_Num;i++)System.out.printf("  Score : %2d",g.players[i].Score);
        System.out.println();
        for(int i=0;i<g.player_Num;i++)System.out.printf("  Cards : %2d",g.players[i].cards.size());
        System.out.println();
        System.out.println();
        System.out.println("Player "+(g.turn_player+1)+"'s turn.");
    }

    public void pirnt_Cards()
    {
        System.out.println("Player "+(g.turn_player+1)+"'s card :");
        for(int i=0;i<g.players[g.turn_player].cards.size();i++)
        {
            switch(g.players[g.turn_player].cards.get(i))
            {
                case '=':
                System.out.println("* BRIDGE CARD *");
                System.out.println("*      =      *");
                System.out.println();
                break;
                case 'S':
                System.out.println("*  SAW  CARD  *");
                System.out.println("*      S      *");
                System.out.println();
                break;
                case 'H':
                System.out.println("* HAMMER CARD *");
                System.out.println("*      H      *");
                System.out.println();
                break;
                case 'P':
                System.out.println("* DRIVER CARD *");
                System.out.println("*      D      *");
                System.out.println();
                break;
            }
        }
    }

    public void print_Pause()
    {
        System.out.println("**************************************");
        System.out.println("*                                    *");
        System.out.println("*   ENTER R to RESUME                *");
        System.out.println("*   ENTER M to Go MAIN MENU(No Save) *");
        System.out.println("*   ENTER E to EXIT Program          *");
        System.out.println("*                                    *");
        System.out.println("**************************************");
        System.out.println();
        System.out.print("Enter Key { R , M , E } :");
        int pause_menu = keyInput_i();
        while(pause_menu!=69&&pause_menu!=101&&pause_menu!=82&&pause_menu!=114&&pause_menu!=77&&pause_menu!=109)
        {
            System.out.print("Enter Key { R , M , E  } :");
            pause_menu = keyInput_i();
        }
        switch(pause_menu)
        {
            case 82: case 114:
            break;
            case 77: case 109:
            menu_interface();
            case 69: case 101:
            System.exit(0);
        }
    }

    public void rollDice_Move()
    {
        g.rollDice();
        boolean isValidString = false;
        System.out.println("Dice : "+g.dice);
        if(g.canNotMove())
        {
            System.out.println("You have "+g.players[g.turn_player].num_bridges()+" bridge cards.\n You can't Move");
        }
        else{
            System.out.println("Please Enter your move. You have "+(g.dice-g.players[g.turn_player].num_bridges())+" chance for move.");
            System.out.print("Enter Key { U , D , L , R , u , d , l , r } :");
            String move = keyInput_s();
            isValidString = isValidStr(move);
            while(!(move.length()==g.dice-g.players[g.turn_player].num_bridges())||!isValidString)
            {
                System.out.println("Please Enter your move. You have "+(g.dice-g.players[g.turn_player].num_bridges())+" chance for move.");
                System.out.print("Enter Key { U , D , L , R , u , d , l , r } :");
                move = keyInput_s();
                isValidString = isValidStr(move);
            }
            while(!g.move(move))
            {
                move = keyInput_s();
            }
        }
    }

    public void GAMEOVER()
    {
        System.out.println("Winner is Player "+(g.winner_num+1)+". Score : "+g.players[g.winner_num].Score);
        System.out.println("*******************************");
        System.out.println("*                             *");
        System.out.println("*   ENTER M to Go MAIN MENU   *");
        System.out.println("*   ENTER E to EXIT Program   *");
        System.out.println("*                             *");
        System.out.println("*******************************");
        System.out.println();
        System.out.print("Enter Key { M , E } :");
        int gameover_menu = keyInput_i();
        while(gameover_menu!=69&&gameover_menu!=101&&gameover_menu!=77&&gameover_menu!=109)
        {
            System.out.print("Enter Key { M , E  } :");
            gameover_menu = keyInput_i();
        }
        switch(gameover_menu)
        {
            case 77: case 109:
            menu_interface();
            case 69: case 101:
            System.exit(0);
        }
    }

    public boolean isValidKey_menu(int x)
    {
        switch(x)
        {
            case 69: case 101: case 76: case 71: case 108: case 103:
            return true;
        }
        return false;
    }

    public boolean isValidKey_game(int x)
    {
        switch(x)
        {
            case 67: case 99: case 80: case 112: case 82: case 114: case 83: case 115:
            return true;
        }
        return false;
    }

    public boolean isValidStr(String s)
    {
        int b=0;
        for(int i=0;i<s.length();i++)
        {
            switch(s.charAt(i))
            {
                case 'u': case 'U': case 'd': case 'D': case 'l': case 'L': case 'r': case 'R':
                b++;
                break;
            }
        }
        if(b==s.length())return true;
        else
        {
             return false;
        }
    }

    public int keyInput_i()
    {
        int keycode=0;
        try {
            keycode = System.in.read();
        } catch (Exception e) {
            keyInput_i();
        }
        try
        {
        System.in.skip(2);
        }catch (Exception ignore){}
        return keycode;
    }
    public String keyInput_s() 
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String str = new String();
        try{
        str = input.readLine();
        }catch (Exception e)
        {
            keyInput_s();
        }
        return str;
    }
}
