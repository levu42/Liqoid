/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.quadrillenschule.liquidroid.model;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author andi
 */
public class Initiativen extends ArrayList<Initiative> {

    private ArrayList<Integer> existingIssueIds = new ArrayList<Integer>();
    SharedPreferences instancePrefs;

    public Initiativen(SharedPreferences instancePrefs) {
        super();
        this.instancePrefs = instancePrefs;
    }

    public Initiativen getSelectedIssues() {
        Initiativen retval = new Initiativen(instancePrefs);
        String[] selectedissues_str = instancePrefs.getString("selectedissues", "0").split(":");
        ArrayList<Integer> selectedIssues = new ArrayList<Integer>();
        for (String s : selectedissues_str) {
            try {
                selectedIssues.add(Integer.parseInt(s));
            } catch (Exception e) {
            }
        }

        for (Integer i : selectedIssues) {
            for (Initiative ini : findByIssueID(i)) {
                retval.add(ini);
            }
        }
        return retval;
    }

    public boolean isPositionSelected(int pos) {

        Initiative myini = this.get(pos);
        String[] selectedissues_str = instancePrefs.getString("selectedissues", "0").split(":");
        for (String s : selectedissues_str) {
            try {
                if (Integer.parseInt(s) == myini.issue_id) {
                    return true;
                }
            } catch (Exception e) {
            }
        }


        return false;
    }

    public boolean isIssueSelected(int issueid) {
        Initiativen myinis = findByIssueID(issueid);
        if (myinis.size() <= 0) {
            return false;
        }
        Initiative myini = myinis.get(0);
        String[] selectedissues_str = instancePrefs.getString("selectedissues", "0").split(":");
        for (String s : selectedissues_str) {
            try {
                if (Integer.parseInt(s) == myini.issue_id) {
                    return true;
                }
            } catch (Exception e) {
            }
        }


        return false;
    }

    public void setSelectedIssue(int issueid, boolean value) {

        String selectedIssuesString = instancePrefs.getString("selectedissues", "");

        if (value) {
            //Shall be selected
            if (this.getSelectedIssues().findByIssueID(issueid).size() == 0) {
                //is not selected
                selectedIssuesString = selectedIssuesString + ":" + issueid;
            } else {
                //is already selected - change noting
            }
        } else {
            //Shall not be selected
            if (this.getSelectedIssues().findByIssueID(issueid).size() == 0) {
                //is not selected do nothing
            } else {
                //is selected, unselect

                String newselectedissues = "";
                for (String snippet : selectedIssuesString.split(":")) {
                    if (!snippet.equals(issueid + "")) {
                        newselectedissues += snippet + ":";
                    }
                }
                int len = newselectedissues.length();
                if (len > 0) {
                    selectedIssuesString = newselectedissues.substring(0, len - 1);
                }
            }

        }


        SharedPreferences.Editor editor = instancePrefs.edit();
        selectedIssuesString = selectedIssuesString.replaceAll("::", ":");
        if (selectedIssuesString.startsWith(":")) {
            selectedIssuesString = selectedIssuesString.substring(1);
        }
        if (selectedIssuesString.endsWith(":")) {
            selectedIssuesString = selectedIssuesString.substring(0, selectedIssuesString.length() - 1);
        }
        editor.putString("selectedissues", selectedIssuesString);
        editor.commit();
    }

    @Override
    public boolean add(Initiative ini) {
        if (!existingIssueIds.contains((Integer) ini.issue_id)) {
            existingIssueIds.add((Integer) ini.issue_id);
            return super.add(ini);
        }
        processDuplicate(ini);
        return false;
    }

    private void processDuplicate(Initiative ini) {
        Initiative existing = findByIssueID(ini.issue_id).get(0);

    }

    @Override
    public void clear() {
        existingIssueIds.clear();
        super.clear();

    }

    @Override
    public Initiative remove(int i) {
        existingIssueIds.remove((Integer) get(i).issue_id);
        return super.remove(i);
    }

    public Initiativen findByIssueID(int find) {
        Initiativen retval = new Initiativen(instancePrefs);
        for (Initiative i : this) {
            if (i.issue_id == find) {
                retval.add(i);
            }
        }
        return retval;
    }

    public Initiativen findByName(String name) {
        Initiativen retval = new Initiativen(instancePrefs);
        for (Initiative i : this) {
            if (i.name == name) {
                retval.add(i);
            }
        }
        return retval;
    }

    public class SortByIssueCreatedComparator implements Comparator<Initiative> {
        public boolean orderNormal = true;
        public int compare(Initiative o1, Initiative o2) {
            if ((o1 == null) || (o2 == null)) {
                return 0;
            }
            long retval = 0;
            if (orderNormal) {
                retval = (o1.issue_created.getTime() - o2.issue_created.getTime());
            } else {
                retval = (o2.issue_created.getTime() - o1.issue_created.getTime());

            }
            if (retval > 0) {
                return 1;
            }
            if (retval < 0) {
                return -1;
            }
            return 0;
        }
    }

    public void sort(int comparator) {
        SortByIssueCreatedComparator c = new SortByIssueCreatedComparator();
        c.orderNormal = true;

        Collections.sort(this, c);

    }

    public void reverse(int comparator) {
        SortByIssueCreatedComparator c = new SortByIssueCreatedComparator();
        c.orderNormal = false;

        Collections.sort(this, c);

    }
}
