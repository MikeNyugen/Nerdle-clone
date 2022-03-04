package com.numble.EvaluatorTests;

import com.numble.evaluator.Evaluator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class EvaluatorTest {
    @Test
    public void test() {
        assertEquals(42, Evaluator.evaluate("42"));
        assertEquals(179, Evaluator.evaluate("178+1"));
        assertEquals(179, Evaluator.evaluate("180-1"));
        assertEquals(179, Evaluator.evaluate("3x60-1"));
        assertEquals(12, Evaluator.evaluate("1+2+9"));
        assertEquals(12, Evaluator.evaluate("2x4+4"));
        assertEquals(12, Evaluator.evaluate("4+2x2"));
        assertEquals(179, Evaluator.evaluate("179x2/2"));
        assertEquals(8, Evaluator.evaluate("4+(2x2)"));
        assertEquals(20, Evaluator.evaluate("4+(6+2x2)"));
        assertEquals(14, Evaluator.evaluate("4+(6+(2x2))"));
    }
}
