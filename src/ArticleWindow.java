import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import com.toedter.calendar.JYearChooser;
import com.toedter.components.JSpinField;
import com.toedter.calendar.JMonthChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.event.ActionEvent;

public class ArticleWindow {

  JFrame frame;
  private JTextField textField;
  protected String credentials;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ArticleWindow window = new ArticleWindow();
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
  public ArticleWindow() {
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
    
    JLabel lblCreateAnArticle = new JLabel("Create An Article");
    lblCreateAnArticle.setBounds(161, 12, 119, 15);
    frame.getContentPane().add(lblCreateAnArticle);
    
    textField = new JTextField();
    textField.setBounds(55, 38, 363, 19);
    frame.getContentPane().add(textField);
    textField.setColumns(10);
    
    JLabel lblTitle = new JLabel("Title:");
    lblTitle.setBounds(12, 40, 44, 15);
    frame.getContentPane().add(lblTitle);
    
    JLabel lblBody = new JLabel("Body:");
    lblBody.setBounds(12, 67, 44, 15);
    frame.getContentPane().add(lblBody);
    
    final JTextArea textArea = new JTextArea();
    textArea.setBounds(55, 69, 363, 121);
    frame.getContentPane().add(textArea);
    
    final JComboBox<String> comboBox = new JComboBox<String>();
    comboBox.setBounds(55, 231, 128, 19);
    comboBox.addItem("work");
    comboBox.addItem("personal");
    comboBox.addItem("programming");
    comboBox.addItem("middle earth");
    comboBox.addItem("school");
    frame.getContentPane().add(comboBox);
    
    JLabel lblTag = new JLabel("Tag:");
    lblTag.setBounds(12, 233, 44, 15);
    frame.getContentPane().add(lblTag);
    
    JLabel lblPublishedAt = new JLabel("Published At:");
    lblPublishedAt.setBounds(12, 202, 94, 15);
    frame.getContentPane().add(lblPublishedAt);
    
    final JYearChooser yearChooser = new JYearChooser();
    yearChooser.setBounds(115, 200, 52, 19);
    frame.getContentPane().add(yearChooser);
    
    final JSpinField spinField = new JSpinField();
    spinField.setBounds(177, 200, 44, 19);
    frame.getContentPane().add(spinField);
    
    final JMonthChooser monthChooser = new JMonthChooser();
    monthChooser.setBounds(233, 198, 122, 19);
    frame.getContentPane().add(monthChooser);
    
    JButton btnSubmit = new JButton("Submit");
    btnSubmit.addActionListener(new ActionListener() {
      // If submit button was pressed.
      public void actionPerformed(ActionEvent e) {
        // Submit the article contents to the API.
      String title = textField.getText();
      String body = textArea.getText();
      int day = spinField.getValue();
      int month = monthChooser.getMonth();
      int year = yearChooser.getValue();
      String tag = (String) comboBox.getSelectedItem();
      String articleContent = "title=" + title + "&body=" + body + "&published_at=" + year + "-" + month + "-" + day + "&tag_list=" + tag;
        
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
                textArea.setText("Your article has been created!");
              }
            }              
        } catch(Exception e1) {
            e1.printStackTrace();
        }
      }
    });
    btnSubmit.setBounds(209, 228, 100, 25);
    frame.getContentPane().add(btnSubmit);
    
    JButton btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    btnCancel.setBounds(321, 228, 117, 25);
    frame.getContentPane().add(btnCancel);
  }
}
