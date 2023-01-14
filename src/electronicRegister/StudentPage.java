package electronicRegister;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class StudentPage extends JFrame implements ActionListener {
	private DefaultListModel<Course> courses = new DefaultListModel<Course>();
	private Student student;
	JList<Course> coursesList;

	public StudentPage(ArrayList<Course> courses, Student student) throws IOException {
		super("Student page");
		this.setLayout(new GridLayout(4,1));
		
		setCourses(courses);
		setStudent(student);
		
		this.setSize(500,300);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.coursesList = new JList<Course>(this.courses);
		JScrollPane coursesScroll = new JScrollPane(coursesList);
		coursesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		coursesScroll.setViewportView(coursesList);
		coursesList.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		JPanel coursesPanel = new JPanel(new BorderLayout());
		coursesPanel.setBackground(new Color(0, 0, 0));
		coursesPanel.add(coursesScroll, BorderLayout.CENTER);
		
		
		JPanel textPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
		
		JLabel textLabel1 = new JLabel("WELCOME BACK " + student + ".");
		textLabel1.setFont(new Font("Calibri", Font.BOLD, 24));
		textLabel1.setBackground(new Color(255, 254, 204));
		textPanel.add(textLabel1, BorderLayout.CENTER);
		
		textPanel.setBackground(new Color(255, 254, 204));
		
		//set button image
		Image img = ImageIO.read(getClass().getResource("button1.png"));
		Graphics imgGraphics = img.getGraphics();
		imgGraphics.setFont(new Font("Arial Black", Font.LAYOUT_LEFT_TO_RIGHT, 18));
		imgGraphics.setColor(new Color(255, 254, 204));
        imgGraphics.drawString("review", 20, 27);
        
		JButton button = new JButton();
		button.addActionListener(this);
		button.setIcon(new ImageIcon(img));
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		buttonPanel.setBackground(new Color(255, 254, 204));
		buttonPanel.add(button, BorderLayout.PAGE_END);
		
		JPanel empty = new JPanel();
		empty.setBackground(new Color(255, 255, 204));
		
		JLabel textLabel2 = new JLabel("    Here are the courses you are enrolled in :");
		textLabel2.setFont(new Font("Calibri", Font.ITALIC, 22));
		textLabel2.setBackground(new Color(255, 251, 204));
		empty.add(textLabel2, BorderLayout.SOUTH);
		
		this.add(textPanel);
		this.add(empty);
		this.add(coursesPanel);
		this.add(buttonPanel);

		
		setVisible(true);
	}
	
	public void setCourses(ArrayList<Course> courses) {
		for(Course currentCourse: courses)
			this.courses.addElement(currentCourse);
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public void addCourse(Course course) {
		this.courses.addElement(course);
	}
	
	public void removeCourse(Course course) {
		this.courses.removeElement(course);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Course selectedCourse = coursesList.getSelectedValue();
		
		JFrame responseFrame = new JFrame("Course details");
		responseFrame.setSize(new Dimension(400, 200));
		responseFrame.setBackground(new Color(255, 153, 153));
		
		
		if(selectedCourse == null) {
			JPanel text1Panel = new JPanel();
			JPanel text2Panel = new JPanel();
			
			JLabel text1 = new JLabel("You have not selected any course.\n");
			text1.setLayout(new BorderLayout());
			text1.setBackground(new Color(255, 153, 153));
			JLabel text2 = new JLabel();
			text2.setLayout(new BorderLayout());
			text2.setText("Please select a course to be able to review the details");
			text2.setBackground(new Color(255, 153, 153));
			
			JPanel empty1 = new JPanel();
			empty1.setBackground(new Color(255, 153, 153));
			JPanel empty2 = new JPanel();
			empty2.setBackground(new Color(255, 153, 153));
			text1Panel.setBackground(new Color(255, 153, 153));
			text2Panel.setBackground(new Color(255, 153, 153));
			text1Panel.add(text1);
			text2Panel.add(text2);
			
			responseFrame.setSize(new Dimension(350, 120));
			
			responseFrame.setLayout(new GridLayout(4,1));
			responseFrame.add(empty1);
			responseFrame.add(text1Panel);
			responseFrame.add(text2Panel);
			responseFrame.add(empty2);
		} else {
			ArrayList<JPanel> panels = new ArrayList<JPanel>();
			
			
			JPanel teacherPanel = new JPanel();
			teacherPanel.setBackground(new Color(204, 255, 139));
			teacherPanel.setLayout(new BorderLayout());
			JLabel teacherText = null;
			if(selectedCourse.getTeacher() != null)
				teacherText = new JLabel("Teacher is: " + selectedCourse.getTeacher());
			else 
				teacherText = new JLabel("                                                           You do not have an assigned teacher!");
			teacherText.setAlignmentX(Component.CENTER_ALIGNMENT);
			teacherPanel.add(teacherText, BorderLayout.CENTER);
			panels.add(teacherPanel);
			
			
			JPanel assistantsPanel = new JPanel();
			assistantsPanel.setBackground(new Color(227, 255, 188));
			assistantsPanel.setLayout(new BoxLayout(assistantsPanel, BoxLayout.Y_AXIS));
			JLabel assistantsText = null;
			if(selectedCourse.getAssistants().isEmpty()) {
				assistantsText = new JLabel("There are no assistants assigned to this course!");
				assistantsPanel.add(assistantsText);
			}
			else {
				for(Assistant currentAssistant : selectedCourse.getAssistants()) {
					JLabel newAssistant = new JLabel("Assistant : " + currentAssistant);
					assistantsPanel.add(newAssistant);
				}
			}
			panels.add(assistantsPanel);
			
			
			JPanel assignedAssistant = new JPanel(new GridLayout(1,1));
			assignedAssistant.setBackground(new Color(204, 255, 139));
			Assistant assignAssistant = null;
			//find the assistant of the group's student
			for(Map.Entry<String, Group> group : selectedCourse.getGroups().entrySet()) {
				for(Student currentStudent : group.getValue()) {
					if(currentStudent.toString().equals(student.toString()))
						assignAssistant = group.getValue().assistant;
						
				}
			}
			JLabel assignAssistantText = new JLabel("Your assigned assistant is: " + assignAssistant);
			assignAssistantText.setAlignmentX(Component.LEFT_ALIGNMENT);
			assignedAssistant.add(assignAssistantText);
			panels.add(assignedAssistant);
			
			
			JPanel gradePanel = new JPanel(new GridLayout(3, 1));
			gradePanel.setBackground(new Color(227, 255, 188));
			JLabel examLabel = new JLabel();
			JLabel partialLabel = new JLabel();
			JLabel totalLabel = new JLabel();
			Grade studentGrade = selectedCourse.getGrade(student);


			if(studentGrade == null) {
				examLabel.setText("Exam Grade : Not assigned");
				partialLabel.setText("Partial Grade : Not assigned");
				totalLabel.setText("Total Grade : Not assigned");
			} else if(studentGrade.getExamScore() == null) {
				examLabel.setText("Exam Grade : Not assigned");
				totalLabel.setText("Total Grade : Not assigned");
			} else if (studentGrade.getPartialScore() == null) {
				partialLabel.setText("Partial Grade : Not assigned");
				totalLabel.setText("Total Grade : Not assigned");
			} else {
				examLabel.setText("Exam Grade : " + studentGrade.getExamScore());
				partialLabel.setText("Partial Grade : " + studentGrade.getPartialScore());
				totalLabel.setText("Total Grade : " + studentGrade.getTotal());
			}

			gradePanel.add(examLabel);
			gradePanel.add(partialLabel);
			gradePanel.add(totalLabel);
			
			
			responseFrame.setLayout(new GridLayout(4,1));
			
			responseFrame.add(teacherPanel);
			responseFrame.add(assistantsPanel);
			responseFrame.add(assignedAssistant);
			responseFrame.add(gradePanel);
		}
		responseFrame.setVisible(true);
	}
}
