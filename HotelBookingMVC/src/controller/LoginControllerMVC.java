//package controller;
//
//import java.sql.SQLException;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Button;
//import model.database.AccountsDB;
//import model.database.HotelEmployeesDB;
//import model.database.UsersDB;
//import model.users.Users;
//import view.LoginView;
//import view.SceneChanging;
//
//public class LoginController {
//	private static Users user = null;
//	
//	public static void setUser(String name, String phone, String email, String password) {
//		user.setEmail(email);
//		user.setName(name);
//		user.setPassword(password);
//		user.setPhoneNumber(phone);
//	}
//	
//	public static void setUser(Users user){
//		LoginController.user=user;
//	}
//	
//	public static Users getUser() {
//		return user;
//	}
//	
//	public static void signOut() {
//		user = null;
//	}
//	
//	public static LoginView loginView;
//	
//	public LoginController(LoginView view) {
//		loginView=view;
//	}
//
//	public void init() {
//		// --------------------------------------------- init signUpButton
//		 Button signUpButton= loginView.getSignUpButton();
//		 signUpButton.setOnAction(new EventHandler<ActionEvent>() { 
//				@Override
//				public void handle(ActionEvent event) {
//			    	  new SceneChanging().changeScene(event, "SignUp.fxml");
//				}  
//			 });
//		 loginView.setSignUpButton(signUpButton);
//		// --------------------------------------------- init signUpButton
//		 Button signInButton= loginView.getSignInButton();
//		 signInButton.setOnAction(new EventHandler<ActionEvent>() { 
//				@Override
//				public void handle(ActionEvent event) {
//					try {
//						LoginController.signInButtonOnAction(event);
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}  
//			 });
//		 loginView.setSignInButton(signInButton);
//		//
//	}
//	
//	//---------------------------------------------------------------------------
//	public static void signInButtonOnAction(ActionEvent event) throws SQLException {
//		// type =1 -> user,=2 -> hotel employees
//		if (loginView.getAccountName().getText().trim().isEmpty() || loginView.getPassword().getText().trim().isEmpty()) {
//			loginView.setAlertLabel("Please fill in the blank");
//			System.out.println("abc");
//			return;
//		}
//		
//		if (!loginView.getUserType().isSelected() && !loginView.getEmployeeType().isSelected()) {
//			loginView.setAlertLabel("Please choose a user type");
//			System.out.println("abc2");
//			return;
//		}
//		
//		int userID = AccountsDB.checkPassword(loginView.getAccountName().getText(),loginView.getPassword().getText());
//
//		if (userID == -1) {
//			loginView.setAlertLabel("Wrong user name or password");
//			System.out.println("abc3");
//			return;
//		}
//
//		if (loginView.getEmployeeType().isSelected()) {
//			user = HotelEmployeesDB.queryEmployeeInfo(userID);
//			user.setUsername(loginView.getAccountName().getText());
//			user.setPassword(loginView.getPassword().getText());
//			new SceneChanging().changeScene(event, "HotelInfo.fxml");
//		}
//		UsersDB usersDB = new UsersDB();
//		user = (Users)usersDB.queryInstance(userID);
//		user.setPassword(loginView.getPassword().getText());
//		user.setUsername(loginView.getAccountName().getText());
//		new SceneChanging().changeScene(event, "Filter.fxml");
//	}
//}
