package com.ferbajoo.testthings.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;

import java.util.ArrayList;

@Foo(name = "VisitorPatternActivity", value = "Pattern visitor ejemplo", drawable = R.drawable.default_image)
public class VisitorPatternActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class ShoppingCart {
        //normal shopping cart stuff
        private ArrayList<Visitable> items;

        public double calculatePostage() {
            //create a visitor
            PostageVisitor visitor = new PostageVisitor();
            //iterate through all items
            for (Visitable item : items) {
                item.accept(visitor);
            }
            return visitor.getTotalPostage();
        }
    }

    public class PostageVisitor implements Visitor {
        private double totalPostageForCart;

        //collect data about the book
        public void visit(Book book) {
            //assume we have a calculation here related to weight and price
            //free postage for a book over 10
            if (book.getPrice() < 10.0) {
                totalPostageForCart += book.getWeight() * 2;
            }
        }

        @Override
        public void visit(CD cd) {
            
        }

        @Override
        public void visit(DVD dvd) {

        }

        //return the internal state
        public double getTotalPostage() {
            return totalPostageForCart;
        }
    }

    public interface Visitor {
        public void visit(Book book);

        //visit other concrete items
        public void visit(CD cd);
        public void visit(DVD dvd);
    }

    public class CD implements Visitable{

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }
    public class DVD implements Visitable{

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    //concrete element
    public class Book implements Visitable {
        private double price;
        private double weight;

        //accept the visitor
        public void accept(Visitor vistor) {
            vistor.visit(this);
        }

        public double getPrice() {
            return price;
        }

        public double getWeight() {
            return weight;
        }
    }

    //Element interface
    public interface Visitable {
        public void accept(Visitor visitor);
    }

}

