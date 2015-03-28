import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TakeProfitGUI implements Constants {

    TakeProfitGUI() {
        JFrame jfrm = new JFrame("TakeProfit Distance Application");
        jfrm.setSize(framesizeH, framesizeW);
        jfrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jfrm.setVisible(true);

        TPDPanel tpdPanel = new TPDPanel();
        MultiplicatorPanel multPanel = new MultiplicatorPanel();

        JTabbedPane jtp = new JTabbedPane();
        jtp.setFont(REGFONT);
        jtp.addTab("TPD", tpdPanel);
        jtp.addTab("Multiplicator", multPanel);
        jfrm.add(jtp);
    }

    class TPDPanel extends JPanel{

        DataBase DB;

        JTextArea text;
        JComboBox<String> multBox;
        File[] selectedFiles;
        JLabel fileName;

        TPDPanel(){
            DB = new DataBase();
            setSize(framesizeH, framesizeW);
            setVisible(true);

            JButton selectFile = new JButton("Select File");
            selectFile.setFont(BIGFONT);
            ButtonActionListener buttonActionListener = new ButtonActionListener();
            selectFile.addActionListener(buttonActionListener);
            fileName = new JLabel("");
            fileName.setFont(REGFONT);
            JLabel multLabel = new JLabel("L:");
            multLabel.setFont(BIGFONT);
            multBox = new JComboBox<>();
            multBox.setFont(BIGFONT);
            for(int i = 0; i<=10; i++) multBox.addItem(""+(double)i/10);
            multBox.setSelectedItem(""+DB.multiplicator);
            ComboBoxActionListener comboBoxActionListener = new ComboBoxActionListener();
            multBox.addActionListener(comboBoxActionListener);
            text = new JTextArea();
            text.setFont(REGFONT);
            text.setEditable(false);
            JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, SPACEW, SPACEH));
            setLayout(new BorderLayout());
            north.add(multLabel);
            north.add(multBox);
            north.add(selectFile);
            add(fileName, BorderLayout.SOUTH);
            add(north, BorderLayout.NORTH);
            add(text, BorderLayout.CENTER);

        }

        class ButtonActionListener implements ActionListener {
            public void actionPerformed(ActionEvent ae){
                JFileChooser selectFileBox = new JFileChooser("C:\\Users\\eugene\\Google Drive\\Forex\\Results");
                selectFileBox.setMultiSelectionEnabled(true);
                int returnValue = selectFileBox.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = selectFileBox.getSelectedFiles();
                    fileName.setText(getFileNames(selectedFiles));
                }
                DB = new DataBase(selectedFiles, getComboBoxValue());
                double[][] array = DB.getTPDResult();
                String str = printArray(array);
                text.setText(str);
            }
        }

        class ComboBoxActionListener implements ActionListener {
            public void actionPerformed(ActionEvent ae){
                DB = new DataBase(selectedFiles, getComboBoxValue());
                double[][] array = DB.getTPDResult();
                String str = printArray(array);
                text.setText(str);
            }
        }

        double getComboBoxValue(){
            String str = (String) multBox.getSelectedItem();
            return Double.parseDouble(str);
        }

        String getFileNames(File[] files){
            StringBuilder str = new StringBuilder("");
            for (File file : files) {
                str.append(file.getName());
                str.append(" ");
            }
            return str.toString();
        }

        String printArray(double[][] array){
            StringBuilder str = new StringBuilder("");
            for (double[] anArray : array) str.append(anArray[0]).append("\t").append(anArray[1]).append("\n");
            return str.toString();
        }

    }

    class MultiplicatorPanel extends JPanel{

        DataBase DB;

        JTextArea text;
        JComboBox<String> multBox;
        File[] selectedFiles;
        JLabel fileName;

        MultiplicatorPanel(){
            DB = new DataBase();
            setSize(framesizeH, framesizeW);
            setVisible(true);

            JButton selectFile = new JButton("Select File");
            selectFile.setFont(BIGFONT);
            ButtonActionListener buttonActionListener = new ButtonActionListener();
            selectFile.addActionListener(buttonActionListener);
            fileName = new JLabel("");
            fileName.setFont(REGFONT);
            JLabel multLabel = new JLabel("TP:");
            multLabel.setFont(BIGFONT);
            multBox = new JComboBox<>();
            multBox.setFont(BIGFONT);
            for(int i = 0; i<=22; i++) multBox.addItem(""+(double)i/10);
            multBox.setSelectedItem(""+DB.TPD);
            ComboBoxActionListener comboBoxActionListener = new ComboBoxActionListener();
            multBox.addActionListener(comboBoxActionListener);
            text = new JTextArea();
            text.setFont(REGFONT);
            text.setEditable(false);
            JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, SPACEW, SPACEH));
            setLayout(new BorderLayout());
            north.add(multLabel);
            north.add(multBox);
            north.add(selectFile);
            add(fileName, BorderLayout.SOUTH);
            add(north, BorderLayout.NORTH);
            add(text, BorderLayout.CENTER);
        }

        class ButtonActionListener implements ActionListener {
            public void actionPerformed(ActionEvent ae){
                JFileChooser selectFileBox = new JFileChooser("C:\\Users\\eugene\\Google Drive\\Forex\\Results");
                selectFileBox.setMultiSelectionEnabled(true);
                int returnValue = selectFileBox.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = selectFileBox.getSelectedFiles();
                    fileName.setText(getFilesNames(selectedFiles));
                }
                DB = new DataBase(getComboBoxValue(), selectedFiles);
                double[][] array = DB.getMultResult();
                String str = printarray(array);
                text.setText(str);
            }
        }

        class ComboBoxActionListener implements ActionListener {
            public void actionPerformed(ActionEvent ae){
                DB = new DataBase(getComboBoxValue(), selectedFiles);
                double[][] array = DB.getMultResult();
                String str = printarray(array);
                text.setText(str);
            }
        }

        double getComboBoxValue(){
            String str = (String) multBox.getSelectedItem();
            return Double.parseDouble(str);
        }

        String getFilesNames(File[] files){
            StringBuilder str = new StringBuilder("");
            for (File file : files) {
                str.append(file.getName()).append(" ");
            }
            return str.toString();
        }

        String printarray(double[][] array){
            StringBuilder str = new StringBuilder("");
            for (double[] anArray : array) str.append(anArray[0]).append("\t").append(anArray[1]).append("\n");
            return str.toString();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(TakeProfitGUI::new);
    }


}

