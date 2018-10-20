public class MySort implements SortingAlgorithm{

    public static int[] skip_indexes = new int[10];
    public static int run_size = 50;
    public static boolean firstRound = false;

    @Override
    public void sort(int[] array) {

        int[] newskip = new int[(array.length*2)+2];
        skip_indexes = newskip;

        run_size = 5;

        find_runs(array);

        sort_extras(array);

        run_size = 1;

        find_runs(array);

        helpMerge(array);
    }

    //////////////////////////////////////////////

    public static void sort_extras(int[] arr) {
        firstRound = true;
        if (skip_indexes[0] == 0 && skip_indexes[1] == 0){ //if the first two elements of skip indexes are both 0, there are no runs => sort the whole array
            insertionSort(arr, 0, arr.length-1);
            return;
        }

        if (skip_indexes[0] != 0 && skip_indexes[0] != 1) { //if the first run does not start at the beginning of the array
            //it's no use sorting an array of 1 element, so if the first run starts at index 1, don't bother sorting element 1
            insertionSort(arr, 0, skip_indexes[0] - 1); //start at 0, and end at the element before the start of the 1st run
        }

        for (int i = 1; i < skip_indexes.length-2; i+=2) {
            if (skip_indexes[i + 1] - skip_indexes[i] > 2) { //if there are at least two elements between consecutive runs, then sort what is in between
                insertionSort(arr, skip_indexes[i] + 1, skip_indexes[i + 1] - 1);
            }
            else if (skip_indexes[i+1] == 0){ //if we are at the end of the last run
                if(skip_indexes[i] < arr.length-2){ //if there are at least two elements left unsorted after the end of the last run
                    insertionSort(arr, skip_indexes[i]+1, arr.length-1);
                }
            }
        }//for
    }

    public static void insertionSort(int[] a, int start, int end) { //start is the start index of the sub-array. end is the end index

        int sorted = start; //index of the last element in the sorted array, initialized to start at the given starting point
        int tempval = 0; //default value set to 0
        int counter = 0;

        while (sorted < end) { //goes through until the given end is reached (this is not necessarily the end of the array)
            if (a[sorted + 1] >= a[sorted]) { //if the next element is greater than the last element of the sorted array, move the marker one to the right.
                sorted++;
            } else {

                tempval = a[sorted + 1];
                counter = sorted;
                while (a[counter] > tempval) {
                    a[counter + 1] = a[counter];

                    if (counter == start)
                        break;
                    else if (a[counter - 1] < a[counter] && a[counter - 1] < tempval)
                        break;
                    else
                        counter--;
                }
                a[counter] = tempval;
                sorted++;
            }
        }
    }

    public static void find_runs(int[] arr) {
        int i = 0;
        int check = 0;
        int skip_track_index = 0;
        for (i = 0; i < arr.length - 1; i++) { //go through arr
            check = i; //this is the first thing that is looked at as if it were the first element of the run (it might be, it might not)
            while (arr[check] <= arr[check + 1]) { //compares check to the next one
                check++; //increase check, to be able to check the next one
                if (check >= arr.length - 1)
                    break;
            }
            if ((check - i) + 1 >= run_size) {   //if run of sufficient length is found
                if(check == i){
                    skip_indexes[skip_track_index++] = i;
                    skip_indexes[skip_track_index++] = check + 1; //why check + 1?
                }
                else {
                    skip_indexes[skip_track_index++] = i;
                    skip_indexes[skip_track_index++] = check;
                }
            }
            i = check;  //skip over run
        }
    }

    public static void merge(int[] arr, int left, int right, int mid) {
        //uses the merge part of mergesort

        int l = skip_indexes[left];//sets the low to the index of arr given by the element in skip_indexes
        int m = skip_indexes[mid]; //sets the mid to the index of arr given by the element in skip_indexes
        int r = skip_indexes[right];//sets the high to the index of arr given by the element in skip_indexes

        if(r==0||m==0)
            return;
        int sizeL = m - l; //size of left array
        int sizeR = r - m + 1; //size of right array

        int[] newL = new int[sizeL]; //create new left array
        int[] newR = new int[sizeR]; //create new right array

        //add the values into the new array
        for (int i = 0; i < sizeL; i++) {
            newL[i] = arr[l + i];
        }

        for (int j = 0; j < sizeR; j++) {
            newR[j] = arr[m + j];
        }

        //Merging the two arrays
        int i = 0;
        int j = 0;
        int k = l;
        while (i < sizeL && j < sizeR) {
            if (newL[i] < newR[j]) {
                arr[k] = newL[i]; //add the left value into the array
                i++;
            } else {
                arr[k] = newR[j]; //add the right value into the array
                j++;
            }
            k++;
        }

        while (i < sizeL) {
            arr[k] = newL[i];
            i++;
            k++;
        }
        while (j < sizeR) {
            arr[k] = newR[j];
            j++;
            k++;
        }
    }

    public static void helpMerge(int[] arr) {

        while (skip_indexes[1] != arr.length - 1) {//when the second element is the last index, then the array has been sorted
            int[] newsk = new int[skip_indexes.length];
            int i = 0;
            int index = 0;


            while (skip_indexes[i] != 0 || skip_indexes[i + 1] != 0) { //second condition must be there to be able to start with skip_indexes[i] = 0

                if(skip_indexes[i+2] == 0){//if the last run doesn't have anything to merge with, just add it to the new skip index array, and merge it in the next round
                    newsk[index++] = skip_indexes[i];
                    newsk[index] = skip_indexes[i+1];
                    break;
                }
                int l = i;
                int r = i+3;
                int m = i+2;
                merge(arr, l, r, m);
                newsk[index] = skip_indexes[i];
                newsk[index + 1] = skip_indexes[i + 3];
                index += 2;
                i += 4;
            }

            skip_indexes = newsk; //have skip_indexes point to the new list of run indexes

        }//while 1
    }

}