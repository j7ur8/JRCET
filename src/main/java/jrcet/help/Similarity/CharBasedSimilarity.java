package jrcet.help.Similarity;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import jrcet.help.Similarity.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class CharBasedSimilarity implements ISimilarity {
    private final double alpha = 0.6;
    private final double beta = 0.4;
    private static CharBasedSimilarity instance = null;

    public static CharBasedSimilarity getInstance() {
        if (instance == null) {
            instance = new CharBasedSimilarity();
        }

        return instance;
    }

    private CharBasedSimilarity() {
    }

    public double getSimilarity(String word1, String word2) {
        if (StringUtil.isBlank(word1) && StringUtil.isBlank(word2)) {
            return 1.0;
        } else if (!StringUtil.isBlank(word1) && !StringUtil.isBlank(word2)) {
            if (word1.equalsIgnoreCase(word2)) {
                return 1.0;
            } else {
                List<Character> sameChars = new ArrayList();
                String longString = StringUtil.getLongString(word1, word2);
                String shortString = StringUtil.getShortString(word1, word2);

                for(int i = 0; i < longString.length(); ++i) {
                    Character ch = longString.charAt(i);
                    if (shortString.contains(ch.toString())) {
                        sameChars.add(ch);
                    }
                }

                double dp = Math.min(1.0 * (double)word1.length() / (double)word2.length(), 1.0 * (double)word2.length() / (double)word1.length());
                double part1 = 0.6 * (1.0 * (double)sameChars.size() / (double)word1.length() + 1.0 * (double)sameChars.size() / (double)word2.length()) / 2.0;
                double part2 = 0.4 * dp * (this.getWeightedResult(word1, sameChars) + this.getWeightedResult(word2, sameChars)) / 2.0;
                return part1 + part2;
            }
        } else {
            return 0.0;
        }
    }

    private double getWeightedResult(String word, List<Character> sameChars) {
        double top = 0.0;
        double bottom = 0.0;

        for(int i = 0; i < word.length(); ++i) {
            if (sameChars.contains(word.charAt(i))) {
                top += (double)(i + 1);
            }

            bottom += (double)(i + 1);
        }

        return 1.0 * top / bottom;
    }
}
