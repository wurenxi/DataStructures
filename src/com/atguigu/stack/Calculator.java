package com.atguigu.stack;

/**
 * @author gxl
 * @description 使用栈实现计算器
 * @createDate 2022/7/24 10:15
 */
public class Calculator {
    public static void main(String[] args) {
        String expression = "7*2*2-5+1-5+3-4"; // 如何处理多位数的问题
        // 创建两个栈，数栈、符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 opStack = new ArrayStack2(10);
        // 定义需要的相关变量
        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int op = 0;
        int res = 0;
        char ch = ' '; // 将每次扫描得到char保存到ch
        String keepNum = ""; // 用于拼接多位数
        // 开始while循环的扫描expression
        while (true) {
            // 依次得到expression每一个字符
            ch = expression.substring(index, index+1).charAt(0);

            // 判断ch是什么，然后做相应的处理
            if(opStack.isOp(ch)) { // 如果是运算符
                // 判断当前符号栈是否为空
                if(!opStack.isEmpty()) {
                    // 如果符号栈有操作符，进行比较，如当前的操作符的优先级小于等于栈中的操作符，就需要从数栈中pop出两个数，
                    // 在从符号栈中pop出一个符号，进行运算，得到结果，入数栈，然后将当前的操作符入符号栈
                    if(opStack.priority(ch) <= opStack.priority(opStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        op = opStack.pop();
                        res = numStack.cal(num1, num2, op);
                        // 把运算的结果入数栈
                        numStack.push(res);
                        opStack.push(ch);
                    }else {
                        // 如果当前的操作符优先级大于栈中的操作符，就直接入符号栈。
                        opStack.push(ch);
                    }
                }else {
                    // 如果为空直接入栈
                    opStack.push(ch);
                }
            }else { // 如果是数字，直接入数栈
//                numStack.push(ch); // ? "1+3" '1' => 1
                // 分析思路
                // 1.当处理多位数时，不能发现是一个数立即入栈，因为它可能是多位数
                // 2.在处理数，需要向expression的表达式的index后再看一位，如果是数就继续扫描，如果是符号，才入栈
                // 3.因此我们需要定义一个变量 字符串变量，用于拼接

                // 处理多位数
                keepNum += ch;

                // 判断下一个字符是不是数字，如果是数字，就继续扫描，如果是运算符，则入栈
                // 注意是看后一位，不是index++
                // 如果ch已经是expression的最后一位，就直接入栈
                if(index == expression.length()-1 || opStack.isOp(expression.substring(index+1, index+2).charAt(0))) {
                    // 如果后一位是运算符，则入栈
                    numStack.push(Integer.parseInt(keepNum));
                    // 重要！！！清空keepNum
                    keepNum = "";
                }
            }
            // 让index + 1，并判断是否扫描到expression最后
            index++;
            if(index >= expression.length()) {
                break;
            }
        }

        // 当表达式扫描完毕，就顺序的从数栈和符号栈中pop出相应的数和符号，并运行。
        // 如果符号栈为空，则计算到最后的结果，数栈中只有一个数字【结果】
        while (!opStack.isEmpty()) {
            num1 = numStack.pop();
            num2 = numStack.pop();
            op = opStack.pop();
            res = numStack.cal(num1, num2, op);
            numStack.push(res); // 入栈
        }
        // 将数栈最后数，pop出，就是结果
        int res2 = numStack.pop();
        System.out.println("表达式"+expression+"="+res2);
    }
}

/**
 *
 */
class ArrayStack2 {
    private int maxSize; // 栈的大小

    private int[] stack; // 数组，模拟栈，数据放在该数组中

    private int top = -1; // top表示栈顶，初始化为-1

    // 构造器
    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    // 增加一个方法，可以返回当前栈顶的值，但不是真正的pop
    public int peek() {
        return stack[top];
    }

    // 栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈 push
    public void push(int value) {
        // 先判断栈是否满
        if(isFull()) {
            System.out.println("栈满");
            return;
        }

        top++;
        stack[top] = value;
    }

    // 出栈 pop 将栈顶的数据返回
    public int pop() {
        // 先判断栈是否空
        if(isEmpty()) {
            // 抛出异常
            throw new RuntimeException("栈空");
        }

        int value = stack[top];
        top--;

        return value;
    }

    // 显示栈的情况【遍历栈】，遍历时，需要从栈顶开始显示数据
    public void list() {
        if(isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }

        for (int i = top; i >= 0; i--) {
            System.out.println("stack["+i+"]="+stack[i]);
        }
    }

    // 返回运算符的优先级，优先级是程序员来确定的，优先级使用数字表示
    // 数字越大，则优先级越高
    public int priority(int op) {
        if(op == '*' || op == '/') {
            return 1;
        }else if(op == '+' || op == '-') {
            return 0;
        }else {
            return -1; // 假定目前的表达式只有+，-，*，/
        }
    }

    // 判断是不是一个运算符
    public boolean isOp(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    // 计算方法
    public int cal(int num1, int num2, int op) {
        int res = 0; // 存放计算的结果
        switch (op) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }

        return res;
    }
}
