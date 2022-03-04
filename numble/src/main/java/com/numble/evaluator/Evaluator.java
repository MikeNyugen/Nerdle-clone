package com.numble.evaluator;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    static private boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    static private Integer combine(Number left, Operation operation, Number right) {
        switch (operation) {
            case PLUS: return left.number + right.number;
            case MUL: return left.number * right.number;
            case DIV: return left.number / right.number;
            case MINUS: return left.number - right.number;
            default: throw new RuntimeException("unreachable code reached, probably bad equation used");
        }
    }

    private interface EvaluatorToken {
    }

    private static class Number implements EvaluatorToken {
        public Integer number;

        Number(int a) {
            number = a;
        }
    }

    private enum Operation implements EvaluatorToken {
        PLUS,
        MINUS,
        MUL,
        DIV;

        static Operation fromChar(char c) {
            switch (c) {
                case '+': return PLUS;
                case '-': return MINUS;
                case 'x': return MUL;
                case '/': return DIV;
                default: throw new RuntimeException("unreachable code reached, probably bad equation used");
            }
        }
    }

    private enum Bracket implements EvaluatorToken {
        OPENING,
        CLOSING;

        static Bracket fromChar(char c) {
            switch (c) {
                case '(': return OPENING;
                case ')': return CLOSING;
                default: throw new RuntimeException("unreachable code reached, probably bad equation used");
            }
        }
    }

    static private boolean isBracket(char c) {
        return c == '(' || c == ')';
    }

    static private List<EvaluatorToken> tokenize(String equation) {
        List<EvaluatorToken> result = new ArrayList<>();
        int i = 0;
        while (i < equation.length()) {
            if (isNumber(equation.charAt(i))) {
                int number = 0;
                while (i < equation.length() && isNumber(equation.charAt(i))) {
                    number *= 10;
                    number += (equation.charAt(i) - '0');
                    i++;
                }
                result.add(new Number(number));
            } else if (isBracket(equation.charAt(i))) {
                result.add(Bracket.fromChar(equation.charAt(i)));
                i++;
            } else {
                result.add(Operation.fromChar(equation.charAt(i)));
                i++;
            }
        }
        return result;
    }

    static public Integer evaluate(String equation) {
        var tokens = tokenize(equation);
        return evaluate(tokens).number;
    }

    static public Number evaluate(List<EvaluatorToken> tokens) {
        int last_token_index = tokens.size() - 1;
        if (tokens.get(last_token_index) instanceof Bracket) {
            int i = last_token_index;
            int balance = 1;
            while (balance != 0) {
                i--;
                if (tokens.get(i) instanceof Bracket && tokens.get(i) == Bracket.CLOSING) {
                    balance++;
                } else if (tokens.get(i) instanceof Bracket && tokens.get(i) == Bracket.OPENING) {
                    balance--;
                }
            }
            var last_bracket = evaluate(tokens.subList(i + 1, last_token_index));
            tokens = tokens.subList(0, i);
            tokens.add(last_bracket);
            return evaluate(tokens);
        } else {
            // last token is a number
            if (tokens.size() == 1) {
                return (Number) tokens.get(0);
            }
            Number last_number = (Number) tokens.get(last_token_index);
            Operation operation = (Operation) tokens.get(last_token_index - 1);
            return new Number(combine(evaluate(tokens.subList(0, last_token_index - 1)), operation, last_number));
        }
    }
}
