package edu.upenn.cit594.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code CSVReader} provides a stateful API for streaming individual CSV rows
 * as arrays of strings that have been read from a given CSV file.
 *
 * @author YOUR-NAME-HERE
 */
public class CSVReader {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5130409650040L;
    private final CharacterReader reader;

    public CSVReader(CharacterReader reader) {
        this.reader = reader;
    }

    /**
     * This method uses the class's {@code CharacterReader} to read in just enough
     * characters to process a single valid CSV row, represented as an array of
     * strings where each element of the array is a field of the row. If formatting
     * errors are encountered during reading, this method throws a
     * {@code CSVFormatException} that specifies the exact point at which the error
     * occurred.
     *
     * @return a single row of CSV represented as a string array, where each
     *         element of the array is a field of the row; or {@code null} when
     *         there are no more rows left to be read.
     * @throws IOException when the underlying reader encountered an error
     * @throws CSVFormatException when the CSV file is formatted incorrectly
     */
    public String[] readRow() throws IOException, CSVFormatException {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        int state = 0; // using an integer to denote the type of field, 0 = Unquoted field, 1 = Quoted field, 2 = Quote in quote field
        int line = 1, column = 1, row = 0, fieldNumber = 1;

        while (true){
            int c = reader.read();
            if (c == -1) break;
            // switch-case and stringbuilder work with actual char
            char ch = (char) c;


            switch (state){
                case 0: //Unquoted fields
                    switch(ch){
                        case ',':
                            fields.add(field.toString());
                            field.setLength(0);
                            fieldNumber++;
                            // break out of the ch switch case statement, continue to execute the rest code for state == 0
                            break;
                        case '\n':
                            fields.add(field.toString());
                            field.setLength(0);
                            row++;
                            fieldNumber = 1;
                            line++;
                            column = 1;
                            return fields.toArray(new String[0]);
                        case '\r':
                            // when encountered a CR, there should always be a LF following.
                            ch = (char)reader.read();
                            if (ch != '\n'){
                                throw new CSVFormatException("Format Error", line, column, row, fieldNumber);
                            }
                            fields.add(field.toString());
                            field.setLength(0);
                            row++;
                            fieldNumber = 1;
                            line++;
                            column = 1;
                            return fields.toArray(new String[0]);
                        case '"':
                            if (field.length() != 0){
                                throw new CSVFormatException("Format Error", line, column, row, fieldNumber);
                            }
                            state = 1;
                            break;
                        default:
                            field.append(ch);
                            break;
                    }
                    // break out of the state switch case statement, continue to execute the rest code in the while loop
                    break;

                case 1: // Quoted field
                    if (ch == '"'){
                        state = 2;
                    } else {
                        field.append(ch);
                    }
                    break;

                case 2: //Quote in quote field
                    switch (ch){
                        // if encountered a ',' after seeing two '"'
                        case ',':
                            state = 0;
                            fields.add(field.toString());
                            field.setLength(0);
                            fieldNumber++;
                            break;
                        // if encountered a '"' after seeing two '"'
                        case '"':
                            field.append(ch);
                            state = 1;
                            break;
                        // if encountered a line break, it means that it's time to exit and return
                        case '\n':
                            state = 0;
                            fields.add(field.toString());
                            row++;
                            fieldNumber = 1;
                            line++;
                            column = 1;
                            return fields.toArray(new String[0]);
                        case '\r':
                            ch = (char)reader.read();
                            if (ch != '\n'){
                                throw new CSVFormatException("Format Error", line, column, row, fieldNumber);
                            }
                            state = 0;
                            fields.add(field.toString());
                            row++;
                            fieldNumber = 1;
                            line++;
                            column = 1;
                            return fields.toArray(new String[0]);
                        // everything else is not allowed after seeing two '"'
                        default:
                            throw new CSVFormatException("Format Error", line, column, row, fieldNumber);

                    }
                    break;
            }
            // how is column different from fieldNumber, and how is line different from row?
            column++;

        }
        // if the last line doesn't end with a line break.
        // we will exit the while loop, but still need to process the last field
        if (field.length() > 0){
            fields.add(field.toString());
        }
        if (fields.size() > 0){
            return fields.toArray(new String[0]);
        } else{
            return null; // no more rows
        }
    }



}
