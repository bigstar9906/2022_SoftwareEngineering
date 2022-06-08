package src.main;

import java.util.ArrayList;

public class Player {
    String player_name;
    int current_location[];
    ArrayList<Character> cards;
    char EnterdBridgeFrom;
    boolean isEnd;
    int Score;
    int rank;

    Player(int start_xPosition, int start_yPosition) {
        current_location = new int[2];
        cards  = new ArrayList<>();
        EnterdBridgeFrom =  '=';
        isEnd = false;
        Score = 0;
        rank = 5; //들어오지 않았을 경우 동일 점수 획득 시 최하 순위 부여
        this.current_location[1] = start_xPosition;
        this.current_location[0] = start_yPosition;

    }

    public void setName(String s) {
        this.player_name = s;
    }

    public void getCard(char c) {
        this.cards.add(c);
    }

    public void throw_bridge() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) == '=') {
                cards.remove(i);
                return;
            }
            if(i==cards.size()-1)
            {
                System.out.println("You don't have bridge card.");
                return;
            }
        }

    }

    public int num_bridges() {
        int num = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            if (cards.get(i) == '=') {
                num++;
            }
        }
        return num;
    }
}
