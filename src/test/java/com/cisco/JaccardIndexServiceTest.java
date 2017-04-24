package com.cisco;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JaccardIndexServiceTest {

    private JaccardIndexService jaccardIndexService;

    @Before
    public void setUp(){
        jaccardIndexService = new JaccardIndexService();
    }

    @Test
    public void shouldReturn1WhenTheContentMatch() {
        String text1 = "test";
        String text2 = "test";
        assertThat(jaccardIndexService.perform(text1,text2), is(equalTo(1.0F)));
    }

    @Test
    public void shouldReturn0WhenThereAreNoCommonWords(){
        String text1 = "there are no";
        String text2 = "common words";
        assertThat(jaccardIndexService.perform(text1,text2), is(equalTo(0.0F)));
    }

    @Test
    public void shouldDetectCommonWords(){
        //2 common words out of 6, 2 / 6 = 1/3 = 0.333
        String text1 = "this is an apple";
        String text2 = "this is a book";
        assertThat(jaccardIndexService.perform(text1,text2), is(equalTo(0.333F)));
    }

    @Test
    public void shouldBeCaseInsentitive(){
        String text1 = "it should Ignore CASE";
        String text2 = "it should ignore case";
        assertThat(jaccardIndexService.perform(text1,text2), is(equalTo(1.0F)));
    }

    @Test
    public void shouldIgnorePunctuation(){
        String text1 = "I know, I () have punctuation?";
        String text2 = "I know I have punctuation";
        assertThat(jaccardIndexService.perform(text1,text2), is(equalTo(1.0F)));
    }
}