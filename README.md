# Euler-Totient-GPU-Programming

The **Euler totient function**, named after eighteenth-century Swiss mathematician Leonhard Euler, symbolized as Φ(n), is the number of numbers less than the number n that are relatively prime to n. Two numbers are relatively prime if the numbers have no factors in common other than 1.

To determine if two numbers a and b are relatively prime, compute the greatest common denominator (or greatest common factor), gcd(a,b), using the well-known Euclidean Algorithm. If gcd(a,b) = 1, then the largest (and therefore only) common factor of a and b is 1, so a and b are relatively prime. If gcd(a,b) > 1, then a and b share that as a common factor, so a and b are not relatively prime.

### Compiling and Running The Program
* Set the CLASSPATH, PATH, and LD_LIBRARY_PATH variables.
* Compile the Java main program using this command:
```
$ javac Totient.java
```
* Compile the CUDA kernel using this command:
```
$ nvcc -ptx --compiler-bindir=/usr/bin/gcc-4.8 -arch compute_20 -o Totient.ptx Totient.cu
```
* Run the program using this command (substituting the proper command line argument):
```
$ java pj2 Totient <n>
```

### Software Specification

The program runs by typing this command line:
```
java pj2 Totient <n>
```
* `<n>` is the argument of Φ(n); it must be a decimal long integer ≥ 1 (type long).

**If the command line does not have the required number of arguments, if any argument is erroneous, or if any other error occurs, the program prints an error message on the console and must exit.**

