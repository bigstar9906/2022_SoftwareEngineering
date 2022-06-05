package src.main;

public class Player {
    String player_name;
    int current_location[] = new int[2];
    Player(int start_xPosition,int start_yPosition)
    {
        this.current_location[1] = start_xPosition;
        this.current_location[0] = start_yPosition;
    }

    public void setName(String s)
    {
        this.player_name = s;
    }


}
