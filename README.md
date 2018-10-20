# Assignment1

SortingFactory and SortingAlgorithm are slightly modified versions of a class and an interface by the same names, respectively.
Tester is slightly modified from the class Practice05Test. All three files were originally written by Professor David Guy Brizan.

I used Insertion sort to sort the rest of the array that was not in a run. I chose Insertion sort because it is faster for smaller arrays,
such as the elements of the array between two runs. I find the logic of Insertion sort to be easier to follow than most other sorting algorithms,
which drove me to choose it over the others.

My implementation of the convoluted, modified merge sort was, as I expected, much slower than the conventional Merge sort. However, I was shocked
by how much slower it was. Nevertheless, it makes sense, because my algorithm performs many more processes than normal Merge sort. Merge sort just splits
the array into little arrays, orders and merges them. My algorithm goes through the array, sorts the little pieces, goes through it again, and only then
does it merge the sorted sub-arrays. It is a much more roundabout way to sort the array, and definitely not efficient at all.