# Implementation details

For implementation, I simply followed the tasks:

### Task 1.1 : ```Catalog class``` 

- created the ```Catalog``` class using Singleton Design Pattern
- implemented methods ```addCourse(Course course)``` and ```removeCourse(Course course)```

### Task 1.2 : ```User class``` 

- implemented abstract class ```User class``` from the file
- implemented setters and getters for both ```Parent``` attributes of a ```Student``` class
- created ```UserFactory``` design pattern and implemented it in ```Test.java``` main class.

### Task 1.3 : ```Grade class``` 

- implemented setters and getters for ```Grade class``` 
- implemented ```public Double getTotal()``` by adding the partialScore and examScore
- added Grade comparator the first letter of the father's lastName

### Task 1.4 : ```Group class``` 

- implemented ```Group``` as an ```ArrayList<Student>```
- created the constructors properly

### Task 1.5 : ```Course class``` 
 
- implemented course methods
- created ```PartialCourse``` class and implemented abstract method
- created ```FullCourse``` class and implemented abstract method

### Task 1.6 : ```Observer design pattern```

- implemented required methods
- implemented the Observers as ```Parent class``` and the subject as the instance of the ```Catalog class```

### Task 1.7 : ```Strategy design pattern```

- created BestPartialScore, BestExamScore and BestTotalScore classes implementing ```strategy```
- implemented specific strategies

### Task 1.8 : ```Visitor design pattern```

- implemented the instructor(teacher/assistant) as Element
- implemented ScoreVisitor as Visitor
- in the visit methods the required instructor downloaded the grades into the respective HashMaps
- the ```getGrades``` function returns an ```ArrayList``` of grades from the students enrolled to the specific course
- the ```getStudents``` function returns an ```ArrayList``` of students enrolled to the course
- the ```addExamTeacher```/```addPartialAssistant```/```addExamGrade```/```addPartialGrade``` functions help create the 
HashMap (depending if the ```key``` exists or not)

### Task 1.9 : ```Memento design pattern```

- implemented the memento design pattern by taking a ```Snapshot``` of the grades when calling the ```makeBackup()``` 
function
- reverting to the Snapshop when calling the ```undo()``` function

### Swing GUI
- creating ```JFrame``` elements, adding ```JLabel```, ```JTextField```, ```JScrollPane``` to the frames
- sometimes creating the first frames using the ```setLayout(null)``` option to be able to place every element easier


# How to run :

- simply uncomment the student, instructor and parent frames
- the 1.1 - 1.5 tasks are verified when parsing the json file
- 1.6 is used after filling some grades (after instructor GUI ) and opening the parent page
- 1.7 is tested at the end by uncommenting the indicated lines
- 1.8 is tested in the Instructor GUI when validating the grades
- 1.9 is tested at the end by uncommenting the indicated lines