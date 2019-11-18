/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparators;

import java.util.Comparator;
import pojos.Member;

/**
 *
 * @author Gitta Szabo
 */
public class MemberNameComparator implements Comparator<Member>{

    @Override
    public int compare(Member o1, Member o2) {
        return o1.getName().compareTo(o2.getName());
    }
    
}
