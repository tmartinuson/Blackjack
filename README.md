# Blackjack Casino Card Game

## A simple game of Blackjack versus the dealer.

## Summary
This project is a simple game of Blackjack playing against the dealer. The rules of this program will follow the 
standard rules of Blackjack in which the player must score a higher dealt hand than the dealer to win the game. 
There is plenty opportunity to customize the settings and add different variables to this game but for now I'd like to
start off simple and build up.

## Domain Analysis
- Blackjack
    - runBlackjack();
    - displayTable();
    - checkBlackjack();
    - playTurn();
    - endOfTurn();
    - playDealer();
    - checkWhoWon();
    - reset();
    - placeBets();
    - Requires Card, Dealer and Player
- Card
    - getCardValue();
    - toString();
    - swapAceValue();
    - findValue();
    - drawCard();
- Dealer
    - hit();
    - deal();
    - handTotal();
    - dealerHand();
    - toString();
    - setBust();
    - getBust();
    - hasAce();
    - swapAce();
    - addGivenCard();
- Player extends Dealer
    - subtractCash();
    - addCash();
    - getCash();
    - setCash();

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
****************OLD******************
Ideas for classes, methods and variables?
-  RunGame main();
    - Dealer
        - Hand
    - Player
        - Hand
        - Bet
        - Cash/Bank
    - Deal
        - Deck
            - Shuffle();
            - var Amount of cards/decks
            - Cards
                - var Face;
                - Value();
        - Who won
        - Rules for winning
*************************************
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 

## People that will use it:
- People who like to play card games for fun.
- People who are bored at work and are searching through the *games* folder in Windows.
- People who enjoy the thrill of victory and risking it all over some pieces of virtual paper.
- People who want to sharpen their addition skills.

## Why this project?
This project interests me because I want to program a video game to add to my project resume, and I 
also enjoy playing Blackjack with friends in games like GTA V online. It's also something that seems pretty attainable 
given my current assessment of my skills and where I'm at as a programmer of Java. I'd like to use this project to
represent my interest in working in the video game sector as a software developer and I feel it would be great to have 
this on my resume. I also want to send this to a couple of my friends after I complete this course so that they can play 
Blackjack without having to load up GTA V and see something that I created!

## User Stories
- As a user, I want to be able to play a solo game of blackjack and win/lose.
- As a user, I want to be able to bet certain amounts.
- As a user, I want to be able to view the cards in my possession.
- As a user, I want to be able to add multiple cards to a hand.
- As a user, I want to be able to save my game.
- As a user, I want to be able to load my save and resume where I left off.

##Phase 4: Task 2 - Type Hierarchy
- Type hierarchy for BlackjackGUI.java and Blackjack.java(Console version) extending abstract class PlayableGame.java
- Overridden abstract class methods from PlayableGame
    - runGame();
    - displayHands(boolean dealerTurn);
    - loadPrompt();
    - checkWhoWon();
    - placeBets();
    - reset();

