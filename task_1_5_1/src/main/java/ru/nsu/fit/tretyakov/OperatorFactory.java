package ru.nsu.fit.tretyakov;

import ru.nsu.fit.tretyakov.operators.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the factory-class which produces specific operator by
 * specific token. It also can take user's factories and add them to
 * the list of all operators.
 */
public class OperatorFactory {
    private final List<Operator> operatorList;

    /**
     * Constructor of the factory.
     * It creates current list of operators and initialize it.
     */
    public OperatorFactory() {
        operatorList = new ArrayList<>();
        initOperatorsList();
    }

    /**
     * This method adds user's list of operators.
     *
     * @param userMap is the required user's list of operators which
     *                will be added to the current operators list.
     */
    public void addOperatorsList(List<Operator> userList) {
        operatorList.addAll(userList);
    }

    /**
     * Main method of the class. It runs (returns) specific operator by the
     * string token of the operator.
     *
     * @param operator is the string token of the operator.
     * @return suitable operator in the operators' list.
     */
    public Operator runOperator(String token) {
        for (var operator: operatorList){
            if (operator.getToken().equals(token)){
                return operator;
            }
        } return null;
    }

    private void initOperatorsList() {
        operatorList.add(new Plus());
        operatorList.add(new Minus());
        operatorList.add(new Multiply());
        operatorList.add( new Divide());
        operatorList.add(new Sinus());
        operatorList.add(new Log());
        operatorList.add(new Sqrt());
        operatorList.add(new Pow());
        operatorList.add(new Rad());
        operatorList.add(new Rad());
    }
}
