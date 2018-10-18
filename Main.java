public class Main{

    public static int[] skip_indexes = new int[15];
    public static int run_size = 3;

    public static void main(String []args){
        int[] array = new int[]{4, 6, 3, 5, 7, 1, 6, 4, 2, 6, 8, 9, 2, 5, 7, 1, 4, 3, 8, 9, 13, 2, 4, 3};

        printArray(array);
        for(int i = 0; i < array.length; i++)
            if(i < 10)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");
        System.out.println();
        find_runs(array);
        System.out.println();
        printArray(skip_indexes);

//        sort_extras(array);
//        printArray(array);
//        helpMerge(array);
//        printArray(array);
//        sortLasts(array);
//        printArray(array);
    }


    public static void sort_extras(int[] arr){
        if (skip_indexes[0] != 0){ //if the first run does not start at the beginning of the array
            insertionSort(arr, 0, skip_indexes[0] - 1); //start at 0, and end at the element before the start of the 1st run
        }

        for (int i = 1; i < skip_indexes.length; i++) {
            if (skip_indexes[i] == 0) {//if a 0 appears in the skip indexes, then there are no more run indexes in the skip_indexes array; the rest of the array is all 0s
                insertionSort(arr, skip_indexes[i - 2] + 1, arr.length - 1);
                break; //since i starts at 1, this will not break prematurely
            }
            else {
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

                    if (counter == start)
                        break;
                    else if(a[counter-1] < a[counter] && a[counter-1] < tempval)
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

    public static void printArray (int[] array){
        for (int item: array) {
            System.out.print(item + "  ");
        }
        System.out.println();
    }

    public static void find_runs(int[] arr) {
        int i = 0;
        int check = 0;
        int skip_track_index = 0;
        for (i = 0; i < arr.length-1; i++) { //go through arr
            check = i; //this is the first thing that is looked at as if it were the first element of the run (it might be, it might not)
            while (arr[check] < arr[check + 1]) { //compares check to the next one
                check++; //increase check, to be able to check the next one
                if(check >= arr.length-1)
                    break;
            }

            if (check - i >= run_size-1) { //make sure that the run is the necessary length- ie 3+ --> for the run to be at least
                // 3 elements, the difference between the start index and the end index must be at least 2- ie runsize - 1
//                skip_indexes[skip_track_index++] = i-1;
                skip_indexes[skip_track_index++] = i; //set the next element of the skip_indexes array to be the index of the start of the run.
                skip_indexes[skip_track_index++] = check; //this is the last index of the given run
//                skip_indexes[skip_track_index++] = check+1;
//                printArray(skip_indexes);
            }

//            else if(check - i == 0)
//                skip_indexes[skip_track_index++] = i; //sets the first element to 0 if the run doesn't start at 0 by itself

            i = check;
            if(i == arr.length-2)
                break; //if the next index we look at is the last element in the array, break
        }
//        if(skip_indexes[skip_track_index] == arr.length-1)
//            skip_indexes[skip_track_index] = arr.length-1;
    }

    public static void merge(int[] arr, int left, int right, int mid) {
        //uses the merge part of mergesort
        int l = left;//skip_indexes[left];//+1; //was 0 for first pair skipIndexs[1] + 1 for the second set
        if (l != 0)
            l = skip_indexes[left] + 1;

        int m = 0; //2
        int r = 1;
        if (skip_indexes[2] ==0 && right > skip_indexes[1]) { //when there are two 'runs' left, only the first two indexes are important- 2nd = end of the first run
            r = right;
            m = mid; //change value of m to whatever is given in the last, separate merge, because otherwise it might be out of bounds
        }
        else if(skip_indexes[right] == 0) {
            r = arr.length-1;
            m = skip_indexes[mid];
        }
        else {
            r = skip_indexes[right];//4)
            m = skip_indexes[mid];
        }
        int sizeL = m - l; //size of left array
        int sizeR = r - m +1; //size of right array
//        System.out.println("l: " + l+" r: " + r +" m: "+ m +" sl: " +sizeL +" sr: "+ sizeR);

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

    public static void helpMerge(int[] arr){

        while(skip_indexes[1] != arr.length-1) {//when the second element is the last index, then the array has been sorted
            int[] newsk = new int[skip_indexes.length];
            int i = 0;
            int index = 0;
            if (skip_indexes[i] == 0) {
                merge(arr, i, i + 3, i + 2);//0 3 2
                newsk[index] = 0;
                newsk[index + 1] = skip_indexes[i + 3];
                printArray(arr);
                printArray(newsk);
                i += 4;
                index += 2;
            } else {
                merge(arr, i, i + 1, i); //0 1 0
                newsk[index] = i;
                newsk[index + 1] = i + 4;
                printArray(arr);
                printArray(newsk);
                i += 1;
                index += 2;

            }
            while (skip_indexes[i] != 0 && skip_indexes[i + 1] != 0) {// && skip_indexes[i+2] != 0 && skip_indexes[i+3] != 0){//it will go through the skip_indexes array until it reaches the 0s
                merge(arr, i, i + 2, i + 1);
                newsk[index] = skip_indexes[i] + 1;
                newsk[index + 1] = skip_indexes[i + 2];
                printArray(arr);
                i += 4;
                index += 2;

            }
//            change_indexes(arr);
            skip_indexes = newsk;
            if (skip_indexes[2] == 0) {
                System.out.println("last");
                break; //if the indexes are the first two elements, and the rest are 0s, then the while loop can end
            }
        }

    }

//    public static void change_indexes(int[] arr){
//        int[] newSkipIndex = new int[skip_indexes.length];
//        int i = 1;
//        if (skip_indexes[0] != 0){
//            newSkipIndex[0] = 0;
//        }
//        else
//            newSkipIndex[0] = skip_indexes[0];
//
//        while (skip_indexes[i] != 0){
//            newSkipIndex[i] = skip_indexes[i];
//            newSkipIndex[i+1] = skip_indexes[i] + 1;
//            i+=2;
//        }
////        newSkipIndex[i] = arr.length-1;
//        skip_indexes = newSkipIndex;
//        System.out.println("skip indexes:");
//        printArray(skip_indexes);
//    }

    public static void sortLasts(int[] arr){
        if (skip_indexes[1] + 1 <= arr.length-1) {//if it is not the last element of the array,
            merge(arr, 0, arr.length-1, skip_indexes[1]+1); //merge the sorted part with the last element
            System.out.println("SORTED");
            printArray(arr);
        }
        else {
            System.out.println("skp");
            printArray(skip_indexes);
            System.out.println("arr");
        }
    }



} //class