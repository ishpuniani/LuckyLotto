package com.poppulo.dao;

import com.poppulo.TestUtils;
import com.poppulo.dao.LineDao;
import com.poppulo.entity.Line;
import com.poppulo.utils.LineUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LineDaoTest {

    @Resource
    private LineDao lineDao;

    @Test
    public void testSave() {
        // Creating lines and saving to lines table
        List<Line> lines = TestUtils.getTestLines();
        lines = lineDao.save(lines);

        // Getting all the lines from database
        List<Line> savedLines = lineDao.getAll();

        // Comparing the saved lines and generated lines
        Set<String> savedLineIds = savedLines.stream().map(Line::getId).collect(Collectors.toSet());
        Set<String> lineIds = lines.stream().map(Line::getId).collect(Collectors.toSet());
        Assert.assertEquals(lineIds, savedLineIds);
    }
}
