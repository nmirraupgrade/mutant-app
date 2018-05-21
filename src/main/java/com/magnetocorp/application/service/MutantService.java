package com.magnetocorp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnetocorp.application.domain.Dna;
import com.magnetocorp.application.dto.StatsDto;
import com.magnetocorp.application.enums.Direction;
import com.magnetocorp.application.repository.DnaRepository;
import com.magnetocorp.application.validator.DnaValidator;

/**
 * Service that contains the logic needed to analyze and persist DNA sequences and also to get data already persisted.
 * 
 * @author Nadia Mirra
 */
@Service
public class MutantService {

    public static final int MUTANT_CANDIDATE_OCCURRENCES = 4;

    private static final int HORIZONTAL_MASK = 4;
    private static final int VERTICAL_MASK = 2;
    private static final int DIAGONAL_MASK = 1;

    @Autowired
    private DnaValidator validator;

    @Autowired
    private DnaRepository repository;

    private int[][] visited;

    /**
     * Decides if a DNA sequence is mutant or not.
     * 
     * @param dna the DNA sequence to be analyzed
     * @return true if the DNA matches the criteria and is a mutant, false otherwise
     */
    public boolean isMutant(String[] dna) {
        String stringDna = convertDnaToString(dna);
        Dna found = repository.findBySequence(stringDna);

        if (found != null) {
            return found.getIsMutant();
        }

        validator.validateDNA(dna);
        int sequenceCount = 0;
        boolean isMutant;
        visited = new int [dna.length][dna.length];

        for(int lineIndex = 0; lineIndex < dna.length; lineIndex++) {
            String currentLine = dna[lineIndex];
            for (int baseIndex = 0; baseIndex < currentLine.length(); baseIndex++) {

                char currentBase = currentLine.charAt(baseIndex);
                validator.validateBase(currentBase);
                if (matchesCriteria(currentBase, dna, lineIndex, baseIndex, Direction.HORIZONTAL)) {
                    sequenceCount++;
                }
                if (matchesCriteria(currentBase, dna, lineIndex, baseIndex, Direction.VERTICAL)) {
                    sequenceCount++;
                }
                if (matchesCriteria(currentBase, dna, lineIndex, baseIndex, Direction.DIAGONAL)) {
                    sequenceCount++;
                }
            } 
        }

        isMutant = sequenceCount > 1;
        saveDnaSequence(stringDna, isMutant);
        return isMutant;
    }

    /**
     * Gets the data persisted from the database and returns the stats of the DNA sequences already processed.
     * 
     * @return StatsDto the DTO that contains the amount of humans, the amount of mutants and the ratio.
     */
    public StatsDto getStats() {
        long mutants = repository.countByIsMutantTrue();
        long humans = repository.countByIsMutantFalse();

        double ratio;
        if (humans == 0) {
            ratio = 1;
        } else {
            ratio = (double) mutants / humans;
        }

        return StatsDto.builder()
                            .countMutantDna(mutants)
                            .countHumanDna(humans)
                            .ratio(ratio)
                            .build();
    }

    /**
     * Decides if a DNA base matches all the criteria to be considered a mutant candidate or not.
     * 
     * @param base the DNA base to be analyzed
     * @param dna the DNA that is being analyzed
     * @param lineIndex the index of the line that the base belongs to
     * @param baseIndex the index of the base inside the line
     * @param direction the direction used to find the matches
     * @return true if the base matches the criteria and has the amount of occurrences required, false otherwise
     */
    private boolean matchesCriteria(char base, String[] dna, int lineIndex, int baseIndex, Direction direction) {
        int occurrences = 0;

        if (!wasVisited(lineIndex, baseIndex, direction)) {
            String currentLine = dna[lineIndex];
            if (direction == Direction.HORIZONTAL && canMatch(currentLine.length(), baseIndex)) {
                occurrences = getNextHorizontalOccurrences(base, dna[lineIndex].substring(baseIndex), 0);
            }
            if (direction == Direction.VERTICAL && canMatch(dna.length, lineIndex)) {
                occurrences = getNextVerticalOccurrences(base, dna, baseIndex);
            }
            if (direction == Direction.DIAGONAL && canMatch(currentLine.length(), baseIndex)) {
                occurrences = getNextDiagonalOccurrences(base, dna, lineIndex, baseIndex);
            }
        }
 
        if (occurrences == MUTANT_CANDIDATE_OCCURRENCES) {
            markAsVisited(lineIndex, baseIndex, occurrences, direction);
            return true;
        }
        return false;
    }

