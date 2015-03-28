import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.DoubleStream;

public class Data implements Constants {

    double db[][] = new double[1][2];
    public double multiplicator = 0.3;
    public double takeProfitDistance = 0.0;

    Data(){}

    Data(File[] files, Double multiplicator, Double takeProfitDistance){
        if (files == null) return;
        if (multiplicator != null ) this.multiplicator = multiplicator;
        if (takeProfitDistance != null ) this.takeProfitDistance = takeProfitDistance;
        String[] paths = new String[files.length];
        for(int i=0; i<files.length; i++){
            paths[i] = files[i].getPath();
        }
        String str = readFiles(paths);
        String[] lines = str.split(System.getProperty("line.separator"));
        Scanner scanner;
        double res[][] = new double[lines.length][2];
        for(int i=0; i<lines.length; i++){
            scanner = new Scanner(lines[i]);
            if (scanner.hasNextDouble()) res[i][0] = scanner.nextDouble();
            if (scanner.hasNextDouble()) res[i][1] = scanner.nextDouble();
        }
        this.db = res;
    }

    double[] computeTP(double takeProfit){
        int length = db.length;
        double[] res = new double[length];
        for(int i = 0; i<length; i++){
            if (db[i][0]>=takeProfit)
                res[i] = takeProfit;
            else
                res[i] = db[i][0];
            if (db[i][1]>=takeProfit)
                res[i] = takeProfit;
            if (takeProfit==0.0)
                res[i] = db[i][0];
        }
        return res;
    }

    String readFiles(String[] files){
        StringBuilder str = new StringBuilder("");
        for (String file : files) {
            try{
                byte[] bytes = Files.readAllBytes(Paths.get(file));
                String content = new String(bytes);
                str.append(content);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return str.toString();
    }

    double getProfit(double data[], double L){
        return DoubleStream.of(data).reduce(1, (x, y) -> x*(1+L*y));
    }

    double [][] getTPDResult(){
        int length = (int) ((maxTPD - minTPD)/stepTPD);
        double res[][] = new double[length][2];
        double currentTP = minTPD;
        for(int i=0; i<length; i++){
            res[i][0] = (double) Math.round(100*currentTP)/100;
            res[i][1] = (double) Math.round(100*getProfit(computeTP(currentTP), multiplicator))/100;
            currentTP+=stepTPD;
        }
        return res;
    }

    double [][] getMultResult(){
        int length = (int) ((maxMult - minMult)/stepMult);
        double res[][] = new double[length][2];
        double currentMult = minMult;
        for(int i=0; i<length; i++){
            res[i][0] = (double) Math.round(100*currentMult)/100;
            res[i][1] = (double) Math.round(100*getProfit(computeTP(takeProfitDistance), currentMult))/100;
            currentMult+=stepMult;
        }
        return res;
    }
}
