/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.quadrillenschule.liquidroid.gui;

import android.view.View;
import android.view.ViewGroup;
import de.quadrillenschule.liquidroid.InitiativesTabActivity;
import de.quadrillenschule.liquidroid.model.MultiInstanceInitiativen;

/**
 *
 * @author andi
 */
public class RecentInitiativenListAdapter extends InitiativenListAdapter {

    public RecentInitiativenListAdapter(InitiativesTabActivity activity, MultiInstanceInitiativen initiativen, int viewId) {
        super(activity, initiativen, viewId);
    }
  

    @Override
     protected View getInternalView(int position){
      RecentEventsIssueItemView retval = new RecentEventsIssueItemView(activity, initiativen.get(position));

        return retval;
    }
}
