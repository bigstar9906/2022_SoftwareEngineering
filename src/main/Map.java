package src.main;

import java.io.*;

public class Map {
    char[][] current_map = null;
    int Mapsize_row, Mapsize_col;
    File MapFile;
    File recent_validMapFile;
    int line_cnt = 0;
    int Map_row_Max = 1; // Map이 Start보다 왼쪽, End보다 오른쪽에 타일이 위치한 맵일 경우까지 구현하기 위해 시작을 1,1로 놓은 뒤 Max와 Min을
                         // 통해 Map사이즈를 파악
    int Map_col_Max = 1;
    int Map_row_Min = 1;
    int current_row = 1;
    int current_col = 1;
    boolean isSizeDetermined;

    Map() {
        MapFile = new File("./map/default.map");
        recent_validMapFile = MapFile;
        try {
            mapInit();
        } catch (Exception ignore) {
        }

    }

    public void selectMap() {
        FileManager f = new FileManager();
        File temp;
        if ((temp = f.SelectFile()) != null)
            this.MapFile = temp;

    }

    public void printCurrentMapFile() {
        System.out.println(this.MapFile.getName());
    }

    public boolean mapInit() throws IOException {
        variableInit();
        char direction_passed = 'S';
        char direction_passed_opposite;
        int isAllBridgeConnected = 0;

        BufferedReader reader = new BufferedReader(
                new FileReader(this.MapFile));
        String str;
        while ((str = reader.readLine()) != null) // map[][] 배열의 할당을 위해 size를 파악하는 동안, map file의 내용이 valid한지 체크.
        {
            if (line_cnt == 0) {
                direction_passed = str.charAt(2);
            } else if (str.charAt(0) == 'E') {
                if (!expandMap(direction_passed)) {
                    System.out.println("This file is not valid Map file." + line_cnt);
                    return false;
                }
                System.out.println("end of file");
            } else {
                direction_passed_opposite = oppositeDirection(direction_passed);
                if (direction_passed_opposite == 'S' || !isValidChar(str.charAt(0))) {
                    System.out.println("This file is not valid Map file.1" + line_cnt);
                    return false;
                } else {
                    if (!expandMap(direction_passed)) {
                        System.out.println("This file is not valid Map file." + line_cnt);
                        return false;
                    }
                    if (str.charAt(2) == direction_passed_opposite)
                        direction_passed = str.charAt(4);
                    else if (str.charAt(4) == direction_passed_opposite)
                        direction_passed = str.charAt(2);
                    else {
                        System.out.println("this file is not valid Map file.2");
                        return false;
                    }
                }
            }
            line_cnt++;
        }
        reader.close();
        Mapsize_row = Map_row_Max - Map_row_Min + 1;
        Mapsize_col = Map_col_Max;
        current_map = new char[Mapsize_row][Mapsize_col];
        reader = new BufferedReader(
                new FileReader(this.MapFile));
        line_cnt = 0;
        current_row = 1 - Map_row_Min;
        current_col = 0;
        isSizeDetermined = true;
        while ((str = reader.readLine()) != null) {
            current_map[current_row][current_col] = str.charAt(0);
            if (str.charAt(0) == 'B') // B 셀의 경우 다리 건너편 셀이 b인지 확인하고 사이에 다리를 설치. B셀보다 b셀이 먼저 설정될 경우
            {
                if (isValidChar(current_map[current_row][current_col + 2])) // 셀 값이 들어있는지를 확인
                {
                    if (current_map[current_row][current_col + 2] == 'b') {
                        current_map[current_row][current_col + 1] = '='; // 다리 셀을 '='로 표현.
                        isAllBridgeConnected -= 1;
                    } else {
                        System.out.println("this file is not valid Map file.3");
                        return false;
                    }
                } else {
                    isAllBridgeConnected += 1;
                }
            } else if (str.charAt(0) == 'b') // b 셀의 경우 다리 건너편 셀이 B인지 확인하고 사이에 다리를 설치. b셀보다 B셀이 먼저 설정될 경우
            {
                if (isValidChar(current_map[current_row][current_col - 2])) // 셀 값이 들어있는지를 확인
                {
                    if (current_map[current_row][current_col - 2] == 'B') {
                        current_map[current_row][current_col - 1] = '='; // 다리 셀을 '='로 표현.
                        isAllBridgeConnected -= 1;
                    } else {
                        System.out.println("this file is not valid Map file.4");
                        return false;
                    }
                } else {
                    isAllBridgeConnected += 1;
                }
            }
            if (line_cnt == 0) {
                expandMap(str.charAt(2));
                direction_passed = str.charAt(2);
            } else if (str.charAt(0) == 'E') {
                System.out.println("end of file");
            } else {
                char[] s = new char[2];
                s = except_direction(str.charAt(2), str.charAt(4));
                for (int i = 0; i < 2; i++) {
                    int[] isSircletest = new int[2];
                    isSircletest[0] = current_row;
                    isSircletest[1] = current_col;
                    System.out.println(line_cnt + " current_row:" + current_row + " current_col" + current_col);
                    switch (s[i]) {
                        case 'U':
                            isSircletest[0] -= 1;
                            break;
                        case 'D':
                            isSircletest[0] += 1;
                            break;
                        case 'L':
                            isSircletest[1] -= 1;
                            break;
                        case 'R':
                            isSircletest[1] += 1;
                            break;
                    }
                    if (isSircletest[0] >= 0 && isSircletest[0] < Mapsize_row && isSircletest[1] >= 0
                            && isSircletest[1] < Mapsize_col) {
                        if (isValidChar(current_map[isSircletest[0]][isSircletest[1]])
                                && current_map[isSircletest[0]][isSircletest[1]] != '=') {
                            System.out.println("this file is not valid Map file.");
                            return false;
                        }
                    }
                }
                direction_passed_opposite = oppositeDirection(direction_passed);
                if (str.charAt(2) == direction_passed_opposite) {
                    expandMap(str.charAt(4));
                    direction_passed = str.charAt(4);
                }
                if (str.charAt(4) == direction_passed_opposite) {
                    expandMap(str.charAt(2));
                    direction_passed = str.charAt(2);
                }
            }
            line_cnt++;
        }
        reader.close();
        if (isAllBridgeConnected != 0) {
            System.out.println("this file is not valid Map file.5");
            return false;
        }
        System.out.println("This map file is valid");
        recent_validMapFile = this.MapFile;
        printMap();
        return true;
    }

