package prereqchecker;

import java.util.*;
/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {

    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        String inputFileName = args[0];
        String outputFileName = args[1];

        StdIn.setFile(inputFileName);

        int numCourses = StdIn.readInt();

        String[][] adjList = new String[numCourses][];

        for (int i = 0; i < numCourses; i++) {
            String courseId = StdIn.readString();
            adjList[i] = new String[]{courseId};
        }

        int numEdges = StdIn.readInt();

        for (int i = 0; i < numEdges; i++) {
            String course = StdIn.readString();
            String prerequisite = StdIn.readString();

            int courseIndex = getCourseIndex(course, adjList);

            adjList[courseIndex] = append(adjList[courseIndex], prerequisite);
        }

        StdOut.setFile(outputFileName);

        for (String[] courseEntry : adjList) {
            StdOut.print(courseEntry[0] + " ");
            for (int i = 1; i < courseEntry.length; i++) {
                StdOut.print(courseEntry[i] + " ");
            }
            StdOut.println();
        }
    }

    private static int getCourseIndex(String course, String[][] adjList) {
        for (int i = 0; i < adjList.length; i++) {
            if (adjList[i][0].equals(course)) {
                return i;
            }
        }
        return -1; 
    }

    private static String[] append(String[] array, String element) {
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = element;
        return newArray;
    }

    private HashMap<String, ArrayList<String>> adjList; 

    public void build(){
        int numCourses = StdIn.readInt();
        StdIn.readLine();

        String course = null;

        for(int i = 0; i < numCourses; i++){
            course = StdIn.readLine();
            adjList.put(course, new ArrayList<String>());
            adjList.get(course).add(course);
        }
        
        int numConnections = StdIn.readInt();
        StdIn.readLine();
        String prereq = null;

        for(int i = 0; i < numConnections; i++){
            prereq = StdIn.readLine();
            
            course = prereq.substring(0, prereq.indexOf(" "));

            prereq = prereq.substring(prereq.indexOf(" ") + 1);
            adjList.get(course).add(prereq);
        }
    }

    public AdjList(){ 
        adjList = new HashMap<String, ArrayList<String>>();
        build();
    }

    public boolean search(String course1, String course2){
        if(course2.equals(course1)){
            return true;
        }
        if(adjList.get(course2).size() == 1){
            return false;
        }

        for(int i = 1; i < adjList.get(course2).size(); i++){

            if(search(course1, adjList.get(course2).get(i))){
                return true;
            }
        }

        return false;
    }

    public void Edge(String course, String prereq){
        adjList.get(course).add(prereq);
    }

    public HashMap<String, ArrayList<String>> getGraph(){
        return adjList;
    }

    public boolean Cycle(String course1, String course2){
        boolean hasCycle = search(course1, course2);
        return hasCycle;
    }

    
    public void prereq(ArrayList<String> prereqList, String course){
        for(int i = 1; i < adjList.get(course).size(); i++){
            prereqList.add(adjList.get(course).get(i));
            prereq(prereqList, adjList.get(course).get(i));
        }
    }
    
    public ArrayList<String> Completed(ArrayList<String> courses){
        ArrayList<String> prereqList = new ArrayList<String>();
        for(int i = 0; i < courses.size(); i++){
            prereqList.add(courses.get(i));
            prereq(prereqList, courses.get(i));
        }
        return prereqList;
    }
}
