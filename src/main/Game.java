package src.main;

public class Game {
    int player_Num;
    int dice;
    Map game_Map;
    Player players[];
    
    Game(Map m,int num_of_players){
        this.game_Map = m;
        this.player_Num = num_of_players;
        players = new Player[this.player_Num];
        for(int i = 0; i<player_Num;i++)
        {
            players[i] = new Player(0, 1-game_Map.Map_row_Min);
        }
        System.out.println(game_Map.isSizeDetermined);
    }


}
