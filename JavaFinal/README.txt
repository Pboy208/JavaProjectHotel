First,add the java project to your Eclipse

Open the Run Configurations on class controller.Main, move to the Arguments section

Paste this script into the VM arguments
	--module-path "\libary\javafx-sdk-16\lib" --add-modules javafx.controls,javafx.fxml

Open the project build path, move to Libraries section

Click on modulepath and select Add external JARs
	choose the mysql-connector-java-8.0.25.jar in the file mysql-connector-java-8.0.25 in libary file

Click on modulepath and select Add library, select user library and next
Click User Libraries ... and press New... add a name to it (JavaFx)
Select library that you just created and click Add External JARs
Go to \libary\javafx-sdk-16\lib and select all file that end with jar 
Press apply and close

Click on modulepath and select Add library and select user library,add the library that you just created

Press apply and close and now the system is ready

Run the controller.Main to run the application

Please import the database to phpadmin with database name of latestdb to run immediately
You can change the name of the database in \src\model\database\Mysql