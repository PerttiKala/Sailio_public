This application was a school project for Software Design -course and was made in a group of four.
I was responsible for following parts:
    -Made class diagrams 
    -Wrote sections (to the Documentation): Self-evaluation (partially), Description of components, Thrid-party components, Potential issues 
    -Implemented fetching and displaying of WeatherData 
    -Implemented functionality that takes parameters from GUI to data fetchers 
    -Added select visualization 

Tuni virtual desktop WINDOWS:
Setting the environment
1. Launch TUNI Windows Virtual Desktop
2. On command line run `setx JAVA_HOME "C:\Apps\Java\OpenJDK17_x64"` to set your user's JAVA to a JDK.
3. Restart command line console to apply changes.

Running the application (Instructions apply Git Bash)
1. Open command line 
2. Change drive to C drive with "cd /c" command.
3. Navigate to Users/your_user_shortname.
4. Create a folder to a location of your choosing and clone the git repo. 
5. Run application with `./start.sh` command on command line in "demo" directory.
6. App should be compiled and started
7. Or you can use commmand `./mvnw clean javafx:run`

The JAVA_HOME needs to be changed as the virtual desktop defaults to JRE.
Instructions have been tested with C drive, may or may not work with other drives. 
Windows is the main environment and the functionality is guaranteed with Windows. 
LINUX is offered as an option.

Tuni virtual desktop LINUX:
1. Create new folder and clone git repo.
2. Run application with `sh start.sh` command on command line in "demo" directory.
3. Or you can use commmand `sh mvnw clean javafx:run`

No set up should be required on LINUX. 
