# Telegram Stock Bot
This Telegram bot allows you to retrieve the latest stock prices for a given stock at the end of the day. Simply send a command to the bot with the stock ticker symbol, and it will retrieve the "High price", "Low Price", "Open Price", and "Close Price" of the stock.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

## Prerequisites
* Telegram account
* Java 8
* Maven

## Installing
* Clone the repository: git clone https://github.com/moisesvidal09/StockBot.git
* Navigate to the project directory
* Install the dependencies: mvn clean install
* Peform the following command to start the app in test mode: mvn spring-boot:run -Dspring-boot.run.profiles=test -f pom.xml

## How to use
* Add the bot to your Telegram contacts by searching for @StockMarketBrBot in the search field.
* Send a message to the bot with the command /ajuda to get Help in how to add/remove/retrieve stocks in your wallet
* Once you add stocks to your wallet, the bot will respond with the latest stock prices.


## Supported exchanges
The bot currently supports stock prices from the following exchanges:

B3 (Brasil Bolsa Balcão)
