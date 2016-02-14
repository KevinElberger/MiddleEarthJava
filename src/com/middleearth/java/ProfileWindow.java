package com.middleearth.java;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class ProfileWindow {

	JFrame frame;
	protected String credentials;

	public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ProfileWindow window = new ProfileWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
	/**
	 * Create the application.
	 */
	public ProfileWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUpdateYourProfile = new JLabel("Middle Earth");
		lblUpdateYourProfile.setBounds(156, 12, 152, 15);
		frame.getContentPane().add(lblUpdateYourProfile);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			// Close the window if the cancel button was pressed.
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setBounds(307, 244, 117, 25);
		frame.getContentPane().add(btnCancel);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(20, 100, 404, 132);
		frame.getContentPane().add(textArea);
		
		final JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(20, 52, 404, 25);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblUpdateYourProfile_1 = new JLabel("Update Your Profile:");
		lblUpdateYourProfile_1.setBounds(20, 83, 162, 15);
		frame.getContentPane().add(lblUpdateYourProfile_1);
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(20, 30, 70, 15);
		frame.getContentPane().add(lblResult);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(20, 244, 117, 25);
		frame.getContentPane().add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			// If submit button was pressed, submit profile data
			// to the API.
			public void actionPerformed(ActionEvent e) {
				String profileText = textArea.getText();
				String profileContent = "profile=" + profileText;
				
				try {
				    // Create the URL, the request methods and properties, as well
				    // as creating an HttpURL Connection that attaches the authenticated
				    // user's credentials for authentication.
		            URL url = new URL ("http://localhost:8888/api/v1/url/profile");
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            connection.setRequestMethod("POST");
		            connection.setDoOutput(true);
		            connection.setRequestProperty  ("Authorization", "Basic " + credentials);
	            
		            DataOutputStream wr = new DataOutputStream(connection.getOutputStream()); 		           
		            // Send the URL parameters to the API.
		            wr.writeBytes(profileContent);
		            InputStream content = (InputStream)connection.getInputStream();
		            // Use a buffered reader to read the output from the API.
		            BufferedReader in = new BufferedReader (new InputStreamReader (content));
		            String line;
		            while ((line = in.readLine()) != null) {
	            		if(line.contains("response")) {
	            			System.out.println(line);
	            			textArea_1.setText("Your profile has been successfully updated!");
	            		} else {
	            			textArea_1.setText("An error has occurred trying to update your profile.");
	            		}
		            }		           
		        } catch(Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
	}
}