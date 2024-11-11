# The Part 3 Project 

This project makes up the development and testing portion of my year long undergraduate University dissertation project. 

The project involved planning, research, design, development, testing and evaluation of a modern Android “routine management” application, utilizing digital interrupts and gamification techniques to engage a user, encouraging them to build healthy routines. 

For the planning, research, design and evaluation portions of the project, the project report can be found [here](Exports/Final%20Report.pdf).

## Decision Overview
For easy reading, I have given a brief overview of some of the decisions made in the project. A more in depth explanation can be found within the project report.

### Kotlin
Despite prior personal experience with Java, **Kotlin** was chose as the language for the project. It has a variety of significant language features that Java lacks, such as type inference and null-safety. Kotlin is also the industry standard for Android application development - Google announced in 2019 that Android development
will be Kotlin first.

### Jetpack Compose 
Kotlin also allows for Jetpack Compose to be used for the project's User Interface toolkit. 
Compose was chosen over other UI toolkits, such as Views, because of it's modern, declarative nature.
A declerative UI allows for a UI element's behaviour to be intuitively defined by the element, instead of behaviour being created via mutations of the element. 
This made development significantly more efficient, with less boilerplate code.  
