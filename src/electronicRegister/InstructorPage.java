package electronicRegister;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;

public class InstructorPage extends JFrame{

    String selectedInstructor = "";
    String firstName = "";
    String lastName = "";

    ScoreVisitor allScores;
    public InstructorPage(ScoreVisitor allScores) throws IOException {
        super("Instructor Page");
        this.setSize(400,400);
        this.setLayout(null);
        this.allScores = allScores;

        JLabel enterYourRole = new JLabel("Please enter your role (teacher/assistant):");
        enterYourRole.setBounds(75,65,300,30);
        enterYourRole.setBackground(new Color(255, 254, 204));
        this.add(enterYourRole);

        JTextField enterYourRoleBar;
        enterYourRoleBar=new JTextField();
        enterYourRoleBar.setBounds(90,100, 200,30);
        enterYourRoleBar.setBackground(new Color(219, 241, 255));
        this.add(enterYourRoleBar);


        JLabel enterYourFirstName = new JLabel("Please enter your firstname:");
        enterYourFirstName.setBounds(75,135,300,30);
        enterYourFirstName.setBackground(new Color(255, 254, 204));
        this.add(enterYourFirstName);

        JTextField enterYourFirstNameBar;
        enterYourFirstNameBar=new JTextField();
        enterYourFirstNameBar.setBounds(90,170, 200,30);
        enterYourFirstNameBar.setBackground(new Color(219, 241, 255));
        this.add(enterYourFirstNameBar);


        JLabel enterYourLastName = new JLabel("Please enter your lastname:");
        enterYourLastName.setBounds(75,205,300,30);
        enterYourLastName.setBackground(new Color(255, 254, 204));
        this.add(enterYourLastName);

        JTextField enterYourLastNameBar;
        enterYourLastNameBar=new JTextField();
        enterYourLastNameBar.setBounds(90,240, 200,30);
        enterYourLastNameBar.setBackground(new Color(219, 241, 255));
        this.add(enterYourLastNameBar);




        JLabel welcome = new JLabel("Welcome back!");
        welcome.setBounds(145,25,300,30);
        welcome.setBackground(new Color(255, 254, 204));
        this.add(welcome);


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
                selectedInstructor = enterYourRoleBar.getText();
                firstName = enterYourFirstNameBar.getText();
                lastName = enterYourLastNameBar.getText();

                try {
                    createInstructorFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        submitButton.setBounds(140,290,105,45);
        this.add(submitButton);

        this.getContentPane().setBackground(new Color(255, 254, 204));
        this.setVisible(true);
    }

    public void createInstructorFrame() throws IOException {
        JFrame instructorFrame = new JFrame("INSTRUCTOR");
        if(!selectedInstructor.equalsIgnoreCase("teacher") &&
                !selectedInstructor.equalsIgnoreCase("assistant")) {

            JPanel panel1 = new JPanel(new BorderLayout());
            JLabel label1 = new JLabel();
            label1.setLayout(new BorderLayout());
            label1.setText("                 Invalid type of instructor");
            label1.setBackground(new Color(255, 148, 148));
            panel1.add(label1, BorderLayout.SOUTH);
            panel1.setBackground(new Color(255, 148, 148));

            JPanel panel2 = new JPanel(new BorderLayout());
            JLabel label2 = new JLabel();
            label2.setLayout(new BorderLayout());
            label2.setText("         Please type 'assistant' or 'teacher'.");
            label2.setBackground(new Color(255, 148, 148));
            panel2.add(label2, BorderLayout.NORTH);
            panel2.setBackground(new Color(255, 148, 148));

            instructorFrame.setLayout(new GridLayout(4,1));
            instructorFrame.setSize(new Dimension(300,130));

            JPanel empty1 = new JPanel();
            empty1.setBackground(new Color(255, 148, 148));
            JPanel empty2 = new JPanel();
            empty2.setBackground(new Color(255, 148, 148));

            instructorFrame.add(empty1);
            instructorFrame.add(panel1);
            instructorFrame.add(panel2);
            instructorFrame.add(empty2);
        }

        if(selectedInstructor.equalsIgnoreCase("teacher")) {
            instructorFrame.setSize(new Dimension(400,250));

            instructorFrame.setLayout(new BorderLayout());

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            titlePanel.setBackground(new Color(255, 231, 153));
            JPanel scrollGrades = new JPanel();
            scrollGrades.setBackground(new Color(255, 234, 143));
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setBackground(new Color(255, 231, 153));

            //create Title Panel
            JLabel title1 = new JLabel("                     WELCOME BACK   " + firstName + " " + lastName + "!");
            JLabel title2 = new JLabel("             SELECT A COURSE TO SEE THE DETAILS!");
            title1.setFont(new Font("Calibri", Font.BOLD, 18));
            title2.setFont(new Font("Calibri", Font.BOLD, 18));

            titlePanel.add(title1);
            titlePanel.add(title2);


            //the courses list for a teacher
            String teacherFullName = firstName + " " + lastName;
            DefaultListModel<Course> teacherCourses = new DefaultListModel<Course>();
            for(Course currentCourse : Catalog.getInstance().courses)
                if(currentCourse.getTeacher().toString().equals(teacherFullName))
                    teacherCourses.addElement(currentCourse);

            // scroll list
            JList<Course> teacherCoursesJList = new JList<Course>(teacherCourses);
            JScrollPane coursesScroll = new JScrollPane(teacherCoursesJList);
            coursesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            coursesScroll.setViewportView(teacherCoursesJList);
            teacherCoursesJList.setLayoutOrientation(JList.VERTICAL_WRAP);
            scrollGrades.add(coursesScroll);


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
            buttonsPanel.add(submitButton);
            submitButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                   Course selectedCourse = teacherCoursesJList.getSelectedValue();

                    try {
                        courseInformation(selectedCourse);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            instructorFrame.add(titlePanel, BorderLayout.NORTH);
            instructorFrame.add(scrollGrades, BorderLayout.CENTER);
            instructorFrame.add(buttonsPanel, BorderLayout.SOUTH);

        } else if (selectedInstructor.equalsIgnoreCase("assistant")) {
            instructorFrame.setSize(new Dimension(400,250));

            instructorFrame.setLayout(new BorderLayout());

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            titlePanel.setBackground(new Color(255, 231, 153));
            JPanel scrollGrades = new JPanel();
            scrollGrades.setBackground(new Color(255, 234, 143));
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setBackground(new Color(255, 231, 153));

            //create Title Panel
            JLabel title1 = new JLabel("                     WELCOME BACK   " + firstName + " " + lastName + "!");
            JLabel title2 = new JLabel("             SELECT A COURSE TO SEE THE DETAILS!");
            title1.setFont(new Font("Calibri", Font.BOLD, 18));
            title2.setFont(new Font("Calibri", Font.BOLD, 18));

            titlePanel.add(title1);
            titlePanel.add(title2);


            //the courses list for an assistant
            String assistantFullName = firstName + " " + lastName;
            DefaultListModel<Course> assistantCourses = new DefaultListModel<Course>();
            for(Course currentCourse : Catalog.getInstance().courses) {
                Iterator<Assistant> assistantItr = currentCourse.getAssistants().iterator();
                while(assistantItr.hasNext())
                    if(assistantItr.next().toString().equals(assistantFullName))
                        assistantCourses.addElement(currentCourse);
            }


            // scroll list
            JList<Course> assistantCoursesJList = new JList<Course>(assistantCourses);
            JScrollPane coursesScroll = new JScrollPane(assistantCoursesJList);
            coursesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            coursesScroll.setViewportView(assistantCoursesJList);
            assistantCoursesJList.setLayoutOrientation(JList.VERTICAL_WRAP);
            scrollGrades.add(coursesScroll);


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
            buttonsPanel.add(submitButton);
            submitButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    Course selectedCourse = assistantCoursesJList.getSelectedValue();

                    for(Assistant currentAssistant : selectedCourse.getAssistants())
                        if(currentAssistant.toString().equals(assistantFullName)) {
                            try {
                                courseInformationAssistant(selectedCourse, currentAssistant);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                }
            });

            instructorFrame.add(titlePanel, BorderLayout.NORTH);
            instructorFrame.add(scrollGrades, BorderLayout.CENTER);
            instructorFrame.add(buttonsPanel, BorderLayout.SOUTH);
        }

        instructorFrame.setVisible(true);
    }

    public void notifyParents() {
        for(Course currentCourse : Catalog.getInstance().courses)
            if(!currentCourse.getGrades().isEmpty())
                for(Grade currentGrade : currentCourse.getGrades()) {
                    Notification newNotification = new Notification(currentGrade);
                    Student student = currentGrade.getStudent();

                    Catalog catalog = Catalog.getInstance();
                    if(student.getMother() != null)
                        catalog.addObserver(student.getMother());

                    if(student.getFather() != null)
                        catalog.addObserver(student.getFather());

                    catalog.notifyObservers(currentGrade);

                    if(student.getMother() != null)
                        System.out.println(student.getMother().currentNotification);
                    if(student.getFather() != null)
                        System.out.println(student.getFather().currentNotification);


                    if(student.getMother() != null)
                        catalog.removeObserver(student.getMother());

                    if(student.getFather() != null)
                        catalog.removeObserver(student.getFather());
                }
    }
    public void courseInformation(Course course) throws IOException {
        JFrame courseFrame = new JFrame("Course");
        courseFrame.setLayout(new BorderLayout());
        courseFrame.setSize(new Dimension(400, 300));

        JPanel titlePanel = new JPanel();
        JLabel titleText = new JLabel("Here are the grades you have to validate: ");
        titlePanel.setBackground(new Color(255, 225, 128));
        titlePanel.add(titleText);


        Teacher teacher = null;
        for(Course currentCourse : Catalog.getInstance().courses)
            if(currentCourse.getTeacher().toString().equals(course.getTeacher().toString()))
                teacher = currentCourse.getTeacher();


        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(new Color(255, 225, 128));

        DefaultListModel<String> enrolledStudents = new DefaultListModel<>();
        for(int i = 0; i < allScores.getStudents(teacher, course.getName()).size(); i++) {
            String entry = allScores.getStudents(teacher, course.getName()).get(i) +
                    " exam grade is : " + allScores.getGrades(teacher, course.getName()).get(i);
            enrolledStudents.addElement(entry);
        }


//        // scroll list
        JList<String> studentsJList = new JList<String>(enrolledStudents);
        JScrollPane studentsScroll = new JScrollPane(studentsJList);
        studentsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        studentsScroll.setViewportView(studentsJList);
        scrollPanel.add(studentsScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 225, 128));
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
        buttonPanel.add(submitButton);
        Teacher finalTeacher = teacher;
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                allScores.visit(finalTeacher);

                for(Course currentCourse : Catalog.getInstance().courses)
                        System.out.println(currentCourse.getGrades());


                //notify every parent of their children exams' grades:
                notifyParents();

            }
        });

        courseFrame.add(titlePanel, BorderLayout.NORTH);
        courseFrame.add(scrollPanel, BorderLayout.CENTER);
        courseFrame.add(buttonPanel, BorderLayout.SOUTH);

        courseFrame.setVisible(true);
    }

    public void courseInformationAssistant(Course course, Assistant assistant) throws IOException {
        JFrame courseFrame = new JFrame("Course");
        courseFrame.setLayout(new BorderLayout());
        courseFrame.setSize(new Dimension(400, 300));

        JPanel titlePanel = new JPanel();
        JLabel titleText = new JLabel("Here are the grades you have to validate: ");
        titlePanel.setBackground(new Color(255, 225, 128));
        titlePanel.add(titleText);


        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(new Color(255, 225, 128));

        DefaultListModel<String> enrolledStudents = new DefaultListModel<>();
        for(int i = 0; i < allScores.getStudents(assistant, course.getName()).size(); i++) {
            String entry = allScores.getStudents(assistant, course.getName()).get(i) +
                    " partial grade is : " + allScores.getGrades(assistant, course.getName()).get(i);
            enrolledStudents.addElement(entry);
        }

//        // scroll list
        JList<String> studentsJList = new JList<String>(enrolledStudents);
        JScrollPane studentsScroll = new JScrollPane(studentsJList);
        studentsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        studentsScroll.setViewportView(studentsJList);
        scrollPanel.add(studentsScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 225, 128));
        //set button graphics
        Image img = ImageIO.read(getClass().getResource("button1.png"));
        Graphics imgGraphics = img.getGraphics();
        imgGraphics.setFont(new Font("Arial Black", Font.LAYOUT_LEFT_TO_RIGHT, 18));
        imgGraphics.setColor(new Color(255, 254, 204));
        imgGraphics.drawString("submit", 20, 27);

        JButton submitButton=new JButton();
        submitButton.setIcon(new ImageIcon(img));
        submitButton.setContentAreaFilled(false);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setBackground(new Color(255, 254, 204));
        buttonPanel.add(submitButton);

        Assistant finalAssistant = assistant;
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                allScores.visit(finalAssistant);
                for(Course currentCourse : Catalog.getInstance().courses)
                    System.out.println(currentCourse.getGrades());

                notifyParents();
            }
        });

        courseFrame.add(titlePanel, BorderLayout.NORTH);
        courseFrame.add(scrollPanel, BorderLayout.CENTER);
        courseFrame.add(buttonPanel, BorderLayout.SOUTH);

        courseFrame.setVisible(true);
    }

}
