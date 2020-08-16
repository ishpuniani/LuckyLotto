package com.poppulo.utils;

import com.poppulo.entity.Line;
import com.poppulo.entity.LineInTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LineUtils {
    private static final Logger logger = LoggerFactory.getLogger(LineUtils.class);

    public static int lineSize;
    private static List<Character> lineValues;

    public static int getLineSize() {
        return lineSize;
    }

    @Value("${org.poppulo.luckyLotto.lineSize}")
    public void setLineSize(int lineSize){
        LineUtils.lineSize = lineSize;
    }

    @Value("${org.poppulo.luckyLotto.lineValues}")
    public void setLineValues(String lineValuesStr) {
        lineValuesStr = lineValuesStr.replace(",","");
        LineUtils.lineValues = lineValuesStr.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }


    private static final Random random = new Random();

    public static List<Line> generateLines(int lineCount) {
        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < lineCount; i++) {
            lines.add(generateRandomLine());
        }
        return lines;
    }

    private static Line generateRandomLine() {
        Line line = new Line();
        List<Character> elements = new ArrayList<Character>();
        for (int i = 0; i < lineSize; i++) {
            int index = randomIndex(0, lineValues.size());
            char val = lineValues.get(index);
            elements.add(val);
        }
        line.setElements(elements);
        return line;
    }

    public static int randomIndex(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static List<LineInTicket> getLineMapping(UUID ticketId, List<Line> lines) {
        List<LineInTicket> lineInTickets = new ArrayList<LineInTicket>();
        for(Line line : lines) {
            LineInTicket lineInTicket = new LineInTicket(line.getId(), ticketId);
            lineInTickets.add(lineInTicket);
        }
        return lineInTickets;
    }

    public static List<Character> charListFromArray(Object[] elementArr) {
//        String [] elementArr = (String[]) rs.getArray("elements").getArray();
        List<Character> elements = new ArrayList<Character>();
        for(Object s: elementArr) {
            char c = ((String) s).charAt(0);
            elements.add(c);
        }
        return elements;
    }
}
