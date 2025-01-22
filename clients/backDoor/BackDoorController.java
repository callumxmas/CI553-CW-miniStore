package clients.backDoor;


/**
 * The BackDoor Controller
 */

public class BackDoorController
{
  private BackDoorModel model = null;
  private BackDoorView  view  = null;
  public boolean remchecked = false;
  public boolean rchecked = false;
  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public BackDoorController( BackDoorModel model, BackDoorView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Query interaction from view
   * @param pn The product number to be checked
   */
  public void doQuery( String pn )
  {
    model.doQuery(pn);
  }
  
  /**
   * RStock interaction from view
   * @param pn       The product number to be re-stocked
   * @param quantity The quantity to be re-stocked
   */
  public void doRStock( String pn, String quantity ) //add stock
  {
    model.doRStock(pn, quantity, rchecked);
    rchecked = !rchecked; //switch checked
  }
  
  public void doRemStock( String pn, String quantity) //remove stock
  {
    model.doRemStock(pn, quantity, remchecked);
    remchecked = !remchecked; //switch checked
  }

  /**
   * Clear interaction from view
   */
  public void doClear()
  {
    model.doClear();
  }
  
  public void newPro(String desc, String price, String quantity) {
    //model.newPro(desc, price, quantity);
  }

}

