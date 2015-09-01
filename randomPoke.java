import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.List;
import java.net.*;
public class randomPoke extends JPanel implements ActionListener {
    static ArrayList<String> holder=new ArrayList<String>();
    static int playerNum;
    static Scanner scan = new Scanner(System.in);
    static Random gen = new Random();
    static player play;
    static JLabel label=new JLabel();
    static String pokemon="";
    static int turn=1;
    static ArrayList<String> playerNames = new ArrayList<String>();
    JTextField draftedPoke, pokeCost;
    JButton draftP;
    static JTextArea playersArea, currentPoke;
    static GridBagConstraints c;
    static JFrame frame;
    public randomPoke(ArrayList<String> playerName,String tier,JFrame f) throws IOException {
        super(new GridBagLayout());
        c = new GridBagConstraints();
        frame=f;
        frame.getContentPane().removeAll();
        
        File file = new File("tiers/"+tier+".txt");
        Scanner in = new Scanner(file);
        while (in.hasNext()) {
            holder.add(in.nextLine());
        }

        draftedPoke = new JTextField("Drafter");
        draftedPoke.setPreferredSize(new Dimension(120,24));
        c.weightx=0.5;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=1;
        frame.add(draftedPoke, c);

        pokeCost=new JTextField("Cost");
        pokeCost.setPreferredSize(new Dimension(120,24));
        c.weightx=0.5;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=1;
        c.gridy=1;
        frame.add(pokeCost,c);

        draftP = new JButton("Draft Pokemon");
        c.weightx=0.5;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=2;
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

        currentPoke=new JTextArea(9,24);
        currentPoke.setEditable(false);
        c.weightx=0.5;
        c.gridx=0;
        c.gridy=4;
        frame.add(currentPoke,c);

        frame.revalidate();
        frame.repaint();

        playerNames=playerName;
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
                        System.out.println("I am IOException hear my roar. (main method)");
                    }
                }
            });
    }

    public static void editText() {
        String players="";
        int z=1;
        for (String s : playerNames) {
            players+=s;
            players+=(" "+play.getPokes(z));
            players+=(" "+play.getMoney(z));
            players+="\n";
            z++;
        }
        pokemon=holder.get(gen.nextInt(holder.size()));
        currentPoke.setText(pokemon);
        playersArea.setText(players);
        try {
            editPicture();
        }
        catch (IOException d) {
            System.out.println("editPicture IOException");
        }
    }

    public static void editPicture() throws IOException {
        frame.remove(label);
        URL path = new URL("http://img.pokemondb.net/artwork/"+pokemon.toLowerCase()+".jpg");
        BufferedImage image = ImageIO.read(path);
        label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.revalidate();
        frame.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            try {
                doesCode(draftedPoke.getText(),pokeCost.getText());
            }
            catch(IOException z) {
                System.out.println("I am IOException hear my roar. (actionPerformed)");
            }
        }
    }

    public static void doesCode(String drafter, String cost) throws FileNotFoundException, IOException {
        if (holder.indexOf(pokemon)!=-1 && play.getMoney(playerNames.indexOf(drafter)+1)>Integer.parseInt(cost)) {
            play.addPoke(playerNames.indexOf(drafter)+1,pokemon);
            play.subtract(playerNames.indexOf(drafter)+1,Integer.parseInt(cost));
            holder.remove(holder.indexOf(pokemon));
            editText();
            turn++;
            if (turn>playerNames.size())
                turn=1;
        }
    }

    private static void createAndShowGUI()  throws IOException {
        //Create and set up the window.
        frame = new JFrame("pokeDraft");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playerNames = new ArrayList<String>();
        playerNames.add("Kenny");
        playerNames.add("Alex");
        
        //Create and set up the content pane.
        JComponent newContentPane = new randomPoke(playerNames,"OU",frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setFocusable(true);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}