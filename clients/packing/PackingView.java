package clients.packing;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Packing view.

 */

public class PackingView implements Observer
{
  private static final String PACKED = "Packed";

  private static final int H = 400;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtPack= new JButton( PACKED );
 
  private OrderProcessing theOrder     = null;
  
  private PackingController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public PackingView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                           // 
    {      
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );
    
    cp.setBackground(new Color(240, 248, 255)); //set background colour

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is
    Font boldf = new Font("Monospaced", Font.BOLD, 12); //Bold font
    
    pageTitle.setOpaque(true);
    pageTitle.setHorizontalAlignment(SwingConstants.CENTER); // Centre align the text
    pageTitle.setBounds(0, 0, W, 20);     // Centre horizontally within the window
    pageTitle.setFont(boldf);  //Bold font
    pageTitle.setForeground(Color.BLACK); //text colour
    pageTitle.setBackground(new Color(255, 223, 186)); //background colour
    pageTitle.setText( "Packing Brought Orders" ); //title
    
    cp.add( pageTitle, BorderLayout.NORTH);

    theBtPack.setBounds( 16, 55, 80, 40 );   // packed Button
    theBtPack.addActionListener(                   // Call back code
      e -> cont.doPacked() );
    cp.add( theBtPack );                          //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theSP.setBounds( 110, 55, 270, 270 );           // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
  }
  
  public void setController( PackingController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )
  {
	  PackingModel model  = (PackingModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    Basket basket =  model.getBasket();
    if ( basket != null )
    {
      theOutput.setText( basket.getDetails() );
    } else {
      theOutput.setText("");
    }
  }

}

