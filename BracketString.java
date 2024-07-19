import java.util.Stack;

public class BracketString {
    public static boolean isValid(String s) {
        // Create a stack to hold the opening brackets
        Stack<Character> stack = new Stack<>();

        // Traverse each character in the input string
        for (char c : s.toCharArray()) {
            // If the character is an opening bracket, push it onto the stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // If the stack is empty or the top of the stack does not match the corresponding opening bracket
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), c)) {
                    return false;
                }
            }
        }

        // If the stack is empty, all opening brackets have been matched, so the string is valid
        return stack.isEmpty();
    }

    // Helper method to check if the pair of brackets matches
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println(isValid("()"));         // true
        System.out.println(isValid("()[]{}"));     // true
        System.out.println(isValid("(]"));         // false
        System.out.println(isValid("([)]"));       // false
        System.out.println(isValid("{[]}"));       // true
    }
}
