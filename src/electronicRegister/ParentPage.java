package electronicRegister;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ParentPage extends JFrame {
    String firstName;
    String lastName;

    Course course;
    ParentPage(Course course) throws IOException {
        this.setSize(new Dimension(400, 380));
        this.setLayout(null);
        this.course = course;

        JLabel welcome = new JLabel("Welcome back!");
        welcome.setBounds(145,25,300,30);
        welcome.setBackground(new Color(255, 254, 204));
        this.add(welcome);


        JLabel enterYourFirstName = new JLabel("Please enter your firstname:");
        enterYourFirstName.setBounds(75,85,300,30);
        enterYourFirstName.setBackground(new Color(255, 254, 204));
        this.add(enterYourFirstName);

        JTextField enterYourFirstNameBar;
        enterYourFirstNameBar=new JTextField();
        enterYourFirstNameBar.setBounds(90,120, 200,30);
        enterYourFirstNameBar.setBackground(new Color(219, 241, 255));
        this.add(enterYourFirstNameBar);


        JLabel enterYourLastName = new JLabel("Please enter your lastname:");
        enterYourLastName.setBounds(75,155,300,30);
        enterYourLastName.setBackground(new Color(255, 254, 204));
        this.add(enterYourLastName);

        JTextField enterYourLastNameBar;
        enterYourLastNameBar=new JTextField();
        enterYourLastNameBar.setBounds(90,190, 200,30);
        enterYourLastNameBar.setBackground(new Color(219, 241, 255));
        this.add(enterYourLastNameBar);


        //set button graphics
        Image img = ImageIO.read(getClass().getResource("button1.png"));
        Graphics imgGraphics = img.getGraphics();
        imgGraphics.setFont(new Font("Arial Black", Font.PLAIN, 18));
        imgGraphics.setColor(new Color(255, 254, 204));
        imgGraphics.drawString("submit", 20, 27);

        JButton submitButton=new JButton();
        submitButton.setIcon(new ImageIcon(img));
        submitButton.setContentAreaFilled(false);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setBackground(new Color(255, 254, 204));
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                firstName = enterYourFirstNameBar.getText();
                lastName = enterYourLastNameBar.getText();

                displayNotification();
            }
        });
        submitButton.setBounds(140,260,105,45);
        this.add(submitButton);

        this.getContentPane().setBackground(new Color(255, 254, 204));
        this.setVisible(true);
    }

    public void displayNotification() {
        JFrame notificationFrame = new JFrame("Notification");
        notificationFrame.setSize(new Dimension(450, 250));
        notificationFrame.setLayout(null);

        String fullName = firstName + " " + lastName;
        Parent signedInParent = null;

        for(Student currentStudent : course.getAllStudents()) {
            if(currentStudent.getMother() != null)
                if(currentStudent.getMother().toString().equals(fullName))
                    signedInParent = currentStudent.getMother();

            if(currentStudent.getFather() != null)
                if(currentStudent.getFather().toString().equals(fullName))
                    signedInParent = currentStudent.getFather();
        }

        if(signedInParent != null) {
            if(signedInParent.currentNotification != null) {
               String[] lines = signedInParent.currentNotification.toString().split("\\r?\\n");

                JLabel notificationLabel0 = new JLabel(lines[0]);
                JLabel notificationLabel1 = new JLabel(lines[1]);
                JLabel notificationLabel2 = new JLabel(lines[2]);

                notificationLabel0.setBounds(75,50,300,30);
                notificationLabel1.setBounds(75,80,300,30);
                notificationLabel2.setBounds(75,110,300,30);

                notificationFrame.add(notificationLabel0);
                notificationFrame.add(notificationLabel1);
                notificationFrame.add(notificationLabel2);
            } else {
                notificationFrame.setSize(new Dimension(250, 80));
                notificationFrame.setLayout(new FlowLayout());
                JLabel error = new JLabel("There are no notifications!");
                notificationFrame.add(error, BorderLayout.CENTER);

            }
            notificationFrame.setBackground(new Color(255, 254, 204));
            notificationFrame.setVisible(true);
        }

    }
}
