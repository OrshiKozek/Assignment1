# Assignment1

SortingFactory, SortingAlgorithm is a slightly modified version of a class and an interface by the same names, respectively.
Tester is slightly modified from the class Practice05Test. All three files were originally written by Professor David Guy Brizan.

I was unable to fully complete the hybrid sorting algorithm. When given certain data (ie the array that I had tested it with),
everything worked fine. However, with a different set of data, it does not sort the array. There is either an index out of bounds
or a negative index exception thrown, or an infinite loop created that doesn't allow the sorting to be completed. I still need to work on this.

I used Insertion sort to sort the rest of the array that was not in a run. I chose Insertion sort because it is faster for smaller arrays,
such as the elements of the array between two runs. I find the logic of Insertion sort to be on the easier side, which is also why I chose Insertion sort.

I don't know if this is true since I was unable to run the whole tester program, but my hypothesis is that the hybrid sorting algorithm
will have roughly the same wall-clock time as normal Merge sort, though possibly slightly faster. I think this is because the algoritm does
not have to split and sort all the individual elements as in merge sort, because they are already sorted. They only have to be merged, so overall
it is faster. This might change depending on if I am able to fix the code and run the program correctly.