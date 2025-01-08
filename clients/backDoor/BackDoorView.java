package clients.backDoor;

import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class BackDoorView implements Observer
{
  private static final String RESTOCK  = "Add";
  private static final String REMOVE    = "Remove";
  private static final String CLEAR    = "Clear";
  private static final String CHECK    = "Check";
  private static final String NEW    = "Add";
 
  private static final int H = 295;       // Height of window pixels 450
  private static final int W = 400;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  theInputNo = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtClear = new JButton( CLEAR );
  private final JButton     theBtRStock = new JButton( RESTOCK );
  private final JButton     theBtRemStock = new JButton( REMOVE );
  private final JButton     theBtQuery = new JButton( CHECK );
  
  private final JLabel      proTitle  = new JLabel();
  private final JLabel      proAction  = new JLabel();
  private final JLabel  	productID   = new JLabel();
  private final JTextField  productDes   = new JTextField();
  private final JTextField  productPrice   = new JTextField();
  private final JTextField  productQuan   = new JTextField();
  private final JButton     newPro = new JButton( NEW );
  
  private StockReadWriter theStock     = null;
  private BackDoorController cont= null;


  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public BackDoorView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock = mf.makeStockReadWriter();          // Database access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );
    
    cp.setBackground(Color.GRAY); //set background colour

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is
    Font boldf = new Font("Monospaced", Font.BOLD, 12); //Bold font

    pageTitle.setOpaque(true);
    pageTitle.setHorizontalAlignment(SwingConstants.CENTER); // Centre align the text
    pageTitle.setBounds(0, 0, W, 20);     // Centre horizontally within the window
    pageTitle.setFont(boldf);  //Bold font
    pageTitle.setForeground(Color.BLACK); //text colour
    pageTitle.setBackground(Color.LIGHT_GRAY); //background colour
    pageTitle.setText( "Search products" ); //title
    
    cp.add( pageTitle, BorderLayout.NORTH);
    
    theBtQuery.setBounds( 16, 50, 80, 40 );    // Check button 
    theBtQuery.addActionListener(                   // Call back code
      e -> cont.doQuery( theInput.getText() ) );
    cp.add( theBtQuery );                           //  Add to canvas

    theBtRStock.setBounds( 16, 100, 80, 40 );   // Add Button
    theBtRStock.addActionListener(                  // Call back code
      e -> cont.doRStock( theInput.getText(),
                          theInputNo.getText() ) );
    cp.add( theBtRStock );                          //  Add to canvas
    
    theBtRemStock.setBounds( 16, 100+60*1, 80, 40 );   // Remove Button
    theBtRemStock.addActionListener(                  // Call remove code
      e -> cont.doRemStock( theInput.getText(),
                          theInputNo.getText() ) );
    cp.add( theBtRemStock );                          //  Add to canvas
    theBtClear.setBounds( 16, 100+60*2, 80, 40 );    // Clear button
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas

 
    theAction.setBounds( 25, 25 , 350, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 120, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theInputNo.setBounds( 260, 50, 120, 40 );       // Input Area
    theInputNo.setText("0");                        // 0
    cp.add( theInputNo );                           //  Add to canvas

    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    //theInput.requestFocus();                        // Focus is here
    
    proTitle.setOpaque(true);
    proTitle.setHorizontalAlignment(SwingConstants.CENTER); // Centre align the text
    proTitle.setBounds(0, 270, W, 20);     // Centre horizontally within the window
    proTitle.setFont(boldf);  //Bold font
    proTitle.setForeground(Color.BLACK); //text colour
    proTitle.setBackground(Color.LIGHT_GRAY); //background colour
    proTitle.setText( "Add New Product" ); //title
    cp.add( proTitle );
    
    proAction.setBounds( 110, 295 , 270, 20 );       // Message area
    proAction.setText( "message" );                        // Blank
    cp.add( proAction );                            //  Add to canvas
    
    productID.setOpaque(true);
    productID.setBounds( 10, 329, 120, 32 );         // Input Area
    productID.setText("DISPLAY NEXT ID");                           // Blank                        //  Add to canvas
    productID.setForeground(Color.BLACK); //text colour
    productID.setBackground(Color.WHITE); //background colour
    cp.add( productID );
    
    productID.setOpaque(true);
    productID.setBounds( 10, 329, 120, 32 );
    productID.setText("Product ID");
    productID.setForeground(Color.BLACK);
    productID.setBackground(Color.WHITE);
    cp.add( productID );
    
    productDes.setBounds( 140, 325, 240, 40 );         // Input Area
    productDes.setText("Description");                           // Blank
    cp.add( productDes );                             //  Add to canvas
    
    productPrice.setBounds( 7, 370, 125, 40 );         // Input Area
    productPrice.setText("Price");                           // Blank
    cp.add( productPrice );                             //  Add to canvas
    
    productQuan.setBounds( 140, 370, 140, 40 );         // Input Area
    productQuan.setText("Quantity");                           // Blank
    cp.add( productQuan );                             //  Add to canvas
    
    newPro.setBounds( 300, 370, 80, 40 );    // Clear button 
    newPro.addActionListener(                   // Call back code
      e -> cont.newPro(productDes.getText(), productPrice.getText(), productQuan.getText()));
    cp.add( newPro ); //  Add to canvas
    
    
  }
  
  public void setController( BackDoorController c )
  {
    cont = c;
  }

  /**
   * Update the view, called by notifyObservers(theAction) in model,
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )  
  {
    BackDoorModel model  = (BackDoorModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();
  }

}