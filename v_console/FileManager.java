package src.v_console;

import java.io.*;

public class FileManager {

    public File SelectFile() {
        File mapDir = new File("./map");
        String[] list = mapDir.list();
        String[] maps = new String[list.length];
        int map_cnt = 0;
        for(int i=0;i<list.length;i++)
        {
            if(list[i].split("\\.")[1].equals(new String("map")))
            {
                maps[map_cnt] = list[i];
                map_cnt++;
            }
        }
        System.out.println("*****************************************");
        System.out.println("*                                       *");
        System.out.println("*               LOAD MAP                *");
        System.out.println("*                                       *");
        System.out.println("*                                       *");
        for(int i=0;i<map_cnt;i++)
        {
        int space = (39-maps[i].length()-2)/2;
        System.out.print("*");
        for(int j=0;j<space;j++)System.out.print(" ");
        System.out.print((i+1)+"."+maps[i]);
        for(int j=0;j<(39-maps[i].length()-2)-space;j++)System.out.print(" ");
        System.out.println("*");
        }
        System.out.println("*                                       *");
        System.out.println("*                                       *");
        System.out.println("*****************************************");

        System.out.println();
        System.out.print("Enter Map Number to select File : ");
        
        int map_num = getMapNum();
        while(!(map_num>0)||!(map_num<=map_cnt))
        {
            System.out.println("Please Enter valid map number.");
            map_num = getMapNum();
        }
        mapDir = new File("./map/"+maps[map_num-1]);
        return mapDir;
    }

    public int getMapNum()
    {
        int map_num;
        try
        {
            map_num =  System.in.read();
            System.in.skip(2);
        }catch (Exception e)
        {
            System.out.println("Please Enter valid map number.");
            return getMapNum();
        }
        return map_num-48;
    }
}
