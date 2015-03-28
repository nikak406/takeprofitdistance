import javax.swing.*;

public class TakeProfitGUI implements Constants {

    TakeProfitGUI() {
        JFrame jFrame = new JFrame("TakeProfit Distance Application");
        jFrame.setSize(framesizeH, framesizeW);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setFont(REGFONT);
        jTabbedPane.addTab("TPD", new TPDPanel());
        jTabbedPane.addTab("Multiplicator", new MultPanel());

        jFrame.add(jTabbedPane);
    }

    class TPDPanel extends AbstractPanel {

        TPDPanel() {
            super("L:");
            for (int i = 0; i <= 10; i++) multBox.addItem("" + (double) i / 10);
            multBox.setSelectedItem("" + DB.multiplicator);

            selectFile.addActionListener(actionEvent -> {
                selectFiles();
                DB = new Data(selectedFiles, getComboBoxValue(), null);
                displayResult(DB.getTPDResult());
            });

            multBox.addActionListener(actionEvent -> {
                DB = new Data(selectedFiles, getComboBoxValue(), null);
                displayResult(DB.getTPDResult());
            });
        }
    }

    class MultPanel extends AbstractPanel{

        MultPanel(){
            super("TP:");
            for(int i = 0; i<=22; i++) multBox.addItem("" + (double) i / 10);
            multBox.setSelectedItem("" + DB.takeProfitDistance);

            selectFile.addActionListener(actionEvent -> {
                selectFiles();
                DB = new Data(selectedFiles, null, getComboBoxValue());
                displayResult(DB.getMultResult());
            });

            multBox.addActionListener(actionEvent -> {
                DB = new Data(selectedFiles, null, getComboBoxValue());
                displayResult(DB.getMultResult());
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TakeProfitGUI::new);
    }
}
