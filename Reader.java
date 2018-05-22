import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

    public static ArrayList<String> parameters;
    public static ArrayList<String> units ;
    public static ArrayList<Double> values;


    public Reader() {
        parameters = new ArrayList<>();
        units=new ArrayList<>();
        values=new ArrayList<>();
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    public void  ReadFromFile(String fileName) {

        try {

            Scanner s = new Scanner(new File(fileName));



            boolean stop = true;

            while (stop){
                if(s.next().equals("~A")) stop = false;
            }
            stop = true;
            String string = "";
            while (stop){
                string = s.next();
                if(string.equals("#") || isNumeric(string)) stop = false;
                else
                    parameters.add(string);
            }

            stop = true;
            if(string.equals("#"))
                while (stop) {
                    string = s.next();
                    if (isNumeric(string) != false) stop = false;
                    else
                        units.add(string);
                }
            /*if(isNumeric(string)) values.add(string);
            while (s.hasNext()){
                values.add(s.next());
            }*/
            stop = true;
            while (stop) {
                values.add(Double.parseDouble(string));
                if(s.hasNext())
                    string = s.next();
                else
                    stop = false;
            }
            /*for(int i =0 ; i<parameters.size();i++)
            {
                System.out.println(parameters.get(i));
            }*/
            /*for(String a : units)
                System.out.println(a);*/

            for(Double a : values)
                System.out.println(a);


            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
