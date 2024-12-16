import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URI;
import javax.swing.*;

public class SoundCloudViewBot
{
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            // Creates the main frame for the GUI
            JFrame frame = new JFrame("SoundCloud Streamer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout(10,10));
            frame.setSize(400, 150);
            frame.setLocationRelativeTo(null); // Centers the window on screen
            
            // Creates a panel to hold input fields and labels
            JPanel inputPanel = new JPanel(new FlowLayout());
            
            JLabel urlLabel = new JLabel("SoundCloud Link:");
            JTextField urlField = new JTextField();
            urlField.setPreferredSize(new Dimension(200, 25));
            
            JLabel streamsLabel = new JLabel("Number of Streams:");
            JTextField streamsField = new JTextField();
            streamsField.setPreferredSize(new Dimension(50, 25));
            
            // Add all components to the input panel
            inputPanel.add(urlLabel);
            inputPanel.add(urlField);
            inputPanel.add(streamsLabel);
            inputPanel.add(streamsField);
            
            // Creates a Start button to initiate the view bot process
            JButton startButton = new JButton("Start");
            startButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    String url = urlField.getText().trim();
                    String streamsText = streamsField.getText().trim();

                    // Check if URL or stream count fields are empty
                    if (url.isEmpty() || streamsText.isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(frame, "Please enter both the URL and the number of streams.");
                        return;
                    }
                    
                    int streams;
                    try 
                    {
                        streams = Integer.parseInt(streamsText);
                    } catch (NumberFormatException ex) 
                    {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number for streams.");
                        return;
                    }
                    
                    // Run the streaming logic in a separate thread to keep the GUI responsive
                    new Thread(() -> 
                    {
                        try 
                        {
                            Robot r = new Robot();
                            
                            // Launch the SoundCloud link in the default system browser
                            Desktop.getDesktop().browse(new URI(url));
                            
                            // Wait a few seconds to ensure the page loads before starting refresh cycle
                            Thread.sleep(5000); // You may need to adjust this based on your internet speed and system performance
                            
                            // Perform the specified number of streams by refreshing the page
                            for (int x = 0; x < streams; x++) 
                            {
                                // Wait between plays to simulate actual listening time
                                Thread.sleep(40000); 
                                
                                // Simulate pressing Ctrl+R to refresh the page
                                r.keyPress(KeyEvent.VK_CONTROL);
                                r.keyPress(KeyEvent.VK_R);
                                Thread.sleep(1000);
                                r.keyRelease(KeyEvent.VK_R);
                                r.keyRelease(KeyEvent.VK_CONTROL);
                                Thread.sleep(1000);
                            }
                            
                            // Notify the user once all requested streams have completed
                            JOptionPane.showMessageDialog(frame, "Completed " + streams + " streams.");
                        } catch (Exception ex) 
                        {
                            // Show an error message if something unexpected occurs
                            JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
                        }
                    }).start();
                }
            });

            // Add components to the frame and make it visible
            frame.add(inputPanel, BorderLayout.CENTER);
            frame.add(startButton, BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}