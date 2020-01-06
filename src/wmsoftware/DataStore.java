/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wmsoftware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meenakshi 10092019
 */
public class DataStore {

    private File[] fileList;
//    private ArrayList<Mouse> Mice = new ArrayList<>();
    private HashMap<Integer, DataTrace_ver1> position = null;
    private HashMap<Integer, ArrayList<Integer>> residenceTime = null;
    private HashMap<Integer, DataTrace_ver1> displacement = null;
    private HashMap<Integer, DataTrace_ver1> velocity = null;
    private HashMap<Integer, DataTrace_ver1> velocityAPt = null;
    private HashMap<Integer, DataTrace_ver1> velocityPPt = null;
    private HashMap<Integer, DataTrace_ver1> velocityError = null;
    private HashMap<Integer, ArrayList<Double>> distance = null;
    private HashMap<Integer, ArrayList<Double>> speed = null;
    private HashMap<Integer, ArrayList<Double>> speedAPt = null;
    private HashMap<Integer, ArrayList<Double>> speedPPt = null;
    private HashMap<Integer, ArrayList<Double>> speedError = null;

    /**
     *
     * @param num Total number of mice
     * @param files
     */
    public DataStore(int num, File[] files) {
        for (int i = 0; i < num; i++) {
//            Mice.add(i, new Mouse());
//            Mice.get(i).setID(i);
        }
        fileList = files;
    }

    /**
     * Get the files selected for a particular DataStore/trial For now, prints
     * the array of files as well
     *
     * @return an array of files
     */
    public File[] getFiles() {
        return fileList;
    }

    public int getTotalMice() {
        return fileList.length;
    }

//    public ArrayList<Mouse> getMiceList() {
//        return Mice;
//    }
//
//    public void setMouse(Mouse M) {
//        Mice.add(M);
//    }
//
//    public Mouse getMouse(int i) {
//        return Mice.get(i);
//    }
    public HashMap getHMap(String s) {
        HashMap result = null;
        switch (s) {
            case "Position":
                result = position;
                break;
            case "Residence Time":
                result = residenceTime;
                break;
            case "Displacement":
                result = displacement;
                break;
            case "Velocity":
                result = velocity;
                break;
            case "Velocity along Platform":
                result = velocityAPt;
                break;
            case "Velocity perpendicular Platform":
                result = velocityPPt;
                break;
            case "Velocity Error":
                result = velocityError;
                break;
            case "Distance":
                result = distance;
                break;
            case "Speed":
                result = speed;
                break;
            case "Speed along Pt":
                result = speedAPt;
                break;
            case "Speed perpendicular Pt":
                result = speedPPt;
                break;
            case "Speed Error":
                result = speedError;
                break;
        }
        return result;
    }

    public void setHMap(String s, HashMap hm) {
        switch (s) {
            case "Position":
                position = hm;
                break;
            case "Residence Time":
                residenceTime = hm;
                break;
            case "Displacement":
                displacement = hm;
                break;
            case "Velocity":
                velocity = hm;
                break;
            case "Velocity along Platform":
                velocityAPt = hm;
                break;
            case "Velocity perpendicular Platform":
                velocityPPt = hm;
                break;
            case "Velocity Error":
                velocityError = hm;
                break;
            case "Distance":
                distance = hm;
                break;
            case "Speed":
                speed = hm;
                break;
            case "Speed along Pt":
                speedAPt = hm;
                break;
            case "Speed perpendicular Pt":
                speedPPt = hm;
                break;
            case "Speed Error":
                speedError = hm;
                break;
        }
    }

    /**
     * Reads an array of file
     *
     */
    public void readFile() {
        if (this.fileList != null) {
            int noFiles = this.fileList.length;   //stores the number of files selected by the user
            int count = 0;

            for (File curFile : this.fileList) {

                String dataString = "";
                int c = 0;
                float xData = 0;
                float yData = 0;
                FileReader fReader = null;                      //Reader class : Java class for reading text files (ASCII)
                DataTrace_ver1 series = new DataTrace_ver1();
                position = position == null ? new HashMap<>() : position;

                if (curFile.exists()) {

                    try {
                        fReader = new FileReader(curFile);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        while ((c = fReader.read()) != -1) {
                            switch (c) {
                                case '\t':
                                    xData = Float.parseFloat(dataString);
                                    dataString = "";
                                    break;
                                case '\n':
                                    yData = Float.parseFloat(dataString);
                                    series.addData(xData, yData);
                                    dataString = "";
                                    break;
                                default:
                                    dataString += (char) c;
                            }
                        }
                        position.put(count, series);
//                        position.put(Mice.get(count).getID(), series);
                        count = count + 1;
                    } catch (IOException ex) {
                        Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public File writeFile(String name, String directory, DataTrace_ver1 measure) {
        //create a filewriter object
        FileWriter outStream = null;
        File out = new File(directory + "\\" + name);
        try {
            outStream = new FileWriter(out);
                int count2 = 0;
                String toWrite = "";
                while (count2 < measure.size()) {
                    toWrite += measure.getX().get(count2) + "\t" + measure.getY().get(count2) + "\n";
                    count2++;
                }
                outStream.write(toWrite);
                outStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

}
