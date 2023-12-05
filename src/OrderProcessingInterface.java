import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class OrderProcessingInterface extends JFrame {
    private JLabel numOrdersLabel, closingTimeLabel, enterOrdersLabel;
    private JTextField numOrdersField, closingTimeField;
    private JButton processButton;
    private JTextArea orderInputArea;

    public OrderProcessingInterface() {
        setTitle("Order Processing Interface");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        numOrdersLabel = new JLabel("Enter the number of orders:");
        inputPanel.add(numOrdersLabel);
        numOrdersField = new JTextField();
        inputPanel.add(numOrdersField);

        closingTimeLabel = new JLabel("Enter the closing time:");
        inputPanel.add(closingTimeLabel);
        closingTimeField = new JTextField();
        inputPanel.add(closingTimeField);

        enterOrdersLabel = new JLabel("Enter orders (each line: arrivalTime duration):");
        inputPanel.add(enterOrdersLabel);

        orderInputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(orderInputArea);
        inputPanel.add(scrollPane);

        add(inputPanel, BorderLayout.CENTER);

        processButton = new JButton("Process Orders");
        processButton.setFont(new Font("Arial", Font.PLAIN, 14));
        processButton.setBackground(new Color(50, 150, 50));
        processButton.setForeground(Color.WHITE);
        processButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        processButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(processButton);
        add(buttonPanel, BorderLayout.SOUTH);

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numOrders = Integer.parseInt(numOrdersField.getText());
                int closingTime = Integer.parseInt(closingTimeField.getText());
                Scanner scanner = new Scanner(orderInputArea.getText());
                OrderProcessing orderProcessing = new OrderProcessing();

                JFrame outputFrame = createOutputFrame();
                outputFrame.setVisible(true);

                JTextArea outputTextArea = findJTextAreaInScrollPane(outputFrame.getContentPane());
                if (outputTextArea != null) {
                    PrintStream outputPrintStream = new PrintStream(new OutputStream() {
                        @Override
                        public void write(int b) {
                            outputTextArea.append(String.valueOf((char) b));
                        }
                    });
                    System.setOut(outputPrintStream);
                    orderProcessing.processOrders(numOrders, closingTime, scanner);
                } else {
                    System.out.println("TextArea not found in JScrollPane!");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private JFrame createOutputFrame() {
        JFrame outputFrame = new JFrame("Output Window");
        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputTextArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border around scroll pane

        outputFrame.add(scrollPane);
        outputFrame.setSize(400, 300);
        outputFrame.setLocationRelativeTo(this);
        return outputFrame;
    }

    private JTextArea findJTextAreaInScrollPane(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                for (Component comp2 : scrollPane.getViewport().getComponents()) {
                    if (comp2 instanceof JTextArea) {
                        return (JTextArea) comp2;
                    }
                }
            } else if (comp instanceof Container) {
                JTextArea textArea = findJTextAreaInScrollPane((Container) comp);
                if (textArea != null) {
                    return textArea;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OrderProcessingInterface interfaceFrame = new OrderProcessingInterface();
                interfaceFrame.setVisible(true);
            }
        });
    }
}