public class MySort implements SortingAlgorithm{

    private static int[] skip_indexes = new int[35];
    private static int run_size = 3;
    private static boolean first_round = true;


    @Override
    public void sort(int[] a) {
        int[] array = new int[]{6, 4, 3, 5, 7, 1, 6, 4, 2, 6, 8, 9, 2, 5, 7, 1, 4, 3, 8, 9, 13, 2, 5, 4};//, 3, 7, 1};

        printArray(array);
//
        System.out.println();
        find_runs(array);
        printArray(skip_indexes);

        sort_extras(array);
        printArray(array);
        run_size = 1;
        find_runs(array);
        printArray(skip_indexes);
        helpMerge(array);
        printArray(array);
        printArray(array);

    }

    /////////////////////////////////

        public void sort_extras(int[] arr) {
            if (skip_indexes[0] != 0) { //if the first run does not start at the beginning of the array
                insertionSort(arr, 0, skip_indexes[0] - 1); //start at 0, and end at the element before the start of the 1st run
            }

            for (int i = 1; i < skip_indexes.length; i++) {
                if (skip_indexes[i+1] == 0) {//if a 0 appears in the skip indexes, then there are no more run indexes in the skip_indexes array; the rest of the array is all 0s
                    insertionSort(arr, skip_indexes[i - 2] + 1, arr.length - 1);
                    break; //since i starts at 1, this will not break prematurely
                } else {
                    if (i < skip_indexes.length - 1) { //still within bounds, and i is not 0 ie not an 'empty' element of the array
                        insertionSort(arr, skip_indexes[i] + 1, skip_indexes[i + 1] - 1);
                        i++;
//                    printArray(arr);
                    } else if (skip_indexes[i] == arr.length - 2) { //if the remaining unsorted part of the array is only 1 element, don't bother
                        break;
                    } else
                        insertionSort(arr, skip_indexes[i] + 1, arr.length - 1);
//                    printArray(arr);
                    //            if (i+1 < skip_indexes.length)
                    //                i+=2;//only add an extra to i if i+1 is still within bounds
                }
            }
        }

        public void insertionSort(int[] a, int start, int end) { //start is the start index of the sub-array. end is the end index

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
//                 printArray(a);
                }
            }
        }

        public void printArray(int[] array) {
            for (int item : array) {
                System.out.print(item + "  ");
            }
            System.out.println();
        }

        public void find_runs(int[] arr) {
            int i = 0;
            int check = 0;
            int skip_track_index = 0;
            for (i = 0; i < arr.length - 1; i++) { //go through arr
                check = i; //this is the first thing that is looked at as if it were the first element of the run (it might be, it might not)
                while (arr[check] < arr[check + 1]) { //compares check to the next one
                    check++; //increase check, to be able to check the next one
                    if (check >= arr.length - 1)
                        break;
                }
                if ((check - i) + 1 >= run_size) {
                    //make sure that the run is the necessary length- ie 3+ --> for the run to be at least
                    // 3 elements, the difference between the start index and the end index must be at least 2- ie runsize - 1
//                skip_indexes[skip_track_index++] = i-1;
//                if (i!=0&&i!=1&&i - skip_indexes[skip_track_index-1]  != 1) {
////                    skip_indexes[skip_track_index++] = 0; //sets next element of skip_indexes to be 1 greater than the index before it
//                    int next = skip_indexes[skip_track_index] + 1;
//                    skip_indexes[skip_track_index++] = next;
//                    skip_indexes[skip_track_index++] = i-1;
                    skip_indexes[skip_track_index++] = i;
                    skip_indexes[skip_track_index++] = check;
                }
                i = check;
                if (i == arr.length - 2){// && first_round){
                    if(arr[check+1] == arr[arr.length-1]) {
                        int last = skip_indexes[skip_track_index-1]+1;
                        skip_indexes[skip_track_index++] = last;
                        skip_indexes[skip_track_index] = arr.length - 1;
                    }
                    first_round = false;
                    break; //if the next index we look at is the last element in the array, break
                }
            }
        }

        public void merge(int[] arr, int left, int right, int mid) {
            //uses the merge part of mergesort

            int l = skip_indexes[left];//sets the low to the index of arr given by the element in skip_indexes
            int m = skip_indexes[mid]; //sets the mid to the index of arr given by the element in skip_indexes
            int r = skip_indexes[right];//sets the high to the index of arr given by the element in skip_indexes

            int sizeL = m - l; //size of left array
            int sizeR = r - m + 1; //size of right array

            int[] newL = new int[sizeL]; //create new left array
            int[] newR = new int[sizeR]; //create new right array

            //add the values into the new array
            for (int i = 0; i < sizeL; i++) {
                newL[i] = arr[l + i];
            }
            printArray(newL);
            for (int j = 0; j < sizeR; j++) {
                newR[j] = arr[m + j];
            }
            printArray(newR);
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

        public void helpMerge(int[] arr) {

            while (skip_indexes[1] != arr.length - 1) {//when the second element is the last index, then the array has been sorted
                int[] newsk = new int[skip_indexes.length];
                int i = 0;
                int index = 0;

                while (skip_indexes[i] != 0 || skip_indexes[i + 1] != 0) { //second condition must be there to be able to start with skip_indexes[i] = 0
                    int l = i;
                    int r = i+3;
                    int m = i+2;
                    merge(arr, l, r, m);
                    newsk[index] = skip_indexes[i];
                    newsk[index + 1] = skip_indexes[i + 3];
//                printArray(arr);
                    index += 2;
                    i += 4;
                }
                System.out.print("array: ");
                printArray(arr);
                System.out.print("skip_indexes: ");
                printArray(newsk);
                skip_indexes = newsk; //have skip_indexes point to the new list of run indexes

            } //class
        }
    }