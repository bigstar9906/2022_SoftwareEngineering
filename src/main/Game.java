package src.main;

import java.util.Random;

public class Game {
    int player_Num;
    int dice;
    Map game_Map;
    Player players[];
    int turn_num;
    int turn_player;
    int turn;

    Game(Map m, int num_of_players) {
        this.game_Map = m;
        this.player_Num = num_of_players;
        players = new Player[this.player_Num];
        turn = 0;
        turn_player =0;
        turn_num =0;
        for (int i = 0; i < player_Num; i++) {
            players[i] = new Player(0, 1 - game_Map.Map_row_Min);
        }
        System.out.println(game_Map.isSizeDetermined);
    }

    public void nextTurn() {
        turn += 1;
        turn_num = turn/player_Num;
        turn_player = turn%player_Num;
        while(players[turn_player].num_bridges()==6||players[turn_player].isEnd)//다리 카드가 6개 있을 경우 주사위를 굴려도 턴을 진행할 수 없으므로 자동 스킵.
        {
            players[turn_player].throw_bridge();
            turn+=1;
            turn_player = turn%player_Num;
        }
        return;
    }

    public int rollDice() {
        Random r = new Random();
        this.dice = r.nextInt(6)+1;
        return dice;
    }

    public boolean canNotMove()
    {
        if(this.dice<this.players[turn_player].num_bridges()) return true;
        return false;
    }

    public boolean move(String s) {
        int test_y = players[turn_player].current_location[0];
        int test_x = players[turn_player].current_location[1];
        char current_cell = ' ';
        int[] one_step = new int[2];
        for (int i = 0; i < s.length(); i++) {
            one_step = move_1cell(s.charAt(i));
            System.out.println("Before move : "+test_x+" "+test_y);
            test_y += one_step[0];
            test_x += one_step[1];
            if(test_y<0||test_y>=game_Map.Mapsize_row||test_x<0||test_x>=game_Map.Mapsize_col)
            {
                System.out.println("Entered Move is out of bound"+test_x+" "+test_y);
                return false;
            }
            else if(!game_Map.isValidChar(game_Map.current_map[test_y][test_x]))
            {
                System.out.println("Entered String is not Movable.Please Enter valid String again."+test_x+" "+test_y);
                return false;
            }
        }
        for(int i=0;i<s.length();i++)
        {            
            one_step = move_1cell(s.charAt(i));
            players[turn_player].current_location[0] +=one_step[0];
            players[turn_player].current_location[1] +=one_step[1];
            current_cell = game_Map.current_map[players[turn_player].current_location[0]][players[turn_player].current_location[1]];
            if(current_cell=='B'&&players[turn_player].EnterdBridgeFrom=='B') players[turn_player].EnterdBridgeFrom = '='; //다리를 건너지 않고 되돌아온 것이므로 초기화
            if(current_cell=='b'&&players[turn_player].EnterdBridgeFrom=='b') players[turn_player].EnterdBridgeFrom = '=';
            if((current_cell=='B'&&players[turn_player].EnterdBridgeFrom=='b')||(current_cell=='b'&&players[turn_player].EnterdBridgeFrom=='B'))
            {
                players[turn_player].getCard('=');//다리를 건넜을 땐 다리 카드 1장
                players[turn_player].EnterdBridgeFrom = '='; //다리 지나감 판단 변수 초기화
            }
            if(current_cell=='=')
            {
                if(s.charAt(i)=='R') players[turn_player].EnterdBridgeFrom = 'B';
                else if(s.charAt(i)=='L') players[turn_player].EnterdBridgeFrom = 'b';
            }
            if(current_cell=='E')
            {
                switch(endPlayer_CNT())
                {
                    case 0:
                    players[turn_player].Score +=7;
                    break;
                    case 1:
                    players[turn_player].Score +=3;
                    break;
                    case 2:
                    players[turn_player].Score +=1;
                    break;
                }
                players[turn_player].isEnd = true;
                if(endPlayer_CNT()==player_Num-1)
                {
                    //게임 종료.
                }
                return true;
            }
        }
        if(!players[turn_player].current_location.equals(new int[]{1-game_Map.Map_row_Min,0}))
        {
            if(current_cell=='S'||current_cell=='H'||current_cell=='P') players[turn_player].getCard(current_cell);
            
        }
        System.out.println("Player "+(turn_player+1)+"'s current x :"+ players[turn_player].current_location[1]+" current y : "+players[turn_player].current_location[0]);
        System.out.println("Player "+(turn_player+1)+"'s bridge card count is "+players[turn_player].num_bridges());
        return true;
    }

    public int[] move_1cell(char c) {
        int[] result = new int[2];
        switch (c) {
            case 'U': case 'u':
                result = new int[] { -1, 0 };
                break;
            case 'D': case 'd':
                result = new int[] { 1, 0 };
                break;
            case 'L': case 'l':
                result = new int[] { 0, -1 };
                break;
            case 'R': case 'r':
                result = new int[] { 0, 1 };
                break;
        }
        return result;
    }

    public int endPlayer_CNT()
    {
        int cnt=0;
        for(int i =0; i<player_Num;i++)
        {
            if(players[i].isEnd) cnt++;
        }
        return cnt;
    }
}
