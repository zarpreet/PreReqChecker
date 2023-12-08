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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

    // WRITE YOUR CODE HERE

        StdIn.setFile(args[0]);
        StdOut.setFile(args[2]);

        ArrayList<classes> result = result();
        StdIn.setFile(args[1]);
        String courseW = StdIn.readLine();

        ArrayList<String> required = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>(); 

        classes targetCourse = null;

        for (int m = 0; m < result.size(); m++) {
            if (!result.get(m).getName().equals(courseW))
                continue;
            else
                targetCourse = result.get(m);
        }

        ArrayList<String> taken = classesTaken(result);

        required = needToTake(taken, required, targetCourse, result);

        for (String course : required) {
            if (!temp.contains(course))
                temp.add(course);
                
        }

        printList(temp);

    } 

    public static void printList(ArrayList<String> printList) {
        for (int i = 0; i < printList.size(); i++) {
            StdOut.println(printList.get(i));
        }
    }

    public static ArrayList<String> needToTake(ArrayList<String> taken, ArrayList<String> needToTake, classes courseW, ArrayList<classes> DONE) {
        ArrayList<String> requirementsNeeded = courseW.getPreReqs();
        classes classIterate = null;
        if (!taken.contains(courseW.getName())) {
            for (int i = 0; i < requirementsNeeded.size(); i++) {
                String hold = requirementsNeeded.get(i);
                if (taken.contains(hold)) {
                    continue;
                }
                for(int m = 0; m < DONE.size(); m++) {
                    if(DONE.get(m).getName().equals(hold)) {
                        classIterate = DONE.get(m);
                    }
                }
                courseW = classIterate;
                needToTake.add(hold);
                ArrayList<String> neededCourses = needToTake(taken, needToTake, courseW, DONE);
                needToTake.addAll(neededCourses);
            }
        } else {

            return needToTake;

        }
        return needToTake;
    }

    public static ArrayList<String> classesTaken(ArrayList<classes> adjList) {
        int courseSize = Integer.parseInt(StdIn.readLine());
        ArrayList<String> done = new ArrayList<String>();
        ArrayList<String> form = new ArrayList<String>();

        for (int i = 0; i < courseSize; i++) {
            String hold = StdIn.readLine();
            done.add(hold);
        }

        for (int i = 0; i < done.size(); i++) {
            String name = done.get(i);
            classes currentCourse = null;

            for (int n = 0; n < adjList.size(); n++) {
                if (!adjList.get(n).getName().equals(name)) {
                } else {
                    currentCourse = adjList.get(n);
                }
            }

            ArrayList<String> needed = new ArrayList<String>();
            needed = classesTaken(adjList, currentCourse, needed);

            form.add(name);
            form.addAll(needed);

        }

        int zero = 0; 
        ArrayList<String> coursesDone = new ArrayList<String>();
        if(zero != 0) {
        form = traverseAdd(coursesDone);

        }
        for (String element : coursesDone) {
            if (!form.contains(element)) {
                form.add(element);
            }
        }

        return form;

    }

    public static ArrayList<String> classesTaken(ArrayList<classes> result, classes currentCourse, ArrayList<String> needed) {
        
        String hold;
        classes courseW; 

        if (!currentCourse.getPreReqs().isEmpty()) { 
            needed.addAll(currentCourse.getPreReqs());
            ArrayList<String> neededList = currentCourse.getPreReqs();
            for (int i = 0; i < neededList.size(); i++) {

                hold = neededList.get(i);
                courseW = null;

                for (int m= 0; m < result.size(); m++) {
                    if (result.get(m).getName().equals(hold)) {
                        courseW = result.get(m);
                    }
                }
                ArrayList<String> finalL = classesTaken(result, courseW, needed);
                needed.addAll(finalL);
            }

        } else if(currentCourse.getPreReqs().isEmpty()){
            return needed;
        }

        return needed;
    }

    public static ArrayList<classes> result() {
        int courseNum = StdIn.readInt();

        String courseTitle = StdIn.readLine();
        ArrayList<classes> courseList = new ArrayList<classes>();
        for (int i = 0; i < courseNum; i++) { 
            courseTitle = StdIn.readLine();
            classes courseID = new classes(); 

            courseID.Classes(courseTitle);
            courseList.add(courseID);
        }
        courseNum = StdIn.readInt();
        StdIn.readLine();

        while (StdIn.isEmpty() != true) {
            String course = StdIn.readString();
            String needed = StdIn.readString();

            for (int i = 0; i < courseList.size(); i++) {
                if (courseList.get(i).getName().equals(course)) {
                    courseList.get(i).takePrePreq(needed);
                    break;
                }
            }
        }
        return courseList;
    }

    public static ArrayList<String> traverseAdd(ArrayList<String> addedList) {

        ArrayList<String> newList = new ArrayList<String>();
        ArrayList<String> prevList = new ArrayList<String>();

        for (String course : prevList) {
            if (!newList.contains(course))
                newList.add(course);
        }
        return newList;
    }

    public static classes getcourseW(ArrayList<classes> done) {

        String readCourse = StdIn.readLine();
        classes courseW = null;

        for (int i = 0; i < done.size(); i++) {
            if (done.get(i).getName().equals(readCourse))
                courseW = done.get(i);
        }

        return courseW;

    }
    
    public static class classes {
    
        private String classTitle;
        private ArrayList<String> needToTake = new ArrayList<String>();
    
        public void Classes (String classTitle) {
            this.classTitle = classTitle;
            this.needToTake = new ArrayList<String>();
        }
    
        public String getName() {
            return classTitle; 
        }
    
        public ArrayList<String> getPreReqs() {
            return this.needToTake;
        }
    
        public void takePrePreq(String preReq) {
            this.needToTake.add(preReq);
        }
    }
}
