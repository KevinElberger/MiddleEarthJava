package com.middleearth.java;
import com.middleearth.java.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LoginForm {
  
    public static void main(String[] args) {
      new LoginForm();
    }
	  /**
	  * Set up instance variables & URL Parameters for API query.
	 * @throws IOException 
	  * 
	  */
    MainWindow window = new MainWindow();
	  Display display = new Display();
	  Shell shell = new Shell(display);
	  Label label1,label2;
	  Text email;
	  Text password;
	  Text text;
	  private Label lblNewLabel;
	 
	  public LoginForm() {
		  shell.setMinimumSize(new Point(350, 150));
		  shell.setSize(318, 225);
		  shell.setText("Middle Earth Login");
		  shell.setLayout(new GridLayout(2, false));
		  
		  lblNewLabel = new Label(shell, SWT.NONE);
		  lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		  lblNewLabel.setText("Middle Earth");
		  label1=new Label(shell, SWT.NULL);
		  label1.setText("Email: ");
		  
		  email = new Text(shell, SWT.SINGLE | SWT.BORDER);
		  email.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		  email.setText("");
		  email.setTextLimit(30);
		  
		  label2=new Label(shell, SWT.NULL);
		  label2.setText("Password: ");
		  
		  password = new Text(shell, SWT.SINGLE | SWT.BORDER);
		  password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		  System.out.println(password.getEchoChar());
		  password.setEchoChar('*');
		  password.setTextLimit(30);
		
		  Button button=new Button(shell,SWT.PUSH);
		  button.addSelectionListener(new SelectionAdapter() {
		  	@Override
		  	public void widgetSelected(SelectionEvent e) {
		  	}
		  });
		  button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		  button.setText("Submit");
		  button.addListener(SWT.Selection, new Listener() {
		  public void handleEvent(Event event) {
		  String selected=email.getText();
		  String selected1=password.getText();
		  
			  if(selected==""){ 
				  MessageBox messageBox = new MessageBox(shell, SWT.OK |
				  SWT.ICON_WARNING |SWT.CANCEL);
				  messageBox.setMessage("Enter the User Name");
				  messageBox.open();
			  }
			  if(selected1==""){
				  MessageBox messageBox = new MessageBox(shell, SWT.OK |
				  SWT.ICON_WARNING |SWT.CANCEL);
				  messageBox.setMessage("Enter the Password");
				  messageBox.open();
			  }
		  else if(selected != "" && selected1 != "") {
			 String email = selected;
			 String password = selected1;
			 String authString = email + ":" + password;
			 String encodedLogin = Base64.encodeBase64String(authString.getBytes() );
			  
			  try {
				    // Create the URL, the request methods and properties, as well
				    // as creating an Http URL Connection that attaches the authenticated
				    // user's credentials for authentication.
		            URL url = new URL ("http://localhost:8888/api/v1/url/show");
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            connection.setRequestMethod("GET");
		            connection.setDoOutput(true);
		            connection.setRequestProperty  ("Authorization", "Basic " + encodedLogin);
	            
		            InputStream content = (InputStream)connection.getInputStream();
		            // Use a buffered reader to read the output from the API.
		            BufferedReader in = new BufferedReader (new InputStreamReader (content));
		            String line;
		            while ((line = in.readLine()) != null) {
		                System.out.println(line);
		                // Check if authenticated was passed back by the API.
		                if(line.contains("authenticated")) {
		                	shell.dispose();
	                	  window.frame.setVisible(true);
	                	  window.credentials = encodedLogin;
		                }		           
		            }
      	  		  
		        } catch(Exception e) {
			        	// If there was a server-related error, show error window.
			        	MessageBox message3 = new MessageBox(shell, SWT.ICON_ERROR | SWT.CANCEL);
			        	message3.setMessage("Invalid credentials.");
			        	message3.open();
			            e.printStackTrace();
		        	}
		  		}
		  	}
		  });
		  
		  shell.pack();
		  shell.open();
		  
		  while (!shell.isDisposed()) {
			  if (!display.readAndDispatch()) {
				  display.sleep();
		  	  }
		  }
		  	display.dispose();
		  }
}