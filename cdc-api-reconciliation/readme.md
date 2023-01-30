# Readme

## Prerequisite

- Java version 11/+
- Maven

## Requirements

- verify the consistency of the candlesticks in terms of its attributes: O, H, L, C.
- can test 1-minute chart, 5-minute chart, 15-minute chart, etc.

## How to install

- `mvn install` or `mvn clean install`

## How to run the test cases
- execute `mvn test`
- execute `mvn surefire-report:report` to generate a test report and located at `.\target\site\surefire-report.html`

## Conclusion
- Cannot verify the consistency of the candlesticks in terms of its attributes: O, H, L, C based on the continuous api call in the same candle stick time
- Can verify the consistency of the candlesticks in terms of its attributes: O, H, L, C based on the continuous api call under the same candle stick and also count the pre or post candle stick, assume that they show in post candle stick may because of the delay record.
- For validating N-minutes of Candle Stick vs Trade, we must have all the trade records for that N-minutes, while the public/get-trades api will only have maximum 225 trade records per call, and except continuous api call, is hard to get the consistency record
- incomplete data can be a real issues, if we cannot get the trade record from the start of the minutes till the end of the minutes, we may not sync the open(O) and close(C), High(H) and Low(L) result same as the candle stick already.