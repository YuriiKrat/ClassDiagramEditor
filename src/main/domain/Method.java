package main.domain;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 09.05.16.
 */
public class Method extends Property {

    private LinkedList<Argument> arguments;

    public Method() {
        super();
        arguments = new LinkedList<>();
    }

    @Override
    public boolean parse(String str) {
        boolean flag = true;
        if (str.indexOf('(') != -1) {
            String tmpStr = str.substring(0, str.indexOf('('));
            if (tmpStr.length() > 0 && tmpStr.matches("\\w*")) {
                setName(tmpStr);
            }
            else {
                flag = false;
            }
        }
        else {
            flag = false;
        }
        if (str.lastIndexOf(':') + 1 != -1) {
            String tmpStr1 = str.substring(str.lastIndexOf(':') + 1);
            if (tmpStr1.length() > 0 && tmpStr1.matches("\\w*")) {
                setType(tmpStr1);
            }
            else {
                flag = false;
            }
        }
        else {
            flag = false;
        }
        if (str.indexOf('(') != -1 && str.indexOf(')') != -1) {
            String[] strArguments = str.substring(str.indexOf('(') + 1, str.indexOf(')')).split(",");
            arguments.clear();
            if (strArguments.length >= 1 && !strArguments[0].equals("")) {
                for (String argument : strArguments) {
                    String[] signature = argument.split(":");
                    if (signature.length != 2 || !signature[1].matches("\\w*") || !signature[0].matches("\\w*")) {
                        return false;
                    }
                    Argument newArgument = new Argument(signature[1], signature[0]);
                    arguments.add(newArgument);
                }
            }
        }
        else {
            flag = false;
        }
        return flag;
    }

    @Override
    public String toString() {
        String str = super.toString();
        if (str.length() > 0) {
            str += "(";
            Iterator<Argument> i = arguments.iterator();
            while (i.hasNext()) {
                Argument argument = i.next();
                str += argument.getType() + " " + argument.getName();
                if (i.hasNext()) {
                    str += ", ";
                }
            }
            str += ")";
        }
        return str;
    }
}
