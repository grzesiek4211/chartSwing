import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class App  {

    public static String fileName;

    public static void main(String args[]) {

        JFrame frame = new JFrame("Chart Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new GridBagLayout()); // the panel is not visible in output
        //panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));


        JLabel label = new JLabel("File:");
        JLabel labe2 = new JLabel("Parameter:");
        JButton button = new JButton("Execute");

        JComboBox comboBox1 = new JComboBox();
        JComboBox comboBox2 = new JComboBox();

        comboBox1.addItem("SOB.LAS");
        comboBox1.addItem("K.las");
        comboBox1.addItem("W.LAS");
        comboBox1.setSelectedIndex(-1);
        JCheckBox checkBox = new JCheckBox("logarithmic scale");

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,0,5,5);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(label,c); // Components Added using Flow Layout
        c.gridx = 0;
        c.gridy = 2;
        panel.add(comboBox1,c); // Components Added using Flow Layout
        //panel.add(Box.createRigidArea(new Dimension(0,50)));
        c.gridx = 0;
        c.gridy = 3;
        panel.add(labe2,c); // Components Added using Flow Layout
        c.gridx = 0;
        c.gridy = 4;
        panel.add(comboBox2,c); // Components Added using Flow Layout
        c.gridx = 0;
        c.gridy = 5;
        //panel.add(Box.createRigidArea(new Dimension(0,50)));
        panel.add(button,c);
        c.gridx = 0;
        c.gridy = 6;
        panel.add(checkBox,c);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fileName = (String) comboBox1.getSelectedItem();

                setReader();

                DefaultComboBoxModel model = new DefaultComboBoxModel( Reader.parameters.toArray() );
                model.removeElementAt(0);
                comboBox2.setModel(model);

                comboBox2.setEnabled(true);
            }
        });


        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double du = 0;

                String parametr = (String) comboBox2.getSelectedItem();

                int draw = Reader.parameters.indexOf(parametr);

                for (int i = draw; i < Reader.values.size(); i+=(Reader.parameters.size()))
                {
                    du = Reader.values.get(i);
                    if(du  == -999.000 || du == 999.000 || du == -1) continue;
                    if (Reader.values.get(i) <=  0)
                    {
                        checkBox.setSelected(false);
                        checkBox.setEnabled(false);
                        return;
                    }
                    else checkBox.setEnabled(true);
                }
            }
        });

        frame.getContentPane().add(BorderLayout.EAST, panel);

        frame.setVisible(true);
    }

    public static void setReader()
    {
        Reader reader = new Reader();
        reader.ReadFromFile(fileName);
    }
}
