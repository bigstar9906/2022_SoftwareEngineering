package src.main;

public class Game {
    int player_Num;
    int dice;
    Map game_Map;
    Player players[];
    int turn_num;
    Boolean isTurnEnd_player[];
    Game(Map m,int num_of_players){
        this.game_Map = m;
        this.player_Num = num_of_players;
        players = new Player[this.player_Num];
        turn_num = 0;
        isTurnEnd_player = new Boolean[this.player_Num];
        for(int i = 0; i<player_Num;i++)
        {
            players[i] = new Player(0, 1-game_Map.Map_row_Min);
            isTurnEnd_player[i] = false;
        }
        System.out.println(game_Map.isSizeDetermined);
    }

    public int nextTurn(){
        turn_num+=1;
        return turn_num%player_Num;        
    }

}


