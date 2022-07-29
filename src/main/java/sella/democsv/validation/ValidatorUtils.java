package sella.democsv.validation;

import org.springframework.stereotype.Service;
import sella.democsv.exception.EmptyFileException;
import sella.democsv.exception.IncorrectFileNameException;

@Service
public class ValidatorUtils {

    public static void validateArgs(String[] args) throws EmptyFileException, IncorrectFileNameException {
        if ( args.length == 0)
            throw new EmptyFileException("Please provide the csv file!");

        if (!args[0].endsWith(".csv"))
            throw new IncorrectFileNameException("The file you entered " + args[0] + " is not correct!");
    }
}
