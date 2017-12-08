

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class example1 {
    public static final String fileName = "3spiral.txt";
    public static final String resultFileName = "result.txt";
    public static double r = 3; // 半径
    public static int minPoints = 2;// 密度阈值
    public static List<List<Point>> microCluster = new ArrayList<>();


    public static List<Point> readFile() throws Exception {
        System.out.println("Entering point creation");
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        List<Point> points = new ArrayList<Point>();
        String line = file.readLine();
        int i = 0;
        while (line != null) {
            String p[] = line.split("[;|,|\\s]");
            List<Double> location = new ArrayList<Double>();
            for (int j = 0; j < p.length; j++) {
                location.add(Double.parseDouble(p[j]));
            }
            points.add(new Point(i++, location, 0));
            line = file.readLine();
        }
        file.close();
        return points;
    }

    public static void saveResult() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFileName));
        int i = 1;
        System.out.println(microCluster.size());
        for(List<Point> mic : microCluster){
            for(Point p  : mic){
                StringBuffer sb = new StringBuffer();
                for(int j = 0;j<p.location.size();j++){
                    sb.append(p.location.get(j)+",");
                }
                bw.write(sb.append(i).toString());
                bw.newLine();
            }
            i++;
        }
        bw.flush();
        bw.close();
    }

    public static double getDistance(Point point1, Point point2) {
        int wide = point1.location.size(); // 共多少维
        double sum = 0;
        for (int i = 0; i < wide; i++) {
            sum += Math.pow((point1.location.get(i) - point2.location.get(i)), 2);
        }
        return Math.sqrt(sum);
    }

    public static List<Double> getDistances(Point point1, List<Point> points) {
        List<Double> diss = new ArrayList<>();
        int size = points.size();
        for (int i = 0; i < size; i++)
            diss.add(getDistance(point1, points.get(i)));
        return diss;
    }

    public static boolean canCombine(List<Point> clu1, List<Point> clu2) {
        Set<Point> result = new HashSet<Point>();
        Set<Point> s1 = new HashSet<Point>(clu1);
        Set<Point> s2 = new HashSet<Point>(clu2);
        result.clear();
        result.addAll(s1);
        result.retainAll(s2);
        if (result.size() > 0) {
            return true;
        }
        return false;
    }

    public static void combine(List<Point> clu){
        List<Integer> combine = new ArrayList<>();
        for(int i = 0;i< microCluster.size();i++){
            if(canCombine(clu,microCluster.get(i))) combine.add(i);
        }
        Set<Point> com = new HashSet<>();
        List<List<Point>> remove = new ArrayList<>();
        for(int i = 0;i<combine.size();i++){
            List<Point> p = microCluster.get(combine.get(i));
            com.addAll(p);
            remove.add(p);
        }
        microCluster.removeAll(remove);
        microCluster.add(new ArrayList<>(com));
    }

    public static void cluster(List<Point> points) {
        int size = points.size();
        for (int i = 0; i < size; i++) {
            Point p = points.get(i);
            if (p.flag != 0)
            {
                continue;
            }
            p.flag = 1;
            List<Double> diss = getDistances(p, points);
            List<Point> clu = new ArrayList<Point>();
            for (int j = 0; j < size; j++) {
                if (diss.get(j) < r)
                {
                    clu.add(points.get(j)); //若距离大于r
                }
            }
            if(clu.size() < minPoints) continue;
            else {
                microCluster.add(clu);
                combine(clu);
            }
        }
        System.out.println("Finished Cluster");
    }

    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();
        List<Point> points = readFile();
        System.out.println(points.size());
        cluster(points);
        saveResult();
        System.out.println("use time:"+(System.nanoTime() - start));
        System.out.println("Finished");
    }
}