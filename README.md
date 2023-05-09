# Testing the FotBot Server

## Overview

This assignment deals with input partitioning, boundary-value analysis, and control-flow testing; and a bit of mutation analysis.

You are given a specification and a program that implements that specification. The aim of this assignment is to test the program using the different techniques, and to analyse the difference between them.

You are expected to derive and compare test cases, but you are not expected to debug the program.

The assignment is part laboratory exercise because you are expected to write a JUnit driver program to run your test cases automatically. Some exploration may be needed here. The assignment is also part analysis exercise as you are expected to apply the testing techniques to derive your test cases, and to compare them. Finally, the assignment is also part competition, as your solutions to various tasks will be evaluated against all other submissions in a small tournament to measure its effectiveness and completeness.

## Your tasks

### Task 1 -- Equivalence partitioning

Using the specification, apply equivalence partitioning to derive equivalence classes for the following methods in the API: `register`, `update`, and `getSteps`.

### Task 2 -- Junit test driver for equivalence partitioning

Select test cases associated with your equivalence classes, and implement these in the JUnit test driver named `tests/swen90006/fotbot/PartitioningTests.java`. Use *one* JUnit test method for each equivalence class. For each test, clearly identify from which equivalence class it has been selected. Push this script back to you git repository.

### Task 3 -- Boundary-value analysis

Conduct a boundary-value analysis for your equivalence classes. Show your working for this. Select test cases associated with your boundary values.

### Task 4 -- Junit test driver for boundary-value analysis

Implement your boundary-value tests in the JUnit test driver called `test/swen90006/fotbot/BoundaryTests.java`. As before, use *one* JUnit test method for each test input.  Push this script back to you git repository.

### Task 5 -- Multiple-condition coverage

Calculate the coverage score of your two test suites (equivalence partitioning and boundary-value analysis) using *multiple-condition coverage* each the three methods, as well as those methods in `FotBot.java` that they call; that is, `checkUsernamePassword`.

### Task 6 -- Mutation selection

Derive five *non-equivalent* mutants for the FotBot class  using only the nine Java mutation operators in the subject notes. These mutants should be difficult to find using testing. Insert each of these mutants into the files `programs/mutant-1/swen90006/fotbot/FotBot.java`, `programs/mutant-2/swen90006/fotbot/FotBot.java`, etc.

All five mutants must be non-equivalent AND each mutant must be killed by at least one test in your JUnit BoundaryTest script to demonstrate that they are non-equivalent.

## The System: FotBot

A FotBot looks like a wristwatch and includes functions for counting the steps of the wearer. This data is uploaded into a cloud-based system. The FotBot and its cloud application has three main intended features:
1. To record the number of steps a person takes each day.
2. To share information with other FotBot wearers for social reasons; e.g.competitions to see who can take the most steps.
3. To share information with the FotBot company.

Each FotBot's data is stored in the cloud. Each user can set access permissions to their own data. 

A description of the methods for FotBot and its implementation can be found in the file `programs/original/swen90006/fotbot/FotBot.java`.

For simplicity for the assignment, the database is implemented as a Java data structure.

### Building and running the program

The source code has been successfully built and tested on JDK 14 but should also work with some earlier versions of Java.

The file `build.xml` contains an Ant build script, should you choose to use it. The README.md file in the top-level folder has instructions for using this.

There are two JUnit test scripts in `test/swen90006/fotbot`. You will need to modify each of these to complete the tasks. You can run these by compiling and running as a class, but you will need to include the library files in the `lib/` directory on your classpath.

To compile, run:

`ant compile_orig`

To run the test scripts on the original (hopefully non-faulty) implementation, use:

`ant test -Dprogram="original" -Dtest="PartitioningTests"`

or

`ant test -Dprogram="original" -Dtest="BoundaryTests"`

To run a test script on the first mutant, use:

`ant test -Dprogram="mutant-1" -Dtest="BoundaryTests"`

Results for the tests can be found in the `results/` folder.

To clean all class files, run: `ant clean`
