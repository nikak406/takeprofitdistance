import java.io.File;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataBase implements Constants {

    double db[][];
    public double multiplicator = 0.3;
    public double TPD = 0.0;

    DataBase(String[] files){
        String str = readFiles(files);
        String[] line = Pattern.compile("\n").split(str);
        Scanner scanner;
        double res[][] = new double[line.length][2];
        for(int i=0; i<line.length; i++){
            scanner = new Scanner(line[i]);
            if (scanner.hasNextDouble()) res[i][0] = scanner.nextDouble();
            if (scanner.hasNextDouble()) res[i][1] = scanner.nextDouble();
        }
        db = res;
    }

    DataBase(File[] files, double x){
        multiplicator = x;
        String[] paths = new String[files.length];
        for(int i=0; i<files.length; i++){
            paths[i] = files[i].getPath();
        }
        String str = readFiles(paths);
        String[] line = Pattern.compile("\n").split(str);
        Scanner scanner;
        double res[][] = new double[line.length][2];
        for(int i=0; i<line.length; i++){
            scanner = new Scanner(line[i]);
            if (scanner.hasNextDouble()) res[i][0] = scanner.nextDouble();
            if (scanner.hasNextDouble()) res[i][1] = scanner.nextDouble();
        }
        db = res;
    }

    DataBase(double x, File[] files){
        TPD = x;
        String[] paths = new String[files.length];
        for(int i=0; i<files.length; i++){
            paths[i] = files[i].getPath();
        }
        String str = readFiles(paths);
        String[] line = Pattern.compile("\n").split(str);
        Scanner scanner;
        double res[][] = new double[line.length][2];
        for(int i=0; i<line.length; i++){
            scanner = new Scanner(line[i]);
            if (scanner.hasNextDouble()) res[i][0] = scanner.nextDouble();
            if (scanner.hasNextDouble()) res[i][1] = scanner.nextDouble();
        }
        db = res;
    }


    DataBase(){
        db = new double[1][2];
    }

    double[] data(double tpd){
        int k = db.length;
        double[] res = new double[k];
        for(int i = 0; i<k; i++){
            if (db[i][0]>=tpd) res[i] = tpd;
            else res[i] = db[i][0];
            if (db[i][1]>=tpd) res[i] = tpd;
            if (tpd==0.0) res[i] = db[i][0];
        }
        return res;
    }

    String readFiles(String[] files){
        StringBuilder str= new StringBuilder("");
        for (String file : files) {
            try (FileChannel fChan = (FileChannel) Files.newByteChannel(Paths.get(file))) {
                long fSize = fChan.size();
                MappedByteBuffer mBuf = fChan.map(FileChannel.MapMode.READ_ONLY, 0, fSize);
                for (int i = 0; i < fSize; i++) str.append((char) mBuf.get());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return str.toString();
    }

    double getProfit(double data[], double L){
        double res = 1;
        for(double x: data) res*=(1+L*x);
        return res;
    }

    double [][] getTPDResult(){
        int length = (int) ((maxTPD - minTPD)/stepTPD);
        double res[][] = new double[length][2];
        double currenttpd = minTPD;
        for(int i=0; i<length; i++){
            res[i][0] = (double) Math.round(100*currenttpd)/100;
            res[i][1] = (double) Math.round(100*getProfit(data(currenttpd), multiplicator))/100;
            currenttpd+=stepTPD;
        }
        return res;
    }

    double [][] getMultResult(){
        int length = (int) ((maxMult - minMult)/stepMult);
        double res[][] = new double[length][2];
        double currentmult = minMult;
        for(int i=0; i<length; i++){
            res[i][0] = (double) Math.round(100*currentmult)/100;
            res[i][1] = (double) Math.round(100*getProfit(data(TPD), currentmult))/100;
            currentmult+=stepMult;
        }
        return res;
    }
}
