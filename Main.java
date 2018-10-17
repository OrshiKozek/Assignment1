import java.util.ArrayList;

public class Main {

    private static int[] skip_indexes = new int[5];
    private static int run_size = 3;

    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello World");
        int[] arr = new int[]{3, 5, 7, 1, 6, 4, 2, 6, 8, 9, 3};
    }


    public static void find_runs(int[] arr) {
        int i;
        int check = 0;
        int skip_track_index = 0;
        for (i = 0; i < arr.length; i++) { //go through arr
            check = i; //this is the first thing that is looked at as if it were the first element of the run (it might be, it might not)
            while (arr[check] < arr[check + 1]) { //compares check to the next one
                check++; //increase check, to be able to check the next one
            }
            if (check - i >= run_size) //make sure that the run is the necessary length- ie 3+
                skip_indexes[skip_track_index++] = i; //set the next element of the skip_indexes array to be the index of the start of the run.
                skip_indexes[skip_track_index++] = check; //this is the last index of the given run
        }


    }//find_runs

    public static void sort_extras(int[] arr, int[] skip_indexes){ //this function sorts the elements of the array that are not in runs
        //somehow iterate through the array, but skip over the indexes that contain the runs
        //fix the find_runs to store the beginning of all the runs, and the beginnings of the not runs as well
        //sort the non-run elements
        //merge the newly sorted ones and the original runs
        //first new run merged with first old run. then go on to the next two runs and merge them
        // merge first big run and second big run, etc etc until array is sorted

        //this is where we sort the non-runs in the array, based on where they are located in the array (ie by indexes)
        //go through the array that holds the start and end of the runs, and sort the ones in between
        for (int i = 1; i < arr.length; i+=2){
            //iterate through the array, based on where there are runs and where there are not
            //each pair of elements is the start and end index of a run
            //this starts at i = 1, because we're looking at the end of one run, and the start of the next, and sorting what is in between
            insertionSort(arr, skip_indexes[i], skip_indexes[i+1]); //calls insertionSort on the specified section of the array
        }
    }

    public static void insertionSort(int[] a, int start, int end) { //start is the start index of the sub-array. end is the end index

        int sorted = start ; //index of the last element in the sorted array, initialized to start at the given starting point
        int tempval = 0; //default value set to 0
        int counter = 0;

        while(sorted < end){ //goes through until the given end is reached (this is not necessarily the end of the array)
            if (a[sorted + 1] >= a[sorted]){ //if the next element is greater than the last element of the sorted array, move the marker one to the right.
                sorted++;
            }
            else {

                tempval = a[sorted + 1];
                counter = sorted;
                while(a[counter] > tempval){
                    a[counter + 1] = a[counter];

                    if (counter == start)//break once the start is reached (this might not be the element at index 0)
                        break;
                    else if(a[counter-1] < a[counter] && a[counter-1] < tempval)
                        break;
                    else
                        counter--;
                }
                a[counter] = tempval;
                sorted++;
                // printArray(a);
            }
        }
    }

}//main