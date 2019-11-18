/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparators;

import java.util.Comparator;
import pojos.Book;

/**
 *
 * @author Gitta Szabo
 */
public class AuthorComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        return o1.getAuthor().compareTo(o2.getAuthor());
    }
    
}
