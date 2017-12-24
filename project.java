//@author tuxin
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class project {
    public static double r = 3; // radius
    public static int minPoints = 2;
    public static List<List<Point>> clusters = new ArrayList<>();

    public static double dist(Point point1, Point point2) {
        double distance = Math.sqrt(Math.pow((point1.x - point2.x), 2) + Math.pow((point1.y - point2.y), 2) );
        return distance;
    }


    public static void startClustering(List<Point> points) throws Exception {
        for (Point point : points) {
            if (point.clustered == true)
            {
                continue;
            }
            List<Double> pointToClusrter = new ArrayList<>(); ;
            for (int k = 0;k<points.size();k++)
            {
            	pointToClusrter.add(dist(point,points.get(k)));
            }
            ArrayList<Point> cluster = new ArrayList<Point>();
            for (int i = 0; i < points.size(); i++) {
                if (pointToClusrter.get(i) < r)
                {
                    cluster.add(points.get(i));
                }
            }
            if(cluster.size() < minPoints) 
        	{
        		continue;
        	}
        	point.clustered = true;
            clusters.add(cluster);
            merge(cluster);
        }
        System.out.println("Finished Clustering");
        writeToFile();
    }

    public static boolean checkRadiusPoints(List<Point> c1, List<Point> c2) {
       	ArrayList<Point> result = new ArrayList<>(c1);
       	result.retainAll(c2);
        if (result.size() > 0) {
            return true;
        }
        return false;
    }

    public static void merge(ArrayList<Point> cluster){
    	int index = 0;
        ArrayList<Integer> indexList = new ArrayList<>();
        for(List<Point> cluster_tmp : clusters)
        {
            if(checkRadiusPoints(cluster,cluster_tmp) == false)
            {
            	index++;
            	continue;
            }
            indexList.add(index);
            index++;
        }
        ArrayList<Point> small_cluster = new ArrayList<>();
        List<List<Point>> unique_delete = new ArrayList<>();
        for (Integer loca : indexList)
        {
        	small_cluster.addAll(clusters.get(loca));
        	unique_delete.add(clusters.get(loca));		
        }
        clusters.removeAll(unique_delete);
        ArrayList<Point> tmp = new ArrayList<>();
        for (Point p : small_cluster)
        {
        	if(!tmp.contains(p))
        	{
        		tmp.add(p);
        	}
        }
        clusters.add(tmp);
    }
    public static List<Point> readFile() throws Exception {
        System.out.println("Entering points creation");
        List<Point> points = new ArrayList<Point>();
        BufferedReader br = new BufferedReader(new FileReader("3spiral.txt"));
        String line ;
        while ((line = br.readLine()) != null) {
            String p[] = line.split("\\s+"); //regular expresion, use to match the white spaces between x and y
            points.add(new Point( Double.parseDouble(p[0]),Double.parseDouble(p[1]), false));
        }
        br.close();
        return points;
    }

    public static void writeToFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("result.txt"));
        if (clusters.size() != 3)
        {
        	System.out.println("We dont have 3 clusters,something wrong.change r or minPoints");
        	System.exit(0);
        }
        for(int i =1;i<=3;i++)
        {
        	List<Point> cluster  = clusters.get(i-1);
        	for (int j=0;j<cluster.size();j++)
        	{
        		Point point = cluster.get(j);
        		StringBuffer str = new StringBuffer();
                str.append(point.x+",").append(point.y+",").append(i).append("\n");              
                bw.write(str.toString());
        	}
        }
        bw.close();
    }

    public static void main(String[] args) throws Exception {
        List<Point> points = readFile();
        System.out.println(points.size()+"	data points");
        startClustering(points);
        System.out.println("Resulting file->result.txt, format is (x,y,cluster)\nTask Successed!");
    }
}