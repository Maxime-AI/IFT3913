# IFT3913 TP1

* This program analyses java code and generates csv files containing different metrics such as LOC, CLOC and
  cyclomatic complexity, with the classes and methods of the files in the source folder.
 
 * @author : Maxime Lechasseur
 * @author : Han Zhang
 
 Glossary
===========

 - Commentary lines of code(CLOC) : The number of commentary lines of code found in a file.  

 - Lines of code(LOC) : The number of non-empty and non-commentary lines of code, also known as lines of source code(SLOC), found in a file.

 - Comments density(DC) : The ratio of comments(CLOC) per lines of code(LOC) in a program file.
 
 - Cyclomatic Complexity(CC) :  "is a software metric used to indicate the complexity of a program. It is a quantitative measure of the number of linearly independent paths through a program's source code. It was developed by Thomas J. McCabe, Sr. in 1976." Source: https://en.wikipedia.org/wiki/Cyclomatic_complexity
 
 - The Weighted Method Count(WMC) : "is a good indicator of how much time and effort is required to modify and maintain this class. The WMC metric is defined as the sum of complexities of all methods declared in a class." 
 Source: https://phpmd.org/rules/codesize.html#:~:text=The%20Weighted%20Method%20Count%20(WMC,methods%20declared%20in%20a%20class.
 
 How to run the project
==========================

Go to our Jar file: 

* Step 1: Open a notepad.exe.

* Step 2: Write : java -jar Example. jar.

* Step 3: Save it with the extension . bat.

* Step 4: Copy it to the directory which has the . jar file.

* Step 5: Double click it to run your . jar file.


Source: https://stackoverflow.com/questions/394616/running-jar-file-on-windows

The classes et methods that are less well commented on the jfreechart project
===============================================================================

* classes
  ========

  
  
       
	1 .Name: XYPlot       | loc: 5068 cloc: 2344 class_DC: 0.46251 wmc: 446 class_BC: 0.001037
  
	2. Name: CategoryPlot | loc: 4630 cloc: 2228 class_DC: 0.48121 wmc: 398 class_BC: 0.001209
  
	3. Name: ChartPanel   | loc: 2766 cloc: 1146 class_DC: 0.4143  wmc: 292 class_BC: 0.001419
  
  Solution (Class): Regarding the classes, we took into account the Weighted Method Count (wmc) as well as the number of
  lines of code in order to assess its quality.
  
* Methods
  ========
  


	
	1. Path: XYPlot/equals_Object       | loc: 131 cloc: 0 method_DC: 0     CC: 57 method_BC: 0
  
	2. Path: CategoryPlot/equals_Object | loc: 115 cloc: 0 method_DC: 0     CC: 52 method_BC: 0
  
	3. Path: PiePlot/equals_Object      | loc: 110 cloc: 1 method_DC: 0.009 CC: 50 method_BC: 1.82E-04

  Solution (Method): Regarding the methods, we took into account the complexity(CC) as well as the number of lines of
  code in order to evaluate the quality of this one.

  Improvement: In terms of classes, it would be important to increase
  the number of comments where the complexity of McCabe or Cyclomatic Complexity(CC)
  increases in the methods or to reduce this complexity, if possible, while
  continuing to comment on operations and changes.

      

