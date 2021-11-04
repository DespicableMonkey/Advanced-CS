package PrayagGameOfLife;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;

/*
    this is the Controller component
 */

class Life extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private LifeView view;
    private LifeModel model;
    private JButton runButton, pauseButton, resumeButton, colorButton, resetButton ;


    /** construct a randomized starting grid */
    Life() throws IOException
    {
        this(null);
    }

    /** construct a grid using the info in text file */
    Life(String fileName) throws IOException
    {
        super("Conway's GameOfLife.Life");

        // build the buttons
        JPanel controlPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        runButton.setEnabled(true);
        controlPanel.add(runButton);
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);
        pauseButton.setEnabled(false);
        controlPanel.add(pauseButton);
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        resumeButton.setEnabled(false);
        controlPanel.add(resumeButton);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setDefaultCapable(true);
        controlPanel.add(resetButton);

        colorButton = new JButton("Random Color");
        colorButton.setDefaultCapable(true);
        colorButton.addActionListener(this);
        controlPanel.add(colorButton);



        // build the view
        view = new LifeView();
        view.setBackground(Color.white);

        // put buttons, view together
        Container c = getContentPane();
        c.add(controlPanel, BorderLayout.NORTH);
        c.add(view, BorderLayout.CENTER);

        // build the model
        model = new LifeModel(view, fileName);
    }

    public void actionPerformed(ActionEvent e)
    {



        JButton b = (JButton)e.getSource();
        if ( b == runButton )
        {
            model.run();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
            colorButton.setEnabled(true);
        }
        else if ( b == pauseButton )
        {
            model.pause();
            runButton.setEnabled(false);
            pauseButton.setEnabled(false);
            resumeButton.setEnabled(true);
            colorButton.setEnabled(true);
        }
        else if ( b == resumeButton )
        {
            model.resume();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
            colorButton.setEnabled(true);
        }
        else if( b == resetButton )
        {
            try {
                if(pauseButton.isEnabled())
                    model.pause();
                runButton.setEnabled(true);
                resumeButton.setEnabled(false);
                pauseButton.setEnabled(false);
                model.reset(view, null);
                colorButton.setEnabled(true);
            } catch(Exception ex) {

            }
        }

        else if(b == colorButton) {
            view.RandomColor();
            colorButton.setEnabled(true);
        }

    }

    public static void main(String[] args) throws IOException
    {
        Life conway = new Life(); //parameterize to start w/ a particular file

        conway.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        conway.setSize(640, 640);
        //conway.show(); //deprecated, added call below
        conway.setVisible(true);
    }
}