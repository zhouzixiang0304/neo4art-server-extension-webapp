package it.inserpio.neo4art.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Book extends IdentifiableEntity {

    @Indexed(unique = true)
    private String bookId;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "bookName")
    private String bookName;


    public Book() {/* NOOP */}

    public Book(String bookId, String name) {
        super();

        this.bookId = bookId;
        this.bookName = name;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "graphId=" + this.getGraphId() +
                ", bookId=" + this.bookId +
                ", bookName=" + this.bookName +
                //", #clickedProductsRelationships=" + this.clickedProductsRelationships.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (bookId != null ? !bookId.equals(book.bookId) : book.bookId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        return result;
    }
}
