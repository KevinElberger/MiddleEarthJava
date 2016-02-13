import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.event.ActionEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.JTextPane;
import com.toedter.calendar.JYearChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.components.JSpinField;
import javax.swing.JTabbedPane;

public class MainWindow {

	JFrame frame;
	private JTextField textField;
	protected String credentials;
	JTextField textTitle = new JTextField();
	JTextField textBody = new JTextField();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 751, 527);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterUserName = new JLabel("Enter User Name To Find:");
		lblEnterUserName.setBounds(27, 48, 189, 76);
		frame.getContentPane().add(lblEnterUserName);
		
		JLabel lblMiddleEarth = new JLabel("Middle Earth");
		lblMiddleEarth.setBounds(340, 12, 138, 44);
		frame.getContentPane().add(lblMiddleEarth);
		
		textField = new JTextField();
		textField.setBounds(234, 71, 475, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		final TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(35, 179, 674, 167);
		frame.getContentPane().add(textArea);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(570, 114, 138, 48);
		frame.getContentPane().add(btnSearch);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(227, 360, 36, 15);
		frame.getContentPane().add(lblTitle);
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(281, 404, 285, 79);
		frame.getContentPane().add(textPane);
		
		JLabel lblBody = new JLabel("Body:");
		lblBody.setBounds(224, 404, 50, 15);
		frame.getContentPane().add(lblBody);
		
		JLabel lblPublishedAt = new JLabel("Published At:");
		lblPublishedAt.setBounds(35, 375, 109, 15);
		frame.getContentPane().add(lblPublishedAt);
		
		JButton btnLatestArticle = new JButton("Latest Article");
		// If latest article button was pressed.
		btnLatestArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Query the API for the latest article.
				try {
				    // Create the URL, the request methods and properties, as well
				    // as creating an HttpURL Connection that attaches the authenticated
				    // user's credentials for authentication.
		            URL url = new URL ("http://localhost:8888/api/v1/url/article");
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            connection.setRequestMethod("GET");
		            connection.setDoOutput(true);
		            connection.setRequestProperty  ("Authorization", "Basic " + credentials);
	           
		            InputStream content = (InputStream)connection.getInputStream();
		            // Use a buffered reader to read the output from the API.
		            BufferedReader in = new BufferedReader (new InputStreamReader (content));
		            String line;
		            while ((line = in.readLine()) != null) {
		                JSONObject obj = new JSONObject(line);
		                JSONObject articleData = obj.getJSONObject("article");
		                String articleID = "Article ID:" + articleData.getString("id") + "\n";
		                String articleTitle = "Article Title:" + articleData.getString("title") + "\n";
		                String articleBody = "Article Body:" + articleData.getString("body") + "\n";
		                String articleCreate = "Article Created At:" + articleData.getString("created_at") + "\n";
		                String articleUpdate = "Article Updated At:" + articleData.getString("updated_at") + "\n";
		                String articlePublish = "Article Published At: " + articleData.getString("published_at") + "\n";
		                textArea.setText(articleID + articleTitle + articleBody + articleCreate + articleUpdate + articlePublish);
		            }		           
		        } catch(Exception e1) {
		            e1.printStackTrace();
		        }	
			}
		});
		
		// Items involved with the article creation and
		// article submission processes.
		btnLatestArticle.setBounds(37, 114, 138, 48);
		frame.getContentPane().add(btnLatestArticle);
				
		final JTextField textField_1 = new JTextField();

		textField_1.setBounds(281, 352, 285, 31);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		final JMonthChooser monthChooser = new JMonthChooser();
		monthChooser.setBounds(94, 400, 122, 19);
		frame.getContentPane().add(monthChooser);
		
		final JSpinField spinField = new JSpinField();
		spinField.setBounds(27, 400, 46, 19);
		spinField.setMaximum(30);
		spinField.setMinimum(1);
		frame.getContentPane().add(spinField);
		
		final JYearChooser yearChooser = new JYearChooser();
		yearChooser.setBounds(164, 431, 52, 19);
		frame.getContentPane().add(yearChooser);
		
		JButton btnSubmit = new JButton("Create Article");
		btnSubmit.addActionListener(new ActionListener() {
			// If the create article button was pressed.
			public void actionPerformed(ActionEvent e) {
				// Submit the article contents to the API.
				String title = textField_1.getText();
				String body = textPane.getText();
				int day = spinField.getValue();
				int month = monthChooser.getMonth();
				int year = yearChooser.getValue();
				String articleContent = "title=" + title + "&body=" + body + "&published_at=" + year + "-" + month + "-" + day;
				
				try {
				    // Create the URL, the request methods and properties, as well
				    // as creating an HttpURL Connection that attaches the authenticated
				    // user's credentials for authentication.
		            URL url = new URL ("http://localhost:8888/api/v1/url/article");
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            connection.setRequestMethod("POST");
		            connection.setDoOutput(true);
		            connection.setRequestProperty  ("Authorization", "Basic " + credentials);
	            
		            DataOutputStream wr = new DataOutputStream(connection.getOutputStream()); 		           
		            // Send the URL parameters to the API.
		            wr.writeBytes(articleContent);
		            InputStream content = (InputStream)connection.getInputStream();
		            // Use a buffered reader to read the output from the API.
		            BufferedReader in = new BufferedReader (new InputStreamReader (content));
		            String line;
		            while ((line = in.readLine()) != null) {
	            		if(line.contains("new_article")) {
	            			textArea.setText("Your article has been created! Click the Latest Article button to see it.");
	            		}
		            }		           
		        } catch(Exception e1) {
		            e1.printStackTrace();
		        }
			}
		});
		
		btnSubmit.setBounds(578, 443, 131, 44);
		frame.getContentPane().add(btnSubmit);
		
		JButton btnUpdateProfile = new JButton("Update Profile");
		btnUpdateProfile.addActionListener(new ActionListener() {
			// If update profile button is pressed.
			public void actionPerformed(ActionEvent e) {
				ProfileWindow profileWindow = new ProfileWindow();
				profileWindow.frame.setVisible(true);
				profileWindow.credentials = credentials;
			}
		});
		btnUpdateProfile.setBounds(187, 114, 165, 48);
		frame.getContentPane().add(btnUpdateProfile);
		
		btnSearch.addActionListener(new ActionListener() {
			// If search button was pressed.
			public void actionPerformed(ActionEvent e) {
				// If the text field is empty, spit back an error.
				if (textField.getText().isEmpty()) {
					textArea.setText("Please enter a valid user name!");
				}
				
				// If the text field isn't empty, query API for user name.
				if(!textField.getText().isEmpty()) {
					String name = "name=" + textField.getText();
					
					try {
					    // Create the URL, the request methods and properties, as well
					    // as creating an HttpURL Connection that attaches the authenticated
					    // user's credentials for authentication.
			            URL url = new URL ("http://localhost:8888/api/v1/url");
			            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			            connection.setRequestMethod("POST");
			            connection.setDoOutput(true);
			            connection.setRequestProperty  ("Authorization", "Basic " + credentials);
		            
			            DataOutputStream wr = new DataOutputStream(connection.getOutputStream()); 		           
			            // Send the URL parameters to the API.
			            wr.writeBytes(name);
			            InputStream content = (InputStream)connection.getInputStream();
			            // Use a buffered reader to read the output from the API.
			            BufferedReader in = new BufferedReader (new InputStreamReader (content));
			            String line;
			            while ((line = in.readLine()) != null) {
			                JSONObject obj = new JSONObject(line);
			                JSONArray userData = obj.getJSONArray("user");
			                JSONObject user = userData.getJSONObject(0);
			                String userID = "User ID:" + user.getString("id") + "\n";
			                String userName = "User Name:" + user.getString("name") + "\n";
			                String userEmail = "User Email:" + user.getString("email") + "\n";
			                String userCreate = "User Created At:" + user.getString("created_at") + "\n";
			                String userUpdate = "User Updated At:" + user.getString("updated_at") + "\n";
			                textArea.setText(userID + userName + userEmail + userCreate + userUpdate);
			            }		           
			        } catch(Exception e1) {
			            textArea.setText("This user doesn't exist. Remember that names are case sensitive!");
			        }
				}
			}
		});
	}
}