    /**
     * Returns how many times a base is repeated horizontally.
     * 
     * @param base the DNA base to be found
     * @param line the line of the DNA that the base belongs to
     * @param count how many times the base has already been found
     * @return the amount of horizontal occurrences
     */
    private int getNextHorizontalOccurrences(char base, String line, int count) {
        if (line.isEmpty() || line.toLowerCase().charAt(0) != Character.toLowerCase(base)) {
            return count;
        } else {
            return getNextHorizontalOccurrences(base, line.substring(1), count + 1);
        }
    }

    /**
     * Returns how many times a base is repeated vertically.
     * 
     * @param base the DNA base to be found
     * @param dna the DNA that is being analyzed
     * @param baseIndex the index of the base inside the line
     * @return the amount of vertical occurrences
     */
    private int getNextVerticalOccurrences(char base, String[] dna, int baseIndex) {
        int count = 0;

        for (int line = 0; line < dna.length; line++) {
            if (dna[line].toLowerCase().charAt(baseIndex) == Character.toLowerCase(base)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * Returns how many times a base is repeated diagonally
     * 
     * @param base the DNA base to be found
     * @param dna the DNA that is being analyzed
     * @param lineIndex the index of the line that the base belongs to
     * @param baseIndex the index of the base inside the line
     * @return the amount of diagonal occurrences
     */
    private int getNextDiagonalOccurrences(char base, String[] dna, int lineIndex, int baseIndex) {
        int count = 0;
        int index = baseIndex;

        for (int line = lineIndex; line < dna.length; line++) {
            int lineLength = dna[line].length();
            if (lineLength > index && dna[line].toLowerCase().charAt(index) == Character.toLowerCase(base)) {
                count++;
            } else {
                return count;
            }
            index++;
        }
        return count;
    }

    /**
     * Converts a dna to a plain String.
     * 
     * @param dna the be converted
     * @return String containing the DNA sequence
     */
    private String convertDnaToString(String[] dna) {
        StringBuilder sequence = new StringBuilder();
        for(int index = 0; index < dna.length; index++) {
            sequence.append(dna[index]);
        }
        return sequence.toString();
    }

    /**
     * Decides if it's possible to find the required amount of occurrences or not.
     * 
     * @param length the length of the sequence
     * @param index the position of the base inside the sequence
     * @return true if the base can be found the amount of times expected, false if that is not possible
     */
    private boolean canMatch(int length, int index) {
        return (length - index) + 1 > MUTANT_CANDIDATE_OCCURRENCES;
    }

    /**
     * Tells if a base has already been counted, depending on the direction.
     * 
     * @param line the line of the visited matrix to analyze
     * @param column the column of the visited matrix to analyze
     * @param direction the direction that the base could have been counted for
     * @return true if the base was already counted for that direction, false otherwise
     */
    private boolean wasVisited(int line, int column, Direction direction) {
        int visitedValue = visited[line][column];

        if (direction == Direction.HORIZONTAL && (visitedValue & (1<<2)) == HORIZONTAL_MASK) {
            return true;
        }
        if (direction == Direction.VERTICAL && (visitedValue & (1<<1)) == VERTICAL_MASK) {
            return true;
        }
        if (direction == Direction.DIAGONAL && (visitedValue & (1<<0)) == DIAGONAL_MASK) {
            return true;
        }
        return false;
    }

    /**
     * Mark a base or bases as visited in the visited matrix for the appropriate direction.
     * 
     * @param fromLine the line from to start marking as visited
     * @param fromColumn the column from to start marking as visited
     * @param occurrencesVisited the amount of bases to be marked as visited
     * @param direction the direction that the base will be marked as visited for
     */
    private void markAsVisited(int fromLine, int fromColumn, int occurrencesVisited, Direction direction) {
        int column = fromColumn;
        int line = fromLine;
        int occurrences = occurrencesVisited;
        if (direction == Direction.HORIZONTAL) {
            while(occurrences > 0) {
                visited[line][column] = visited[line][column] | (1<<2);
                column++;
                occurrences--;
            }
        }
        if (direction == Direction.VERTICAL) {
            while(occurrences > 0) {
                visited[line][column] = visited[line][column] | (1<<1);
                line++;
                occurrences--;
            }
        }
        if (direction == Direction.DIAGONAL) {
            while(occurrences > 0) {
                visited[line][column] = visited[line][column] | (1<<0);
                line++;
                column++;
                occurrences--;
            }
        }
    }

    /**
     * Saves the DNA sequence.
     * 
     * @param dna the DNA to be persisted
     * @param isMutant whether the DNA belongs to a mutant or not
     */
    private void saveDnaSequence(String dna, boolean isMutant) {
        Dna dnaToSave = Dna.builder().sequence(dna).isMutant(isMutant).build();
        repository.save(dnaToSave);
    }

}
