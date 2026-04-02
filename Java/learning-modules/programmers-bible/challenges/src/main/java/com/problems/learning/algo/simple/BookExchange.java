package com.problems.learning.algo.simple;


public class BookExchange {

    /*
        Book Exchange Calculation
            Your local Foyle's is running a scheme to promote reading amongst users.
            If you sign up for the scheme, you can buy any books from the store at a fixed cost of c.
            You can also exchange x number of books to get 1 new book.
             Write an algorithm to find the maximum number of books you can read for a given amount of money m.

                Ex, given the cost of books, c =5, and exchange rate, x=2. If you spend £10, you can read a total of 3 books.
                given the cost of books, c =1, and exchange rate, x=2. If you spend £5, you can read a total of 9 books.

                5 books purchased
                previous Total = 5
                 Itr   Remaining AfterExchange (CouldNot Exchange)  Total
                  1      5            2                  1            7
                  2      3            1                  1            8
                  3      2            1                  0            9
         And asked to provide different Edge Cases for Testing
    */
    public int maxPurchase(int fixedCost, int bookExchangeCount, int totalCost) {

        if (fixedCost <= 0 || bookExchangeCount <= 1 || totalCost <= 0) return 0;

        int totalBooksPurchased = 0;
        int remainingBooksCount = totalCost/fixedCost;
        int exchangedBooksCount = remainingBooksCount / bookExchangeCount; //current no of books after exchange
        int booksCouldNotBeExchanged = remainingBooksCount % bookExchangeCount;
        totalBooksPurchased += remainingBooksCount + exchangedBooksCount;
        remainingBooksCount = exchangedBooksCount + booksCouldNotBeExchanged;
        while(remainingBooksCount >= bookExchangeCount) {
            int booksCountAfterExchange = remainingBooksCount/bookExchangeCount;
            booksCouldNotBeExchanged = remainingBooksCount % bookExchangeCount;
            totalBooksPurchased += booksCountAfterExchange;
            remainingBooksCount = booksCountAfterExchange + booksCouldNotBeExchanged;
        }
        return totalBooksPurchased;
    }

}
