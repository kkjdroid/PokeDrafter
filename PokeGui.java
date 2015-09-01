import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class PokeGui extends JPanel implements ActionListener {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    static boolean draftIsOrdered = true;
    static ArrayList<String> players = new ArrayList<String>();
    static JFrame frame;
    static JTextField playerName;
    static JComboBox tier;
    JTextArea playersArea;
    JButton addPlayer;

    public static void main(String[] args)  {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
            {
                public void run() {
                    createAndShowGUI();
                }
            });
    }

    public PokeGui()  {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        playerName = new JTextField("Player name");
        playerName.setPreferredSize(new Dimension(120, 24));
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        this.add(playerName, c);

        addPlayer = new JButton("Add player");
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        addPlayer.addActionListener(this);
        addPlayer.setActionCommand("add");
        this.add(addPlayer, c);
        
        File[] tiers = new File("tiers").listFiles();
        java.util.List<String> tiers2 = new ArrayList<String>();
        for(File f : tiers)
        {
            tiers2.add(f.toString().substring(6,f.toString().length() - 4));
        }
        Collections.sort(tiers2, String.CASE_INSENSITIVE_ORDER);
        String[] tiers3 = tiers2.toArray(new String[tiers2.size()]);
        tier = new JComboBox(tiers3);
        tier.setPreferredSize(new Dimension(120,24));
        c.weightx=0.5;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=0;
        c.gridwidth = 2;
        this.add(tier,c);
        
        JRadioButton ordered = new JRadioButton("Ordered draft");
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        ordered.setSelected(true);
        ordered.addActionListener(this);
        ordered.setActionCommand("ordered");
        this.add(ordered, c);

        JRadioButton random = new JRadioButton("Random draft");
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        random.addActionListener(this);
        random.setActionCommand("random");
        this.add(random, c);

        ButtonGroup isOrdered = new ButtonGroup();
        isOrdered.add(ordered);
        isOrdered.add(random);

        playersArea = new JTextArea(9, 24);
        playersArea.setEditable(false);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        this.add(playersArea, c);

        JButton startButton = new JButton("Start draft");
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        startButton.addActionListener(this);
        startButton.setActionCommand("start");
        this.add(startButton, c);
    }

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("ordered"))
        {
            draftIsOrdered = true;
        }
        else if(e.getActionCommand().equals("random"))
        {
            draftIsOrdered = false;
        }
        else if(e.getActionCommand().equals("add"))
        {
            if(players.size() == 7)
            {
                addPlayer.setEnabled(false);
            }
            String newName = playerName.getText();
            while(players.indexOf(newName) != -1)
            {
                if(Character.isDigit(newName.charAt(newName.length() - 1)))
                {
                    int d = Integer.parseInt(newName.substring(newName.length() - 1)) + 1;
                    newName = newName.substring(0,newName.length() - 1) + d;
                }
                else if (newName.charAt(newName.length() - 1) == ' ')
                {
                    newName += "2";
                }
                else
                {
                    newName += " 2";
                }
            }
            players.add(newName);
            String playersString = "";
            for(String s : players)
            {
                playersString += s;
                playersString += "\n";
            }
            playersArea.setText(playersString);
        }
        else if(e.getActionCommand() == "start")
        {
            try {
                draftRunner(draftIsOrdered);
            }
            catch(IOException z) {

            }
        }

    }

    public static void draftRunner(boolean draftIsOrdered) throws IOException {
        if(draftIsOrdered)
        {

            new draftPoke(players,tier.getSelectedItem().toString(),frame);
        }
        else
        {
            new randomPoke(players,tier.getSelectedItem().toString(),frame);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()  {
        //Create and set up the window.
        frame = new JFrame("RadioButtonDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new PokeGui();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setFocusable(true);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
