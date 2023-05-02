import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

public class SwingControlDemo implements ActionListener {

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();

    }

    private JFrame background;
    private JLabel headerLabel;
    private JLabel statusLabel;

    private JPanel topPannel;
    private JPanel bottomPannel;

    private JButton button1;
    private JTextArea urlBox;
    private JTextArea key;
    public JTextArea output;
    private JScrollPane scroll;

    private JTextArea bottomCenterText;
    private int WIDTH = 800;
    private int HEIGHT = 700;

    boolean run = true;


    public SwingControlDemo() {
        prepareGUI();
    }

    private void prepareGUI() {
        background = new JFrame("Test");
        background.setSize(WIDTH, HEIGHT);
        background.setLayout(new GridLayout(2, 1));


        background.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });


        topPannel = new JPanel();
        topPannel.setLayout(new GridLayout(2, 2));

        bottomPannel = new JPanel();
        bottomPannel.setLayout(new BorderLayout());

        output = new JTextArea();
        output.setEditable(false); // set textArea non-editable

        scroll = new JScrollPane(output);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        bottomPannel.add(scroll);


        urlBox = new JTextArea("Enter Url Here");
        topPannel.add(urlBox);

        key = new JTextArea("Enter Key Here");
        topPannel.add(key);

        button1 = new JButton("Search");
        button1.setActionCommand("Search");
        button1.addActionListener(new ButtonClickListener());


        topPannel.add(button1);


        background.add(topPannel);
        background.add(bottomPannel);
        background.setVisible(true);
    }

    public void readHTML(String thing, String pat, int depth) {
        String path = pat + thing + " --> ";
//        if (thing.contains(key.getText())) {
//            System.out.println("DONE");
//            System.out.println(path);
//            output.append(path);
//        }

        if(depth < 2) {

 //           else {

                try {
                    System.out.println("It's working");
                    //ArrayList<String> str = new ArrayList<String>();
                    System.out.println();
                    URL url = new URL( thing);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(url.openStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("href") && !line.contains("http") && !line.contains("href=\"mw") && !line.contains("e.en") && !line.contains("wikimedia") && !line.contains("href=\"#cite_") && !line.contains("/en.") && !line.contains("wiki/File") && !line.contains("w/index.") && !line.contains("wiki/Wikipedia") && !line.contains("#") && !line.contains("Help")) {
                            int x = line.indexOf("href=\"") + 6;
                            int y = line.indexOf("\"", x);
                            if (y < 0) {
                                y = x;
                            }
                          String Line = ( "https://en.wikipedia.org" + line.substring(x, y));
                            //String Line = (line.substring(x, y));
                            System.out.println(depth + " " + Line);


                            //if (Line.contains(lowerKEY)) {
//                            output.append(Line);
//                            output.append("\n");
                            //}
                            // else {
                            if (Line.contains(key.getText())) {
                            System.out.println("DONE");
                             path = path + Line;
                             System.out.println(path);
                            output.append(path);
                            output.append("\n");
                            run = false;
                           }
                            if(run == true) {
                                readHTML(Line, path, depth + 1);
                            }
                            // }


                        }

                    }

                    reader.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
 //           }
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Search")) {
                run = true;
                readHTML(urlBox.getText(), "",0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
