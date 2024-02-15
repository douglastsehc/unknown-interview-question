# CIBC Java Quant Dev Coding Quiz

## Prerequisite

- Java 20
- Maven 3.9.6
- Libraries in pom.xml

## Requirements

- Please watch CibcJavaQuantDevCodingQuiz.pdf

## Assumption

### Question 1 RandomGen

- It will only run the nextNum() in Integer.MAX_VALUE times
- parameter element in randomNums must be an integer
- parameter element in randomNums will not have repeated number
- parameter element in probabilities will not lower than or equal to 0
- parameter element in probabilities will have precision with 0.00001
- the probability in probabilities will cumulate to get the respective number
- the probabilities sum is always 1

### Question 2 KingAndKnight

- The maximum of the parameter days is Integer.MAX_VALUE
- will throw errors if parameter days is negative

## Implementation

### Question 1 RandomGen

The solution is I need to count the number of nextNum() based on the probabilities based on random number respectively
As it is a float solution

I created a helper class called **MathUtills** to handle float checking, for example to see if it is equal, greater than
or less than

For the randomGen class.

The constructor takes two arrays as parameters - randomNums (an array of integers representing possible outcomes)
and probabilities (an array of floats representing the corresponding probabilities for each outcome).

The constructor ensures that the input arrays are not null, have consistent lengths, and have at least one element.
Cumulative probabilities are calculated for efficient selection during random number generation.

Validation are performed to ensure that probabilities are within valid ranges and do not violate mathematical
constraints.
A check is performed to ensure that random numbers do not repeat in the provided array.

**Random Number Generation**

The nextNum method generates a random number based on the initialized probabilities. It uses a binary search algorithm
to efficiently select a random number according to the cumulative probabilities.

**Access to Cumulative Probabilities**

The getCumulativeProbabilities method provides access to the cumulative probabilities calculated during initialization.
Error Handling:

The class performs various checks during initialization to ensure that the provided inputs are valid. If any check
fails, an IllegalArgumentException is thrown with an appropriate error message.

**Testcase file**

The remaining counting is implement in the RandomGenTests class, as it is used for different situation.
create a map as a counter to store the times of random number happen in  **nextNum()**
loop the nextNum() everytime and update the value to the hashmap / concurrent hashmap

At last, I get the total times of each value to see the sum is the same as the times of calling nextNum

### Question 2 KingAndKnight

The solution is I based on the question theory, first day to hire a knight is one coin, second , third day is two coin
per day
for 4,5,6 day is 3 coin per day.

The calculation method takes an integer days as input and calculates the total number of coins earned based on a
specific pattern. The method follows the following logic:

1.If the input days is less than 0, an IllegalArgumentException is thrown with the message "days cannot be smaller than
0."

2.If days is equal to 0, the method returns 0.

3.For positive values of days, the method uses a loop to calculate the total number of coins earned. It initializes
variables coins, daysCounter, coinPerDay, and currentDay.

4.Inside the loop, the variable coinPerDay starts at 1, and the loop continues as long as daysCounter is less than or
equal to the input days. In each iteration, currentDay is updated by subtracting the current value of coinPerDay if
currentDay is greater than or equal to coinPerDay. Otherwise, the loop breaks.

5.Inside the loop, the variable coins is updated by adding the product of coinPerDay * coinPerDay.

6.After the loop, the remaining days (currentDay) are multiplied by the current value of coinPerDay and added to the
total number of coins (coins).

7.The final result is the total number of coins earned, and it is returned as a long value.

In summary, the method calculates the total number of coins earned based on a specific pattern where the number of coins
earned each day is determined by the triangular number sequence (1, 3, 6, 10, ...) until the input days are exhausted.

## Performance

### Question 1 RandomGen

Time Complexity:
Initialization (init method):

The loop in the init method iterates over the length of the probabilities array, performing constant-time operations
within each iteration.

Therefore, the time complexity of the initialization is O(n), where n is the length of the probabilities array.
Random Number Generation (nextNum method):

The nextNum method uses a binary search to find the appropriate random number based on the target probability.
The time complexity of the binary search is O(log n), where n is the number of possible outcomes.

For many trials to call the nextNum() and the initialization:
O(T)*O(log N) + O(I) where T is the number of trials to call the nextNum(), N is the nextNum() performance and I is the
initialization

Space Complexity:
Arrays and Maps:

The space complexity is primarily influenced by the size of the arrays and maps used to store data.
The space complexity is O(n), where n is the length of the probabilities array. This accounts for the space used by
randomNums, probabilities, cumulativeProbabilities, and the randomNumberCounterMap.

The use of a Hashmap for checking duplicate random numbers adds additional space complexity.
In the worst case, where all random numbers are unique, the space complexity of the Hashmap check is O(n), the size of
the array.

### Question 2 KingAndKnight

Time Complexity:
The core logic is a loop that iterates over the number of days, and in each iteration, it increments the coinPerDay and
updates the currentDay.
The loop runs until daysCounter exceeds the specified number of days.
The time complexity of the loop is O(days), where days is the input parameter.

Space Complexity:
The space complexity is minimal, with only a few variables (coins, daysCounter, coinPerDay, currentDay) being used.
The space complexity is O(1), indicating constant space usage.

## Alternative Solution

- I should try to implement the concurrent approach such as executorService or just Thread, runnable or ForkJoinPool
  to see will it perform faster when testing, although it is not a CPU-bound operation.
- Try to find out a better formula in second question.

## Build, Run Tests and Read the Coverage report

### To build the project use a build tool like Maven. Execute the following commands:

```
mvn clean install
```

### Test the testcase

```
mvn test
```

### Watch the Report

The html-report with test coverage will be crated after you use build or test the testcase, and it will locate in
./target/site/jacoco/