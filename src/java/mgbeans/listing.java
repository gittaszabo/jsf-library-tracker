/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgbeans;

import comparators.AuthorComparator;
import comparators.MemberNameComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pojos.Book;
import pojos.Member;

/**
 *
 * @author Gitta Szabo
 */
@ManagedBean
@SessionScoped
public class listing {

    private List<Member> members;
    private List<Book> books;
    private Map<Integer, Member> memberMap;
    private Member selectedMember;
    private Book selectedBook;
    private int selectedMemberId;
    private Date selectedDate;
    private List<Book> memberBooks;

    public listing() {

        members = new ArrayList<>();
        books = new ArrayList<>();

        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        members = session.createQuery("FROM Member").list();
        books = session.createQuery("FROM Book").list();
        session.close();

        Collections.sort(members, new MemberNameComparator());
        Collections.sort(books, new AuthorComparator());
        memberBooks = books;
        memberMap = new HashMap<>();
        for (Member t : members) {
            memberMap.put(t.getId(), t);
        }
    }

    public String newMember() {
        selectedMember = new Member();
        return "member";
    }

    public String memberEdit(Member t) {
        selectedMember = t;
        return "member";
    }

    public String toDeleteMember(Member t) {
        selectedMember = t;
        return "memberDelete";
    }

    public String memberDelete() {
        if (selectedMember.getBooks().isEmpty()) {
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(selectedMember);
            session.getTransaction().commit();
            session.close();
            members.remove(selectedMember);
            memberMap.remove(selectedMember.getId());
            return "members";
        } else {
            return "memberDeleteError";
        }

    }

    public String memberSave() {
        boolean newMember = false;
        if (selectedMember.getId() == null) {
            newMember = true;
        }
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(selectedMember);
        session.getTransaction().commit();
        session.close();
        if (newMember) {
            members.add(selectedMember);
            Collections.sort(members, new MemberNameComparator());
            memberMap.put(selectedMember.getId(), selectedMember);
        }
        return "members";
    }

    public String newBook() {
        selectedBook = new Book();
        return "book";
    }

    public String bookEdit(Book k) {
        selectedBook = k;
        return "book";
    }

    public String bookSave() {
        boolean newBook = false;
        if (selectedBook.getId() == null) {
            newBook = true;
            selectedBook.setMember(memberMap.get(0));
        }
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(selectedBook);
        session.getTransaction().commit();
        session.close();
        if (newBook) {
            books.add(selectedBook);
            Collections.sort(books, new AuthorComparator());
        }
        return "books";
    }

    public String toDeleteBook(Book k) {
        selectedBook = k;
        return "bookDelete";
    }

    public String bookDelete() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(selectedBook);
        session.getTransaction().commit();
        session.close();
        selectedMember = selectedBook.getMember();
        selectedMember.getBooks().remove(selectedBook);
        books.remove(selectedBook);
        return "books";
    }

    public void memberSelect() {
        selectedMember = memberMap.get(selectedMemberId);
        memberBooks = new ArrayList(selectedMember.getBooks());
        Collections.sort(memberBooks, new AuthorComparator());
    }

    public String memberBooks() {
        memberBooks = new ArrayList(selectedMember.getBooks());
        return "loan";
    }

    public String bookSelect(Book b) {
        selectedBook = b;
        selectedMember = new Member();
        return "loanSelectStatus";
    }

    public String bookReturn(Book b) {
        selectedBook = b;
        selectedBook.setDate(null);
        Member oldMember = selectedBook.getMember();
        oldMember.getBooks().remove(selectedBook);
        selectedBook.setMember(memberMap.get(0));
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(selectedBook);
        session.getTransaction().commit();
        session.close();
        return "loanSuccessful";
    }

    public String loan() {
        if (selectedMember != null && selectedDate != null) {
            Member oldMember = selectedBook.getMember();
            selectedBook.setDate(selectedDate);
            selectedBook.setMember(selectedMember);
            selectedMember.getBooks().add(selectedBook);
            memberBooks = new ArrayList<>(selectedMember.getBooks());
            Collections.sort(memberBooks, new AuthorComparator());
            oldMember.getBooks().remove(selectedBook);
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(selectedMember);
            session.update(selectedBook);
            session.getTransaction().commit();
            session.close();
            selectedMember = null;
            return "loanSuccessful";
        } else {
            return "loanError";
        }

    }

    public void allBook() {
        memberBooks = books;
        selectedMember = new Member();
    }

    public String displayLoan() {
        memberBooks = books;
        selectedMember = new Member();
        return "loan";
    }

    public void memberloan(Member t) {
        selectedMember = t;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Member getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(Member selectedMember) {
        this.selectedMember = selectedMember;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public int getSelectedMemberId() {
        return selectedMemberId;
    }

    public void setSelectedMemberId(int selectedMemberId) {
        this.selectedMemberId = selectedMemberId;
    }

    public List<Book> getMemberBooks() {
        return memberBooks;
    }

    public void setMemberBooks(List<Book> memberBooks) {
        this.memberBooks = memberBooks;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

}
