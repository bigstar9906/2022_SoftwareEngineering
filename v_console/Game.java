package src.v_console;


import java.util.Random;

public class Game {
    int player_Num;
    int dice;
    Map game_Map;
    Player players[];
    int turn_num;
    int turn_player;
    int turn;
    boolean gameOver;
    int winner_num;

    Game(Map m, int num_of_players) {
        this.game_Map = m;
        this.player_Num = num_of_players;
        players = new Player[this.player_Num];
        turn = 0;
        turn_player =0;
        turn_num =0;
        gameOver = false;
        winner_num=-1;
        for (int i = 0; i < player_Num; i++) {
            players[i] = new Player(0, 1 - game_Map.Map_row_Min);
        }
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

    public void skipTurn()
    {
        players[turn_player].throw_bridge();
        nextTurn();
    }

    public int rollDice() {
        Random r = new Random();
        this.dice = r.nextInt(6)+1;
        return dice;
    }

    public boolean canNotMove()
    {
        if(this.dice<=this.players[turn_player].num_bridges()) return true;
        return false;
    }

    public boolean move(String s) {
        int test_y = players[turn_player].current_location[0];
        int test_x = players[turn_player].current_location[1];
        char current_cell = ' ';
        int[] one_step = new int[2];
        for (int i = 0; i < s.length(); i++) {
            one_step = move_1cell(s.charAt(i));
            test_y += one_step[0];
            test_x += one_step[1];
            if(test_y<0||test_y>=game_Map.Mapsize_row||test_x<0||test_x>=game_Map.Mapsize_col)
            {
                System.out.println("Entered Move is out of bound");
                return false;
            }
            else if(game_Map.current_map[test_y][test_x]=='E')
            {
                break;
            }
            else if(!game_Map.isValidChar(game_Map.current_map[test_y][test_x]))
            {
                System.out.println("Entered String is not Movable.Please Enter valid String again.");
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
                if(s.charAt(i)=='R'||s.charAt(i)=='r') players[turn_player].EnterdBridgeFrom = 'B';
                else if(s.charAt(i)=='L'||s.charAt(i)=='l') players[turn_player].EnterdBridgeFrom = 'b';
            }
            if(current_cell=='E')
            {
                players[turn_player].isEnd = true;
                calculate_Score(turn_player);
                if(endPlayer_CNT()==player_Num-1)
                {
                    GAMEOVER();
                }
                return true;
            }
        }
        if(!players[turn_player].current_location.equals(new int[]{1-game_Map.Map_row_Min,0}))
        {
            if(current_cell=='S'||current_cell=='H'||current_cell=='P') players[turn_player].getCard(current_cell);
            
        }
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

    public void calculate_Score(int index)
    {
        players[index].Score=0;
        if(players[index].isEnd)
        {
        switch(endPlayer_CNT()-1)
        {
            case 0:
            players[index].Score +=7;
            players[index].rank = 1;
            break;
            case 1:
            players[index].Score +=3;
            players[index].rank = 2;
            break;
            case 2:
            players[index].Score +=1;
            players[index].rank = 3;
            break;
            case 3:
            players[index].rank = 4;
        }
        }
        for(int i=0;i<players[index].cards.size();i++)
        {
            switch(players[index].cards.get(i))
            {
                case 'S': players[index].Score +=3;break;
                case 'H': players[index].Score +=2;break;
                case 'P': players[index].Score +=1;break;
            }
        }
    }

    public void GAMEOVER()
    {
        for(int i=0;i<player_Num;i++)
        {
            calculate_Score(i);
        }
        gameOver = true;
        int winner_score=6;//winner의 최소 점수-1로 설정하여 게임 종료 시 winner가 반드시 설정되도록 구현
        for(int i =0;i<player_Num;i++)
        {
            if(winner_score<players[i].Score)
            {
                winner_score=players[i].Score;
                winner_num =i;
            }
            else if(winner_score==players[i].Score&&players[winner_num].rank>players[i].rank)
            {
                winner_score=players[i].Score;
                winner_num =i;
            }
        }
    }
}
