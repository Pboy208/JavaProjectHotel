package database;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class Threads implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
            int count = 10;
            for (int i = count; i > 0; i--) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.out.println("time out sss");
        }
		}).start();
	}
}