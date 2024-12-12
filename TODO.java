import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Task3 extends JFrame implements ActionListener {
    private DefaultListModel<TaskItem> listModel;
    private JList<TaskItem> taskList;
    private JTextField taskField;
    private JButton addButton, editButton, completeButton, deleteButton, showCompletedButton;
    public static int index = 0;
    private List<TaskItem> completedTasks;

    public Task3() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Task 3");
        setSize(800, 400);
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskField = new JTextField();
        taskField.setPreferredSize(new Dimension(300, 30));
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        completeButton = new JButton("Complete");
        deleteButton = new JButton("Delete");
        showCompletedButton = new JButton("Show Completed Tasks");
        completedTasks = new ArrayList<>();

        add(new JScrollPane(taskList), BorderLayout.CENTER);
        JLabel enterToDoLabel = new JLabel("Enter To Do:");
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(enterToDoLabel);
        inputPanel.add(taskField);
        add(inputPanel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));  // Added a new button
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(showCompletedButton);
        add(buttonPanel, BorderLayout.SOUTH);
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        completeButton.addActionListener(this);
        deleteButton.addActionListener(this);
        showCompletedButton.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();

        if (buttonText.equals("Add")) {
            String taskDescription = taskField.getText();
            if (!taskDescription.isEmpty()) {
                TaskItem taskItem = new TaskItem(index++, taskDescription);
                listModel.addElement(taskItem);
                taskField.setText("");
            }
        } else if (buttonText.equals("Edit")) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String taskDescription = taskField.getText();
                if (!taskDescription.isEmpty()) {
                    TaskItem taskItem = listModel.get(selectedIndex);
                    taskItem.setDescription(taskDescription);
                    listModel.set(selectedIndex, taskItem);
                    taskField.setText("");
                }
            }
        } else if (buttonText.equals("Complete")) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                TaskItem taskItem = listModel.get(selectedIndex);
                completedTasks.add(taskItem);
                listModel.remove(selectedIndex);
            }
        } else if (buttonText.equals("Delete")) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                taskField.setText("");
            }
        } else if (buttonText.equals("Show Completed Tasks")) {
            // Open a new form to display completed tasks
            showCompletedTasks();
        }
    }

    private void showCompletedTasks() {
        JFrame completedTasksFrame = new JFrame("Completed Tasks");
        completedTasksFrame.setSize(400, 400);
        completedTasksFrame.setLayout(new BorderLayout());

        DefaultListModel<TaskItem> completedListModel = new DefaultListModel<>();
        JList<TaskItem> completedTasksList = new JList<>(completedListModel);

        for (TaskItem task : completedTasks) {
            completedListModel.addElement(task);
        }

        completedTasksFrame.add(new JScrollPane(completedTasksList), BorderLayout.CENTER);

        completedTasksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        completedTasksFrame.setLocationRelativeTo(this);
        completedTasksFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Task3();
    }

    class TaskItem {
        private int index;
        private String description;

        public TaskItem(int index, String description) {
            this.index = index;
            this.description = description;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return index + ": " + description;
        }
    }
}
