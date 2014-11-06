package com.twitter.penguin.korean;

import scala.collection.JavaConversions;
import scala.collection.Seq;

import com.twitter.penguin.korean.stemmer.KoreanStemmer;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken;
import com.twitter.penguin.korean.TwitterKoreanProcessor.KoreanSegment;
import com.twitter.penguin.korean.TwitterKoreanProcessor.KoreanSegmentWithText;

/**
 * Java wrapper for TwitterKoreanProcessor using Builder pattern
 */
public class TwitterKoreanProcessorJava {
  private boolean stemmerEnabled;
  private boolean normalizerEnabled;

  private TwitterKoreanProcessorJava(boolean normalizerEnabled, boolean stemmerEnabled) {
    // Use the builder to instantiate this
    this.stemmerEnabled = stemmerEnabled;
    this.normalizerEnabled = normalizerEnabled;
  }

  /**
   * Normalize Korean text
   * 그랰ㅋㅋㅋㅋㅋㅋ -> 그래ㅋㅋ
   *
   * @param text Input text.
   * @return Normalized text.
   */
  public CharSequence normalize(CharSequence text) {
    return TwitterKoreanProcessor.normalize(text);
  }

  /**
   * Stem Korean Verbs and Adjectives
   *
   * @param text Input text.
   * @return StemmedTextWithTokens(text, tokens)
   */
  public KoreanStemmer.StemmedTextWithTokens stem(CharSequence text) {
    return TwitterKoreanProcessor.stem(text);
  }

  /**
   * Tokenize with the builder options.
   *
   * @param text Input text.
   * @return A list of Korean Tokens
   */
  public Iterable<KoreanToken> tokenize(CharSequence text) {
    Seq<KoreanToken> tokenized = TwitterKoreanProcessor.tokenize(
        text, normalizerEnabled, stemmerEnabled
    );
    return JavaConversions.seqAsJavaList(tokenized);
  }

  /**
   * Tokenize with the builder options into a String Iterable.
   *
   * @param text Input text.
   * @return An iterable of token strings.
   */
  public Iterable<String> tokenizeToStrings(CharSequence text) {
    Seq<String> tokenized = TwitterKoreanProcessor.tokenizeToStrings(
        text, normalizerEnabled, stemmerEnabled
    );
    return JavaConversions.seqAsJavaList(tokenized);
  }

  /**
   * Tokenize into KoreanSegments, which includes the indices
   *
   * @param text Input text.
   * @return An iterable of KoreanSegments.
   */
  public Iterable<KoreanSegment> tokenizeWithIndex(CharSequence text) {
    return JavaConversions.seqAsJavaList(
        TwitterKoreanProcessor.tokenizeWithIndex(text)
    );
  }

  /**
   * Tokenize into KoreanSegmentWithText, which includes
   * the stemmed text and the KoreanSegments
   *
   * @param text Input text.
   * @return KoreanSegmentWithText(text, KoreanSegments)
   */
  public KoreanSegmentWithText tokenizeWithIndexWithStemmer(CharSequence text) {
    return TwitterKoreanProcessor.tokenizeWithIndexWithStemmer(text);
  }

  /**
   * Builder for TwitterKoreanProcessorJava
   */
  public static final class Builder{
    private boolean normalizerEnabled = true;
    private boolean stemmerEnabled = true;

    public Builder disableNormalizer() {
      normalizerEnabled = false;
      return this;
    }
    public Builder disableStemmer() {
      stemmerEnabled = false;
      return this;
    }

    public TwitterKoreanProcessorJava build(){
      return new TwitterKoreanProcessorJava(normalizerEnabled, stemmerEnabled);
    }
  }
}
