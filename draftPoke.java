import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.List;
import java.net.*;
public class draftPoke extends JPanel implements ActionListener {
    static ArrayList<String> holder=new ArrayList<String>();
    static int playerNum;
    static Scanner scan = new Scanner(System.in);
    static Random gen = new Random();
    static player play;
    static JLabel label;
    static String pokemon;
    static int turn=1;
    static ArrayList<String> playerNames = new ArrayList<String>();
    JTextField draftedPoke;
    JButton draftP;
    static JTextArea playersArea;
    static JFrame frame;
    static GridBagConstraints c;
    public draftPoke(ArrayList<String> playerNames,String tier,JFrame f) throws IOException {
        super(new GridBagLayout());
        c = new GridBagConstraints();
        frame=f;
        frame.getContentPane().removeAll();
        
        this.playerNames=playerNames;

        File file = new File("tiers/"+tier+".txt");
        Scanner in = new Scanner(file);
        while (in.hasNext()) {
            holder.add(in.nextLine());
        }

        String path = ("images/OP.jpg");
        File fileP = new File(path);
        BufferedImage image = ImageIO.read(fileP);
        label = new JLabel(new ImageIcon(image));
        c.weightx=0.5;
        c.gridx=0;
        c.gridy=0;
        frame.add(label,c);

        draftedPoke = new JTextField("Draft pokemon");
        draftedPoke.setPreferredSize(new Dimension(120,24));
        c.weightx=0.5;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=1;
        frame.add(draftedPoke, c);

        draftP = new JButton("Draft Pokemon");
        c.weightx=0.5;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=1;
        c.gridy=1;
        draftP.addActionListener(this);
        draftP.setActionCommand("add");
        frame.add(draftP,c);

        playersArea=new JTextArea(9,24);
        playersArea.setEditable(false);
        c.weightx=0.5;
        c.gridx=0;
        c.gridy=3;
        frame.add(playersArea,c);

        frame.revalidate();
        frame.repaint();

        playerNum=playerNames.size();
        play=new player(playerNum);
        for (int i=0; i<playerNum; i++)
            play.setName(i,playerNames.get(i));
        editText();
    }

    public static void main(String [] args) throws IOException{
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
            {
                public void run() {
                    try {
                        createAndShowGUI();
                    }
                    catch(IOException z) {
                        System.out.println("I am IOException hear my roar.");
                    }
                }
            });
    }

    public static void editText() {
        String players="";
        int z=0;
        for (String s : playerNames) {
            players+=s;
            players+=(" "+play.getPokes(z+1));
            players+="\n";
            z++;
        }
        playersArea.setText(players);
    }

    public static void editPicture(String pokemon) throws IOException {
        frame.remove(label);
        URL path = new URL("http://img.pokemondb.net/artwork/"+pokemon.toLowerCase()+".jpg");
        BufferedImage image = ImageIO.read(path);
        label = new JLabel(new ImageIcon(image));
        c.weightx=0.5;
        c.gridx=0;
        c.gridy=0;
        frame.add(label,c);
        frame.revalidate();
        frame.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            try {
                doesCode(draftedPoke.getText());
            }
            catch(IOException z) {
                System.out.println("I am IOException hear my roar.");
            }
        }
    }

    public static void doesCode(String pokemon) throws FileNotFoundException, IOException {
        if (holder.indexOf(pokemon)!=-1) {
            play.addPoke(turn,pokemon);    
            holder.remove(holder.indexOf(pokemon));
            editText();
            editPicture(pokemon);
            turn++;
            if (turn>playerNames.size())
                turn=1;
        }
    }

    private static void createAndShowGUI()  throws IOException {
        //Create and set up the window.
        frame = new JFrame("pokeDraft");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<String> test = new ArrayList<String>();
        test.add("Kenny");
        test.add("Alex");

        //Create and set up the content pane.
        JComponent newContentPane = new draftPoke(test,"OU",frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setFocusable(true);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}