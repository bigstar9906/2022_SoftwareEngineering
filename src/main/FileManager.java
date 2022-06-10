package src.main;

import java.io.*;
import javax.swing.*;

public class FileManager {

    public File SelectFile() {                                  //GUI를 활용하여 map폴더를 열고 map 파일 선택
        JFrame window = new JFrame();
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File mapDir = new File("./map");
        jfc.setCurrentDirectory(mapDir);
        jfc.showOpenDialog(window);
        return jfc.getSelectedFile();
    }
}