    char oppositeDirection(char c) {
        if (c == 'L')
            return 'R';
        if (c == 'R')
            return 'L';
        if (c == 'U')
            return 'D';
        if (c == 'D')
            return 'U';
        return 'S';
    }

    boolean isValidChar(char c) {
        switch (c) {
            case 'L':
            case 'R':
            case 'U':
            case 'D':
            case 'S':
            case 'B':
            case 'b':
            case 'H':
            case 'P':
            case 'C':
            case 'E':
            case '=':
                return true;
        }
        return false;
    }

    boolean expandMap(char c) {
        if (c == 'L')
            current_col -= 1;
        if (c == 'R')
            current_col += 1;
        if (c == 'U')
            current_row -= 1;
        if (c == 'D')
            current_row += 1;
        if (!isSizeDetermined) {
            if (current_row > Map_row_Max)
                Map_row_Max = current_row;
            if (current_row < Map_row_Min)
                Map_row_Min = current_row;
            if (current_col > Map_col_Max)
                Map_col_Max = current_col;
            if (current_col < Map_col_Max) {
                return false;
            }
        }
        return true;
    }

    char[] except_direction(char a, char b) {
        int[] UDLR = new int[4];
        char[] result = new char[2];
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            UDLR[i] = 0;
        }
        switch (a) {
            case 'U':
                UDLR[0] = 1;
                break;
            case 'D':
                UDLR[1] = 1;
                break;
            case 'L':
                UDLR[2] = 1;
                break;
            case 'R':
                UDLR[3] = 1;
                break;
        }
        switch (b) {
            case 'U':
                UDLR[0] = 1;
                break;
            case 'D':
                UDLR[1] = 1;
                break;
            case 'L':
                UDLR[2] = 1;
                break;
            case 'R':
                UDLR[3] = 1;
                break;
        }
        for (int i = 0; i < 4; i++) {
            if (UDLR[i] == 0) {
                switch (i) {
                    case 0:
                        result[cnt] = 'U';
                        break;
                    case 1:
                        result[cnt] = 'D';
                        break;
                    case 2:
                        result[cnt] = 'L';
                        break;
                    case 3:
                        result[cnt] = 'R';
                        break;
                }
                cnt++;
            }
        }
        return result;
    }

    void variableInit() {
        line_cnt = 0;
        Map_row_Max = 1;
        Map_col_Max = 1;
        Map_row_Min = 1;
        current_row = 1;
        current_col = 1;
        isSizeDetermined = false;
    }

    void printMap() {
        for (int i = 0; i < Mapsize_row; i++) {
            for (int j = 0; j < Mapsize_col; j++) {
                if (isValidChar(current_map[i][j]))
                    System.out.print(current_map[i][j] + " ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }
}
