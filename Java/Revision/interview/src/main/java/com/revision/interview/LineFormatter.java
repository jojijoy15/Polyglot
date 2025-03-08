package com.revision.interview;

public class LineFormatter {

  private static int CURRENT_LINE = 0;

  public int getDiffBetweenCorrectAndIncorrectFormattedLine(int[] wordsInLine, int maxCharactersPerLine, int spacesLimitPerLineAtEnd) {
    int linesInCorrectFormat = 0, linesInWrongFormat = 0;
    for(int i = 0; i < wordsInLine.length; ++i) {
      int spacesLeftInEachLine = maxCharactersPerLine - wordsInLine[i];
      if(spacesLeftInEachLine < spacesLimitPerLineAtEnd) {
        linesInCorrectFormat++;
      } else {
        linesInWrongFormat++;
      }
    }
    return Math.abs(linesInCorrectFormat - linesInWrongFormat);
  }

  public int[] increaseWordsPerLine(int[] arr, int maxCharactersPerLine) {
    int[] newWordsInLine = null;
    if(CURRENT_LINE < arr.length
        && arr[CURRENT_LINE] + arr[CURRENT_LINE + 1] < maxCharactersPerLine) {
        newWordsInLine = new int[arr.length - 1];
        newWordsInLine[CURRENT_LINE] = arr[CURRENT_LINE] + arr[CURRENT_LINE + 1] + 1; // +1 for space
        System.arraycopy(arr, 1, newWordsInLine, 1, arr.length - 1);
    }
    if(CURRENT_LINE < newWordsInLine.length
        && newWordsInLine[CURRENT_LINE] + newWordsInLine[CURRENT_LINE + 1] >= maxCharactersPerLine) {
      CURRENT_LINE++;
    }
    return newWordsInLine;
  }

}
