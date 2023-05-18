# Cram Buddy 1.2a
Welcome to the first markdown file of this months of brain-suffering project! I will be covering a few notable things that you may want to know and I can explain.

## Requirements to run this program
<p>Here are list of things that you should have on your computer IF you WISH TO RUN THIS PROJECT!</p>

### All websites/tools I used (at least what I can find for now XD): 
##### PocketSphinx: https://cmusphinx.github.io/ , Github: https://github.com/cmusphinx/pocketsphinx
##### Sphinx Language Model: http://www.speech.cs.cmu.edu/tools/lmtool-new.html
##### Python: https://www.python.org/downloads/
##### C Make: https://cmake.org/install/
##### SphinxTrain (Training Model, optional): https://github.com/cmusphinx/sphinxtrain
##### Homebrew (Mac OS): https://brew.sh/
##### Stack Overflow: https://stackoverflow.com/questions/4535208/pocketsphinx-adding-words-and-improving-accuracy
##### Python speech_recognition library install via IntelliJ code editor
##### Perl (May be required): https://www.perl.org/
##### Audio file extention converter to .m4a (Sphinx supported file extension): https://cloudconvert.com/m4a-converter
##### Stack Overflow help to install pocketsphinx: https://stackoverflow.com/questions/14890546/installing-pocketsphinx-on-a-mac-os-x

### Mac OS command to install pocketsphinx after Python 3 is installed: pip3 install pocketsphinx

## Java and Python incorporated project
In this project, I have built a brief bridge between Java to Python
<p>Why? Cause I hate java binding and all that pain of java set up when Python is a lot EASIER!
</p>
<p>How did I make this possible? Let's break this down! (to the best of my explaining ability</p>

#### When running the project, YOU MUST DO THE FOLLOWING STEPS CORRECTLY TO AVOID PYTHON FILES RUN UNDER THE JAVA SDK!
#### IntelliJ: Go to Project Structure, go to Modules, and remove all folders in there!
#### Next, select "import module", select the python folder FIRST, configure it with the Python SDK and complete the remaining steps. When you finished this, click on "Apply" and then "OK"
#### After that, select "import module" again, select the "CramBuddyProgram" folder, and proceed to do the step and configure with Java SDK. 
#### Now when you done all of the above steps, go to Project Structure again, and IF the python folder is shown with Java SDK, just change it into Python SDK/Interpreter that should come with the Python that you downloaded. Click on Apply, and OK button. 
#### You should be able to run the program, if you have followed the above steps completely correct unless any new technical issues came up. As this is extremely hard to set up on a new computer environment to run on.

## How did I make the JList work?
#### 1. the public variables are necessary to initialize the variables needed to use in later stages, like the files = New File [0] is part of the necessary setup to avoid return null/initialization issue
#### 2. I made the method getNoteDirectory return as a string variable, since the key variable noteDirectory is a JFileChooser variable and I needed it to be used as a String variable later on
#### 3. Added annotations from line 215 to 245 as the chunk is directly addressing fileList which I have made a JList out of it at line 197.
#### The overall logic is basically like, create the initial variable noteDirectory, create a method called getNoteDirectory to ask for an Output folder as the "Principal directory" to save, edit, and read all the .txt files as "Notes" for users. Later on, at HomePage, create a JList on the JFrame with bounds big enough size to fit in the frame. After that, utilize the Arrays (Since the folder would have multiple files, and to store them is by using Arrays) to read if there are any .txt files in there at all, avoid it return null, and also to pick out all the .txt files to store inside of the Array and JList will do its job to list them all. When user want to edit one, they have to select, and the selected file on the list will have the varaiable passed onto the EditPage where the user can access the file content and save when they are done. 
#### For MacOS users, make sure to use chmod command in the terminal to give the program access to your program folder. I used chmod ugo=rwx CramBuddy1.2 (user group others with given access to read write and all modify access, easy and instant solve the issue if it ever throws you NullPointerException Error. If there isn't any permissions for the program to access the entire folder with Output folder contained within, everything will be null valued and it will become part of reason why NullPointerException is shown.)

