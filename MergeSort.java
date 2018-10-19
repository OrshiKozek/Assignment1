public class MergeSort implements  SortingAlgorithm{

    @Override
    public void sort(int[] arr) {
        int l = 0;
        int r = arr.length -1;
        sortHelp(arr, l, r);
    }

    public void sortHelp (int[] arr, int l, int r){
        if (l < r) {
            int m = (l + r) / 2; //generate the midpoint
            sortHelp(arr, l, m);//left side
            sortHelp(arr, m+1, r);//right side
            merge(arr, l, m, r);
        }
    }

    public void merge(int[] arr, int l, int m, int r){
        int sizeL = m - l + 1; //size of left array
        int sizeR = r - m; //size of right array

        int[] newL = new int[sizeL]; //create new left array
        int[] newR = new int[sizeR]; //create new right array

        //add the values into the new array
        for (int i = 0; i < sizeL; i++){
            newL[i] = arr[l+i];
        }
        for (int j = 0; j <sizeR; j++){
            newR[j] = arr[m+ 1 + j];
        }

        //Merging the two arrays
        int i = 0;
        int j = 0;
        int k = l;
        while (i < sizeL && j < sizeR){
            if (newL[i] < newR[j]){
                arr[k] = newL[i]; //add the left value into the array
                i++;
            }
            else{
                arr[k] = newR[j]; //add the right value into the array
                j++;
            }
            k++;
        }

        while (i < sizeL){
            arr[k] = newL[i];
            i++;
            k++;
        }
        while (j < sizeR){
            arr[k] = newR[j];
            j++;
            k++;
        }
    }


}
