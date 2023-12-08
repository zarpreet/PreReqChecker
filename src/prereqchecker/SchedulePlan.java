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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }

    // WRITE YOUR CODE HERE

        StdIn.setFile(args[0]);
        AdjList graph = new AdjList();

        StdIn.setFile(args[1]);
        String targetF = StdIn.readLine();
        int numTaken = StdIn.readInt();
        StdIn.readLine();
        ArrayList<String> inputList = new ArrayList<String>();
        for (int i = 0; i < numTaken; i++) {
            inputList.add(StdIn.readLine());
        }

        ArrayList<String> target = new ArrayList<String>();
        target.add(targetF);

        ArrayList<String> taken = graph.Completed(inputList);
        ArrayList<String> targetC = graph.Completed(target);

        for (int i = 0; i < targetC.size(); i++) { 
            for(int j = i + 1; j < targetC.size(); j++){
                if(targetC.get(i).equals(targetC.get(j))){
                    targetC.remove(j);
                }
            }
        }

        targetC.remove(0);

        for(int i = 0; i < taken.size(); i++) {
            targetC.remove(taken.get(i));
        }

        ArrayList<ArrayList<String>> courseSem = new ArrayList<ArrayList<String>>();
        HashMap<String, ArrayList<String>> adjList = graph.getGraph();

        while(targetC.size() != 0){
            ArrayList<String> semester = new ArrayList<String>();

            for(int i = 0; i < targetC.size(); i++){
                boolean completed = true;
                for(int j = 1; j < adjList.get(targetC.get(i)).size(); j++){
                    if(targetC.contains(adjList.get(targetC.get(i)).get(j))){
                        completed = false; 
                        break;
                    }

                }
                if(completed){
                    semester.add(targetC.get(i));
                }
            }

            for(String course : semester){
                targetC.remove(course);
            }

            courseSem.add(semester);
        }

        StdOut.setFile(args[2]);
        StdOut.println(courseSem.size());
        for(int i = 0; i < courseSem.size(); i++){
            for(int j = 0; j < courseSem.get(i).size(); j++){
                StdOut.print(courseSem.get(i).get(j) + " ");
            }
            StdOut.println();
        }
    }
}