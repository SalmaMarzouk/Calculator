package eg.edu.alexu.csd.oop.calculator.cs32;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc implements Calculator {
    private String input;
    private String[] lastOp;
    private Integer index;
    private Integer i;

    public Calc() {
        lastOp = new String[5];
        index = 0;
        i = 0;
    }

    /* Take input from user */
    public void input(String s) {
        input = s;
    }

    public Integer getIndex() {
        return index;
    }

    /* Return the result of the current operations or throws a runtime
        exception */
    public String getResult() {
        double result;
        if (!check()) {
            throw new RuntimeException("Invalid Expression");
        }
        Operation op = getOperation();
        switch (op.getOperator()) {
            case '+':
                result = op.getOperand1() + op.getOperand2();
                break;
            case '-':
                result = op.getOperand1() - op.getOperand2();
                break;
            case '*':
                result = op.getOperand1() * op.getOperand2();
                break;
            case '/':
                if (op.getOperand2() == 0)
                    throw new RuntimeException("Don't divide by zero");
                result = op.getOperand1() / op.getOperand2();
                break;
            default:
                result = op.operand1;
        }
        getLastOp(input);
        return Double.toString(result);
    }

    public Integer getI() {
        return i;
    }

    /* return the current formula */
    public String current() {
        if (index == 0)
            return null;
        return lastOp[i];
    }

    /* return the last operation in String format, or Null if no more history
    available, will update current */
    public String prev() {
        if (i == 0) {
            return null;
        }
        i--;
        return lastOp[i];
    }

    /* return the next operation in String format, or Null if no more history
    available, will update current */
    public String next() {
        if (i == 4) {
            return null;
        }
        i++;
        return lastOp[i];
    }

    /* Save in file the last 5 done Operations */
    public void save() {
        try {
            FileOutputStream opFile = new FileOutputStream(new File("save.txt"));
            ObjectOutputStream opObject = new ObjectOutputStream(opFile);
            opObject.writeObject(lastOp);
            opObject.writeObject(index);
            opObject.writeObject(i);
            opObject.close();
            opFile.close();
        } catch (FileNotFoundException s) {
            System.out.println("File not Found");
        } catch (IOException a) {
            System.out.println("Error");
        }
    }

    /* Load from file the saved operations */
    public void load() {
        try {
            FileInputStream opFile = new FileInputStream(new File("save.txt"));
            ObjectInputStream opObject = new ObjectInputStream(opFile);
            lastOp = (String[]) opObject.readObject();
            index = (Integer) opObject.readObject();
            i = (Integer) opObject.readObject();
            opObject.close();
            opFile.close();
        } catch (FileNotFoundException s) {
            System.out.println("File not Found");
        } catch (IOException a) {
            System.out.println("Error");
        } catch (ClassNotFoundException l) {
            System.out.println("Error !");
        }
    }

    private boolean check() {
        Pattern ipPattern = Pattern.compile("^(([-])?\\d+([.]\\d+)?)(([+]|[-]|[*]|[/])(([-])?\\d+([.]\\d+)?))?$");
        Matcher ipMatcher = ipPattern.matcher(input);
        return ipMatcher.matches();
    }

    private Operation getOperation() {
        Operation op = new Operation();
        Pattern ipPattern = Pattern.compile("^(([-])?\\d+([.]\\d+)?)(([+]|[-]|[*]|[/])(([-])?\\d+([.]\\d+)?))?$");
        Matcher ipMatcher = ipPattern.matcher(input);
        ipMatcher.matches();
        op.setOperand1(Double.parseDouble(ipMatcher.group(1)));
        if (ipMatcher.group(5) != null) {
            op.setOperand2(Double.parseDouble(ipMatcher.group(6)));
            op.setOperator(ipMatcher.group(5).charAt(0));
        }
        return op;
    }

    private class Operation {
        private Operation() {
            operator = 's';
        }

        private double operand1;
        private double operand2;
        private char operator;

        public double getOperand1() {
            return operand1;
        }

        public void setOperand1(double operand1) {
            this.operand1 = operand1;
        }

        public double getOperand2() {
            return operand2;
        }

        public void setOperand2(double operand2) {
            this.operand2 = operand2;
        }

        public char getOperator() {
            return operator;
        }

        public void setOperator(char operator) {
            this.operator = operator;
        }
    }

    private void getLastOp(String Op) {
        if (index > 4) {
            for (int i = 0; i < 4; i++) {
                lastOp[i] = lastOp[i + 1];
            }
            index--;
        }
        lastOp[index++] = Op;
        i = index - 1;
    }

}
