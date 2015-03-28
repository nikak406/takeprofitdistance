import javax.swing.*;
import java.awt.*;
import java.io.File;

abstract class AbstractPanel extends JPanel implements Constants{
    Data DB;

    JTextArea text;
    JComboBox<String> multBox;
    File[] selectedFiles;
    JLabel fileName, multLabel;
    JButton selectFile;

    AbstractPanel(String multLabelName){
        DB = new Data();
        setSize(framesizeH, framesizeW);
        setVisible(true);
        selectFile = new JButton("Select File");
        selectFile.setFont(BIGFONT);
        fileName = new JLabel("");
        fileName.setFont(REGFONT);
        multLabel = new JLabel(multLabelName);
        multLabel.setFont(BIGFONT);
        multBox = new JComboBox<>();
        multBox.setFont(BIGFONT);
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

    String printArray(double[][] array){
        StringBuilder str = new StringBuilder("");
        for (double[] anArray : array) str.append(anArray[0]).append("\t").append(anArray[1]).append("\n");
        return str.toString();
    }

    double getComboBoxValue(){
        String str = (String) multBox.getSelectedItem();
        return Double.parseDouble(str);
    }

    String getFileNames(File[] files){
        StringBuilder str = new StringBuilder("");
        for (File file : files) {
            str.append(file.getName()).append(" ");
        }
        return str.toString();
    }

    void selectFiles(){
        JFileChooser selectFileBox = new JFileChooser(filesPath);
        selectFileBox.setMultiSelectionEnabled(true);
        int returnValue = selectFileBox.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFiles = selectFileBox.getSelectedFiles();
            fileName.setText(getFileNames(selectedFiles));
        }
    }

    void displayResult(double[][] array){
        String str = printArray(array);
        text.setText(str);
    }
}
